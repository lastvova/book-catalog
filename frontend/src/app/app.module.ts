import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BookComponent} from './views/book/book.component';
import {AuthorComponent} from './views/author/author.component';
import {Routes} from "@angular/router";
import { ReviewComponent } from './views/review/review.component';
import { AppRoutingModule } from './app-routing.module';
import {FormsModule} from "@angular/forms";
import { MainPageComponent } from './views/main-page/main-page.component';
import {BookService} from "./service/book.service";
import {HttpClientModule} from "@angular/common/http";

const routes: Routes = [
  { path: '', component: MainPageComponent},
  { path: 'main', component: MainPageComponent},
  { path: 'authors', component: AuthorComponent},
  { path: 'books', component: BookComponent},
]

@NgModule({
  declarations: [
    AppComponent,
    BookComponent,
    AuthorComponent,
    ReviewComponent,
    MainPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
