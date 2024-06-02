import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasInfoPacienteDashAdminComponent } from './mas-info-paciente-dash-admin.component';

describe('MasInfoPacienteDashAdminComponent', () => {
  let component: MasInfoPacienteDashAdminComponent;
  let fixture: ComponentFixture<MasInfoPacienteDashAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasInfoPacienteDashAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MasInfoPacienteDashAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
