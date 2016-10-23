package br.com.uefs.papaleguasweb;
 
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath ("app")//caminho inicial
public class MeuAplicativo extends ResourceConfig{
    //ResourceConfig classe do Jersey usada para controladores
    //Construtor q inicia todos os controladores
    public MeuAplicativo() {
        packages("br.com.uefs.papaleguasweb.controller");
    }
    
}