import {Author} from "./Author";

export class Book {
  id: number;
  name: string;
  yearPublisher: number;
  publisher: string;
  isbn: string;
  rating: number;
  authors: Author[];
}
