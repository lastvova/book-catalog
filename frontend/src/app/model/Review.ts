import {Book} from "./Book";

export class Review {
  id: number;
  commenterName: string;
  comment: string;
  rating: number;
  book: Book;

  constructor(id: number, commenterName: string, comment: string, rating: number, book: Book) {
    this.id = id;
    this.commenterName = commenterName;
    this.comment = comment;
    this.rating = rating;
    this.book = book;
  }
}
