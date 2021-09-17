export enum AuthorFieldType {
  AUTHOR_FIRST_NAME = "AUTHOR_FIRST_NAME",
  AUTHOR_SECOND_NAME = "AUTHOR_SECOND_NAME",
  RATING = "RATING",
}

export const AuthorFieldType2LabelMapping: Record<AuthorFieldType, string> = {
  [AuthorFieldType.AUTHOR_FIRST_NAME]: "AUTHOR FIRST NAME",
  [AuthorFieldType.AUTHOR_SECOND_NAME]: "AUTHOR SECOND NAME",
  [AuthorFieldType.RATING]: "RATING",
};
