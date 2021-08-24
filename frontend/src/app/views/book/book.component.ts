import {Component, OnInit} from '@angular/core';
import {Book} from "../../model/Book";
import {DataHandlerService} from "../../service/data-handler.service";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  books: Book[] = [];

  constructor(private dataHandler: DataHandlerService) {
  }

  ngOnInit(): void {
    this.books = this.dataHandler.getBooks();
  }
}
