import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { routing }  from './app.routing';

import { AppComponent } from './app.component';
import {HomeComponent} from './components/home.component';

import { CommentService } from './comment.service';
import { CommentlistComponent } from './commentlist/commentlist.component';

import { PaginationModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-bootstrap';
import { ConfirmdialogComponent } from './confirmdialog/confirmdialog.component';

import { AuthGuard } from './_guards/auth.guard';
import { LoginComponent } from './login/login.component';
import {AuthenticationService} from './authentication.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CommentlistComponent,
    ConfirmdialogComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    routing,
    FormsModule,
    HttpModule, 
    PaginationModule.forRoot(),
    ModalModule.forRoot()
  ],
  providers: [CommentService,
  AuthGuard, AuthenticationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
