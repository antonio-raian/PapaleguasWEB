package br.com.uefs.papaleguasweb.controller;

import br.com.uefs.papaleguasweb.exception.EncontraVerticeException;
import br.com.uefs.papaleguasweb.model.Corrida;
import br.com.uefs.papaleguasweb.util.GrafoJson;
import java.io.IOException;
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
    public ControllerCorrida() throws EncontraVerticeException, IOException {
        ctrl = new Controlador();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bairros/")
    public List<String> listBairros(){
        return ctrl.getBairros();
    }
    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/grafo/")
//    public GrafoJson grafo(){
//        return ctrl.getGrafoJson();
//    }
//    
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/{corrida}")
//    public Response criarCorrida(@PathParam("corrida")Corrida corrida){
//        System.out.println("Adicionando Corrida "+corrida);
//        //ctrl.salvaCorrida(corrida);
//        return Response.status(Response.Status.OK).build();
//    }
//    
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/")
//    public Response alteraCorrida(Corrida corrida){
//        System.out.println("Alterando Corrida "+corrida);
//        return Response.status(Response.Status.OK).build();
//    }
//    
//    @DELETE
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/")
//    public Response deletaCorrida(Corrida corrida){
//        System.out.println("Alterando Corrida "+corrida);
//        return Response.status(Response.Status.OK).build();
//    }
}
