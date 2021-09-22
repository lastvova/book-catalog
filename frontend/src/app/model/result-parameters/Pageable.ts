import {PageableSort} from "./PageableSort";

export class Pageable {
  sort: PageableSort
  offset: number;
  pageNumber: number;
  pageSize: number;
  unpaged: boolean;
  paged: boolean;
}
