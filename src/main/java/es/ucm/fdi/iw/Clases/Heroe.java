package es.ucm.fdi.iw.Clases;

public class Heroe {
    private String nombre;
    private String imagen;
    private int vida;
    private int armadura;
    private int daño;
    private int velocidad;
    private String descripcion;
    private int faccion;  // 0 = humanos, 1 = dragones, 2 = trolls, 3 = no muertos, 4 = criaturas legendarias

    public Heroe(String nombre, String imagen, int vida, int armadura, int daño, int velocidad, String descripcion, int faccion) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.vida = vida;
        this.armadura = armadura;
        this.daño = daño;
        this.velocidad = velocidad;
        this.descripcion = descripcion;
        this.faccion = faccion;
    }

    // Getters y Setters
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

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getArmadura() {
        return armadura;
    }

    public void setArmadura(int armadura) {
        this.armadura = armadura;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFaccion() {
        return faccion;
    }

}
