import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoGlucemiaDashUserComponent } from './info-glucemia-dash-user.component';

describe('InfoGlucemiaDashUserComponent', () => {
  let component: InfoGlucemiaDashUserComponent;
  let fixture: ComponentFixture<InfoGlucemiaDashUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InfoGlucemiaDashUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoGlucemiaDashUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
