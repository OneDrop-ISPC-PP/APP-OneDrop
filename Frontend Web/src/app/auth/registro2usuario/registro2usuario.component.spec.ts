import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Registro2usuarioComponent } from './registro2usuario.component';

describe('Registro2usuarioComponent', () => {
  let component: Registro2usuarioComponent;
  let fixture: ComponentFixture<Registro2usuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Registro2usuarioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Registro2usuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
