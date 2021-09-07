import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Author} from "../model/Author";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getAuthors(): Observable<Author[]> {
    return this.http.get<Author[]>(`${this.apiServerUrl}/api/authors`);
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
