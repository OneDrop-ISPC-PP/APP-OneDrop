import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModNotaUsuarioComponent } from './mod-nota-usuario.component';

describe('ModNotaUsuarioComponent', () => {
  let component: ModNotaUsuarioComponent;
  let fixture: ComponentFixture<ModNotaUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModNotaUsuarioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModNotaUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
