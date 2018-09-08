import { UtilityService } from './utility.service';
import { Component ,OnInit} from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})


export class HomeComponent implements OnInit{
    title = 'Home';
    reciverName = "vincent";
    senderName= "";
    messageToSend = "";
    myMessages = [
        
        {type:"recieved",msg:"Hi Vincent, how are you? How is the project coming along?",date:"0:10 AM, Today"},
        {type:"sent",msg:"Are we meeting today? Project has been already finished and I have results to show you.",date:"10:12 AM, Today"},
        {type:"recieved",msg:"Well I am not sure. The rest of the team is not here yet. Maybe in an hour or so? Have you faced any problems at the last phase of the project?",date:"10:14 AM, Today"},
        {type:"sent",msg:"Actually everything was fine. I'm very excited to show this to our team.",date:"10:20 AM, Today"},
    ]
    private stompClient;

    constructor(private utiliyService:UtilityService){

    }

    ngOnInit() {
        this.initializeWebSocketConnection()
    }


    initializeWebSocketConnection(){
        let ws = new SockJS('/ws');
        this.stompClient = Stomp.over(ws);
        let that = this;
        this.stompClient.connect({}, (frame) => {
          console.log(frame);
          if(this.senderName == ""){
            this.utiliyService.getUser().subscribe(
              x => {
                console.log(x)
                this.senderName = x['_body']}
            )
          }
          that.stompClient.subscribe('/user/queue/notify', (message) => {
         
            console.log(message);
            console.log(message.body)
            if(message.body) {
              console.log(JSON.parse(message.body).message);
              console.log(JSON.parse(message.body).sender + " , " +this.senderName)
              JSON.parse(message.body).sender != this.senderName? this.myMessages.push({type:"recieved",msg:JSON.parse(message.body).message,date:"now"},):
              this.myMessages.push({type:"sent",msg:JSON.parse(message.body).message,date:"now"},)
            }
          });
        });
      }

      sendMessage(message){
        console.log("send")
        this.messageToSend = ""
        this.reciverName = this.senderName == "amem.vck@gmail.com"?"ame.vck@gmail.com":"amem.vck@gmail.com"
        this.stompClient.send("/send/message" , {}, JSON.stringify({message:message,reciever:this.reciverName,sender:this.senderName}))
        // this.myMessages.push({type:"recieved",msg:JSON.parse(message.body).content,date:"now"},)
      }
}
