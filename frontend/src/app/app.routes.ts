import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import {RegisterComponent} from "./register/register.component";
import {canActivate} from "./services/auth.guard";
import {ProfileComponent} from "./profile/profile.component";
import {ImageViewComponent} from "./imageviewer/imageviewer.component";
import {ImageuploaderComponent} from "./imageuploader/imageuploader.component";
import {EventsComponent} from "./events/events.component";
import {AddeventComponent} from "./addEvent/addevent.component";
import {MyeventsComponent} from "./myevents/myevents.component";
import {EventComponent} from "./event/event.component";
import {OrganizzatoriComponent} from "./organizzatori/organizzatori.component";

const routeConfig: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [canActivate]},
  { path: 'register', component: RegisterComponent, canActivate: [canActivate]},
  { path: 'myprofile', component: ProfileComponent, canActivate: [canActivate]},
  { path: 'imagev', component: ImageViewComponent, canActivate: [canActivate]},
  { path: 'imageu', component: ImageuploaderComponent, canActivate: [canActivate]},
  { path: '', component: EventsComponent, canActivate: [canActivate]},
  { path: 'organizzaevento', component: AddeventComponent, canActivate: [canActivate]},
  { path: 'register', component: RegisterComponent, canActivate: [canActivate]},
  { path: 'myevents', component: MyeventsComponent, canActivate: [canActivate]},
  { path: 'events/:id', component: EventComponent, canActivate: [canActivate]},
  { path: 'organizzatori', component: OrganizzatoriComponent, canActivate: [canActivate]},
  { path: 'organizzatori/:id', component: ProfileComponent, canActivate: [canActivate]},
  { path: '**', redirectTo: '' }
];

export default routeConfig;
