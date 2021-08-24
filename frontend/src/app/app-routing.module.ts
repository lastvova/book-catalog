import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AuthorComponent} from "./views/author/author.component";
import {BookComponent} from "./views/book/book.component";
import {ReviewComponent} from "./views/review/review.component";
import {MainPageComponent} from "./views/main-page/main-page.component";

const routes: Routes = [
  {path: '', component: MainPageComponent},
  {path: 'main', component: MainPageComponent},
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
