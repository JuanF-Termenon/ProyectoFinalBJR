package vista;

import com.st.models.Historial;
import com.st.repositories.HistorialRepository;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VentanaHistorial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;

	public VentanaHistorial(int idIncidencia) {
		setTitle("Historial de Incidencia #" + idIncidencia);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 800, 400);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		panel.setBounds(-5, 0, 795, 50);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblTitulo = new JLabel("Historial de Incidencia #" + idIncidencia);
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setBounds(10, 10, 400, 30);
		panel.add(lblTitulo);

		String[] columnas = {"Fecha", "Acci\u00F3n", "Estado Anterior", "Estado Nuevo", "Usuario", "Comentario"};
		modelo = new DefaultTableModel(null, columnas);

		try {
			HistorialRepository hRepo = new HistorialRepository();
			ArrayList<Historial> lista = hRepo.findByIdIncidencia(idIncidencia);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			for (Historial h : lista) {
				String fecha = h.getFechaEvento() != null ? sdf.format(h.getFechaEvento()) : "";
				modelo.addRow(new Object[] {
					fecha,
					traducirAccion(h.getAccion()),
					h.getEstadoAnterior() != null ? h.getEstadoAnterior() : "",
					h.getEstadoNuevo() != null ? h.getEstadoNuevo() : "",
					h.getNombreUsuario(),
					h.getComentario() != null ? h.getComentario() : ""
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 770, 290);
		contentPane.add(scrollPane);

		table = new JTable(modelo);
		scrollPane.setViewportView(table);
		table.setRowHeight(30);
	}

	private String traducirAccion(String accion) {
		switch (accion) {
			case "CREAR": return "Creaci\u00F3n";
			case "CAMBIAR_ESTADO": return "Cambio de estado";
			case "ASIGNAR": return "Asignaci\u00F3n";
			case "RESOLVER": return "Resoluci\u00F3n";
			case "REABRIR": return "Reapertura";
			case "CANCELAR": return "Cancelaci\u00F3n";
			default: return accion;
		}
	}
}
