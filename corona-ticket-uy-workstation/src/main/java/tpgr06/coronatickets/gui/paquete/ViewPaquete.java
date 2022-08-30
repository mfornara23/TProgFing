package tpgr06.coronatickets.gui.paquete;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.gui.funcion.ConsultaFuncion;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.paquete.IPaqueteController;
import tpgr06.coronatickets.sys.paquete.PaqueteDTO;
import tpgr06.coronatickets.sys.plataforma.PlataformaDTO;

public class ViewPaquete {

	private IPaqueteController paqueteController;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private String[] columns = { "Nombre", "Descripcion", "Duracion", "Espec. Min.", "Espec. Max.", "URL", "Costo",
			"Fecha Alta", "Plataforma", "Artista", "Categorias"};
	private JTable table = null;
	private JInternalFrame frame;
	private JScrollPane scrollPane = null;
	private JTextField tFieldNombre;
	private JTextField tFieldDescripcion;
	private JTextField tFieldFechaInicio;
	private JTextField tFieldFechaFin;
	private JTextField tFieldDescuento;
	private JComboBox<String> paqueteFinder;
	private JLabel lblIngresoNombre;
	private JLabel lblInfoPaquete;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblFechaInicio;
	private JLabel lblFechaFin;
	private JLabel lblDescuento;
	private JLabel lblEspectaculos;
	private JButton btnBuscar;
	private JButton btnCerrar;

	public static void main(PaqueteDTO paqueteDTO) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPaquete window = new ViewPaquete(paqueteDTO);
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
	public ViewPaquete(PaqueteDTO paquete) {
		paqueteController = Factory.getInstance().getIPaqueteController();
		if (paquete != null) {
			buscarPaqueteHandler(null);
		}

		frame = new JInternalFrame();
		frame.setTitle("Consulta de Paquete");
		frame.setResizable(false);
		frame.setBounds(100, 100, 881, 534);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblIngresoNombre = new JLabel("Paquetes disponibles");
		lblIngresoNombre.setBounds(27, 24, 156, 14);
		frame.getContentPane().add(lblIngresoNombre);

		paqueteFinder = new JComboBox<String>();
		paqueteFinder.setBounds(228, 20, 271, 23);
		frame.getContentPane().add(paqueteFinder);

        cargarPaquetes();

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPaqueteHandler(e);
			}
		});
		btnBuscar.setBounds(527, 16, 95, 30);
		frame.getContentPane().add(btnBuscar);

		lblInfoPaquete  =new JLabel("Informaci\u00F3n de paquete");
		lblInfoPaquete.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInfoPaquete.setBounds(27, 62, 180, 14);
		frame.getContentPane().add(lblInfoPaquete);

		// Una etiqueta (JLabel) indicando los campos que se veran
		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(37, 92, 65, 14);
		frame.getContentPane().add(lblNombre);

		lblDescripcion = new JLabel("Descripci\u00F3n");
		lblDescripcion.setBounds(37, 127, 95, 14);
		frame.getContentPane().add(lblDescripcion);

		lblFechaInicio = new JLabel("Fecha de inicio");
		lblFechaInicio.setBounds(37, 163, 95, 14);
		frame.getContentPane().add(lblFechaInicio);

		lblFechaFin = new JLabel("Fecha de fin");
		lblFechaFin.setBounds(37, 203, 83, 14);
		frame.getContentPane().add(lblFechaFin);

		lblDescuento = new JLabel("Descuento");
		lblDescuento.setBounds(37, 238, 95, 14);
		frame.getContentPane().add(lblDescuento);

		lblEspectaculos = new JLabel("Espect\u00E1culos");
		lblEspectaculos.setBounds(37, 282, 105, 14);
		frame.getContentPane().add(lblEspectaculos);

		tFieldNombre = new JTextField();
		tFieldNombre.setEditable(false);
		tFieldNombre.setBounds(183, 88, 271, 24);
		frame.getContentPane().add(tFieldNombre);

		tFieldDescripcion = new JTextField();
		tFieldDescripcion.setEditable(false);
		tFieldDescripcion.setBounds(183, 122, 271, 24);
		frame.getContentPane().add(tFieldDescripcion);

		tFieldFechaInicio = new JTextField();
		tFieldFechaInicio.setEditable(false);
		tFieldFechaInicio.setBounds(183, 158, 271, 24);
		frame.getContentPane().add(tFieldFechaInicio);

		tFieldFechaFin = new JTextField();
		tFieldFechaFin.setEditable(false);
		tFieldFechaFin.setBounds(183, 198, 271, 24);
		frame.getContentPane().add(tFieldFechaFin);

		tFieldDescuento = new JTextField();
		tFieldDescuento.setEditable(false);
		tFieldDescuento.setBounds(183, 233, 271, 24);
		frame.getContentPane().add(tFieldDescuento);

		// Un boton (JButton) con un evento asociado que permite cerrar el formulario (solo ocultarlo).
		// Dado que antes de cerrar se limpia el formulario, se invoca un metodo reutilizable para ello.
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarFormulario();
				frame.dispose();
			}
		});
		btnCerrar.setBounds(769, 451, 89, 23);
		frame.getContentPane().add(btnCerrar);
		loadTable(getEspectaculosData(Collections.emptyList()), columns);
	}

    private Object[][] getEspectaculosData(List<EspectaculoDTO> espectaculos) {
        Object[][] data = new Object[espectaculos.size()][columns.length];
        int i = 0;
        for( EspectaculoDTO espectaculoDTO : espectaculos){
            data[i] = getEspectaculoRow(espectaculoDTO);
            i++;
        }
        return data;
    }

    private String[] getEspectaculoRow(EspectaculoDTO espectaculo){
        String [] row = {espectaculo.getNombre(),espectaculo.getDescripcion(), espectaculo.getDuracion().toString(), espectaculo.getCantMin().toString(),
                espectaculo.getCantMax().toString(), espectaculo.getUrl(), espectaculo.getCosto().toString(), dateFormatter.format(espectaculo.getFechaReg()),
                espectaculo.getPlataforma().getNombre(), espectaculo.getArtista().getEmail(), espectaculo.getCategorias().toString()};
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
        scrollPane.setBounds(27, 320, 800, 110);
        frame.getContentPane().add(scrollPane);        
    }

    private void cargarPaquetes() {
		List<PaqueteDTO> paquetes = paqueteController.listarPaquetes();
		if(paquetes.isEmpty()) {
			JOptionPane.showMessageDialog(ViewPaquete.this.frame,
					"No hay paquetes en el sistema", "Error",
					JOptionPane.WARNING_MESSAGE);
			this.frame.dispose();
		}
		for(PaqueteDTO p:paquetes) {
			paqueteFinder.addItem(p.getNombre());
		}
    }
    
	protected void buscarPaqueteHandler(ActionEvent e) {
		try {
		    PaqueteDTO paquete = paqueteController.consultaPaquete(String.valueOf(paqueteFinder.getSelectedItem()));
			tFieldNombre.setText(paquete.getNombre());
			tFieldDescripcion.setText(paquete.getDescripcion());
			tFieldFechaInicio.setText(dateFormatter.format(paquete.getFechaInicio()));
			tFieldFechaFin.setText(dateFormatter.format(paquete.getFechaFin()));
			tFieldDescuento.setText(paquete.getDescuento().toString());
            loadTable(getEspectaculosData(paquete.getEspectaculos()), columns);
		} catch (Exception e1) {
			// Si el paquete no existe, se muestra mensaje de error y se limpia el formulario
			JOptionPane.showMessageDialog(ViewPaquete.this.frame, e1.getMessage(), "Buscar Paquete", JOptionPane.ERROR_MESSAGE);
			limpiarFormulario();
		}
	}

	private void limpiarFormulario() {
		tFieldNombre.setText("");
		tFieldDescripcion.setText("");
		tFieldFechaInicio.setText("");
		tFieldFechaFin.setText("");
		tFieldDescuento.setText("");
	}
}