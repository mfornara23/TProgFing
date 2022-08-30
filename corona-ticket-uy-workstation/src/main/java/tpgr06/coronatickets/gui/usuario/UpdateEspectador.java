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
import tpgr06.coronatickets.sys.usuario.espectador.EspectadorDTO;

public class UpdateEspectador {

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
	public static void main(final EspectadorDTO espectador) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateEspectador window = new UpdateEspectador(espectador);
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
	public UpdateEspectador(EspectadorDTO espectador) {
		initialize(espectador);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(EspectadorDTO espectador) {
		frameEspectador = new JInternalFrame();
		frameEspectador.setTitle("Actualizar Espectador");
		frameEspectador.setSize(400, 450);
		frameEspectador.setBounds(300, 350, 400, 450);
		frameEspectador.setResizable(false);
		frameEspectador.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameEspectador.getContentPane().setLayout(null);

		JLabel lblNickname = new JLabel("Nickname");
		lblNickname.setBounds(29, 22, 59, 14);
		frameEspectador.getContentPane().add(lblNickname);

		nicknameField = new JTextField();
		nicknameField.setEditable(false);
		nicknameField.setBounds(150, 19, 205, 20);
		frameEspectador.getContentPane().add(nicknameField);
		nicknameField.setColumns(10);
		nicknameField.setText(espectador.getNickname());

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(29, 58, 59, 14);
		frameEspectador.getContentPane().add(lblNombre);

		nombreField = new JTextField();
		nombreField.setBounds(150, 55, 205, 20);
		frameEspectador.getContentPane().add(nombreField);
		nombreField.setColumns(10);
		nombreField.setText(espectador.getNombre());

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(29, 94, 59, 14);
		frameEspectador.getContentPane().add(lblApellido);

		apellidoField = new JTextField();
		apellidoField.setBounds(150, 91, 205, 20);
		frameEspectador.getContentPane().add(apellidoField);
		apellidoField.setColumns(10);
		apellidoField.setText(espectador.getApellido());

		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(29, 127, 59, 14);
		frameEspectador.getContentPane().add(lblEmail);

		emailField = new JTextField();
		emailField.setEditable(false);
		emailField.setBounds(150, 124, 205, 20);
		frameEspectador.getContentPane().add(emailField);
		emailField.setColumns(10);
		emailField.setText(espectador.getEmail());

		JLabel lblFecha = new JLabel("Fecha Nacimiento:");
		lblFecha.setBounds(29, 166, 117, 14);
		frameEspectador.getContentPane().add(lblFecha);

		fechaNacmiento = new JDateChooser();
		fechaNacmiento.setBounds(150, 160, 205, 20);
		frameEspectador.getContentPane().add(fechaNacmiento);
		fechaNacmiento.setDate(espectador.getFechaNac());

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(55, 379, 86, 23);
		frameEspectador.getContentPane().add(btnAccept);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(269, 379, 86, 23);
		frameEspectador.getContentPane().add(btnCancel);

		// ----------------------------------------------------------------------------//

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (nombreField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(UpdateEspectador.this.frameEspectador,
							"Campo Nombre no puede ser vac\u00EDo", "Error al actualizar", JOptionPane.ERROR_MESSAGE);
				} else if (apellidoField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(UpdateEspectador.this.frameEspectador,
							"Campo Apellido no puede ser vac\u00EDo", "Error al actualizar", JOptionPane.ERROR_MESSAGE);
				} else if (fechaNacmiento.getDate() == null) {
					JOptionPane.showMessageDialog(UpdateEspectador.this.frameEspectador,
							"Campo Fecha de Nacimiento no puede ser vac\u00EDo", "Error al actualizar",
							JOptionPane.ERROR_MESSAGE);
				} else {
					UsuarioDTO user = new EspectadorDTO(nicknameField.getText(), nombreField.getText(),
							apellidoField.getText(), emailField.getText(), fechaNacmiento.getDate(), null, null, null);
					try {
						factory.getIUsuarioController().actualizarUsuario(user);
						JOptionPane.showMessageDialog(UpdateEspectador.this.frameEspectador, "Actualizacion exitosa!",
								"Alta Espectador", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(UpdateEspectador.this.frameEspectador,
								"Ha ocurrido un error interno, intente mas tarde", "Error al actualizar",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				UpdateEspectador.this.frameEspectador.dispose();

			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}

}
