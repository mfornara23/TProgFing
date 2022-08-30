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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;

@SuppressWarnings("serial")
public class ConsultaEspectaculo extends JInternalFrame {
	
	private Factory factory = Factory.getInstance();
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private String[] columns = {"Nombre", "Descripcion", "Duracion", "Esp. Min", "Esp. Max." , "URL", "Costo", "Fecha Registro",
			"Plataforma", "Artista", "Categorias", "Estado"};
	private final int NOMBRE_COL = 0; 
	private Object[][] data;
	private JTable table = null;
	private JScrollPane scrollPane = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String nombrePlataforma) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaEspectaculo window = new ConsultaEspectaculo(nombrePlataforma);
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
	public ConsultaEspectaculo(String nombrePlataforma) {
		initialize(nombrePlataforma);
	}

	/**
	 * Obtiene los espectaculos del sistema asociados a la plataforma y los parsea a la forma adecuada para la tabla.
	 * @return Un array bidimensional con los datos de los espectaculos.
	 * @throws NotFoundException 
	 */
	private Object[][] getEspectaculosData(String nombrePlataforma){
		List<EspectaculoDTO> espectaculos;
		Object[][] data = null;
		try {
			espectaculos = factory.getIEspectaculoController().listarEspectaculos(nombrePlataforma);
			data = new Object[espectaculos.size()][columns.length];
			int i = 0;
			for( EspectaculoDTO espectaculo : espectaculos){
				data[i] = getEspectaculoRow(espectaculo);
				i++;
			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(ConsultaEspectaculo.this,
					e2.getMessage(), "Error interno",
					JOptionPane.ERROR_MESSAGE);
		}
		return data;
	}

	/**
	 * Crea la fila con los datos necesarios para la tabla
	 * @param espectaculo EspectaculoDTO del que se obtienen los datos
	 * @return Array de Strings con los datos de la fila
	 */
	private String[] getEspectaculoRow(EspectaculoDTO espectaculo){
		
		String categorias = "";
		
		for(String c : espectaculo.getCategorias()) {
			categorias = categorias.concat(" ").concat(c);
		}
		
		String [] row = {espectaculo.getNombre(), espectaculo.getDescripcion(), espectaculo.getDuracion().toString(),
				espectaculo.getCantMin().toString(), espectaculo.getCantMax().toString(), espectaculo.getUrl(),
				espectaculo.getCosto().toString(), format.format(espectaculo.getFechaReg()).toString(),
				espectaculo.getPlataforma().getNombre(), espectaculo.getArtista().getEmail(),
				categorias, espectaculo.getEstado().toString() };
		return row;
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
		scrollPane.setBounds(12, 13, 800, 400);
		getContentPane().add(scrollPane);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String nombrePlataforma) {
		setBounds(100, 100, 850, 510);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		data = getEspectaculosData(nombrePlataforma);
		loadTable(data, columns);

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(83, 426, 97, 25);
		getContentPane().add(btnAccept);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(430, 426, 97, 25);
		getContentPane().add(btnCancel);

		// ----------------------------------------------------------------------------/

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(ConsultaEspectaculo.this,
							"Por favor seleccione una fila para continuar", "Error al seleccionar",
							JOptionPane.WARNING_MESSAGE);
				} else {
					EspectaculoDTO espectaculo = null;
					try {
						espectaculo = factory.getIEspectaculoController().consultaEspectaculo(data[table.getSelectedRow()][NOMBRE_COL].toString());
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(ConsultaEspectaculo.this,
								"Error al consultar el Espectaculo", "Error interno",
								JOptionPane.ERROR_MESSAGE);
					}
					
					ViewEspectaculo.main(espectaculo);
					ConsultaEspectaculo.this.dispose();
				}

			}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ConsultaEspectaculo.this.dispose();

			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}
}