export class PageSortFilterParameters {
  sortField: string;
  order: string;
  reverseForSorting: boolean = true;
  pageNumber: number;
  pageSize: number;
  pattern?: {};


  constructor(sortField: string, order: string, pageNumber: number, pageSize: number, pattern?: any) {
    this.sortField = sortField;
    this.order = order;
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.pattern = pattern;
  }
}
