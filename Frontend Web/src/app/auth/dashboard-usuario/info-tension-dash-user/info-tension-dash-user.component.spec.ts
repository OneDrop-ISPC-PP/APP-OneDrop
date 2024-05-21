import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoTensionDashUserComponent } from './info-tension-dash-user.component';

describe('InfoTensionDashUserComponent', () => {
  let component: InfoTensionDashUserComponent;
  let fixture: ComponentFixture<InfoTensionDashUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InfoTensionDashUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoTensionDashUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
