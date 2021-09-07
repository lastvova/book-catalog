import {Author} from "./Author";

export class Book {
  id: number;
  name: string;
  yearPublisher: number;
  publisher: string;
  isbn: number;
  rating: number;
  authors: Author[];

  constructor(id: number, name: string, publisher: string, yearPublisher: number, isbn: number, rating: number, authors: Author[]) {
    this.id = id;
    this.name = name;
    this.publisher = publisher;
    this.yearPublisher = yearPublisher;
    this.isbn = isbn;
    this.rating = rating;
    this.authors = authors;
  }
}
