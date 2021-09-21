import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {PageSortFilterParameters} from "../model/parameters/PageSortFilterParameters";
import {Observable} from "rxjs";
import {DataWithTotalRecords} from "../model/result-parameters/DataWithTotalRecords";
import {Review} from "../model/Review";

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getReviews(pageSortFilterParameters: PageSortFilterParameters): Observable<DataWithTotalRecords> {
    return this.http.get<DataWithTotalRecords>(`${this.apiServerUrl}/api/reviews`);
  }

  public getReviewsWithParameters(pageSortFilterParameters: PageSortFilterParameters): Observable<DataWithTotalRecords> {
    return this.http.post<DataWithTotalRecords>(`${this.apiServerUrl}/api/reviews`, pageSortFilterParameters);
  }

  public getReview(reviewId: number): Observable<Review> {
    return this.http.get<Review>(`${this.apiServerUrl}/api/reviews/${reviewId}`);
  }

  public createReview(review: Review): Observable<Review> {
    return this.http.post<Review>(`${this.apiServerUrl}/api/reviews/create`, review);
  }
}
