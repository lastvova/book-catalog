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

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  public books: Book[] = [];
  public authors: Author[] = [];
  public searchedAuthors: Author[] = [];
  public detailBook: Book;
  public editBook: Book;
  public deletedBook: Book;
  public selectedAuthors: Author[];
  public currentYear: number = new Date().getFullYear();
  public totalRecords: number;

  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();
  public bookFilterParameters: BookFilterParameters;

  @ViewChild('matPaginator') matPaginator: MatPaginator;
  @ViewChild('filterForm') filterForm: NgForm;
  @ViewChild('multiSearch') multiAuthorSearch: ElementRef;

  constructor(private bookService: BookService, private authorService: AuthorService,
              private reviewService: ReviewService) {
  }

  ngOnInit() {
    this.getBooks();
  }

  public getAuthors(): void {
    this.authorService.getAuthors(this.pageSortFilterParameters).subscribe(
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

  public onInputChange() {
    this.searchedAuthors = this.authors;
    const searchInput = this.multiAuthorSearch.nativeElement.value ?
      this.multiAuthorSearch.nativeElement.value.toLowerCase() : '';
    this.authors = this.searchedAuthors.filter(a => {
      const name: string = a.firstName.toLowerCase();
      return name.indexOf(searchInput) > -1;
    })
  }

  public reformatIsbn(isbn: any): string {
    debugger;
    let result: string = isbn.toString().substring(0, 3);
    return result;
  }
}
