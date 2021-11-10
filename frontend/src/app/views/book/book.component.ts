import {Component, OnInit} from '@angular/core';
import {Book} from "../../model/Book";
import {BookService} from "../../service/book.service";
import {NgForm} from "@angular/forms";
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {DataWithTotalRecords} from "../../model/result-parameters/DataWithTotalRecords";
import {PageSortFilterParameters} from "../../model/parameters/PageSortFilterParameters";
import {BookFilterParameters} from "../../model/parameters/BookFilterParameters";
import {NotificationService} from "../../service/notification.service";
import {RouterService} from "../../service/router.service";
import {BaseComponent} from "../base/base.component";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent extends BaseComponent<Book> implements OnInit {

  public authors: Author[] = [];
  public deletedBook: Book;
  public currentYear: number = new Date().getFullYear();

  public bookFilterParameters: BookFilterParameters;

  selectedAuthors: Author[] = [];
  dropdownSettings = {};
  requiredAuthors: boolean = false;


  constructor(private bookService: BookService, private authorService: AuthorService,
              public notificationService: NotificationService, public router: RouterService) {
    super(notificationService, router, bookService)
  }

  ngOnInit() {
    super.ngOnInit();
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'fullName',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 5,
      allowSearchFilter: true,
      enableCheckAll: false,
    }
  }

  public getAuthors(): void {
    let pageParameters = new PageSortFilterParameters();
    //todo
    pageParameters.pageNumber = 0;
    pageParameters.pageSize = 999999;
    this.authorService.getAllWithParameters(pageParameters).subscribe(
      (response: DataWithTotalRecords<Author>) => {
        this.authors = this.setFullNameForAuthors(response.content);
      });

  }

  public onOpenModal(mode: string, book: Book): void {
    this.requiredAuthors = false;
    const container = document.getElementById('main-container')!;
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      this.getAuthors();
      this.selectedAuthors = [];
      button.setAttribute('data-target', '#createBookModal');
    }
    if (mode === 'edit') {
      this.getAuthors();
      this.editEntity = book;
      this.selectedAuthors = this.setFullNameForAuthors(this.editEntity.authors);
      button.setAttribute('data-target', '#updateBookModal');
    }
    if (mode === 'delete') {
      this.deletedBook = book;
      button.setAttribute('data-target', '#deleteBookModal');
    }
    if (mode === 'bulkDelete' && this.selection.hasValue()) {
      button.setAttribute('data-target', '#bulkDeleteBooksModal');
    }
    container.appendChild(button);
    button.click();
  }

  public onItemSelect(item: any) {
    this.selectedAuthors.push(item)
  }

  public onSelectAll(items: any) {
    this.selectedAuthors.push(items);
  }

  isInvalidForm(form: NgForm): boolean {
    if (form.invalid) {
      Object.keys(form.form.controls).forEach(key => {
        form.form.controls[key].markAsTouched()
      })
      this.requiredAuthors = true;
      return true;
    }
    return false;
  }

  manipulateWithEntityFields(createdBook: Book): Book {
    createdBook.name = createdBook.name.trim();
    createdBook.publisher = createdBook.publisher.trim();
    return createdBook;
  }

  manipulateWithEntityFieldsWhenUpdate(updatedBook: Book): Book {
    updatedBook.name = updatedBook.name.trim();
    updatedBook.publisher = updatedBook.publisher.trim();
    updatedBook.authors = this.authors.filter(author => updatedBook.authors.map(authorsId => authorsId.id).includes(author.id));
    return updatedBook;
  }

  getIdsFromSelection(): number[] {
    return this.selection.selected.map(value => value.id);
  }

  getFilterParameters(): any {
    let filterParameters = new BookFilterParameters();
    filterParameters.name = this.filterForm.value.name;
    filterParameters.isbn = this.filterForm.value.isbn;
    filterParameters.publisher = this.filterForm.value.publisher;
    filterParameters.toRating = this.filterForm.value.toRating;
    filterParameters.fromRating = this.filterForm.value.fromRating;
    filterParameters.yearPublisher = this.filterForm.value.yearPublisher;
    filterParameters.searchingName = this.filterForm.value.searchingName;
    return filterParameters;
  }

  private setFullNameForAuthors(authors: Author[]): Author[] {
    authors.forEach(author => author.fullName = author.firstName + " " + (author.secondName === null ? "" : author.secondName));
    return authors;
  }
}
