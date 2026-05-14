package vista;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.st.models.Usuario;
import com.st.repositories.IncidenciaRepository;

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

public class VentanaIncidencia extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputTitulo;
	private JTextField inputDpt;
	private JTextField inputDescripcion;
	private JTextField inputReportadoPor;
	private JComboBox comboPrioridad;
	private VentanaPrincipal ventana;
	private DefaultTableModel modelo;
	private Usuario user;

	public VentanaIncidencia(VentanaPrincipal ventana, DefaultTableModel modelo, Usuario user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(410, 100, 450, 520);
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
		
		JLabel lblNewLabel_1 = new JLabel("Titulo *");
		lblNewLabel_1.setBounds(20, 72, 48, 14);
		contentPane.add(lblNewLabel_1);
		
		inputTitulo = new JTextField();
		inputTitulo.setBounds(20, 97, 394, 31);
		contentPane.add(inputTitulo);
		inputTitulo.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Departamento *");
		lblNewLabel_1_1.setBounds(20, 139, 81, 14);
		contentPane.add(lblNewLabel_1_1);
		
		inputDpt = new JTextField();
		inputDpt.setColumns(10);
		inputDpt.setBounds(20, 164, 183, 31);
		contentPane.add(inputDpt);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Prioridad *");
		lblNewLabel_1_1_1.setBounds(231, 139, 81, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Descripcion *");
		lblNewLabel_1_1_2.setBounds(20, 206, 81, 14);
		contentPane.add(lblNewLabel_1_1_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 231, 394, 46);
		contentPane.add(scrollPane);
		
		inputDescripcion = new JTextField();
		scrollPane.setViewportView(inputDescripcion);
		inputDescripcion.setColumns(10);
		
		JLabel lblNewLabel_1_1_2_1 = new JLabel("Reportado por *");
		lblNewLabel_1_1_2_1.setBounds(20, 288, 81, 14);
		contentPane.add(lblNewLabel_1_1_2_1);
		
		inputReportadoPor = new JTextField();
		inputReportadoPor.setColumns(10);
		inputReportadoPor.setBounds(20, 313, 394, 31);
		contentPane.add(inputReportadoPor);
		
		JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Categoria *");
		lblNewLabel_1_1_2_1_1.setBounds(20, 355, 81, 14);
		contentPane.add(lblNewLabel_1_1_2_1_1);
		
		JComboBox comboCategoria = new JComboBox(new String[] {"HARDWARE", "SOFTWARE", "RED", "SEGURIDAD", "OTRO"});
	
		comboCategoria.setBounds(20, 380, 394, 32);
		contentPane.add(comboCategoria);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaIncidencia.this.setVisible(false);
			}
		});
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setBorderPainted(false);
		btnCancelar.setFocusPainted(false);
		btnCancelar.setBounds(153, 426, 88, 46);
		contentPane.add(btnCancelar);
		
		comboPrioridad = new JComboBox(new String[] {"ALTA", "MEDIA", "BAJA"});
		comboPrioridad.setBounds(231, 164, 183, 31);
		contentPane.add(comboPrioridad);
		
		JButton btnGuardarIncidencia = new JButton("Guardar Incidencia");
		btnGuardarIncidencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardar();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnGuardarIncidencia.setBackground(new Color(0, 102, 153));
		btnGuardarIncidencia.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGuardarIncidencia.setBorderPainted(false);
		btnGuardarIncidencia.setFocusPainted(false);
		btnGuardarIncidencia.setForeground(new Color(255, 255, 255));
		btnGuardarIncidencia.setBounds(263, 426, 151, 46);
		contentPane.add(btnGuardarIncidencia);
		
	}
	
	public void guardar() throws SQLException {
			String descripcion = inputDescripcion.getText();
			String prioridad = (String) comboPrioridad.getSelectedItem();
			
			IncidenciaRepository repo = new IncidenciaRepository();
			repo.reportarIncidencia(descripcion, prioridad, user.getRol().getIdRol(), user.getIdUsuario());

	}
}
