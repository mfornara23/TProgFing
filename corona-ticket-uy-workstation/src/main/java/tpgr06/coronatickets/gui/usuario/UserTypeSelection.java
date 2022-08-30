package tpgr06.coronatickets.gui.usuario;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.usuario.TipoUsuario;

import javax.swing.JComboBox;
import java.awt.Window.Type;

public class UserTypeSelection {

	private JFrame frmSeleccinDeUsuario;
	private JComboBox<String> comboUsuario;
	private final String[] PERFILES = {TipoUsuario.ARTISTA.toString(),TipoUsuario.ESPECTADOR.toString()};
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserTypeSelection window = new UserTypeSelection();
					window.frmSeleccinDeUsuario.setVisible(true);
					window.frmSeleccinDeUsuario.setLocationRelativeTo(MenuPrincipal.frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserTypeSelection() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSeleccinDeUsuario = new JFrame();
		frmSeleccinDeUsuario.setType(Type.UTILITY);
		frmSeleccinDeUsuario.setTitle("Selecci\u00F3n de Usuario");
		frmSeleccinDeUsuario.setResizable(false);
		frmSeleccinDeUsuario.setBounds(100, 100, 312, 233);
		frmSeleccinDeUsuario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSeleccinDeUsuario.getContentPane().setLayout(null);
		
		
		JLabel lblCoronaTicket = new JLabel("Seleccione Tipo de Usuario:");
		lblCoronaTicket.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoronaTicket.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCoronaTicket.setBounds(20, 11, 276, 16);
		frmSeleccinDeUsuario.getContentPane().add(lblCoronaTicket);
		
		comboUsuario = new JComboBox<String>();
		comboUsuario.setBounds(59, 53, 202, 22);
		comboUsuario.setModel(new DefaultComboBoxModel<String>(PERFILES));
		frmSeleccinDeUsuario.getContentPane().add(comboUsuario);
		
		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(35, 160, 97, 25);
		frmSeleccinDeUsuario.getContentPane().add(btnAccept);
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(178, 160, 97, 25);
		frmSeleccinDeUsuario.getContentPane().add(btnCancel);
		
		//----------------------------------------------------------------------------//
		
		ActionListener accept = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(comboUsuario.getSelectedItem().toString().equals(TipoUsuario.ARTISTA.toString())) {
					AltaArtista.main(null);
				}else {
					AltaEspectador.main(null);
				}
				UserTypeSelection.this.frmSeleccinDeUsuario.dispose();	
			}
		};
		
		ActionListener cancel =  new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				UserTypeSelection.this.frmSeleccinDeUsuario.dispose();				
			}
		};
		
		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}
}
