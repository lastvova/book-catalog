import {Component, OnInit} from '@angular/core';
import {Book} from "../../model/Book";
import {BookService} from "../../service/book.service";
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {HttpErrorResponse} from "@angular/common/http";
import {BookFilterParameters} from "../../model/parameters/BookFilterParameters";
import {AuthorService} from "../../service/author.service";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {
  public books: Book[] = [];
  public zeroStarBooks: number;
  public oneStarBooks: number;
  public twoStarBooks: number;
  public threeStarBooks: number;
  public fourStarBooks: number;
  public fiveStarBooks: number;
  public topThreeBooks: Book[] = [];
  public bookFilterParameters: BookFilterParameters;
  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();

  constructor(private bookService: BookService, private authorService: AuthorService) {
  }

  ngOnInit(): void {
    this.getBooksByRatingZero();
    this.getBooksByRatingOne();
    this.getBooksByRatingTwo();
    this.getBooksByRatingThree();
    this.getBooksByRatingFour();
    this.getBooksByRatingFive();
    this.getTopThreeBooks();

  }

  public getTopThreeBooks(): void {
    this.pageSortFilterParameters.pattern = {};
    this.pageSortFilterParameters.pageSize = 3;
    this.pageSortFilterParameters.pageNumber = 0;
    this.pageSortFilterParameters.order = "DESC";
    this.pageSortFilterParameters.sortField = "rating";
    this.bookService.getBooksWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.topThreeBooks = response.content;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }

  public getBooksByRatingZero(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating = 1;
    this.bookFilterParameters.fromRating = 0;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getBooksWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.zeroStarBooks = response.totalElements
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getBooksByRatingOne(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating = 2;
    this.bookFilterParameters.fromRating = 1;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getBooksWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.oneStarBooks = response.totalElements
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getBooksByRatingTwo(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating = 3;
    this.bookFilterParameters.fromRating = 2;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getBooksWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.twoStarBooks = response.totalElements
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getBooksByRatingThree(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating = 4;
    this.bookFilterParameters.fromRating = 3;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getBooksWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.threeStarBooks = response.totalElements
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getBooksByRatingFour(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating = 5;
    this.bookFilterParameters.fromRating = 4;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getBooksWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.fourStarBooks = response.totalElements
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getBooksByRatingFive(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating;
    this.bookFilterParameters.fromRating = 5;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getBooksWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.fiveStarBooks = response.totalElements
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }
}
