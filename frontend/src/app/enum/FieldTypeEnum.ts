export enum FieldTypeEnum {
  AUTHOR_ID = "id",
  AUTHOR_FIRST_NAME = "firstName",
  AUTHOR_SECOND_NAME = "secondName" ,
  BOOK_NAME = "name",
  BOOK_YEAR_PUBLISHER= "yearPublisher",
  BOOK_ISBN = "isbn",
  BOOK_PUBLISHER= "publisher",
  BOOK_RATING= "rating",
  REVIEW_COMMENTER_NAME = "commenterName",
  REVIEW_COMMENT = "comment",

}
export const FieldType2LabelMapping: Record<FieldTypeEnum, string> = {
  [FieldTypeEnum.AUTHOR_ID]: "AUTHOR ID",
  [FieldTypeEnum.AUTHOR_FIRST_NAME]: "AUTHOR FIRST NAME",
  [FieldTypeEnum.AUTHOR_SECOND_NAME]: "AUTHOR SECOND NAME",
  [FieldTypeEnum.BOOK_NAME]: "BOOK NAME",
  [FieldTypeEnum.BOOK_YEAR_PUBLISHER]: "BOOK YEAR PUBLISHER",
  [FieldTypeEnum.BOOK_ISBN]: "BOOK ISBN",
  [FieldTypeEnum.BOOK_PUBLISHER]: "BOOK PUBLISHER",
  [FieldTypeEnum.BOOK_RATING]: "BOOK RATING",
  [FieldTypeEnum.REVIEW_COMMENTER_NAME]: "REVIEW COMMENTER NAME",
  [FieldTypeEnum.REVIEW_COMMENT]: "REVIEW COMMENT",
};
