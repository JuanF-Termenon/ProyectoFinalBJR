package vista;

import com.st.repositories.IncidenciaRepository;

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
import java.util.ArrayList;

public class VentanaEstadisticas extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableTecnicos;
    private JTable tableDeptos;

    public VentanaEstadisticas(int userId) {
        setTitle("Estad\u00EDsticas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(200, 100, 750, 520);
        this.setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 153));
        panel.setBounds(-5, 0, 745, 50);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Estad\u00EDsticas del departamento");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitulo.setBounds(10, 10, 400, 30);
        panel.add(lblTitulo);

        JLabel lblTecnicos = new JLabel("Rendimiento por t\u00E9cnico");
        lblTecnicos.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTecnicos.setBounds(10, 60, 200, 20);
        contentPane.add(lblTecnicos);

        String[] colTec = {"T\u00E9cnico", "Activas", "En curso", "Resueltas", "Total", "Media (h)"};
        DefaultTableModel modeloTec = new DefaultTableModel(null, colTec);
        tableTecnicos = new JTable(modeloTec);
        tableTecnicos.setRowHeight(25);
        JScrollPane scrollTec = new JScrollPane(tableTecnicos);
        scrollTec.setBounds(10, 85, 715, 150);
        contentPane.add(scrollTec);

        JLabel lblDeptos = new JLabel("Incidencias por departamento");
        lblDeptos.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDeptos.setBounds(10, 250, 250, 20);
        contentPane.add(lblDeptos);

        String[] colDept = {"Departamento", "Total", "Activas", "En curso", "Resueltas"};
        DefaultTableModel modeloDept = new DefaultTableModel(null, colDept);
        tableDeptos = new JTable(modeloDept);
        tableDeptos.setRowHeight(25);
        JScrollPane scrollDept = new JScrollPane(tableDeptos);
        scrollDept.setBounds(10, 275, 715, 150);
        contentPane.add(scrollDept);

        try {
            IncidenciaRepository repo = new IncidenciaRepository(userId);
            ArrayList<String[]> tecnicos = repo.getStatsByTecnico();
            for (String[] row : tecnicos) modeloTec.addRow(row);
            ArrayList<String[]> deptos = repo.getStatsByDepartamento();
            for (String[] row : deptos) modeloDept.addRow(row);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
