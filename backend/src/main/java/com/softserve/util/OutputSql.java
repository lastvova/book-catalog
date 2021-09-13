package com.softserve.util;

import com.softserve.enums.FilteringOperator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.NoSuchElementException;

@Getter
@Setter
public class OutputSql {

    private SortingParameters sortingParameters;
    private List<FilteringParameters> filteringParams;
    private PaginationParameters paginationParams;
    private String joinedEntity;

    public Query getQuery(EntityManager entityManager, Class entityClass, Integer totalRecords) {
        Query query = entityManager.createNativeQuery(buildSql(entityClass.getSimpleName(), totalRecords), entityClass);
        query.setParameter("sortingField", entityClass.getSimpleName() + "." + sortingParameters.getField());
        query.setParameter("sortingOrder", sortingParameters.getOrder());
        query.setParameter("recordsPerPage", paginationParams.getRecordsPerPage());
        query.setParameter("startFrom", paginationParams.getStartFrom());
        if (CollectionUtils.isNotEmpty(filteringParams)) {
            for (FilteringParameters params : filteringParams) {
                query.setParameter(params.getField(), params.getValue());
            }
        }
        return query;
    }

    private String buildSql(String entityName, Integer totalRecords) {
        String and = " and ";
        String sql = "select * from " + entityName + "s as " + entityName;
        calculateStartFromParameter(paginationParams,totalRecords);

        if (!StringUtils.isBlank(joinedEntity)) {
            sql += joinedEntity;
        }
        if (CollectionUtils.isNotEmpty(filteringParams)) {
            sql += " where ";
            for (FilteringParameters params : filteringParams) {

                sql += entityName + "." + params.getField();

                if (params.getOperator().equalsIgnoreCase(FilteringOperator.CONTAINS.toString())) {
                    sql += " LIKE concat('%', :" + params.getField() + ",'%')" + and;
                } else if (params.getOperator().equalsIgnoreCase(FilteringOperator.NOT_EQUALS.toString())) {
                    sql += " != :" + params.getField() + "" + and;
                } else sql += " = :" + params.getField() + "" + and;
            }
            sql = sql.substring(0, sql.length() - and.length());
        }

        sql += SortingParameters.SORTING_PART_OF_QUERY + PaginationParameters.PAGINATION_PART_OF_QUERY;
        return sql;
    }

    private void calculateStartFromParameter(PaginationParameters parameters, Integer totalRecords){
        parameters.setTotalPages((totalRecords % parameters.getRecordsPerPage()) == 0 ?
                totalRecords / parameters.getRecordsPerPage() :
                totalRecords / parameters.getRecordsPerPage() + 1);
        parameters.setStartFrom(parameters.getCurrentPage() * parameters.getRecordsPerPage() - parameters.getRecordsPerPage());
        if (totalRecords <= parameters.getStartFrom()) {
            throw new NoSuchElementException("Wrong input value for pagination, no such elements");
        }
    }
}
