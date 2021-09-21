import {Component, OnInit, ViewChild} from '@angular/core';
import {Book} from "../../model/Book";
import {Router} from "@angular/router";
import {BookService} from "../../service/book.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormControl, NgForm} from "@angular/forms";
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {SortingParameters} from "../../model/parameters/SortingParameters";
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {BookFilterParameters} from "../../model/parameters/BookFilterParameters";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  toppings = new FormControl();

  public books: Book[] = [];
  public authors: Author[] = [];
  public detailBook: Book | undefined;
  // @ts-ignore
  public editBook: Book;
  // @ts-ignore: Object is possibly 'null'
  public deletedBook: Book;
  public selectedAuthors?: Author[];

  public totalRecords?: number;
  // @ts-ignore: Object is possibly 'null'
  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();
  // @ts-ignore: Object is possibly 'null'
  public sortParameters: SortingParameters = new SortingParameters();
  // @ts-ignore: Object is possibly 'null'
  public bookFilterParameters: BookFilterParameters;

  // @ts-ignore: Object is possibly 'null'
  @ViewChild('matPaginator') matPaginator: MatPaginator;
  // @ts-ignore: Object is possibly 'null'
  @ViewChild('filterForm') filterForm: NgForm;

  constructor(private router: Router, private bookService: BookService, private authorService: AuthorService) {
  }

  ngOnInit() {
    this.getBooks();
  }

  public getAuthors(): void {
    this.authorService.getAuthors(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.authors = response.content;
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
        this.totalRecords = response.totalElements
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
        this.pageSortFilterParameters.pageNumber = response.number;
        this.pageSortFilterParameters.pageSize = response.size;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
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
    // @ts-ignore
    document.getElementById('add-book-form').click();
    this.bookService.createBook(addForm.value).subscribe(
      (response: Book) => {
        console.log(response);
        this.getBooksWithParameters();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    )
  }

  public updateBook(book: Book): void {
    this.bookService.updateBook(book).subscribe(
      (response: Book) => {
        console.log(response);
        this.getBooksWithParameters();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public deleteBook(bookId: number): void {
    this.bookService.deleteBook(bookId).subscribe(
      (response: void) => {
        console.log(response);
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
    this.bookFilterParameters.authorNameAndSecondName = this.filterForm.value.authorNameAndSecondName;
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
    if (mode === 'detail') {
      this.detailBook = book;
      button.setAttribute('data-target', '#detailBookModal')
    }
    // @ts-ignore
    container.appendChild(button);
    button.click();
  }

  public onPageChange(event: PageEvent) {
    this.pageSortFilterParameters.pageNumber = event.pageIndex;
    this.pageSortFilterParameters.pageSize = event.pageSize;
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
}
