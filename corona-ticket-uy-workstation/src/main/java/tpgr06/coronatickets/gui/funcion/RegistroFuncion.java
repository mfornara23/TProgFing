package tpgr06.coronatickets.gui.funcion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.espectaculo.IEspectaculoController;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.paquete.IPaqueteController;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;
import tpgr06.coronatickets.sys.usuario.IUsuarioController;
import tpgr06.coronatickets.sys.usuario.UsuarioDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;
import tpgr06.coronatickets.sys.usuario.espectador.EspectadorDTO;
import tpgr06.coronatickets.sys.usuario.espectador.RegistroFuncionDTO;

@SuppressWarnings("serial")
public class RegistroFuncion extends JInternalFrame {

	private IPaqueteController paqueteController;
	private IEspectaculoController especController;
	private IUsuarioController usuarioController;

	SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");

	private String[] columnsFuncion = { "Nombre", "Fecha", "Hora", "Artistas invitados", "Fecha Alta"};
	private String[] columnsEspectador = {"Nickname", "Nombre", "Apellido", "Email", "Fecha de Nacimiento"};

	private Object[][] dataFuncion;

	private Object[][] dataEspectadores;

	private final int EMAIL_COL = 3;
	private final int NOMBRE_COL = 0;


	private JTable tableFuncion = null;
	private JTable tableEspectador = null;

	private JScrollPane scrollPane = null;
	private JScrollPane scrollPane2 = null;

	private JDateChooser fecha=null;

	private ActionListener platAL;
	private ListSelectionListener espectadorLL;
	private ActionListener accept;
	private ActionListener cancel;

	private JComboBox<String> comboBoxEspec;
	private JComboBox<String> comboBoxPlat;

	private JComboBox<String> comboBoxR1;
	private JComboBox<String> comboBoxR2;
	private JComboBox<String> comboBoxR3;

	private String plataforma;
	private String espectaculo;
	private String funcion;
	private String email;
	private String r1="Seleccionar";
	private String r2="Seleccionar";
	private String r3="Seleccionar";

	private Boolean puedeCanjear=false;
	private ActionListener confirmarAL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroFuncion window = new RegistroFuncion();
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(window);
					window.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void refresh() {
		revalidate();
		repaint();
	}

	private void cargarPlataformas() {
		List<PlataformaDTO> plats = paqueteController.listarPlataformas();
		if(plats.isEmpty()) {
			JOptionPane.showMessageDialog(RegistroFuncion.this,
					"No hay plataformas en el sistema", "Error al registrar",
					JOptionPane.WARNING_MESSAGE);
			dispose();
		}
		for(PlataformaDTO p:plats) {
			comboBoxPlat.addItem(p.getNombre());
		}
	}

	private void mostrarPlataformas() {

		JLabel lblPlataforma = new JLabel("Plataforma");
		GridBagConstraints gbc_lblPlataforma = new GridBagConstraints();
		gbc_lblPlataforma.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlataforma.gridx = 1;
		gbc_lblPlataforma.gridy = 1;
		getContentPane().add(lblPlataforma, gbc_lblPlataforma);

		comboBoxPlat = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboBoxPlat, gbc_comboBox);	

		cargarPlataformas();

		comboBoxPlat.addActionListener(platAL);


	}

	private void cargarEspectaculos(String plat) throws NotFoundException {
		liberarEspectaculos();
		List<EspectaculoDTO> espec = especController.listarEspectaculos(plat);
		if(espec.isEmpty()) {
			JOptionPane.showMessageDialog(RegistroFuncion.this,
					"No hay espectaculos en esta plataforma", "Error al registrar",
					JOptionPane.WARNING_MESSAGE);
		}else {

			for(EspectaculoDTO e:espec) {
				comboBoxEspec.addItem(e.getNombre());
			}
		}
	}

	private void liberarEspectaculos(){
		comboBoxEspec.removeAllItems();
	}

	private void liberarFunciones() {
		if(scrollPane!=null) {
			getContentPane().remove(tableFuncion);
			getContentPane().remove(scrollPane);
		}
	}
	private void mostrarFunciones(String espec) throws NotFoundException {
		liberarFunciones();
		List<FuncionDTO> funciones = especController.listarFuncionesByEspectaculo(espec);
		if(funciones.isEmpty()) {
			JOptionPane.showMessageDialog(RegistroFuncion.this,
					"No existen funciones para el espectaculo "+espec, "Error al registrar",
					JOptionPane.WARNING_MESSAGE);
			throw new NotFoundException("No hay funciones");
		}else {
			mostrarSeleccioneUnaFuncion();
			dataFuncion = getFunciones(espec);
			cargarTablaFuncion(dataFuncion, columnsFuncion);
		}
	}

	private void mostrarSeleccioneUnaFuncion() {
		JLabel lblFunciones = new JLabel("Seleccione una funcion");
		GridBagConstraints gbc_lblFunciones = new GridBagConstraints();
		gbc_lblFunciones.anchor = GridBagConstraints.WEST;
		gbc_lblFunciones.gridwidth = 2;
		gbc_lblFunciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblFunciones.gridx = 1;
		gbc_lblFunciones.gridy = 2;
		getContentPane().add(lblFunciones, gbc_lblFunciones);
	}

	private void cargarTablaFuncion(Object[][] data, String[] columns) {

		DefaultTableModel model = new DefaultTableModel(data, columns) {
			private static final long serialVersionUID = 1L;
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableFuncion = new JTable(model);
		tableFuncion.setAutoCreateRowSorter(true);
		tableFuncion.setRowSelectionAllowed(true);
		tableFuncion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(tableFuncion);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		getContentPane().add(scrollPane, gbc_scrollPane);
	}

	private Object[][] getFunciones(String espec) {
		List<FuncionDTO> funciones = especController.listarFuncionesByEspectaculo(espec);

		Object[][] data = new Object[funciones.size()][columnsFuncion.length];
		int i = 0;
		for( FuncionDTO f : funciones){
			data[i] = getFuncionRow(f);
			i++;
		}

		return data;
	}

	private String[] getFuncionRow(FuncionDTO f){
		List<String> invitados = new ArrayList<String>();
		for(ArtistaDTO a: f.getInvitados()) {
			invitados.add(a.getNombre()+" "+a.getApellido());
		}
		String [] row = {f.getNombre(),formatFecha.format(f.getFecha()),formatHora.format(f.getFecha()),invitados.toString(),formatFecha.format(f.getFechaReg())};		
		return row;
	}

	private void liberarEspectadores() {
		if(scrollPane2!=null) {
			getContentPane().remove(tableEspectador);
			getContentPane().remove(scrollPane2);
		}

	}

	private void mostrarEspectadores() {
		liberarEspectadores();
		List<EspectadorDTO> users = usuarioController.listarEspectadores();
		if(users.isEmpty()) {
			JOptionPane.showMessageDialog(RegistroFuncion.this,
					"No hay especatdores registrados", "Error al registrar",
					JOptionPane.WARNING_MESSAGE);
			dispose();
		}else {
			mostrarEspectadorLabel();
			dataEspectadores = getEspectadores();
			cargarTablaEspectador(dataEspectadores, columnsEspectador);
			tableEspectador.getSelectionModel().addListSelectionListener(espectadorLL);
		}
	}

	private void mostrarEspectadorLabel() {
		JLabel lblSeleccioneUnEspectador = new JLabel("Seleccione un espectador");
		GridBagConstraints gbc_lblSeleccioneUnEspectador = new GridBagConstraints();
		gbc_lblSeleccioneUnEspectador.anchor = GridBagConstraints.WEST;
		gbc_lblSeleccioneUnEspectador.gridwidth = 2;
		gbc_lblSeleccioneUnEspectador.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccioneUnEspectador.gridx = 1;
		gbc_lblSeleccioneUnEspectador.gridy = 4;
		getContentPane().add(lblSeleccioneUnEspectador, gbc_lblSeleccioneUnEspectador);
	}

	private Object[][] getEspectadores() {
		List<EspectadorDTO> users = usuarioController.listarEspectadores();

		Object[][] data = new Object[users.size()][columnsEspectador.length];
		int i = 0;
		for(EspectadorDTO u : users){
			data[i] = getUsuarioRow(u);
			i++;
		}

		return data;
	}

	private void cargarTablaEspectador(Object[][] data, String[] columns) {

		DefaultTableModel model = new DefaultTableModel(data, columns) {
			private static final long serialVersionUID = 1L;
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableEspectador = new JTable(model);
		tableEspectador.setAutoCreateRowSorter(true);
		tableEspectador.setRowSelectionAllowed(true);
		tableEspectador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane2 = new JScrollPane(tableEspectador);
		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 5;
		getContentPane().add(scrollPane2, gbc_scrollPane);
	}

	private String[] getUsuarioRow(EspectadorDTO u){
		String [] row = {u.getNickname(), u.getNombre(), u.getApellido(), u.getEmail(), formatFecha.format(u.getFechaNac())};		
		return row;
	}


	private void cargarFechaRegistro() {
		if(fecha==null) {
			JLabel lblFecha = new JLabel("Fecha de registro");
			lblFecha.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			GridBagConstraints gbc_lblFecha = new GridBagConstraints();
			gbc_lblFecha.gridwidth = 2;
			gbc_lblFecha.anchor = GridBagConstraints.WEST;
			gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
			gbc_lblFecha.gridx = 1;
			gbc_lblFecha.gridy = 6;
			getContentPane().add(lblFecha, gbc_lblFecha);

			fecha = new JDateChooser();
			GridBagConstraints gbc_lblD = new GridBagConstraints();
			gbc_lblD.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblD.gridwidth = 3;
			gbc_lblD.insets = new Insets(0, 0, 5, 5);
			gbc_lblD.gridx = 3;
			gbc_lblD.gridy = 6;
			getContentPane().add(fecha, gbc_lblD);
		}
	}

	private void cargarCanjeRegistros(String email) throws NotFoundException {

		JLabel lblSeleccioneRegistros = new JLabel();
		lblSeleccioneRegistros.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		GridBagConstraints gbc_lblSeleccioneRegistros = new GridBagConstraints();
		gbc_lblSeleccioneRegistros.anchor = GridBagConstraints.WEST;
		gbc_lblSeleccioneRegistros.gridwidth = 2;
		gbc_lblSeleccioneRegistros.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccioneRegistros.gridx = 1;
		gbc_lblSeleccioneRegistros.gridy = 8;
		getContentPane().add(lblSeleccioneRegistros, gbc_lblSeleccioneRegistros);

		if(usuarioController.puedeCanjear(email)) {
			lblSeleccioneRegistros.setText("Seleccione 3 registros previos (opcional)");
			puedeCanjear=true;
			if(comboBoxR1==null) {

				comboBoxR1 = new JComboBox<String>();
				comboBoxR1.addItem("Seleccionar");
				GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
				gbc_comboBox_3.insets = new Insets(0, 0, 5, 5);
				gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBox_3.gridx = 3;
				gbc_comboBox_3.gridy = 8;
				getContentPane().add(comboBoxR1, gbc_comboBox_3);

				comboBoxR2 = new JComboBox<String>();
				comboBoxR2.addItem("Seleccionar");
				GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
				gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
				gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBox_2.gridx = 4;
				gbc_comboBox_2.gridy = 8;
				getContentPane().add(comboBoxR2, gbc_comboBox_2);

				comboBoxR3 = new JComboBox<String>();
				comboBoxR3.addItem("Seleccionar");
				GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
				gbc_comboBox_4.insets = new Insets(0, 0, 5, 5);
				gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBox_4.gridx = 5;
				gbc_comboBox_4.gridy = 8;
				getContentPane().add(comboBoxR3, gbc_comboBox_4);

			}
			cargarRegistros(email);
		}
		else {
			lblSeleccioneRegistros.setText("No tiene suficientes registros para canjear");
			puedeCanjear = false;
		}
	}

	private void cargarRegistros(String email) throws NotFoundException {
		comboBoxR1.removeAllItems();
		comboBoxR2.removeAllItems();
		comboBoxR3.removeAllItems();
		comboBoxR1.addItem("Seleccionar");
		comboBoxR2.addItem("Seleccionar");
		comboBoxR3.addItem("Seleccionar");
		List<String> reg = usuarioController.getRegistrosSinCanjear(email);
		for(String s:reg) {
			comboBoxR1.addItem(s);
			comboBoxR2.addItem(s);
			comboBoxR3.addItem(s);
		}
	}

	private void cargarBotonAceptar() {
		JButton btnAccept = new JButton("Aceptar");
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.anchor = GridBagConstraints.EAST;
		gbc_btnAccept.fill = GridBagConstraints.VERTICAL;
		gbc_btnAccept.insets = new Insets(0, 0, 0, 5);
		gbc_btnAccept.gridx = 4;
		gbc_btnAccept.gridy = 10;
		getContentPane().add(btnAccept, gbc_btnAccept);
		btnAccept.addActionListener(accept);
	}


	public RegistroFuncion() throws NotFoundException{

		Factory factory = Factory.getInstance();
		paqueteController = factory.getIPaqueteController();
		especController = factory.getIEspectaculoController();
		usuarioController = factory.getIUsuarioController();

		setClosable(false);
		setTitle("Alta de Funcion de Espectaculo");
		setBounds(100, 100, 800, 500);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 100, 160, 100, 100, 100, 30, 30};
		gridBagLayout.rowHeights = new int[]{30, 0, 30, 92, 25, 97, 0, 20, 30, 30, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JButton btnConfirmar = new JButton("Confirmar");
		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConfirmar.gridx = 5;
		gbc_btnConfirmar.gridy = 2;
		getContentPane().add(btnConfirmar, gbc_btnConfirmar);

		JButton btnCancel = new JButton("Cancelar");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.anchor = GridBagConstraints.EAST;
		gbc_btnCancel.fill = GridBagConstraints.VERTICAL;
		gbc_btnCancel.gridx = 5;
		gbc_btnCancel.gridy = 10;
		getContentPane().add(btnCancel, gbc_btnCancel);

		JLabel lblEspectaculo = new JLabel("Espectaculo");
		GridBagConstraints gbc_lblEspectaculo = new GridBagConstraints();
		gbc_lblEspectaculo.insets = new Insets(0, 0, 5, 5);
		gbc_lblEspectaculo.gridx = 3;
		gbc_lblEspectaculo.gridy = 1;
		getContentPane().add(lblEspectaculo, gbc_lblEspectaculo);

		comboBoxEspec = new JComboBox<String>();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 2;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 4;
		gbc_comboBox_1.gridy = 1;
		getContentPane().add(comboBoxEspec, gbc_comboBox_1);
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.anchor = GridBagConstraints.EAST;
		gbc_btnCancel.fill = GridBagConstraints.VERTICAL;
		gbc_btnCancel.gridx = 5;
		gbc_btnCancel.gridy = 2;


		// ----------------------------------------------------------------------------/

		platAL = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String plat = (String) comboBoxPlat.getSelectedItem();
				liberarEspectadores();
				liberarFunciones();
				try {
					cargarEspectaculos(plat);
				} catch (NotFoundException e1) {
					JOptionPane.showMessageDialog(RegistroFuncion.this,
							"Error al seleccionar Plataforma", "Error interno",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};


		confirmarAL = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					String espec = (String) comboBoxEspec.getSelectedItem();
					liberarFunciones();
					liberarEspectadores();
					refresh();
					mostrarFunciones(espec);
					mostrarEspectadores();
					refresh();

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(RegistroFuncion.this,
							"Error al mostrar Funciones y Espectadores", "Error interno",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};

		espectadorLL = new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				cargarFechaRegistro();
				UsuarioDTO user = usuarioController.getUsuarioByEmail(dataEspectadores[tableEspectador.getSelectedRow()][EMAIL_COL].toString());
				try {
					cargarCanjeRegistros(user.getEmail());
				} catch (NotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cargarBotonAceptar();

			}
		};

		accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tableFuncion.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(RegistroFuncion.this,
							"Por favor seleccione una funcion", "Error al seleccionar",
							JOptionPane.WARNING_MESSAGE);
				}
				else if(tableEspectador.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(RegistroFuncion.this,
							"Por favor seleccione un espectador", "Error al seleccionar",
							JOptionPane.WARNING_MESSAGE);
				}
				else if(fecha.getDate() == null) {
					JOptionPane.showMessageDialog(RegistroFuncion.this,
							"Campo Fecha de Registro no puede ser vacio", "Error al registrar",
							JOptionPane.WARNING_MESSAGE);

				}
				else {
					try {
						plataforma = (String) comboBoxPlat.getSelectedItem();
						espectaculo = (String) comboBoxEspec.getSelectedItem();
						funcion = String.valueOf(dataFuncion[tableFuncion.getSelectedRow()][NOMBRE_COL]);
						email = String.valueOf(dataEspectadores[tableEspectador.getSelectedRow()][EMAIL_COL]);

						if(puedeCanjear) {
							r1=(String) comboBoxR1.getSelectedItem();
							r2=(String) comboBoxR2.getSelectedItem();
							r3=(String) comboBoxR3.getSelectedItem();

							if(r1.equals("Seleccionar") && r2.equals("Seleccionar") && r3.equals("Seleccionar")) {
								RegistroFuncionDTO reg = especController.registroFuncion(plataforma, espectaculo, funcion, email,fecha.getDate(), null, null, null);

								JOptionPane.showMessageDialog(RegistroFuncion.this, "Id registro: "+ reg.getIdentificador() + "\n" +
										"Costo: "+ reg.getCosto(), "Registro Exitoso",JOptionPane.INFORMATION_MESSAGE);
								dispose();

							}else if(r1.equals(r2) || r1.equals(r3) || r3.equals(r2)) {
								JOptionPane.showMessageDialog(RegistroFuncion.this,
										"Seleccione 3 registros distintos", "Error al registrar",
										JOptionPane.WARNING_MESSAGE);
							}
							else if(!r1.equals("Seleccionar") && !r2.equals("Seleccionar") && !r3.equals("Seleccionar")) {
								RegistroFuncionDTO reg = especController.registroFuncion(plataforma, espectaculo, funcion, email, fecha.getDate(), r1, r2, r3);
								JOptionPane.showMessageDialog(RegistroFuncion.this, "Id registro: "+ reg.getIdentificador() + "\n" +
										"Costo: "+ reg.getCosto(), "Registro Exitoso",JOptionPane.INFORMATION_MESSAGE);
								dispose();

							}
							else {
								JOptionPane.showMessageDialog(RegistroFuncion.this,
										"Seleccione 3 registros distintos", "Error al registrar",
										JOptionPane.WARNING_MESSAGE);
							}
						}
						else {
							RegistroFuncionDTO reg = especController.registroFuncion(plataforma, espectaculo, funcion, email,fecha.getDate(), null, null, null);

							JOptionPane.showMessageDialog(RegistroFuncion.this, "Id registro: "+ reg.getIdentificador() + "\n" +
									"Costo: "+ reg.getCosto(), "Registro Exitoso",JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(RegistroFuncion.this,
								e2.getMessage(), "Error al registrar",
								JOptionPane.ERROR_MESSAGE);

					}

				}
			}
		};


		cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		};

		btnCancel.addActionListener(cancel);
		btnConfirmar.addActionListener(confirmarAL);


		mostrarPlataformas();

	}
}





