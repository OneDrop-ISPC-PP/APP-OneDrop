import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasInfoServicioDashUserComponent } from './mas-info-servicio-dash-user.component';

describe('MasInfoServicioDashUserComponent', () => {
  let component: MasInfoServicioDashUserComponent;
  let fixture: ComponentFixture<MasInfoServicioDashUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasInfoServicioDashUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MasInfoServicioDashUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
