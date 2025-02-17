package es.ucm.fdi.iw.Clases;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatLog {
    
    private List<Mensaje> mensajes;

    public ChatLog() {
        this.mensajes = new ArrayList<>();
    }

    public void agregarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
    }

    public void mostrarHistorial() {
        for (Mensaje mensaje : mensajes)
            System.out.println(mensaje);
    }

}
