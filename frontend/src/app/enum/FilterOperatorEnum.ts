export enum FilterOperatorEnum {
  EQUALS = "EQUALS",
  NOT_EQUALS = "NOT_EQUALS",
  CONTAINS = "CONTAINS"
}

export const FilterOperator2LabelMapping: Record<FilterOperatorEnum, string> = {
  [FilterOperatorEnum.EQUALS]: "EQUALS",
  [FilterOperatorEnum.NOT_EQUALS]: "NOT EQUALS",
  [FilterOperatorEnum.CONTAINS]: "CONTAINS",
};
