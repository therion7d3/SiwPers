import {Component, OnInit} from '@angular/core';
import {Event} from "../models/event.model";
import {EventService} from "../services/event.service";
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {AuthService} from "../services/auth.service";
import {ImageLoaderService} from "../services/image.loader.service";

@Component({
  selector: 'app-myrecipes',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NgOptimizedImage
  ],
  templateUrl: './myevents.component.html',
  styleUrl: './myevents.component.css'
})
export class MyeventsComponent implements OnInit {
  userId!: number;
  events!: Event[];
  selectedEvent: Event | null = null;

  constructor(private eventService: EventService, private authService: AuthService, private imageLoaderService: ImageLoaderService) {
  }

  ngOnInit(): void {
    this.caricaRicette();
  }

  caricaRicette(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.eventService.getEventsMadeByUser(userId)
        .subscribe(
          (data) => {
            this.events = data.map(event => ({
              ...event,
              immagineUrl: this.loadImage(event.immagine1)
            }));
          },
          (error) => {
            console.error('Errore nel caricamento delle ricette:', error);
          }
        );
    }
  }

  loadImage(image: string) {
    return this.imageLoaderService.convertiBase64ToUrl(image);
  }

  deleteEvent(id: number | undefined): void {
    if (id === undefined) {
      console.error('ID della ricetta non definito.');
      return;
    }


    this.eventService.deleteEvent(id).subscribe(
      () => {
        // Rimuovi la ricetta dall'array locale
        this.events = this.events.filter((event) => event.id !== id);
        console.log('Ricetta eliminata con successo.');
      },
      (error) => {
        console.error('Errore durante l\'eliminazione della ricetta:', error);
      }
    );
  }
}
