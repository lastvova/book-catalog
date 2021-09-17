import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Author} from "../model/Author";
import {DataWithTotalRecords} from "../model/result-parameters/DataWithTotalRecords";
import {SortingParameters} from "../model/parameters/SortingParameters";
import {PaginationParameters} from "../model/parameters/PaginationParameters";
import {FilterParameters} from "../model/parameters/FilterParameters";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getAuthors(sortParameters: SortingParameters, pageParameters: PaginationParameters, filterParameters: FilterParameters): Observable<DataWithTotalRecords> {
    let requestUrl = '';
    if (pageParameters === undefined || pageParameters.currentPage === undefined || pageParameters.recordsPerPage === undefined) {
      requestUrl = requestUrl.concat('?page=0&size=5');
    } else {
      requestUrl = requestUrl.concat(`?page=${pageParameters.currentPage}&size=${pageParameters.recordsPerPage}`);
    }
    if (sortParameters?.sortBy != null) {
      requestUrl = requestUrl.concat(`&sortBy=${sortParameters.sortBy}&order=${sortParameters.sortOrder}`);
    }
    if (filterParameters?.filterBy != null && filterParameters.filterValue != null) {
      requestUrl = requestUrl.concat(`&filterBy=${filterParameters.filterBy}&filterValue=${filterParameters.filterValue}`);
    }
    return this.http.get<DataWithTotalRecords>(`${this.apiServerUrl}/api/authors` + requestUrl);
  }

  public getAuthor(authorId: number): Observable<Author> {
    return this.http.get<Author>(`${this.apiServerUrl}/api/authors/${authorId}`);
  }

  public createAuthor(author: Author): Observable<Author> {
    return this.http.post<Author>(`${this.apiServerUrl}/api/authors`, author);
  }

  public updateAuthor(author: Author): Observable<Author> {
    return this.http.put<Author>(`${this.apiServerUrl}/api/authors/${author.id}`, author);
  }

  public deleteAuthor(authorId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/api/authors/${authorId}`);
  }

  public getAuthorsWithPagination(currentPage: number, recordsPerPage: number): Observable<DataWithTotalRecords> {
    return this.http.get<DataWithTotalRecords>(`${this.apiServerUrl}/api/authors?page=${currentPage}&size=${recordsPerPage}`);
  }
}
