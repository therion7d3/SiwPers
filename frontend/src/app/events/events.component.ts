import { Component, OnInit } from '@angular/core';
import { Event } from '../models/event.model';
import { EventService } from '../services/event.service';
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {ImageLoaderService} from "../services/image.loader.service";

@Component({
  selector: 'app-ricette',
  templateUrl: './events.component.html',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NgOptimizedImage,
  ],
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {

  events!: Event[];

  constructor(private eventService: EventService, private imageLoaderService: ImageLoaderService) { }

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.eventService.getAllEvents()
      .subscribe(
        (data) => {
          this.events = data.map(event => ({
            ...event,
            immagineUrl: event.immagine1 ? this.loadImage(event.immagine1) : '',
          }));
        },
      );
  }

  loadImage(image :string) {
    return this.imageLoaderService.convertiBase64ToUrl(image);
  }
}
