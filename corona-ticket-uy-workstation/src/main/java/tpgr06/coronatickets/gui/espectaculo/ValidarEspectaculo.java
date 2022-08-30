package tpgr06.coronatickets.gui.espectaculo;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.espectaculo.EstadoEspectaculo;

public class ValidarEspectaculo {

	private Factory factory = Factory.getInstance();
	List<EspectaculoDTO> espectaculos;
	
	private JInternalFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ValidarEspectaculo window = new ValidarEspectaculo();
					window.frame.setVisible(true);
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(window.frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ValidarEspectaculo() {
		try {
			espectaculos = factory.getIEspectaculoController().listarEspectaculosIngresados();
			initialize();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ValidarEspectaculo.this.frame,
					e.getMessage(), "Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JInternalFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("Aceptar/Rechazar Espect\u00E1culo");
		frame.setBounds(100, 100, 450, 300);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEspecName = new JLabel("Espect\u00E1culo:");
		lblEspecName.setBounds(12, 31, 90, 16);
		frame.getContentPane().add(lblEspecName);
		
		JComboBox<String> comboEspectaculos = new JComboBox<String>();
		comboEspectaculos.setBounds(114, 28, 281, 22);
		espectaculos.forEach(espec -> comboEspectaculos.addItem(espec.getNombre()));
		frame.getContentPane().add(comboEspectaculos);
		
		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(27, 226, 97, 25);
		frame.getContentPane().add(btnAccept);
		
		JButton btnReject = new JButton("Rechazar");
		btnReject.setBounds(175, 226, 97, 25);
		frame.getContentPane().add(btnReject);
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(314, 226, 97, 25);
		frame.getContentPane().add(btnCancel);
		
		// ----------------------------------------------------------------------------//

				ActionListener accept = new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						if (comboEspectaculos.getSelectedIndex() == -1) {
							JOptionPane.showMessageDialog(ValidarEspectaculo.this.frame, "Debe seleccionar un espectaculo",
									"Error al actualizar", JOptionPane.ERROR_MESSAGE);
						} else {
							
							try {
								EspectaculoDTO espectaculo = factory.getIEspectaculoController()
										.consultaEspectaculo(comboEspectaculos.getSelectedItem().toString());
								factory.getIEspectaculoController().cambiarEstadoEspectaculo(espectaculo, EstadoEspectaculo.ACEPTADO);
								JOptionPane.showMessageDialog(ValidarEspectaculo.this.frame, "Actualizacion exitosa!", "Actualizacion Espectaculo",
										JOptionPane.INFORMATION_MESSAGE);
								ValidarEspectaculo.this.frame.dispose();
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(ValidarEspectaculo.this.frame,
										e2.getMessage(), "Error al actualizar",
										JOptionPane.ERROR_MESSAGE);
							}

						}
					}
				};

				ActionListener reject = new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {

						if (comboEspectaculos.getSelectedIndex() == -1) {
							JOptionPane.showMessageDialog(ValidarEspectaculo.this.frame, "Debe seleccionar un espectaculo",
									"Error al actualizar", JOptionPane.ERROR_MESSAGE);
						} else {
							
							try {
								EspectaculoDTO espectaculo = factory.getIEspectaculoController()
										.consultaEspectaculo(comboEspectaculos.getSelectedItem().toString());
								factory.getIEspectaculoController().cambiarEstadoEspectaculo(espectaculo, EstadoEspectaculo.RECHAZADO);
								JOptionPane.showMessageDialog(ValidarEspectaculo.this.frame, "Actualizacion exitosa!", "Actualizacion Espectaculo",
										JOptionPane.INFORMATION_MESSAGE);
								ValidarEspectaculo.this.frame.dispose();
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(ValidarEspectaculo.this.frame,
										e2.getMessage(), "Error al actualizar",
										JOptionPane.ERROR_MESSAGE);
							}

						}
					}
				};
				
				ActionListener cancel = new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						ValidarEspectaculo.this.frame.dispose();

					}
				};

				btnAccept.addActionListener(accept);
				btnCancel.addActionListener(cancel);
				btnReject.addActionListener(reject);
	}
}
