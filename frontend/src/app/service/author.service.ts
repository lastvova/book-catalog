import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Author} from "../model/Author";
import {DataWithTotalRecords} from "../model/result-parameters/DataWithTotalRecords";
import {PageSortFilterParameters} from "../model/parameters/PageSortFilterParameters";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getAuthors(pageSortFilterParameters: PageSortFilterParameters): Observable<DataWithTotalRecords> {
    return this.http.get<DataWithTotalRecords>(`${this.apiServerUrl}/api/authors`);
  }

  public getAuthorsWithParameters(pageSortFilterParameters: PageSortFilterParameters): Observable<DataWithTotalRecords> {
    return this.http.post<DataWithTotalRecords>(`${this.apiServerUrl}/api/authors`, pageSortFilterParameters);
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
}
