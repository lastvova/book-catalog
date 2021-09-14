import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../model/Book";
import {environment} from "../../environments/environment";
import {FilterParameters} from "../model/FilterParameters";
import {Author} from "../model/Author";
import {DataWithTotalRecords} from "../model/DataWithTotalRecords";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getBooks(): Observable<DataWithTotalRecords> {
    return this.http.get<DataWithTotalRecords>(`${this.apiServerUrl}/api/books`);
  }

  public getBook(bookId: number): Observable<Book> {
    return this.http.get<Book>(`${this.apiServerUrl}/api/books/${bookId}`);
  }

  public createBook(book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.apiServerUrl}/api/books`, book);
  }

  public updateBook(book: Book): Observable<Book> {
    return this.http.put<Book>(`${this.apiServerUrl}/api/books/${book.id}`, book);
  }

  public deleteBook(bookId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/api/books/${bookId}`);
  }

  public filterBooks(filter: FilterParameters): Observable<DataWithTotalRecords> {
    return this.http.get<DataWithTotalRecords>(`${this.apiServerUrl}/books?filteringField=${filter.filteringField}
    &filteringValue=${filter.filteringValue}&filteringOperator=${filter.filteringOperator}`)
  }

  public getBooksWithPagination(currentPage: number, recordsPerPage: number): Observable<DataWithTotalRecords> {
    return this.http.get<DataWithTotalRecords>(`${this.apiServerUrl}/api/books?currentPage=${currentPage}&recordsPerPage=${recordsPerPage}`);
  }
}
