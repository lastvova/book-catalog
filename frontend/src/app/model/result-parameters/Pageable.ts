import {PageableSort} from "./PageableSort";

export class Pageable {
  sort: PageableSort
  offset: number;
  pageNumber: number;
  pageSize: number;
  unpaged: boolean;
  paged: boolean;

  constructor(sort: PageableSort, offset: number, pageNumber: number, pageSize: number, unpaged: boolean, paged: boolean) {
    this.sort = sort;
    this.offset = offset;
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.unpaged = unpaged;
    this.paged = paged;
  }
}
