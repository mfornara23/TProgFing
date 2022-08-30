package tpgr06.coronatickets.gui.espectaculo;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.categoria.CategoriaDTO;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;

@SuppressWarnings("serial")
public class AltaEspectaculo extends JInternalFrame {

	private Factory factory = Factory.getInstance();
	
	private List<PlataformaDTO> plataformasDTO;
	private List<ArtistaDTO> artistasDTO;
	private List<CategoriaDTO> categoriasDTO;
	
	private JTextField nombreField;
	private JTextPane descTextPane;
	private JTextField duracionField;
	private JTextField espMaxField;
	private JTextField espMinField;
	private JTextField urlField;
	private JTextField costoField;
	private JDateChooser fechaRegistro;
	private JComboBox<String> plataformaList;
	private JComboBox<String> artistaList;
	
	private String[] columns = { "Nombre"};
	private Object[][] data;
	private JTable table = null;
	private JScrollPane scrollPane = null;
	private List<String> selectedCategories =  new LinkedList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaEspectaculo window = new AltaEspectaculo();
					window.setVisible(true);
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AltaEspectaculo() {
		plataformasDTO = factory.getIPaqueteController().listarPlataformas();
		artistasDTO = factory.getIUsuarioController().listarUsuario()
				.stream()
				.filter(ArtistaDTO.class::isInstance)
				.map(ArtistaDTO.class::cast)
				.collect(Collectors.toList());
		categoriasDTO = factory.getIEspectaculoController().listarCategorias();
		
		if (plataformasDTO.isEmpty()) {
			JOptionPane.showMessageDialog(AltaEspectaculo.this,
					"No hay plataformas en el sistema", "Error",
					JOptionPane.WARNING_MESSAGE);
			dispose();
		} else if (artistasDTO.isEmpty()) {
			JOptionPane.showMessageDialog(AltaEspectaculo.this,
					"No hay artistas registrados en el sistema", "Error",
					JOptionPane.WARNING_MESSAGE);
			dispose();
		} else if (categoriasDTO.isEmpty()) {
			JOptionPane.showMessageDialog(AltaEspectaculo.this,
					"No hay categorias registradas en el sistema", "Error",
					JOptionPane.WARNING_MESSAGE);
			dispose();
		} else {
			initialize();
		}
	}
	
	
	/**
	 * Obtiene las categorias del sistema y los parsea a la forma adecuada para la tabla.
	 * @return Un array bidimensional con los datos de los usuarios.
	 */
	private Object[][] getCategoriaData(List<String> categorias) {
		Object[][] data = new Object[categorias.size()][columns.length];
		int i = 0;
		for(String c : categorias){
			String [] dato = {c};
			data[i] = dato;
			i++;
		}

		return data;
	}

	/**
	 * Metodo para cargar la tabla con los datos del array "data".
	 * 
	 * @param data
	 * @param columns
	 */
	private void loadTable(Object[][] data, String[] columns) {

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

		// Si ya hay una tabla la borro antes de crear otra.
		for (Component c : getContentPane().getComponents()) {
			if (c.equals(scrollPane)) {
				getContentPane().remove(scrollPane);
			}

		}
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(367, 91, 377, 200);
		getContentPane().add(scrollPane);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Alta Espectaculo");
		setSize(850, 500);
		setBounds(0, 0, 850, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(29, 22, 104, 14);
		getContentPane().add(lblNombre);

		nombreField = new JTextField();
		nombreField.setBounds(150, 19, 205, 20);
		getContentPane().add(nombreField);
		nombreField.setColumns(10);
		
		JLabel lblDesc = new JLabel("Descripci\u00F3n:");
		lblDesc.setBounds(29, 59, 117, 16);
		getContentPane().add(lblDesc);

		descTextPane = new JTextPane();
		descTextPane.setBounds(150, 237, 205, 50);
		getContentPane().add(descTextPane);
		
		JScrollPane scrollDesc = new JScrollPane(descTextPane);
		scrollDesc.setBounds(150, 50,  205, 50);
		getContentPane().add(scrollDesc);
		
		JLabel lblDuracin = new JLabel("Duraci\u00F3n:");
		lblDuracin.setBounds(29, 111, 104, 16);
		getContentPane().add(lblDuracin);
		
		duracionField = new JTextField();
		duracionField.setColumns(10);
		duracionField.setBounds(150, 111, 205, 22);
		getContentPane().add(duracionField);
		
		JLabel lblEspMin = new JLabel("Espec. M\u00EDn:");
		lblEspMin.setBounds(29, 147, 104, 16);
		getContentPane().add(lblEspMin);
		
		espMinField = new JTextField();
		espMinField.setColumns(10);
		espMinField.setBounds(150, 144, 205, 22);
		getContentPane().add(espMinField);
		
		JLabel lblEspMax = new JLabel("Espec. M\u00E1x:");
		lblEspMax.setBounds(29, 180, 104, 16);
		getContentPane().add(lblEspMax);

		espMaxField = new JTextField();
		espMaxField.setBounds(150, 177, 205, 22);
		getContentPane().add(espMaxField);
		espMaxField.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setBounds(29, 213, 104, 16);
		getContentPane().add(lblUrl);
		
		urlField = new JTextField();
		urlField.setColumns(10);
		urlField.setBounds(150, 210, 205, 22);
		getContentPane().add(urlField);
		
		JLabel lblCosto = new JLabel("Costo:");
		lblCosto.setBounds(29, 247, 104, 16);
		getContentPane().add(lblCosto);
		
		costoField = new JTextField();
		costoField.setColumns(10);
		costoField.setBounds(150, 244, 205, 22);
		getContentPane().add(costoField);

		JLabel lblFecha = new JLabel("Fecha Registro:");
		lblFecha.setBounds(29, 277, 117, 14);
		getContentPane().add(lblFecha);

		fechaRegistro = new JDateChooser();
		fechaRegistro.setBounds(150, 277, 205, 20);
		getContentPane().add(fechaRegistro);

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(50, 428, 86, 23);
		getContentPane().add(btnAccept);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(261, 428, 86, 23);
		getContentPane().add(btnCancel);
		
		JLabel lblPlataforma = new JLabel("Plataforma:");
		lblPlataforma.setBounds(29, 310, 104, 16);
		getContentPane().add(lblPlataforma);
		
		plataformaList = new JComboBox<String>();
		plataformaList.setBounds(150, 308, 205, 20);
		plataformasDTO.forEach(plataforma -> plataformaList.addItem(plataforma.getNombre()));
		getContentPane().add(plataformaList);
		
		JLabel lblArtista = new JLabel("Artista:");
		lblArtista.setBounds(29, 341, 104, 16);
		getContentPane().add(lblArtista);
				
		artistaList = new JComboBox<String>();
		artistaList.setBounds(150, 341, 205, 20);
		artistasDTO.forEach(artista -> artistaList.addItem(artista.getEmail()));
		getContentPane().add(artistaList);
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(367, 21, 104, 16);
		getContentPane().add(lblCategoria);
		
		JComboBox<String> categoriasList = new JComboBox<String>();
		categoriasList.setBounds(441, 19, 205, 20);
		categoriasDTO.forEach(cat -> categoriasList.addItem(cat.getNombre()));
		getContentPane().add(categoriasList);
		
		JButton btnAdd = new JButton("Agregar");
		btnAdd.setBounds(658, 17, 86, 23);
		getContentPane().add(btnAdd);
		
		JLabel lblSelectedCat = new JLabel("Categorias Seleccionadas:");
		lblSelectedCat.setBounds(367, 70, 172, 16);
		getContentPane().add(lblSelectedCat);
		
		loadTable(data, columns);
		
		// ----------------------------------------------------------------------------//

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (nombreField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this, "Campo Nombre no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (descTextPane.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this, "Campo Descripcion no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (duracionField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this, "Campo Duracion no puede ser vac\u00EDo",
						"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (espMinField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this, "Campo de Espectadores Min no puede ser vac\u00EDo",
						"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (espMaxField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this, "Campo de Espectadores Max no puede ser vac\u00EDo",
						"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (urlField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this, "Campo de URL no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (costoField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this, "Campo de Costo no puede ser vac\u00EDo",
							"Error al registrar", JOptionPane.ERROR_MESSAGE);
				} else if (fechaRegistro.getDate() == null) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this,
							"Campo Fecha de Registro no puede ser vac\u00EDo", "Error al registrar",
							JOptionPane.ERROR_MESSAGE);
				} else if (artistaList.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this,
							"Debe seleccionar un artista", "Error al registrar",
							JOptionPane.ERROR_MESSAGE);
				} else if (plataformaList.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this,
							"Debe seleccionar una plataforma", "Error al registrar",
							JOptionPane.ERROR_MESSAGE);
				} else if (selectedCategories.isEmpty()) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this,
							"Debe elegir al menos una categoria", "Error al registrar",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						factory.getIEspectaculoController().altaEspectaculo(nombreField.getText(),
								descTextPane.getText(), Integer.valueOf(duracionField.getText()), Integer.valueOf(espMinField.getText()),
								Integer.valueOf(espMaxField.getText()), urlField.getText(), Integer.valueOf(costoField.getText()),
								fechaRegistro.getDate(),plataformaList.getSelectedItem().toString(),
								artistaList.getSelectedItem().toString(), selectedCategories, "/media/img/espectaculo-generico.png",null,0,null);
						JOptionPane.showMessageDialog(AltaEspectaculo.this, "Alta exitosa!", "Alta Espectaculo",
								JOptionPane.INFORMATION_MESSAGE);
						AltaEspectaculo.this.dispose();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(AltaEspectaculo.this,
								e2.getMessage(), "Error interno",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};

		ActionListener cancel = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AltaEspectaculo.this.dispose();

			}
		};
		
		ActionListener add =  new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (categoriasList.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(AltaEspectaculo.this,
							"Debe seleccionar una categoria", "Error al registrar",
							JOptionPane.ERROR_MESSAGE);
				} else {
					selectedCategories.add(categoriasList.getSelectedItem().toString());
					loadTable(getCategoriaData(selectedCategories), columns);
				}
				
			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
		btnAdd.addActionListener(add);
	}
}
