package pGrafica;

import java.util.regex.Pattern;

public class HojaExcel {

	private String[][] hojaEntrada;
	private int[][] hojaSalida;
	boolean booleanaResuelta[][];
	private int noHeAcabado=-6666;

	public HojaExcel(String[][]hoja){
		this.hojaEntrada=hoja;
		this.hojaSalida=new int[hojaEntrada.length][hojaEntrada[0].length];
		booleanaResuelta=new boolean[hojaEntrada.length][hojaEntrada[0].length];
	}

	//metodo para saber si es numero o no
	public static boolean esNumero(String cadena){
		try{
			int num= Integer.parseInt(cadena);
			return true;
		}
		catch(Exception e){
			return false;
		}
	} 

	//metodo para saber si es formula
	private boolean esFormula(String cadena) {
		if(cadena.charAt(0)== '='){
			return true;
		}
		return false;
	}

	//metodo para saber si se resuelven todas las formulas
	private boolean TodasFormulasResueltas() {
		for(int f=0; f<booleanaResuelta.length; f++){
			for(int c=0; c<booleanaResuelta[f].length; c++){
				if(booleanaResuelta[f][c]!=true){
					return false;
				}
			}
		}
		return true;
	}

	//metodo resuelve la hoja excel
	public void resuelve() {
		while(TodasFormulasResueltas()==false){
			//recorro la hija de entrada
			for(int f=0; f<hojaEntrada.length; f++){
				for(int c=0; c<hojaEntrada[f].length; c++){
					//si no es formula pongo la matriz booleana a true
					if(!esFormula(hojaEntrada[f][c])){
						booleanaResuelta[f][c]=true;
						//si es numero lo copio en la hoja de salida directamente
						if(esNumero(hojaEntrada[f][c])){
							hojaSalida[f][c]=Integer.parseInt(hojaEntrada[f][c]);
						}
						//si es formula la resuelvo
					}else{
						int resultado= resuelvoFormula(hojaEntrada[f][c]);
						if(resultado!=noHeAcabado){
							hojaSalida[f][c]=resultado;
							booleanaResuelta[f][c]=true;
						}
						//si no es formula ni numero error y fin
					}if(!esFormula(hojaEntrada[f][c]) && esNumero(hojaEntrada[f][c])==false){
						System.out.println("Entrada Inválida. Esto no es ni número ni fórmula.");
						System.exit(0);
					}
				}
			}
		}
		//imprimo la hoja resuelta 
		Main.imprimeHojaSalida(hojaSalida);
	}

	//metodo que resuelve la suma de la formula
	public int resuelvoFormula(String formula) {
		//borro el = del principio
		String formulasinespacios=formula.substring(1,formula.length()/*-1*/);
		//separo la formula por el signo +
		String []lineaFormula= formulasinespacios.split(Pattern.quote("+"));
		int suma=0;
		//ya tengo las formulas separadas en el string[]
		for(int i=0; i< lineaFormula.length; i++){
			//buscoPrimerNumero me dice donde empiezan los numeros(y por consiguinte las filas)
			int empiezaFila=buscoPrimerNumero(lineaFormula[i]);
			String letrasColumna=lineaFormula[i].substring(0,empiezaFila);
			//numColumna: desde el principio del string al empiezaFila (esa letra o letras) equivaldran a los numeros de las columnas
			int numColumna=halloColumna(letrasColumna);
			if(numColumna<0|| numColumna>=18277){
				System.out.println("Entrada Inválida. Las formulas son incorrectas, menor que A=(1) o mayor que ZZZ=(18278).");
				System.exit(0);

			}
			//desde el empiezafila del string al final seria el numero de la fila
			String numeroFila=lineaFormula[i].substring(empiezaFila, lineaFormula[i].length());
			//si meto una letra con otra letra es error
			if(!esNumero(numeroFila)){
				System.out.println("Entrada Inválida. Las formulas son incorrectas.");
				System.exit(0);
			}
			int numFila=Integer.parseInt(numeroFila)-1;
			if(numFila<0|| numFila>=998){
				System.out.println("Entrada Inválida. Las formulas son incorrectas, numero mayor de 999 o menor de 1.");
				System.exit(0);
			}
			//si la celda a la que voy es una formula aun no acabo y vuelvo a resolverla
			if(booleanaResuelta[numFila][numColumna]==false){
				return noHeAcabado;
			}
			//voy acumulando el resultado
			suma=suma+hojaSalida[numFila][numColumna];
		}
		//System.out.println(suma +"suma");
		return suma;	
	}

	//metodo que transforma las letras en numero de columna
	public int halloColumna(String cadena){
		int numColumna=0;
		for(int i=0; i<cadena.length(); i++){
			int posicion=halloNumeroLetra(cadena.charAt(i));
			numColumna=numColumna*26;
			numColumna= numColumna+posicion;
		}

		return numColumna-1;

	}
	//metodo que transforma las letras en el numero equivalente
	public int halloNumeroLetra(char letra){
		int numeroLetra = 0;
		switch(letra){
		case 'A':
			numeroLetra=1;
			break;
		case 'B':
			numeroLetra=2;
			break;
		case 'C':
			numeroLetra=3;
			break;
		case 'D':
			numeroLetra=4;
			break;
		case 'E':
			numeroLetra=5;
			break;
		case 'F':
			numeroLetra=6;
			break;
		case 'G':
			numeroLetra=7;
			break;
		case 'H':
			numeroLetra=8;
			break;
		case 'I':
			numeroLetra=9;
			break;
		case 'J':
			numeroLetra=10;
			break;
		case 'K':
			numeroLetra=11;
			break;
		case 'L':
			numeroLetra=12;
			break;
		case 'M':
			numeroLetra=13;
			break;
		case 'N':
			numeroLetra=14;
			break;
		case 'O':
			numeroLetra=15;
			break;
		case 'P':
			numeroLetra=16;
			break;
		case 'Q':
			numeroLetra=17;
			break;
		case 'R':
			numeroLetra=18;
			break;
		case 'S':
			numeroLetra=19;
			break;
		case 'T':
			numeroLetra=20;
			break;
		case 'U':
			numeroLetra=21;
			break;
		case 'V':
			numeroLetra=22;
			break;
		case 'W':
			numeroLetra=23;
			break;
		case 'X':
			numeroLetra=24;
			break;
		case 'Y':
			numeroLetra=25;
			break;
		case 'Z':
			numeroLetra=26;
			break;
		default:
			System.out.println("Entrada Inválida. Las letras de las formulas son incorrectas.");
			System.exit(0);
		}
		return numeroLetra;		
	}

	//metodo que me busca la posicion del primer numero y cuando estoy en el, me devuelve la posicion del primer numero para saber donde empiezan las filas de la hoja excel
	public int buscoPrimerNumero(String cadena) {
		String[] cadenaString=cadena.split("");
		for(int i=0; i<cadena.length(); i++){
			if(esNumero(cadenaString[i])){
				//System.out.println(i+"columna");
				return i;	
			}
		}			
		return -1;//nunca debo llegar aqui
	}
}

