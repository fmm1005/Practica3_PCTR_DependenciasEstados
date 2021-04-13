package src.p03.c01;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase ActividadSalidaPuerta.
 *
 * @author Alvaro L�pez Pereda
 * @author Francisco Medel Molinero
 */
public class ActividadSalidaPuerta implements Runnable{

	/** Constante NUMENTRADAS. */
	private static final int NUMENTRADAS = 20;
	
	/** Puerta. */
	private String puerta;
	
	/** Parque. */
	private IParque parque;

	/**
	 * Constructor de la clase.
	 *
	 * @param puerta la puerta
	 * @param parque el parque
	 */
	public ActividadSalidaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		for (int i = 0; i < NUMENTRADAS; i ++) {
			try {
				parque.salirDelParque(puerta);
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Salida interrumpida");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
	}
}
