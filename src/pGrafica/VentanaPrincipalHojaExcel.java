package pGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipalHojaExcel extends JFrame implements ActionListener{

	//paneles
	private JPanel panelTablero=new JPanel();
	private JPanel panelPrincipal=new JPanel();
	private JPanel panelArriba=new JPanel();
	private JPanel panelAbajo=new JPanel();

	//Declaramos un objeto JMenuBar para crear la barra que contenga los menus 
	private JMenuBar barraMenu; 

	//Declaramos los menus que estran dentro de la barra de menus que terminamos de crear
	private JMenu menuArchivo=new JMenu("Archivo");
	private JMenu menuEditar=new JMenu("Editar");  

	//items de los menus
	private JMenuItem menuArchivoNuevo=new JMenuItem("Nueva hoja");
	private JMenuItem menuArchivoCargar=new JMenuItem("Cargar hoja");
	private JMenuItem menuArchivoGuardar=new JMenuItem("Guardar hoja");

	private JMenuItem menuEditarResolver= new JMenuItem("Resolver");
	private JMenuItem menuEditarDeshacer= new JMenuItem("Deshacer");
	private JMenuItem menuEditarRehacer= new JMenuItem("Rehacer");


	//hojas de calculo
	private  HojaExcel oHojaExcel;
	private String nombreArchivo="";
	private DefaultTableModel modelo = new DefaultTableModel();
	private JTable oTabla = new JTable (modelo);
	private JButton[][] botonesCeldas;

	//panel de arriba
	private JLabel etiqueta=new JLabel("soy una etiqueta");
	private JTextField cajaTexto= new JTextField("soy una caha");
	private JButton botton1= new JButton("soy un boton");

	//panel de abajo
	private JLabel etiqueta1=new JLabel("soy una etiqueta");
	private JTextField cajaTexto1= new JTextField("soy una caha");
	private JButton botton2= new JButton("soy un boton");

	//hacer y desghacer
	ArrayList<HojaExcel> listaHojasExcelMovRealizados= new ArrayList<HojaExcel>();
	ArrayList<HojaExcel> listaHojasExcelMovDeshechos= new ArrayList<HojaExcel>();


	//constructor de la ventana
	public VentanaPrincipalHojaExcel() {
		this.setTitle("Hoja Calculo 2019");		//colocamos titulo a la ventana
		this.setSize(700,700); 					//ancho, alto
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//hacemos que cuando se cierre la ventana termina todo proceso
		this.setLocationRelativeTo(null);                       // centramos la ventana en la pantalla
		//this.setResizable(false); 							// hacemos que la ventana no sea redimiensionable
		inicializarMenus();
		add(panelPrincipal);
		cargarPaneles();

	}

	private void cargarPaneles() {
		//cargo panel principal
		panelPrincipal.setLayout(new BorderLayout());
		panelTablero.setBackground(Color.GREEN);
		panelPrincipal.add(panelTablero, BorderLayout.CENTER);

		//cargo panel de arriba
		panelPrincipal.add(panelArriba, BorderLayout.NORTH);
		//cargo panel de abajo
		panelPrincipal.add(panelAbajo, BorderLayout.SOUTH);
	}

	//panel de arriba
	private void cargoPanelArriba() {
		panelArriba.setBackground(Color.BLUE);
		panelArriba.add(etiqueta);
		panelArriba.add(cajaTexto);
		panelArriba.add(botton1);
	}

	//panel de abajo
	private void cargoPanelAbajo() {
		panelAbajo.setBackground(Color.RED);
		//.add(etiqueta1);

	}

	//metodo para inicializar los menus
	private void inicializarMenus() {
		//creo la barra Menu
		barraMenu = new JMenuBar();
		setJMenuBar(barraMenu);

		//agregar los menus en la barra de menus
		barraMenu.add(menuArchivo);
		barraMenu.add(menuEditar);

		//agregaSubmenus
		menuArchivo.add(menuArchivoNuevo);
		menuArchivo.addSeparator();//separacion barras
		menuArchivo.add(menuArchivoCargar);
		menuArchivo.addSeparator();
		menuArchivo.add(menuArchivoGuardar);

		menuEditar.add(menuEditarResolver);
		menuEditar.addSeparator();
		menuEditar.add(menuEditarDeshacer);
		menuEditar.addSeparator();//separacion de barra
		menuEditar.add(menuEditarRehacer);

		//menus desactivados
		//menuArchivoNuevo.setEnabled(false);

		//creamos eventos
		menuArchivoNuevo.addActionListener(this);
		menuArchivoCargar.addActionListener(this);
		menuArchivoGuardar.addActionListener(this);
		menuEditarResolver.addActionListener(this);
		menuEditarRehacer.addActionListener(this);
		menuEditarDeshacer.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		if(evento.getSource()==menuArchivoNuevo){
			String numeroFilasString = JOptionPane.showInputDialog("Introduzca el numero de filas:");
			int nFilas= Integer.parseInt(numeroFilasString);
			String numeroColumnasString = JOptionPane.showInputDialog("Introduzca el numero de filas:");
			int nColumnas= Integer.parseInt(numeroColumnasString);

			//para crear otra ventana nueva

			/*VentanaPrincipalHojaExcel oVentana=new VentanaPrincipalHojaExcel();
			oVentana.oHojaExcel= new HojaExcel(nFilas, nColumnas);
			oVentana.cargoPanelPrincipal();
			oVentana.setVisible(true);
			oVentana.dispose();//cerrar la ventana*/

			oHojaExcel= new HojaExcel(nFilas, nColumnas);
			cargoPanelPrincipal();
			addMovimientoRealizado(oHojaExcel);
		}
		if(evento.getSource()==menuArchivoCargar){
			lectura();
			//cargar los paneles que he creado y declarado
			cargoPanelPrincipal();	
			cargoPanelArriba();
			cargoPanelAbajo();
			addMovimientoRealizado(oHojaExcel);
		}

		if(evento.getSource()==menuArchivoGuardar){
			leerHojaPantalla();
			guardar();	
		}
		//ver si el usuario pincha en alguna celda y cambiar el dato
		for(int f=0; f<oHojaExcel.getHojaEntrada().length; f++){
			for(int c=0; c<oHojaExcel.getHojaEntrada()[0].length; c++){
				if(evento.getSource()==botonesCeldas[f][c]){
					String fila= String.valueOf(f+1);
					String columna= Character.toString((char)(65+c));
					String valor= JOptionPane.showInputDialog("Dime el valor que quieres introducir en\ncelda: Fila: "+fila+" Columna: "+columna);
					if(valor!=null){//para cuando no se cambia, se le da a cancelar al boton
						botonesCeldas[f][c].setText(valor);
						leerHojaPantalla();
						addMovimientoRealizado(oHojaExcel);
					}
				}
			}
		}
		//para resolver
		if(evento.getSource()==menuEditarResolver){
			leerHojaPantalla();
			oHojaExcel.resuelve();
			cargarHojaSolucion();
			//menuEditarDeshacer.setEnabled(false);
			//menuEditarRehacer.setEnabled(false);
		}
		if(evento.getSource()==menuEditarDeshacer){
			HojaExcel nueva=buscoUltimoMovRealizado();
			if(nueva!=null){
				oHojaExcel=nueva;
				cargoPanelPrincipal();
			}
		}

		if(evento.getSource()==menuEditarRehacer){
			HojaExcel aux=buscoUltimoMovDesHecho();
			if(aux!=null){
				oHojaExcel=aux;
				cargoPanelPrincipal();
			}
		}

	}
	private HojaExcel buscoUltimoMovDesHecho() {
		if(listaHojasExcelMovDeshechos.size()==0){
			JOptionPane.showMessageDialog(null, "no hay movimientos que rehacwer");
			return null;
		}else{
			//guardo el movimienrto que voy a deshacer
			HojaExcel oHojaExcel=listaHojasExcelMovDeshechos.get(listaHojasExcelMovDeshechos.size()-1);
			addMovimientoRealizado(oHojaExcel);

			//lo borro
			listaHojasExcelMovDeshechos.remove(listaHojasExcelMovDeshechos.size()-1);
			return oHojaExcel;
		}
	}

	//busca el ultima movimiento que he hecho actualizando la lista
	private HojaExcel buscoUltimoMovRealizado() {
		if(listaHojasExcelMovRealizados.size()==1){
			JOptionPane.showMessageDialog(null, "no hay movimientos que deshacwer");
			return null;
		}else{
			//guardo el movimienrto que voy a deshacer
			HojaExcel oHojaExcel=listaHojasExcelMovRealizados.get(listaHojasExcelMovRealizados.size()-1);
			addMovimientoDeshecho(oHojaExcel);

			//lo borro
			listaHojasExcelMovRealizados.remove(listaHojasExcelMovRealizados.size()-1);
			//la devuelvo paar imprimir panatlla
			oHojaExcel=listaHojasExcelMovRealizados.get(listaHojasExcelMovRealizados.size()-1);
			return oHojaExcel;
		}

	}

	private void addMovimientoDeshecho(HojaExcel oHoja) {
		HojaExcel oHojaAux = clonarHoja(oHoja.getHojaEntrada());
		listaHojasExcelMovDeshechos.add(oHojaAux);

	}

	//me carga la hoja de solucion
	private void cargarHojaSolucion() {
		System.out.println(oHojaExcel.getHojaEntrada().length+"tamañooooo");
		panelTablero.removeAll();

		panelTablero.setVisible(false);
		panelTablero.setLayout(new GridLayout(oHojaExcel.getHojaEntrada().length, oHojaExcel.getHojaEntrada()[0].length, 3, 3));

		/*oTabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		oTabla.getTableHeader().setReorderingAllowed(false);
		String [] columnNombre= new String[oHojaExcel.getHojaEntrada()[0].length];
		for (int i = 0; i < columnNombre.length; i++) {
			columnNombre[i]= Character.toString((char)(65+i));
		}
		oTabla =new JTable(oHojaExcel.getHojaEntrada(),columnNombre);
		JScrollPane tableContainer = new JScrollPane(oTabla);
		comprobarCeldaSeleccionada();
		panelTablero.add(tableContainer, BorderLayout.CENTER);*/


		///////////////oHojaExcel.getHojaEntrada()[0].length
		botonesCeldas= new JButton[oHojaExcel.getHojaEntrada().length][oHojaExcel.getHojaEntrada()[0].length];
		for (int f = 0; f < oHojaExcel.getHojaEntrada().length; f++) {
			for (int c = 0; c < oHojaExcel.getHojaEntrada()[0].length; c++) {
				String valorHoja=String.valueOf(oHojaExcel.getHojaSalida()[f][c]);
				JButton oBoton= new JButton(valorHoja);
				oBoton.setBackground(Color.CYAN);
				oBoton.addActionListener(this);
				botonesCeldas[f][c]=oBoton;
				panelTablero.add(oBoton);
			}
		}	
		panelTablero.setVisible(true);
		//this.pack();//ajusta la ventana al tamaÒo de la tabla


	}

	//metodo para leerl la hoja por la pantalla 
	private void leerHojaPantalla(){
		for(int f=0; f<oHojaExcel.getHojaEntrada().length; f++){
			for(int c=0; c<oHojaExcel.getHojaEntrada()[0].length; c++){
				oHojaExcel.setHojaEntradaCelda(f, c, botonesCeldas[f][c].getText());
			}
		}
	}



	private void comprobarCeldaSeleccionada() {
		oTabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 1) {
					oTabla = (JTable) evt.getSource();
					int fil = oTabla.rowAtPoint(evt.getPoint());
					int col=oTabla.columnAtPoint(evt.getPoint());
					System.out.println(fil+" "+col);
				}
			}
		});
	}
	private void guardar() {
		if(nombreArchivo.equals("")) 
			guardarComo();

		FileWriter fichero = null;	
		try {
			PrintWriter pw;
			fichero= new FileWriter(nombreArchivo);
			pw = new PrintWriter(fichero);

			//pw.println(oHojaCalculo.nColumnas()+" "+oHojaCalculo.nFilas());
			pw.println(oHojaExcel.getHojaEntrada().length+" "+oHojaExcel.getHojaEntrada()[0].length);
			//escribo la hoja en el fichero
			for (int f = 0; f < oHojaExcel.getHojaEntrada().length; f++) {
				for (int c = 0; c < oHojaExcel.getHojaEntrada()[0].length; c++) {
					pw.print(oHojaExcel.getHojaEntrada()[f][c]+" "); //sobra el ultimo espacio 
				}
				pw.println(); 
			}    
			pw.close();
			JOptionPane.showMessageDialog(null, "Hoja guardada","SALVAR",JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			try {
				// Utilizamos finally para asegurarnos que se cierra el fichero.
				if (null != fichero) {
					fichero.close();
				}
			} catch (Exception e2) {
				//e2.printStackTrace();
			}
		}	
	}

	private void guardarComo() {
		//Crear una superficie grafica para elegir el nombre del fichero a usar para guardar el juego
		final JFileChooser fc = new JFileChooser();

		//Mostrar la superficie grafica
		int returnVal = fc.showSaveDialog(null);

		//if (returnVal == JFileChooser.APPROVE_OPTION) {
		if (returnVal == 0) {
			File file = fc.getSelectedFile();
			//Guardamos el nombre del archivo en el objeto del criptojuego
			nombreArchivo=file.getAbsolutePath();//leer la ruta completa del archivo
			//le fuerzo la extension
			if(!nombreArchivo.contains(".txt")) {
				nombreArchivo+=".txt";
			}

		} else {//cancelar y X es 1

			return;//salgo del metodo
		}

		//compruebo si existe el nombre del fichero
		File f = new File(nombreArchivo);

		if (f.exists()) {
			int respuestaBoton = JOptionPane.showConfirmDialog (null, "El fichero existe quieres sobreescribirlo. \n\nSI->Sobreescribe el fichero \n\nNO->Sale sin guardar\n\n");
			if(respuestaBoton == 0){ //si es cero
				//if(respuestaBoton == JOptionPane.YES_OPTION){ //si es cero

				guardar();
			}
			else if(respuestaBoton == JOptionPane.CANCEL_OPTION) {
				return;
			}
			else { //si pulsa no o cancelar
				return;
			}
		}
		else
			//en el caso de que el nombre del fichero no exista, lo creo
			guardar();

	}

	private void cargoPanelPrincipal() {
		System.out.println(oHojaExcel.getHojaEntrada().length+"tamañooooo");
		panelTablero.removeAll();

		panelTablero.setVisible(false);
		panelTablero.setLayout(new GridLayout(oHojaExcel.getHojaEntrada().length, oHojaExcel.getHojaEntrada()[0].length, 3, 3));

		/*oTabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		oTabla.getTableHeader().setReorderingAllowed(false);
		String [] columnNombre= new String[oHojaExcel.getHojaEntrada()[0].length];
		for (int i = 0; i < columnNombre.length; i++) {
			columnNombre[i]= Character.toString((char)(65+i));
		}
		oTabla =new JTable(oHojaExcel.getHojaEntrada(),columnNombre);
		JScrollPane tableContainer = new JScrollPane(oTabla);
		comprobarCeldaSeleccionada();
		panelTablero.add(tableContainer, BorderLayout.CENTER);*/


		///////////////oHojaExcel.getHojaEntrada()[0].length
		botonesCeldas= new JButton[oHojaExcel.getHojaEntrada().length][oHojaExcel.getHojaEntrada()[0].length];
		for (int f = 0; f < oHojaExcel.getHojaEntrada().length; f++) {
			for (int c = 0; c < oHojaExcel.getHojaEntrada()[0].length; c++) {
				JButton oBoton= new JButton(oHojaExcel.getHojaEntrada()[f][c]);
				oBoton.setBackground(Color.CYAN);
				oBoton.addActionListener(this);
				botonesCeldas[f][c]=oBoton;
				panelTablero.add(oBoton);
			}
		}	
		panelTablero.setVisible(true);
		//this.pack();//ajusta la ventana al tamaÒo de la tabla

	}

	private void lectura() {
		//abre una ventana para elegir el archivo a leer
		//guardo el nombre del archivo
		leoFicheroVentana();
		System.out.println(nombreArchivo);
		ArrayList<String> lineas=new ArrayList<String>();
		//guardo en una lista todas las lineas que tiene el archivo
		//le paso la ruta del archivo
		if(nombreArchivo.equals("")) {
			JOptionPane.showMessageDialog(null, "No has abierto ningun fichero");
		}
		else {
			//guardo en lineas todas las lineas del fichero
			lineas=leoFichero();
			System.out.println(lineas);
			String dimensiones=lineas.get(0);//primera linea, dimensiones (nFilas y nColumnas)
			String[] filasCol=dimensiones.split(" ");
			int columnas=Integer.parseInt(filasCol[0]);
			int filas=Integer.parseInt(filasCol[1]);

			String[][] tablaExcel=new String[filas][columnas];

			for (int f = 0; f < filas; f++) {
				String lineaCol= lineas.get(1+f);
				String []lineaColVector= lineaCol.split(" ");
				for (int c = 0; c < columnas; c++) {
					tablaExcel[f][c]=lineaColVector[c];
				}
			}
			oHojaExcel= new HojaExcel(tablaExcel);

		}
	}



	private ArrayList<String> leoFichero() {
		FileReader fr = null;
		try {
			fr = new FileReader(nombreArchivo);
		} catch (FileNotFoundException e) {
			// Si hay algun error.....
			//e.printStackTrace();
		}
		BufferedReader bf = new BufferedReader(fr);
		String linea;
		ArrayList<String> listaLectura=new ArrayList<String>();
		try {
			while ((linea = bf.readLine())!=null) {//mientras tengamos lineas que leer
				listaLectura.add(linea);
			}
		} catch (IOException e) {
			// Si hay algun error.....
			//e.printStackTrace();
		}
		return listaLectura;
	}

	//lee en nombre del fichero desde la ventaba
	private  void  leoFicheroVentana() {
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		//In response to a button click:
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			//This is where a real application would open the file.
			nombreArchivo = file.getAbsolutePath();//leer la ruta completa del archivo
		} 
		else {
			//error al seleccionar fichero
		}
	}



	//
	public void addMovimientoRealizado(HojaExcel oHojaExcel){
		HojaExcel oHojaExcel2= clonarHoja(oHojaExcel.getHojaEntrada());
		listaHojasExcelMovRealizados.add(oHojaExcel2);	
	}

	private HojaExcel clonarHoja(String[][] hojaEntrada) {
		HojaExcel oHojaExcel2= new HojaExcel(hojaEntrada.length, hojaEntrada[0].length);
		for(int f=0; f<hojaEntrada.length; f++){
			for(int c=0; c<hojaEntrada[0].length; c++){
				oHojaExcel2.setHojaEntradaCelda(f, c, hojaEntrada[f][c]);
			}
		}
		return oHojaExcel2;
	}

	//main
	public static void main(String[] args){
		VentanaPrincipalHojaExcel oV= new VentanaPrincipalHojaExcel();
		oV.setVisible(true);	
	}



}
