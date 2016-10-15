package br.com.uefs.papaleguasweb;
 
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath ("app")
public class MeuAplicativo extends ResourceConfig{

    public MeuAplicativo() {
        packages("br.com.uefs.papaleguasweb.controller");
    }
    
}