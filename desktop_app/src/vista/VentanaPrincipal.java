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
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private int filaSeleccionada;
	private Login login;
	private Usuario user;

	private IncidenciaRepository repo = new IncidenciaRepository();
	private JLabel contadorActivas = new JLabel("0");
	private JLabel contadorEnCurso = new JLabel("0");
	private JLabel contadorResueltas = new JLabel("0");
	private JComboBox comboDept = new JComboBox();
	private JButton btnAnadir = new JButton("Añadir incidencia");
	public VentanaPrincipal(Login login, Usuario user) {
		setTitle("BJR Technician Services");
		this.login = login;
		this.user = user;

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
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(10, 113, 48, 14);
		contentPane.add(lblEstado);
		
		JLabel lblPrioridad = new JLabel("Prioridad:");
		lblPrioridad.setBounds(174, 113, 60, 14);
		contentPane.add(lblPrioridad);
		
		JLabel lblDept = new JLabel("Dept:");
		lblDept.setBounds(364, 113, 36, 14);
		contentPane.add(lblDept);
		
		JComboBox comboEstado = new JComboBox(new String[] {"", "ACTIVA", "EN_CURSO", "RESUELTA", "REABIERTA", "CANCELADA"});
		comboEstado.setBounds(55, 109, 99, 22);
		contentPane.add(comboEstado);
		
		JComboBox comboPrioridad = new JComboBox(new String[]{"", "BAJA", "MEDIA", "ALTA", "CRITICA"});
		comboPrioridad.setBounds(234, 109, 99, 22);
		contentPane.add(comboPrioridad);
		
		comboDept.addItem("");
		try {
			PuestoRepository pRepo = new PuestoRepository();
			for (var d : pRepo.findAllDepartamentos()) {
				comboDept.addItem(d);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		comboDept.setBounds(399, 109, 99, 22);
		contentPane.add(comboDept);
		
		JButton btnRefrescar = new JButton("Refrescar tabla");
		btnRefrescar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // 1. Limpiamos la tabla para volver a cargarla
		        modelo.setRowCount(0);
		        
		        // 2. Obtenemos lo que el usuario ha seleccionado en los combos
		        String filtroEstado = (String) comboEstado.getSelectedItem();
		        String filtroPrioridad = (String) comboPrioridad.getSelectedItem();
		        String filtroDept = (String) comboDept.getSelectedItem();
		        
		        try {
		            // Reiniciamos contadores para el nuevo filtrado
		            int totalActivas = 0;
		            int totalEnCurso = 0;
		            int totalResueltas = 0;

		            // 3. Volvemos a pedir las incidencias
		            ArrayList<Incidencia> lista = repo.findAll();
		            
		            for (Incidencia i : lista) {
		                // --- LA LÓGICA DE FILTRADO ---
		                // Si el combo no está vacío Y el estado no coincide, saltamos a la siguiente incidencia
		                if (!filtroEstado.isEmpty() && !i.getEstado().equalsIgnoreCase(filtroEstado)) continue;
		                if (!filtroPrioridad.isEmpty() && !i.getPrioridad().equalsIgnoreCase(filtroPrioridad)) continue;
		                if (!filtroDept.isEmpty() && !i.getNombreDepartamento().equalsIgnoreCase(filtroDept)) continue;

		                // 4. Si pasa el filtro, añadimos la fila (Tu código exacto)
		                modelo.addRow(new Object[] {
		                    i.getIdIncidencia(),
		                    i.getDescripcion(),
		                    i.getNombreDepartamento(),
		                    i.getPrioridad(),
		                    i.getEstado(),
		                    i.getNombreReportadoPor(),
		                    i.getFechaCreacion()
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
		contentPane.add(btnAnadir);
		
		String[] columnas = {"ID", "Descripcion", "Departamento", "Prioridad", "Estado", "Reportado por", "Fecha"};
		
		modelo = new DefaultTableModel(null, columnas);
		ArrayList<Incidencia> incidencias;
		try {
			
			int totalActivas = 0;
			int totalEnCurso = 0;
			int totalResueltas = 0;
			
			incidencias = repo.findAll();
			
			for (Incidencia i : incidencias) {
				modelo.addRow(new Object[] {
				        i.getIdIncidencia(),
				        i.getDescripcion(),
				        i.getNombreDepartamento(),
				        i.getPrioridad(),
				        i.getEstado(),
				        i.getNombreReportadoPor(),
				        i.getFechaCreacion()
				    });
				
			    if (i.getEstado().equalsIgnoreCase("ACTIVA") || i.getEstado().equalsIgnoreCase("REABIERTA")) {
			        totalActivas++;
			    } else if (i.getEstado().equalsIgnoreCase("EN_CURSO")) {
			        totalEnCurso++;
			    } else if (i.getEstado().equalsIgnoreCase("RESUELTA")) {
			        totalResueltas++;
			    }	
				
			    contadorActivas.setText(String.valueOf(totalActivas));
			    contadorEnCurso.setText(String.valueOf(totalEnCurso));
			    contadorResueltas.setText(String.valueOf(totalResueltas));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(10, 210, 1062, 387);
		contentPane.add(scrollPane);
		
		table = new JTable(modelo);
		scrollPane.setViewportView(table);
		table.setRowHeight(35);
		
		
		JButton btnGestionar = new JButton("Gestionar incidencia");
		btnGestionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaVista = table.getSelectedRow();
		       
		        if (filaVista == -1) {
		            JOptionPane.showMessageDialog(null, 
		                "Por favor, selecciona una fila de la tabla para editar.", 
		                "Aviso", 
		                JOptionPane.WARNING_MESSAGE);
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
		if (user.getRol().getIdRol() != 1) {
			btnGestionar.setVisible(false);
		}
		contentPane.add(btnGestionar);

		if (user.getRol().getIdRol() == 3) {
			btnAnadir.setVisible(false);
		}

	}

	public void cargarIncidencias() {
		modelo.setRowCount(0);
		try {
			int totalActivas = 0, totalEnCurso = 0, totalResueltas = 0;
			for (Incidencia i : repo.findAll()) {
				modelo.addRow(new Object[] {
					i.getIdIncidencia(), i.getDescripcion(), i.getNombreDepartamento(),
					i.getPrioridad(), i.getEstado(), i.getNombreReportadoPor(), i.getFechaCreacion()
				});
				if (i.getEstado().equalsIgnoreCase("ACTIVA")) totalActivas++;
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
