import {Injectable} from '@angular/core';
import {Book} from "../model/Book";
import {TestData} from "../data/TestData";
import {Author} from "../model/Author";
import {Review} from "../model/Review";

@Injectable({
  providedIn: 'root'
})
export class DataHandlerService {

  constructor() {
  }

  getBooks(): Book[] {
    return TestData.books;/*books*/
  }

  getAuthors(): Author[] {
    return TestData.authors;/*authors*/
  }

  getReviews(): Review[] {
    return TestData.reviews;/*reviews*/
  }
}
