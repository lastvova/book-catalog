import {Pageable} from "./Pageable";
import {PageableSort} from "./PageableSort";

export class DataWithTotalRecords {
  content: any[];
  pageable: Pageable;
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  sort: PageableSort;
  first: boolean;
  numberOfElements: number;
  empty: boolean;


  constructor(content: any[], pageable: Pageable, last: boolean, totalPages: number, totalElements: number, size: number, number: number, sort: PageableSort, first: boolean, numberOfElements: number, empty: boolean) {
    this.content = content;
    this.pageable = pageable;
    this.last = last;
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.size = size;
    this.number = number;
    this.sort = sort;
    this.first = first;
    this.numberOfElements = numberOfElements;
    this.empty = empty;
  }
}
