export class DataWithTotalRecords{
  data: any;
  totalRecords:number;

  constructor(data: any, totalRecords: number) {
    this.data = data;
    this.totalRecords = totalRecords;
  }
}
