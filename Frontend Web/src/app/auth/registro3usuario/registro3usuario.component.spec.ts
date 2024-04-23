import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Registro3usuarioComponent } from './registro3usuario.component';

describe('Registro3usuarioComponent', () => {
  let component: Registro3usuarioComponent;
  let fixture: ComponentFixture<Registro3usuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Registro3usuarioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Registro3usuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
