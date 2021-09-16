export class FilterParameters{
  filterBy: string;
  filterValue: string;
  filteringOperator?: string;


  constructor(filteringField: string, filteringValue: string, filteringOperator: string) {
    this.filterBy = filteringField;
    this.filterValue = filteringValue;
    this.filteringOperator = filteringOperator;
  }
}
