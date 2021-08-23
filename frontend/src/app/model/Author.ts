import {Book} from "./Book";

export class Author{
  id: number;
  firstname: string;
  secondName: string;
  books?: Book[]

  constructor(id: number, firstname: string, secondName: string, books: Book[]) {
    this.id = id;
    this.firstname = firstname;
    this.secondName = secondName;
    this.books = books;
  }
}
