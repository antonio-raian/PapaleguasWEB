package br.com.uefs.papaleguasweb.controller;

import br.com.uefs.papaleguasweb.exception.EncontraVerticeException;
import br.com.uefs.papaleguasweb.model.Corrida;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("corrida")
public class ControllerCorrida {
    Controlador ctrl;
    List<Corrida> corridas;
    public ControllerCorrida() throws EncontraVerticeException, IOException {
        //ctrl = new Controlador();
        Corrida c1 = new Corrida("Casa", "Rua", "10m", "5s", 10);
        Corrida c2 = new Corrida("Hotel", "Rua", "10m", "5s", 15);
        Corrida c3 = new Corrida("Hotel", "Casa", "10m", "5s", 20);
        corridas = new ArrayList<>();
        
        corridas.add(c1);
        corridas.add(c2);
        corridas.add(c3);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<Corrida> listCorridas(){
        return corridas;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{corrida}/")
    public Corrida getCorrida(@PathParam("corrida")String corrida){
        for(Corrida b:corridas)
            if(b.getOrigem().equals(corrida))
                return b;
        
        return null;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response criarCorrida(Corrida corrida){
        System.out.println("Adicionando Corrida "+corrida);
        corridas.add(corrida);
        return Response.status(Response.Status.OK).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response alteraCorrida(Corrida corrida){
        System.out.println("Alterando Corrida "+corrida);
        return Response.status(Response.Status.OK).build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response deletaCorrida(Corrida corrida){
        System.out.println("Alterando Corrida "+corrida);
        corridas.remove(corrida);
        return Response.status(Response.Status.OK).build();
    }
}
