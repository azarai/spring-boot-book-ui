import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';

@Injectable()
export class AuthenticationService {

    private server:string ="http://localhost:8080";

    constructor(private http: Http) { }
 
    login(username: string, password: string) {     
        const body = new URLSearchParams();
    
        body.set("username", username);
        body.set("password", password);

        let headers = new Headers();
        headers.append('Content-Type','application/x-www-form-urlencoded');
        let options = new RequestOptions({ headers: headers, withCredentials: true });

        //first requests inits session and retrieves xsrf token
        return this.http.get(this.server + "/preauth", options).flatMap(
            () => {
                return this.http.post(this.server + "/authenticate", body.toString(), options).map((response: Response) => {
                    if( response.status === 200) {
                        localStorage.setItem('currentUser', JSON.stringify({ user: username, token: "a"}));
                    }
                });
            }
        );

    }
 
    logout() {
        // remove user from local storage to log user out
        
        let headers = new Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
        let options = new RequestOptions({ headers: headers, withCredentials: true });
        return this.http.post(this.server + "/logout", "", options).map((response: Response) => {
            localStorage.removeItem('currentUser');
        });
    }
}