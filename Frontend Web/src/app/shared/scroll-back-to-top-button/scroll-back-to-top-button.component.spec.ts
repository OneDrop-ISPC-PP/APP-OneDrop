import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScrollBackToTopButtonComponent } from './scroll-back-to-top-button.component';

describe('ScrollBackToTopButtonComponent', () => {
  let component: ScrollBackToTopButtonComponent;
  let fixture: ComponentFixture<ScrollBackToTopButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScrollBackToTopButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScrollBackToTopButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
