import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
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
import {ReviewService} from "../../service/review.service";
import {SelectionModel} from "@angular/cdk/collections";
import {NotificationService} from "../../service/notification.service";
import {MatAccordion} from "@angular/material/expansion";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  public books: Book[] = [];
  public authors: Author[] = [];
  public searchedAuthors: Author[] = [];
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

  @ViewChild('matPaginator') matPaginator: MatPaginator;
  @ViewChild('filterForm') filterForm: NgForm;
  @ViewChild('multiSearch') multiAuthorSearch: ElementRef;
  @ViewChild(MatAccordion) accordion: MatAccordion;

  constructor(private bookService: BookService, private authorService: AuthorService,
              private reviewService: ReviewService, private notificationService: NotificationService) {
  }

  ngOnInit() {
    this.getBooks();
  }

  public getAuthors(): void {
    let pageParameters = new PageSortFilterParameters();
    //todo
    pageParameters.pageNumber = 0;
    pageParameters.pageSize = 999999;
    this.authorService.getAuthorsWithParameters(pageParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.authors = response.content;
        this.searchedAuthors = response.content;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getBooks(): void {
    this.bookService.getBooks(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.books = [];
        this.books = response.content;
        this.totalRecords = response.totalElements;
        this.totalPages = response.totalPages;
        this.numberOfRecords = response.number;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getBooksWithParameters(): void {
    this.bookService.getBooksWithParameters(this.pageSortFilterParameters).subscribe(
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
    this.bookService.getBook(bookId).subscribe(
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
      return;
    }
    let createdBook: Book = addForm.value;
    createdBook.name = createdBook.name.trim();
    createdBook.publisher = createdBook.publisher.trim();
    this.bookService.createBook(createdBook).subscribe(
      (response: Book) => {
        console.log(response);
        this.getBooksWithParameters();
        addForm.reset();
        this.notificationService.successSnackBar("Success!");
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        this.notificationService.errorSnackBar((error.message))
        addForm.reset();
      }
    );
    //@ts-ignore
    document.getElementById('close-book-form').click();
  }

  public updateBook(editForm: NgForm): void {
    debugger;
    if (editForm.invalid) {
      editForm.controls.name.markAsDirty();
      editForm.controls.authors.markAsDirty();
      editForm.controls.publisher.markAsDirty();
      editForm.controls.isbn.markAsDirty();
      return;
    }
    if (editForm.untouched) {
      //@ts-ignore
      document.getElementById('close-edit-book-form').click();
      return;
    }
    this.editBook = editForm.value;
    this.editBook.name = this.editBook.name.trim();
    this.editBook.publisher = this.editBook.publisher.trim();
    this.bookService.updateBook(editForm.value).subscribe(
      (response: Book) => {
        console.log(response);
        this.getBooksWithParameters();
        this.notificationService.successSnackBar("Success!");
        editForm.resetForm(this.editBook);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    //@ts-ignore
    document.getElementById('close-edit-book-form').click();
  }

  public deleteBook(bookId: number): void {
    this.bookService.deleteBook(bookId).subscribe(
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
      this.getAuthors()
      button.setAttribute('data-target', '#createBookModal');
    }
    if (mode === 'edit') {
      this.editBook = book;
      this.getAuthors();
      button.setAttribute('data-target', '#updateBookModal');
    }
    if (mode === 'delete') {
      this.deletedBook = book;
      button.setAttribute('data-target', '#deleteBookModal');
    }
    if (mode === 'bulkDelete' && this.selection.hasValue()) {
      button.setAttribute('data-target', '#bulkDeleteBooksModal');
    }
    if (mode === 'detail') {
      this.detailBook = book;
      button.setAttribute('data-target', '#detailBookModal')
    }
    if (mode === 'createReview') {
      this.detailBook = book;
      button.setAttribute('data-target', '#createReviewModal')
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

  public createReview(reviewForm: NgForm): void {
    // @ts-ignore
    document.getElementById('add-review-form').click();
    this.reviewService.createReview(reviewForm.value).subscribe(
      (response: any) => {
        console.log(response);
        this.getBooksWithParameters();
        reviewForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        reviewForm.reset();
      }
    )
  }

  public changeElementsPerPage(event: number) {
    this.pageSortFilterParameters.pageNumber = 0;
    this.pageSortFilterParameters.pageSize = event;
    this.matPaginator.pageIndex = 0;
    this.matPaginator.pageSize = event;
    this.getBooksWithParameters()
  }

  public onInputChange() {
    this.searchedAuthors = this.authors;
    const searchInput = this.multiAuthorSearch.nativeElement.value ?
      this.multiAuthorSearch.nativeElement.value.toLowerCase() : '';
    this.authors = this.searchedAuthors.filter(a => {
      const name: string = a.firstName.toLowerCase();
      return name.indexOf(searchInput) > -1;
    })
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
}
