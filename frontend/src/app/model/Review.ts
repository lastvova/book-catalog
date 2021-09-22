import {Book} from "./Book";

export class Review {
  id: number;
  commenterName: string;
  comment: string;
  rating: number;
  book: Book;
}
