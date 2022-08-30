package tpgr06.coronatickets.gui.plataforma;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;

public class AltaPlataforma extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Factory factory = Factory.getInstance();
	private JPanel contentPane;
	private JTextField nombre_plataforma;
	private JTextField URL_plataforma;
	private JTextPane desc_plataforma;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaPlataforma frame = new AltaPlataforma();
					frame.setVisible(true);
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AltaPlataforma() {
		setTitle("Alta Plataforma");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nombre_etiqueta = new JLabel("Nombre: ");
		nombre_etiqueta.setHorizontalAlignment(SwingConstants.LEFT);
		nombre_etiqueta.setBounds(10, 35, 72, 14);
		contentPane.add(nombre_etiqueta);
		
		JLabel desc_etiqueta = new JLabel("Descripcion:");
		desc_etiqueta.setHorizontalAlignment(SwingConstants.LEFT);
		desc_etiqueta.setBounds(10, 84, 82, 14);
		contentPane.add(desc_etiqueta);
		
		JLabel URL_etiqueta = new JLabel("URL:");
		URL_etiqueta.setHorizontalAlignment(SwingConstants.LEFT);
		URL_etiqueta.setBounds(10, 184, 46, 14);
		contentPane.add(URL_etiqueta);
		
		nombre_plataforma = new JTextField();
		nombre_plataforma.setBounds(102, 32, 292, 20);
		contentPane.add(nombre_plataforma);
		nombre_plataforma.setColumns(10);
		
		desc_plataforma = new JTextPane();
		desc_plataforma.setBounds(150, 237, 205, 50);
		getContentPane().add(desc_plataforma);
		
		JScrollPane scrollDesc = new JScrollPane(desc_plataforma);
		scrollDesc.setBounds(104, 84,  292, 75);
		getContentPane().add(scrollDesc);
		
		URL_plataforma = new JTextField();
		URL_plataforma.setBounds(102, 181, 292, 20);
		contentPane.add(URL_plataforma);
		URL_plataforma.setColumns(10);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.setBounds(102, 228, 89, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Que hace el Boton "Aceptar"
				
				// Reviso si los campos estan vacios
				if( nombre_plataforma.getText().isEmpty() ){
					JOptionPane.showMessageDialog(null,"Campo Nombre no puede ser vac\u00EDo",
							"Error de Alta Plataforma", JOptionPane.ERROR_MESSAGE);
				} else if (desc_plataforma.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo Descripcion no puede ser vac\u00EDo",
							"Error de Alta Plataforma", JOptionPane.ERROR_MESSAGE);
				} else if (URL_plataforma.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campo URL no puede ser vac\u00EDo",
							"Error de Alta Plataforma", JOptionPane.ERROR_MESSAGE);
					
				// Verifico si la plataforma ya pertenece
				}else if( factory.getIPaqueteController().existePlataforma(nombre_plataforma.getText()) ) {
					JOptionPane.showMessageDialog(null,"Ya existe una plataforma de nombre: "+nombre_plataforma.getText(),
							"Error", JOptionPane.ERROR_MESSAGE);
					
				// Creamos la plataforma
				}else{
					try {
						factory.getIPaqueteController().altaPlataforma(nombre_plataforma.getText(), desc_plataforma.getText(),
								URL_plataforma.getText());
						JOptionPane.showMessageDialog(null,"Se dio de Alta la plataforma: "+nombre_plataforma.getText(),
								"Alta Confirmada", JOptionPane.INFORMATION_MESSAGE);
						AltaPlataforma.this.dispose();
					} catch (BadRequestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		
		JButton btncancelar = new JButton("Cancelar");
		btncancelar.setBounds(305, 228, 89, 23);
		contentPane.add(btncancelar);
		// Que hace el Boton "Cancelar"
		ActionListener cancel = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();  // Cierra la Ventana
			}
		};
		btncancelar.addActionListener(cancel);
	}
}
