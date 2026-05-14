package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

public class VentanaEditar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel modelo;
	private int filaSeleccionada;
	private JLabel lblID;
	private JLabel lblTitulo;
	private JLabel lblDescripcion;
	private JLabel lblDepartamento;
	private JLabel lblPrioridad;
	private JLabel lblCategoria;
	private JLabel lblReportadoPor;
	private JLabel lblFechaCreacion;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public VentanaEditar(DefaultTableModel modelo, int filaSeleccionada) {
		this.modelo = modelo;
		this.filaSeleccionada = filaSeleccionada;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 548);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		panel.setBounds(0, 0, 478, 67);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Detalle de Incidencia");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(130, 11, 251, 45);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setBounds(10, 78, 48, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblID = new JLabel("New label");
		lblID.setBounds(10, 108, 48, 14);
		contentPane.add(lblID);
		
		JLabel lblNewLabel_2 = new JLabel("Titulo");
		lblNewLabel_2.setBounds(10, 150, 48, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblTitulo = new JLabel("New label");
		lblTitulo.setBounds(10, 175, 48, 14);
		contentPane.add(lblTitulo);
		
		JLabel lblNewLabel_3 = new JLabel("Departamento");
		lblNewLabel_3.setBounds(10, 212, 48, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblDepartamento = new JLabel("New label");
		lblDepartamento.setBounds(10, 231, 48, 14);
		contentPane.add(lblDepartamento);
		
		JLabel lblNewLabel_4 = new JLabel("Prioridad");
		lblNewLabel_4.setBounds(10, 266, 48, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblPrioridad = new JLabel("New label");
		lblPrioridad.setBounds(10, 288, 48, 14);
		contentPane.add(lblPrioridad);
		
		JLabel lblNewLabel_5 = new JLabel("Descripcion");
		lblNewLabel_5.setBounds(10, 324, 48, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblDescripcion = new JLabel("New label");
		lblDescripcion.setBounds(10, 349, 48, 14);
		contentPane.add(lblDescripcion);
		
		JLabel lblNewLabel_7 = new JLabel("Reportado por");
		lblNewLabel_7.setBounds(10, 386, 48, 14);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblReportadoPor = new JLabel("");
		lblReportadoPor.setBounds(10, 410, 48, 14);
		contentPane.add(lblReportadoPor);
		
		JLabel lblNewLabel_6 = new JLabel("Fecha Creación");
		lblNewLabel_6.setBounds(10, 441, 48, 14);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblFechaCreacion = new JLabel("New label");
		lblFechaCreacion.setBounds(10, 466, 48, 14);
		contentPane.add(lblFechaCreacion);
		
		JLabel lblNewLabel_8 = new JLabel("Categoria");
		lblNewLabel_8.setBounds(292, 192, 48, 14);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblCategoria = new JLabel("New label");
		lblCategoria.setBounds(292, 212, 48, 14);
		contentPane.add(lblCategoria);
		
		JLabel lblNewLabel_9 = new JLabel("Estado");
		lblNewLabel_9.setBounds(292, 266, 48, 14);
		contentPane.add(lblNewLabel_9);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(292, 291, 114, 22);
		contentPane.add(scrollPane);
		
		JComboBox comboBox = new JComboBox(new String[] {"ABIERTA", "EN_PROGRESO", "RESUELTA", "CERRADA"});
		scrollPane.setViewportView(comboBox);
		
		cargarLabels();

	}
	
	public void cargarLabels() {
	    
	    lblID.setText(modelo.getValueAt(filaSeleccionada, 0).toString());
	    lblTitulo.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
	    lblDepartamento.setText(modelo.getValueAt(filaSeleccionada, 2).toString());
	    lblPrioridad.setText(modelo.getValueAt(filaSeleccionada, 3).toString());
	    lblCategoria.setText(modelo.getValueAt(filaSeleccionada, 4).toString());
	    lblReportadoPor.setText(modelo.getValueAt(filaSeleccionada, 5).toString());
	    lblFechaCreacion.setText(modelo.getValueAt(filaSeleccionada, 6).toString());
	    
	}
}
