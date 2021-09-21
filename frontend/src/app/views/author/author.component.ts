import {Component, OnInit, ViewChild} from '@angular/core';
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {SortingParameters} from "../../model/parameters/SortingParameters";
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {AuthorFilterParameters} from "../../model/parameters/AuthorFilterParameters";

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  public authors: Author[] = [];
  public detailAuthor: Author | undefined;
  //@ts-ignore
  public editAuthor: Author;
  //@ts-ignore
  public deletedAuthor: Author;
  public totalRecords?: number;

  // @ts-ignore: Object is possibly 'null'
  public pageSortFilterParameters: PageSortFilterParameters = new PageSortFilterParameters();
  // @ts-ignore: Object is possibly 'null'
  public sortParameters: SortingParameters = new SortingParameters();
  // @ts-ignore: Object is possibly 'null'
  public authorFilterParameters: AuthorFilterParameters;

  // @ts-ignore: Object is possibly 'null'
  @ViewChild('matPaginator') matPaginator: MatPaginator;
  // @ts-ignore: Object is possibly 'null'
  @ViewChild('filterForm') filterForm: NgForm;

  constructor(private authorService: AuthorService) {
  }

  ngOnInit(): void {
    this.getAuthors();
  }

  public getAuthors(): void {
    this.authorService.getAuthors(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.authors = [];
        this.authors = response.content;
        this.totalRecords = response.totalElements;
        this.pageSortFilterParameters.pageNumber = response.number;
        this.pageSortFilterParameters.pageSize = response.size;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public getAuthorsWithParameters(): void {
    this.authorService.getAuthorsWithParameters(this.pageSortFilterParameters).subscribe(
      (response: DataWithTotalRecords) => {
        this.authors = [];
        this.authors = response.content;
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
    this.authorService.createAuthor(addForm.value).subscribe(
      (response: Author) => {
        console.log(response);
        this.getAuthorsWithParameters();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message)
        addForm.reset();
      }
    )
  }

  public updateAuthor(author: Author): void {
    this.authorService.updateAuthor(author).subscribe(
      (response: Author) => {
        console.log(response);
        this.getAuthorsWithParameters();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public deleteAuthor(authorId: number): void {
    this.authorService.deleteAuthor(authorId).subscribe(
      (response: void) => {
        console.log(response);
        this.getAuthorsWithParameters();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public filterAuthors(): void {
    this.authorFilterParameters = new AuthorFilterParameters();
    this.authorFilterParameters.firstName = this.filterForm.value.firstName;
    this.authorFilterParameters.secondName = this.filterForm.value.secondName;
    this.authorFilterParameters.toRating = this.filterForm.value.toRating;
    this.authorFilterParameters.fromRating = this.filterForm.value.fromRating;
    this.pageSortFilterParameters.pattern = this.authorFilterParameters;
    this.matPaginator.pageIndex = 0;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getAuthorsWithParameters()
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
    if (mode === 'edit') {
      this.editAuthor = author;
      button.setAttribute('data-target', '#updateAuthorModal');
    }
    if (mode === 'delete') {
      this.deletedAuthor = author;
      button.setAttribute('data-target', '#deleteAuthorModal');
    }
    if (mode === 'detail') {
      this.detailAuthor = author;
      button.setAttribute('data-target', '#detailAuthorModal');
    }
    //@ts-ignore
    container.appendChild(button);
    button.click();
  }

  public onPageChange(event: PageEvent) {
    this.pageSortFilterParameters.pageNumber = event.pageIndex;
    this.pageSortFilterParameters.pageSize = event.pageSize;
    this.getAuthorsWithParameters()
  }

  public sortByColumn(sortBy: string) {
    this.pageSortFilterParameters.sortField = sortBy;
    this.pageSortFilterParameters.reverseForSorting = !this.pageSortFilterParameters.reverseForSorting;
    if (this.pageSortFilterParameters.reverseForSorting) {
      this.pageSortFilterParameters.order = 'ASC'
    } else {
      this.pageSortFilterParameters.order = 'DESC'
    }
    this.getAuthorsWithParameters();
  }

  public resetForm(filterForm: NgForm) {
    filterForm.reset();
    // @ts-ignore
    this.pageSortFilterParameters.pattern = null;
    this.pageSortFilterParameters.pageSize = this.matPaginator.pageSize;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getAuthorsWithParameters();
  }
}
