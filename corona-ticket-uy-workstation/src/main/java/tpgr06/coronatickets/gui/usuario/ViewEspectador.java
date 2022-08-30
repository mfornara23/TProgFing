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
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import tpgr06.coronatickets.gui.MenuPrincipal;
import tpgr06.coronatickets.gui.funcion.MostarFuncionDTO;
import tpgr06.coronatickets.sys.Factory;
import tpgr06.coronatickets.sys.exceptions.NotFoundException;
import tpgr06.coronatickets.sys.funcion.FuncionDTO;
import tpgr06.coronatickets.sys.usuario.artista.ArtistaDTO;
import tpgr06.coronatickets.sys.usuario.espectador.EspectadorDTO;

public class ViewEspectador {

	private final int NOMBRE_COL = 0;
	private Factory factory = Factory.getInstance();
	SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm");
	private JInternalFrame frameEspectador;
	private JTextField nicknameField;
	private JTextField nombreField;
	private JTextField apellidoField;
	private JTextField emailField;
	JDateChooser fechaNacmiento;
	Object[][] data;
	private String[] columns = { "Nombre", "Fecha", "Hora Inicio", "Fecha Alta", "Artista Invitados"};
	private JTable table = null;
	private JScrollPane scrollPane = null;

	/**
	 * Launch the application.
	 */
	public static void main(final EspectadorDTO user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewEspectador window = new ViewEspectador(user);
					window.frameEspectador.setVisible(true);
					MenuPrincipal.mainPanel.removeAll();
					MenuPrincipal.mainPanel.add(window.frameEspectador);
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
	public ViewEspectador(EspectadorDTO userToView) throws NotFoundException {
		initialize(userToView);
	}

	/**
	 * Obtiene los espectaculos del sistema y los parsea a la forma adecuada para la
	 * tabla.
	 * 
	 * @return Un array bidimensional con los datos de los usuarios.
	 * @throws NotFoundException 
	 */
	private Object[][] getFuncionesData(EspectadorDTO espectador) throws NotFoundException {
		List<FuncionDTO> funciones = factory.getIEspectaculoController().listarFuncionesByEspectador(espectador.getNickname());
		
		Object[][] data = new Object[funciones.size()][columns.length];
		int i = 0;
		for (FuncionDTO fun : funciones) {
			data[i] = getFuncionRow(fun);
			i++;
		}

		return data;
	}

	/**
	 * Crea la fila con los datos necesarios para la tabla
	 * 
	 * @param espec FuncionDTO del que se obtienen los datos
	 * @return Array de Strings con los datos de la fila
	 */
	private String[] getFuncionRow(FuncionDTO fun) {
		
		String invitados = "";
		
		for (ArtistaDTO a : fun.getInvitados()) {
			invitados+="  ".concat(a.getNombre());
		}
		
		String[] row = { fun.getNombre(), DateFormat.format(fun.getFecha()), TimeFormat.format(fun.getFecha()),
				DateFormat.format(fun.getFechaReg()), invitados};
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
		for (Component c : frameEspectador.getContentPane().getComponents()) {
			if (c.equals(scrollPane)) {
				frameEspectador.getContentPane().remove(scrollPane);
			}

		}
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 192, 760, 221);
		frameEspectador.getContentPane().add(scrollPane);

	}

	/**
	 * Initialize the contents of the frame.
	 * @throws NotFoundException 
	 */
	private void initialize(EspectadorDTO userToView) throws NotFoundException {
		frameEspectador = new JInternalFrame();
		frameEspectador.setTitle("Ver Espectador");
		frameEspectador.setSize(400, 550);
		frameEspectador.setBounds(300, 350, 800, 510);
		frameEspectador.setResizable(false);
		frameEspectador.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameEspectador.getContentPane().setLayout(null);

		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(29, 22, 104, 14);
		frameEspectador.getContentPane().add(lblNickname);

		nicknameField = new JTextField();
		nicknameField.setEditable(false);
		nicknameField.setBounds(114, 19, 205, 20);
		frameEspectador.getContentPane().add(nicknameField);
		nicknameField.setColumns(10);
		nicknameField.setText(userToView.getNickname());

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(401, 22, 59, 14);
		frameEspectador.getContentPane().add(lblNombre);

		nombreField = new JTextField();
		nombreField.setEditable(false);
		nombreField.setBounds(491, 19, 205, 20);
		frameEspectador.getContentPane().add(nombreField);
		nombreField.setColumns(10);
		nombreField.setText(userToView.getNombre());

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(29, 61, 59, 14);
		frameEspectador.getContentPane().add(lblApellido);

		apellidoField = new JTextField();
		apellidoField.setEditable(false);
		apellidoField.setBounds(114, 58, 205, 20);
		frameEspectador.getContentPane().add(apellidoField);
		apellidoField.setColumns(10);
		apellidoField.setText(userToView.getApellido());

		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(401, 61, 59, 14);
		frameEspectador.getContentPane().add(lblEmail);

		emailField = new JTextField();
		emailField.setEditable(false);
		emailField.setBounds(491, 58, 205, 20);
		frameEspectador.getContentPane().add(emailField);
		emailField.setColumns(10);
		emailField.setText(userToView.getEmail());

		JLabel lblFecha = new JLabel("Fecha Nac.:");
		lblFecha.setBounds(29, 101, 117, 14);
		frameEspectador.getContentPane().add(lblFecha);

		fechaNacmiento = new JDateChooser();
		fechaNacmiento.getCalendarButton().setEnabled(false);
		fechaNacmiento.getDateEditor().setEnabled(false);
		fechaNacmiento.setBounds(114, 95, 205, 20);
		frameEspectador.getContentPane().add(fechaNacmiento);
		fechaNacmiento.setDate(userToView.getFechaNac());

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBounds(98, 439, 86, 23);
		frameEspectador.getContentPane().add(btnAccept);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(607, 439, 86, 23);
		frameEspectador.getContentPane().add(btnCancel);


		JLabel lblEspectaculos = new JLabel("Funciones:");
		lblEspectaculos.setBounds(29, 163, 91, 16);
		frameEspectador.getContentPane().add(lblEspectaculos);

		data = getFuncionesData(userToView);
		loadTable(data, columns);

		// ----------------------------------------------------------------------------/

		ActionListener accept = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(ViewEspectador.this.frameEspectador,
							"Por favor seleccione una fila para continuar", "Error al seleccionar",
							JOptionPane.WARNING_MESSAGE);
				} else {
					FuncionDTO funToView = null;
					try {
						funToView = userToView.getFuncionByName( data[table.getSelectedRow()][NOMBRE_COL].toString());
					} catch (NotFoundException e2) {
						JOptionPane.showMessageDialog(ViewEspectador.this.frameEspectador,
								"Error al seleccionar Funcion", "Error interno",
								JOptionPane.ERROR_MESSAGE);
					}
					MostarFuncionDTO.main(funToView);
				}

			}
		};

		ActionListener cancel = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ViewEspectador.this.frameEspectador.dispose();

			}
		};

		btnAccept.addActionListener(accept);
		btnCancel.addActionListener(cancel);
	}
}
