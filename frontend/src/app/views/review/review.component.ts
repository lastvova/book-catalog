import {Component, OnInit, ViewChild} from '@angular/core';
import {Review} from "../../model/Review";
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {NgForm} from "@angular/forms";
import {ReviewService} from "../../service/review.service";
import {ReviewFilterParameters} from "../../model/parameters/ReviewFilterParameters";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {HttpErrorResponse} from "@angular/common/http";
import {Author} from "../../model/Author";

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {

  reviews: Review [] = [];
  public totalRecords?: number;

  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();
  public reviewFilterParameters: ReviewFilterParameters;

  @ViewChild('matPaginator') matPaginator: MatPaginator;
  @ViewChild('filterForm') filterForm: NgForm;

  constructor(private reviewService: ReviewService) {
  }

  ngOnInit(): void {
    this.getReviews();
  }

  public getReviews(): void {
    this.reviewService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Review>) => {
        this.reviews = [];
        this.reviews = response.content;
        this.totalRecords = response.totalElements;
        this.pageSortFilterParameters.pageNumber = response.number;
        this.pageSortFilterParameters.pageSize = response.size;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getReviewsWithParameters(): void {
    this.reviewService.getAllWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords<Review>) => {
        this.reviews = [];
        this.reviews = response.content;
        this.totalRecords = response.totalElements;
        this.pageSortFilterParameters.pageNumber = response.number;
        this.pageSortFilterParameters.pageSize = response.size;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public createAuthor(addForm: NgForm): void {
    //@ts-ignore
    document.getElementById('add-author-form').click();
    this.reviewService.create(addForm.value).subscribe(
      (response: Review) => {
        console.log(response);
        this.getReviewsWithParameters();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
        addForm.reset();
      }
    )
  }

  public filterReviews(): void {
    this.reviewFilterParameters = new ReviewFilterParameters();
    this.reviewFilterParameters.commenterName = this.filterForm.value.commenterName;
    this.reviewFilterParameters.bookName = this.filterForm.value.bookName;
    this.reviewFilterParameters.toRating = this.filterForm.value.toRating;
    this.reviewFilterParameters.fromRating = this.filterForm.value.fromRating;
    this.pageSortFilterParameters.pattern = this.reviewFilterParameters;
    this.matPaginator.pageIndex = 0;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getReviewsWithParameters()
  }

  public onOpenModal(mode: string, author: Author): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#createAuthorModal');
    }
    //@ts-ignore
    container.appendChild(button);
    button.click();
  }

  public onPageChange(event: PageEvent) {
    this.pageSortFilterParameters.pageNumber = event.pageIndex;
    this.pageSortFilterParameters.pageSize = event.pageSize;
    this.getReviewsWithParameters()
  }

  public resetForm(filterForm: NgForm) {
    filterForm.reset();
    // @ts-ignore
    this.pageSortFilterParameters.pattern = null;
    this.pageSortFilterParameters.pageSize = this.matPaginator.pageSize;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getReviewsWithParameters();
  }
}
