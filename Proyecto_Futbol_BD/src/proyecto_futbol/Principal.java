// INTEGRANTES DEL GRUPO: Álvaro Benítez López, Mario Rufo Ariza, David Rodríguez Díaz
package proyecto_futbol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.sql.*;

public class Principal {

	// RUTA PARA IDE - ECLIPSE
	private static final String RUTA_FICHEROS = "src\\ACT_1_2\\ficheros\\";

	// RUTA PARA IDE - NETBEANS
	// private static final String RUTA_FICHEROS =
	// "src\\main\\java\\ACT_1_2\\ficheros\\";
	private static final int MAX_ENTRENAMIENTO = 2;
	private static final int JUGADORES_SEMANA = 6;
	public static Scanner sn = new Scanner(System.in);

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		// TODO code application logic here

		System.out.println("BIENVENIDO A MODO CARRERA");
		try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/modo_carrera","root","");
                    PreparedStatement st = con.prepareStatement("select * from equipo e ");
                    
                    ResultSet rs = st.executeQuery();
                    
                    while(rs.next()){
                        System.out.println(rs.getString("nombre"));
                    }
                    
			//Principal.menuInicial();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 *
	 * MENÚ INICIAL:
	 *
	 */

	private static void menuInicial() throws NumberFormatException, Exception {

		boolean salir = false;
		int opcion;

		while (!salir) {

			Principal.opcionesMenuInicial();

			try {

				System.out.print("Elige una opción: ");
				opcion = sn.nextInt();
				System.out.println();

				switch (opcion) {
				case 1:
					Principal.seleccionarEquipo();
					break;
				case 2:
					Principal.reglasDelJuego();
					break;
				case 3:
					salir = true;
					break;
				default:
					System.out.println("Solo números entre 1 y 3");
				}
			} catch (InputMismatchException e) {
				System.out.println("Debes insertar un número");
				sn.next();
			}
		}
	}

	private static void opcionesMenuInicial() {
		System.out.println("__________________________");
		System.out.println("");
		System.out.println("1. Seleccionar equipo");
		System.out.println("2. Ver reglas del juego");
		System.out.println("3. Salir de la aplicación");
		System.out.println("__________________________");
		System.out.println();
	}

	/*
	 *
	 * MENÚ MERCADO DE FICHAJES:
	 *
	 */

	private static void menuMercadoFichajes(Equipo miEquipo, LinkedList<Equipo> equipos)
			throws NumberFormatException, Exception {

		char respuesta_sn;
		boolean salir = false;
		int opcion, contadorSemanas = 4, numeroJugador = 0;
		LinkedList<Jugador> jugadoresMercadoLibre = Principal.leerJugadoresLibres();
		LinkedList<Jugador> jugadoresSemanales;
		jugadoresSemanales = Principal.devolverJugadoresLibres(jugadoresMercadoLibre);
		Jugador jugadorAFichar;

		while (!salir) {

			if (contadorSemanas == 0) {
				opcion = 6;

			} else {

				System.out.print("-------------------------------------------------------------------");

				System.out.println(
						"\n|| PREPARATE PARA EL TORNEO - QUEDAN " + contadorSemanas + " SEMANAS PARA EL COMIENZO ||\n");
				System.out.println("MERCADO DE FICHAJES (" + miEquipo.getNombreEquipo() + " - Presupuesto: "
						+ miEquipo.getPresupuesto() + " €)");

				System.out.print("-------------------------------------------------------------------");

				Principal.opcionesMenuMercadoFichajes();
				System.out.print("Elige una opción:");
				opcion = sn.nextInt();
				System.out.println();

			}
			try {

				switch (opcion) {
				case 1:
					Principal.comprarJugadoresOtrosEquipos(equipos, miEquipo);
					break;
				case 2:

					if (jugadoresSemanales.size() != 0) {

						System.out.println(mostrarJugadoresSemana(jugadoresSemanales));

						do {

							System.out.print("¿Qué jugador quieres fichar? (1-" + jugadoresSemanales.size() + "): ");
							numeroJugador = sn.nextInt();

						} while (numeroJugador <= 0 || numeroJugador > jugadoresSemanales.size());

						jugadorAFichar = jugadoresSemanales.get(numeroJugador - 1);

						respuesta_sn = validacion(
								"¿Estás seguro de fichar al jugador " + jugadorAFichar.getNombre() + "? (S/N): ");

						if (respuesta_sn == 'S') {

							ficharJugadorSemanal(jugadoresMercadoLibre, jugadoresSemanales, miEquipo, jugadorAFichar);

						}

					} else {

						System.out.println(
								"No puedes fichar más jugadores libres. Avanza a la siguiente semana, para fichar más jugadores.");

					}

					break;
				case 3:
					Principal.venderJugadores(miEquipo);
					break;
				case 4:
					jugadoresSemanales = Principal.devolverJugadoresLibres(jugadoresMercadoLibre);
					contadorSemanas--;
					break;
				case 5:
					System.out.println(miEquipo.getNombreEquipo() + "\n");
					System.out.println(miEquipo.listarPlantilla());
					break;
				case 6:
					Principal.menuTorneo(miEquipo, equipos);
					break;
				case 7:
					Principal.reglasDelJuego();
					break;
				case 8:
					System.out.println("Tirando del cable...");
					System.exit(0);

					break;
				default:
					System.out.println("Solo números entre 1 y 8");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
	}

	private static void opcionesMenuMercadoFichajes() {
		System.out.println();
		System.out.println("______________________________________");
		System.out.println("\n1. Fichar jugadores de otro equipo");
		System.out.println("2. Fichar jugadores libres");
		System.out.println("3. Vender jugadores");
		System.out.println("4. Avanzar semana");
		System.out.println("5. Mostrar información equipo");
		System.out.println("6. Comenzar torneo");
		System.out.println("7. Ver reglas del juego");
		System.out.println("8. Salir de la aplicacion");
		System.out.println("______________________________________");
		System.out.println();
	}

	/*
	 *
	 * MENÚ TORNEO:
	 *
	 */

	private static void menuTorneo(Equipo miEquipo, LinkedList<Equipo> equipos) throws Exception {
		boolean salir = false, hasPerdido = false;
		int opcion;
		int cont = 0;
		int ronda = 0;
		miEquipo.setEntrenamientosSemana(0);

		System.out.println("\n" + "COMIENZA EL TORNEO");
		equipos.add(miEquipo);

		while (!salir) {

			try {

				if (equipos.contains(miEquipo)) {
					Principal.opcionesMenuTorneo();
					System.out.print("Elige una opción: ");
					opcion = sn.nextInt();
				} else {
					opcion = 2;
				}

				switch (opcion) {
				case 1:

					miEquipo.entrenarEquipo();
					cont++;
					System.out.println("Entrenamiento realizado con éxito (" + cont + "/" + MAX_ENTRENAMIENTO + ")");
					break;
				case 2:

					equipos = Principal.jugarTorneo(equipos, ronda);
					miEquipo.setEntrenamientosSemana(0);
					ronda++;

					if ((!equipos.contains(miEquipo)) && hasPerdido == false) {
						System.out.println("TENEMOS MALAS NOTICIAS, tu equipo " + miEquipo.getNombreEquipo()
								+ " ha sido eliminado...");
						hasPerdido = true;

					}

					if (ronda == 3) {

						System.out.println("Gracias por jugar a nuestro minijuego.");
						System.exit(0);
					}

					cont = 0;

					break;

				case 3:

					System.out.println(miEquipo.listarPlantilla());

					break;

				case 4:
					Principal.reglasDelJuego();
					break;
				case 5:
					System.out.println("Tirando del cable...");
					System.exit(0);
					break;
				default:
					System.out.println("Solo números entre 1 y 4");
				}
			} catch (InputMismatchException e) {
				System.out.println("Debes insertar un número");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void opcionesMenuTorneo() {
		System.out.println("_______________________________");
		System.out.println();
		System.out.println("1. Avanzar a entrenamiento");
		System.out.println("2. Jugar partido");
		System.out.println("3. Mostrar información equipo");
		System.out.println("4. Ver reglas del juego");
		System.out.println("5. Salir de la aplicación");
		System.out.println("_______________________________");
		System.out.println();
	}

	/*
	 *
	 * SECCIONES MENÚ INICIAL:
	 *
	 */

	/*
	 * En el siguiente método llamamos al método cargarEquipos() y una vez tengamos
	 * los equipos listados y mostrados preguntamos qué equipo desea elegir para
	 * jugar. Al seleccionar el equipo y confirmar que desea elegir gracias al
	 * método validacion() se guardará la selección para el resto del torneo
	 * guardando el equipo seleccionado en la variable Equipo seleccionEquipo
	 */

	private static void seleccionarEquipo() {

		// Cargas equipos y guardar el equipo seleccionado en una variable equipo
		// seleccionado.

		try {

			LinkedList<Equipo> equipos = cargarEquipos();

			Iterator<Equipo> it;

			int opcionEquipo;

			int cont;

			char respuesta_sn;

			boolean equipoSeleccionado = false;

			Equipo seleccionEquipo;

			System.out.println();
			System.out.print("*******************");

			System.out.println("\n" + "* ELIGE TU EQUIPO *");
			System.out.println("*******************");

			do {

				do {

					cont = 1;
					it = equipos.iterator();

					System.out.println("-----------------------");

					while (it.hasNext()) {
						System.out.println("Equipo " + cont + ":" + it.next().getNombreEquipo());
						cont++;
					}

					System.out.println("-----------------------");

					System.out.print("Elige un equipo (1-"+equipos.size()+"): ");
					opcionEquipo = Integer.parseInt(sn.next());

				} while (opcionEquipo < 1 || opcionEquipo > equipos.size());

				seleccionEquipo = equipos.get(opcionEquipo - 1);

				System.out.println(seleccionEquipo.getNombreEquipo().toUpperCase());

				System.out.println(seleccionEquipo.listarPlantilla());
				respuesta_sn = validacion(
						"¿Estás seguro de elegir al " + seleccionEquipo.getNombreEquipo() + " como equipo? (S/N): ");
				System.out.println();

				if (respuesta_sn == 'S') {
					equipoSeleccionado = true;
					
				}

				equipos.remove(seleccionEquipo);

			} while (!equipoSeleccionado);
			Principal.menuMercadoFichajes(seleccionEquipo, equipos);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/*
	 * Leemos el fichero equipos.txt e introducimos cada equipo en un
	 * LinkedList<Equipo> llamado equipos
	 */

	private static LinkedList<Equipo> cargarEquipos() throws Exception {

		String linea;

		String datos[];

		LinkedList<Equipo> equipos = new LinkedList<Equipo>();
		LinkedList<Jugador> jugadoresEquipo = new LinkedList<Jugador>();
		String nombreEquipo;
		int annioFundacion;
		int presupuesto;
		Entrenador e;

		try (BufferedReader filtroLectura = new BufferedReader(new FileReader(RUTA_FICHEROS + "equipos.txt"))) {

			linea = filtroLectura.readLine();

			while (linea != null) {

				datos = linea.split(";");
				nombreEquipo = datos[0];
				annioFundacion = Integer.parseInt(datos[1]);
				presupuesto = Integer.parseInt(datos[datos.length - 1]);
				jugadoresEquipo = meterJugadoresEquipo(nombreEquipo);
				e = meterEntrenadorEquipo(nombreEquipo);
				equipos.add(new Equipo(nombreEquipo, annioFundacion, presupuesto, jugadoresEquipo, e));

				linea = filtroLectura.readLine();
			}

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
		return equipos;

	}

	/*
	 * Leemos el fichero de entrenadores de cada equipo introducido previamente y
	 * creamos cada entrenador en un objeto Entrenar e
	 */

	private static Entrenador meterEntrenadorEquipo(String nombreEquipo) {
		// TODO Auto-generated method stub

		Entrenador e = null;

		String linea;
		String datosLinea[];
		String nombreEntrenador;
		String nacionalidadEntrenador;
		int edadEntrenador, estadisticaEntrenamiento;

		try (BufferedReader filtroLectura = new BufferedReader(
				new FileReader(RUTA_FICHEROS + "entrenador_" + nombreEquipo + ".txt"))) {

			linea = filtroLectura.readLine();

			datosLinea = linea.split(";");

			nombreEntrenador = datosLinea[0];

			nacionalidadEntrenador = datosLinea[1];
			edadEntrenador = Integer.parseInt(datosLinea[2]);
			estadisticaEntrenamiento = Integer.parseInt(datosLinea[datosLinea.length - 1]);

			e = new Entrenador(nombreEntrenador, nacionalidadEntrenador, edadEntrenador, estadisticaEntrenamiento);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}

		return e;
	}

	/*
	 * Leemos el fichero de jugadores de cada equipo introducido previamente y
	 * creamos cada jugador en un objeto Jugador j, que posteriormente añadimos a
	 * una LinkedList<Jugador> de nombre jugadores
	 */

	private static LinkedList<Jugador> meterJugadoresEquipo(String nombreEquipo) throws Exception {

		LinkedList<Jugador> jugadores = new LinkedList<Jugador>();
		String linea;
		Jugador j = null;
		String nombreJugador;
		String nacionalidadJugador;
		int edadJugador;
		int valoracionJugador;
		int precioJugador;
		String datosLinea[];

		try (BufferedReader filtroLectura = new BufferedReader(
				new FileReader(RUTA_FICHEROS + "jugadores_" + nombreEquipo + ".txt"))) {

			linea = filtroLectura.readLine();

			while (linea != null) {

				datosLinea = linea.split(";");

				nombreJugador = datosLinea[0];
				nacionalidadJugador = datosLinea[2];
				edadJugador = Integer.parseInt(datosLinea[3]);
				valoracionJugador = Integer.parseInt(datosLinea[4]);
				precioJugador = Integer.parseInt(datosLinea[datosLinea.length - 1]);
				j = new Jugador(nombreJugador, nacionalidadJugador, edadJugador, valoracionJugador, precioJugador);
				jugadores.add(j);

				linea = filtroLectura.readLine();

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return jugadores;
	}

	/*
	 *
	 * SECCIONES MENÚ MERCADO DE FICHAJES:
	 *
	 */

	/*
	 * En este método podremos fichar jugadores de los demás equipos que conforman
	 * el torneo. Para eso primeros seleccionamos el equipo del que queremos ver el
	 * jugador a fichar y tras ello vemos y elegimos el jugador deseado previa
	 * confirmación mediante el método validacion() Si deseamos comprar un jugador y
	 * lo hemos elegido, llamamos al método transferirJugador() de la clase Equipo.
	 */

	private static void comprarJugadoresOtrosEquipos(LinkedList<Equipo> equipos, Equipo miEquipo) throws Exception {

		char car;
		int numeroJugador, cont = 1, opcionEquipo;
		Iterator<Equipo> it;
		Jugador jugadorTransferir = null;

		System.out.println();
		System.out.print("*******************");

		System.out.println("\n" + "* ELIGE EL EQUIPO *");
		System.out.println("*******************");
		do {

			it = equipos.iterator();

			System.out.println("-----------------------");

			while (it.hasNext()) {
				System.out.println("Equipo " + cont + ":" + it.next().getNombreEquipo());
				cont++;
			}

			System.out.println("-----------------------");
			System.out.println();
			
			System.out.print("Elige un equipo (1-"+equipos.size()+"): ");
			opcionEquipo = Integer.parseInt(sn.next());

		} while (opcionEquipo < 1 || opcionEquipo > equipos.size());

		opcionEquipo--;

		for (int i = 0; i < equipos.get(opcionEquipo).getJugadores().size(); i++) {
			System.out.println((i + 1) + "." + equipos.get(opcionEquipo).getJugadores().get(i).toString());
		}

		do {

			System.out.print("¿Desea comprar algún jugador? (S/N): ");
			car = sn.next().toUpperCase().charAt(0);

		} while (!(car == 'S' || car == 'N'));

		if (car == 'S') {

			do {
				System.out.print("Introduzca el numero del jugador a comprar (1-"+equipos.get(opcionEquipo).getJugadores().size()+"): ");
				numeroJugador = Integer.parseInt(sn.next());
				numeroJugador--;

				if (numeroJugador > (equipos.get(opcionEquipo).getJugadores().size() + 1) || numeroJugador < 0) {
					System.out.println("Numero fuera de parametros");
				} else {
					jugadorTransferir = equipos.get(opcionEquipo).getJugadores().get(numeroJugador);
					equipos.get(opcionEquipo).transeferirJugador(jugadorTransferir, miEquipo);

				}

			} while (numeroJugador > (equipos.get(opcionEquipo).getJugadores().size() + 1) || numeroJugador < 0);

			System.out.println("El jugador " + jugadorTransferir.getNombre() + " ha sido comprado con exito");

		}
	}

	/*
	 * Se leen los lineas del archivo jugadoresLibres.txt y se hace un split de
	 * dicha linea para crear un objeto jugador y añadirlo al LinkedList
	 * mercadoLibre
	 */

	private static LinkedList<Jugador> leerJugadoresLibres() {

		LinkedList<Jugador> mercadoLibre = new LinkedList<Jugador>();

		String linea, nombreArchivo = RUTA_FICHEROS + "jugadoresLibres.txt";
		String[] datos;
		Jugador nuevoJugador;

		try (FileReader flujoLectura = new FileReader(nombreArchivo);
				BufferedReader filtroLectura = new BufferedReader(flujoLectura);) {

			linea = filtroLectura.readLine();
			while (linea != null) {

				datos = linea.split(";");

				nuevoJugador = new Jugador(datos[0], datos[2], Integer.parseInt(datos[3]), Integer.parseInt(datos[4]),
						Integer.parseInt(datos[5]));

				mercadoLibre.add(nuevoJugador);

				linea = filtroLectura.readLine();

			}

		} catch (FileNotFoundException e) {
			System.out.println("No existe el fichero " + nombreArchivo);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return mercadoLibre;

	}

	// Mostrar jugadores para dicha semana en el mercado de jugadores libres

	private static String mostrarJugadoresSemana(LinkedList<Jugador> jugadoresSemanales) {
		// TODO Auto-generated method stub

		Iterator<Jugador> it = jugadoresSemanales.iterator();

		StringBuilder sb = new StringBuilder();

		int cont = 1;

		while (it.hasNext()) {

			sb.append(cont + ". " + it.next());
			cont++;
		}

		return sb.toString();
	}

	/*
	 * El metodo recibe por parametros tanto los jugadores semanales,como los del
	 * mercadoLibre y el jugador a fichar usa la funcion ficharJugador en miEquipo y
	 * lo elimina de el mercadoLibre y de los jugadoresSemanales
	 */

	private static void ficharJugadorSemanal(LinkedList<Jugador> jugadoresMercadoLibre,
			LinkedList<Jugador> jugadoresSemanales, Equipo miEquipo, Jugador jugadorAFichar) throws Exception {
		// TODO Auto-generated method stub

		miEquipo.ficharJugador(jugadorAFichar);
		jugadoresMercadoLibre.remove(jugadorAFichar);
		jugadoresSemanales.remove(jugadorAFichar);
		System.out.println();
		System.out.println("\nEl jugador " + jugadorAFichar.getNombre() + " ha sido comprado (Presupuesto actual: "
				+ miEquipo.getPresupuesto() + "€)");
		System.out.println();
	}

	// Obtenemos los jugadores que quedan en el mercado libre

	private static LinkedList<Jugador> devolverJugadoresLibres(LinkedList<Jugador> jugadoresMercadoLibre) {
		// TODO Auto-generated method stub

		Random rand = new Random();

		int posicionJugador = 0;
		LinkedList<Jugador> jugadoresSemana;

		HashSet<Jugador> jugadoresNoRepetidos = new HashSet<Jugador>();

		while (jugadoresNoRepetidos.size() != JUGADORES_SEMANA) {

			posicionJugador = rand.nextInt(jugadoresMercadoLibre.size());

			jugadoresNoRepetidos.add(jugadoresMercadoLibre.get(posicionJugador));

		}

		jugadoresSemana = new LinkedList<Jugador>(jugadoresNoRepetidos);

		return jugadoresSemana;
	}

	/*
	 * En el siguiente método tenemos la posibilidad de vender jugadores de nuestro
	 * equipo. Se listarán todos los jugadores del equipo y se preguntará mediante
	 * validacion() si se desea vender a algún jugador. En caso de que sea sí, se
	 * deberá introducir el número del jugador y se procederá a vender el jugador en
	 * el método venderJugador() de la clase Equipo.
	 */

	private static void venderJugadores(Equipo miEquipo) throws Exception {

		int numeroJugador;
		char car;
		Jugador jugadorVender;

		System.out.println("JUGADORES DE MI EQUIPO (" + miEquipo.getNombreEquipo() + ")");

		for (int i = 0; i < miEquipo.getJugadores().size(); i++) {

			System.out.println((i + 1) + "." + miEquipo.getJugadores().get(i));

		}

		do {

			System.out.print("¿Desea vender algún jugador? (S/N): ");
			car = sn.next().toUpperCase().charAt(0);

		} while (!(car == 'S' || car == 'N'));

		if (car == 'S') {

			do {

				System.out.print("Introduzca el numero del jugador a vender (1-"+miEquipo.getJugadores().size()+"): ");
				numeroJugador = Integer.parseInt(sn.next());

				if (numeroJugador > miEquipo.getJugadores().size() || numeroJugador < 0) {
					System.out.println("Numero fuera de parametros");
					System.out.println();
				} else {
					jugadorVender=miEquipo.getJugadores().get(numeroJugador - 1);
					miEquipo.venderJugador(jugadorVender);
					System.out.println(
							"El jugador " + jugadorVender.getNombre() + " ha sido vendido.");
					System.out.println();
				}

			} while (numeroJugador > (miEquipo.getJugadores().size() + 1) || numeroJugador < 0);

		}

	}

	/*
	 *
	 * SECCIONES MENÚ TORNEO:
	 *
	 */

	// Jugar torneo

	private static LinkedList<Equipo> jugarTorneo(LinkedList<Equipo> equipos, int ronda) {

		Random rand = new Random();
		LinkedList<Equipo> equiposRestantes = new LinkedList<Equipo>(equipos), sorteo = new LinkedList<Equipo>();
		int u = 0, numeroSorteo, numeroEquiposRestantes;
		boolean resultadoPartido;
		String[] tipoRonda = { "pasa a Semifinales", "pasa a la Final", "ha ganado el torneo." };
		String[] mensajeRonda = { "LOS CUARTOS DE FINAL", "LAS SEMIFINALES", "LA FINAL" };
		numeroEquiposRestantes = equiposRestantes.size();

		for (int i = 0; i < numeroEquiposRestantes; i++) {

			numeroSorteo = rand.nextInt(equiposRestantes.size());

			sorteo.add(equiposRestantes.get(numeroSorteo));
			equiposRestantes.remove(numeroSorteo);

		}

		System.out.println("\nRESULTADOS DE " + mensajeRonda[ronda] + ":\n");

		for (int i = 0; i < sorteo.size() / 2; i++) {

			resultadoPartido = sorteo.get(u).jugarPartido(sorteo.get(u + 1));

			if (resultadoPartido == true) {

				equiposRestantes.add(sorteo.get(u));
				System.out.println(
						sorteo.get(u).getNombreEquipo() + " ha derrotado a " + sorteo.get(u + 1).getNombreEquipo()
								+ ". " + sorteo.get(u).getNombreEquipo() + " " + tipoRonda[ronda] + "\n");

			} else {

				equiposRestantes.add(sorteo.get(u + 1));
				System.out.println(
						sorteo.get(u + 1).getNombreEquipo() + " ha derrotado a " + sorteo.get(u).getNombreEquipo()
								+ ". " + sorteo.get(u + 1).getNombreEquipo() + " " + tipoRonda[ronda] + "\n");

			}

			u = u + 2;

		}

		return equiposRestantes;
	}

	/*
	 * En la validación leemos el carácter de la respuesta de confirmación del
	 * usuario por consola mientras que no sea S o N. Si es una de esas dos opciones
	 * se procesa la respuesta.
	 */

	private static char validacion(String mensaje) {

		char respuesta;

		do {

			System.out.print(mensaje);
			respuesta = sn.next().toUpperCase().charAt(0);
		} while (!(respuesta == 'S' || respuesta == 'N'));

		return respuesta;

	}

	/*
	 * Buscamos el archivo reglasDeJuego.txt en nuestros ficheros y le pasamos el
	 * File creado mediante ese archivo al método imprimirReglasDeJuegoPorLineas()
	 */

	private static void reglasDelJuego() {

		File nombreFicheroReglasDelJuego = new File(RUTA_FICHEROS + "reglasDeJuego.txt");
		// File nombreFicheroReglasDelJuego = new
		// File("src\\main\\java\\ACT_1_2\\reglasDeJuego.txt");
		Principal.imprimirReglasDeJuegoPorLineas(nombreFicheroReglasDelJuego);
	}

	/*
	 * Leemos cada línea del File de reglasDeJuego.txt y lo pintamos para que el
	 * usuario pueda verlas y entender el juego.
	 */

	private static void imprimirReglasDeJuegoPorLineas(File nombreFicheroReglasDelJuego) {
		String linea;
		System.out.println("________________________________________________________________________________\n");
		try (FileReader flujoLectura = new FileReader(nombreFicheroReglasDelJuego);
				BufferedReader filtroLectura = new BufferedReader(flujoLectura);) {

			linea = filtroLectura.readLine();
			while (linea != null) {
				System.out.println(linea);
				linea = filtroLectura.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("No existe el fichero " + nombreFicheroReglasDelJuego);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("________________________________________________________________________________\n");
		System.out.println("");
	}

}