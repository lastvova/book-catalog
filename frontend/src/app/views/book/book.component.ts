import {Component, OnInit, ViewChild} from '@angular/core';
import {Book} from "../../model/Book";
import {Router} from "@angular/router";
import {BookService} from "../../service/book.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormControl, NgForm} from "@angular/forms";
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {FilterParameters} from "../../model/parameters/FilterParameters";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {FieldTypeEnum, FieldType2LabelMapping} from "../../enum/FieldTypeEnum";
import {FilterOperator2LabelMapping, FilterOperatorEnum} from "../../enum/FilterOperatorEnum";
import {SortingParameters} from "../../model/parameters/SortingParameters";
import {PaginationParameters} from "../../model/parameters/PaginationParameters";

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
  public pageParameters: PaginationParameters = new PaginationParameters();
  // @ts-ignore: Object is possibly 'null'
  public sortParameters: SortingParameters = new SortingParameters();
  // @ts-ignore: Object is possibly 'null'
  public filterParameters: FilterParameters = new FilterParameters();

  public FieldType2LabelMapping = FieldType2LabelMapping;
  public fieldTypes = Object.values(FieldTypeEnum);

  public FilterOperator2LabelMapping = FilterOperator2LabelMapping;
  public filterOperators = Object.values(FilterOperatorEnum);

  // @ts-ignore: Object is possibly 'null'
  @ViewChild('matPaginator') matPaginator: MatPaginator;

  constructor(private router: Router, private bookService: BookService, private authorService: AuthorService) {
  }

  ngOnInit() {
    this.getBooks();
  }

  public getAuthors(): void {
    this.authorService.getAuthors().subscribe(
      (response: DataWithTotalRecords) => {
        this.authors = response.content;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getBooks(): void {
    this.bookService.getBooks(this.sortParameters, this.pageParameters, this.filterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.books = [];
        this.books = response.content;
        this.totalRecords = response.totalElements;
        this.pageParameters.currentPage = response.number;
        this.pageParameters.recordsPerPage = response.size;
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
        this.getBooks();
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
        this.getBooks();
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
        this.getBooks();
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
        this.getBooks();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public filterBooks(filter: FilterParameters): void {
    this.filterParameters.filterBy = filter.filterBy;
    this.filterParameters.filterValue = filter.filterValue;
    this.matPaginator.pageIndex=0;
    this.pageParameters.currentPage = 0;
    this.getBooks()
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
    this.pageParameters.currentPage = event.pageIndex;
    this.pageParameters.recordsPerPage = event.pageSize;
    this.getBooks()
  }

  public sortByColumn(sortBy: string) {
    this.sortParameters.sortBy = sortBy;
    this.sortParameters.reverse = !this.sortParameters.reverse;
    if (this.sortParameters.reverse) {
      this.sortParameters.sortOrder = 'ASC'
    } else {
      this.sortParameters.sortOrder = 'DESC'
    }
    this.getBooks();
  }

}
