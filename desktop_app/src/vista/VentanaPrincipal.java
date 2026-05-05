package vista;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private Login login;

	public VentanaPrincipal(Login login) {
		this.login = login;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = (int) pantalla.getWidth();
		int alto = (int) pantalla.getHeight();
		this.setBounds(150, 50, 932, 617);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		panel.setBounds(10, 11, 898, 76);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("GESTOR DE INCIDENCIAS");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(24, 21, 262, 33);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ABIERTA");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(629, 11, 48, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("EN PROGRESO");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setBounds(707, 11, 81, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("RESUELTAS");
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setBounds(814, 11, 74, 14);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_2 = new JLabel("0");
		lblNewLabel_2.setForeground(new Color(204, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(649, 35, 38, 25);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("0");
		lblNewLabel_2_1.setForeground(new Color(204, 255, 0));
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_1.setBounds(743, 35, 38, 25);
		panel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("0");
		lblNewLabel_2_2.setForeground(new Color(0, 204, 0));
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2_2.setBounds(839, 34, 38, 25);
		panel.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_3 = new JLabel("Estado:");
		lblNewLabel_3.setBounds(10, 113, 48, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Prioridad:");
		lblNewLabel_3_1.setBounds(174, 113, 60, 14);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("Dept:");
		lblNewLabel_3_2.setBounds(364, 113, 36, 14);
		contentPane.add(lblNewLabel_3_2);
		
		JComboBox comboEstado = new JComboBox(new String[] {"", "ABIERTA", "EN_PROGRESO", "RESUELTA", "CERRADA"});
		comboEstado.setBounds(55, 109, 99, 22);
		contentPane.add(comboEstado);
		
		JComboBox comboPrioridad = new JComboBox(new String[]{"", "ALTA", "MEDIA", "BAJA"});
		comboPrioridad.setBounds(234, 109, 99, 22);
		contentPane.add(comboPrioridad);
		
		JComboBox comboDept = new JComboBox();
		comboDept.setBounds(399, 109, 99, 22);
		contentPane.add(comboDept);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnActualizar.setBackground(new Color(0, 102, 153));
		btnActualizar.setForeground(Color.WHITE);
		btnActualizar.setBounds(596, 104, 145, 32);
		btnActualizar.setBorderPainted(false);
		btnActualizar.setFocusPainted(false);
		contentPane.add(btnActualizar);
		
		JButton btnNuevaIncidencia = new JButton("+ Nueva Incidencia");
		btnNuevaIncidencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaIncidencia incidencia = new VentanaIncidencia(VentanaPrincipal.this, modelo);
				
				incidencia.setVisible(true);
			}
		});
		btnNuevaIncidencia.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNuevaIncidencia.setBackground(new Color(50, 205, 50));
		btnNuevaIncidencia.setForeground(Color.WHITE);
		btnNuevaIncidencia.setBorderPainted(false);
		btnNuevaIncidencia.setFocusPainted(false);
		btnNuevaIncidencia.setBounds(763, 104, 145, 32);
		contentPane.add(btnNuevaIncidencia);
		
		String[] columnas = {"ID", "Titulo", "Departamento", "Prioridad", "Estado", "Reportado por", "Fecha"};
		
		modelo = new DefaultTableModel(null, columnas);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		scrollPane.setBounds(10, 182, 898, 387);
		contentPane.add(scrollPane);
		
		table = new JTable(modelo);
		scrollPane.setViewportView(table);
		table.setRowHeight(35);
		
		

	}
}
