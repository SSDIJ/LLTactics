package es.ucm.fdi.iw.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ObjetoUsos {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jid", referencedColumnName = "id", nullable = false)
    private User user;

   @ManyToOne
    @JoinColumn(name = "oid", referencedColumnName = "idobjeto", nullable = false)
    private Objeto objeto;

    @Column(nullable = true)
    private int vecesUsado;

}
