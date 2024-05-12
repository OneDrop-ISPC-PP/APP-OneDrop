import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoUsuarioDashAdminComponent } from './info-usuario-dash-admin.component';

describe('InfoUsuarioDashAdminComponent', () => {
  let component: InfoUsuarioDashAdminComponent;
  let fixture: ComponentFixture<InfoUsuarioDashAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InfoUsuarioDashAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoUsuarioDashAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
