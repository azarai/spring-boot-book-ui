import { Component } from '@angular/core';
import {Router, CanActivate} from '@angular/router';
import {AuthenticationService} from './authentication.service';
import {isLoggedin}  from './is-loggedin';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Angular and Spring Boot Book CommentStore';

  constructor(public auth: AuthenticationService, public router: Router) {}

  onLogout() {
    this.auth.logout()
      .subscribe(
        () => this.router.navigate(['../Login']),
      );
  }
}
