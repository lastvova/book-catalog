import {Component, OnInit} from '@angular/core';
import {Book} from "../../model/Book";
import {BookService} from "../../service/book.service";
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {BookFilterParameters} from "../../model/parameters/BookFilterParameters";
import {AuthorService} from "../../service/author.service";
import {Author} from "../../model/Author";
import {AuthorFilterParameters} from "../../model/parameters/AuthorFilterParameters";
import {RouterService} from "../../service/router.service";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {
  public zeroStarBooks: number;
  public oneStarBooks: number;
  public twoStarBooks: number;
  public threeStarBooks: number;
  public fourStarBooks: number;
  public fiveStarBooks: number;
  public topThreeBooks: Book[] = [];
  public topThreeAuthors: Author[] = [];
  public bookFilterParameters: BookFilterParameters;
  public authorFilterParameters: AuthorFilterParameters;
  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();

  constructor(private bookService: BookService, private authorService: AuthorService, private router: RouterService) {
  }

  ngOnInit(): void {
    this.getBooksByRatingZero();
    this.getBooksByRatingOne();
    this.getBooksByRatingTwo();
    this.getBooksByRatingThree();
    this.getBooksByRatingFour();
    this.getBooksByRatingFive();
    this.getTopThreeBooks();
    this.getTopThreeAuthors();

  }

  public getTopThreeBooks(): void {
    this.pageSortFilterParameters.pattern = {};
    this.pageSortFilterParameters.pageSize = 3;
    this.pageSortFilterParameters.pageNumber = 0;
    this.pageSortFilterParameters.order = "DESC";
    this.pageSortFilterParameters.sortField = "rating";
    this.bookService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Book>) => {
        this.topThreeBooks = response.content;
      });
  }

  public getTopThreeAuthors(): void {
    this.pageSortFilterParameters.pattern = {};
    this.pageSortFilterParameters.pageSize = 3;
    this.pageSortFilterParameters.pageNumber = 0;
    this.pageSortFilterParameters.order = "DESC";
    this.pageSortFilterParameters.sortField = "rating";
    this.authorService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Author>) => {
        this.topThreeAuthors = response.content;
      });
  }

  public getBooksByRatingZero(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating = 1;
    this.bookFilterParameters.fromRating = 0;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Book>) => {
        this.zeroStarBooks = response.totalElements
      })
  }

  public getBooksByRatingOne(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating = 2;
    this.bookFilterParameters.fromRating = 1;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Book>) => {
        this.oneStarBooks = response.totalElements
      })
  }

  public getBooksByRatingTwo(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating = 3;
    this.bookFilterParameters.fromRating = 2;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Book>) => {
        this.twoStarBooks = response.totalElements
      })
  }

  public getBooksByRatingThree(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating = 4;
    this.bookFilterParameters.fromRating = 3;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Book>) => {
        this.threeStarBooks = response.totalElements
      })
  }

  public getBooksByRatingFour(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.toRating = 5;
    this.bookFilterParameters.fromRating = 4;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Book>) => {
        this.fourStarBooks = response.totalElements
      })
  }

  public getBooksByRatingFive(): void {
    this.bookFilterParameters = new BookFilterParameters();
    this.bookFilterParameters.fromRating = 5;
    this.pageSortFilterParameters.pattern = this.bookFilterParameters;
    this.bookService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Book>) => {
        this.fiveStarBooks = response.totalElements
      })
  }
}
