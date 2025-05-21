package es.ucm.fdi.iw.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ConfigPartida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int estrellasIni;
    private int vidaIni;
    private int danyoVictoria;
    private int estrellasRonda;
    private int precioRefrescar;
}
