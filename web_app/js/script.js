
    const loginScreen = document.getElementById('loginScreen');
    const appScreen = document.getElementById('appScreen');
    const loginForm = document.getElementById('loginForm');
    const ticketForm = document.getElementById('ticketForm');
    const logoutBtn = document.getElementById('logoutBtn');
    const navButtons = document.querySelectorAll('.nav-btn');
    const pageLinks = document.querySelectorAll('[data-page-link]');
    const pages = document.querySelectorAll('.page');
    const pageTitle = document.getElementById('pageTitle');
    const pageDescription = document.getElementById('pageDescription');
    const userChip = document.getElementById('userChip');
    const puestoInput = document.getElementById('puestoInput');
    const descriptionInput = document.getElementById('descriptionInput');
    const priorityInput = document.getElementById('priorityInput');
    const puestoList = document.getElementById('puestoList');
    const ticketsTable = document.getElementById('ticketsTable');
    const historyList = document.getElementById('historyList');
    const statusFilter = document.getElementById('statusFilter');
    const priorityFilter = document.getElementById('priorityFilter');
    const searchInput = document.getElementById('searchInput');
    const metricActivas = document.getElementById('metricActivas');
    const metricCurso = document.getElementById('metricCurso');
    const metricResueltas = document.getElementById('metricResueltas');
    const metricCriticas = document.getElementById('metricCriticas');
    const mobileNavToggle = document.getElementById('mobileNavToggle');
    const sidebar = document.getElementById('sidebar');

    const pageMeta = {
      dashboardPage: { title: 'Resumen', description: 'Estado general del sistema de incidencias.' },
      puestoPage: { title: 'Panel de puesto', description: 'Registro rápido de nuevas incidencias.' },
      stPage: { title: 'Panel ST', description: 'Vista operativa para técnicos y supervisión.' },
      historyPage: { title: 'Histórico', description: 'Consulta de incidencias resueltas y trazabilidad básica.' }
    };

    let currentUser = null;
    let theme = matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
    document.documentElement.setAttribute('data-theme', theme);

    const tickets = [
      { id: 1, puesto: 'TP01', description: 'No enciende el equipo principal', priority: 'Alta', status: 'Activa', date: '16/04/2026 08:30', assignedTo: '-', report: '' },
      { id: 2, puesto: 'Prensa04', description: 'La impresora no responde', priority: 'Media', status: 'En curso', date: '16/04/2026 09:10', assignedTo: 'tecnico1', report: '' },
      { id: 3, puesto: 'TP02', description: 'Pantalla sin señal', priority: 'Baja', status: 'Resuelta', date: '15/04/2026 16:40', assignedTo: 'tecnico2', report: 'Se reconectó el cable HDMI y se verificó imagen.' },
      { id: 4, puesto: 'Corte07', description: 'Equipo reiniciándose solo', priority: 'Crítica', status: 'Activa', date: '16/04/2026 10:05', assignedTo: '-', report: '' }
    ];

    function fmtDate() {
      const d = new Date();
      const dd = String(d.getDate()).padStart(2, '0');
      const mm = String(d.getMonth() + 1).padStart(2, '0');
      const yy = d.getFullYear();
      const hh = String(d.getHours()).padStart(2, '0');
      const mi = String(d.getMinutes()).padStart(2, '0');
      return `${dd}/${mm}/${yy} ${hh}:${mi}`;
    }

    function stateClass(status) {
      if (status === 'Activa') return 'state-activa';
      if (status === 'En curso') return 'state-curso';
      return 'state-resuelta';
    }

    function priorityClass(priority) {
      const map = {
        'Baja': 'priority-baja',
        'Media': 'priority-media',
        'Alta': 'priority-alta',
        'Crítica': 'priority-crítica'
      };
      return map[priority] || 'priority-media';
    }

    function renderMetrics() {
      metricActivas.textContent = tickets.filter(t => t.status === 'Activa').length;
      metricCurso.textContent = tickets.filter(t => t.status === 'En curso').length;
      metricResueltas.textContent = tickets.filter(t => t.status === 'Resuelta').length;
      metricCriticas.textContent = tickets.filter(t => t.priority === 'Crítica').length;
    }

    function filteredTickets() {
      const s = statusFilter.value;
      const p = priorityFilter.value;
      const q = searchInput.value.trim().toLowerCase();
      return tickets.filter(ticket => {
        const matchesStatus = s === 'Todas' || ticket.status === s;
        const matchesPriority = p === 'Todas' || ticket.priority === p;
        const haystack = `${ticket.puesto} ${ticket.description} ${ticket.assignedTo}`.toLowerCase();
        const matchesSearch = !q || haystack.includes(q);
        return matchesStatus && matchesPriority && matchesSearch;
      }).sort((a, b) => b.id - a.id);
    }

    function renderTable() {
      const items = filteredTickets();
      ticketsTable.innerHTML = '';
      if (!items.length) {
        ticketsTable.innerHTML = `<tr><td colspan="8"><div class="empty"><h3>Sin resultados</h3><p>No hay incidencias que coincidan con los filtros actuales.</p></div></td></tr>`;
        return;
      }

      items.forEach(ticket => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
          <td>#${ticket.id}</td>
          <td>${ticket.puesto}</td>
          <td>${ticket.description}</td>
          <td><span class="priority ${priorityClass(ticket.priority)}">${ticket.priority}</span></td>
          <td><span class="badge ${stateClass(ticket.status)}">${ticket.status}</span></td>
          <td>${ticket.date}</td>
          <td>${ticket.assignedTo}</td>
          <td>
            <div class="action-row">
              <button class="tiny-btn" data-action="activa" data-id="${ticket.id}">Activa</button>
              <button class="tiny-btn" data-action="curso" data-id="${ticket.id}">En curso</button>
              <button class="tiny-btn" data-action="resuelta" data-id="${ticket.id}">Resuelta</button>
            </div>
          </td>
        `;
        ticketsTable.appendChild(tr);
      });
    }

    function renderPuestoList() {
      const puesto = currentUser?.role === 'puesto' ? currentUser.username : puestoInput.value.trim();
      const items = tickets.filter(ticket => !puesto || ticket.puesto === puesto).sort((a, b) => b.id - a.id);
      puestoList.innerHTML = '';
      if (!items.length) {
        puestoList.innerHTML = `<div class="empty"><h3>Sin incidencias</h3><p>Este puesto todavía no tiene incidencias registradas.</p></div>`;
        return;
      }

      items.slice(0, 6).forEach(ticket => {
        const article = document.createElement('article');
        article.className = 'ticket-card';
        article.innerHTML = `
          <div class="ticket-card-head">
            <div>
              <strong>#${ticket.id} · ${ticket.description}</strong>
              <p>${ticket.puesto} · ${ticket.date}</p>
            </div>
            <div class="status-inline">
              <span class="priority ${priorityClass(ticket.priority)}">${ticket.priority}</span>
              <span class="badge ${stateClass(ticket.status)}">${ticket.status}</span>
            </div>
          </div>
        `;
        puestoList.appendChild(article);
      });
    }

    function renderHistory() {
      const items = tickets.filter(ticket => ticket.status === 'Resuelta').sort((a, b) => b.id - a.id);
      historyList.innerHTML = '';
      if (!items.length) {
        historyList.innerHTML = `<div class="empty"><h3>No hay histórico todavía</h3><p>Las incidencias resueltas aparecerán aquí con su informe final.</p></div>`;
        return;
      }

      items.forEach(ticket => {
        const article = document.createElement('article');
        article.className = 'ticket-card';
        article.innerHTML = `
          <div class="ticket-card-head">
            <div>
              <strong>#${ticket.id} · ${ticket.puesto}</strong>
              <p>${ticket.description}</p>
            </div>
            <span class="badge ${stateClass(ticket.status)}">${ticket.status}</span>
          </div>
          <p><strong>Técnico:</strong> ${ticket.assignedTo}</p>
          <p><strong>Fecha:</strong> ${ticket.date}</p>
          <p><strong>Informe:</strong> ${ticket.report || 'Sin informe registrado en esta demo.'}</p>
        `;
        historyList.appendChild(article);
      });
    }

    function renderAll() {
      renderMetrics();
      renderTable();
      renderPuestoList();
      renderHistory();
    }

    function setPage(pageId) {
      pages.forEach(page => page.classList.remove('active'));
      document.getElementById(pageId).classList.add('active');
      navButtons.forEach(btn => btn.classList.toggle('active', btn.dataset.page === pageId));
      pageTitle.textContent = pageMeta[pageId].title;
      pageDescription.textContent = pageMeta[pageId].description;
      sidebar.classList.remove('open');
    }

    function setRoleUI() {
      document.querySelector('[data-page="puestoPage"]').style.display = 'block';
      document.querySelector('[data-page="stPage"]').style.display = 'block';
      document.querySelector('[data-page="historyPage"]').style.display = 'block';

      if (!currentUser) return;
      userChip.textContent = `${currentUser.role.toUpperCase()} · ${currentUser.username}`;

      if (currentUser.role === 'puesto') {
        puestoInput.value = currentUser.username;
        puestoInput.setAttribute('readonly', 'readonly');
        setPage('puestoPage');
      } else if (currentUser.role === 'st') {
        puestoInput.removeAttribute('readonly');
        setPage('stPage');
      } else {
        puestoInput.removeAttribute('readonly');
        setPage('dashboardPage');
      }
    }

    loginForm.addEventListener('submit', (e) => {
      e.preventDefault();
      const role = document.getElementById('accessRole').value;
      const username = document.getElementById('accessUser').value.trim();
      const password = document.getElementById('accessPassword').value.trim();
      if (!role || !username || !password) return;

      currentUser = { role, username };
      loginScreen.classList.add('hidden');
      appScreen.classList.remove('hidden');
      setRoleUI();
      renderAll();
      loginForm.reset();
    });

    logoutBtn.addEventListener('click', () => {
      currentUser = null;
      loginScreen.classList.remove('hidden');
      appScreen.classList.add('hidden');
      userChip.textContent = 'Sin sesión';
      setPage('dashboardPage');
    });

    ticketForm.addEventListener('submit', (e) => {
      e.preventDefault();
      const puesto = puestoInput.value.trim();
      const description = descriptionInput.value.trim() || 'Sin descripción';
      const priority = priorityInput.value;
      if (!puesto) {
        alert('Debes indicar un puesto.');
        return;
      }
      tickets.push({
        id: tickets.length ? Math.max(...tickets.map(t => t.id)) + 1 : 1,
        puesto,
        description,
        priority,
        status: 'Activa',
        date: fmtDate(),
        assignedTo: '-',
        report: ''
      });
      descriptionInput.value = '';
      priorityInput.value = 'Media';
      renderAll();
      setPage('stPage');
    });

    ticketsTable.addEventListener('click', (e) => {
      const btn = e.target.closest('[data-id]');
      if (!btn) return;
      const id = Number(btn.dataset.id);
      const ticket = tickets.find(t => t.id === id);
      if (!ticket) return;

      const actor = currentUser?.username || 'tecnico-demo';
      if (btn.dataset.action === 'activa') {
        ticket.status = 'Activa';
        ticket.assignedTo = '-';
      }
      if (btn.dataset.action === 'curso') {
        ticket.status = 'En curso';
        ticket.assignedTo = actor;
      }
      if (btn.dataset.action === 'resuelta') {
        ticket.status = 'Resuelta';
        ticket.assignedTo = actor;
        ticket.report = `Cierre registrado por ${actor}. En una versión completa se solicitaría informe técnico obligatorio.`;
      }
      renderAll();
    });

    [statusFilter, priorityFilter, searchInput].forEach(el => el.addEventListener('input', renderTable));
    puestoInput.addEventListener('input', renderPuestoList);

    navButtons.forEach(btn => {
      btn.addEventListener('click', () => setPage(btn.dataset.page));
    });

    pageLinks.forEach(btn => {
      btn.addEventListener('click', () => setPage(btn.dataset.pageLink));
    });

    mobileNavToggle.addEventListener('click', () => {
      sidebar.classList.toggle('open');
    });

    const themeBtn = document.querySelector('[data-theme-toggle]');
    function renderThemeBtn() {
      themeBtn.textContent = theme === 'dark' ? 'Modo claro' : 'Modo oscuro';
      themeBtn.setAttribute('aria-label', theme === 'dark' ? 'Cambiar a modo claro' : 'Cambiar a modo oscuro');
    }
    renderThemeBtn();
    themeBtn.addEventListener('click', () => {
      theme = theme === 'dark' ? 'light' : 'dark';
      document.documentElement.setAttribute('data-theme', theme);
      renderThemeBtn();
    });

    renderAll();