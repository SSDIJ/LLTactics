package es.ucm.fdi.iw;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
        String errorMsg = "Usuario o contraseña incorrectos";
        if (exception instanceof DisabledException) {
            errorMsg = "Tu cuenta está baneada. Contacta con soporte si crees que es un error.";
            request.getSession().setAttribute("loginError", errorMsg);
            response.sendRedirect("/banned");  
        }
        else {

            System.out.println("\n\n\n BANEADO \n\n\n");
            request.getSession().setAttribute("loginError", errorMsg);
            response.sendRedirect("/login?error");    
        }
        
    }
}
