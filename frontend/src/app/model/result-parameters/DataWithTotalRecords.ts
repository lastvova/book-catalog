import {Pageable} from "./Pageable";
import {PageableSort} from "./PageableSort";

export class DataWithTotalRecords {
  content: any[];
  pageable: Pageable;
  sort: PageableSort;
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}
