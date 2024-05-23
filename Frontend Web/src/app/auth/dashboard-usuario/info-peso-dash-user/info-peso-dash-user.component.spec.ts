import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoPesoDashUserComponent } from './info-peso-dash-user.component';

describe('InfoPesoDashUserComponent', () => {
  let component: InfoPesoDashUserComponent;
  let fixture: ComponentFixture<InfoPesoDashUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InfoPesoDashUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoPesoDashUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
