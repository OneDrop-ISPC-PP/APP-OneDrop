import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModServicioAdminComponent } from './mod-servicio-admin.component';

describe('ModServicioAdminComponent', () => {
  let component: ModServicioAdminComponent;
  let fixture: ComponentFixture<ModServicioAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModServicioAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModServicioAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
