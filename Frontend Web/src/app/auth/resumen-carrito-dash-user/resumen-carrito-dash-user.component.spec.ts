import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResumenCarritoDashUserComponent } from './resumen-carrito-dash-user.component';

describe('ResumenCarritoDashUserComponent', () => {
  let component: ResumenCarritoDashUserComponent;
  let fixture: ComponentFixture<ResumenCarritoDashUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResumenCarritoDashUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResumenCarritoDashUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
