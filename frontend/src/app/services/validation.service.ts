import {Injectable} from "@angular/core";
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})

export class ValidationService {
  constructor() {}

  getAuthToken(): string | null {
    return window.localStorage.getItem('token');
  }

  setAuthToken(token: string | null): void {
    if (token !== null)
      window.localStorage.setItem('token', token);
    else {
      window.localStorage.removeItem('token');
    }
  }

  request(method: string, url: string, data: any): Promise<any> {
    let headers = {};

    if (this.getAuthToken() !== null) {
      headers= {"Authorization": "Bearer " + this.getAuthToken()};
    }

    return axios({
      method: method,
      url: url,
      data: data,
      headers: headers,});
  }
}
