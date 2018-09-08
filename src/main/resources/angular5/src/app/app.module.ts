import { HomeComponent } from './home/home.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';
import { AppStartupModule } from './app.startup.module';
import { FormsModule } from '@angular/forms';


@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        AppStartupModule,
        FormsModule
    ],
    declarations: [
        AppComponent,
       
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule {
}
