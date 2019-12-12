package pGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class VentanaPrincipalHojaExcel extends JFrame implements ActionListener {
	
	//panel
	private JPanel miPanel;
	
	//declaramos un objeto JMenuBar para crear la barra que contenga los menus
	private JMenuBar barraMenu;
	
	//Declaramos los menus que estaran dentro de la barra de menus que acabamos de crear
	private JMenu menuArchivo=new JMenu("Archivo");
	private JMenu menuEditar=new JMenu("Editar");
	
	//Declaramos los menusItems que estaran dentro de los menus que acabamos de crear
	private JMenuItem menuArchivoNuevo= new JMenuItem("nueva ventana hoja excel");
	private JMenuItem menuArchivoCargar= new JMenuItem("cargar hoja excel");
	
	//hojas de calculo
	private ArrayList<String[][]> listaHojasExcel= new ArrayList<String[][]>();
	
	//ruta del archivo( nombre del archivo) para guardarlo
	private String nombreArchivo="";
	
	//constructor de la ventana
	public VentanaPrincipalHojaExcel () {
		this.setTitle("hoja de calculo 2019");
		this.setSize(900,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//hacemos que cuando e da a la cruz se cierre
		this.setLocationRelativeTo(null);//centramos la ventana
		inicializarMenus();
		//add(panelPrincipal);	
	}
	
	private void inicializarMenus() {
		barraMenu=new JMenuBar();
		setJMenuBar(barraMenu);
		
		//agregar los menus en la barra de menus
		barraMenu.add(menuArchivo);
		barraMenu.add(menuEditar);
		
		//agregamos submenus
		menuArchivo.add(menuArchivoNuevo);
		menuArchivo.addSeparator();//separacion de barra
		menuArchivo.add(menuArchivoCargar);
		
		//menus desactivados
		menuArchivoNuevo.setEnabled(false);
		
		//creamos eventos
		menuArchivoNuevo.addActionListener(this);
		menuArchivoCargar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==menuArchivoCargar){
			lectura();
			HojaExcel oHojaExcel=new HojaExcel(listaHojasExcel.get(0));

			oHojaExcel.resuelve();
		}
	}

	private ArrayList<String[][]> lectura() {
		ArrayList<String[][]> listaHojas= new ArrayList<String[][]>();
		
		//abre una ventana para elegir el archivo a leer
		//guardo el nombre del archivo
		leoFicheroVentana();
		System.out.println(nombreArchivo);
		ArrayList<String> lineas= new ArrayList<String>();
		//guardo en una lista todas las lineas que tiene el fichero
		//le paso a ruta del archivo
		if(nombreArchivo.equals("")){
			JOptionPane.showMessageDialog(null, "no se ha abiero ningun fichero");
		}else{
			
			//guardo en lineas todas las lineas del fichero
			lineas=leoFichero();
			System.out.println(lineas);
			String linea=lineas.get(1);//primera linea , dimensiones (nFilas, nColumnas)
			String [] lineaColumnasyFilas= linea.split(" ");
			int columnas =Integer.parseInt(lineaColumnasyFilas[0]);
			int filas=Integer.parseInt(lineaColumnasyFilas[1]);
			//creo la hoja de excel con el numero de filas y columnas
			String[][] tablaExcel=new String[filas][columnas];
			//recorremos las filas ya leidas
			for(int f=0; f<filas; f++){
				String lineaCol= lineas.get(2+f);
				String []lineaColVector= lineaCol.split(" ");
				for(int c=0; c<lineaColVector.length; c++){
					tablaExcel[f][c]=lineaColVector[c];
				}
			}
			listaHojasExcel.add(tablaExcel);
		}
		return listaHojas;
	}
	
	private ArrayList<String> leoFichero() {
		FileReader fr= null;
		try{
			fr= new FileReader(nombreArchivo);
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			//si hay algun error...
			//e.printStacktrace();
		}
		BufferedReader bf = new BufferedReader(fr);
		String linea;
		ArrayList<String> listaLectura= new ArrayList<String>();
		try{
			while ((linea =bf.readLine())!=null){ //mietras haya lineas qiue leer sigo 
				listaLectura.add(linea);
			}
		}catch (IOException e) {
			//si hay algun error..
			// TODO: handle exception
		}
		return listaLectura;
	}

	//lee en nombre del fichwero desde la ventana
	private void leoFicheroVentana() {
		//create a file chooser
		final JFileChooser fc=new JFileChooser();
		int returnVal= fc.showOpenDialog(null);
		
		if(returnVal== JFileChooser.APPROVE_OPTION){
			File file=fc.getSelectedFile();
			//this is where...
			nombreArchivo = file.getAbsolutePath();//leer ña ruta completa del archivo
		}else{
			//errror al selecionar archivo
		}
	}

	public static void main(String[] args){
		VentanaPrincipalHojaExcel oV= new VentanaPrincipalHojaExcel();
		oV.setVisible(true);	
	}
}

