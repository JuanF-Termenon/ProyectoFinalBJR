/* ============================================
   BJR Technician Services — main.js
   ============================================ */

document.addEventListener('DOMContentLoaded', () => {



  /* ------------------------------------------
     9. AÑO DINÁMICO en el footer
  ------------------------------------------ */
  const yearEl = document.getElementById('footer-year');
  if (yearEl) yearEl.textContent = new Date().getFullYear();


  /* ------------------------------------------
     10. API: cargar estados desde la BD
  ------------------------------------------ */
  const API_URL = 'api/estados.php';

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
