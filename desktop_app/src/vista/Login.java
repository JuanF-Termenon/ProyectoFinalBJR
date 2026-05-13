package vista;

import com.st.repositories.UsuarioRepository;
import com.st.security.Utils;
import com.st.models.Usuario;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField inputUsuario;
    private JPasswordField inputContrasena;
    private JComboBox<String> seleccionarPerfil;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setTitle("ST Connect - Acceso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 100, 450, 376);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- CABECERA ---
        JLabel lblTitle = new JLabel("ST Connect");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setBounds(10, 0, 115, 36);
        contentPane.add(lblTitle);

        JLabel lblSub = new JLabel("Gestión de incidencias");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setBounds(10, 32, 115, 14);
        contentPane.add(lblSub);

        // --- TIPO DE ACCESO ---
        JLabel lblTipo = new JLabel("Tipo de acceso");
        lblTipo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTipo.setBounds(54, 69, 150, 14);
        contentPane.add(lblTipo);

        String[] perfiles = { "Selecciona un perfil", "Técnico ST", "Jefe ST" };
        seleccionarPerfil = new JComboBox<>(perfiles);
        seleccionarPerfil.setBounds(64, 94, 295, 22);
        contentPane.add(seleccionarPerfil);

        // --- USUARIO ---
        JLabel lblUser = new JLabel("Usuario");
        lblUser.setBounds(64, 127, 85, 14);
        contentPane.add(lblUser);

        inputUsuario = new JTextField();
        inputUsuario.setBounds(64, 152, 295, 20);
        contentPane.add(inputUsuario);

        // --- CONTRASEÑA ---
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setBounds(64, 188, 85, 14);
        contentPane.add(lblPass);

        inputContrasena = new JPasswordField();
        inputContrasena.setBounds(64, 213, 295, 20);
        contentPane.add(inputContrasena);

        // --- BOTÓN ENTRAR ---
        JButton btnLogin = new JButton("Entrar al sistema");
        btnLogin.setBackground(new Color(64, 128, 128));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogin.setBounds(64, 266, 295, 41);
        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ejecutarLogin();
            }
        });
        contentPane.add(btnLogin);
    }

    private void ejecutarLogin() {
        String username = inputUsuario.getText().trim();
        String passPlana = new String(inputContrasena.getPassword()).trim();
        String perfilSeleccionado = (String) seleccionarPerfil.getSelectedItem();

        // 1. Validación de campos vacíos
        if (username.isEmpty() || passPlana.isEmpty() || perfilSeleccionado.equals("Selecciona un perfil")) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
            return;
        }

        try {
            // 2. Lógica de Backend
            String hash = Utils.simplificarHash(passPlana);
            UsuarioRepository repo = new UsuarioRepository();
            Usuario user = repo.login(username, hash);

            // 3. Verificación de resultado
            if (user != null) {
                // Comprobamos que el rol coincida con el perfil del combo
                if (user.getRol().getNombreRol().equalsIgnoreCase(perfilSeleccionado)) {
                    VentanaPrincipal principal = new VentanaPrincipal(this);
                    principal.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "El perfil seleccionado no coincide con su rol asignado.", "Error de Perfil", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos (o cuenta inactiva).", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error crítico de conexión con la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
        }
    }
}