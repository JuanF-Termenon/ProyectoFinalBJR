package vista;
import java.awt.Toolkit;
import com.st.repositories.IncidenciaRepository;
import com.st.models.Incidencia;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import jdk.javadoc.doclet.Reporter;

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
import java.sql.SQLException;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private Login login;

	private IncidenciaRepository repo = new IncidenciaRepository();
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
		
		JLabel lblNewLabel_1 = new JLabel("ACTIVAS");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(629, 11, 51, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("EN CURSO");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setBounds(719, 11, 62, 16);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("RESUELTAS");
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setBounds(814, 11, 72, 16);
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
		
		JComboBox comboEstado = new JComboBox(new String[] {"", "ACTIVA", "EN_CURSO", "RESUELTA", "REABIERTA", "CANCELADA"});
		comboEstado.setBounds(55, 109, 99, 22);
		contentPane.add(comboEstado);
		
		JComboBox comboPrioridad = new JComboBox(new String[]{"", "BAJA", "MEDIA", "ALTA", "CRITICA"});
		comboPrioridad.setBounds(234, 109, 99, 22);
		contentPane.add(comboPrioridad);
		
		JComboBox comboDept = new JComboBox();
		comboDept.setBounds(399, 109, 99, 22);
		contentPane.add(comboDept);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // 1. Limpiamos la tabla para volver a cargarla
		        modelo.setRowCount(0);
		        
		        // 2. Obtenemos lo que el usuario ha seleccionado en los combos
		        String filtroEstado = (String) comboEstado.getSelectedItem();
		        String filtroPrioridad = (String) comboPrioridad.getSelectedItem();
		        
		        try {
		            // Reiniciamos contadores para el nuevo filtrado
		            int cAbiertas = 0;
		            int cProgreso = 0;
		            int cResueltas = 0;

		            // 3. Volvemos a pedir las incidencias
		            ArrayList<Incidencia> lista = repo.findAll();
		            
		            for (Incidencia i : lista) {
		                // --- LA LÓGICA DE FILTRADO ---
		                // Si el combo no está vacío Y el estado no coincide, saltamos a la siguiente incidencia
		                if (!filtroEstado.isEmpty() && !i.getEstado().equalsIgnoreCase(filtroEstado)) continue;
		                if (!filtroPrioridad.isEmpty() && !i.getPrioridad().equalsIgnoreCase(filtroPrioridad)) continue;

		                // 4. Si pasa el filtro, añadimos la fila (Tu código exacto)
		                modelo.addRow(new Object[] {
		                    i.getIdIncidencia(),
		                    i.getDescripcion(),
		                    i.getIdPuesto(),
		                    i.getPrioridad(),
		                    i.getEstado(),
		                    i.getIdUsuarioCreador(),
		                    i.getFechaCreacion()
		                });
		                
		                // 5. Actualizamos contadores según lo que estamos viendo
		                if (i.getEstado().equalsIgnoreCase("ACTIVA") || i.getEstado().equalsIgnoreCase("REABIERTA")) cAbiertas++;
		                else if (i.getEstado().equalsIgnoreCase("EN_CURSO")) cProgreso++;
		                else if (i.getEstado().equalsIgnoreCase("RESUELTA")) cResueltas++;
		            }

		            // 6. Refrescamos los labels con los nuevos totales filtrados
		            lblNewLabel_2.setText(String.valueOf(cAbiertas));
		            lblNewLabel_2_1.setText(String.valueOf(cProgreso));
		            lblNewLabel_2_2.setText(String.valueOf(cResueltas));

		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    }
		});
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
		ArrayList<Incidencia> incidencias;
		try {
			
			int contadorAbiertas = 0;
			int contadorProgreso = 0;
			int contadorResueltas = 0;
			
			incidencias = repo.findAll();
			
			for (Incidencia i : incidencias) {
				modelo.addRow(new Object[] {
				        i.getIdIncidencia(),
				        i.getDescripcion(),        // Mapeado a "Titulo" en tus columnas
				        i.getIdPuesto(),           // Mapeado a "Departamento"
				        i.getPrioridad(),
				        i.getEstado(),
				        i.getIdUsuarioCreador(),   // Mapeado a "Reportado por"
				        i.getFechaCreacion()
				    });
				
				//Lógica del recuento
			    // Comparamos el estado (asegúrate de que coincidan con los strings de tu BD/DML)
			    if (i.getEstado().equalsIgnoreCase("ACTIVA")) {
			        contadorAbiertas++;
			    } else if (i.getEstado().equalsIgnoreCase("EN_CURSO")) {
			        contadorProgreso++;
			    } else if (i.getEstado().equalsIgnoreCase("RESUELTA")) {
			        contadorResueltas++;
			    }	
				
			    lblNewLabel_2.setText(String.valueOf(contadorAbiertas));   // ABIERTA
			    lblNewLabel_2_1.setText(String.valueOf(contadorProgreso)); // EN PROGRESO
			    lblNewLabel_2_2.setText(String.valueOf(contadorResueltas)); // RESUELTAS
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
