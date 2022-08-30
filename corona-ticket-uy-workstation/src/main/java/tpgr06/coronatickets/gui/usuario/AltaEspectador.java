package tpgr06.coronatickets.gui.usuario;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.usuario.UsuarioDTO;
import tpgr06.coronatickets.sys.usuario.espectador.EspectadorDTO;

public class AltaEspectador {

	private Factory factory = Factory.getInstance();
	private JInternalFrame frameEspectador;
	private JTextField nicknameField;
	private JTextField nombreField;
	private JTextField apellidoField;
	private JTextField emailField;
	JDateChooser fechaNacmiento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaEspectador window = new AltaEspectador();
					window.frameEspectador.setVisible(true);
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(window.frameEspectador);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AltaEspectador() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameEspectador = new JInternalFrame();
		frameEspectador.setTitle("Alta Espectador");
		frameEspectador.setSize(400, 450);
		frameEspectador.setBounds(300, 350, 400, 450);
		frameEspectador.setResizable(false);
		frameEspectador.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameEspectador.getContentPane().setLayout(null);

		JLabel lblNickname = new JLabel("Nickname");
		lblNickname.setBounds(29, 22, 59, 14);
		frameEspectador.getContentPane().add(lblNickname);

		nicknameField = new JTextField();
		nicknameField.setBounds(150, 19, 205, 20);
		frameEspectador.getContentPane().add(nicknameField);
		nicknameField.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(29, 58, 59, 14);
		frameEspectador.getContentPane().add(lblNombre);

		nombreField = new JTextField();
		nombreField.setBounds(150, 55, 205, 20);
		frameEspectador.getContentPane().add(nombreField);
		nombreField.setColumns(10);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(29, 94, 59, 14);
		frameEspectador.getContentPane().add(lblApellido);

		apellidoField = new JTextField();
		apellidoField.setBounds(150, 91, 205, 20);
		frameEspectador.getContentPane().add(apellidoField);
		apellidoField.setColumns(10);

		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(29, 127, 59, 14);
		frameEspectador.getContentPane().add(lblEmail);

		emailField = new JTextField();
		emailField.setBounds(150, 124, 205, 20);
		frameEspectador.getContentPane().add(emailField);
		emailField.setColumns(10);

		JLabel lblFecha = new JLabel("Fecha Nacimiento:");
		lblFecha.setBounds(29, 247, 117, 14);
		frameEspectador.getContentPane().add(lblFecha);

		fechaNacmiento = new JDateChooser();
		fechaNacmiento.setBounds(150, 247, 205, 20);
		frameEspectador.getContentPane().add(fechaNacmiento);

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(55, 379, 86, 23);
		frameEspectador.getContentPane().add(btnAccept);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(269, 379, 86, 23);
		frameEspectador.getContentPane().add(btnCancel);

		JLabel lblPassword = new JLabel("password:");
		lblPassword.setBounds(29, 163, 80, 16);
		frameEspectador.getContentPane().add(lblPassword);

		JLabel lblConfirmar = new JLabel("confirmar");
		lblConfirmar.setBounds(29, 191, 61, 16);
		frameEspectador.getContentPane().add(lblConfirmar);

		JLabel lblPassword_1 = new JLabel("password:");
		lblPassword_1.setBounds(29, 205, 80, 16);
		frameEspectador.getContentPane().add(lblPassword_1);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(150, 163, 205, 20);
		frameEspectador.getContentPane().add(passwordField);

		JPasswordField passwordField2 = new JPasswordField();
		passwordField2.setColumns(10);
		passwordField2.setBounds(150, 201, 205, 20);
		frameEspectador.getContentPane().add(passwordField2);

		// ----------------------------------------------------------------------------//

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (nicknameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador,
							"Campo Nickname no puede ser vac\u00EDo", "Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (nombreField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador,
							"Campo Nombre no puede ser vac\u00EDo", "Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (apellidoField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador,
							"Campo Apellido no puede ser vac\u00EDo", "Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (emailField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador,
							"Campo e-mail no puede ser vac\u00EDo", "Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (!emailField.getText().contains("@") && !emailField.getText().contains(".")) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador, "Formato de e-mail no valido",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (passwordField.getPassword().length==0) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador, "Campo password no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (passwordField2.getPassword().length==0) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador, "Campo confirmar password no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (!passwordField.getText().equals(passwordField2.getText())) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador, "Campos de password no coinciden",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (fechaNacmiento.getDate() == null) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador,
							"Campo Fecha de Nacimiento no puede ser vac\u00EDo", "Error al registrar",
							JOptionPane.ERROR_MESSAGE);
				} else if (factory.getIUsuarioController().getUsuarioByNickname(nicknameField.getText()) != null) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador,
							"El Nickname seleccionado se encuentra en uso, por favor seleccione otro",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (factory.getIUsuarioController().getUsuarioByEmail(emailField.getText()) != null) {
					JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador,
							"El e-mail seleccionado se encuentra en uso, por favor seleccione otro",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else {
					UsuarioDTO user = new EspectadorDTO(nicknameField.getText(), nombreField.getText(),
							apellidoField.getText(), emailField.getText(), fechaNacmiento.getDate(), new String(passwordField.getPassword()),null,null);
					user.setImagen("/media/img/user-generico.png");
					try {
						factory.getIUsuarioController().altaUsuario(user);
						JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador, "Alta exitosa!",
								"Alta Espectador", JOptionPane.INFORMATION_MESSAGE);
						AltaEspectador.this.frameEspectador.dispose();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(AltaEspectador.this.frameEspectador,
								"Ha ocurrido un error interno, intente mas tarde", "Error al registrar",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AltaEspectador.this.frameEspectador.dispose();

			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}

}
