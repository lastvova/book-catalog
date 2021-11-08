import {Component, OnInit, ViewChild} from '@angular/core';
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {NotificationService} from "../../service/notification.service";
import {RouterService} from "../../service/router.service";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {Base} from "../../service/base.service";
import {SelectionModel} from "@angular/cdk/collections";
import {NgForm} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.css']
})
export abstract class BaseComponent<T> implements OnInit {

  public entityArray: T[] = [];
  public editEntity: T;
  public numberOfRecords: number;
  public totalRecords: number;
  public totalPages: number;

  public selection = new SelectionModel<T>(true, []);

  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();

  @ViewChild('matPaginator') matPaginator: MatPaginator;
  @ViewChild('filterForm') filterForm: NgForm;

  protected constructor(public notificationService: NotificationService, public router: RouterService, public baseService: Base<T>) {

  }

  ngOnInit(): void {
    this.getAllWithParameters();
  }

  public abstract isInvalidForm(form: NgForm): boolean;

  public abstract manipulateWithEntityFields(t: T): T;

  public abstract manipulateWithEntityFieldsWhenUpdate(t: T): T;

  public abstract getIdsFromSelection(): number[];

  public abstract getFilterParameters(): any;

  public getAllWithParameters() {
    this.baseService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<T>) => {
        this.entityArray = response.content;
        this.totalRecords = response.totalElements;
        this.totalPages = response.totalPages;
        this.numberOfRecords = response.number;
        this.pageSortFilterParameters.pageNumber = response.number;
        this.pageSortFilterParameters.pageSize = response.size;
      });
    this.selection.clear();
  }

  public create(createForm: NgForm): void {
    if (this.isInvalidForm(createForm)) {
      return;
    }
    let t = createForm.value;
    t = this.manipulateWithEntityFields(t);
    this.baseService.create(t).subscribe(
      (response: T) => {
        this.getAllWithParameters();
        createForm.reset();
        this.notificationService.successSnackBar("Success!");
      },
      (error: HttpErrorResponse) => {
        this.notificationService.errorSnackBar((error.message))
        createForm.reset();
      }
    );
    document.getElementById('close-create-form')!.click();
  }

  public update(updateForm: NgForm): void {
    if (this.isInvalidForm(updateForm)) {
      return;
    }
    this.editEntity = updateForm.value;
    this.editEntity = this.manipulateWithEntityFieldsWhenUpdate(this.editEntity);
    this.baseService.update(this.editEntity).subscribe(
      () => {
        this.getAllWithParameters();
        this.notificationService.successSnackBar("Success!");
        updateForm.reset(this.editEntity);
      },
      (error: HttpErrorResponse) => {
        this.notificationService.errorSnackBar((error.message))
        updateForm.reset();
      }
    );
    document.getElementById('close-edit-form')!.click();
  }

  public delete(tId: number) {
    this.baseService.delete(tId).subscribe(
      (response: void) => {
        this.notificationService.successSnackBar("Success!");
        this.getAllWithParameters();
      }
    )
  }

  public bulkDelete() {
    if (this.selection.hasValue()) {
      let ids = this.getIdsFromSelection();
      this.baseService.bulkDelete(ids).subscribe(
        (response: void) => {
          this.notificationService.successSnackBar("Success!");
          this.getAllWithParameters();
        }
      )
    }
  }

  public filterEntity(): void {
    this.pageSortFilterParameters.pattern = this.getFilterParameters();
    this.matPaginator.pageIndex = 0;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getAllWithParameters()
  }

  // Whether the number of selected elements matches the total number of rows.
  public isAllSelectedInSelection() {
    const numSelected = this.selection.selected.length;
    const numRows = this.entityArray.length;
    return numSelected === numRows;
  }

  // Selects all rows if they are not all selected; otherwise clear selection.
  public masterToggleForSelection() {
    this.isAllSelectedInSelection() ?
      this.selection.clear() :
      this.entityArray.forEach(row => this.selection.select(row));
  }

  public onPageChange(event: PageEvent) {
    this.pageSortFilterParameters.pageNumber = event.pageIndex;
    this.pageSortFilterParameters.pageSize = event.pageSize;
    this.selection.clear();
    this.getAllWithParameters();
  }

  public sortByColumn(sortBy: string) {
    this.pageSortFilterParameters.sortField = sortBy;
    //TODO not sure why this is required, could be rewritten using the order
    this.pageSortFilterParameters.reverseForSorting = !this.pageSortFilterParameters.reverseForSorting;
    if (this.pageSortFilterParameters.reverseForSorting) {
      this.pageSortFilterParameters.order = 'ASC'
    } else {
      this.pageSortFilterParameters.order = 'DESC'
    }
    this.selection.clear();
    this.getAllWithParameters();
  }

  public resetForm(filterForm: NgForm) {
    filterForm.reset();
    this.pageSortFilterParameters.pattern = {};
    this.pageSortFilterParameters.pageSize = this.matPaginator.pageSize;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getAllWithParameters();
  }

  public changeElementsPerPage(event: number) {
    this.pageSortFilterParameters.pageNumber = 0;
    this.pageSortFilterParameters.pageSize = event;
    this.matPaginator.pageIndex = 0;
    this.matPaginator.pageSize = event;
    this.getAllWithParameters()
  }

  public getInfoAboutRecords(): string {
    if (this.totalRecords > 0) {
      let currentRecords = 1 + this.matPaginator.pageSize * this.numberOfRecords;
      let currentRecordsTo = this.totalRecords <= ((1 + this.numberOfRecords) * this.matPaginator.pageSize) ? this.totalRecords
        : ((1 + this.numberOfRecords) * this.matPaginator.pageSize);
      return "Showing " + currentRecords + " to " + currentRecordsTo + " of " + this.totalRecords;
    }
    return "Showing 0";
  }

  //TODO out of place method
  public formatIsbn(isbn: string): string {
    return isbn.substring(0, 3) + "-" + isbn.substring(3, 4) + "-" + isbn.substring(4, 8) + "-" + isbn.substring(8, 12) + "-" + isbn.substring(12, 13);
  }

}


