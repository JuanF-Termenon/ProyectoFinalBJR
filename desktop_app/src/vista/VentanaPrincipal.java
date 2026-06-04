package vista;

import com.st.repositories.IncidenciaRepository;
import com.st.repositories.PuestoRepository;
import com.st.models.Incidencia;
import com.st.models.Usuario;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel modelo;
    private int filaSeleccionada;
    private Usuario user;

    private IncidenciaRepository repo;
    private JLabel contadorActivas = new JLabel("0");
    private JLabel contadorEnCurso = new JLabel("0");
    private JLabel contadorResueltas = new JLabel("0");
    private JComboBox comboEstado = new JComboBox(new String[] {"", "ACTIVA", "EN_CURSO", "RESUELTA", "REABIERTA", "CANCELADA"});
    private JComboBox comboPrioridad = new JComboBox(new String[]{"", "BAJA", "MEDIA", "ALTA", "CRITICA"});
    private JComboBox comboDept = new JComboBox();
    private JCheckBox chkSoloMisIncidencias = new JCheckBox("Solo mis incidencias");
    private JButton btnAnadir = new JButton("A\u00F1adir incidencia");
    private JButton btnRefrescar = new JButton("Refrescar tabla");
    private JButton btnGestionar = new JButton("Gestionar incidencia");
    private JButton btnExportar = new JButton("Exportar CSV");
    private JButton btnEstadisticas = new JButton("Ver estad\u00EDsticas");

    public VentanaPrincipal(Login login, Usuario user) {
        setTitle("ST Connect");
        this.user = user;
        this.repo = new IncidenciaRepository(user.getIdUsuario());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(150, 50, 1096, 642);
        this.setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 153));
        panel.setBounds(10, 11, 1062, 76);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("GESTOR DE INCIDENCIAS");
        lblTitulo.setForeground(new Color(255, 255, 255));
        lblTitulo.setBackground(new Color(255, 255, 255));
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitulo.setBounds(24, 21, 262, 33);
        panel.add(lblTitulo);

        String bienvenida = user.getNombreVisible();
        if (user.getRol().getIdRol() == 3) {
            try {
                PuestoRepository pRepo = new PuestoRepository();
                com.st.models.Puesto puesto = pRepo.findByUsuarioId(user.getIdUsuario());
                if (puesto != null) bienvenida += " (" + puesto.getCodigoPuesto() + ")";
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        JLabel lblBienvenida = new JLabel(bienvenida);
        lblBienvenida.setForeground(new Color(200, 200, 200));
        lblBienvenida.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblBienvenida.setBounds(296, 33, 200, 14);
        panel.add(lblBienvenida);

        JLabel lblActivas = new JLabel("ACTIVAS");
        lblActivas.setForeground(Color.WHITE);
        lblActivas.setBounds(783, 11, 72, 16);
        panel.add(lblActivas);

        JLabel lblEnCurso = new JLabel("EN CURSO");
        lblEnCurso.setForeground(Color.WHITE);
        lblEnCurso.setBounds(873, 11, 83, 16);
        panel.add(lblEnCurso);

        JLabel lblResueltas = new JLabel("RESUELTAS");
        lblResueltas.setForeground(Color.WHITE);
        lblResueltas.setBounds(966, 11, 86, 16);
        panel.add(lblResueltas);

        contadorActivas.setForeground(new Color(204, 0, 0));
        contadorActivas.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contadorActivas.setBounds(803, 38, 38, 25);
        panel.add(contadorActivas);

        contadorEnCurso.setForeground(new Color(204, 255, 0));
        contadorEnCurso.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contadorEnCurso.setBounds(898, 38, 38, 25);
        panel.add(contadorEnCurso);

        contadorResueltas.setForeground(new Color(0, 204, 0));
        contadorResueltas.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contadorResueltas.setBounds(992, 38, 38, 25);
        panel.add(contadorResueltas);

        boolean esOperario = user.getRol().getIdRol() == 3;

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(10, 113, 48, 14);
        lblEstado.setVisible(!esOperario);
        contentPane.add(lblEstado);

        JLabel lblPrioridad = new JLabel("Prioridad:");
        lblPrioridad.setBounds(174, 113, 60, 14);
        lblPrioridad.setVisible(!esOperario);
        contentPane.add(lblPrioridad);

        JLabel lblDept = new JLabel("Dept:");
        lblDept.setBounds(364, 113, 36, 14);
        lblDept.setVisible(!esOperario);
        contentPane.add(lblDept);

        comboEstado.setBounds(55, 109, 99, 22);
        comboEstado.setVisible(!esOperario);
        contentPane.add(comboEstado);

        comboPrioridad.setBounds(234, 109, 99, 22);
        comboPrioridad.setVisible(!esOperario);
        contentPane.add(comboPrioridad);

        comboDept.addItem("");
        try {
            PuestoRepository pRepo = new PuestoRepository();
            for (var d : pRepo.findAllDepartamentos()) comboDept.addItem(d);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        comboDept.setBounds(399, 109, 99, 22);
        comboDept.setVisible(!esOperario);
        contentPane.add(comboDept);

        chkSoloMisIncidencias.setBounds(10, 148, 150, 22);
        chkSoloMisIncidencias.setVisible(user.getRol().getIdRol() == 2);
        contentPane.add(chkSoloMisIncidencias);

        btnRefrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modelo.setRowCount(0);
                String filtroEstado = (String) comboEstado.getSelectedItem();
                String filtroPrioridad = (String) comboPrioridad.getSelectedItem();
                String filtroDept = (String) comboDept.getSelectedItem();
                try {
                    int totalActivas = 0, totalEnCurso = 0, totalResueltas = 0;
                    ArrayList<Incidencia> lista;
                    if (user.getRol().getIdRol() == 2 && chkSoloMisIncidencias.isSelected()) {
                        lista = repo.findByAsignadoA(user.getIdUsuario());
                    } else {
                        lista = repo.findAll();
                    }
                    for (Incidencia i : lista) {
                        if (!filtroEstado.isEmpty() && !i.getEstado().equalsIgnoreCase(filtroEstado)) continue;
                        if (!filtroPrioridad.isEmpty() && !i.getPrioridad().equalsIgnoreCase(filtroPrioridad)) continue;
                        if (!filtroDept.isEmpty() && !i.getNombreDepartamento().equalsIgnoreCase(filtroDept)) continue;
                        String asignado = i.getNombreAsignado() != null ? i.getNombreAsignado() : "";
                        modelo.addRow(new Object[] {
                            i.getIdIncidencia(), i.getDescripcion(), i.getNombreDepartamento(),
                            i.getPrioridad(), i.getEstado(), i.getNombreReportadoPor(), i.getFechaCreacion(),
                            asignado
                        });
                        if (i.getEstado().equalsIgnoreCase("ACTIVA") || i.getEstado().equalsIgnoreCase("REABIERTA")) totalActivas++;
                        else if (i.getEstado().equalsIgnoreCase("EN_CURSO")) totalEnCurso++;
                        else if (i.getEstado().equalsIgnoreCase("RESUELTA")) totalResueltas++;
                    }
                    contadorActivas.setText(String.valueOf(totalActivas));
                    contadorEnCurso.setText(String.valueOf(totalEnCurso));
                    contadorResueltas.setText(String.valueOf(totalResueltas));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnRefrescar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnRefrescar.setBackground(new Color(0, 102, 153));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setBounds(596, 104, 145, 32);
        btnRefrescar.setBorderPainted(false);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setVisible(!esOperario);
        contentPane.add(btnRefrescar);

        btnAnadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaIncidencia incidencia = new VentanaIncidencia(VentanaPrincipal.this, modelo, user);
                incidencia.setVisible(true);
            }
        });
        btnAnadir.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnAnadir.setBackground(new Color(50, 205, 50));
        btnAnadir.setForeground(Color.WHITE);
        btnAnadir.setBorderPainted(false);
        btnAnadir.setFocusPainted(false);
        btnAnadir.setBounds(763, 104, 145, 32);
        btnAnadir.setVisible(user.getRol().getIdRol() != 2);
        contentPane.add(btnAnadir);

        String[] columnas = {"ID", "Descripcion", "Departamento", "Prioridad", "Estado", "Reportado por", "Fecha", "Asignado a"};
        modelo = new DefaultTableModel(null, columnas);

        cargarIncidencias();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 210, 1062, 387);
        contentPane.add(scrollPane);

        if (esOperario) scrollPane.setBounds(10, 160, 1062, 387);

        table = new JTable(modelo);
        scrollPane.setViewportView(table);
        table.setRowHeight(35);

        btnGestionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaVista = table.getSelectedRow();
                if (filaVista == -1) {
                    JOptionPane.showMessageDialog(null,
                        "Por favor, selecciona una fila de la tabla para editar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                filaSeleccionada = table.convertRowIndexToModel(filaVista);
                VentanaEditar ve = new VentanaEditar(VentanaPrincipal.this, modelo, filaSeleccionada, user);
                ve.setVisible(true);
            }
        });
        btnGestionar.setForeground(Color.WHITE);
        btnGestionar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnGestionar.setFocusPainted(false);
        btnGestionar.setBorderPainted(false);
        btnGestionar.setBackground(new Color(0, 102, 153));
        btnGestionar.setBounds(927, 104, 145, 32);
        btnGestionar.setVisible(user.getRol().getIdRol() == 1 || user.getRol().getIdRol() == 2);
        contentPane.add(btnGestionar);

        btnEstadisticas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaEstadisticas ve = new VentanaEstadisticas(user.getIdUsuario());
                ve.setVisible(true);
            }
        });
        btnEstadisticas.setForeground(Color.WHITE);
        btnEstadisticas.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnEstadisticas.setFocusPainted(false);
        btnEstadisticas.setBorderPainted(false);
        btnEstadisticas.setBackground(new Color(0, 102, 153));
        btnEstadisticas.setBounds(763, 145, 145, 32);
        btnEstadisticas.setVisible(user.getRol().getIdRol() == 1);
        contentPane.add(btnEstadisticas);

        btnExportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportarCSV();
            }
        });
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnExportar.setFocusPainted(false);
        btnExportar.setBorderPainted(false);
        btnExportar.setBackground(new Color(0, 102, 153));
        btnExportar.setBounds(927, 143, 145, 32);
        btnExportar.setVisible(user.getRol().getIdRol() == 1);
        contentPane.add(btnExportar);
    }

    public void exportarCSV() {
        try {
            javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
            fc.setSelectedFile(new java.io.File("incidencias_export.csv"));
            int option = fc.showSaveDialog(this);
            if (option != javax.swing.JFileChooser.APPROVE_OPTION) return;
            java.io.File archivo = fc.getSelectedFile();
            try (FileWriter fw = new FileWriter(archivo)) {
                for (int c = 0; c < modelo.getColumnCount(); c++) {
                    if (c > 0) fw.write(',');
                    fw.write(modelo.getColumnName(c));
                }
                fw.write("\r\n");
                for (int r = 0; r < modelo.getRowCount(); r++) {
                    for (int c = 0; c < modelo.getColumnCount(); c++) {
                        if (c > 0) fw.write(',');
                        Object val = modelo.getValueAt(r, c);
                        if (val != null) {
                            String s = val.toString().replace("\"", "\"\"");
                            if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
                                fw.write('"'); fw.write(s); fw.write('"');
                            } else {
                                fw.write(s);
                            }
                        }
                    }
                    fw.write("\r\n");
                }
            }
            JOptionPane.showMessageDialog(this, "Exportado correctamente: " + archivo.getName());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage());
        }
    }

    public void cargarIncidencias() {
        modelo.setRowCount(0);
        try {
            int totalActivas = 0, totalEnCurso = 0, totalResueltas = 0;
            ArrayList<Incidencia> lista;
            if (user.getRol().getIdRol() == 3) {
                lista = repo.findByUsuarioCreador(user.getIdUsuario());
            } else if (user.getRol().getIdRol() == 2 && chkSoloMisIncidencias.isSelected()) {
                lista = repo.findByAsignadoA(user.getIdUsuario());
            } else {
                lista = repo.findAll();
            }
            for (Incidencia i : lista) {
                String asignado = i.getNombreAsignado() != null ? i.getNombreAsignado() : "";
                modelo.addRow(new Object[] {
                    i.getIdIncidencia(), i.getDescripcion(), i.getNombreDepartamento(),
                    i.getPrioridad(), i.getEstado(), i.getNombreReportadoPor(), i.getFechaCreacion(),
                    asignado
                });
                if (i.getEstado().equalsIgnoreCase("ACTIVA") || i.getEstado().equalsIgnoreCase("REABIERTA")) totalActivas++;
                else if (i.getEstado().equalsIgnoreCase("EN_CURSO")) totalEnCurso++;
                else if (i.getEstado().equalsIgnoreCase("RESUELTA")) totalResueltas++;
            }
            contadorActivas.setText(String.valueOf(totalActivas));
            contadorEnCurso.setText(String.valueOf(totalEnCurso));
            contadorResueltas.setText(String.valueOf(totalResueltas));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
