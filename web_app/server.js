const express = require('express');
const { Pool } = require('pg');
const path = require('path');

const app = express();
const pool = new Pool({
  host: 'localhost',
  port: 5432,
  database: 'SistemaGestor',
  user: 'postgres',
  password: 'pass'
});

app.use(express.static(path.join(__dirname)));

app.get('/api/estados', async (req, res) => {
  try {
    const mapa = { cola: 'ACTIVA', curso: 'EN_CURSO', resuelta: 'RESUELTA' };
    const resultado = {};

    for (const [key, estado] of Object.entries(mapa)) {
      const { rows: countRows } = await pool.query(
        'SELECT COUNT(*)::int as total FROM incidencia WHERE estado = $1', [estado]
      );
      resultado[key] = { total: countRows[0].total };
    }

    res.json(resultado);
  } catch (err) {
    console.error('Error:', err);
    res.status(500).json({ error: err.message });
  }
});

const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Servidor web: http://localhost:${PORT}`);
});
