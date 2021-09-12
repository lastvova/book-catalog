import {Component, OnInit} from '@angular/core';
import {Book} from "../../model/Book";
import {Router} from "@angular/router";
import {BookService} from "../../service/book.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormControl, NgForm} from "@angular/forms";
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {FilterParameters} from "../../model/FilterParameters";

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

  constructor(private router: Router, private bookService: BookService, private authorService: AuthorService) {
  }

  ngOnInit() {
    this.getBooks();
  }

  public getAuthors(): void {
    this.authorService.getAuthors().subscribe(
      (response: Author[]) => {
        this.authors = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getBooks(): void {
    this.bookService.getBooks().subscribe(
      (response: Book[]) => {
        this.books = response;
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
      (response: Book[])=>{
        this.books = response;
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
}
