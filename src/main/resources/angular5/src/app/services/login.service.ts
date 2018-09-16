import { Injectable } from '@angular/core';
import { Http,Headers } from '@angular/http';

@Injectable()
export class LoginService {

  private headers = new Headers({ 'Content-Type': 'application/json' });
  constructor(private http: Http) { }

  login(uname: string, pwd: string) {
    // console.log(uname)
    console.log(uname)
    console.log(pwd)
    let formData: FormData = new FormData();
    formData.append('email', uname);
    formData.append('password', pwd);
    return this.http.post('/login', formData)

  }

  signUp(signupObj) {
    return this.http.post('/register', JSON.stringify(signupObj), { headers: this.headers })

  }

}
