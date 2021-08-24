import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AuthorComponent} from "./views/author/author.component";
import {BookComponent} from "./views/book/book.component";
import {ReviewComponent} from "./views/review/review.component";

const routes: Routes = [
  {path: 'authors', component: AuthorComponent},
  {path: 'books', component: BookComponent},
  {path: 'reviews', component: ReviewComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
