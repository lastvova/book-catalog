export class PaginationParameters {
  currentPage: number;
  recordsPerPage: number;

  constructor(currentPage: number, recordsPerPage: number) {
    this.currentPage = currentPage;
    this.recordsPerPage = recordsPerPage;
  }
}
