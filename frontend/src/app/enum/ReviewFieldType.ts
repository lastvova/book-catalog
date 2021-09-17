export enum ReviewFieldType {
  COMMENTER_NAME = "REVIEW_COMMENTER_NAME",
  COMMENT = "REVIEW_COMMENT",
  RATING = "REVIEW_RATING"
}

export const ReviewFieldType2LabelMapping: Record<ReviewFieldType, string> = {
  [ReviewFieldType.COMMENTER_NAME]: "REVIEW COMMENTER NAME",
  [ReviewFieldType.COMMENT]: "REVIEW COMMENT",
  [ReviewFieldType.RATING]: "REVIEW RATING",
};
