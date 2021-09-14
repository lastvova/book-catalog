import {Component, OnInit} from '@angular/core';
import {Book} from "../../model/Book";
import {Router} from "@angular/router";
import {BookService} from "../../service/book.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormControl, NgForm} from "@angular/forms";
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {FilterParameters} from "../../model/parameters/FilterParameters";
import {PageEvent} from "@angular/material/paginator";
import {DataWithTotalRecords} from "../../model/DataWithTotalRecords";
import {FieldTypeEnum, FieldType2LabelMapping} from "../../enum/FieldTypeEnum";
import {FilterOperator2LabelMapping, FilterOperatorEnum} from "../../enum/FilterOperatorEnum";

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
  public filterParameters?: FilterParameters;
  public selectedAuthors?: Author[];
  public totalRecords?: number;

  public FieldType2LabelMapping = FieldType2LabelMapping;
  public fieldTypes = Object.values(FieldTypeEnum);

  public FilterOperator2LabelMapping = FilterOperator2LabelMapping;
  public filterOperators = Object.values(FilterOperatorEnum);

  constructor(private router: Router, private bookService: BookService, private authorService: AuthorService) {
  }

  ngOnInit() {
    this.getBooks();
  }

  public getAuthors(): void {
    this.authorService.getAuthors().subscribe(
      (response: DataWithTotalRecords) => {
        this.authors = response.data;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getBooks(): void {
    this.bookService.getBooks().subscribe(
      (response: DataWithTotalRecords) => {
        this.books = response.data;
        this.totalRecords = response.totalRecords;
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
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
    this.bookService.filterBooks(filter).subscribe(
      (response: DataWithTotalRecords) => {
        this.books = [];
        this.books = response.data;
        this.totalRecords = response.totalRecords;
      },
      (error: HttpErrorResponse) =>{
        alert(error.message);
      }
    )
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
    this.bookService.getBooksWithPagination(event.pageIndex+1, event.pageSize).subscribe(
      (response: DataWithTotalRecords) => {
        this.books = response.data;
        this.totalRecords = response.totalRecords;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

}
