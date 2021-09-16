export class DataWithTotalRecords {
  data: any;
  pageable: number;

  constructor(data: any, totalRecords: number) {
    this.data = data;
    this.totalRecords = totalRecords;
  }
}
