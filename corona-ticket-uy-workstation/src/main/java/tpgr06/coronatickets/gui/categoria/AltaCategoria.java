package tpgr06.coronatickets.gui.categoria;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;

public class AltaCategoria {

	private Factory factory = Factory.getInstance();
	private JInternalFrame frame;
	private JTextField categoryNameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaCategoria window = new AltaCategoria();
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
	public AltaCategoria() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JInternalFrame();
		frame.setTitle("Alta Categor\u00EDa");
		frame.setBounds(100, 100, 450, 300);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCategoryName = new JLabel("Nombre:");
		lblCategoryName.setBounds(12, 31, 93, 16);
		frame.getContentPane().add(lblCategoryName);
		
		categoryNameField = new JTextField();
		categoryNameField.setBounds(101, 28, 300, 22);
		frame.getContentPane().add(categoryNameField);
		categoryNameField.setColumns(10);
		
		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(70, 226, 97, 25);
		frame.getContentPane().add(btnAccept);
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(274, 226, 97, 25);
		frame.getContentPane().add(btnCancel);
		
		
		// ----------------------------------------------------------------------------//

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (categoryNameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaCategoria.this.frame, "Campo Categoria no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else {
					
					try {
						factory.getIEspectaculoController().altaCategoria(categoryNameField.getText());
						JOptionPane.showMessageDialog(AltaCategoria.this.frame, "Alta exitosa!", "Alta Categoria",
								JOptionPane.INFORMATION_MESSAGE);
						AltaCategoria.this.frame.dispose();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(AltaCategoria.this.frame,
								e2.getMessage(), "Error al dar de alta",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AltaCategoria.this.frame.dispose();

			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}
}
