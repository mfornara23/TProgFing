package tpgr06.coronatickets.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tpgr06.coronatickets.gui.categoria.AltaCategoria;
import tpgr06.coronatickets.gui.espectaculo.AltaEspectaculo;
import tpgr06.coronatickets.gui.espectaculo.PlataformaSelection;
import tpgr06.coronatickets.gui.espectaculo.ValidarEspectaculo;
import tpgr06.coronatickets.gui.funcion.AltaFuncion;
import tpgr06.coronatickets.gui.funcion.ConsultaFuncion;
import tpgr06.coronatickets.gui.funcion.RegistroFuncion;
import tpgr06.coronatickets.gui.paquete.AgregarEspectaculoAPaquete;
import tpgr06.coronatickets.gui.paquete.AltaPaquete;
import tpgr06.coronatickets.gui.paquete.ViewPaquete;
import tpgr06.coronatickets.gui.plataforma.AltaPlataforma;
import tpgr06.coronatickets.gui.usuario.ConsultaUsuario;
import tpgr06.coronatickets.gui.usuario.ModificarUsuario;
import tpgr06.coronatickets.gui.usuario.UserTypeSelection;
import tpgr06.coronatickets.sys.Utils;


public class MenuPrincipal {

	public static JFrame frame;
	public static JPanel mainPanel;
	private Boolean datosCargados=false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					MenuPrincipal window = new MenuPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 900, 900);
		frame.setTitle("Menu Principal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		//Menu Usuarios
		JMenu menuUsuarios = new JMenu("Usuarios");
		menuBar.add(menuUsuarios);

		JMenuItem menuItemAltaUsuario = new JMenuItem("Alta de Usuario");
		menuUsuarios.add(menuItemAltaUsuario);

		JMenuItem menuItemConsultaUsuario = new JMenuItem("Consulta de Usuario");
		menuUsuarios.add(menuItemConsultaUsuario);

		JMenuItem menuItemModificarUsuario = new JMenuItem("Modificar Datos de Usuario");
		menuUsuarios.add(menuItemModificarUsuario);

		//Menu Espectaculos
		JMenu menuEspectaculos = new JMenu("Espect\u00E1culos");
		menuBar.add(menuEspectaculos);

		JMenuItem menuItemAltaEspec = new JMenuItem("Alta Espect\u00E1culo");
		menuEspectaculos.add(menuItemAltaEspec);

		JMenuItem menuItemConsultaEspec = new JMenuItem("Consulta de Espect\u00E1culo");
		menuEspectaculos.add(menuItemConsultaEspec);
		
		JMenuItem menuItemValidarEspec = new JMenuItem("Validar Espect\u00E1culo");
		menuEspectaculos.add(menuItemValidarEspec);

		//Menu Funciones
		JMenu menuFunciones = new JMenu("Funciones");
		menuBar.add(menuFunciones);

		JMenuItem menuItemAltaFun = new JMenuItem("Alta de Funci\u00F3n de Espect\u00E1culo");
		menuFunciones.add(menuItemAltaFun);

		JMenuItem menuItemConsultaFun = new JMenuItem("Consulta de Funci\u00F3n de Espect\u00E1culo");
		menuFunciones.add(menuItemConsultaFun);

		JMenuItem menuItemRegistroFun = new JMenuItem("Registro a Funci\u00F3n de Espect\u00E1culo");
		menuFunciones.add(menuItemRegistroFun);

		//Menu Paquetes
		JMenu menuPaquetes = new JMenu("Paquetes");
		menuBar.add(menuPaquetes);

		JMenuItem menuItemCrearPaq = new JMenuItem("Crear Paquete de Espect\u00E1culos");
		menuPaquetes.add(menuItemCrearPaq);

		JMenuItem menuItemAgregarEspecPaq = new JMenuItem("Agregar Espect\u00E1culo a Paquete");
		menuPaquetes.add(menuItemAgregarEspecPaq);

		JMenuItem menuItemConsultaPaq = new JMenuItem("Consulta de Paquete de Espect\u00E1culos");
		menuPaquetes.add(menuItemConsultaPaq);

		//Menu Plataformas
		JMenu menuPlataformas = new JMenu("Plataformas");
		menuBar.add(menuPlataformas);

		JMenuItem menuItemAltaPlat = new JMenuItem("Alta de Plataforma");
		menuPlataformas.add(menuItemAltaPlat);
		
		JMenu menuCategorias = new JMenu("Categor\u00EDas");
		menuBar.add(menuCategorias);
		
		JMenuItem menuItemCategorias = new JMenuItem("Alta Categor\u00EDa");
		menuCategorias.add(menuItemCategorias);

		JMenu menuDatosdePrueba = new JMenu("Datos de Prueba");
		menuBar.add(menuDatosdePrueba);

		JMenuItem menuItemDatosdePrueba = new JMenuItem("Cargar datos de prueba");
		menuDatosdePrueba.add(menuItemDatosdePrueba);


		//----------------------------------------------------------------------------//
		//Action Listeners para las opciones

		ActionListener altaUsuario = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				UserTypeSelection.main(null);				
			}
		};

		ActionListener consultaUsuario = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ConsultaUsuario.main(null);

			}
		}; 

		ActionListener modificarUsuario = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ModificarUsuario.main(null);

			}
		};

		ActionListener altaEspectaculo = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AltaEspectaculo.main(null);

			}
		};

		ActionListener consultaEspectaculo = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				PlataformaSelection.main(null);

			}
		};
		
		ActionListener validarEspectaculo = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ValidarEspectaculo.main(null);

			}
		};

		ActionListener altaFuncion = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AltaFuncion.main(null);	

			}
		};

		ActionListener consultaFuncion = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ConsultaFuncion.main(null);

			}
		};

		ActionListener registroFuncion = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				RegistroFuncion.main(null);

			}
		};

		ActionListener crearPaquete = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AltaPaquete.main(null);

			}
		};

		ActionListener agregarEspectaculoPaquete = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AgregarEspectaculoAPaquete.main(null);
			}
		};

		ActionListener consultaPaquete = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ViewPaquete.main(null);
			}
		};

		ActionListener altaPlataforma = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AltaPlataforma.main(null);

			}
		};
		
		ActionListener datosPrueba = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(!datosCargados) { 
					try {
						Utils.cargarDatosDePrueba();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					datosCargados=true;
				}
			JOptionPane.showMessageDialog(MenuPrincipal.frame, "Datos cargados correctamente", "Carga Exitosa",JOptionPane.INFORMATION_MESSAGE);
			}
		};
		
		ActionListener altaCategoria = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				AltaCategoria.main(null);

			}
		};

		//----------------------------------------------------------------------//
		//Asignacion de Acciones
		menuItemAltaUsuario.addActionListener(altaUsuario);
		menuItemConsultaUsuario.addActionListener(consultaUsuario);
		menuItemModificarUsuario.addActionListener(modificarUsuario);

		menuItemAltaEspec.addActionListener(altaEspectaculo);
		menuItemConsultaEspec.addActionListener(consultaEspectaculo);
		menuItemValidarEspec.addActionListener(validarEspectaculo);

		menuItemAltaFun.addActionListener(altaFuncion);
		menuItemConsultaFun.addActionListener(consultaFuncion);
		menuItemRegistroFun.addActionListener(registroFuncion);

		menuItemCrearPaq.addActionListener(crearPaquete);
		menuItemConsultaPaq.addActionListener(consultaPaquete);
		menuItemAgregarEspecPaq.addActionListener(agregarEspectaculoPaquete);

		menuItemAltaPlat.addActionListener(altaPlataforma);

		menuItemDatosdePrueba.addActionListener(datosPrueba);
		
		menuItemCategorias.addActionListener(altaCategoria);
	}

}
