export class SortingParameters{
  sortingField: string;
  sortingOrder: string;

  constructor(sortingField: string, sortingOrder: string) {
    this.sortingField = sortingField;
    this.sortingOrder = sortingOrder;
  }
}
