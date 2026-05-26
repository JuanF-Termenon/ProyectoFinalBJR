package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.st.models.Usuario;
import com.st.repositories.IncidenciaRepository;
import com.st.repositories.PuestoRepository;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class VentanaIncidencia extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox comboDepartamento;
	private JTextField inputDescripcion;
	private JComboBox comboPrioridad;
	private VentanaPrincipal ventana;
	private DefaultTableModel modelo;
	private Usuario user;

	public VentanaIncidencia(VentanaPrincipal ventana, DefaultTableModel modelo, Usuario user) {
		setTitle("BJR Technician Services");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(410, 100, 450, 430);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.ventana = ventana;
		this.modelo = modelo;
		this.user = user;
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		panel.setBounds(0, 0, 436, 64);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nueva Incidencia");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(131, 11, 199, 42);
		panel.add(lblNewLabel);
		
		JLabel lblDepartamento = new JLabel("Departamento *");
		lblDepartamento.setBounds(20, 72, 100, 14);
		contentPane.add(lblDepartamento);
		
		comboDepartamento = new JComboBox();
		try {
			PuestoRepository pRepo = new PuestoRepository();
			for (var d : pRepo.findAllDepartamentos()) {
				comboDepartamento.addItem(d);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this,
				"Error al cargar departamentos: " + ex.getMessage(),
				"Error BD", JOptionPane.ERROR_MESSAGE);
		}
		comboDepartamento.setBounds(20, 92, 183, 31);
		contentPane.add(comboDepartamento);
		
		JLabel lblPrioridad = new JLabel("Prioridad *");
		lblPrioridad.setBounds(231, 72, 81, 14);
		contentPane.add(lblPrioridad);
		
		JLabel lblDescripcion = new JLabel("Descripcion *");
		lblDescripcion.setBounds(20, 140, 81, 14);
		contentPane.add(lblDescripcion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 165, 394, 46);
		contentPane.add(scrollPane);
		
		inputDescripcion = new JTextField();
		scrollPane.setViewportView(inputDescripcion);
		inputDescripcion.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setBorderPainted(false);
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBounds(153, 300, 88, 46);
		contentPane.add(btnCancelar);
		
		comboPrioridad = new JComboBox(new String[] {"BAJA", "MEDIA", "ALTA", "CRITICA"});
		comboPrioridad.setBounds(231, 92, 183, 31);
		contentPane.add(comboPrioridad);
		
		JButton btnGuardarIncidencia = new JButton("Guardar Incidencia");
		btnGuardarIncidencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardar();
					ventana.cargarIncidencias();
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(VentanaIncidencia.this,
						"Error al guardar: " + e1.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnGuardarIncidencia.setBackground(new Color(0, 102, 153));
		btnGuardarIncidencia.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGuardarIncidencia.setBorderPainted(false);
		btnGuardarIncidencia.setFocusPainted(false);
		btnGuardarIncidencia.setForeground(new Color(255, 255, 255));
		btnGuardarIncidencia.setBounds(263, 300, 151, 46);
		contentPane.add(btnGuardarIncidencia);
		
	}
	
	public void guardar() throws SQLException {
			String descripcion = inputDescripcion.getText();
			String prioridad = (String) comboPrioridad.getSelectedItem();
			String deptSeleccionado = (String) comboDepartamento.getSelectedItem();

			PuestoRepository pRepo = new PuestoRepository();
			for (var p : pRepo.findAll()) {
				if (p.getDepartamento().equals(deptSeleccionado)) {
					IncidenciaRepository repo = new IncidenciaRepository(user.getIdUsuario());
					repo.reportarIncidencia(descripcion, prioridad, p.getIdPuesto(), user.getIdUsuario());
					return;
				}
			}
	}
}
