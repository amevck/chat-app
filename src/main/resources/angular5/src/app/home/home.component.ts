import { UtilityService } from './utility.service';
import { Component ,OnInit} from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import swal from 'sweetalert2';
import { LoginService } from '../services/login.service';
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
    userName = "amem.vck@gmail.com";
    password = "madushan";
    username = 'login'
    onlineUsers = [];
    myMessages = [
        
        // {type:"recieved",msg:"Hi Vincent, how are you? How is the project coming along?",date:"0:10 AM, Today"},
        // {type:"sent",msg:"Are we meeting today? Project has been already finished and I have results to show you.",date:"10:12 AM, Today"},
        // {type:"recieved",msg:"Well I am not sure. The rest of the team is not here yet. Maybe in an hour or so? Have you faced any problems at the last phase of the project?",date:"10:14 AM, Today"},
        // {type:"sent",msg:"Actually everything was fine. I'm very excited to show this to our team.",date:"10:20 AM, Today"},
    ]
    private stompClient;

    constructor(private utiliyService:UtilityService,private loginService:LoginService){

    }

    ngOnInit() {
        this.initializeWebSocketConnection()
        // this.login()
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
                this.senderName = x['_body']
                this.username = this.senderName;

                this.utiliyService.getOnlineUsers().subscribe(
                  data => { 
                    this.onlineUsers = JSON.parse(data["_body"])
                    this.onlineUsers = this.onlineUsers.filter(user => user!= this.senderName)
                    this.onlineUsers =  this.onlineUsers.map(user => user = {'userName':user,'messages':[]})
                    if(this.onlineUsers && this.onlineUsers.length > 0){
                      this.messageToSend = this.onlineUsers[0].userName
                    }
                    console.log(this.onlineUsers)
                  }
                )
              }
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

      convercationChanged(user){
        this.reciverName = user.userName;
        this.myMessages = user.messages;
        console.log(user)
      }
      sendMessage(message){
        console.log("send")
        this.messageToSend = ""
        this.reciverName = this.senderName == "amem.vck@gmail.com"?"ame.vck@gmail.com":"amem.vck@gmail.com"
        this.stompClient.send("/send/message" , {}, JSON.stringify({message:message,reciever:this.reciverName,sender:this.senderName}))
        // this.myMessages.push({type:"recieved",msg:JSON.parse(message.body).content,date:"now"},)
      }

      login(){
      swal({
        title: "user login",
        html:
        '<input id="swal-input1" [(ngmodel)] = "userName" type = "text" class="swal2-input">' +
        '<input id="swal-input2" [(ngmodel)] = "password" type = "password" class="swal2-input">',
        type: 'warning',
        showConfirmButton: true,
        showCancelButton: true,
        confirmButtonText: 'Login',
        timer: 100000,
       
      })
      .then( result =>{
        console.log("fired")
        if(result.value){
          this.loginService.login((document.getElementById("swal-input1") as HTMLInputElement).value,(document.getElementById("swal-input2") as HTMLInputElement).value).subscribe(
          // this.loginService.login(this.userName,this.password).subscribe(   
        data => {
          console.log(data)
          if (!data.url.includes('error')) {
            this.initializeWebSocketConnection()

            swal({
              type: 'success',
              title: "you are successfully login",
              showConfirmButton: false,
              timer: 3000
            });
          }
          else {
  
            swal({
              type: 'warning',
              title: "login Error",
              showConfirmButton: false,
              timer: 3000
            });
          }
        }
         )
        }
      }
      );
    }
  
}
