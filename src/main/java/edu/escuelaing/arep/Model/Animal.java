package edu.escuelaing.arep.Model;
/**
 * Clase Animal
 * 
 * @author Diego Puerto
 *
 */
public class Animal {
	
	private int Id;
	private String Animal;
	private String Nombre;
	
	/**
	 *  Retorna el Id del animal
	 * @return Id
	 */
	public int getId() {
		return Id;
	}
	
	/**
	 * Asigna un numero a la variable Id
	 * @param Id
	 */
	public void setId(int id) {
		Id = id;
	}
	
	/**
	 *  Retorna la clasificacion del animal
	 * @return Animal
	 */
	public String getAnimal() {
		return Animal;
	}
	
	/**
	 * Asigna una clasificacion a la variable Animal
	 * @param animal
	 */
	public void setAnimal(String animal) {
		Animal = animal;
	}
	
	/**
	 *  Retorna el nombre del animal
	 * @return Nombre
	 */
	public String getNombre() {
		return Nombre;
	}
	
	/**
	 * Asigna un nombre a la variable nombre
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
}