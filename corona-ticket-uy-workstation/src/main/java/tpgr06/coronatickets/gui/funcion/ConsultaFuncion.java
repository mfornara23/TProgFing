package tpgr06.coronatickets.gui.funcion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.espectaculo.IEspectaculoController;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.paquete.IPaqueteController;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;

@SuppressWarnings("serial")
public class ConsultaFuncion extends JInternalFrame {
	
	private IPaqueteController paqueteController;
	private IEspectaculoController especController;
	
	private JPanel panel;
	private JLabel nombre;
	private JLabel xnombre;
	private JLabel fecha;
	private JLabel xfecha;
	private JLabel invitados;
	private JLabel xinvitados;
	private JLabel fechaReg;
	private JLabel xfechaReg;
	private JLabel espec;
	private JLabel xespec;
	private JComboBox<String> comboBoxPlat;
	private JComboBox<String> comboBoxEspec;
	private JComboBox<String> comboBoxFunc;
	SimpleDateFormat formatInicio = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	SimpleDateFormat formatRegistro = new SimpleDateFormat("dd/MM/yyyy");

	private ActionListener platAL;
	private ActionListener especAL;
	private ActionListener funcAL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaFuncion frame = new ConsultaFuncion();
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
	public ConsultaFuncion() {
		
		Factory factory = Factory.getInstance();
		paqueteController = factory.getIPaqueteController();
		especController = factory.getIEspectaculoController();

		setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(false);
        setBounds(0, 0, 550, 315);
        setTitle("Consultar un Usuario");
		setTitle("Consulta de Funcion de Espectaculo");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{42, 102, 0, 193, 0, 0};
		gridBagLayout.rowHeights = new int[]{10, 0, 0, 0, 107, 35, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		
		JLabel lblPlataforma = new JLabel("Plataforma");
		lblPlataforma.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblPlataforma = new GridBagConstraints();
		gbc_lblPlataforma.anchor = GridBagConstraints.WEST;
		gbc_lblPlataforma.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlataforma.gridx = 1;
		gbc_lblPlataforma.gridy = 1;
		getContentPane().add(lblPlataforma, gbc_lblPlataforma);
		
		comboBoxPlat = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 1;
				

		getContentPane().add(comboBoxPlat, gbc_comboBox);
		
		JLabel lblEspecatculo = new JLabel("Espectaculo");
		lblEspecatculo.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblEspecatculo = new GridBagConstraints();
		gbc_lblEspecatculo.anchor = GridBagConstraints.WEST;
		gbc_lblEspecatculo.insets = new Insets(0, 0, 5, 5);
		gbc_lblEspecatculo.gridx = 1;
		gbc_lblEspecatculo.gridy = 2;
		getContentPane().add(lblEspecatculo, gbc_lblEspecatculo);
	
		comboBoxEspec = new JComboBox<String>();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 3;
		gbc_comboBox_1.gridy = 2;
		getContentPane().add(comboBoxEspec, gbc_comboBox_1);
				
		JLabel lblFuncion = new JLabel("Funcion");
		lblFuncion.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblFuncion = new GridBagConstraints();
		gbc_lblFuncion.anchor = GridBagConstraints.WEST;
		gbc_lblFuncion.insets = new Insets(0, 0, 5, 5);
		gbc_lblFuncion.gridx = 1;
		gbc_lblFuncion.gridy = 3;
		getContentPane().add(lblFuncion, gbc_lblFuncion);
		
		comboBoxFunc = new JComboBox<String>();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 3;
		gbc_comboBox_2.gridy = 3;
		getContentPane().add(comboBoxFunc, gbc_comboBox_2);
		
		panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.highlight"));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{148, 224, 0};
		gbl_panel.rowHeights = new int[]{16, 16, 16, 16, 16, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		nombre = new JLabel(" Nombre:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(nombre, gbc_lblNewLabel);
		
		xnombre = new JLabel("");
		xnombre.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 0;
		panel.add(xnombre, gbc_lblNewLabel_4);
		
		fecha = new JLabel(" Fecha:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(fecha, gbc_lblNewLabel_1);
		
		xfecha = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 1;
		panel.add(xfecha, gbc_lblNewLabel_2);
		
		invitados = new JLabel(" Artistas invitados:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 2;
		panel.add(invitados, gbc_lblNewLabel_5);
		
		xinvitados = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 2;
		panel.add(xinvitados, gbc_lblNewLabel_3);
		
		fechaReg = new JLabel(" Fecha de registro:");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 3;
		panel.add(fechaReg, gbc_lblNewLabel_7);
		
		xfechaReg = new JLabel("");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.fill = GridBagConstraints.BOTH;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 0);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 3;
		panel.add(xfechaReg, gbc_lblNombre);
		
		espec = new JLabel(" Espectadores:");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 4;
		panel.add(espec, gbc_lblNewLabel_6);
		
		xespec = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_8.gridx = 1;
		gbc_lblNewLabel_8.gridy = 4;
		panel.add(xespec, gbc_lblNewLabel_8);

		panel.setVisible(false);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GridBagConstraints gbc_btnCerrar = new GridBagConstraints();
		gbc_btnCerrar.anchor = GridBagConstraints.EAST;
		gbc_btnCerrar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCerrar.gridx = 3;
		gbc_btnCerrar.gridy = 5;
		getContentPane().add(btnCerrar, gbc_btnCerrar);
		
		platAL = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String plat = (String) comboBoxPlat.getSelectedItem();
				comboBoxEspec.removeActionListener(especAL);
				comboBoxFunc.removeActionListener(funcAL);
				liberarEspectaculos();
				liberarFunciones();
				ocultarPanelFuncion();
				try {
					if(cargarEspectaculos(plat))
						comboBoxEspec.addActionListener(especAL);
				} catch (NotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		especAL = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String espec = (String) comboBoxEspec.getSelectedItem();
				comboBoxFunc.removeActionListener(funcAL);
				liberarFunciones();
				ocultarPanelFuncion();
				if(cargarFunciones(espec));	
					comboBoxFunc.addActionListener(funcAL);
			}
		};
		
		funcAL = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String func = (String) comboBoxFunc.getSelectedItem();
				String espec = (String) comboBoxEspec.getSelectedItem();

				try {
					mostrarInfoFuncion(func,espec);
				} catch (NotFoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		comboBoxPlat.addActionListener(platAL);
		cargarPlataformas();
		
	}

	private void mostrarInfoFuncion(String nombreFunc, String espect) throws NotFoundException {
		FuncionDTO f = especController.getFuncionEspectaculo(nombreFunc, espect);
		xnombre.setText(f.getNombre());
		xfecha.setText(formatInicio.format(f.getFecha()));
		List<String> invitados = new LinkedList<String>();
		for(ArtistaDTO a:f.getInvitados())
			invitados.add(a.getNombre()+" "+a.getApellido());
		xinvitados.setText(invitados.toString());
		xfechaReg.setText(formatRegistro.format(f.getFechaReg()));	
		xespec.setText(f.getCantEspectadores().toString());
	
		panel.setVisible(true);
	}
	
	private void ocultarPanelFuncion() {
		panel.setVisible(false);
	}
	
	private void cargarPlataformas() {
		List<PlataformaDTO> plats = paqueteController.listarPlataformas();
		if(plats.isEmpty()) {
			JOptionPane.showMessageDialog(ConsultaFuncion.this,
					"No hay plataformas en el sistema", "Error",
					JOptionPane.WARNING_MESSAGE);
			dispose();
		}
		for(PlataformaDTO p:plats) {
			comboBoxPlat.addItem(p.getNombre());
		}
	}
	
	private Boolean cargarEspectaculos(String plat) throws NotFoundException {
		List<EspectaculoDTO> espec = especController.listarEspectaculos(plat);
		if(espec.isEmpty()) {
			JOptionPane.showMessageDialog(ConsultaFuncion.this,
					"No hay espectaculos en la plataforma "+plat, "Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		for(EspectaculoDTO e:espec) {
			comboBoxEspec.addItem(e.getNombre());
		}
		return true;
	}
		
	private void liberarEspectaculos(){
		comboBoxEspec.removeAllItems();
	}
	
	private Boolean cargarFunciones(String espec) {
		List<FuncionDTO> funciones = especController.listarFuncionesByEspectaculo(espec);
		if(funciones.isEmpty()) {
			JOptionPane.showMessageDialog(ConsultaFuncion.this,
					"No hay funciones para el espectaculo "+espec, "Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		for(FuncionDTO f:funciones) {
			comboBoxFunc.addItem(f.getNombre());
		}
		return true;
	}
	
	private void liberarFunciones() {
		comboBoxFunc.removeAllItems();
	}
	
	
	
	
	
}

