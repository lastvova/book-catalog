import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../model/Book";
import {environment} from "../../environments/environment";
import {FilterParameters} from "../model/parameters/FilterParameters";
import {DataWithTotalRecords} from "../model/result-parameters/DataWithTotalRecords";
import {SortingParameters} from "../model/parameters/SortingParameters";
import {PaginationParameters} from "../model/parameters/PaginationParameters";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getBooks(sortParameters: SortingParameters, pageParameters: PaginationParameters, filterParameters: FilterParameters): Observable<DataWithTotalRecords> {
    let requestUrl = '';
    if (pageParameters === undefined || pageParameters.currentPage === undefined || pageParameters.recordsPerPage === undefined) {
      requestUrl = requestUrl.concat('?page=1&size=5');
    } else {
      requestUrl = requestUrl.concat(`?page=${pageParameters.currentPage}&size=${pageParameters.recordsPerPage}`);
    }
    if (sortParameters?.sortBy != null) {
      requestUrl = requestUrl.concat(`&sortBy=${sortParameters.sortBy}&order=${sortParameters.sortOrder}`);
    }
    if (filterParameters?.filterBy != null && filterParameters.filterValue != null) {
      requestUrl = requestUrl.concat(`&filterBy=${filterParameters.filterBy}&filterValue=${filterParameters.filterValue}`);
    }
    return this.http.get<DataWithTotalRecords>(`${this.apiServerUrl}/api/books` + requestUrl);
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

}
