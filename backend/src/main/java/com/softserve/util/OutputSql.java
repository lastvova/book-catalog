package com.softserve.util;

import com.softserve.enums.FilteringOperator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Getter
@Setter
public class OutputSql {

    private SortingParameters sortingParameters;
    private List<FilteringParameters> filteringParams;
    private PaginationParameters paginationParams;

    public Query getQuery(EntityManager entityManager, Class entityClass, Integer totalRecords) {
        Query query = entityManager.createNativeQuery(buildSql(entityClass.getSimpleName(), totalRecords), entityClass);
        query.setParameter("sortingField", sortingParameters.getField());
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
        if (CollectionUtils.isNotEmpty(filteringParams)) {
            sql +=" where ";
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

        sql += sortingParameters.buildOrderPartOfQuery() + paginationParams.buildPaginationPartOfQuery(totalRecords);
        return sql;
    }
}
