import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BienvenidaDashAdminComponent } from './bienvenida-dash-admin.component';

describe('BienvenidaDashAdminComponent', () => {
  let component: BienvenidaDashAdminComponent;
  let fixture: ComponentFixture<BienvenidaDashAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BienvenidaDashAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BienvenidaDashAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
