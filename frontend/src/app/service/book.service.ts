import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../model/Book";
import {environment} from "../../environments/environment";
import {DataWithTotalRecords} from "../model/result-parameters/DataWithTotalRecords";
import {PageSortFilterParameters} from "../model/parameters/PageSortFilterParameters";

@Injectable({
  providedIn: 'root'
})
//TODO the same remarks as in the author.service.ts
export class BookService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getBooks(pageSortFilterParameters: PageSortFilterParameters): Observable<DataWithTotalRecords> {
    return this.http.get<DataWithTotalRecords>(`${this.apiServerUrl}/api/books`);
  }

  public getBooksWithParameters(pageSortFilterParameters: PageSortFilterParameters): Observable<DataWithTotalRecords> {
    return this.http.post<DataWithTotalRecords>(`${this.apiServerUrl}/api/books`, pageSortFilterParameters);
  }

  public getBook(bookId: number): Observable<Book> {
    return this.http.get<Book>(`${this.apiServerUrl}/api/books/${bookId}`);
  }

  public createBook(book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.apiServerUrl}/api/books/create`, book);
  }

  public updateBook(book: Book): Observable<Book> {
    return this.http.put<Book>(`${this.apiServerUrl}/api/books/`, book);
  }

  public deleteBook(bookId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/api/books/${bookId}`);
  }

  public bulkDelete(booksIds: number[]): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/api/books?ids=${booksIds}`);
  }

}
