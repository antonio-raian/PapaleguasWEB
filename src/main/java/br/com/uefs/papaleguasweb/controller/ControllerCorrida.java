package br.com.uefs.papaleguasweb.controller;

import br.com.uefs.papaleguasweb.exception.EncontraVerticeException;
import br.com.uefs.papaleguasweb.model.Cliente;
import br.com.uefs.papaleguasweb.model.Corrida;
import br.com.uefs.papaleguasweb.model.Motorista;
import br.com.uefs.papaleguasweb.util.MenorCaminho;
import br.com.uefs.papaleguasweb.util.jsons.GrafoJson;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Classe Rest para coneção com o front-End
@Path("corridaController")
public class ControllerCorrida {
    Controlador ctrl;
    public ControllerCorrida() throws EncontraVerticeException, IOException {
        ctrl = new Controlador();//Construtor
    }
    
    //Metodo para pegar a lista de bairros, produz um Json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bairros/")
    public List<String> listBairros(){
        return ctrl.getBairros();//Invoca o metodo do Controlador para coletar os bairros
    }
    
    //Metodo para pegar o grafo inicial, produz um Json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("grafo/")
    public GrafoJson grafo(){
        GrafoJson graph = ctrl.getGrafoJson(null);//Invoca o metodo do Controlador para coletar o grafo padrão
        return graph;
    }
    
    //Metodo para pegar a lista de Motorista, produz um Json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("motoristas/")
    public List<Motorista> motoristas(){
        return ctrl.motoristas();//Invoca o metodo do Controlador para coletar os motoristas
    }
    
    //Metodo para pegar a lista de Cientes, produz um Json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("clientes/")
    public List<Cliente> clientes(){
        return ctrl.clientes();//Invoca o metodo do Controlador para coletar os clientes
    }
    
    //Metodo para pegar a lista de Corridas, produz um Json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("corridas/")
    public List<Corrida> corridas(){
        return ctrl.corridas();//Invoca o metodo do Controlador para coletar as corridas
    }
    
    //Metodo para pegar o Menor caminho entre dois bairros, produz um Json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("menorCaminho/{origem}/{destino}")
    public MenorCaminho menorCaminho(@PathParam("origem") String origem, @PathParam("destino") String destino){
        return ctrl.menorCaminho(origem, destino, true);//Invoca o metodo do Controlador para encontrar o menor caminho
    }
    
    //Metodo para pegar o grafo alterado pelo menor caminho, produz um Json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("menorCaminhoGrafo/{origem}/{destino}")
    public GrafoJson menorCaminhoGrafo(@PathParam("origem") String origem, @PathParam("destino") String destino){
        MenorCaminho menor = ctrl.menorCaminho(origem, destino, true);//Invoca o metodo do Controlador para coletar o menor caminho
        return ctrl.getGrafoJson(menor);//Invoca o metodo do Controlador para coletar o grafo de acordo com o menor caminho
    }
    
    //Metodo para salvar uma Corrida, consome/ler um Json
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("salvarCorrida/")
    public Response criarCorrida(Corrida corrida){
        ctrl.salvaCorrida(corrida); //Invoca o metodo para salvar corrida do Controlador
        return Response.status(Response.Status.OK).build();
    }
    
    //Metodo para salvar um Cliente, consome/ler um Json
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("salvarCliente/")
    public Response criarCliente(String cliente){
        ctrl.salvaCliete(cliente);//Invoca o metodo para salvar cliente do Controlador
        return Response.status(Response.Status.OK).build();
    }
    
    //Metodo para alterar o tempo entre dois bairros, consome/ler um Json
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("alteraTempo/")
    public Response alterarTempo(String alterar){
        alterar = alterar.replace("{", "");//Retira os '{'
        alterar = alterar.replace("}", "");//Retira os '}'
        alterar = alterar.replace('"', ' ');//Retira todas as '"'
        String[] str = alterar.split(","); //divide a string pelo ','
        String origem = (str[0].split(":"))[1];//A origem é a primeira parte da string separada
        String destino = (str[1].split(":"))[1];//O destino é a segunda parte da string separada
        String tempo = (str[2].split(":"))[1];//O tempo é a terceira parte da string separada
        
        if(ctrl.alteraTempo(origem, destino, tempo)){//Invoca o metodo do controlador
            return Response.status(Response.Status.OK).build();//Se alterar retorna OK
        }
        return Response.status(Response.Status.NOT_MODIFIED).build(); //Retorna erro304 caso não consiga alterar
        
    }
    
}
