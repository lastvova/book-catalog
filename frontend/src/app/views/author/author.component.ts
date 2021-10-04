import {Component, OnInit, ViewChild} from '@angular/core';
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {AuthorFilterParameters} from "../../model/parameters/AuthorFilterParameters";
import {MatAccordion} from "@angular/material/expansion";
import {NotificationService} from "../../service/notification.service";

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  public authors: Author[] = [];
  public detailAuthor: Author = new Author();
  public editAuthor: Author = new Author();
  public deletedAuthor: Author = new Author();
  public numberOfRecords: number;
  public totalRecords: number;
  public totalPages: number;

  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();
  public authorFilterParameters: AuthorFilterParameters;

  @ViewChild('matPaginator') matPaginator: MatPaginator;
  @ViewChild('filterForm') filterForm: NgForm;
  @ViewChild(MatAccordion) accordion: MatAccordion;

  constructor(private authorService: AuthorService, private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.getAuthors();
  }

  public getAuthors(): void {
    this.authorService.getAuthors(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.authors = [];
        this.authors = response.content;
        this.totalRecords = response.totalElements;
        this.totalPages = response.totalPages;
        this.numberOfRecords = response.number;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getAuthorsWithParameters(): void {
    this.authorService.getAuthorsWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.authors = [];
        this.authors = response.content;
        this.totalRecords = response.totalElements;
        this.totalPages = response.totalPages;
        this.numberOfRecords = response.number;
        this.pageSortFilterParameters.pageNumber = response.number;
        this.pageSortFilterParameters.pageSize = response.size;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public createAuthor(addForm: NgForm): void {
    if (addForm.invalid){
      addForm.controls.firstName.markAsTouched();
      return;
    }
    let createdAuthor: Author = addForm.value;
    createdAuthor.firstName= createdAuthor.firstName.trim();
    createdAuthor.secondName= createdAuthor.secondName.trim();
    this.authorService.createAuthor(addForm.value).subscribe(
      (response: Author) => {
        console.log(response);
        this.getAuthorsWithParameters();
        addForm.reset();
        this.notificationService.successSnackBar("Success!");
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        this.notificationService.errorSnackBar((error.message));
        addForm.reset();
      }
    );
    //@ts-ignore
    document.getElementById('close-author-form').click();
  }

  public updateAuthor(editForm: NgForm): void {
    if (editForm.invalid){
      editForm.controls.firstName.markAsTouched();
      return;
    }
    this.editAuthor = editForm.value;
    this.editAuthor.firstName= this.editAuthor.firstName.trim();
    this.editAuthor.secondName= this.editAuthor.secondName.trim();
    this.authorService.updateAuthor(editForm.value).subscribe(
      (response: Author) => {
        console.log(response);
        this.getAuthorsWithParameters();
        this.notificationService.successSnackBar("Success!");
        editForm.resetForm(this.editAuthor);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
    //@ts-ignore
    document.getElementById('close-edit-form').click();
  }

  public deleteAuthor(authorId: number): void {
    this.authorService.deleteAuthor(authorId).subscribe(
      (response: void) => {
        console.log(response);
        this.notificationService.successSnackBar("Success!");
        this.getAuthorsWithParameters();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public filterAuthors(): void {
    this.authorFilterParameters = new AuthorFilterParameters();
    this.authorFilterParameters.name = this.filterForm.value.name;
    this.authorFilterParameters.toRating = this.filterForm.value.toRating;
    this.authorFilterParameters.fromRating = this.filterForm.value.fromRating;
    this.pageSortFilterParameters.pattern = this.authorFilterParameters;
    this.matPaginator.pageIndex = 0;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getAuthorsWithParameters()
  }

  public onOpenModal(mode: string, author: Author): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#createAuthorModal');
    }
    if (mode === 'edit') {
      this.editAuthor = author;
      button.setAttribute('data-target', '#updateAuthorModal');
    }
    if (mode === 'delete') {
      this.deletedAuthor = author;
      button.setAttribute('data-target', '#deleteAuthorModal');
    }
    if (mode === 'detail') {
      this.detailAuthor = author;
      button.setAttribute('data-target', '#detailAuthorModal');
    }
    //@ts-ignore
    container.appendChild(button);
    button.click();
  }

  public onPageChange(event: PageEvent) {
    this.pageSortFilterParameters.pageNumber = event.pageIndex;
    this.pageSortFilterParameters.pageSize = event.pageSize;
    this.getAuthorsWithParameters()
  }

  public sortByColumn(sortBy: string) {
    this.pageSortFilterParameters.sortField = sortBy;
    this.pageSortFilterParameters.reverseForSorting = !this.pageSortFilterParameters.reverseForSorting;
    if (this.pageSortFilterParameters.reverseForSorting) {
      this.pageSortFilterParameters.order = 'ASC'
    } else {
      this.pageSortFilterParameters.order = 'DESC'
    }
    this.getAuthorsWithParameters();
  }

  public resetForm(filterForm: NgForm) {
    filterForm.reset();
    // @ts-ignore
    this.pageSortFilterParameters.pattern = null;
    this.pageSortFilterParameters.pageSize = this.matPaginator.pageSize;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getAuthorsWithParameters();
  }

  public changeElementsPerPage(event: number) {
    this.pageSortFilterParameters.pageNumber = 0;
    this.pageSortFilterParameters.pageSize = event;
    this.matPaginator.pageIndex = 0;
    this.matPaginator.pageSize = event;
    this.getAuthorsWithParameters()
  }
}
