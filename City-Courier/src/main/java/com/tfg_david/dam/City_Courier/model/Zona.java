package com.tfg_david.dam.City_Courier.model;

public enum Zona {
    ARAHAL("Arahal"),
    CARMONA("Carmona"),
    ECIJA("Écija"),
    FUENTES_DE_ANDALUCIA("Fuentes de Andalucía"),
    LA_LANTEJUELA("La Lantejuela"),
    MARCHENA("Marchena"),
    MORON_DE_LA_FRONTERA("Morón de la Frontera"),
    OSUNA("Osuna"),
    PARADAS("Paradas"),
    UTRERA("Utrera");

    private final String display;

    Zona(String display) { this.display = display; }

    public String getDisplay() { return display; }
}
