import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Book} from "../../model/Book";
import {BookService} from "../../service/book.service";
import {ReviewService} from "../../service/review.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {NotificationService} from "../../service/notification.service";
import {Review} from "../../model/Review";

@Component({
  selector: 'app-book-page',
  templateUrl: './book-page.component.html',
  styleUrls: ['./book-page.component.css']
})
export class BookPageComponent implements OnInit {
  public book = new Book();
  public bookId: any;
  public selectedValue: any;

  constructor(private route: ActivatedRoute, private bookService: BookService,
              private reviewService: ReviewService, private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.bookId = this.route.snapshot.paramMap.get('id');
    this.getBook(this.bookId);
  }

  public getBook(bookId: any) {
    this.bookService.getBook(bookId).subscribe((response: Book) => {
        this.book = response
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
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
    createdReview.comment = createdReview.comment.trim();
    this.reviewService.createReview(reviewForm.value).subscribe(
      (response: any) => {
        console.log(response);
        this.getBook(this.bookId);
        this.notificationService.successSnackBar("Success");
        reviewForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        reviewForm.reset();
      }
    );
    //@ts-ignore
    document.getElementById('close-review-form').click();
  }

  public countStar(star: any) {
    this.selectedValue = star;
    console.log('Value of star', star);
  }

  public formatIsbn(isbn: string): string {
    return isbn.substring(0, 3) + "-" + isbn.substring(3, 4) + "-" + isbn.substring(4, 8) + "-" + isbn.substring(8, 12) + "-" + isbn.substring(12, 13);
  }
}
