/* ============================================
   ST Connect — main.js
   ============================================ */

const translations = {
  es: {
    nav_architecture: 'Arquitectura',
    nav_roles: 'Roles',
    nav_status: 'Estados',
    nav_menu: 'Abrir menú',
    hero_tag: 'ST Connect · Servicio Técnico',
    hero_title: 'Gestión de incidencias técnicas',
    hero_sub: 'Plataforma centralizada para reportar, asignar y resolver incidencias técnicas desde cualquier puesto de trabajo. Rápido, trazable y seguro.',
    hero_btn: 'Explorar roles →',
    arch_tag: 'Arquitectura',
    arch_title: 'Cuatro componentes,<br>un sistema unificado',
    arch_sub: 'La arquitectura separa responsabilidades con claridad: los clientes solo hablan con el backend, y únicamente el backend accede a la base de datos.',
    arch_card1_title: 'App de escritorio',
    arch_card1_desc: 'Instalada en puestos fijos. Inicio automático, acceso rápido para reportar incidencias sin abrir navegador.',
    arch_card2_title: 'Aplicación web',
    arch_card2_badge: 'Proximamente',
    arch_card2_desc: 'Responsive. Acceso desde móvil, tablet u ordenador externo sin instalación previa.',
    arch_card3_title: 'Backend central',
    arch_card3_desc: 'Autenticación, reglas de negocio, gestión de estados, exportaciones y auditoría. Único punto de acceso a la base de datos.',
    arch_card4_title: 'Base de datos',
    arch_card4_desc: 'Almacena usuarios, puestos, incidencias, estados, históricos, informes, auditoría y configuraciones del sistema.',
    roles_tag: 'Usuarios y roles',
    roles_title: 'Cada usuario, en su contexto',
    roles_sub: 'Sistema de permisos RBAC con cuatro niveles de acceso, adaptados al flujo real del servicio técnico.',
    role1_badge: 'Operario',
    role1_title: 'Puesto de trabajo',
    role1_desc: 'Representa un área física como TP01 o Prensa04. Enfocado en la rapidez de reporte.',
    role1_perm1: 'Iniciar sesión con usuario de puesto',
    role1_perm2: 'Reportar incidencia con formulario mínimo',
    role1_perm3: 'Descripción breve opcional',
    role1_perm4: 'Ver estado de su última incidencia',
    role2_badge: 'Técnico ST',
    role2_title: 'Técnico de Servicio Técnico',
    role2_desc: 'Cuenta personal por técnico. Gestión completa del ciclo de vida del ticket.',
    role2_perm1: 'Ver y filtrar todas las incidencias',
    role2_perm2: 'Asignarse y cambiar estados',
    role2_perm3: 'Añadir notas internas',
    role2_perm4: 'Cerrar con informe obligatorio',
    role3_badge: 'Jefe ST',
    role3_title: 'Jefe de Servicio Técnico',
    role3_desc: 'Supervisión global del departamento. Acceso a métricas y exportaciones avanzadas.',
    role3_perm1: 'Histórico completo de incidencias',
    role3_perm2: 'Exportar por rango de fechas / filtros',
    role3_perm3: 'Estadísticas por técnico y categoría',
    role3_perm4: 'Rendimiento global del departamento',
    status_tag: 'Estados',
    status_title: 'Visibilidad en tiempo real',
    status_sub: 'Tres estados codificados por color que reflejan el ciclo completo de cualquier incidencia en el panel operativo.',
    status1_label: 'Sin atender',
    status1_title: 'En cola',
    status1_desc: 'Incidencias reportadas pendientes de asignación.',
    status2_label: 'Trabajando',
    status2_title: 'En curso',
    status2_desc: 'Incidencias siendo atendidas por el equipo técnico.',
    status3_label: 'Completada',
    status3_title: 'Resuelta',
    status3_desc: 'Incidencias cerradas con informe técnico.',
    cta_tag: 'ST Connect · IES Pere Maria Orts i Bosch',
    cta_title: '¿Listo para implementarlo?',
    cta_desc: 'Este sistema está diseñado para ser desplegado en entornos reales de empresa con equipos de servicio técnico.',
    cta_btn: 'Ver arquitectura técnica →',
    footer_security: 'Seguridad',
    footer_copy: 'Juan Francisco · Bogdan · Rubén — IES Pere Maria Orts i Bosch ·',
  },
  en: {
    nav_architecture: 'Architecture',
    nav_roles: 'Roles',
    nav_status: 'Status',
    nav_menu: 'Open menu',
    hero_tag: 'ST Connect · Technical Service',
    hero_title: 'Technical Incident Management',
    hero_sub: 'Centralized platform to report, assign and resolve technical incidents from any workstation. Fast, traceable and secure.',
    hero_btn: 'Explore roles →',
    arch_tag: 'Architecture',
    arch_title: 'Four components,<br>one unified system',
    arch_sub: 'The architecture separates responsibilities clearly: clients only talk to the backend, and only the backend accesses the database.',
    arch_card1_title: 'Desktop App',
    arch_card1_desc: 'Installed on fixed workstations. Auto-start, quick access to report incidents without opening a browser.',
    arch_card2_title: 'Web App',
    arch_card2_badge: 'Coming soon',
    arch_card2_desc: 'Responsive. Access from mobile, tablet or external computer without prior installation.',
    arch_card3_title: 'Central Backend',
    arch_card3_desc: 'Authentication, business rules, status management, exports and auditing. Single point of access to the database.',
    arch_card4_title: 'Database',
    arch_card4_desc: 'Stores users, workstations, incidents, statuses, history, reports, audits and system configurations.',
    roles_tag: 'Users & Roles',
    roles_title: 'Every user in their context',
    roles_sub: 'RBAC permission system with four access levels, adapted to the real technical service workflow.',
    role1_badge: 'Operator',
    role1_title: 'Workstation',
    role1_desc: 'Represents a physical area like TP01 or Prensa04. Focused on speed of reporting.',
    role1_perm1: 'Log in with workstation user',
    role1_perm2: 'Report incident with minimal form',
    role1_perm3: 'Optional brief description',
    role1_perm4: 'View status of last incident',
    role2_badge: 'ST Technician',
    role2_title: 'Technical Service Technician',
    role2_desc: 'Personal account per technician. Complete ticket lifecycle management.',
    role2_perm1: 'View and filter all incidents',
    role2_perm2: 'Assign yourself and change statuses',
    role2_perm3: 'Add internal notes',
    role2_perm4: 'Close with mandatory report',
    role3_badge: 'ST Chief',
    role3_title: 'Technical Service Chief',
    role3_desc: 'Global department supervision. Access to metrics and advanced exports.',
    role3_perm1: 'Full incident history',
    role3_perm2: 'Export by date range / filters',
    role3_perm3: 'Statistics by technician and category',
    role3_perm4: 'Global department performance',
    status_tag: 'Status',
    status_title: 'Real-time visibility',
    status_sub: 'Three color-coded statuses that reflect the complete lifecycle of any incident on the dashboard.',
    status1_label: 'Unattended',
    status1_title: 'In queue',
    status1_desc: 'Reported incidents pending assignment.',
    status2_label: 'In progress',
    status2_title: 'Ongoing',
    status2_desc: 'Incidents being handled by the technical team.',
    status3_label: 'Completed',
    status3_title: 'Resolved',
    status3_desc: 'Incidents closed with technical report.',
    cta_tag: 'ST Connect · IES Pere Maria Orts i Bosch',
    cta_title: 'Ready to implement it?',
    cta_desc: 'This system is designed to be deployed in real business environments with technical service teams.',
    cta_btn: 'View technical architecture →',
    footer_security: 'Security',
    footer_copy: 'Juan Francisco · Bogdan · Rubén — IES Pere Maria Orts i Bosch ·',
  }
};

function setLanguage(lang) {
  const t = translations[lang];
  if (!t) return;

  document.documentElement.lang = lang;
  document.documentElement.dataset.language = lang;

  document.querySelectorAll('[data-i18n]').forEach(el => {
    const key = el.dataset.i18n;
    if (t[key] !== undefined) {
      el.innerHTML = t[key];
    }
  });

  document.querySelectorAll('[data-i18n-aria]').forEach(el => {
    const key = el.dataset.i18nAria;
    if (t[key] !== undefined) {
      el.setAttribute('aria-label', t[key]);
    }
  });

  document.querySelectorAll('.lang-btn').forEach(btn => {
    btn.classList.toggle('active', btn.dataset.lang === lang);
  });

  localStorage.setItem('st-language', lang);
}

document.addEventListener('DOMContentLoaded', () => {

  const savedLang = localStorage.getItem('st-language') || 'es';
  setLanguage(savedLang);

  document.querySelectorAll('.lang-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      setLanguage(btn.dataset.lang);
    });
  });

  const yearEl = document.getElementById('footer-year');
  if (yearEl) yearEl.textContent = new Date().getFullYear();

  const API_URL = 'api/estados.php';

  const cargarEstados = async () => {
    try {
      const res = await fetch(API_URL);
      if (!res.ok) throw new Error('Error HTTP ' + res.status);
      const data = await res.json();

      ['cola', 'curso', 'resuelta'].forEach(seccion => {
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
