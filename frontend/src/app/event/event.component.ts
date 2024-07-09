import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForOf, NgIf } from '@angular/common';
import { EventService } from '../services/event.service';
import { Event } from '../models/event.model';
import { ImageLoaderService } from "../services/image.loader.service";
import { FormsModule, NgForm } from "@angular/forms";
import { AuthService } from "../services/auth.service";

@Component({
  selector: 'app-event',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './event.component.html',
  styleUrl: './event.component.css'
})
export class EventComponent implements OnInit{
  modifiedEvent: Event = {
    maxTickets: 0,
    tipoEvento: "",
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
    dataEvento: ''
  };
  event!: Event;
  editMode = false;
  success: boolean = false;
  imagePreview: string | ArrayBuffer | null = null;
  file1 : any;
  file2 : any;
  file3 : any;


  constructor(
    private route: ActivatedRoute,
    private eventService: EventService,
    private imageLoaderService : ImageLoaderService,
    private authService : AuthService,
    private router: Router
  ) {}


  ngOnInit(): void {
    this.getEventId();
  }

  getEventId(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null && idParam !== undefined) {
      const id = +idParam;
      if (!isNaN(id)) {
        this.loadEvent(id);
      } else {
        console.error('L\'ID non è un numero valido');
      }
    } else {
      console.error('L\'ID non è presente nei parametri della route');
    }
  }

  loadEvent(id: number): void {
    this.eventService.getEventoById(id)
      .subscribe(
        (data) => {
          this.event = {
            ...data,
            immagineUrl: data.immagine1 ? this.loadImage(data.immagine1) : '',
            immagineUrl2: data.immagine2 ? this.loadImage(data.immagine2) : '',
            immagineUrl3: data.immagine3 ? this.loadImage(data.immagine3) : ''
          };

          this.modifiedEvent = { ...this.event };
        },
        (error) => {
          console.error('Errore nel caricamento della ricetta:', error);
        }
      );
  }

  loadImage(image :string) {
    return this.imageLoaderService.convertiBase64ToUrl(image);
  }

  toggleEditMode(): void {
    this.editMode = !this.editMode;
  }

  isEventOwner(): boolean {
    const currentUserId = this.authService.getUserId();
    return (this.event?.ownerId === currentUserId || (this.authService.getRole() === 'admin'));
  }

  saveChanges(): void {
    console.log('Modifiche salvate:', this.event);
    this.editMode = false;
    this.eventService.modifyEvent(this.modifiedEvent, this.file1, this.file2, this.file3);
    this.ngOnInit();
  }

  cancelEdit(): void {
    this.editMode = false;
    this.getEventId();
  }

  isFormValid(form: NgForm): boolean {
    if (!form) {
      return false;
    }
    for (const controlName of Object.keys(form.controls)) {
      const control = form.controls[controlName];
      if (control.invalid || control.value === '') {
        return false;
      }
    }
    return true;
  }

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
      this.modifiedEvent[property] = e.target.result;
    };
    reader.readAsDataURL(file);
  }

  deleteEvent(): void {
    const id = this.event.id;
    if (id != null)
    this.eventService.deleteEvent(id).subscribe();
    this.router.navigate(['/']).then();
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
