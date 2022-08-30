package tpgr06.coronatickets.gui.paquete;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.paquete.IPaqueteController;
import tpgr06.coronatickets.sys.paquete.PaqueteDTO;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;

public class AgregarEspectaculoAPaquete {

	private IPaqueteController paqueteController;

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private final int NOMBRE_COL = 0;
	Object[][] data;
	private PaqueteDTO selectedPaquete;
	private PlataformaDTO selectedPlataforma;
	private List<EspectaculoDTO> espectaculosNoEnPaquete;
	private List<PaqueteDTO> paquetes;
	private List<PlataformaDTO> plataformas;
	private String[] columns = { "Nombre", "Descripcion", "Duracion", "Espec. Min.", "Espec. Max.", "URL", "Costo",
			"Fecha Alta", "Plataforma", "Artista" };
	private JTable table = null;
	private JInternalFrame frame;
	private JScrollPane scrollPane = null;
	private JTextField tFieldNombre;
	private JTextField tFieldDescripcion;
	private JTextField tFieldFechaInicio;
	private JTextField tFieldFechaFin;
	private JTextField tFieldDescuento;
	private JButton btnCerrar;
	private JButton btnAceptar;
	private JComboBox<String> comboBoxPaquete;
	private JComboBox<String> comboBoxPlataforma;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgregarEspectaculoAPaquete window = new AgregarEspectaculoAPaquete();
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
	 * Create the frame.
	 */
	public AgregarEspectaculoAPaquete() {
		paqueteController = Factory.getInstance().getIPaqueteController();
		paquetes = paqueteController.listarPaquetes();
		plataformas = paqueteController.listarPlataformas();

		frame = new JInternalFrame();
		frame.setTitle("Agregar espect\u00E1culo a un paquete");
		frame.setResizable(false);
		frame.setBounds(100, 100, 978, 621);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblIngresoNombre = new JLabel("Seleccione un paquete:");
		lblIngresoNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIngresoNombre.setBounds(27, 24, 156, 14);
		frame.getContentPane().add(lblIngresoNombre);

		comboBoxPaquete = new JComboBox<String>();
		comboBoxPaquete.setBounds(193, 24, 231, 20);
		comboBoxPaquete.addItem("Seleccionar...");
		paquetes.forEach(paqueteDTO -> comboBoxPaquete.addItem(paqueteDTO.getNombre()));
		comboBoxPaquete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleSelectPaquete();
			}
		});
		frame.getContentPane().add(comboBoxPaquete);

		// Una etiqueta (JLabel) indicando los campos que se verán
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(37, 74, 65, 14);
		frame.getContentPane().add(lblNombre);

		JLabel lblDescripcion = new JLabel("Descripci\u00F3n");
		lblDescripcion.setBounds(477, 74, 83, 14);
		frame.getContentPane().add(lblDescripcion);

		JLabel lblFechaInicio = new JLabel("Fecha de inicio");
		lblFechaInicio.setBounds(37, 113, 95, 14);
		frame.getContentPane().add(lblFechaInicio);

		JLabel lblFechaFin = new JLabel("Fecha de fin");
		lblFechaFin.setBounds(477, 113, 83, 14);
		frame.getContentPane().add(lblFechaFin);

		JLabel lblDescuento = new JLabel("Descuento");
		lblDescuento.setBounds(37, 153, 65, 14);
		frame.getContentPane().add(lblDescuento);

		JLabel lblEspectaculos = new JLabel("Seleccione el espect\u00E1culo a agregar");
		lblEspectaculos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEspectaculos.setBounds(27, 332, 271, 14);
		frame.getContentPane().add(lblEspectaculos);

		tFieldNombre = new JTextField();
		tFieldNombre.setEditable(false);
		tFieldNombre.setBounds(153, 69, 271, 24);
		frame.getContentPane().add(tFieldNombre);

		tFieldDescripcion = new JTextField();
		tFieldDescripcion.setEditable(false);
		tFieldDescripcion.setBounds(598, 69, 271, 24);
		frame.getContentPane().add(tFieldDescripcion);

		tFieldFechaInicio = new JTextField();
		tFieldFechaInicio.setEditable(false);
		tFieldFechaInicio.setBounds(153, 108, 271, 24);
		frame.getContentPane().add(tFieldFechaInicio);

		tFieldFechaFin = new JTextField();
		tFieldFechaFin.setEditable(false);
		tFieldFechaFin.setBounds(598, 108, 271, 24);
		frame.getContentPane().add(tFieldFechaFin);

		tFieldDescuento = new JTextField();
		tFieldDescuento.setEditable(false);
		tFieldDescuento.setBounds(153, 148, 271, 24);
		frame.getContentPane().add(tFieldDescuento);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 224, 942, 9);
		frame.getContentPane().add(separator);

		JLabel lblSeleccioneUnaPlataforma = new JLabel("Seleccione una plataforma");
		lblSeleccioneUnaPlataforma.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSeleccioneUnaPlataforma.setBounds(27, 265, 156, 14);
		frame.getContentPane().add(lblSeleccioneUnaPlataforma);

		comboBoxPlataforma = new JComboBox<String>();
		comboBoxPlataforma.setBounds(251, 262, 231, 20);
		comboBoxPlataforma.addItem("Seleccionar...");
		plataformas.forEach(plataforma -> comboBoxPlataforma.addItem(plataforma.getNombre()));
		comboBoxPlataforma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleSelectPlataforma();
			}
		});
		frame.getContentPane().add(comboBoxPlataforma);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(598, 557, 89, 23);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarFormulario();
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnCerrar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(accept);
		btnAceptar.setBounds(251, 557, 89, 23);
		frame.getContentPane().add(btnAceptar);

		frame.getContentPane().add(btnCerrar);
		loadTable(getEspectaculosData(Collections.emptyList()), columns);

	}

	ActionListener accept = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(AgregarEspectaculoAPaquete.this.frame,
						"Por favor seleccione una fila para continuar", "Error al seleccionar",
						JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					Factory.getInstance().getIPaqueteController().agregarEspectaculoAPaquete(data[table.getSelectedRow()][NOMBRE_COL].toString(), selectedPaquete.getNombre());
					JOptionPane.showMessageDialog(AgregarEspectaculoAPaquete.this.frame, "Espectaculo agregado existosamente!", "Agregar espectaculo a paquete",
							JOptionPane.INFORMATION_MESSAGE);
					AgregarEspectaculoAPaquete.this.frame.dispose();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(AgregarEspectaculoAPaquete.this.frame,
							exception.getMessage(), "Error al agregar espectáculo",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};

    private Object[][] getEspectaculosData(List<EspectaculoDTO> espectaculos) {
        data = new Object[espectaculos.size()][columns.length];
        int i = 0;
        for( EspectaculoDTO espectaculoDTO : espectaculos){
            data[i] = getEspectaculoRow(espectaculoDTO);
            i++;
        }
        return data;
    }

    private String[] getEspectaculoRow(EspectaculoDTO espectaculo){
        String [] row = {espectaculo.getNombre(),espectaculo.getDescripcion(), espectaculo.getDuracion().toString(), espectaculo.getCantMin().toString(),
                espectaculo.getCantMax().toString(), espectaculo.getUrl(), espectaculo.getCosto().toString(), format.format(espectaculo.getFechaReg()),
                espectaculo.getPlataforma().getNombre(), espectaculo.getArtista().getEmail()};
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
        for (Component c : frame.getContentPane().getComponents()) {
            if (c.equals(scrollPane)) {
                frame.getContentPane().remove(scrollPane);
            }
        }
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(27, 357, 826, 189);
        frame.getContentPane().add(scrollPane);
    }

    private void handleSelectPaquete() {
    	Optional<PaqueteDTO> optPaquete = paquetes
				.stream()
				.filter(paqueteDTO -> paqueteDTO.getNombre().equals(comboBoxPaquete.getSelectedItem()))
				.findFirst();
    	if (optPaquete.isPresent()) {
    		selectedPaquete = optPaquete.get();
    		tFieldNombre.setText(selectedPaquete.getNombre());
    		tFieldDescripcion.setText(selectedPaquete.getDescripcion());
    		tFieldFechaInicio.setText(format.format(selectedPaquete.getFechaInicio()));
    		tFieldFechaFin.setText(format.format(selectedPaquete.getFechaFin()));
    		tFieldDescuento.setText(selectedPaquete.getDescuento().toString());
    	} else {
    		limpiarPaquete();
    	}
		
	}

	private void handleSelectPlataforma() {
		Optional<PlataformaDTO> optPlataforma = plataformas
				.stream()
				.filter(plataforma -> plataforma.getNombre().equals(comboBoxPlataforma.getSelectedItem()))
				.findFirst();
		if (comboBoxPaquete.getSelectedItem() == null || comboBoxPaquete.getSelectedItem().toString().contains("Seleccionar") ) {
			JOptionPane.showMessageDialog(AgregarEspectaculoAPaquete.this.frame,
					"Debe seleccionar un Paquete", "Error al obtener los espectaculos no asociados al paquete",
					JOptionPane.ERROR_MESSAGE);
		}else if (optPlataforma.isPresent()) {
			selectedPlataforma = optPlataforma.get();
			try {
				espectaculosNoEnPaquete = paqueteController.listarEspectaculosNoEnPaquete(selectedPaquete.getNombre(), selectedPlataforma.getNombre());
				loadTable(getEspectaculosData(espectaculosNoEnPaquete), columns);
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(AgregarEspectaculoAPaquete.this.frame,
						exception.getMessage(), "Error al obtener los espectaculos no asociados al paquete",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
    		limpiarPlataforma();
    	}
		
	}

	private void limpiarFormulario() {
		tFieldNombre.setText("");
		tFieldDescripcion.setText("");
		tFieldFechaInicio.setText("");
		tFieldFechaFin.setText("");
		tFieldDescuento.setText("");
		selectedPaquete = null;
		selectedPlataforma = null;
		espectaculosNoEnPaquete = null;
		comboBoxPaquete.removeAllItems();
		comboBoxPlataforma.removeAllItems();
	}
	
	private void limpiarPaquete() {
		tFieldNombre.setText("");
		tFieldDescripcion.setText("");
		tFieldFechaInicio.setText("");
		tFieldFechaFin.setText("");
		tFieldDescuento.setText("");
		selectedPaquete = null;
	}
	
	private void limpiarPlataforma() {
		selectedPlataforma = null;
		espectaculosNoEnPaquete = null;
		loadTable(getEspectaculosData(Collections.emptyList()), columns);
	}
}