package proyecto_futbol;

import java.util.LinkedList;
import java.util.Random;

public class Equipo implements IEquipo {

    private static final int MINIMO_JUGADORES = 15;
    private static final int NUMERO_JUGADORES = 20;

    private LinkedList<Jugador> jugadores;
    private String nombreEquipo;
    private int presupuesto;
    private Entrenador entrenador;
    private int annioFundacion;
    private int idEquipo;

    public Equipo(int idEquipo, String nombreEquipo, int annioFundacion, int presupuesto, LinkedList<Jugador> jugadores,
                    Entrenador entrenador) {

        this.jugadores = jugadores;
        this.annioFundacion = annioFundacion;
        this.nombreEquipo = nombreEquipo;
        this.presupuesto = presupuesto;
        this.entrenador = entrenador;
    }

    public int getIdEquipo() {          
        return idEquipo;   
    }
        
    public void setIdEquipo(int idEquipo) {         
        this.idEquipo=idEquipo;       
    }  

    public void setPresupuesto(int presupuesto) {
	this.presupuesto = presupuesto;
    }

    public LinkedList<Jugador> getJugadores() {
	return jugadores;
    }

    public String getNombreEquipo() {
	return nombreEquipo;
    }

    public int getPresupuesto() {
	return presupuesto;
    }

    public Entrenador getEntrenador() {
	return entrenador;
    }
    
    public int getAnnioFundacion() {
        return annioFundacion;
    }

    public void setAnnioFundacion(int annioFundacion) {
        this.annioFundacion = annioFundacion;
    }
    
    @Override
    public void ficharJugador(Jugador nuevoJugador) throws Exception {
	if ((this.presupuesto - nuevoJugador.getPrecio()) <= 0) {
            throw new Exception("El presupuesto del " + getNombreEquipo() + " es insuficiente (Presupuesto actual:" + getPresupuesto() + "€)");
	}
        
	if (this.jugadores.size() >= NUMERO_JUGADORES) {
            throw new Exception("El equipo esta al completo");
        }
        
	if (this.jugadores.contains(nuevoJugador)) {
            throw new Exception("El jugador ya esta en el equipo");
	}

	setPresupuesto(this.presupuesto - nuevoJugador.getPrecio());

	jugadores.add(nuevoJugador);
    }

    @Override
    public void venderJugador(Jugador jugadorVender) throws Exception {

	if ((this.jugadores.size()) <= MINIMO_JUGADORES) {
            throw new Exception("El numero de jugadores de un equipo no puede ser inferior a " + MINIMO_JUGADORES+"\n");
	}
        
	if (!this.jugadores.contains(jugadorVender)) {
            throw new Exception("El jugador no esta en el equipo\n");
	}

	setPresupuesto(this.presupuesto + jugadorVender.getPrecio());

	jugadores.remove(jugadorVender);
    }

    @Override
    public void entrenarEquipo() {
        try {
            boolean entrenamiento;
            int entrenamientosExitosos = 0;

            for (int i = 0; i < this.jugadores.size(); i++) {

                entrenamiento = this.jugadores.get(i).entrenarJugador(entrenador.getTasaExitoEntrenamiento());

                if (entrenamiento == true) {
                        entrenamientosExitosos++;
                }
            }

            if (entrenamientosExitosos >= (this.jugadores.size() / 2)) {
                    this.entrenador.setTasaExitoEntrenamiento(this.entrenador.getTasaExitoEntrenamiento() + 1);
            }
        } catch (Exception e) {
            System.out.println("No se ha podido realizar el entrenamiento.");
        }
    }

    @Override
    public int getProbabilidadesGanar() {

	int valoracionesTotales = 0, valoracionFinal;

	for (int i = 0; i < this.jugadores.size(); i++) {

            valoracionesTotales = +this.jugadores.get(i).getValoracionGeneral();

	}

	valoracionFinal = (valoracionesTotales / this.jugadores.size() / 100);

	return valoracionFinal;

    }

    @Override
    public void transeferirJugador(Jugador jugadorTransferir, Equipo equipoTransferir) throws Exception {
        
	if (!this.jugadores.contains(jugadorTransferir)) {
            throw new Exception("No existe tal jugador");
	}

	if (this.jugadores.size() <= MINIMO_JUGADORES) {
            throw new Exception("No se puede transeferir al jugador.\n El equipo "+equipoTransferir.getNombreEquipo()+" se quedaría bajo mínimos en jugadores (MIN:"+MINIMO_JUGADORES+")");
	}

	equipoTransferir.ficharJugador(jugadorTransferir);

	setPresupuesto(jugadorTransferir.getPrecio());

	this.jugadores.remove(jugadorTransferir);
        
        
    }

    public String listarPlantilla() throws Exception {
	StringBuilder listaJugadores = new StringBuilder();

	if (this.jugadores.size() == 0) {
            throw new Exception("No hay jugadores");
	}

	if (this.entrenador == null) {
            throw new Exception("No hay entrenador");
	}

	listaJugadores.append("\nPresupuesto: " + getPresupuesto() + "€ \n\n");
	listaJugadores.append(this.entrenador + "\n\n");

	for (int i = 0; i < jugadores.size(); i++) {
            listaJugadores.append(this.jugadores.get(i).toString() + "\n");
	}
        
	return listaJugadores.toString();
    }

    public boolean jugarPartido(Equipo equipoContrincante) {

	Random rand = new Random();
	int numeroAleatorio;
	boolean pasaRonda = false;

	numeroAleatorio = rand.nextInt((this.getProbabilidadesGanar() + equipoContrincante.getProbabilidadesGanar()) + 1);

	if (numeroAleatorio > this.getProbabilidadesGanar()) {
            pasaRonda = true;
	}

	return pasaRonda;
    }

    @Override
    public String toString() {
	return "Equipo [jugadores=" + jugadores + ", nombreEquipo=" + nombreEquipo + ", presupuesto=" + presupuesto
            + "€, entrenador=" + entrenador + ", annioFundacion=" + annioFundacion + "";
    }
}
