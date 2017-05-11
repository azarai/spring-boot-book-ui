import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from './components/home.component';
import {CommentlistComponent} from './commentlist/commentlist.component'
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './_guards/auth.guard';
const appRoutes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'comments', component: CommentlistComponent, canActivate: [AuthGuard]},
  { path: '', component: HomeComponent, pathMatch: 'full'} // redirect to home page on load
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);