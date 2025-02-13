package es.ucm.fdi.iw.Clases;

public class Jugador {
    private String nombre;
    private String imagen;
    private int indiceRanking;
    private int puntuacion;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int faccionFavorita;

    public Jugador(String nombre, String imagen, int indiceRanking, int puntuacion, int partidasGanadas, int partidasPerdidas, int faccionFavorita) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.indiceRanking = indiceRanking;
        this.puntuacion = puntuacion;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.faccionFavorita = faccionFavorita;
    }

    // Getters y Setters corregidos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getIndiceRanking() {
        return indiceRanking;
    }

    public void setIndiceRanking(int indiceRanking) {
        this.indiceRanking = indiceRanking;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    public int getFaccionFavorita() {
        return faccionFavorita;
    }

    public void setFaccionFavorita(int faccionFavorita) {
        this.faccionFavorita = faccionFavorita;
    }
}
