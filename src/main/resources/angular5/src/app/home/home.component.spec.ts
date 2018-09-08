import { TestBed, async } from '@angular/core/testing';
import { HomeComponent } from './home.component';

describe('Home Component', () => {
    

    it('should create the component', async(() => {
        const fixture = TestBed.createComponent(HomeComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    }));

    it(`should have as title 'Home'`, async(() => {
        const fixture = TestBed.createComponent(HomeComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app.title).toEqual('Home');
    }));

});
