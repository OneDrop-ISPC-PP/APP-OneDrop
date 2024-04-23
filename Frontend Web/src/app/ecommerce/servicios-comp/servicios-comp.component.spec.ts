import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiciosCompComponent } from './servicios-comp.component';

describe('ServiciosCompComponent', () => {
  let component: ServiciosCompComponent;
  let fixture: ComponentFixture<ServiciosCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiciosCompComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiciosCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
