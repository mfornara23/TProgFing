package tpgr06.coronatickets.gui.funcion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.espectaculo.IEspectaculoController;
import tpgr06.coronatickets.sys.exceptions.BadRequestException;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.paquete.IPaqueteController;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;

@SuppressWarnings("serial")
public class AltaFuncion extends JInternalFrame {
	
	private IPaqueteController paqueteController;
	private IEspectaculoController especController;

	private JTextField nombre;

	private JComboBox<String> comboBoxPlat;
	private JComboBox<String> comboBoxEspec;

	private JComboBox<String> hh;
	private JComboBox<String> mm;

	
	private JDateChooser fecha;
	private JDateChooser fechaAlta;
	
	private String[] horas = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
	private String[] min = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};


	private ActionListener platAL;
	private ActionListener especAL;
	private JTextField invitados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaFuncion frame = new AltaFuncion();
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
	public AltaFuncion() {
		
		Factory factory = Factory.getInstance();
		paqueteController = factory.getIPaqueteController();
		especController = factory.getIEspectaculoController();

		setClosable(false);
		setTitle("Alta de Funcion de Espectaculo");
		setBounds(100, 100, 600, 389);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{29, 189, 189, 189, 189, 189, 189, 189, 189, 189, 189, 189, 189, 0, 0};
		gridBagLayout.rowHeights = new int[]{22, 0, 0, 0, 0, 0, 0, 63, 0, 31, 57, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblPlataforma = new JLabel("Plataforma");
		lblPlataforma.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblPlataforma = new GridBagConstraints();
		gbc_lblPlataforma.gridwidth = 3;
		gbc_lblPlataforma.anchor = GridBagConstraints.WEST;
		gbc_lblPlataforma.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlataforma.gridx = 1;
		gbc_lblPlataforma.gridy = 1;
		getContentPane().add(lblPlataforma, gbc_lblPlataforma);
		
		comboBoxPlat = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 8;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboBoxPlat, gbc_comboBox);
		
		JLabel lblEspecatculo = new JLabel("Espectáculo");
		lblEspecatculo.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblEspecatculo = new GridBagConstraints();
		gbc_lblEspecatculo.gridwidth = 3;
		gbc_lblEspecatculo.anchor = GridBagConstraints.WEST;
		gbc_lblEspecatculo.insets = new Insets(0, 0, 5, 5);
		gbc_lblEspecatculo.gridx = 1;
		gbc_lblEspecatculo.gridy = 2;
		getContentPane().add(lblEspecatculo, gbc_lblEspecatculo);
		
		comboBoxEspec = new JComboBox<String>();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 8;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 5;
		gbc_comboBox_1.gridy = 2;
		getContentPane().add(comboBoxEspec, gbc_comboBox_1);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.gridwidth = 3;
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 3;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		nombre = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 8;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 3;
		getContentPane().add(nombre, gbc_textField);
		nombre.setColumns(10);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.gridwidth = 3;
		gbc_lblFecha.anchor = GridBagConstraints.WEST;
		gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha.gridx = 1;
		gbc_lblFecha.gridy = 4;
		getContentPane().add(lblFecha, gbc_lblFecha);
		
		fecha = new JDateChooser();
		GridBagConstraints gbc_lblD = new GridBagConstraints();
		gbc_lblD.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblD.gridwidth = 8;
		gbc_lblD.insets = new Insets(0, 0, 5, 5);
		gbc_lblD.gridx = 5;
		gbc_lblD.gridy = 4;
		getContentPane().add(fecha, gbc_lblD);
						
		JLabel lblHora = new JLabel("Hora");
		lblHora.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblHora = new GridBagConstraints();
		gbc_lblHora.anchor = GridBagConstraints.WEST;
		gbc_lblHora.gridwidth = 2;
		gbc_lblHora.insets = new Insets(0, 0, 5, 5);
		gbc_lblHora.gridx = 1;
		gbc_lblHora.gridy = 5;
		getContentPane().add(lblHora, gbc_lblHora);
		
		JLabel lblH = new JLabel("hh");
		GridBagConstraints gbc_lblH = new GridBagConstraints();
		gbc_lblH.anchor = GridBagConstraints.EAST;
		gbc_lblH.insets = new Insets(0, 0, 5, 5);
		gbc_lblH.gridx = 5;
		gbc_lblH.gridy = 5;
		getContentPane().add(lblH, gbc_lblH);
		
		hh = new JComboBox<String>(horas);
		GridBagConstraints gbc_comboBox_5 = new GridBagConstraints();
		gbc_comboBox_5.gridwidth = 2;
		gbc_comboBox_5.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_5.gridx = 6;
		gbc_comboBox_5.gridy = 5;
		getContentPane().add(hh, gbc_comboBox_5);
		
		JLabel lblM_1 = new JLabel("mm");
		GridBagConstraints gbc_lblM_1 = new GridBagConstraints();
		gbc_lblM_1.anchor = GridBagConstraints.EAST;
		gbc_lblM_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblM_1.gridx = 8;
		gbc_lblM_1.gridy = 5;
		getContentPane().add(lblM_1, gbc_lblM_1);
		
		mm = new JComboBox<String>(min);
		GridBagConstraints gbc_comboBox_6 = new GridBagConstraints();
		gbc_comboBox_6.gridwidth = 2;
		gbc_comboBox_6.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_6.gridx = 9;
		gbc_comboBox_6.gridy = 5;
		getContentPane().add(mm, gbc_comboBox_6);
		
		JLabel lblFechaAlta = new JLabel("Fecha de alta");
		lblFechaAlta.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
		gbc_lblFechaAlta.anchor = GridBagConstraints.WEST;
		gbc_lblFechaAlta.gridwidth = 3;
		gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaAlta.gridx = 1;
		gbc_lblFechaAlta.gridy = 6;
		getContentPane().add(lblFechaAlta, gbc_lblFechaAlta);
		
		fechaAlta = new JDateChooser();
		GridBagConstraints gbc_lblD_1 = new GridBagConstraints();
		gbc_lblD_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblD_1.gridwidth = 8;
		gbc_lblD_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblD_1.gridx = 5;
		gbc_lblD_1.gridy = 6;
		getContentPane().add(fechaAlta, gbc_lblD_1);
		
		JLabel lblArtistasInvitadosopcional = new JLabel("Artistas invitados (opcional)");
		GridBagConstraints gbc_lblArtistasInvitadosopcional = new GridBagConstraints();
		gbc_lblArtistasInvitadosopcional.anchor = GridBagConstraints.WEST;
		gbc_lblArtistasInvitadosopcional.gridwidth = 5;
		gbc_lblArtistasInvitadosopcional.insets = new Insets(0, 0, 5, 5);
		gbc_lblArtistasInvitadosopcional.gridx = 1;
		gbc_lblArtistasInvitadosopcional.gridy = 7;
		getContentPane().add(lblArtistasInvitadosopcional, gbc_lblArtistasInvitadosopcional);
		
		JButton btnCancelar = new JButton("Cancelar");
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.NORTH;
		gbc_btnCancelar.gridwidth = 5;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 8;
		gbc_btnCancelar.gridy = 10;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		invitados = new JTextField();
		GridBagConstraints gbc_txtArtistaartistaartista = new GridBagConstraints();
		gbc_txtArtistaartistaartista.gridwidth = 7;
		gbc_txtArtistaartistaartista.insets = new Insets(0, 0, 5, 5);
		gbc_txtArtistaartistaartista.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtArtistaartistaartista.gridx = 6;
		gbc_txtArtistaartistaartista.gridy = 7;
		getContentPane().add(invitados, gbc_txtArtistaartistaartista);
		invitados.setColumns(10);
		
		JLabel lblRubenRadaLa = new JLabel("(ej: nickname1;nickname2)");
		lblRubenRadaLa.setFont(new Font("Lucida Grande", Font.ITALIC, 12));
		GridBagConstraints gbc_lblRubenRadaLa = new GridBagConstraints();
		gbc_lblRubenRadaLa.anchor = GridBagConstraints.WEST;
		gbc_lblRubenRadaLa.gridwidth = 5;
		gbc_lblRubenRadaLa.insets = new Insets(0, 0, 5, 5);
		gbc_lblRubenRadaLa.gridx = 1;
		gbc_lblRubenRadaLa.gridy = 8;
		getContentPane().add(lblRubenRadaLa, gbc_lblRubenRadaLa);
	
		JButton btnConfirmar = new JButton("Aceptar");
		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.anchor = GridBagConstraints.NORTH;
		gbc_btnConfirmar.gridwidth = 5;
		gbc_btnConfirmar.insets = new Insets(0, 0, 0, 5);
		gbc_btnConfirmar.gridx = 3;
		gbc_btnConfirmar.gridy = 10;
		getContentPane().add(btnConfirmar, gbc_btnConfirmar);
		
		//--------------------------------------------------------------------------------------//
		
		ActionListener confirmar = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (nombre.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaFuncion.this, "Campo nombre no puede ser vacio",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (comboBoxEspec.getSelectedIndex()==-1) {
					JOptionPane.showMessageDialog(AltaFuncion.this, "Debe seleccionar un espectaculo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (hh.getSelectedIndex()==-1) {
					JOptionPane.showMessageDialog(AltaFuncion.this, "Debe seleccionar una hora",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (mm.getSelectedIndex()==-1) {
					JOptionPane.showMessageDialog(AltaFuncion.this, "Debe seleccionar una hora",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (fecha.getDate() == null) {
					JOptionPane.showMessageDialog(AltaFuncion.this,
							"Campo Fecha no puede ser vacio", "Error al registrar",
							JOptionPane.ERROR_MESSAGE);
				} else if (fechaAlta.getDate() == null) {
					JOptionPane.showMessageDialog(AltaFuncion.this,
							"Campo Fecha de alta no puede ser vacio", "Error al registrar",
							JOptionPane.ERROR_MESSAGE);
				} else {
						try {
						altaFuncion();
						JOptionPane.showMessageDialog(AltaFuncion.this, "Alta exitosa!", "Alta Funcion",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();

					} catch (Exception e2) {
						JOptionPane.showMessageDialog(AltaFuncion.this,
								e2.getMessage(), "Error al registrar",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		};

		platAL = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String plat = (String) comboBoxPlat.getSelectedItem();
				comboBoxEspec.removeActionListener(especAL);
				liberarEspectaculos();
				try {
					cargarEspectaculos(plat);
				} catch (NotFoundException e1) {
					e1.printStackTrace();
				}
				comboBoxEspec.addActionListener(especAL);
			}
		};
			
		btnConfirmar.addActionListener(confirmar);

		comboBoxPlat.addActionListener(platAL);
		cargarPlataformas();

	}
	
	private void cargarPlataformas() {
		List<PlataformaDTO> plats = paqueteController.listarPlataformas();
		if(plats.isEmpty()) {
			JOptionPane.showMessageDialog(AltaFuncion.this,
					"No hay plataformas en el sistema", "Error",
					JOptionPane.WARNING_MESSAGE);
			dispose();
		}
		for(PlataformaDTO p:plats) {
			comboBoxPlat.addItem(p.getNombre());
		}
	}
	
	private void cargarEspectaculos(String plat) throws NotFoundException {
		List<EspectaculoDTO> espec = especController.listarEspectaculos(plat);
		if(espec.isEmpty()) {
			JOptionPane.showMessageDialog(AltaFuncion.this,
					"La plataforma "+plat+" no tiene espectaculos registrados", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		for(EspectaculoDTO e:espec) {
			comboBoxEspec.addItem(e.getNombre());
		
		}
	}
		
	private void liberarEspectaculos(){
		comboBoxEspec.removeAllItems();
	}
	@SuppressWarnings("deprecation")
	private void altaFuncion() throws NotFoundException, BadRequestException {
		Date d = fecha.getDate();
		Integer minutes = Integer.parseInt((String)mm.getSelectedItem());
		Integer hours = Integer.parseInt((String)hh.getSelectedItem());
		d.setMinutes(minutes);
		d.setHours(hours);
		String[] invit;
		if(invitados.getText().equals("")) {
			invit=null;
		}else {
			invit = invitados.getText().split(";");
		}
		especController.altaFuncion((String)comboBoxPlat.getSelectedItem(), (String)comboBoxEspec.getSelectedItem(),nombre.getText() , d, invit, fechaAlta.getDate(), "/media/img/funcion.png");
		
	}
	

}
