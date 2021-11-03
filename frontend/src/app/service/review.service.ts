import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Review} from "../model/Review";
import {Base} from "./base.service";

@Injectable({
  providedIn: 'root'
})
export class ReviewService extends Base<Review> {

  constructor(http: HttpClient) {
    super('/reviews', http);
  }
}
