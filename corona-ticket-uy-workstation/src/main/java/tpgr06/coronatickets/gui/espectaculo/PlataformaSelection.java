package tpgr06.coronatickets.gui.espectaculo;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;

public class PlataformaSelection {
	
	private Factory factory = Factory.getInstance();
	private List<PlataformaDTO> plataformasDTO;

	private JFrame frmSeleccinDePlataforma;
	private JComboBox<String> plataformaList;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlataformaSelection window = new PlataformaSelection();
					window.frmSeleccinDePlataforma.setVisible(true);
					window.frmSeleccinDePlataforma.setLocationRelativeTo(MenuPrincipal.frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlataformaSelection() {
		plataformasDTO = factory.getIPaqueteController().listarPlataformas();
		if (plataformasDTO.isEmpty()) {
			JOptionPane.showMessageDialog(PlataformaSelection.this.frmSeleccinDePlataforma,
					"No hay plataformas en el sistema", "Error",
					JOptionPane.WARNING_MESSAGE);
			PlataformaSelection.this.frmSeleccinDePlataforma.dispose();	
		} else {
			initialize();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSeleccinDePlataforma = new JFrame();
		frmSeleccinDePlataforma.setType(Type.UTILITY);
		frmSeleccinDePlataforma.setTitle("Consulta de Espectaculo");
		frmSeleccinDePlataforma.setResizable(false);
		frmSeleccinDePlataforma.setBounds(100, 100, 312, 233);
		frmSeleccinDePlataforma.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSeleccinDePlataforma.getContentPane().setLayout(null);
		
		JLabel lblCoronaTicket = new JLabel("Seleccione la Plataforma:");
		lblCoronaTicket.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoronaTicket.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCoronaTicket.setBounds(20, 11, 276, 16);
		frmSeleccinDePlataforma.getContentPane().add(lblCoronaTicket);
		
		
		plataformaList = new JComboBox<String>();
		plataformaList.setBounds(59, 53, 202, 22);
		plataformaList.setModel(new DefaultComboBoxModel<String>());
		plataformasDTO.forEach(plataforma -> plataformaList.addItem(plataforma.getNombre()));
		frmSeleccinDePlataforma.getContentPane().add(plataformaList);
		
		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(35, 160, 97, 25);
		frmSeleccinDePlataforma.getContentPane().add(btnAccept);
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(178, 160, 97, 25);
		frmSeleccinDePlataforma.getContentPane().add(btnCancel);
		
		//----------------------------------------------------------------------------//
		
		ActionListener accept = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				ConsultaEspectaculo.main(plataformaList.getSelectedItem().toString());
				PlataformaSelection.this.frmSeleccinDePlataforma.dispose();	
			}
		};
		
		ActionListener cancel =  new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				PlataformaSelection.this.frmSeleccinDePlataforma.dispose();				
			}
		};
		
		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}
}
