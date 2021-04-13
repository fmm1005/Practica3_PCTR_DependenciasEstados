package src.p03.c01;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Parque.
 *
 * @author Alvaro López Pereda
 * @author Francisco Medel Molinero
 */
public class Parque implements IParque{

	/** contador de personas totales. */
	private int contadorPersonasTotales;
	
	/** contador personas puerta. */
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	/**Contadores para llevar el recuento de cuantos entran y salen por que puerta. */
	private Hashtable<String, Integer> contadorEntrada;
	private Hashtable<String, Integer> contadorSalida;
	/**Aforo máximo del parque. */
	private final int AFORO_MAXIMO = 50;
	
	/**
	 * Constructor del parque.
	 */
	public Parque() {	// TODO
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		contadorEntrada = new Hashtable<String, Integer>() ;
		contadorSalida = new Hashtable<String, Integer>();
	}


	/**
	 * Entrar al parque.
	 *
	 * @param puerta puerta
	 * 
	 */
	@Override
	public synchronized void entrarAlParque(String puerta) {		// TODO
		
		comprobarAntesDeEntrar();
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		if (contadorEntrada.get(puerta) == null){
			contadorEntrada.put(puerta, 0);
		}
				
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		contadorEntrada.put(puerta, contadorEntrada.get(puerta) + 1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		checkInvariante();
		notifyAll();
		
	}
	/**
	 * Salir del parque.
	 * 
	 * @param puerta puerta
	 * 
	 */
	@Override
	public synchronized void salirDelParque(String puerta) {
		
		comprobarAntesDeSalir();
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		if (contadorSalida.get(puerta) == null){
			contadorSalida.put(puerta, 0);
		}
				
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales--;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)-1);
		contadorSalida.put(puerta, contadorSalida.get(puerta) + 1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");
		
		checkInvariante();
		notifyAll();
		
	}
	
	
	/**
	 * Imprimir info.
	 *
	 * @param puerta the puerta
	 * @param movimiento the movimiento
	 */
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p) + ". Entradas totales recibidas: " + contadorEntrada.get(p) + ". Salidas totales:" + contadorSalida.get(p));
		}
		System.out.println(" ");
	}
	
	/**
	 * Sumar contadores puerta.
	 *
	 * @return the int
	 */
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	/**
	 * Check invariante.
	 */
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		assert contadorPersonasTotales < AFORO_MAXIMO : "Aforo máximo superado";
		assert contadorPersonasTotales > 0 : "No pueden existir personas negativas";
	}

	/**
	 * Comprobar antes de entrar.
	 * 
	 */
	protected void comprobarAntesDeEntrar() {
		while(sumarContadoresPuerta() >= AFORO_MAXIMO) {
			try {
				wait();
			}
			catch (InterruptedException ce) {
				Logger.getGlobal().log(Level.INFO, "Entrada interrumpida");
				Logger.getGlobal().log(Level.INFO, ce.toString());
				return;
			}
			
		}
	}

	/**
	 * Comprobar antes de salir.
	 * 
	 */
	protected void comprobarAntesDeSalir() {
		while(sumarContadoresPuerta() <= 0) {
			try {
				wait();
			}
			catch (InterruptedException ce) {
				Logger.getGlobal().log(Level.INFO, "Entrada interrumpida");
				Logger.getGlobal().log(Level.INFO, ce.toString());
				return;
			}
		}
	}


}
