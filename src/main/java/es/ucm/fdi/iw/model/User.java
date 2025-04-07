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

import es.ucm.fdi.iw.model.Heroe;


/**
 * An authorized user of the system.
 */
@Entity
@Data
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name="User.byUsername",
                query="SELECT u FROM User u "
                        + "WHERE u.username = :username AND u.enabled = TRUE"),
        @NamedQuery(name="User.hasUsername",
                query="SELECT COUNT(u) "
                        + "FROM User u "
                        + "WHERE u.username = :username")
})
@Table(name="IWUser")
public class User implements Transferable<User.Transfer> {

    public enum Role {
        USER,			// normal users 
        ADMIN,          // admin users
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
	private long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String firstName;
    @Column(nullable = true)
    private String lastName;
  @Setter
@Column(nullable = true)
private String fotoPerfil;


    private boolean enabled;
    private String roles; // split by ',' to separate roles
    
    
    @Column(nullable = true)
    private int indiceRanking;
    @Column(nullable = true)
    private int puntuacion;
    @Column(nullable = true)
    private int partidasGanadas;
    @Column(nullable = true)
    private int partidasPerdidas;
    @Column(nullable = true)
    private int faccionFavorita;  // 0 = humanos, 1 = dragones, 2 = trolls, 3 = no muertos, 4 = criaturas 
    @Column(nullable = true)
    private boolean baneado;
    @Column(nullable = true)
   private String razonBaneo;
   @Column(nullable = true)
   private LocalDateTime fechaBaneo;
   //Sin el oneToMany peta (relacion entre 2 clases distintas.)
   @OneToMany
   @Column(nullable = true)
   private List<Heroe> masJugados = new ArrayList<>();



	@OneToMany
	@JoinColumn(name = "sender_id")
	private List<Message> sent = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "recipient_id")	
	private List<Message> received = new ArrayList<>();
    @ManyToMany(mappedBy = "members")
    private List<Topic> topics = new ArrayList<>();		

    /**
     * Checks whether this user has a given role.
     * @param role to check
     * @return true iff this user has that role.
     */
    public boolean hasRole(Role role) {
        String roleName = role.name();
        return Arrays.asList(roles.split(",")).contains(roleName);
    }

    @Getter
    @AllArgsConstructor
    public static class Transfer {
		private long id;
        private String username;
		private int totalReceived;
		private int totalSent;
        private String topics;
    }

    // Convierte el usuario en un objeto Transfer con info básica
	@Override
    public Transfer toTransfer() {
        StringBuilder gs = new StringBuilder();
        for (Topic t : topics) {
            gs.append(t.getName());
            gs.append(", ");
        }
		return new Transfer(id,	username, received.size(), sent.size(), gs.toString());
	}
	
	@Override
	public String toString() {
		return toTransfer().toString();
	}
}

