import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Event } from '../models/event.model';
import {ImageService} from "./image.service";

@Injectable({
  providedIn: 'root'
})

export class EventService {

  private baseUrl = 'http://localhost:8080/api/events';

  constructor(private http: HttpClient, private imageService : ImageService) {
  }

  getAllEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(this.baseUrl);
  }

  getEventsMadeByUser(userId: number): Observable<Event[]> {
    return this.http.get<Event[]>(this.baseUrl + "/organizer/" + userId);
  }

  getEventById(id: number): Observable<Event> {
    return this.http.get<Event>(this.baseUrl + "/" + id);
  }

  deleteEvent(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  modifyEvent(event: Event, image1: File, image2: File, image3: File): any {
    {
      const headers = new HttpHeaders({'Content-Type': 'application/json'});
      const body = JSON.stringify(event);
      const url = `${this.baseUrl}/${event.id}`;
      this.http.post(url, body, {headers})
        .subscribe({
          next: (response: any) => {
            console.log('Dati inviati con successo!');
            const idEvento = response.id;
            if (idEvento) {
              if (image1) {
                this.imageService.uploadImageForEventWithIndex(idEvento, image1, 0)
                  .subscribe(response => {
                    console.log('Upload successful', response);
                  });
              }
              if (image2) {
                this.imageService.uploadImageForEventWithIndex(idEvento, image2, 1)
                  .subscribe(response => {
                    console.log('Upload successful', response);
                  });
              }
              if (image3) {
                this.imageService.uploadImageForEventWithIndex(idEvento, image3, 2)
                  .subscribe(response => {
                    console.log('Upload successful', response);
                  });
              }
            }
            return true;
          },
          error: (error) => {
            console.error('Errore durante l\'invio dei dati:', error);
            return false;
          }
        });
    }
  }

  uploadEvent(evento: Event, image1: File, image2: File, image3: File) {
    const url = 'http://localhost:8080/api/events/addevent';
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = JSON.stringify(evento);

    this.http.post(url, body, { headers })
      .subscribe({
        next: (response: any) => {
          console.log('Dati inviati con successo!');
          const idEvento = response.id;
          if (idEvento) {
            if (image1) {
              this.imageService.uploadImageForEventWithIndex(idEvento, image1,0).subscribe();
            }
            if (image2) {
              this.imageService.uploadImageForEventWithIndex(idEvento, image2,1).subscribe();
            }
            if (image3){
              this.imageService.uploadImageForEventWithIndex(idEvento, image3,2).subscribe();
            }
          }
        },
        error: (error) => {
          console.error('Errore durante l\'invio dei dati:', error);
        }
      });
  }
}
