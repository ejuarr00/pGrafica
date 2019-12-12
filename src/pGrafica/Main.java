package pGrafica;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static int numeroHojas;
	public static void main(String[] args){
		ArrayList<String[][]> listaHojas=lectura();
		for(int i=0; i<listaHojas.size();i++){
			//imprimeHojaEntrada(listaHojas.get(i));
			HojaExcel oHojaExcel=new HojaExcel(listaHojas.get(i));
			//System.out.println("las letras ZZZ son el numero (-1 por la columna): "+oHojaExcel.halloColumna("ZZZ"));
			oHojaExcel.resuelve();
		}
	}

	//imprimir hojaExcel antes de resolver
	/*private static void imprimeHojaEntrada(String[][] strings) {
		for(int f=0;f<strings.length;f++){
			for(int c=0; c<strings[f].length;c++){
				System.out.print(strings[f][c]+" ");
			}
			System.out.println();
		}
	}*/

	//imprimir hojaExcel despues de resolver
	public static void imprimeHojaSalida(int[][] hojaSalida) {
		for(int f=0;f<hojaSalida.length;f++){
			for(int c=0; c<hojaSalida[f].length;c++){
				if(c==hojaSalida[f].length-1){// si estoy ultima columna
					System.out.print(hojaSalida[f][c]);
				}else{
					System.out.print(hojaSalida[f][c]+" ");
				}
			}
			System.out.println();
		}
	}

	//metodo para leer todo 
	private static ArrayList<String[][]>  lectura() {
		Scanner sc=new Scanner(System.in);
		//numero de hojas
		String numHojas=sc.nextLine();
		if(HojaExcel.esNumero(numHojas)==false){
			System.out.println("Entrada Inválida. Número de hojas debe ser un número.");
			System.exit(0);
		}
		numeroHojas=Integer.parseInt(numHojas);
		if(numeroHojas<1){
			System.out.println("Entrada Inválida. Número de hojas debe ser mayor que 0.");
			System.exit(0);
		}
		ArrayList<String[][]> listaHojas= new ArrayList<String[][]>();
		//leo cada una de las hojas
		for(int i=0; i<numeroHojas; i++){
			//primera linea cada hoja: leo columna y filas
			String linea=sc.nextLine();
			String []lineaColumnasyFilas= linea.split(" ");
			if(HojaExcel.esNumero(lineaColumnasyFilas[0])==false){
				System.out.println("Entrada Inválida. Número de columnas debe ser un número.");
				System.exit(0);
			}
			int columnas=Integer.parseInt(lineaColumnasyFilas[0]);
			if(columnas<1||columnas>=18278){
				System.out.println("Entrada Inválida. El Número de columnas debe estar entre 1 y 128278.");
				System.exit(0);
			}
			//System.out.println(columnas+" colummnasas");
			if(HojaExcel.esNumero(lineaColumnasyFilas[1])==false){
				System.out.println("Entrada Inválida. Número de filas debe ser un número.");
				System.exit(0);
			}
			int filas=Integer.parseInt(lineaColumnasyFilas[1]);
			if(filas<1||filas>=999){
				System.out.println("Entrada Inválida. Número de filas debe estar entre 1 y 999.");
				System.exit(0);
			}
			//System.out.println(filas+" filas");
			//creo la hoja de excel con el numero de filas y columnas
			String[][] tablaExcel=new String[filas][columnas];
			//recorremos las filas
			for(int f=0; f<filas; f++){
				String lineaCol=sc.nextLine();
				String []lineaColVector= lineaCol.split(" ");
				//leo las filas y coloco cada columna de esa fila en la tabla
				//System.out.println(lineaColVector.length+" numero columnas");
				for(int c=0; c<lineaColVector.length; c++){
					tablaExcel[f][c]=lineaColVector[c];
					if(lineaColVector.length!=columnas){
						//System.out.println(lineaColVector.length+" tamaño leido columnas");
						System.out.println("Entrada Inválida. Número de columnas distinto de las especificadas.");
						System.exit(0);
					}
					//System.out.println(lineaColVector[c]+"content");
				}
			}
			//aqui tenemos una hoja de string
			listaHojas.add(tablaExcel);
		}
		sc.close();
		//ya he leido todas las hojas
		return listaHojas;

	}

}
