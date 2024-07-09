import { Component } from '@angular/core';
import { ImageService } from '../services/image.service';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-image-upload',
  standalone: true,
  imports: [
    FormsModule
  ],
  template: `
    <input type="file" (change)="onFileSelected($event)">
    <input type="number" [(ngModel)]="userId" placeholder="User ID">
    <button (click)="onUpload()">Upload</button>
  `
})
export class ImageuploaderComponent {
  selectedFile: File | null = null;
  userId: number | null = null;

  constructor(private imageService: ImageService) { }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  onUpload(): void {
    if (this.selectedFile && this.userId) {
      this.imageService.uploadImageForUser(this.userId, this.selectedFile)
        .subscribe(response => {
          console.log('Upload successful', response);
        });
    }
  }
}
