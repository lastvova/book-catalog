export class FilterParameters{
  filteringField: string;
  filteringValue: string;
  filteringOperator?: string;


  constructor(filteringField: string, filteringValue: string, filteringOperator: string) {
    this.filteringField = filteringField;
    this.filteringValue = filteringValue;
    this.filteringOperator = filteringOperator;
  }
}
