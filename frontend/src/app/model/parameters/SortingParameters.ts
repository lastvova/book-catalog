export class SortingParameters{
  sortBy: string;
  sortOrder: string = 'ASC';
  reverse: boolean = true;

  constructor(sortingField: string, sortingOrder: string, reverse: boolean) {
    this.sortBy = sortingField;
    this.sortOrder = sortingOrder;
    this.reverse = reverse;
  }
}
