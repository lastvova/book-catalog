import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BookComponent} from './views/book/book.component';
import {AuthorComponent} from './views/author/author.component';
import {ReviewComponent} from './views/review/review.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MainPageComponent} from './views/main-page/main-page.component';
import {HttpClientModule} from "@angular/common/http";
import {NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatInputModule} from "@angular/material/input";
import {NgxMaskModule} from "ngx-mask";
import {MatButtonModule} from "@angular/material/button";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {BookPageComponent} from './views/book-page/book-page.component';
import {errorInterceptorProviders} from "./service/error-interceptor.service";
import { BaseComponent } from './views/base/base.component';


@NgModule({
  declarations: [
    AppComponent,
    BookComponent,
    AuthorComponent,
    ReviewComponent,
    MainPageComponent,
    BookPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgMultiSelectDropDownModule.forRoot(),
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    MatExpansionModule,
    MatSnackBarModule,
    MatInputModule,
    NgxMaskModule.forRoot(),
    MatButtonModule,
    MatCheckboxModule,
  ],
  providers: [errorInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
