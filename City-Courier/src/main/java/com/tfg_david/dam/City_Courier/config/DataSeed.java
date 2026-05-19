package com.tfg_david.dam.City_Courier.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.model.Ruta;
import com.tfg_david.dam.City_Courier.repository.AsignacionRepository;
import com.tfg_david.dam.City_Courier.repository.EnviosRepository;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;
import com.tfg_david.dam.City_Courier.repository.RutaRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeed {

    private final RepartidorRepository repoRepartidor;
    private final RutaRepository repoRuta;
    private final AsignacionRepository repoAsig;
    private final EnviosRepository repoEnvio;
    
    @PostConstruct
    public void run() {
        
        // 1. CREAMOS LAS ENTIDADES INDEPENDIENTES (Sin relaciones todavía)
        
        Ruta ruta = Ruta.builder()
                .codigoPostal("41640")
                .fechaFinal(LocalDateTime.of(2026, 2, 1, 16, 00))
                .fechaInicio(LocalDateTime.of(2026, 2, 1, 8, 00))
                .distancia(5.5)
                .puntosEntregas(List.of("PlazaJuanDeMesa","BlasInfante","SanCristobal"))
                .build();
        
        Ruta ruta2 = Ruta.builder()
                .codigoPostal("41010")
                .distancia(3.3)
                .fechaFinal(LocalDateTime.of(2026, 2, 1, 16, 00))
                .fechaInicio(LocalDateTime.of(2026, 2, 1, 8, 00))
                .puntosEntregas(List.of("AvdSantaCecilia","LopezGomara","SanJacinto"))
                .build();
        
        Repartidor r = Repartidor.builder()
                  .nombre("David")
                  .apellidos("Díaz Stoica")
                  .dni("31031909x")
                  .cargaMax(10.5)
                  .email("daviddiaz@gmail.com")
                  .fechaAlta(LocalDate.of(2026, 1, 1))
                  .genero("Male")
                  .zona("Osuna")
                  .telefono(697386581)
                  .estado(Disponibilidad.DISPONIBLE)
                  .vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
                  .build();
        
        Repartidor r2 = Repartidor.builder()
                  .nombre("Miguel Angél")
                  .apellidos("Díaz Gallardo")
                  .dni("90009x")
                  .cargaMax(10.5)
                  .email("migeldiaz80@gmail.com")
                  .fechaAlta(LocalDate.of(2026, 7, 1))
                  .genero("Male")
                  .zona("Osuna")
                  .telefono(697386581)
                  .estado(Disponibilidad.VACACIONES)
                  .vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
                  .build();
                  
        Envio e = Envio.builder()
                .destinatario("Manuel Díaz")
                .direccion("Osuna, Plaza Juan de mesa")
                .peso(1.7)
                .fechaEntregaEstimada(LocalDateTime.of(2026, 2, 1, 14, 00))
                .build();
                
        Asignacion a = Asignacion.builder()
                .coste(2.5)
                .estadoPedido(false)
                .fechaAsignacion(LocalDateTime.of(2026, 2, 1, 16, 00))
                .fechaEntrega(LocalDateTime.of(2026, 2, 1, 14, 00))
                .motivoIncidencia(" ")
                .tiempoEstimado(null)
                .build();

        
        a.setRepartidor(r);
        a.setRuta(ruta);
        a.setEnvio(e);
        
        ruta.setAsignacionesRuta(List.of(a));
        r.setAsignacionesRepartidor(List.of(a));
        e.setAsignacion(a);
        
        r.setRuta(ruta);
        r2.setRuta(ruta2);
        
        
        repoRuta.save(ruta);
        repoRuta.save(ruta2);
        repoRepartidor.save(r);
        repoRepartidor.save(r2);
        repoEnvio.save(e);
        
        repoAsig.save(a); 
    }
}