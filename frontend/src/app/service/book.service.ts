import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../model/Book";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.apiServerUrl}/books`);
  }

  public getBook(bookId: number): Observable<Book> {
    return this.http.get<Book>(`${this.apiServerUrl}/books/${bookId}`);
  }

  public createBook(book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.apiServerUrl}/books`, book);
  }

  public updateBook(book: Book): Observable<Book> {
    return this.http.put<Book>(`${this.apiServerUrl}/books/${book.id}`, book);
  }

  public deleteBook(bookId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/books/${bookId}`);
  }
}
