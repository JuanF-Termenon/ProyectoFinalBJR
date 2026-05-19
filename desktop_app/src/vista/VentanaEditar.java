package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.st.repositories.IncidenciaRepository;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

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
	private JComboBox<String> comboBox;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public VentanaEditar(DefaultTableModel modelo, int filaSeleccionada) {
		setTitle("BJR Technician Services");
		this.modelo = modelo;
		this.filaSeleccionada = filaSeleccionada;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		lblNewLabel_1.setBounds(10, 89, 48, 14);
		contentPane.add(lblNewLabel_1);
		
		lblID = new JLabel("New label");
		lblID.setForeground(new Color(128, 128, 128));
		lblID.setBounds(20, 113, 266, 14);
		contentPane.add(lblID);
		
		JLabel lblNewLabel_2 = new JLabel("Titulo");
		lblNewLabel_2.setBounds(10, 150, 48, 14);
		contentPane.add(lblNewLabel_2);
		
		lblTitulo = new JLabel("New label");
		lblTitulo.setForeground(new Color(128, 128, 128));
		lblTitulo.setBounds(20, 175, 333, 14);
		contentPane.add(lblTitulo);
		
		JLabel lblNewLabel_3 = new JLabel("Departamento");
		lblNewLabel_3.setBounds(10, 212, 118, 14);
		contentPane.add(lblNewLabel_3);
		
		lblDepartamento = new JLabel("New label");
		lblDepartamento.setForeground(new Color(128, 128, 128));
		lblDepartamento.setBounds(20, 237, 266, 14);
		contentPane.add(lblDepartamento);
		
		JLabel lblNewLabel_4 = new JLabel("Prioridad");
		lblNewLabel_4.setBounds(10, 266, 76, 14);
		contentPane.add(lblNewLabel_4);
		
		lblPrioridad = new JLabel("New label");
		lblPrioridad.setForeground(new Color(128, 128, 128));
		lblPrioridad.setBounds(20, 288, 266, 14);
		contentPane.add(lblPrioridad);
		
		JLabel lblNewLabel_5 = new JLabel("Descripcion");
		lblNewLabel_5.setBounds(10, 324, 76, 14);
		contentPane.add(lblNewLabel_5);
		
		lblDescripcion = new JLabel("New label");
		lblDescripcion.setForeground(new Color(128, 128, 128));
		lblDescripcion.setBounds(20, 349, 266, 14);
		contentPane.add(lblDescripcion);
		
		lblReportadoPor = new JLabel("");
		lblReportadoPor.setForeground(new Color(128, 128, 128));
		lblReportadoPor.setBounds(20, 410, 266, 14);
		contentPane.add(lblReportadoPor);
		
		JLabel lblNewLabel_6 = new JLabel("Fecha Creación");
		lblNewLabel_6.setBounds(10, 385, 76, 14);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_8 = new JLabel("Categoria");
		lblNewLabel_8.setBounds(292, 212, 106, 14);
		contentPane.add(lblNewLabel_8);
		
		lblCategoria = new JLabel("New label");
		lblCategoria.setForeground(new Color(128, 128, 128));
		lblCategoria.setBounds(302, 231, 156, 14);
		contentPane.add(lblCategoria);
		
		JLabel lblNewLabel_9 = new JLabel("Estado");
		lblNewLabel_9.setBounds(292, 266, 48, 14);
		contentPane.add(lblNewLabel_9);
		
		comboBox = new JComboBox(new String[] {"ABIERTA", "EN_PROGRESO", "RESUELTA", "CERRADA"});
		comboBox.setBounds(290, 295, 118, 22);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("Eliminar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelo.removeRow(filaSeleccionada);
				
				JOptionPane.showMessageDialog(null, "Se ha eliminado la incidencia correctamente.");
				
				dispose();
			}
		});
		btnNewButton.setBounds(190, 464, 126, 36);
		contentPane.add(btnNewButton);
		
		JButton btnGuardarCambios = new JButton("Guardar cambios");
		btnGuardarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        try {
		        	String nuevoEstado = comboBox.getSelectedItem().toString();
			        
			        modelo.setValueAt(nuevoEstado, filaSeleccionada, 4);
			        
			        JOptionPane.showMessageDialog(null, 
			            "El estado de la incidencia se ha actualizado correctamente.", 
			            "Cambios Guardados", JOptionPane.INFORMATION_MESSAGE);
			        
		        	IncidenciaRepository incidencia = new IncidenciaRepository();
					incidencia.actualizarEstado(filaSeleccionada, nuevoEstado, null);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "ERROR: No se ha podido actualizar la incidencia.");
				}

		        dispose();
			}
		});
		btnGuardarCambios.setBounds(332, 464, 126, 36);
		contentPane.add(btnGuardarCambios);
		
		cargarLabels();

	}
	
	public void cargarLabels() {
	    
		lblID.setText(modelo.getValueAt(filaSeleccionada, 0).toString());
	    lblTitulo.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
	    lblDepartamento.setText(modelo.getValueAt(filaSeleccionada, 2).toString());
	    lblPrioridad.setText(modelo.getValueAt(filaSeleccionada, 3).toString());
	    lblDescripcion.setText(modelo.getValueAt(filaSeleccionada, 4).toString());
	    lblCategoria.setText(modelo.getValueAt(filaSeleccionada, 5).toString());
	    lblReportadoPor.setText(modelo.getValueAt(filaSeleccionada, 6).toString());
	    
	}
}
