import {Book} from "./Book";

export class Author {
  id: number;
  firstName: string;
  secondName: string;
  rating: number;
  books: Book[];
  fullName: string;
}
