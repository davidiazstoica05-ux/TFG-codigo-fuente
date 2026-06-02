package com.tfg_david.dam.City_Courier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.model.Ruta;

public interface RutaRepository extends JpaRepository<Ruta, Long> {

	List<Ruta> findByNombreRutaOrCodigoRuta(String nombreRuta, Long codigoRuta);

	/*SQL puro
	 * 
	 * select codRuta, nombreRuta, count(repartidor) as totalRepartidores

		from ruta

		group by codRuta,nombreRuta

		order by totalRepartidores desc ; 
	 * 
	 * 
	 * */
	
	/*V1
	 * @Query("""
		       SELECT r.codigoRuta, r.nombreRuta, COUNT(repartidores) 
		       FROM Ruta r 
		       JOIN r.repartidores rep 
		       GROUP BY r.codigoRuta, r.nombreRuta 
		       ORDER BY COUNT(rep) DESC
		       """)
		List<Object[]> obtenerRutasMasFrecuentes();
		
	*/
	
	//V2
	@Query("SELECT r FROM Ruta r ORDER BY SIZE(r.repartidores) DESC")
	List<Ruta> rutasMasFrecuentes();

}
