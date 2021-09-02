package com.softserve.util;

import com.softserve.enums.FieldType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@Getter
@Setter
public class OutputSql {
    private Integer currentPage;
    private Integer recordsPerPage;
    private Integer startFrom;
    private Integer totalRecords;
    private Integer totalPages;

    private String sortingField;
    private String sortingOrder;

    private String filteringField;
    private String filteringValue;
    private String filteringOperator;

    public Query getQuery(EntityManager entityManager, Class entityClass) {
        Query query = entityManager.createNativeQuery(buildSql(entityClass.getSimpleName()), entityClass);
        query.setMaxResults(recordsPerPage);
        query.setFirstResult(startFrom);
        return query;
    }

    private String buildSql(String entityName) {
        validatePaging();
        validateSortingParams(sortingField, sortingOrder);
        String sql = "select * from " + entityName + "s as e ";
        if (!StringUtils.isBlank(filteringField)) {
            sql += " where e." + filteringField + " = " + filteringValue + " ";
        }
        if (!StringUtils.isBlank(sortingField)) {
            sql += " order by e." + sortingField + " " + sortingOrder;
        }
        return sql;
    }


    private void validateSortingParams(String field, String order) {
        if (StringUtils.isBlank(field) || !EnumUtils.isValidEnumIgnoreCase(FieldType.class, field)) {
            field = FieldType.ENTITY_CREATED_DATE.toString();
        }
        sortingField = field.substring(field.indexOf("_") + 1).toLowerCase(Locale.ROOT);
        if (!StringUtils.isBlank(order) && order.equals("DESC")) {
            sortingOrder = "DESC";
        } else sortingOrder = "ASC";
    }


    private void validatePaging() {
//        if (Objects.isNull(currentPage)) {
//            setCurrentPage(1);
//        }
////        тримати в такому форматі який приходить
//        if (Objects.isNull(recordsPerPage)) {
//            setRecordsPerPage(5);
//        }
        totalPages = (totalRecords % recordsPerPage) == 0 ? totalRecords / recordsPerPage : totalRecords / recordsPerPage + 1;
        startFrom = currentPage * recordsPerPage - recordsPerPage;
        if (totalRecords <= startFrom) {
            throw new NoSuchElementException("Wrong input value for pagination, no such elements");
        }
    }
}
