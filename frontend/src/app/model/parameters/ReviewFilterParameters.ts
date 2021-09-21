export class ReviewFilterParameters {
  commenterName?: string;
  comment?: string;
  fromRating?: number;
  toRating?: number;
  bookName?: string;

  constructor(commenterName?: string, comment?: string, fromRating?: number, toRating?: number, bookName?: string) {
    this.commenterName = commenterName;
    this.comment = comment;
    this.fromRating = fromRating;
    this.toRating = toRating;
    this.bookName = bookName;
  }
}
