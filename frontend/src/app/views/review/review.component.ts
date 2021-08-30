import {Component, OnInit} from '@angular/core';
import {Review} from "../../model/Review";

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {

  reviews: Review [] = [];

  constructor() {
  }

  ngOnInit(): void {
  }

}
