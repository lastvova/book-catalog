export class PageableSort{
  unsorted: boolean;
  sorted: boolean;
  empty: boolean;

  constructor(unsorted: boolean, sorted: boolean, empty: boolean) {
    this.unsorted = unsorted;
    this.sorted = sorted;
    this.empty = empty;
  }
}
