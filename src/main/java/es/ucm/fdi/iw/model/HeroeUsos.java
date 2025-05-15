package es.ucm.fdi.iw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroeUsos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")  // Verifica si la secuencia "gen" existe en la base de datos
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jid", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hid", referencedColumnName = "idheroe", nullable = false)
    private Heroe heroe;

    @Column(nullable = true)
    private int vecesUsado;

     public User getJugador() {
        return this.user;
    }

    public void setJugador(User jugador) {
        this.user = jugador;
    }

    public Heroe getHeroe() {
        return heroe;
    }

    public void setHeroe(Heroe heroe) {
        this.heroe = heroe;
    }

    public int getUsos() {
        return vecesUsado;
    }

    public void setUsos(int usos) {
        this.vecesUsado = usos;
    }
}