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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField inputUsuario;
    private JPasswordField inputContrasena;

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
        setTitle("ST Connect");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 100, 450, 376);
        this.setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("ST Connect");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setBounds(10, 0, 115, 36);
        contentPane.add(lblTitle);

        JLabel lblSub = new JLabel("Gesti\u00F3n de incidencias");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setBounds(10, 32, 115, 14);
        contentPane.add(lblSub);

        JLabel lblUser = new JLabel("Usuario");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUser.setBounds(64, 69, 85, 14);
        contentPane.add(lblUser);

        inputUsuario = new JTextField();
        inputUsuario.setBounds(64, 94, 295, 20);
        contentPane.add(inputUsuario);

        JLabel lblPass = new JLabel("Contrase\u00F1a");
        lblPass.setBounds(64, 139, 85, 14);
        contentPane.add(lblPass);

        inputContrasena = new JPasswordField();
        inputContrasena.setBounds(64, 164, 295, 20);
        contentPane.add(inputContrasena);

        JButton btnLogin = new JButton("Entrar al sistema");
        btnLogin.setBackground(new Color(64, 128, 128));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogin.setBounds(64, 220, 295, 41);
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

        if (username.isEmpty() || passPlana.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
            return;
        }

        try {
            String hash = Utils.simplificarHash(passPlana);
            UsuarioRepository repo = new UsuarioRepository();
            Usuario user = repo.login(username, hash);

            if (user != null) {
                repo.actualizarUltimoLogin(user.getIdUsuario());

                if (user.isPrimerAcceso()) {
                    String nuevaPass = JOptionPane.showInputDialog(this,
                        "Es tu primer acceso. Introduce una nueva contrase\u00F1a:",
                        "Cambio de contrase\u00F1a obligatorio",
                        JOptionPane.WARNING_MESSAGE);
                    if (nuevaPass != null && !nuevaPass.trim().isEmpty()) {
                        String nuevoHash = Utils.simplificarHash(nuevaPass.trim());
                        repo.actualizarPassword(user.getIdUsuario(), nuevoHash);
                        user.setPrimerAcceso(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "Debes cambiar la contrase\u00F1a para continuar.");
                        return;
                    }
                }

                VentanaPrincipal principal = new VentanaPrincipal(this, user);
                principal.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contrase\u00F1a incorrectos (o cuenta inactiva).", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error cr\u00EDtico de conexi\u00F3n con la base de datos.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
        }
    }
}
