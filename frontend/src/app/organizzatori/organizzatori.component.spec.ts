import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizzatoriComponent } from './organizzatori.component';

describe('ListacuochiComponent', () => {
  let component: OrganizzatoriComponent;
  let fixture: ComponentFixture<OrganizzatoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrganizzatoriComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizzatoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
