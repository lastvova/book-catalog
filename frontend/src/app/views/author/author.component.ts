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
import {SelectionModel} from "@angular/cdk/collections";
import {BookService} from "../../service/book.service";
import {Book} from "../../model/Book";
import {BookFilterParameters} from "../../model/parameters/BookFilterParameters";

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
//TODO try to look at similar places for the remaining views
export class AuthorComponent implements OnInit {

  public authors: Author[] = [];
  //TODO these 3 objects should not be created (I might never want to delete an author, but an object has been created anyhow)
  public detailAuthor: Author = new Author();
  public editAuthor: Author = new Author();
  public deletedAuthor: Author = new Author();
  public topThreeBooks: Book[] = [];
  public numberOfRecords: number;
  public totalRecords: number;
  public totalPages: number;
  public selection = new SelectionModel<Author>(true, []);

  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();
  public authorFilterParameters: AuthorFilterParameters;

  @ViewChild('matPaginator') matPaginator: MatPaginator;
  @ViewChild('filterForm') filterForm: NgForm;
  @ViewChild(MatAccordion) accordion: MatAccordion;

  constructor(private authorService: AuthorService, private notificationService: NotificationService,
              private bookService: BookService) {
  }

  ngOnInit(): void {
    this.getAuthors();
  }

  //TODO is this method needed? it seems getAuthorsWithParameters could be used instead
  public getAuthors(): void {
    this.authorService.getAuthors(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
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
        //TODO why is this needed here?
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
    );
    this.selection.clear();
  }

  public createAuthor(addForm: NgForm): void {
    if (addForm.invalid) {
      addForm.controls.firstName.markAsTouched();
      return;
    }
    let createdAuthor: Author = addForm.value;
    createdAuthor.firstName = createdAuthor.firstName.trim();
    if (createdAuthor.secondName != null) {
      createdAuthor.secondName = createdAuthor.secondName.trim();
    }
    this.authorService.createAuthor(createdAuthor).subscribe(
      (response: Author) => {
        //TODO do not keep smth like this in the master branch, however feel free to use it in the dev branch
        console.log(response);
        //TODO avoid this call
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
    //TODO avoid ts-ignore
    //@ts-ignore
    document.getElementById('close-author-form').click();
  }

  public updateAuthor(editForm: NgForm): void {
    if (editForm.invalid) {
      editForm.controls.firstName.markAsTouched();
      return;
    }
    this.editAuthor = editForm.value;
    this.editAuthor.firstName = this.editAuthor.firstName.trim();
    //TODO if (createdAuthor.secondName != null) {... consistency?
    this.editAuthor.secondName = this.editAuthor.secondName.trim();
    //TODO this.editAuthor could be passed, no need for that class variable if this the case
    this.authorService.updateAuthor(editForm.value).subscribe(
      (response: Author) => {
        console.log(response);
        //TODO avoid this call
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
        //TODO avoid this call
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
    if (mode === 'bulkDelete' && this.selection.hasValue()) {
      button.setAttribute('data-target', '#bulkDeleteAuthorsModal');
    }
    if (mode === 'detail') {
      this.detailAuthor = author;
      this.getTopThreeBooksOfAuthor(author);
      button.setAttribute('data-target', '#detailAuthorModal');
    }
    //@ts-ignore
    container.appendChild(button);
    button.click();
  }

  public onPageChange(event: PageEvent) {
    this.pageSortFilterParameters.pageNumber = event.pageIndex;
    this.pageSortFilterParameters.pageSize = event.pageSize;
    this.selection.clear();
    this.getAuthorsWithParameters();
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
    this.getAuthorsWithParameters();
  }

  public resetForm(filterForm: NgForm) {
    filterForm.reset();
    // @ts-ignore
    //TODO why null? can't it be {} like defined in the class originally?
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

  // Whether the number of selected elements matches the total number of rows.
  public isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.authors.length;
    return numSelected === numRows;
  }

  // Selects all rows if they are not all selected; otherwise clear selection.
  public masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.authors.forEach(row => this.selection.select(row));
  }

  public bulkDelete() {
    if (this.selection.hasValue()) {
      let authorsIds = this.selection.selected.map(value => value.id);
      this.authorService.bulkDelete(authorsIds).subscribe(
        (response: void) => {
          console.log(response);
          this.notificationService.successSnackBar("Success!");
          //TODO avoid this call
          this.getAuthorsWithParameters();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      )
    }
  }

  public getTopThreeBooksOfAuthor(author: Author) {
    let paramsForTopThreeBooks = new PageSortFilterParameters();
    let pattern = new BookFilterParameters();
    paramsForTopThreeBooks.pageSize = 3;
    paramsForTopThreeBooks.pageNumber = 0;
    paramsForTopThreeBooks.order = 'DESC';
    paramsForTopThreeBooks.sortField = 'rating';
    pattern.authorId = author.id;
    paramsForTopThreeBooks.pattern = pattern;
    this.bookService.getBooksWithParameters(paramsForTopThreeBooks).subscribe(
      (response: DataWithTotalRecords) => {
        console.log(response);
        this.topThreeBooks = response.content;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }
  //TODO out of place method
  public formatIsbn(isbn: string): string {
    return isbn.substring(0, 3) + "-" + isbn.substring(3, 4) + "-" + isbn.substring(4, 8) + "-" + isbn.substring(8, 12) + "-" + isbn.substring(12, 13);
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
}
