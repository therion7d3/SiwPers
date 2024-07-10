import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterOutlet, RouterLink, RouterLinkActive} from '@angular/router';
import {NgForOf, NgIf} from "@angular/common";
import { AuthService } from "./services/auth.service";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, NgForOf, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  title = 'siwpers';
  username: string | null = '';
  userId: number | null = null;

  constructor(private titleService: Title, protected authService: AuthService) {};

  ngOnInit(): void {
    this.titleService.setTitle("AuraEventi");
    this.username = this.authService.getUsername();
    this.userId = this.authService.getUserId();
  }

  logout() {
    this.authService.logout();
  }
}
