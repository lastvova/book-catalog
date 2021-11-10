import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Book} from "../../model/Book";
import {BookService} from "../../service/book.service";
import {ReviewService} from "../../service/review.service";
import {NgForm} from "@angular/forms";
import {NotificationService} from "../../service/notification.service";
import {Review} from "../../model/Review";
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {ReviewFilterParameters} from "../../model/parameters/ReviewFilterParameters";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {RouterService} from "../../service/router.service";

@Component({
  selector: 'app-book-page',
  templateUrl: './book-page.component.html',
  styleUrls: ['./book-page.component.css']
})
export class BookPageComponent implements OnInit {
  public book = new Book();
  public reviews: Review[] = [];
  public bookId: any;
  public previousUrl: string;
  public selectedStars: any;
  public numberOfRecords: number;
  public totalRecords: number;
  public totalPages: number;
  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();
  public reviewFilterParameters: ReviewFilterParameters = new ReviewFilterParameters()

  @ViewChild('matPaginator') matPaginator: MatPaginator;

  constructor(private route: ActivatedRoute,
              private router: RouterService,
              private bookService: BookService,
              private reviewService: ReviewService,
              private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.previousUrl = this.router.getPreviousUrl();
    this.bookId = this.route.snapshot.paramMap.get('id');
    this.getReviews(this.bookId);
    this.getBook(this.bookId);
  }

  public getBook(bookId: any) {
    this.bookService.getById(bookId).subscribe((response: Book) => {
      this.book = response
    });
  }

  public getReviews(bookId: any) {
    this.reviewFilterParameters.bookId = bookId;
    this.pageSortFilterParameters.pattern = this.reviewFilterParameters;
    this.reviewService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Review>) => {
        this.reviews = response.content;
        this.totalRecords = response.totalElements;
        this.totalPages = response.totalPages;
        this.numberOfRecords = response.number;
        this.pageSortFilterParameters.pageNumber = response.number;
        this.pageSortFilterParameters.pageSize = response.size;
      });
  }

  public createReview(reviewForm: NgForm): void {
    if (reviewForm.invalid) {
      Object.keys(reviewForm.form.controls).forEach(key => {
        reviewForm.form.controls[key].markAsTouched()
      });
      return;
    }
    let createdReview: Review = reviewForm.value;
    createdReview.commenterName = createdReview.commenterName.trim();
    if (createdReview.comment != null) {
      createdReview.comment = createdReview.comment.trim();
    }
    createdReview.book = this.book;
    this.reviewService.create(createdReview).subscribe(
      (response: any) => {
        this.getBook(this.bookId);
        this.getReviews(this.bookId)
        this.notificationService.successSnackBar("Success");
        reviewForm.reset();
      });
    document.getElementById('close-review-form')!.click();
  }

  public countStar(star: any) {
    this.selectedStars = star;
  }

  public formatIsbn(isbn: string): string {
    return isbn.substring(0, 3) + "-" + isbn.substring(3, 4) + "-" + isbn.substring(4, 8) + "-" + isbn.substring(8, 12) + "-" + isbn.substring(12, 13);
  }

  public onPageChange(event: PageEvent) {
    this.pageSortFilterParameters.pageNumber = event.pageIndex;
    this.pageSortFilterParameters.pageSize = event.pageSize;
    this.getReviews(this.bookId);
  }

  public changeElementsPerPage(event: number) {
    this.pageSortFilterParameters.pageNumber = 0;
    this.pageSortFilterParameters.pageSize = event;
    this.matPaginator.pageIndex = 0;
    this.matPaginator.pageSize = event;
    this.getReviews(this.bookId);
  }

  public getInfoAboutRecords(): string {
    if (this.totalRecords > 0) {
      let currentRecords = 1 + this.pageSortFilterParameters.pageSize * this.numberOfRecords;
      let currentRecordsTo = this.totalRecords <= ((1 + this.numberOfRecords) * this.pageSortFilterParameters.pageSize) ? this.totalRecords
        : ((1 + this.numberOfRecords) * this.pageSortFilterParameters.pageSize);
      return "Showing " + currentRecords + " to " + currentRecordsTo + " of " + this.totalRecords;
    }
    return "Showing 0";
  }

  public toggleClassForComment(reviewId: string) {
    let element = document.getElementById(reviewId)!;
    element.classList.toggle("truncated")
  }

  public backInBrowserHistory(): void {
    this.router.goToPrevious();
  }
}
