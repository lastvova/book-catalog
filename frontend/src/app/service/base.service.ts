import {Inject, Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {PageSortFilterParameters} from "../model/parameters/PageSortFilterParameters";
import {Observable} from "rxjs";
import {DataWithTotalRecords} from "../model/result-parameters/DataWithTotalRecords";
import {Author} from "../model/Author";

@Injectable({
  providedIn: 'root'
})
export class Base<T> {

  private apiServerUrl = environment.apiBaseUrl;
  private readonly modelUrl: string;

  constructor(@Inject(String) private url: string, private http: HttpClient) {
    this.modelUrl = url;
  }

  public getAllWithParameters(pageSortFilterParameters: PageSortFilterParameters): Observable<DataWithTotalRecords> {
    return this.http.post<DataWithTotalRecords>(this.apiServerUrl + this.modelUrl, pageSortFilterParameters);
  }

  public getById(id: number): Observable<T> {
    return this.http.get<T>(this.apiServerUrl + this.modelUrl + '/' + id);
  }

  public create(t: T): Observable<T> {
    //TODO is the data in the grid being refreshed right after the creation?
    return this.http.post<T>(this.apiServerUrl + this.modelUrl + '/create', t);
  }

  public update(t: T): Observable<T> {
    return this.http.put<T>(this.apiServerUrl + this.modelUrl, t);
  }

  public delete(id: number): Observable<void> {
    //TODO is the data in the grid being refreshed right after the deletion?
    return this.http.delete<void>(this.apiServerUrl + this.modelUrl + '/' + +id);
  }

  public bulkDelete(ids: number[]): Observable<void> {
    return this.http.delete<void>(this.apiServerUrl + this.modelUrl + '?ids=' + ids);
  }
}
