import {Component, OnInit} from '@angular/core';
import {Author} from "../../model/Author";
import {AuthorService} from "../../service/author.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {PageEvent} from "@angular/material/paginator";

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

  constructor(private authorService: AuthorService) {
  }

  ngOnInit(): void {
    this.getAuthors();
  }

  public getAuthors(): void {
    this.authorService.getAuthors().subscribe(
      (response: Author[]) => {
        this.authors = response;
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
    this.authorService.getAuthorsWithPagination(event.pageIndex+1, event.pageSize).subscribe(
      (response: Author[]) => {
        this.authors = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }
}
