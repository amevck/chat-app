import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

@Injectable()
export class LoginService {

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

}
