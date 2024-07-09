import {Component} from '@angular/core';
import {Event} from "../models/event.model";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {EventService} from "../services/event.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-nuovaricetta',
  standalone: true,
  imports: [
    NgIf,
    FormsModule,
    NgForOf
  ],
  templateUrl: './addevent.component.html',
  styleUrl: './addevent.component.css'
})
export class AddeventComponent {
  event: Event = {
    maxTickets: 0,
    tipoEvento: '',
    ownerId: this.authService.getUserId() ?? 0,
    ownerName: '',
    description: '',
    immagine1: '',
    immagine2: '',
    immagine3: '',
    title: '',
    immagineUrl: '',
    immagineUrl2: '',
    immagineUrl3: '',
    dataEvento: '',
    indirizzo: ''
  }

  formSubmitted: boolean = false;
  success: boolean = false;
  file1 : any;
  file2 : any;
  file3 : any;
  successMessage: string = 'Ottimo! Evento aggiunto con successo.';

  constructor(private router: Router , private eventService: EventService, protected authService: AuthService) {}

  onImageSelected(event: any, index: number): void {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      const file: File = fileList[0];
      if (index === 1) {
        this.file1 = file;
        this.readURL(file, 'immagineUrl');
      } else if (index === 2) {
        this.file2 = file;
        this.readURL(file, 'immagineUrl2');
      } else if (index === 3) {
        this.file3 = file;
        this.readURL(file, 'immagineUrl3');
      }
    }
  }

  readURL(file: File, property: string): void {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.event[property] = e.target.result;
    };
    reader.readAsDataURL(file);
  }

  submitForm() {
    this.formSubmitted = true;
    this.eventService.uploadEvent(this.event, this.file1, this.file2, this.file3);
    setTimeout(() => {this.router.navigate(['/']).then();}, 2000);
  }

  isValidDate(): boolean {
    if (!this.event.dataEvento) {
      return false; // Se non è stata selezionata nessuna data, non è valida
    }
    const selectedDate = new Date(this.event.dataEvento);
    const today = new Date();
    today.setHours(0, 0, 0, 0); // Azzera ore, minuti, secondi e millisecondi per confronto solo con la data

    return selectedDate > today;
  }

  todayDate(): string {
    const today = new Date();
    const day = today.getDate();
    const month = today.getMonth() + 1; // Mese è zero-based, quindi aggiungi 1
    const year = today.getFullYear();

    return `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;
  }
}

