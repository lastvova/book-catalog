import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Author} from "../model/Author";
import {Base} from "./base.service";

@Injectable({
  providedIn: 'root'
})
export class AuthorService extends Base<Author> {

  constructor(http: HttpClient) {
    super('/authors', http);
  }
}
