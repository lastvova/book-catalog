export enum BookFieldType {
  BOOK_NAME = "BOOK_NAME",
  BOOK_YEAR_PUBLISHER= "BOOK_YEAR_PUBLISHER",
  BOOK_ISBN = "BOOK_ISBN",
  BOOK_PUBLISHER= "BOOK_PUBLISHER",
  RATING= "RATING",
  BOOK_AUTHOR = "BOOK_AUTHOR"
}
export const BookFieldType2LabelMapping: Record<BookFieldType, string> = {
  [BookFieldType.BOOK_NAME]: "BOOK NAME",
  [BookFieldType.BOOK_YEAR_PUBLISHER]: "BOOK YEAR PUBLISHER",
  [BookFieldType.BOOK_ISBN]: "BOOK ISBN",
  [BookFieldType.BOOK_PUBLISHER]: "BOOK PUBLISHER",
  [BookFieldType.RATING]: "RATING",
  [BookFieldType.BOOK_AUTHOR]: "AUTHOR",
};
