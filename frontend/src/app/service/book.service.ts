import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Base} from "./base.service";
import {Book} from "../model/Book";

@Injectable({
  providedIn: 'root'
})
export class BookService extends Base<Book>{

  constructor(http: HttpClient) {
    super('/books',http);
  }
}
