import { Component, ViewChild, OnInit } from '@angular/core';
import { AngularFileUploaderComponent } from "angular-file-uploader";
import { Socket } from 'ngx-socket-io';
import 'rxjs/add/operator/map'
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  ngOnInit(): void {
    this.initializeWebSocketConnection();
  }
  private stompClient;
  constructor(){
    
  }
  @ViewChild('fileUpload1')
    private fileUpload :  AngularFileUploaderComponent;
  title = 'app';

  afuConfig = {
    uploadAPI: {
      url:"/upload"
    },
    hideProgressBar: false,
};
test(){
  this.fileUpload.uploadFiles()
}

sendMessage(msg: string){
}


initializeWebSocketConnection(){
  let ws = new SockJS('/ws');
  this.stompClient = Stomp.over(ws);
  let that = this;
  this.stompClient.connect({}, function(frame) {
    console.log(frame);
    that.stompClient.subscribe('/user/queue/notify', (message) => {
      console.log(message);
      if(message.body) {
         console.log(JSON.parse(message.body).content)
      }
    });
  });
}


subscribe($event) {
  
}
}
