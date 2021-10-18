import {Book} from "./Book";

export class Author {
  id: number;
  //TODO no need for default values here
  firstName: string = '';
  secondName: string = '';
  rating: number;
  books: Book[];
  //TODO will always be blank unless calculated separately, no need for a default value anyway
  fullName: string = this.firstName + " " + this.secondName;
}
