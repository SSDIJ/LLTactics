package es.ucm.fdi.iw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;

@Entity
public class FaccionUsos {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

  
    private int faccion;

    private int vecesUsado;

         public User getJugador() {
        return this.user;
    }

    public void setJugador(User jugador) {
        this.user = jugador;
    }

    public int getFaccion() {
        return this.faccion;
    }

    public void setFaccion(int faccion) {
        this.faccion=faccion;
    }

    public int getUsos() {
        return vecesUsado;
    }

    public void setUsos(int usos) {
        this.vecesUsado = usos;
    }
}
