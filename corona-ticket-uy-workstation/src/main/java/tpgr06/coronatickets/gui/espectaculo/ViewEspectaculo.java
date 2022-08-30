package tpgr06.coronatickets.gui.espectaculo;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.gui.funcion.MostarFuncionDTO;
import tpgr06.coronatickets.gui.paquete.ViewPaquete;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.paquete.PaqueteDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;

public class ViewEspectaculo {

	private final int NOMBRE_COL = 0;
	private Factory factory = Factory.getInstance();
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private JInternalFrame frameEspectaculo;
	private JTextField nombreField;
	private JTextPane descTextPane;
	private JTextField duracionField;
	private JTextField espMaxField;
	private JTextField espMinField;
	private JTextField urlField;
	private JTextField costoField;
	private JDateChooser fechaRegistro;
	private JTextField plataforma;
	private JTextField artista;
	private String[] columnsFunciones = { "Nombre", "Fecha", "Hora Inicio", "Fecha Alta", "Artista Invitados" };
	private String[] columnsPaquetes = { "Nombre", "Descripcion", "Fecha Inicio", "Fecha Fin", "Descuento" };
	private Object[][] dataFunciones;
	private Object[][] dataPaquetes;
	private JTable tableFunciones = null;
	private JTable tablePaquetes = null;
	private JScrollPane scrollPaneFunciones = null;
	private JScrollPane scrollPanePaquetes = null;
	private JTextPane categoriasPane;
	private JTextField estadoField;

	/**
	 * Launch the application.
	 */
	public static void main(EspectaculoDTO espectaculo) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewEspectaculo window = new ViewEspectaculo(espectaculo);
					window.frameEspectaculo.setVisible(true);
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(window.frameEspectaculo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewEspectaculo(EspectaculoDTO espectaculo) {
		initialize(espectaculo);
	}
	
	private String getCategorias(EspectaculoDTO espectaculo) {
		String cats = "";
		for (String c : espectaculo.getCategorias()) {
			cats = cats.concat(c).concat(System.lineSeparator());
		}
		
		return cats;
	}

	private Object[][] getFuncionesData(EspectaculoDTO espectaculo) {
		List<FuncionDTO> funciones = espectaculo.getFunciones();

		Object[][] data = new Object[funciones.size()][columnsFunciones.length];
		int i = 0;
		for (FuncionDTO fun : funciones) {
			data[i] = getFuncionRow(fun);
			i++;
		}

		return data;
	}

	private String[] getFuncionRow(FuncionDTO fun) {

		String invitados = "";

		for (ArtistaDTO a : fun.getInvitados()) {
			invitados += "  ".concat(a.getNombre());
		}

		String[] row = { fun.getNombre(), format.format(fun.getFecha()), format.format(fun.getFecha()),
				format.format(fun.getFechaReg()), invitados };
		return row;
	}

	private Object[][] getPaquetesData(EspectaculoDTO espectaculo) {
		List<PaqueteDTO> paquetes = espectaculo.getPaquetes();

		Object[][] data = new Object[paquetes.size()][columnsFunciones.length];
		int i = 0;
		for (PaqueteDTO paq : paquetes) {
			data[i] = getPaqueteRow(paq);
			i++;
		}

		return data;
	}

	private String[] getPaqueteRow(PaqueteDTO paq) {

		String[] row = { paq.getNombre(), paq.getDescripcion(), format.format(paq.getFechaInicio()),
				format.format(paq.getFechaFin()), paq.getDescuento().toString() };
		return row;
	}

	private void loadTableFunciones(Object[][] dataFunciones, String[] columnsFunciones) {

		DefaultTableModel model = new DefaultTableModel(dataFunciones, columnsFunciones) {

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

		// Si ya hay una tabla la borro antes de crear otra.
		for (Component c : frameEspectaculo.getContentPane().getComponents()) {
			if (c.equals(scrollPaneFunciones)) {
				frameEspectaculo.getContentPane().remove(scrollPaneFunciones);
			}

		}
		tableFunciones = new JTable(model);
		tableFunciones.setAutoCreateRowSorter(true);
		tableFunciones.setRowSelectionAllowed(true);
		tableFunciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneFunciones = new JScrollPane(tableFunciones);
		scrollPaneFunciones.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneFunciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneFunciones.setBounds(12, 278, 760, 176);
		frameEspectaculo.getContentPane().add(scrollPaneFunciones);

	}

	private void loadTablePaquetes(Object[][] dataPaquetes, String[] columnsPaquetes) {

		DefaultTableModel model = new DefaultTableModel(dataPaquetes, columnsPaquetes) {

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

		// Si ya hay una tabla la borro antes de crear otra.
		for (Component c : frameEspectaculo.getContentPane().getComponents()) {
			if (c.equals(scrollPanePaquetes)) {
				frameEspectaculo.getContentPane().remove(scrollPanePaquetes);
			}

		}
		tablePaquetes = new JTable(model);
		tablePaquetes.setAutoCreateRowSorter(true);
		tablePaquetes.setRowSelectionAllowed(true);
		tablePaquetes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPanePaquetes = new JScrollPane(tablePaquetes);
		scrollPanePaquetes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanePaquetes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanePaquetes.setBounds(12, 522, 760, 193);
		frameEspectaculo.getContentPane().add(scrollPanePaquetes);
		

	}

	/**
	 * Initialize the contents of the frame.
	 * @throws NotFoundException 
	 */
	private void initialize(EspectaculoDTO espectaculo){
		frameEspectaculo = new JInternalFrame();
		frameEspectaculo.setTitle("Consulta de Espectaculo");
		frameEspectaculo.setSize(400, 499);
		frameEspectaculo.setBounds(300, 350, 800, 850);
		frameEspectaculo.setResizable(false);
		frameEspectaculo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameEspectaculo.getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(29, 22, 104, 14);
		frameEspectaculo.getContentPane().add(lblNombre);

		nombreField = new JTextField();
		nombreField.setBounds(150, 19, 205, 20);
		frameEspectaculo.getContentPane().add(nombreField);
		nombreField.setColumns(10);
		nombreField.setEditable(false);
		nombreField.setText(espectaculo.getNombre());
		
		JLabel lblDesc = new JLabel("Descripci\u00F3n:");
		lblDesc.setBounds(29, 59, 117, 16);
		frameEspectaculo.getContentPane().add(lblDesc);

		descTextPane = new JTextPane();
		descTextPane.setBounds(150, 237, 205, 50);
		frameEspectaculo.getContentPane().add(descTextPane);
		descTextPane.setEditable(false);
		descTextPane.setText(espectaculo.getDescripcion());
		
		JScrollPane scrollDesc = new JScrollPane(descTextPane);
		scrollDesc.setBounds(150, 50,  205, 50);
		scrollDesc.setEnabled(false);
		frameEspectaculo.getContentPane().add(scrollDesc);
		
		JLabel lblDuracin = new JLabel("Duraci\u00F3n:");
		lblDuracin.setBounds(373, 21, 104, 16);
		frameEspectaculo.getContentPane().add(lblDuracin);
		
		duracionField = new JTextField();
		duracionField.setColumns(10);
		duracionField.setBounds(449, 18, 205, 22);
		duracionField.setEditable(false);
		duracionField.setText(espectaculo.getDuracion().toString());
		frameEspectaculo.getContentPane().add(duracionField);
		
		JLabel lblEspMin = new JLabel("Espec. M\u00EDn:");
		lblEspMin.setBounds(367, 59, 104, 16);
		frameEspectaculo.getContentPane().add(lblEspMin);
		
		espMinField = new JTextField();
		espMinField.setColumns(10);
		espMinField.setBounds(449, 56, 205, 22);
		espMinField.setEditable(false);
		espMinField.setText(espectaculo.getCantMin().toString());
		frameEspectaculo.getContentPane().add(espMinField);
		
		JLabel lblEspMax = new JLabel("Espec. M\u00E1x:");
		lblEspMax.setBounds(367, 91, 104, 16);
		frameEspectaculo.getContentPane().add(lblEspMax);

		espMaxField = new JTextField();
		espMaxField.setBounds(449, 88, 205, 22);
		espMaxField.setEditable(false);
		espMaxField.setText(espectaculo.getCantMax().toString());
		frameEspectaculo.getContentPane().add(espMaxField);
		espMaxField.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setBounds(29, 120, 104, 16);
		frameEspectaculo.getContentPane().add(lblUrl);
		
		urlField = new JTextField();
		urlField.setColumns(10);
		urlField.setBounds(150, 117, 205, 22);
		urlField.setEditable(false);
		urlField.setText(espectaculo.getUrl());
		frameEspectaculo.getContentPane().add(urlField);
		
		JLabel lblCosto = new JLabel("Costo:");
		lblCosto.setBounds(367, 120, 104, 16);
		frameEspectaculo.getContentPane().add(lblCosto);
		
		costoField = new JTextField();
		costoField.setColumns(10);
		costoField.setBounds(449, 120, 205, 22);
		costoField.setEditable(false);
		costoField.setText(espectaculo.getCosto().toString());
		frameEspectaculo.getContentPane().add(costoField);

		JLabel lblFecha = new JLabel("Fecha Registro:");
		lblFecha.setBounds(29, 160, 117, 14);
		frameEspectaculo.getContentPane().add(lblFecha);

		fechaRegistro = new JDateChooser();
		fechaRegistro.setBounds(150, 154, 205, 20);
		fechaRegistro.getCalendarButton().setEnabled(false);
		fechaRegistro.getDateEditor().setEnabled(false);
		fechaRegistro.setDate(espectaculo.getFechaReg());
		frameEspectaculo.getContentPane().add(fechaRegistro);

		JButton btnFuncion = new JButton("Seleccionar Funcion");
		btnFuncion.setBounds(317, 467, 160, 23);
		frameEspectaculo.getContentPane().add(btnFuncion);
		
		JButton btnPaquete = new JButton("Seleccionar Paquete");
		btnPaquete.setBounds(317, 728, 160, 23);
		frameEspectaculo.getContentPane().add(btnPaquete);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(633, 758, 86, 23);
		frameEspectaculo.getContentPane().add(btnCancel);
		
		JLabel lblPlataforma = new JLabel("Plataforma:");
		lblPlataforma.setBounds(367, 159, 104, 16);
		frameEspectaculo.getContentPane().add(lblPlataforma);
		
		plataforma = new JTextField();
		plataforma.setBounds(449, 157, 205, 20);
		plataforma.setText(espectaculo.getPlataforma().getNombre());
		plataforma.setEditable(false);
		frameEspectaculo.getContentPane().add(plataforma);
		
		JLabel lblArtista = new JLabel("Artista:");
		lblArtista.setBounds(367, 196, 104, 16);
		frameEspectaculo.getContentPane().add(lblArtista);
				
		artista = new JTextField();
		artista.setBounds(449, 194, 205, 20);
		artista.setText(espectaculo.getArtista().getEmail());
		artista.setEditable(false);
		frameEspectaculo.getContentPane().add(artista);
		
		JLabel lblFunciones = new JLabel("Funciones:");
		lblFunciones.setBounds(29, 249, 91, 16);
		frameEspectaculo.getContentPane().add(lblFunciones);

		dataFunciones = getFuncionesData(espectaculo);
		loadTableFunciones(dataFunciones, columnsFunciones);
		
		JLabel lblPaquetes = new JLabel("Paquetes:");
		lblPaquetes.setBounds(29, 493, 91, 16);
		frameEspectaculo.getContentPane().add(lblPaquetes);

		dataPaquetes = getPaquetesData(espectaculo);
		loadTablePaquetes(dataPaquetes, columnsPaquetes);
		
		JLabel lblCategorias = new JLabel("Categorias:");
		lblCategorias.setBounds(29, 196, 117, 14);
		frameEspectaculo.getContentPane().add(lblCategorias);
		
		categoriasPane = new JTextPane();
		categoriasPane.setBounds(150, 193, 205, 20);
		categoriasPane.setText(getCategorias(espectaculo));
		categoriasPane.setEditable(false);
		frameEspectaculo.getContentPane().add(categoriasPane);
		
		JScrollPane scrollCats = new JScrollPane(categoriasPane);
		scrollCats.setBounds(150, 196,  205, 50);
		scrollCats.setEnabled(false);
		frameEspectaculo.getContentPane().add(scrollCats);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(367, 231, 104, 16);
		frameEspectaculo.getContentPane().add(lblEstado);
		
		estadoField = new JTextField();
		estadoField.setText(espectaculo.getEstado().toString());
		estadoField.setEditable(false);
		estadoField.setBounds(449, 228, 205, 20);
		frameEspectaculo.getContentPane().add(estadoField);

		// ----------------------------------------------------------------------------/

		ActionListener selectFuncion = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tableFunciones.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(ViewEspectaculo.this.frameEspectaculo,
							"Por favor seleccione una fila para continuar", "Error al seleccionar",
							JOptionPane.WARNING_MESSAGE);
				} else {
					FuncionDTO funToView = null;
					try {
						funToView = factory.getIEspectaculoController()
								.getFuncionEspectaculo(dataFunciones[tableFunciones.getSelectedRow()][NOMBRE_COL].toString(), espectaculo.getNombre());
					} catch (NotFoundException e1) {
						JOptionPane.showMessageDialog(ViewEspectaculo.this.frameEspectaculo,
								"Error al seleccionar Funcion", "Error interno",
								JOptionPane.ERROR_MESSAGE);
					}
					MostarFuncionDTO.main(funToView);
				}

			}
		};
		
		ActionListener selectPaquete = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tablePaquetes.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(ViewEspectaculo.this.frameEspectaculo,
							"Por favor seleccione una fila para continuar", "Error al seleccionar",
							JOptionPane.WARNING_MESSAGE);
				} else {
					PaqueteDTO paqToView = null;
					try {
						paqToView = factory.getIPaqueteController()
								.consultaPaquete(dataPaquetes[tablePaquetes.getSelectedRow()][NOMBRE_COL].toString());
					} catch (NotFoundException e1) {
						JOptionPane.showMessageDialog(ViewEspectaculo.this.frameEspectaculo,
								"Error al seleccionar Paquete", "Error interno",
								JOptionPane.ERROR_MESSAGE);
					}
					ViewPaquete.main(paqToView);
				}				
			}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ViewEspectaculo.this.frameEspectaculo.dispose();

			}
		};

		btnFuncion.addActionListener(selectFuncion);
		btnPaquete.addActionListener(selectPaquete);
		btnCancel.addActionListener(cancel);
	}
}
