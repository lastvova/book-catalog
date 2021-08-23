import {Component, OnInit} from '@angular/core';
import {DataHandlerService} from "../../service/data-handler.service";
import {Author} from "../../model/Author";

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  authors: Author[] = [];

  constructor(private dataHandler: DataHandlerService) {
  }

  ngOnInit(): void {
    this.authors = this.dataHandler.getAuthors();
  }

}
