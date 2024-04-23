import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-scroll-back-to-top-button',
  templateUrl: './scroll-back-to-top-button.component.html',
  styleUrls: ['./scroll-back-to-top-button.component.css']
})
export class ScrollBackToTopButtonComponent {
  // Método para realizar el desplazamiento hacia arriba
  scrollToTop() {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  }

  // Mostrar u ocultar el botón en función de la posición de desplazamiento
  @HostListener('window:scroll')
  onWindowScroll() {
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    const scrollButton = document.querySelector('.scroll-back-to-top-button');

    if (scrollButton) {
      if (scrollTop > 300) {
        scrollButton.classList.add('show');
      } else {
        scrollButton.classList.remove('show');
      }
    }
  }
}