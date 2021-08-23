import {Author} from "./Author";

export class Book{
  id: number;
  name: string;
  yearPublisher: number;
  isbn: bigint;
  rating: number;
  authors: Author[]

  constructor(id: number, name: string, yearPublisher: number, isbn: bigint, rating: number, authors: Author[]) {
    this.id = id;
    this.name = name;
    this.yearPublisher = yearPublisher;
    this.isbn = isbn;
    this.rating = rating;
    this.authors = authors;
  }
}
