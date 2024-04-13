package org.example;

public class NODO {

    String nombre;
    String artista;
    String genero;
    int duracion;
    NODO enlace;


    public  NODO(String nom,String ar,String gen, int dur){
        nombre = nom;
        artista = ar;
        genero = gen;
        duracion = dur;
        enlace = null;

    }
    public NODO(String nom,String ar,String gen, int dur, NODO n ){
        nombre = nom;
        artista = ar;
        genero = gen;
        duracion = dur;
        enlace = n;


    }








    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
