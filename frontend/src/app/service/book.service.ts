import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../model/Book";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiServerUrl = '';

  constructor(private http: HttpClient) {
  }

  // public getBooks(): Observable<Book[]> {
  //   return this.http.get<Book[]>(`${this.apiServerUrl}/books`);
  // }
  //
  // public createBook(book: Book): Observable<Book> {
  //   return this.http.post<Book>(`${this.apiServerUrl}/books`);
  // }
  //
  // public updateBook(book: Book): Observable<Book> {
  //   return this.http.put<Book>(`${this.apiServerUrl}/books/`);
  // }
  //
  // public delete(bookId: number): Observable<void> {
  //   return this.http.delete<void>(`${this.apiServerUrl}/books/`);
  // }
}
