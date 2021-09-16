import {Book} from "./Book";

export class Author{
  id: number;
  firstName: string;
  secondName: string;
  rating: number;
  books?: Book[];

  constructor(id: number, firstname: string, secondName: string, rating: number, books: Book[]) {
    this.id = id;
    this.firstName = firstname;
    this.secondName = secondName;
    this.books = books;
    this.rating = rating;
  }
}
