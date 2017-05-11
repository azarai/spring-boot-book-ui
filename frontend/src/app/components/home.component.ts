import {Component} from '@angular/core';

@Component({
    selector: 'app-home',
    template: `<h1>{{welcome}}</h1>
    <a routerLink="/comments" routerLinkActive="active">List comments</a>
    `
})
export class HomeComponent {
    welcome : string;
    constructor(){
        this.welcome = "Welcome to home page";
    };
};