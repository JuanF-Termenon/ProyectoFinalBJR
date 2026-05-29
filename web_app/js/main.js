/* ============================================
   BJR Technician Services — main.js
   ============================================ */

document.addEventListener('DOMContentLoaded', () => {

  /* ------------------------------------------
     1. NAV: shrink al hacer scroll + sección activa
  ------------------------------------------ */
  const nav = document.querySelector('nav');
  const sections = document.querySelectorAll('section[id], footer');
  const navLinks = document.querySelectorAll('.nav-links a[href^="#"]');

  const updateNav = () => {
    if (window.scrollY > 60) {
      nav.classList.add('scrolled');
    } else {
      nav.classList.remove('scrolled');
    }

    let current = '';
    sections.forEach(section => {
      const top = section.offsetTop - 100;
      if (window.scrollY >= top) {
        current = section.getAttribute('id') || '';
      }
    });

    navLinks.forEach(link => {
      link.classList.remove('active');
      if (link.getAttribute('href') === `#${current}`) {
        link.classList.add('active');
      }
    });
  };

  window.addEventListener('scroll', updateNav, { passive: true });
  updateNav();


  /* ------------------------------------------
     2. MENÚ HAMBURGUESA (mobile)
  ------------------------------------------ */
  const hamburger = document.querySelector('.nav-hamburger');
  const navMenu = document.querySelector('.nav-links');

  if (hamburger && navMenu) {
    hamburger.addEventListener('click', () => {
      hamburger.classList.toggle('open');
      navMenu.classList.toggle('open');
      document.body.style.overflow = navMenu.classList.contains('open') ? 'hidden' : '';
    });

    navMenu.querySelectorAll('a').forEach(link => {
      link.addEventListener('click', () => {
        hamburger.classList.remove('open');
        navMenu.classList.remove('open');
        document.body.style.overflow = '';
      });
    });
  }


  /* ------------------------------------------
     8. SMOOTH SCROLL para enlaces internos
  ------------------------------------------ */
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', (e) => {
      const target = document.querySelector(anchor.getAttribute('href'));
      if (target) {
        e.preventDefault();
        const offset = nav ? nav.offsetHeight : 0;
        const top = target.getBoundingClientRect().top + window.scrollY - offset;
        window.scrollTo({ top, behavior: 'smooth' });
      }
    });
  });


  /* ------------------------------------------
     9. AÑO DINÁMICO en el footer
  ------------------------------------------ */
  const yearEl = document.getElementById('footer-year');
  if (yearEl) yearEl.textContent = new Date().getFullYear();


  /* ------------------------------------------
     10. API: cargar estados desde la BD
  ------------------------------------------ */
  const API_URL = '/api/estados.php';

const cargarEstados = async () => {
  try {
    const res = await fetch(API_URL);
    if (!res.ok) throw new Error('Error HTTP ' + res.status);
    const data = await res.json();

    const secciones = ['cola', 'curso', 'resuelta'];
    secciones.forEach(seccion => {
      const grupo = data[seccion];
      
      const numEl = document.getElementById('num-' + seccion);
      if (numEl) {
        numEl.textContent = grupo.total || 0;
      }
    });

  } catch (err) {
    console.error('Error al cargar estados:', err);
    ['cola', 'curso', 'resuelta'].forEach(seccion => {
      const numEl = document.getElementById('num-' + seccion);
      if (numEl) numEl.textContent = '-';
    });
  }
};

cargarEstados();
setInterval(cargarEstados, 5000);
});
