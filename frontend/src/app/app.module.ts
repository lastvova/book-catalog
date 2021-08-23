import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BookComponent} from './views/book/book.component';
import {AuthorComponent} from './views/author/author.component';
import {Routes} from "@angular/router";
import { ReviewComponent } from './views/review/review.component';

const routes: Routes = [
  { path: '', component: AppComponent},
  { path: 'authors', component: AuthorComponent},
  { path: 'books', component: BookComponent},
]

@NgModule({
  declarations: [
    AppComponent,
    BookComponent,
    AuthorComponent,
    ReviewComponent,
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
