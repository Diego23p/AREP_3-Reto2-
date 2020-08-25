package edu.escuelaing.arep.Connection.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.escuelaing.arep.Connection.DbConexion;
import edu.escuelaing.arep.Model.Animal;

/**
 * Implementacion de DbConexion, la cual hace la conexion con la base de datos 
 * 
 * @author Diego Puerto
 *
 */
public class DbConexionImpl implements DbConexion{

	
	private  String user = "mmhnuqtekqreau";
    private  String passw = "507e02d7be4f200071fe4ff923e9f5769be1a5b9514f9f26229c8e11e738ac81";
    private  String url = "jdbc:postgresql://ec2-34-195-115-225.compute-1.amazonaws.com:5432/d6n045gsciacmh?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private  Connection connection;
    
    public DbConexionImpl() throws SQLException {
    	conectar();
    }
    
	/**
	 * Metodo que conecta a la base de datos con el servicio
	 */
	public void conectar() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
	        connection = DriverManager.getConnection(url, user, passw);
	     } catch (SQLException ex) {
	        throw new SQLException(ex);
	     } catch (ClassNotFoundException ex) {
	        throw new ClassCastException(ex.getMessage());
	     }
		
	}
	
	/**
	 * Devuelve una lista de todos los animales que se encuentran en la base de datos
	 * 
	 * @return Lista de animnales
	 */
	public ArrayList<Animal> getEstudiantes()
	   {
			ArrayList<Animal> listaAnimales=new ArrayList<Animal>();
	      try
	      {
	
	         Statement st = connection.createStatement();
	         ResultSet rs = st.executeQuery("select * from Animal" );
	         while (rs.next())
	         {
	        	 Animal animal = new Animal();
	        	 animal.setId(rs.getInt("Id"));
	        	 animal.setAnimal(rs.getString("Animal"));
	        	 animal.setNombre(rs.getString("Nombre"));
	        	 listaAnimales.add(animal);
	         }
	         rs.close();
	         st.close();
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	      }
	      return listaAnimales;
	   }

}