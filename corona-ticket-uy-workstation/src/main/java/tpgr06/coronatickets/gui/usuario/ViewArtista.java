package tpgr06.coronatickets.gui.usuario;

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
import tpgr06.coronatickets.gui.espectaculo.ViewEspectaculo;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.espectaculo.EspectaculoDTO;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;

public class ViewArtista {

	private final int NOMBRE_COL = 0;
	private Factory factory = Factory.getInstance();
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private JInternalFrame frameArtista;
	private JTextField nicknameField;
	private JTextField nombreField;
	private JTextField apellidoField;
	private JTextField emailField;
	JDateChooser fechaNacmiento;
	private JTextField siteField;
	JTextPane descTextPane;
	JTextPane bioTextPane;
	Object[][] data;
	private String[] columns = { "Nombre", "Descripcion", "Duracion", "Espec. Min.", "Espec. Max.", "URL", "Costo",
			"Fecha Alta", "Plataforma" };
	private JTable table = null;
	private JScrollPane scrollPane = null;

	/**
	 * Launch the application.
	 */
	public static void main(final ArtistaDTO user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewArtista window = new ViewArtista(user);
					window.frameArtista.setVisible(true);
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(window.frameArtista);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws NotFoundException 
	 */
	public ViewArtista(ArtistaDTO userToView) throws NotFoundException {
		initialize(userToView);
	}

	/**
	 * Obtiene los espectaculos del sistema y los parsea a la forma adecuada para la
	 * tabla.
	 * 
	 * @return Un array bidimensional con los datos de los usuarios.
	 * @throws NotFoundException 
	 */
	private Object[][] getEspectaculosData(ArtistaDTO artista) throws NotFoundException {
		List<EspectaculoDTO> espectaculos = factory.getIEspectaculoController().listarEspectaculosByArtista(artista.getNickname());
		Object[][] data = new Object[espectaculos.size()][columns.length];
		int i = 0;
		for (EspectaculoDTO espec : espectaculos) {
			data[i] = getEspectaculoRow(espec);
			i++;
		}

		return data;
	}

	/**
	 * Crea la fila con los datos necesarios para la tabla
	 * 
	 * @param espec EspectaculoDTO del que se obtienen los datos
	 * @return Array de Strings con los datos de la fila
	 */
	private String[] getEspectaculoRow(EspectaculoDTO espec) {
		String[] row = { espec.getNombre(), espec.getDescripcion(), espec.getDuracion().toString(),
				espec.getCantMin().toString(), espec.getCantMax().toString(), espec.getUrl(), espec.getUrl(),
				espec.getCosto().toString(), format.format(espec.getFechaReg()), espec.getPlataforma().getNombre() };
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
		for (Component c : frameArtista.getContentPane().getComponents()) {
			if (c.equals(scrollPane)) {
				frameArtista.getContentPane().remove(scrollPane);
			}

		}
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 270, 760, 141);
		frameArtista.getContentPane().add(scrollPane);

	}

	/**
	 * Initialize the contents of the frame.
	 * @throws NotFoundException 
	 */
	private void initialize(ArtistaDTO userToView) throws NotFoundException {
		frameArtista = new JInternalFrame();
		frameArtista.setTitle("Ver Artista");
		frameArtista.setSize(400, 550);
		frameArtista.setBounds(300, 350, 800, 510);
		frameArtista.setResizable(false);
		frameArtista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameArtista.getContentPane().setLayout(null);

		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(29, 22, 104, 14);
		frameArtista.getContentPane().add(lblNickname);

		nicknameField = new JTextField();
		nicknameField.setEditable(false);
		nicknameField.setBounds(114, 19, 205, 20);
		frameArtista.getContentPane().add(nicknameField);
		nicknameField.setColumns(10);
		nicknameField.setText(userToView.getNickname());

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(401, 22, 59, 14);
		frameArtista.getContentPane().add(lblNombre);

		nombreField = new JTextField();
		nombreField.setEditable(false);
		nombreField.setBounds(491, 19, 205, 20);
		frameArtista.getContentPane().add(nombreField);
		nombreField.setColumns(10);
		nombreField.setText(userToView.getNombre());

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(29, 61, 59, 14);
		frameArtista.getContentPane().add(lblApellido);

		apellidoField = new JTextField();
		apellidoField.setEditable(false);
		apellidoField.setBounds(114, 58, 205, 20);
		frameArtista.getContentPane().add(apellidoField);
		apellidoField.setColumns(10);
		apellidoField.setText(userToView.getApellido());

		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(401, 61, 59, 14);
		frameArtista.getContentPane().add(lblEmail);

		emailField = new JTextField();
		emailField.setEditable(false);
		emailField.setBounds(491, 58, 205, 20);
		frameArtista.getContentPane().add(emailField);
		emailField.setColumns(10);
		emailField.setText(userToView.getEmail());

		JLabel lblFecha = new JLabel("Fecha Nac.:");
		lblFecha.setBounds(401, 101, 117, 14);
		frameArtista.getContentPane().add(lblFecha);

		fechaNacmiento = new JDateChooser();
		fechaNacmiento.getCalendarButton().setEnabled(false);
		fechaNacmiento.getDateEditor().setEnabled(false);
		fechaNacmiento.setBounds(491, 95, 205, 20);
		frameArtista.getContentPane().add(fechaNacmiento);
		fechaNacmiento.setDate(userToView.getFechaNac());

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(123, 439, 86, 23);
		frameArtista.getContentPane().add(btnAccept);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(610, 439, 86, 23);
		frameArtista.getContentPane().add(btnCancel);

		JLabel lblSite = new JLabel("Sitio Web:");
		lblSite.setBounds(29, 100, 104, 16);
		frameArtista.getContentPane().add(lblSite);

		siteField = new JTextField();
		siteField.setEditable(false);
		siteField.setBounds(114, 97, 205, 22);
		frameArtista.getContentPane().add(siteField);
		siteField.setColumns(10);
		siteField.setText(userToView.getSitioWeb());

		JLabel lblDesc = new JLabel("Descripci\u00F3n Gral:");
		lblDesc.setBounds(29, 138, 117, 16);
		frameArtista.getContentPane().add(lblDesc);

		descTextPane = new JTextPane();
		descTextPane.setBounds(150, 237, 205, 50);
		descTextPane.setText(userToView.getDescripcion());
		descTextPane.setEditable(false);
		frameArtista.getContentPane().add(descTextPane);

		JScrollPane scrollDesc = new JScrollPane(descTextPane);
		scrollDesc.setEnabled(false);
		scrollDesc.setBounds(29, 167, 290, 50);
		frameArtista.getContentPane().add(scrollDesc);

		JLabel lblBio = new JLabel("Bio:");
		lblBio.setBounds(401, 138, 56, 16);
		frameArtista.getContentPane().add(lblBio);

		bioTextPane = new JTextPane();
		bioTextPane.setBounds(150, 298, 205, 50);
		bioTextPane.setText(userToView.getBio());
		bioTextPane.setEditable(false);
		frameArtista.getContentPane().add(bioTextPane);

		JScrollPane scrollBio = new JScrollPane(bioTextPane);
		scrollBio.setEnabled(false);
		scrollBio.setBounds(401, 167, 295, 50);
		frameArtista.getContentPane().add(scrollBio);

		JLabel lblEspectaculos = new JLabel("Espect\u00E1culos:");
		lblEspectaculos.setBounds(29, 241, 91, 16);
		frameArtista.getContentPane().add(lblEspectaculos);

		data = getEspectaculosData(userToView);
		loadTable(data, columns);

		// ----------------------------------------------------------------------------/

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(ViewArtista.this.frameArtista,
							"Por favor seleccione una fila para continuar", "Error al seleccionar",
							JOptionPane.WARNING_MESSAGE);
				} else {
					EspectaculoDTO espec = null;
					try {
						espec =  factory.getIEspectaculoController().consultaEspectaculo(data[table.getSelectedRow()][NOMBRE_COL] .toString());
					} catch (NotFoundException e1) {
						JOptionPane.showMessageDialog(ViewArtista.this.frameArtista,
								"Hubo un error al seleccionar el espectaculo", "Error interno",
								JOptionPane.ERROR_MESSAGE);
					}
					ViewEspectaculo.main(espec);					
				}

			}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ViewArtista.this.frameArtista.dispose();

			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}
}
