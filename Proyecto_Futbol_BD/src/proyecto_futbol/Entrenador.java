package proyecto_futbol;

public class Entrenador implements IEntrenador {

    private String nombre, nacionalidad;
    private int edad, tasaExitoEntrenamiento;
    private int idEntrenador;

    public Entrenador(int idEntrenador, String nombreEntrenador, String nacionalidadEntrenador, int edadEntrenador,
			int estadisticaEntrenamiento) {
        setIdEntrenador(idEntrenador);
	setNombre(nombreEntrenador);
	setNacionalidad(nacionalidadEntrenador);
	setEdad(edadEntrenador);
	setTasaExitoEntrenamiento(estadisticaEntrenamiento);
    }

    public int getIdEntrenador() {        
        return idEntrenador;    
    }
        
    public void setIdEntrenador(int idEntrenador) {    
        this.idEntrenador=idEntrenador;   
    }        
        
    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public int getEdad() {
	return edad;
    }

    public void setEdad(int edad) {
	this.edad = edad;
    }

    public String getNacionalidad() {
	return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
	this.nacionalidad = nacionalidad;
    }

    public int getTasaExitoEntrenamiento() {
	return tasaExitoEntrenamiento;
    }

    public void setTasaExitoEntrenamiento(int tasaExitoEntrenamiento) {
	if (this.tasaExitoEntrenamiento > 7) {
            tasaExitoEntrenamiento = 7;
	}

	this.tasaExitoEntrenamiento = tasaExitoEntrenamiento;
    }

    @Override
    public String toString() {
	return "Entrenador Nombre:" + getNombre() + " || Nacionalidad:" + getNacionalidad() + " || Edad:" + getEdad()
		+ " || TEE:" + getTasaExitoEntrenamiento() + "";
    }	
}
