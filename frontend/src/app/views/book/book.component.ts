import {Component, OnInit, ViewChild} from '@angular/core';
import {Book} from "../../model/Book";
import {BookService} from "../../service/book.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {BookFilterParameters} from "../../model/parameters/BookFilterParameters";
import {SelectionModel} from "@angular/cdk/collections";
import {NotificationService} from "../../service/notification.service";
import {MatAccordion} from "@angular/material/expansion";
import {RouterService} from "../../service/router.service";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  public books: Book[] = [];
  public authors: Author[] = [];
  public detailBook: Book = new Book();
  public editBook: Book = new Book();
  public deletedBook: Book = new Book();
  public currentYear: number = new Date().getFullYear();
  public numberOfRecords: number;
  public totalRecords: number;
  public totalPages: number;
  public selection = new SelectionModel<Book>(true, []);

  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();
  public bookFilterParameters: BookFilterParameters;

  selectedAuthors: Author[] = [];
  dropdownSettings = {};
  requiredAuthors: boolean = false;

  @ViewChild('matPaginator') matPaginator: MatPaginator;
  @ViewChild('filterForm') filterForm: NgForm;
  @ViewChild(MatAccordion) accordion: MatAccordion;


  constructor(private bookService: BookService, private authorService: AuthorService,
              private notificationService: NotificationService, private router: RouterService) {
  }

  ngOnInit() {
    this.getBooksWithParameters();
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'fullName',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 5,
      allowSearchFilter: true
    }
  }

  public getAuthors(): void {
    let pageParameters = new PageSortFilterParameters();
    //todo
    pageParameters.pageNumber = 0;
    pageParameters.pageSize = 999999;
    this.authorService.getAllWithParameters(pageParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.authors = this.setFullNameForAuthors(response.content);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }

  public getBooksWithParameters(): void {
    this.bookService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.books = [];
        this.books = response.content;
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

  public getBook(bookId: number): void {
    this.bookService.getById(bookId).subscribe(
      (response: Book) => {
        console.log(response);
        this.getBooksWithParameters();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      },
    )
  }

  public createBook(addForm: NgForm): void {
    if (addForm.invalid) {
      Object.keys(addForm.form.controls).forEach(key => {
        addForm.form.controls[key].markAsTouched()
      })
      this.requiredAuthors = true;
      return;
    }
    let createdBook: Book = addForm.value;
    createdBook.name = createdBook.name.trim();
    createdBook.publisher = createdBook.publisher.trim();
    this.bookService.create(createdBook).subscribe(
      (response: Book) => {
        console.log(response);
        this.getBooksWithParameters();
        addForm.reset();
        this.notificationService.successSnackBar("Success!");
      },
      (error: HttpErrorResponse) => {
        // alert(error.message);
        this.notificationService.errorSnackBar((error.message))
        addForm.reset();
      }
    );
    this.requiredAuthors = false;
    //@ts-ignore
    document.getElementById('close-book-form').click();
  }

  public updateBook(editForm: NgForm): void {
    if (editForm.invalid) {
      Object.keys(editForm.form.controls).forEach(key => {
        editForm.form.controls[key].markAsTouched()
      })
      this.requiredAuthors = true;
      return;
    }
    // if (editForm.untouched && this.selectedAuthors.length === this.editBook.authors.length) {
    //   //@ts-ignore
    //   document.getElementById('close-edit-book-form').click();
    //   return;
    // }

    this.editBook = editForm.value;
    this.editBook.name = this.editBook.name.trim();
    this.editBook.publisher = this.editBook.publisher.trim();
    this.editBook.authors = this.authors.filter(author => this.editBook.authors.map(authorsId => authorsId.id).includes(author.id));
    this.bookService.update(this.editBook).subscribe(
      (response: Book) => {
        console.log(response);
        this.getBooksWithParameters();
        this.notificationService.successSnackBar("Success!");
        editForm.resetForm(this.editBook);
      },
      (error: HttpErrorResponse) => {
        // alert(error.message);
      }
    );
    this.requiredAuthors = false;
    //@ts-ignore
    document.getElementById('close-edit-book-form').click();
  }

  public deleteBook(bookId: number): void {
    this.bookService.delete(bookId).subscribe(
      (response: void) => {
        console.log(response);
        this.notificationService.successSnackBar("Success!");
        this.getBooksWithParameters();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public filterBooks(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.name = this.filterForm.value.name;
    this.bookFilterParameters.isbn = this.filterForm.value.isbn;
    this.bookFilterParameters.publisher = this.filterForm.value.publisher;
    this.bookFilterParameters.toRating = this.filterForm.value.toRating;
    this.bookFilterParameters.fromRating = this.filterForm.value.fromRating;
    this.bookFilterParameters.yearPublisher = this.filterForm.value.yearPublisher;
    this.bookFilterParameters.searchingName = this.filterForm.value.searchingName;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.matPaginator.pageIndex = 0;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getBooksWithParameters()
  }

  public onOpenModal(mode: string, book: Book): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      this.getAuthors();

      this.selectedAuthors = [];
      button.setAttribute('data-target', '#createBookModal');
    }
    if (mode === 'edit') {
      this.getAuthors();
      this.editBook = book;
      this.selectedAuthors = this.setFullNameForAuthors(this.editBook.authors);
      button.setAttribute('data-target', '#updateBookModal');
    }
    if (mode === 'delete') {
      this.deletedBook = book;
      button.setAttribute('data-target', '#deleteBookModal');
    }
    if (mode === 'bulkDelete' && this.selection.hasValue()) {
      button.setAttribute('data-target', '#bulkDeleteBooksModal');
    }
    // @ts-ignore
    container.appendChild(button);
    button.click();
  }

  public onPageChange(event: PageEvent) {
    this.pageSortFilterParameters.pageNumber = event.pageIndex;
    this.pageSortFilterParameters.pageSize = event.pageSize;
    this.selection.clear();
    this.getBooksWithParameters()
  }

  public sortByColumn(sortBy: string) {
    this.pageSortFilterParameters.sortField = sortBy;
    this.pageSortFilterParameters.reverseForSorting = !this.pageSortFilterParameters.reverseForSorting;
    if (this.pageSortFilterParameters.reverseForSorting) {
      this.pageSortFilterParameters.order = 'ASC'
    } else {
      this.pageSortFilterParameters.order = 'DESC'
    }
    this.selection.clear();
    this.getBooksWithParameters();
  }

  public resetForm(filterForm: NgForm) {
    filterForm.reset();
    // @ts-ignore
    this.pageSortFilterParameters.pattern = null;
    this.pageSortFilterParameters.pageSize = this.matPaginator.pageSize;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getBooksWithParameters();
  }

  public changeElementsPerPage(event: number) {
    this.pageSortFilterParameters.pageNumber = 0;
    this.pageSortFilterParameters.pageSize = event;
    this.matPaginator.pageIndex = 0;
    this.matPaginator.pageSize = event;
    this.getBooksWithParameters()
  }

  public isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.books.length;
    return numSelected === numRows;
  }

  // Selects all rows if they are not all selected; otherwise clear selection.
  public masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.books.forEach(row => this.selection.select(row));
  }

  public bulkDelete() {
    if (this.selection.hasValue()) {
      let authorsIds = this.selection.selected.map(value => value.id);
      this.bookService.bulkDelete(authorsIds).subscribe(
        (response: void) => {
          console.log(response);
          this.notificationService.successSnackBar("Success!");
          this.getBooksWithParameters();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      )
    }
  }

  public onItemSelect(item: any) {
    this.selectedAuthors.push(item)
  }

  public onSelectAll(items: any) {
    this.selectedAuthors.push(items);
  }

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

  private setFullNameForAuthors(authors: Author[]): Author[] {
    authors.forEach(author => author.fullName = author.firstName + " " + (author.secondName === null ? "" : author.secondName));
    return authors;
  }
}
