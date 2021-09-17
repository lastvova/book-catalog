package com.softserve.enums;

public enum EntityFields {
    ENTITY_ID("Integer", "id"),
    AUTHOR_FIRST_NAME("String", "firstName"),
    AUTHOR_SECOND_NAME("String", "secondName"),
    RATING("Decimal", "rating"),
    BOOK_NAME("String", "name"),
    BOOK_YEAR_PUBLISHER("Integer","yearPublisher"),
    BOOK_ISBN("Integer", "isbn"),
    BOOK_PUBLISHER("String","publisher"),
    BOOK_RATING("Decimal", "rating"),
    REVIEW_COMMENTER_NAME("String","commenterName"),
    REVIEW_COMMENT("String","comment"),
    REVIEW_RATING("Decimal","rating");

    public final String fieldType;
    public final String fieldName;

    EntityFields(String fieldType, String fieldName) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
    }
}
