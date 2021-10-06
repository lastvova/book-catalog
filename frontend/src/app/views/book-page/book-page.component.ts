import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Book} from "../../model/Book";
import {Observable} from 'rxjs';
import {BookService} from "../../service/book.service";
import {ReviewService} from "../../service/review.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-book-page',
  templateUrl: './book-page.component.html',
  styleUrls: ['./book-page.component.css']
})
export class BookPageComponent implements OnInit {
  public book = new Book();
  public bookId: any;

  constructor(private route: ActivatedRoute, private bookService: BookService,
              private reviewService: ReviewService) {
  }

  ngOnInit(): void {
    this.bookId = this.route.snapshot.paramMap.get('id');
    this.bookService.getBook(this.bookId).subscribe((response: Book) => {
        this.book = response
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
      });
  }
}
