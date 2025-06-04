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
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reportado_id", referencedColumnName = "id", nullable = false)
    private User reportado;
    
    @Column(nullable = true)
    private String razonReporte;

    @ManyToOne
    @JoinColumn(name = "reportador_id", referencedColumnName = "id", nullable = true)
    private User reportador;

    public Reporte(User reportado, User reportador, String razonReporte) {
        this.reportado = reportado;
        this.reportador = reportador;
        this.razonReporte = razonReporte;
    }

    public String getNombreReportado() {
        return reportado != null ? reportado.getUsername() : null;
    }

    public String getNombreReportador() {
        return reportador != null ? reportador.getUsername() : null;
    }

}
