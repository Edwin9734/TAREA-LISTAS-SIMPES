package org.example;

public class Nodo {
    private String Cancion;
    private String Artista;
    private String Genero;
    private int duracion; // en segundos

    // Constructor
    public Nodo(String nombreCancion, String artista, String genero, int duracion) {
        this.Cancion = nombreCancion;
        this.Artista = artista;
        this.Genero = genero;
        this.duracion = duracion;
    }

    // Métodos getters
    public String getCancion() {
        return Cancion;
    }

    public String getArtista() {
        return Artista;
    }

    public String getGenero() {
        return Genero;
    }

    public int getDuracion() {
        return duracion;
    }

    // Métodos para obtener el nombre del artista y género en español
    public String getNombreArtistaEnEspanol() {
        // Implementar lógica de traducción aquí si es necesario
        return Artista;
    }

    public String getNombreGeneroEnEspanol() {
        // Implementar lógica de traducción aquí si es necesario
        return Genero;
    }
}
