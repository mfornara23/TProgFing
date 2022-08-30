package tpgr06.coronatickets.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tpgr06.coronatickets.ws.publicador.Publicador;

import java.awt.Window.Type;


public class Login {

	private JFrame frmLogin;
	private JTextField usuarioField;
	private JPasswordField pwdField;
	
	private final static String ADMIN_USERNAME = "admin";
	private final static String ADMIN_PWD = "admin";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Publicador.main();
					Login window = new Login();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setType(Type.UTILITY);
		frmLogin.setTitle("Login");
		frmLogin.setResizable(false);
		frmLogin.setBounds(100, 100, 312, 233);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(29, 38, 71, 14);
		frmLogin.getContentPane().add(lblUsuario);
		
		usuarioField = new JTextField();
		usuarioField.setBounds(110, 38, 162, 20);
		frmLogin.getContentPane().add(usuarioField);
		usuarioField.setColumns(10);
		
		JLabel lblClave = new JLabel("Clave");
		lblClave.setBounds(29, 63, 71, 14);
		frmLogin.getContentPane().add(lblClave);
		
		pwdField = new JPasswordField();
		pwdField.setBounds(110, 63, 162, 20);
		frmLogin.getContentPane().add(pwdField);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(29, 134, 82, 23);
		btnAceptar.setSelectedIcon(null);
		btnAceptar.setIcon(null);
		frmLogin.getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(182, 134, 90, 23);
		frmLogin.getContentPane().add(btnCancelar);
		
		JLabel lblCoronaTicket = new JLabel("coronaTickets.uy");
		lblCoronaTicket.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoronaTicket.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCoronaTicket.setBounds(20, 11, 276, 16);
		frmLogin.getContentPane().add(lblCoronaTicket);
		
		//----------------------------------------------------------------------------//
		
		ActionListener login = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(usuarioField.getText().isEmpty()){
					JOptionPane.showMessageDialog(Login.this.frmLogin,
							"Campo Usuario no puede ser vacio", "Error al ingresar", JOptionPane.ERROR_MESSAGE);
				}else if(pwdField.getPassword().length==0){
					JOptionPane.showMessageDialog(Login.this.frmLogin,
							"Campo Clave no puede ser vacio", "Error al ingresar", JOptionPane.ERROR_MESSAGE);
				} else if(!ADMIN_PWD.equals(new String(pwdField.getPassword()))){
					JOptionPane.showMessageDialog(Login.this.frmLogin,
							"Clave Incorrecta, por favor vuelva a intentarlo", "Error al ingresar", JOptionPane.ERROR_MESSAGE);
				} else if(!usuarioField.getText().equals(ADMIN_USERNAME)){
					JOptionPane.showMessageDialog(Login.this.frmLogin,
							"Usuario no existe o no es ADMINISTRADOR", "Error al ingresar", JOptionPane.ERROR_MESSAGE);
				} else{
					//Valido alguna otras cosa y ejecuto lo que preciso
					MenuPrincipal.main(null); 
					Login.this.frmLogin.dispose();
				}
			}
		};
		
		
		btnAceptar.addActionListener(login);
		
		
		ActionListener cancel = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		
		btnCancelar.addActionListener(cancel);
	}

}
