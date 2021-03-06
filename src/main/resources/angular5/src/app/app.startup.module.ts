import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NavMenuComponent } from './navmenu/navmenu.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { UtilityService } from './home/utility.service';
import { SweetAlert2Module } from '@toverux/ngx-sweetalert2';
import { LoginService } from './services/login.service';
@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        HttpClientModule,
        HttpModule,
       
    ],
    declarations: [
        NavMenuComponent,
        HomeComponent
    ],
    exports: [
        NavMenuComponent,
        HomeComponent
    ],
    providers: [UtilityService,LoginService]
})
export class AppStartupModule {
}
