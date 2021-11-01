import {Pageable} from "./Pageable";
import {PageableSort} from "./PageableSort";

export class DataWithTotalRecords {
  //TODO generics could be used
  content: any[];
  pageable: Pageable;
  sort: PageableSort;
  //TODO it would be good to structure these class variables
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}
