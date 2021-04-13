package src.p03.c01;
/**
 * Clase Sistema Lanzador.
 *
 * @author Alvaro López Pereda
 * @author Francisco Medel Molinero
 */
public class SistemaLanzador {
	
	/**
	 * Método principal.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		IParque parque = new Parque(); // TODO
		char letra_puerta = 'A';
		
		System.out.println("Â¡Parque abierto!");
		
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			
			String puerta = ""+((char) (letra_puerta++));
			
			// CreaciÃ³n de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread (entradas).start();
			
			//Creación de hilos de salida
			ActividadSalidaPuerta salidas = new ActividadSalidaPuerta(puerta, parque);
			new Thread (salidas).start();
			
			
		}
	}	
}