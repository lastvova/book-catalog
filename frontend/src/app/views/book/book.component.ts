import {Component, OnInit} from '@angular/core';
import {Book} from "../../model/Book";
import {DataHandlerService} from "../../service/data-handler.service";
import {Router} from "@angular/router";
import {BookService} from "../../service/book.service";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  books: Book[] = [];

  constructor(private router: Router, private bookService: BookService) {
  }

  ngOnInit(): void {
    this.bookService.getBooks()
      .subscribe(data => {
        this.books = data;
      });
  }

  // createBook(book: Book): any {
  //   this.bookService.createBook(book)
  //     .subscribe(data => {
  //       alert("user created successfully");
  //     })
  // }

  deleteBook(id: number): void {
    this.bookService.deleteBook(id)
      .subscribe(data => {
        this.books = this.books.filter(b => b.id !== id)
      })
  }
}
