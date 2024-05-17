import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModPacienteAdminComponent } from './mod-paciente-admin.component';

describe('ModPacienteAdminComponent', () => {
  let component: ModPacienteAdminComponent;
  let fixture: ComponentFixture<ModPacienteAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModPacienteAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModPacienteAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
