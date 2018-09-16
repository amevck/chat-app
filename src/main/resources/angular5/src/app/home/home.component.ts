import { UtilityService } from './utility.service';
import { Component ,OnInit} from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import swal from 'sweetalert2';
import { LoginService } from '../services/login.service';
import { Http } from '@angular/http';
@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})


export class HomeComponent implements OnInit{
    title = 'Home';
    reciverName = "";
    senderName= "";
    messageToSend = "";
    userName = "";
    password = "madushan";
    username = 'login'
    conversations = []
    test = false;
 
    signUpObject = {
      email:"",
      password:"",
      firstName:"",
      lastName:"",
      userName:"",
      confirmPassword : ""
    }
  user: any = {};
  reciver: any = {};

    validate(){
     this.getValuesFromTemplate()
      var str = ""
      this.signUpObject.email == "" || null? str =  "email":"";
      this.signUpObject.firstName == "" || null?str =  "firstName":"";
      this.signUpObject.lastName == "" || null?str =  "lastName":"";
     this.signUpObject.userName == "" || null? str = "userName":"";
     this.signUpObject.password == "" || null? str = "password":"";
      if(this.signUpObject.password != this.signUpObject.confirmPassword ){
        return "PwdMissMatch"
      }
      if(str == ""){
        return "VALIDATED"
      }else{
        return "please fill " + str + " before submit"
      }
    }

    conversation = null;
    signInTemplate = `<input placeholder="email/userName" id="swal-input1" [(ngmodel)] = "userName" type = "text" class="swal2-input">
    <input placeholder="password" id="swal-input2" [(ngmodel)] = "password" type = "password" class="swal2-input"><label>password mismatch</label>`;

    signUpTemplate = `<div id = "signUp">
    <input placeholder="First name" id="swal-input-firstName" [(ngmodel)] = "signUpObject.firstName" type = "text" class="swal2-input">
    <input placeholder="Last Name" id="swal-input-lastName" [(ngmodel)] = "signUpObject.lastName" type = "text" class="swal2-input">
    <input placeholder="userName" id="swal-input-userName" [(ngmodel)] = "signUpObject.userName" type = "text" class="swal2-input">
    <input placeholder="email" id="swal-input-email" [(ngmodel)] = "signUpObject.email" type = "text" class="swal2-input">
    <input placeholder="password" id="swal-input-password" [(ngmodel)] = "signUpObject.password" type = "password" class="swal2-input">
    <input placeholder="Confirm password" id="swal-input-confirmPassword" [(ngmodel)] = "signUpObject.confirmPassword" type = "password" class="swal2-input">
    <label id="swal-input-pwdMismatch"></label>
    </div>`;
    onlineUsers = [];
    myMessages = [
        
        // {type:"recieved",msg:"Hi Vincent, how are you? How is the project coming along?",date:"0:10 AM, Today"},
        // {type:"sent",msg:"Are we meeting today? Project has been already finished and I have results to show you.",date:"10:12 AM, Today"},
        // {type:"recieved",msg:"Well I am not sure. The rest of the team is not here yet. Maybe in an hour or so? Have you faced any problems at the last phase of the project?",date:"10:14 AM, Today"},
        // {type:"sent",msg:"Actually everything was fine. I'm very excited to show this to our team.",date:"10:20 AM, Today"},
    ]
    private stompClient;

    constructor(private utiliyService:UtilityService,private loginService:LoginService,http: Http){
      
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
                console.log(x['_body'])
                console.log(JSON.parse(x['_body']))
                var user = JSON.parse(x['_body'])
                this.user = user;
                this.senderName = user.email;
                user.conversation?this.conversations = user.conversation:"";
                user.conversation?this.convercationChanged(this.conversations[0]):""
                console.log(this.conversations)
                console.log(this.senderName);
                if(this.senderName){
                this.username = this.senderName;
                }
                this.utiliyService.getOnlineUsers().subscribe(
                  data => { 
                    this.onlineUsers = JSON.parse(data["_body"])
                    this.onlineUsers = this.onlineUsers.filter(user => user!= this.senderName)
                    this.onlineUsers =  this.onlineUsers.map(user => user = {'userName':user,'messages':[]})
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
              var message = JSON.parse(message.body)
              if(message.type == "CONVERSASION"){
              this.addConversation(message);
              }else{
            //  this.myMessages.push(message.obj);
            this.addMessageToConversation(message.obj)
              }
       
            }
          });
        });
      }

      convercationChanged(conversation){
        console.log(conversation)
        console.log(conversation.memberList[0].email)
       
        console.log(this.senderName)
        console.log(conversation.memberList[0].email)
        if(conversation.haveNewMessages){
          conversation.haveNewMessages = false;
        }
        
        this.reciverName = this.senderName == conversation.memberList[0].email?conversation.memberList[1].email:conversation.memberList[0].email;
        this.reciver = this.senderName == conversation.memberList[0].email?conversation.memberList[1]:conversation.memberList[0]
        this.myMessages = conversation.messageList;
        this.conversation = conversation;
        console.log(this.reciver)
    
      }

      addConversation(message){
        var receiver = message.obj.memberList.filter(user => user.email == this.senderName)[0].email;
         var conv =  message.obj
        if(!this.conversation || !this.conversation.id){
          if(this.reciverName == receiver){
            this.convercationChanged(conv)
          }
         
      }else{

        conv =  Object.assign(message.obj, {haveNewMessages:true})
      }
      if(this.conversations.length == 0){
        this.convercationChanged(conv)
      }
      this.conversations.push(conv)
    }

      addMessageToConversation(message:any){
        console.log(message);
   
       var conversation = this.conversations.filter(conversation => conversation.id == message.conversationId)
       console.log(conversation)
        if(conversation && conversation[0] && conversation[0].messageList){
          if(this.conversation.id != conversation[0].id){
            conversation[0] =  Object.assign(conversation[0], {haveNewMessages:true})
          }
          conversation[0].messageList.push(message)
        }
      
        // if(this.conversation.id == message.conversationId){
        //   console.log('fired')
        //   this.myMessages.push(message);
        // }
      }

      onlineUserChanged(user){
        this.reciverName = user.userName;
        this.myMessages = []
        this.conversation = null;
        console.log(user)
      }
      sendMessage(message){
        console.log("send")
        this.messageToSend = ""
        console.log(this.conversation?this.conversation.id?this.conversation.id:null:null)
     
        this.stompClient.send("/send/message" , {}, JSON.stringify({message:message,reciever:this.reciverName,sender:this.senderName,conversationId:this.conversation?this.conversation.id?this.conversation.id:null:null}))

        // this.myMessages.push({type:"recieved",msg:JSON.parse(message.body).content,date:"now"},)
      }

      login(){

        this.test = !this.test;
      swal({
        title: "user login",
        html: this.signInTemplate,
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

getValuesFromTemplate(){
  this.signUpObject.firstName = (document.getElementById("swal-input-firstName") as HTMLInputElement).value
  this.signUpObject.lastName =  (document.getElementById("swal-input-lastName") as HTMLInputElement).value
  this.signUpObject.userName =  (document.getElementById("swal-input-userName") as HTMLInputElement).value
  this.signUpObject.email =  (document.getElementById("swal-input-email") as HTMLInputElement).value
  this.signUpObject.password =  (document.getElementById("swal-input-password") as HTMLInputElement).value
  this.signUpObject.confirmPassword =  (document.getElementById("swal-input-confirmPassword") as HTMLInputElement).value
}
    signUp(){
      swal({
        title: "user login",
        html:this.signUpTemplate,
        type: 'warning',
        showConfirmButton: true,
        onOpen:  () => {
        swal.disableConfirmButton()
          document.getElementById("signUp").addEventListener('keyup', (e) => {
             console.log(e)
             var validate = this.validate()
         if(validate != "VALIDATED"){
            swal.disableConfirmButton()
          }
         else
         {
          document.getElementById("swal-input-pwdMismatch").innerHTML  = "";
           swal.enableConfirmButton();
          }
          validate == "PwdMissMatch"?  document.getElementById("swal-input-pwdMismatch").innerHTML  = "password mismatch":"";
         this.signUpObject.password == this.signUpObject.confirmPassword? document.getElementById("swal-input-pwdMismatch").innerHTML  = "":""
         
         })
      },
        showCancelButton: true,
        confirmButtonText: 'sign Up',
        timer: 100000,
       
      })
      .then( result =>{
        console.log("fired")
        if(result.value){

          this.getValuesFromTemplate();

          console.log(this.signUpObject);
          this.loginService.signUp(this.signUpObject).subscribe(
          // this.loginService.login(this.userName,this.password).subscribe(   
        data => {
          console.log(data)
          var response = JSON.parse(data["_body"])
          if (response.code == 100) {
            this.initializeWebSocketConnection()

            swal({
              type: 'success',
              title: "you are successfully sign up. please login to continue",
              showConfirmButton: false,
              timer: 3000
            });
          }
          else {
  
            swal({
              type: 'warning',
              title: "sign Up Error",
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
