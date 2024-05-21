import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiciosDashUserComponent } from './servicios-dash-user.component';

describe('ServiciosDashUserComponent', () => {
  let component: ServiciosDashUserComponent;
  let fixture: ComponentFixture<ServiciosDashUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiciosDashUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiciosDashUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
