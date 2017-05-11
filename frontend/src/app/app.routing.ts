import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from './components/home.component';
import {CommentlistComponent} from './commentlist/commentlist.component'

const appRoutes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'comments', component: CommentlistComponent},
  { path: '', component: HomeComponent, pathMatch: 'full'} // redirect to home page on load
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);