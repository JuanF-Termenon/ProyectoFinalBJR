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

    // Resaltar enlace activo según sección visible
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

    // Cerrar menú al pulsar un enlace
    navMenu.querySelectorAll('a').forEach(link => {
      link.addEventListener('click', () => {
        hamburger.classList.remove('open');
        navMenu.classList.remove('open');
        document.body.style.overflow = '';
      });
    });
  }


  /* ------------------------------------------
     3. SCROLL ANIMATIONS (fade-up)
  ------------------------------------------ */
  const fadeEls = document.querySelectorAll('.fade-up');

  const fadeObserver = new IntersectionObserver((entries) => {
    entries.forEach((entry, i) => {
      if (entry.isIntersecting) {
        // Stagger delay basado en posición dentro de su padre
        const siblings = Array.from(entry.target.parentElement.querySelectorAll('.fade-up'));
        const index = siblings.indexOf(entry.target);
        const delay = index * 80;
        setTimeout(() => entry.target.classList.add('visible'), delay);
        fadeObserver.unobserve(entry.target);
      }
    });
  }, { threshold: 0.1 });

  fadeEls.forEach(el => fadeObserver.observe(el));


  /* ------------------------------------------
     4. KPI BARS: animar las barras al entrar en viewport
  ------------------------------------------ */
  const kpiBarFills = document.querySelectorAll('.kpi-bar-fill');

  const barObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const fill = entry.target;
        const targetWidth = fill.dataset.width || fill.style.width || '0%';
        // Resetear y reanimar
        fill.style.width = '0%';
        requestAnimationFrame(() => {
          requestAnimationFrame(() => {
            fill.style.width = targetWidth;
          });
        });
        barObserver.unobserve(fill);
      }
    });
  }, { threshold: 0.3 });

  kpiBarFills.forEach(fill => {
    // Guardar el ancho objetivo como data-attribute y resetear
    const w = fill.style.width;
    fill.dataset.width = w;
    fill.style.width = '0%';
    barObserver.observe(fill);
  });


  /* ------------------------------------------
     5. KPI COUNTERS: animar números al entrar en viewport
  ------------------------------------------ */
  const kpiValues = document.querySelectorAll('.kpi-value[data-count]');

  const countUp = (el, target, duration = 1200) => {
    const isFloat = String(target).includes('.');
    const decimals = isFloat ? String(target).split('.')[1].length : 0;
    let start = null;

    const step = (timestamp) => {
      if (!start) start = timestamp;
      const progress = Math.min((timestamp - start) / duration, 1);
      const ease = 1 - Math.pow(1 - progress, 3); // ease-out cubic
      const current = parseFloat((ease * target).toFixed(decimals));
      el.textContent = isFloat ? current.toFixed(decimals) : Math.floor(current);
      if (progress < 1) requestAnimationFrame(step);
      else el.textContent = isFloat ? parseFloat(target).toFixed(decimals) : target;
    };

    requestAnimationFrame(step);
  };

  const counterObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const el = entry.target;
        const target = parseFloat(el.dataset.count);
        countUp(el, target);
        counterObserver.unobserve(el);
      }
    });
  }, { threshold: 0.5 });

  kpiValues.forEach(el => counterObserver.observe(el));


  /* ------------------------------------------
     6. FLUJO: resaltar pasos al hacer scroll
  ------------------------------------------ */
  const flowSection = document.getElementById('flujo');
  const flowSteps = document.querySelectorAll('.flow-step');

  if (flowSection && flowSteps.length) {
    const flowObserver = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          flowSteps.forEach((step, i) => {
            setTimeout(() => step.classList.add('done'), i * 200);
          });
          flowObserver.unobserve(entry.target);
        }
      });
    }, { threshold: 0.4 });

    flowObserver.observe(flowSection);
  }


  /* ------------------------------------------
     7. BACK TO TOP
  ------------------------------------------ */
  const backBtn = document.getElementById('back-to-top');

  if (backBtn) {
    window.addEventListener('scroll', () => {
      if (window.scrollY > 400) {
        backBtn.classList.add('visible');
      } else {
        backBtn.classList.remove('visible');
      }
    }, { passive: true });

    backBtn.addEventListener('click', () => {
      window.scrollTo({ top: 0, behavior: 'smooth' });
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

});