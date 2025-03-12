package proyecto_futbol;

public interface IEquipo {
    public void ficharJugador(Jugador nuevoJugador) throws Exception;

    public void venderJugador(Jugador jugadorVender) throws Exception;

    public String listarPlantilla() throws Exception;

    public void entrenarEquipo() throws Exception;

    public int getProbabilidadesGanar();

    public void transeferirJugador(Jugador jugadorTransferir, Equipo equipoTransferir) throws Exception;
}
