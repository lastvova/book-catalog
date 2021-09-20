export class BookFilterParameters {
  name?: string;
  yearPublisher?: number;
  isbn?: BigInteger;
  publisher?: string;
  fromRating?: number;
  toRating?: number;
  authorNameAndSecondName?: string;


  constructor(name?: string, yearPublisher?: number, isbn?: BigInteger, publisher?: string, fromRating?: number, toRating?: number, authorNameAndSecondName?: string) {
    this.name = name;
    this.yearPublisher = yearPublisher;
    this.isbn = isbn;
    this.publisher = publisher;
    this.fromRating = fromRating;
    this.toRating = toRating;
    this.authorNameAndSecondName = authorNameAndSecondName;
  }
}
