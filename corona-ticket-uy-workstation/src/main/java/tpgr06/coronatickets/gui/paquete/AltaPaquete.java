package tpgr06.coronatickets.gui.paquete;

import com.toedter.calendar.JDateChooser;
import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AltaPaquete {

	private JInternalFrame framePaquete;
	private JTextField tFieldNombre;
	private JEditorPane descTextPane;
	private JTextField tFieldDescuento;
	JDateChooser fechaInicio;
	JDateChooser fechaFin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaPaquete window = new AltaPaquete();
					window.framePaquete.setVisible(true);
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(window.framePaquete);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AltaPaquete() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		framePaquete = new JInternalFrame();
		framePaquete.setTitle("Alta Paquete");
		framePaquete.setSize(510, 450);
		framePaquete.setBounds(300, 350, 400, 450);
		framePaquete.setResizable(false);
		framePaquete.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framePaquete.getContentPane().setLayout(null);

		JLabel lblNickname = new JLabel("Nombre del Paquete:");
		lblNickname.setBounds(29, 25, 143, 14);
		framePaquete.getContentPane().add(lblNickname);

		tFieldNombre = new JTextField();
		tFieldNombre.setBounds(188, 22, 186, 20);
		framePaquete.getContentPane().add(tFieldNombre);
		tFieldNombre.setColumns(10);

		JLabel lblNombre = new JLabel("Descripci\u00F3n");
		lblNombre.setBounds(29, 70, 86, 14);
		framePaquete.getContentPane().add(lblNombre);

		descTextPane = new JEditorPane();
		descTextPane.setBounds(0, 95, 186, 85);
		framePaquete.getContentPane().add(descTextPane);

		JScrollPane scrollDesc = new JScrollPane(descTextPane);
		scrollDesc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDesc.setBounds(188, 61,  186, 73);
		framePaquete.getContentPane().add(scrollDesc);

		JLabel lblFechaInicio = new JLabel("Fecha de inicio:");
		lblFechaInicio.setBounds(29, 172, 117, 14);
		framePaquete.getContentPane().add(lblFechaInicio);

		JLabel lblFechaFin = new JLabel("Fecha de fin:");
		lblFechaFin.setBounds(29, 213, 117, 14);
		framePaquete.getContentPane().add(lblFechaFin);

		fechaInicio = new JDateChooser();
		fechaInicio.setBounds(188, 166, 186, 20);
		framePaquete.getContentPane().add(fechaInicio);

		tFieldDescuento = new JTextField();
		tFieldDescuento.setBounds(188, 260, 186, 20);
		framePaquete.getContentPane().add(tFieldDescuento);

		fechaFin = new JDateChooser();
		fechaFin.setBounds(188, 213, 186, 20);
		framePaquete.getContentPane().add(fechaFin);

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(55, 379, 86, 23);
		framePaquete.getContentPane().add(btnAccept);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(269, 379, 86, 23);
		framePaquete.getContentPane().add(btnCancel);
		
		JLabel lblDescuento = new JLabel("Descuento:");
		lblDescuento.setBounds(29, 263, 117, 14);
		framePaquete.getContentPane().add(lblDescuento);
		

		// ----------------------------------------------------------------------------//

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
						Integer descuento = Integer.parseInt(tFieldDescuento.getText());
						Factory.getInstance().getIPaqueteController().altaPaquete(tFieldNombre.getText(), descTextPane.getText(), fechaInicio.getDate(), fechaFin.getDate(), descuento, "/media/img/paquete-generico.png");
						JOptionPane.showMessageDialog(AltaPaquete.this.framePaquete, "Alta exitosa!", "Alta de Paquete",
								JOptionPane.INFORMATION_MESSAGE);
						AltaPaquete.this.framePaquete.dispose();
					} catch (Exception exception) {
						if (exception.getCause().getCause() instanceof NumberFormatException) {
							JOptionPane.showMessageDialog(AltaPaquete.this.framePaquete,
									"El campo \"Descuento\" debe ser numerico", "Error al registrar",
									JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(AltaPaquete.this.framePaquete,
									exception.getMessage(), "Error al registrar",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AltaPaquete.this.framePaquete.dispose();

			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}
}
