import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BienvenidaDashUserComponent } from './bienvenida-dash-user.component';

describe('BienvenidaDashUserComponent', () => {
  let component: BienvenidaDashUserComponent;
  let fixture: ComponentFixture<BienvenidaDashUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BienvenidaDashUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BienvenidaDashUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
