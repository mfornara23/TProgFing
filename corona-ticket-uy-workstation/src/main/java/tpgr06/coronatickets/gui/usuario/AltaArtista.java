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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.toedter.calendar.JDateChooser;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.usuario.UsuarioDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;

public class AltaArtista {

	private Factory factory = Factory.getInstance();
	private JInternalFrame frameArtista;
	private JTextField nicknameField;
	private JTextField nombreField;
	private JTextField apellidoField;
	private JTextField emailField;
	JDateChooser fechaNacmiento;
	private JTextField siteField;
	JTextPane descTextPane;
	JTextPane bioTextPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaArtista window = new AltaArtista();
					window.frameArtista.setVisible(true);
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(window.frameArtista);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AltaArtista() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameArtista = new JInternalFrame();
		frameArtista.setTitle("Alta Artista");
		frameArtista.setSize(400, 800);
		frameArtista.setBounds(300, 350, 400, 500);
		frameArtista.setResizable(false);
		frameArtista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameArtista.getContentPane().setLayout(null);

		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(29, 22, 104, 14);
		frameArtista.getContentPane().add(lblNickname);

		nicknameField = new JTextField();
		nicknameField.setBounds(150, 19, 205, 20);
		frameArtista.getContentPane().add(nicknameField);
		nicknameField.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(29, 58, 59, 14);
		frameArtista.getContentPane().add(lblNombre);

		nombreField = new JTextField();
		nombreField.setBounds(150, 55, 205, 20);
		frameArtista.getContentPane().add(nombreField);
		nombreField.setColumns(10);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(29, 94, 59, 14);
		frameArtista.getContentPane().add(lblApellido);

		apellidoField = new JTextField();
		apellidoField.setBounds(150, 91, 205, 20);
		frameArtista.getContentPane().add(apellidoField);
		apellidoField.setColumns(10);

		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(29, 127, 59, 14);
		frameArtista.getContentPane().add(lblEmail);

		emailField = new JTextField();
		emailField.setBounds(150, 124, 205, 20);
		frameArtista.getContentPane().add(emailField);
		emailField.setColumns(10);

		JLabel lblFecha = new JLabel("Fecha Nacimiento:");
		lblFecha.setBounds(29, 240, 117, 14);
		frameArtista.getContentPane().add(lblFecha);

		fechaNacmiento = new JDateChooser();
		fechaNacmiento.setBounds(150, 234, 205, 20);
		frameArtista.getContentPane().add(fechaNacmiento);

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(59, 425, 86, 23);
		frameArtista.getContentPane().add(btnAccept);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(269, 425, 86, 23);
		frameArtista.getContentPane().add(btnCancel);

		JLabel lblSite = new JLabel("Sitio Web:");
		lblSite.setBounds(29, 266, 104, 16);
		frameArtista.getContentPane().add(lblSite);

		siteField = new JTextField();
		siteField.setBounds(150, 263, 205, 22);
		frameArtista.getContentPane().add(siteField);
		siteField.setColumns(10);

		JLabel lblDesc = new JLabel("Descripci\u00F3n Gral:");
		lblDesc.setBounds(29, 294, 117, 16);
		frameArtista.getContentPane().add(lblDesc);

		descTextPane = new JTextPane();
		descTextPane.setBounds(150, 294, 205, 50);
		frameArtista.getContentPane().add(descTextPane);

		JScrollPane scrollDesc = new JScrollPane(descTextPane);
		scrollDesc.setBounds(150, 294,  205, 50);
		frameArtista.getContentPane().add(scrollDesc);

		JLabel lblBio = new JLabel("Bio:");
		lblBio.setBounds(29, 361, 56, 16);
		frameArtista.getContentPane().add(lblBio);

		bioTextPane = new JTextPane();
		bioTextPane.setBounds(150, 361, 205, 50);
		frameArtista.getContentPane().add(bioTextPane);

		JScrollPane scrollBio = new JScrollPane(bioTextPane);
		scrollBio.setBounds(150, 361,  205, 50);
		frameArtista.getContentPane().add(scrollBio);		

		JLabel lblPassword = new JLabel("password:");
		lblPassword.setBounds(29, 161, 86, 16);
		frameArtista.getContentPane().add(lblPassword);

		JLabel lblRepetir = new JLabel("confirmar");
		lblRepetir.setBounds(29, 189, 86, 16);
		frameArtista.getContentPane().add(lblRepetir);

		JPasswordField textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(150, 156, 205, 26);
		frameArtista.getContentPane().add(textFieldPassword);
		textFieldPassword.setColumns(10);

		JPasswordField textFieldPassword2 = new JPasswordField();
		textFieldPassword2.setColumns(10);
		textFieldPassword2.setBounds(150, 189, 205, 26);
		frameArtista.getContentPane().add(textFieldPassword2);

		JLabel lblPassword_1 = new JLabel("password:");
		lblPassword_1.setBounds(29, 205, 71, 16);
		frameArtista.getContentPane().add(lblPassword_1);


		// ----------------------------------------------------------------------------//

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (nicknameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista, "Campo Nickname no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (nombreField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista, "Campo Nombre no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (apellidoField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista, "Campo Apellido no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (emailField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista, "Campo e-mail no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (!emailField.getText().contains("@") && !emailField.getText().contains(".")) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista, "Formato de e-mail no valido",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (textFieldPassword.getPassword().length==0) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista, "Campo password no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (textFieldPassword2.getPassword().length==0) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista, "Campo confirmar password no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (!textFieldPassword.getText().equals(textFieldPassword2.getText())) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista, "Campos de password no coinciden",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (fechaNacmiento.getDate() == null) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista,
							"Campo Fecha de Nacimiento no puede ser vac\u00EDo", "Error al registrar",
							JOptionPane.ERROR_MESSAGE);
				} else if (factory.getIUsuarioController().getUsuarioByNickname(nicknameField.getText()) != null) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista,
							"El Nickname seleccionado se encuentra en uso, por favor seleccione otro",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (factory.getIUsuarioController().getUsuarioByEmail(emailField.getText()) != null) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista,
							"El e-mail seleccionado se encuentra en uso, por favor seleccione otro",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (descTextPane.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaArtista.this.frameArtista, "Campo Descripci\\u00F3n Gral no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else {
					UsuarioDTO user = new ArtistaDTO(nicknameField.getText(), nombreField.getText(),
							apellidoField.getText(), emailField.getText(), fechaNacmiento.getDate(),new String(textFieldPassword.getPassword()),
							null,null,descTextPane.getText(), bioTextPane.getText(), siteField.getText());
					user.setImagen("/media/img/user-generico.png");
					try {
						factory.getIUsuarioController().altaUsuario(user);
						JOptionPane.showMessageDialog(AltaArtista.this.frameArtista, "Alta exitosa!", "Alta Artista",
								JOptionPane.INFORMATION_MESSAGE);
						AltaArtista.this.frameArtista.dispose();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(AltaArtista.this.frameArtista,
								"Ha ocurrido un error interno, intente mas tarde", "Error al registrar",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AltaArtista.this.frameArtista.dispose();

			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}
}
