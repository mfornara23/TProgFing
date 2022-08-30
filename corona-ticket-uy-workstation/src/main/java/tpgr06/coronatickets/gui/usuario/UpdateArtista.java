package tpgr06.coronatickets.gui.usuario;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.usuario.UsuarioDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

public class UpdateArtista {

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
	public static void main(final ArtistaDTO artista) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateArtista window = new UpdateArtista(artista);
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
	public UpdateArtista(ArtistaDTO artista) {
		initialize(artista);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ArtistaDTO artista) {
		frameArtista = new JInternalFrame();
		frameArtista.setResizable(false);
		frameArtista.setTitle("Actualizar Artista");
		frameArtista.setSize(400, 550);
		frameArtista.setBounds(300, 350, 400, 450);
		frameArtista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameArtista.getContentPane().setLayout(null);

		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(29, 22, 104, 14);
		frameArtista.getContentPane().add(lblNickname);

		nicknameField = new JTextField();
		nicknameField.setEditable(false);
		nicknameField.setBounds(150, 19, 205, 20);
		frameArtista.getContentPane().add(nicknameField);
		nicknameField.setColumns(10);
		nicknameField.setText(artista.getNickname());

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(29, 58, 59, 14);
		frameArtista.getContentPane().add(lblNombre);

		nombreField = new JTextField();
		nombreField.setBounds(150, 55, 205, 20);
		frameArtista.getContentPane().add(nombreField);
		nombreField.setColumns(10);
		nombreField.setText(artista.getNombre());

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(29, 94, 59, 14);
		frameArtista.getContentPane().add(lblApellido);

		apellidoField = new JTextField();
		apellidoField.setBounds(150, 91, 205, 20);
		frameArtista.getContentPane().add(apellidoField);
		apellidoField.setColumns(10);
		apellidoField.setText(artista.getApellido());

		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(29, 127, 59, 14);
		frameArtista.getContentPane().add(lblEmail);

		emailField = new JTextField();
		emailField.setEditable(false);
		emailField.setBounds(150, 124, 205, 20);
		frameArtista.getContentPane().add(emailField);
		emailField.setColumns(10);
		emailField.setText(artista.getEmail());

		JLabel lblFecha = new JLabel("Fecha Nacimiento:");
		lblFecha.setBounds(29, 166, 117, 14);
		frameArtista.getContentPane().add(lblFecha);

		fechaNacmiento = new JDateChooser();
		fechaNacmiento.setBounds(150, 160, 205, 20);
		frameArtista.getContentPane().add(fechaNacmiento);
		fechaNacmiento.setDate(artista.getFechaNac());

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(55, 379, 86, 23);
		frameArtista.getContentPane().add(btnAccept);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(269, 379, 86, 23);
		frameArtista.getContentPane().add(btnCancel);

		JLabel lblSite = new JLabel("Sitio Web:");
		lblSite.setBounds(29, 205, 104, 16);
		frameArtista.getContentPane().add(lblSite);

		siteField = new JTextField();
		siteField.setBounds(150, 202, 205, 22);
		frameArtista.getContentPane().add(siteField);
		siteField.setColumns(10);
		siteField.setText(artista.getSitioWeb());

		JLabel lblDesc = new JLabel("Descripci\u00F3n Gral:");
		lblDesc.setBounds(29, 242, 117, 16);
		frameArtista.getContentPane().add(lblDesc);

		descTextPane = new JTextPane();
		descTextPane.setBounds(150, 237, 205, 50);
		descTextPane.setText(artista.getDescripcion());
		frameArtista.getContentPane().add(descTextPane);
		
		JScrollPane scrollDesc = new JScrollPane(descTextPane);
		scrollDesc.setBounds(150, 242,  205, 50);
		frameArtista.getContentPane().add(scrollDesc);

		JLabel lblBio = new JLabel("Bio:");
		lblBio.setBounds(32, 304, 56, 16);
		frameArtista.getContentPane().add(lblBio);

		bioTextPane = new JTextPane();
		bioTextPane.setBounds(150, 298, 205, 50);
		bioTextPane.setText(artista.getBio());
		frameArtista.getContentPane().add(bioTextPane);
		
		JScrollPane scrollBio = new JScrollPane(bioTextPane);
		scrollBio.setBounds(150, 304,  205, 50);
		frameArtista.getContentPane().add(scrollBio);		
		

		// ----------------------------------------------------------------------------//

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (nombreField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(UpdateArtista.this.frameArtista, "Campo Nombre no puede ser vac\u00EDo",
							"Error al actualizar", JOptionPane.ERROR_MESSAGE);
				} else if (apellidoField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(UpdateArtista.this.frameArtista, "Campo Apellido no puede ser vac\u00EDo",
							"Error al actualizar", JOptionPane.ERROR_MESSAGE);
				} else if (fechaNacmiento.getDate() == null) {
					JOptionPane.showMessageDialog(UpdateArtista.this.frameArtista,
							"Campo Fecha de Nacimiento no puede ser vac\u00EDo", "Error al actualizar",
							JOptionPane.ERROR_MESSAGE);
				} else if (descTextPane.getText().isEmpty()) {
					JOptionPane.showMessageDialog(UpdateArtista.this.frameArtista, "Campo Descripci\\u00F3n Gral no puede ser vac\u00EDo",
							"Error al actualizar", JOptionPane.ERROR_MESSAGE);
				} else {
					UsuarioDTO user = new ArtistaDTO(nicknameField.getText(), nombreField.getText(),
							apellidoField.getText(), emailField.getText(), fechaNacmiento.getDate(),
							descTextPane.getText(), null, null, bioTextPane.getText(), siteField.getText(), null);
					try {
						factory.getIUsuarioController().actualizarUsuario(user);;
						JOptionPane.showMessageDialog(UpdateArtista.this.frameArtista, "Actualizacion exitosa!", "Alta Artista",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(UpdateArtista.this.frameArtista,
								"Ha ocurrido un error interno, intente mas tarde", "Error al actualizar",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				UpdateArtista.this.frameArtista.dispose();

			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}
}
