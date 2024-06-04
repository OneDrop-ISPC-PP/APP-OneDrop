import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModNotaTensionUsuarioComponent } from './mod-nota-tension-usuario.component';

describe('ModNotaTensionUsuarioComponent', () => {
  let component: ModNotaTensionUsuarioComponent;
  let fixture: ComponentFixture<ModNotaTensionUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModNotaTensionUsuarioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModNotaTensionUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
