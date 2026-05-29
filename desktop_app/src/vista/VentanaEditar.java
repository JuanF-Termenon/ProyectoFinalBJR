package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.st.models.Informe;
import com.st.models.Usuario;
import com.st.repositories.IncidenciaRepository;
import com.st.repositories.InformeRepository;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaEditar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel modelo;
	private int filaSeleccionada;
	private JComboBox<String> comboBox;
	private Usuario user;
	private JTextArea txtInforme;
	private VentanaPrincipal ventana;

	public VentanaEditar(VentanaPrincipal ventana, DefaultTableModel modelo, int filaSeleccionada, Usuario user) {
		setTitle("BJR Technician Services");
		this.ventana = ventana;
		this.modelo = modelo;
		this.filaSeleccionada = filaSeleccionada;
		this.user = user;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 620);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		panel.setBounds(-5, 0, 553, 70);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Detalle de Incidencia");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setBounds(160, 15, 260, 45);
		panel.add(lblNewLabel);

		int idIncidencia = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 0).toString());
		String descripcion = modelo.getValueAt(filaSeleccionada, 1).toString();
		String departamento = modelo.getValueAt(filaSeleccionada, 2).toString();
		String prioridad = modelo.getValueAt(filaSeleccionada, 3).toString();
		String estado = modelo.getValueAt(filaSeleccionada, 4).toString();
		String reportadoPor = modelo.getValueAt(filaSeleccionada, 5).toString();
		String fechaCreacion = modelo.getValueAt(filaSeleccionada, 6).toString();
		try {
			Date fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCreacion.replaceAll("\\..*", ""));
			fechaCreacion = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fecha);
		} catch (Exception ex) {
		}

		JLabel lblId = new JLabel("ID: " + idIncidencia);
		lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblId.setBounds(20, 90, 150, 20);
		contentPane.add(lblId);

		JLabel lblEstadoLabel = new JLabel("Estado:");
		lblEstadoLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEstadoLabel.setBounds(340, 90, 60, 20);
		contentPane.add(lblEstadoLabel);

		comboBox = new JComboBox(new String[] {"ACTIVA", "EN_CURSO", "RESUELTA", "REABIERTA", "CANCELADA"});
		comboBox.setSelectedItem(estado);
		comboBox.setBounds(395, 90, 130, 22);
		contentPane.add(comboBox);

		JLabel lblDescLabel = new JLabel("Descripci\u00F3n:");
		lblDescLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDescLabel.setBounds(20, 130, 100, 20);
		contentPane.add(lblDescLabel);

		JTextArea txtDesc = new JTextArea(descripcion);
		txtDesc.setEditable(false);
		txtDesc.setLineWrap(true);
		txtDesc.setWrapStyleWord(true);
		txtDesc.setBackground(new Color(245, 245, 245));
		txtDesc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		JScrollPane scrollDesc = new JScrollPane(txtDesc);
		scrollDesc.setBounds(20, 155, 500, 60);
		contentPane.add(scrollDesc);

		JLabel lblRepLabel = new JLabel("Reportado por:");
		lblRepLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRepLabel.setBounds(20, 230, 110, 20);
		contentPane.add(lblRepLabel);

		JLabel lblRep = new JLabel(reportadoPor);
		lblRep.setForeground(new Color(80, 80, 80));
		lblRep.setBounds(130, 230, 180, 20);
		contentPane.add(lblRep);

		JLabel lblDeptLabel = new JLabel("Departamento:");
		lblDeptLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDeptLabel.setBounds(20, 260, 110, 20);
		contentPane.add(lblDeptLabel);

		JLabel lblDept = new JLabel(departamento);
		lblDept.setForeground(new Color(80, 80, 80));
		lblDept.setBounds(130, 260, 150, 20);
		contentPane.add(lblDept);

		JLabel lblFechaLabel = new JLabel("Fecha Creaci\u00F3n:");
		lblFechaLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFechaLabel.setBounds(20, 290, 110, 20);
		contentPane.add(lblFechaLabel);

		JLabel lblFecha = new JLabel(fechaCreacion);
		lblFecha.setForeground(new Color(80, 80, 80));
		lblFecha.setBounds(130, 290, 140, 20);
		contentPane.add(lblFecha);

		JLabel lblPriLabel = new JLabel("Prioridad:");
		lblPriLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPriLabel.setBounds(340, 260, 80, 20);
		contentPane.add(lblPriLabel);

		JComboBox<String> comboPrioridad = new JComboBox(new String[] {"BAJA", "MEDIA", "ALTA", "CRITICA"});
		comboPrioridad.setSelectedItem(prioridad);
		comboPrioridad.setBounds(395, 260, 130, 22);
		contentPane.add(comboPrioridad);

		JLabel lblPriValor = new JLabel(prioridad);
		lblPriValor.setForeground(new Color(80, 80, 80));
		lblPriValor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPriValor.setBounds(395, 260, 130, 22);
		lblPriValor.setVisible(false);
		contentPane.add(lblPriValor);

		boolean esTecnico = user.getRol().getIdRol() == 2;
		comboPrioridad.setVisible(!esTecnico);
		lblPriValor.setVisible(esTecnico);

		JLabel lblInformeLabel = new JLabel("Informe de Resoluci\u00F3n:");
		lblInformeLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInformeLabel.setBounds(20, 330, 160, 20);
		contentPane.add(lblInformeLabel);

		txtInforme = new JTextArea();
		txtInforme.setLineWrap(true);
		txtInforme.setWrapStyleWord(true);
		txtInforme.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtInforme.setEditable(false);
		txtInforme.setBackground(new Color(245, 245, 245));

		try {
			InformeRepository iRepo = new InformeRepository();
			Informe informe = iRepo.findByIdIncidencia(idIncidencia);
			if (informe != null) {
				txtInforme.setText(informe.getInforme());
			}
		} catch (SQLException ex) {
			txtInforme.setText("Error al cargar el informe: " + ex.getMessage());
		}

		JScrollPane scrollInforme = new JScrollPane(txtInforme);
		scrollInforme.setBounds(20, 355, 500, 100);
		contentPane.add(scrollInforme);

		boolean visibleInicial = estado.equals("RESUELTA");
		lblInformeLabel.setVisible(visibleInicial);
		scrollInforme.setVisible(visibleInicial);
		if (visibleInicial && txtInforme.getText().isEmpty()) {
			txtInforme.setEditable(true);
			txtInforme.setBackground(Color.WHITE);
		}

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					boolean esResuelta = e.getItem().equals("RESUELTA");
					lblInformeLabel.setVisible(esResuelta);
					scrollInforme.setVisible(esResuelta);
					if (esResuelta && txtInforme.getText().isEmpty()) {
						txtInforme.setText("");
						txtInforme.setEditable(true);
						txtInforme.setBackground(Color.WHITE);
					} else if (esResuelta) {
						txtInforme.setEditable(false);
						txtInforme.setBackground(new Color(245, 245, 245));
					}
				}
			}
		});

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null,
					"\u00BFSeguro que quieres eliminar la incidencia #" + idIncidencia + "?",
					"Confirmar eliminaci\u00F3n", JOptionPane.YES_NO_OPTION);
				if (confirm != JOptionPane.YES_OPTION) return;
				try {
					IncidenciaRepository repo = new IncidenciaRepository(user.getIdUsuario());
					repo.eliminar(idIncidencia);
					ventana.cargarIncidencias();
					JOptionPane.showMessageDialog(null, "Incidencia eliminada correctamente.");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error al eliminar: " + e1.getMessage());
				}
				dispose();
			}
		});
		btnEliminar.setBackground(new Color(200, 50, 50));
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEliminar.setBorderPainted(false);
		btnEliminar.setFocusPainted(false);
		btnEliminar.setBounds(144, 490, 126, 36);
		if (user.getRol().getIdRol() != 1) btnEliminar.setVisible(false);
		contentPane.add(btnEliminar);

		JButton btnGuardarCambios = new JButton("Guardar cambios");
		btnGuardarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        try {
		        	String nuevoEstado = comboBox.getSelectedItem().toString();
			        modelo.setValueAt(nuevoEstado, filaSeleccionada, 4);
		        	IncidenciaRepository repo = new IncidenciaRepository(user.getIdUsuario());
					if (nuevoEstado.equals("RESUELTA") && scrollInforme.isVisible()) {
						String textoInforme = txtInforme.getText().trim();
						if (!textoInforme.isEmpty()) {
							InformeRepository iRepo = new InformeRepository(user.getIdUsuario());
							iRepo.guardarInforme(idIncidencia, user.getIdUsuario(), textoInforme);
						}
					}
					repo.actualizarEstado(idIncidencia, nuevoEstado, null);
					if (user.getRol().getIdRol() == 1) {
						String nuevaPrioridad = comboPrioridad.getSelectedItem().toString();
						modelo.setValueAt(nuevaPrioridad, filaSeleccionada, 3);
						repo.actualizarPrioridad(idIncidencia, nuevaPrioridad);
					}
					ventana.cargarIncidencias();
			        JOptionPane.showMessageDialog(null,
			            "Cambios guardados correctamente.",
			            "Guardado", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					String msg = e1.getMessage();
					if (msg.contains("informe")) {
						JOptionPane.showMessageDialog(null,
							"No se puede resolver la incidencia sin un informe previo.",
							"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "ERROR: " + msg);
					}
				}
		        dispose();
			}
		});
		btnGuardarCambios.setBackground(new Color(0, 102, 153));
		btnGuardarCambios.setForeground(Color.WHITE);
		btnGuardarCambios.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGuardarCambios.setBorderPainted(false);
		btnGuardarCambios.setFocusPainted(false);
		btnGuardarCambios.setBounds(290, 490, 126, 36);
		contentPane.add(btnGuardarCambios);

		// Técnico ST: botón "Asignarme" (auto-asignación)
		if (user.getRol().getIdRol() == 2) {
			JButton btnAsignar = new JButton("Asignarme");
			btnAsignar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						IncidenciaRepository r = new IncidenciaRepository(user.getIdUsuario());
						r.asignarTecnico(idIncidencia, user.getIdUsuario());
						ventana.cargarIncidencias();
						JOptionPane.showMessageDialog(null, "Incidencia #" + idIncidencia + " asignada correctamente.");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error al asignar: " + e1.getMessage());
					}
				}
			});
			btnAsignar.setBackground(new Color(0, 102, 153));
			btnAsignar.setForeground(Color.WHITE);
			btnAsignar.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnAsignar.setBorderPainted(false);
			btnAsignar.setFocusPainted(false);
			btnAsignar.setBounds(10, 490, 126, 36);
			contentPane.add(btnAsignar);
		}

		// Jefe ST: botón "Ver historial"
		if (user.getRol().getIdRol() == 1) {
			JButton btnHistorial = new JButton("Ver historial");
			btnHistorial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaHistorial vh = new VentanaHistorial(idIncidencia);
					vh.setVisible(true);
				}
			});
			btnHistorial.setBackground(new Color(0, 102, 153));
			btnHistorial.setForeground(Color.WHITE);
			btnHistorial.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnHistorial.setBorderPainted(false);
			btnHistorial.setFocusPainted(false);
			btnHistorial.setBounds(10, 490, 126, 36);
			contentPane.add(btnHistorial);
		}
	}
}
