import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModNotaPesoUsuarioComponent } from './mod-nota-peso-usuario.component';

describe('ModNotaPesoUsuarioComponent', () => {
  let component: ModNotaPesoUsuarioComponent;
  let fixture: ComponentFixture<ModNotaPesoUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModNotaPesoUsuarioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModNotaPesoUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
