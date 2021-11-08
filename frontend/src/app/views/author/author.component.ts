import {Component, OnInit} from '@angular/core';
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {NgForm} from "@angular/forms";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {AuthorFilterParameters} from "../../model/parameters/AuthorFilterParameters";
import {NotificationService} from "../../service/notification.service";
import {BookService} from "../../service/book.service";
import {Book} from "../../model/Book";
import {BookFilterParameters} from "../../model/parameters/BookFilterParameters";
import {RouterService} from "../../service/router.service";
import {BaseComponent} from "../base/base.component";

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
//TODO try to look at similar places for the remaining views
export class AuthorComponent extends BaseComponent<Author> implements OnInit {

  //TODO these 3 objects should not be created (I might never want to delete an author, but an object has been created anyhow)
  public detailAuthor: Author = new Author();
  public deletedAuthor: Author;
  public topThreeBooks: Book[] = [];

  public authorFilterParameters: AuthorFilterParameters;

  constructor(private authorService: AuthorService, public notificationService: NotificationService,
              public bookService: BookService, public router: RouterService) {
    super(notificationService, router, authorService);
  }

  public onOpenModal(mode: string, author: Author): void {
    const container = document.getElementById('main-container')!;
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#createAuthorModal');
    }
    if (mode === 'edit') {
      super.editEntity = author;
      button.setAttribute('data-target', '#updateAuthorModal');
    }
    if (mode === 'delete') {
      this.deletedAuthor = author;
      button.setAttribute('data-target', '#deleteAuthorModal');
    }
    if (mode === 'bulkDelete' && this.selection.hasValue()) {
      button.setAttribute('data-target', '#bulkDeleteAuthorsModal');
    }
    if (mode === 'detail') {
      this.detailAuthor = author;
      this.getTopThreeBooksOfAuthor(author);
      button.setAttribute('data-target', '#detailAuthorModal');
    }
    container.appendChild(button);
    button.click();
  }

  public getTopThreeBooksOfAuthor(author: Author) {
    let paramsForTopThreeBooks = new PageSortFilterParameters();
    let pattern = new BookFilterParameters();
    paramsForTopThreeBooks.pageSize = 3;
    paramsForTopThreeBooks.pageNumber = 0;
    paramsForTopThreeBooks.order = 'DESC';
    paramsForTopThreeBooks.sortField = 'rating';
    pattern.authorId = author.id;
    paramsForTopThreeBooks.pattern = pattern;
    this.bookService.getAllWithParameters(paramsForTopThreeBooks).subscribe(
      (response: DataWithTotalRecords<Book>) => {
        this.topThreeBooks = response.content;
      })
  }

  manipulateWithEntityFields(createdAuthor: Author): Author {
    createdAuthor.firstName = createdAuthor.firstName.trim();
    if (createdAuthor.secondName != null) {
      createdAuthor.secondName = createdAuthor.secondName.trim();
    }
    return createdAuthor;
  }

  manipulateWithEntityFieldsWhenUpdate(updatedAuthor: Author): Author {
    updatedAuthor.firstName = updatedAuthor.firstName.trim();
    if (updatedAuthor.secondName != null) {
      updatedAuthor.secondName = updatedAuthor.secondName.trim();
    }
    return updatedAuthor;
  }

  getIdsFromSelection(): number[] {
    return this.selection.selected.map(value => value.id);
  }

  isInvalidForm(form: NgForm): boolean {
    if (form.invalid) {
      form.controls.firstName.markAsTouched();
      return true;
    }
    return false;
  }

  getFilterParameters(): any {
    let filter: AuthorFilterParameters = new AuthorFilterParameters()
    filter.name = this.filterForm.value.name;
    filter.toRating = this.filterForm.value.toRating;
    filter.fromRating = this.filterForm.value.fromRating;
    return filter;
  }
}
