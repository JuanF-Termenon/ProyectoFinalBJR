package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.Canvas;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputUsuario;
	private JTextField inputContrasena;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 100, 450, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ST Connect");
		lblNewLabel.setBackground(new Color(192, 192, 192));
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 0, 115, 36);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Gestión de incidencias");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 32, 115, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Tipo de acceso");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(54, 69, 85, 14);
		contentPane.add(lblNewLabel_2);
		
		String[] perfiles = {"Selecciona un perfil", "Puesto", "Técnico ST", "Jefe ST" };
		
		JComboBox SeleccionarPerfil = new JComboBox(perfiles);
		SeleccionarPerfil.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		SeleccionarPerfil.setToolTipText("Selecciona un perfil");
		SeleccionarPerfil.setBounds(64, 94, 295, 22);
		contentPane.add(SeleccionarPerfil);
		
		JLabel lblNewLabel_2_1 = new JLabel("Usuario");
		lblNewLabel_2_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(54, 127, 85, 14);
		contentPane.add(lblNewLabel_2_1);
		
		inputUsuario = new JTextField();
		inputUsuario.setText("Ej: TP01 o tecnico1");
		inputUsuario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (inputUsuario.getText().equals("Ej: TP01 o tecnico1")) {
			        inputUsuario.setText("");
			        inputUsuario.setForeground(Color.BLACK); 
			    }
			}
		});
		inputUsuario.setFont(new Font("Segoe UI Light", Font.PLAIN, 11));
		inputUsuario.setBounds(64, 152, 295, 20);
		contentPane.add(inputUsuario);
		inputUsuario.setColumns(10);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Contraseña");
		lblNewLabel_2_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblNewLabel_2_1_1.setBounds(54, 188, 85, 14);
		contentPane.add(lblNewLabel_2_1_1);
		
		inputContrasena = new JTextField();
		inputContrasena.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		inputContrasena.setText("Introduce la contraseña");
		inputContrasena.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (inputContrasena.getText().equals("Introduce la contraseña")) {
			        inputContrasena.setText("");
			        inputContrasena.setForeground(Color.BLACK); 
			    }
			}
		});
		inputContrasena.setColumns(10);
		inputContrasena.setBounds(64, 213, 295, 20);
		contentPane.add(inputContrasena);
		
		JButton btnLogin = new JButton("Entrar al sistema");
		btnLogin.setBackground(new Color(34, 197, 94));
		btnLogin.setBorderPainted(false);
		btnLogin.setFocusPainted(false);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal principal = new VentanaPrincipal(Login.this);
				principal.setVisible(true);
				Login.this.setVisible(false);
			}
		});
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(64, 128, 128));
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnLogin.setBounds(64, 266, 295, 41);
		contentPane.add(btnLogin);

	}
}
