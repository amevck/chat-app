import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NavMenuComponent } from './navmenu.component';

describe('Nav Menu Component', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [
                NavMenuComponent,
            ],
            imports: [
                RouterTestingModule.withRoutes([])
            ],
            providers: [
            ]
        }).compileComponents();
    }));

    it('should create the component', async(() => {
        const fixture = TestBed.createComponent(NavMenuComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    }));

});
