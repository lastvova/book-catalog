export class PaginationParameters {
  currentPage: number;
  recordsPerPage: number;
  totalRecords?: number;
  totalPages?: number;

  constructor(currentPage: number, recordsPerPage: number, totalRecords?: number, totalPages?: number) {
    this.currentPage = currentPage;
    this.recordsPerPage = recordsPerPage;
    this.totalRecords = totalRecords;
    this.totalPages = totalPages;
  }
}
