export interface Event {
  [key: string]: any;
  id? : number;
  ownerId: number;
  ownerName: string;
  maxTickets: number;
  tipoEvento: string;
  description: string;
  immagine1: string;
  immagine2: string;
  immagine3: string;
  title: string;
  immagineUrl?: string;
  immagineUrl2?: string;
  immagineUrl3?: string;
  dataEvento: string;
  indirizzo: string;
}


