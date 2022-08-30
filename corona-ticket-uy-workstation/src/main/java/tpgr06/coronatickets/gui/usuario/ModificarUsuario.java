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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.usuario.TipoUsuario;
import tpgr06.coronatickets.sys.usuario.UsuarioDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;
import tpgr06.coronatickets.sys.usuario.espectador.EspectadorDTO;

public class ModificarUsuario {

	private Factory factory = Factory.getInstance();
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private JInternalFrame frame;
	private String[] columns = { "Nombre", "Apellido", "Nickname", "e-mail", "Fecha Nac." , "Tipo" };
	private final int EMAIL_COL = 3;
	private Object[][] data = getUsersData();
	private JTable table = null;
	private JScrollPane scrollPane = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificarUsuario window = new ModificarUsuario();
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
	 * Create the application.
	 */
	public ModificarUsuario() {
		initialize();
	}

	/**
	 * Obtiene los usuarios del sistema y los parsea a la forma adecuada para la tabla.
	 * @return Un array bidimensional con los datos de los usuarios.
	 */
	private Object[][] getUsersData() {
		List<UsuarioDTO> users = factory.getIUsuarioController().listarUsuario();
		Object[][] data = new Object[users.size()][columns.length];
		int i = 0;
		for( UsuarioDTO user : users){
			data[i] = getUserRow(user);
			i++;
		}

		return data;
	}

	/**
	 * Crea la fila con los datos necesarios para la tabla
	 * @param user UsuarioDTO del que se obtienen los datos
	 * @return Array de Strings con los datos de la fila
	 */
	private String[] getUserRow(UsuarioDTO user){
		String [] row = {user.getNombre(),user.getApellido(),user.getNickname(),user.getEmail(),
				format.format(user.getFechaNac()).toString(),
				(user instanceof EspectadorDTO) ? TipoUsuario.ESPECTADOR.toString() : TipoUsuario.ARTISTA.toString()};
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
		scrollPane.setBounds(12, 13, 600, 400);
		frame.getContentPane().add(scrollPane);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JInternalFrame();
		frame.setBounds(100, 100, 645, 510);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		loadTable(data, columns);

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(83, 426, 97, 25);
		frame.getContentPane().add(btnAccept);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(430, 426, 97, 25);
		frame.getContentPane().add(btnCancel);

		// ----------------------------------------------------------------------------/

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(ModificarUsuario.this.frame,
							"Por favor seleccione una fila para continuar", "Error al seleccionar",
							JOptionPane.WARNING_MESSAGE);
				} else {
					UsuarioDTO userToUpdate = factory.getIUsuarioController().getUsuarioByEmail(data[table.getSelectedRow()][EMAIL_COL].toString());
					if(userToUpdate instanceof ArtistaDTO) {
						UpdateArtista.main((ArtistaDTO) userToUpdate);
					} else {
						UpdateEspectador.main((EspectadorDTO) userToUpdate);
					}
					ModificarUsuario.this.frame.dispose();
				}

			}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ModificarUsuario.this.frame.dispose();

			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}

}
