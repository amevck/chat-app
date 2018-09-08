import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Http,Response} from '@angular/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UtilityService  {
  url;
  ngOnInit() {
 
  }

  public userId;
  constructor(private httpClient:Http) { }



   //Utility method for send Get data
  getUser() : Observable<any>
  {
   return this.httpClient.get('/getUser');

  }

  moaGetSyn(url:string,data:any){

  return this.httpClient.get(url+data);


  }


}
