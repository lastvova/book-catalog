export class AuthorFilterParameters {
  firstName?: string;
  secondName?: string;
  fromRating?: number;
  toRating?: number;


  constructor(firstName?: string, secondName?: string, fromRating?: number, toRating?: number) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.fromRating = fromRating;
    this.toRating = toRating;
  }
}
