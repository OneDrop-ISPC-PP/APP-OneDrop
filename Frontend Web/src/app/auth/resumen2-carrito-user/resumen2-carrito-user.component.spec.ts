import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Resumen2CarritoUserComponent } from './resumen2-carrito-user.component';

describe('Resumen2CarritoUserComponent', () => {
  let component: Resumen2CarritoUserComponent;
  let fixture: ComponentFixture<Resumen2CarritoUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Resumen2CarritoUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Resumen2CarritoUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
