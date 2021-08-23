import { Component, OnInit } from '@angular/core';
import {DataHandlerService} from "../../service/data-handler.service";
import {Review} from "../../model/Review";

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {

  reviews: Review [] = [];

  constructor(private dataHandler: DataHandlerService) { }

  ngOnInit(): void {
    this.reviews = this.dataHandler.getReviews();
  }

}
