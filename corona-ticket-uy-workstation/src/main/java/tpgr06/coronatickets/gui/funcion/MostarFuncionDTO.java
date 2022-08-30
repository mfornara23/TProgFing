package tpgr06.coronatickets.gui.funcion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;

@SuppressWarnings("serial")
public class MostarFuncionDTO extends JInternalFrame {
	
	SimpleDateFormat formatInicio = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	SimpleDateFormat formatRegistro = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Launch the application.
	 */
	public static void main(final FuncionDTO f) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MostarFuncionDTO frame = new MostarFuncionDTO(f);
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(frame);
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
	public MostarFuncionDTO(FuncionDTO f) {
		setClosable(true);
		setBounds(100, 100, 416, 212);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 187, 0, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		getContentPane().add(lblNombre, gbc_lblNombre);

		JLabel lblNewLabel_3 = new JLabel(f.getNombre());
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 3;
		gbc_lblNewLabel_3.gridy = 1;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

		JLabel lblNewLabel = new JLabel("Fecha:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_4 = new JLabel(formatInicio.format(f.getFecha()));
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 3;
		gbc_lblNewLabel_4.gridy = 2;
		getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);

		JLabel lblNewLabel_1 = new JLabel("Artistas invitados:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);


		List<String> invitados = new LinkedList<String>();
		if(f.getInvitados()!=null) {
			for(ArtistaDTO a:f.getInvitados())
				invitados.add(a.getNombre()+" "+a.getApellido());
		}
		JLabel lblNewLabel_5 = new JLabel(invitados.toString());
		lblNewLabel_5.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 3;
		getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);

		JLabel lblNewLabel_2 = new JLabel("Fecha de registro:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 4;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

		JLabel lblNewLabel_6 = new JLabel(formatRegistro.format(f.getFechaReg()));
		lblNewLabel_6.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 3;
		gbc_lblNewLabel_6.gridy = 4;
		getContentPane().add(lblNewLabel_6, gbc_lblNewLabel_6);

		JLabel lblEspectadores = new JLabel("Espectadores:");
		GridBagConstraints gbc_lblEspectadores = new GridBagConstraints();
		gbc_lblEspectadores.insets = new Insets(0, 0, 0, 5);
		gbc_lblEspectadores.anchor = GridBagConstraints.WEST;
		gbc_lblEspectadores.gridx = 1;
		gbc_lblEspectadores.gridy = 5;
		getContentPane().add(lblEspectadores, gbc_lblEspectadores);

		JLabel lblNewLabel_7 = new JLabel(String.valueOf(f.getCantEspectadores()));
		lblNewLabel_7.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_7.gridx = 3;
		gbc_lblNewLabel_7.gridy = 5;
		getContentPane().add(lblNewLabel_7, gbc_lblNewLabel_7);

	}




}
