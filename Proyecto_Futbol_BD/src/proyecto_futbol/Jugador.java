package proyecto_futbol;

import java.util.Random;

public class Jugador implements IJugador {

    private static final int AUMENTO_ENTRENAMIENTO_BASE = 2;
    private static final int AUMENTO_ESTADISTICAS = 2;
    private String nombre, nacionalidad, posicion;
    private int edad, valoracionGeneral, precio, idJugador;
	
    public Jugador(int idJugador, String nombre, String posicion, String nacionalidad, int edad, int valoracionGeneral, int precio)
			throws Exception {
        setIdJugador(idJugador);
        setNombre(nombre);
        setPosicion(posicion);
	setNacionalidad(nacionalidad);
	setEdad(edad);
	setValoracionGeneral(valoracionGeneral);
	setPrecio(precio);
    }

    public String getPosicion() {   
        return posicion;
    }
        
    public void setPosicion(String posicion) {         
        this.posicion=posicion;    
    }
        
    public int getIdJugador() {     
        return idJugador;       
    }
        
    public void setIdJugador(int idJugador) {      
        this.idJugador=idJugador;    
    }       

    public String getNombre() {
	return nombre;
    }

    private void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getNacionalidad() {
	return nacionalidad;
    }

    private void setNacionalidad(String nacionalidad) {
	this.nacionalidad = nacionalidad;
    }

    public int getEdad() {
	return edad;
    }

    private void setEdad(int edad) throws Exception {
	if (edad <= 1 || edad > 100) {
            throw new Exception("Edad fuera de parámetros");
	}

	this.edad = edad;
    }

    public int getValoracionGeneral() {
	return valoracionGeneral;
    }

    public void setValoracionGeneral(int valoracionGeneral) throws Exception {
	if (valoracionGeneral <= 0) {
            throw new Exception("Valoracion fuera de parámetros");
	}

	if (valoracionGeneral > 99) {
            valoracionGeneral = 99;
	}

	this.valoracionGeneral = valoracionGeneral;
    }
	
    private void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getPrecio() {
        return precio;
    }
        
    public boolean entrenarJugador(int aumentoEntrenador) throws Exception {
	boolean resultadoEntrenamiento = false;
	Random rand = new Random();
	int numeroAleatorio;

	numeroAleatorio = rand.nextInt(10 + 1);

	if ((aumentoEntrenador + AUMENTO_ENTRENAMIENTO_BASE) >= numeroAleatorio) {
            resultadoEntrenamiento = true;
            setValoracionGeneral(this.valoracionGeneral + AUMENTO_ESTADISTICAS);
	} else {
            setValoracionGeneral(this.valoracionGeneral - AUMENTO_ESTADISTICAS);
	}

        return resultadoEntrenamiento;
    }

    @Override
    public String toString() {
	return "Nombre: " + nombre + " || Nacionalidad: " + nacionalidad + " || Edad: " + edad
		+ " || Valoracion General: " + valoracionGeneral + " || Precio de mercado: " + precio + "\n";
    }
}