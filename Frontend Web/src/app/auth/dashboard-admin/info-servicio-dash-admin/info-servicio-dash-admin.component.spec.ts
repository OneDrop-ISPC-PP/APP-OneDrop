import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoServicioDashAdminComponent } from './info-servicio-dash-admin.component';

describe('InfoServicioDashAdminComponent', () => {
  let component: InfoServicioDashAdminComponent;
  let fixture: ComponentFixture<InfoServicioDashAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InfoServicioDashAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoServicioDashAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
