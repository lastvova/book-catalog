import {Component, OnInit, ViewChild} from '@angular/core';
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {SortingParameters} from "../../model/parameters/SortingParameters";
import {FilterParameters} from "../../model/parameters/FilterParameters";
import {FilterOperator2LabelMapping, FilterOperatorEnum} from "../../enum/FilterOperatorEnum";
import {AuthorFieldType, AuthorFieldType2LabelMapping} from "../../enum/AuthorFieldType";
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

  public FieldType2LabelMapping = AuthorFieldType2LabelMapping;
  public fieldTypes = Object.values(AuthorFieldType);

  public FilterOperator2LabelMapping = FilterOperator2LabelMapping;
  public filterOperators = Object.values(FilterOperatorEnum);

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

  public createAuthor(addForm: NgForm): void {
    //@ts-ignore
    document.getElementById('add-author-form').click();
    this.authorService.createAuthor(addForm.value).subscribe(
      (response: Author) => {
        console.log(response);
        this.getAuthors();
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
        this.getAuthors();
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
        this.getAuthors();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public filterAuthors(filterForm: NgForm): void {
    this.authorFilterParameters = new AuthorFilterParameters();
    this.authorFilterParameters.firstName = this.filterForm.value.firstName;
    this.authorFilterParameters.secondName = this.filterForm.value.secondName;
    this.authorFilterParameters.toRating = this.filterForm.value.toRating;
    this.authorFilterParameters.fromRating = this.filterForm.value.fromRating;
    this.pageSortFilterParameters.pattern = this.authorFilterParameters;
    this.matPaginator.pageIndex = 0;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getAuthors()
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
    this.getAuthors()
  }

  public sortByColumn(sortBy: string) {
    this.sortParameters.sortBy = sortBy;
    this.sortParameters.reverse = !this.sortParameters.reverse;
    if (this.sortParameters.reverse) {
      this.sortParameters.sortOrder = 'ASC'
    } else {
      this.sortParameters.sortOrder = 'DESC'
    }
    this.getAuthors();
  }

  public resetForm(filterForm: NgForm) {
    // this.sortParameters.;
    filterForm.reset();
    // @ts-ignore
    this.filterParameters.filterValue = null;
    // @ts-ignore
    // this.filterParameters.filterBy = null;
    this.pageSortFilterParameters.pageSize = this.matPaginator.pageSize;
    this.pageSortFilterParameters.pageNumber = 0;
    this.getAuthors();
  }
}
