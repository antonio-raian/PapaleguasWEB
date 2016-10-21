package br.com.uefs.papaleguasweb.controller;

import br.com.uefs.papaleguasweb.exception.EncontraVerticeException;
import br.com.uefs.papaleguasweb.model.Cliente;
import br.com.uefs.papaleguasweb.model.Corrida;
import br.com.uefs.papaleguasweb.model.Motorista;
import br.com.uefs.papaleguasweb.util.GrafoJson;
import br.com.uefs.papaleguasweb.util.Grafo;
import br.com.uefs.papaleguasweb.util.MenorCaminho;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controlador {
    private final Grafo grafo;
    private final List<Corrida> corridas;
    private final List<Cliente> clientes;
    private final List<Motorista> motoristas;

    public Controlador() throws FileNotFoundException, IOException, EncontraVerticeException{
        grafo = new Grafo();
        corridas = new ArrayList<>();
        clientes = new ArrayList<>();
        motoristas = new ArrayList<>();
        //lerArquivo();
        teste();
        
    }
    
    public void adicionaVertice(String dado) throws EncontraVerticeException{
        grafo.insereVertice(dado);
    }
    
    public void adicionaAresta(String origem, String destino, String distancia, String tempo) throws EncontraVerticeException{
        grafo.insereAresta(origem, destino, distancia, tempo);
    }
    
    public int[][] mostraGrafo(){
        return grafo.matriz();
    }

    private void lerArquivo() throws FileNotFoundException, IOException, EncontraVerticeException {
        FileReader bairros = new FileReader("/home/netsrak/UEFSProjetos/PapaleguasWEB/bairros.txt");
        BufferedReader readBairro = new BufferedReader(bairros);
        
        FileReader tabela = new FileReader("/home/netsrak/UEFSProjetos/PapaleguasWEB/data.txt");
        BufferedReader readTab = new BufferedReader(tabela);
        
        int qtdVer =Integer.parseInt(readTab.readLine());
        int qtdAresta = Integer.parseInt(readTab.readLine());
        String[] vert = new String[qtdVer];
      
        for(int i=0;i<qtdVer;i++){
            vert[i] = readBairro.readLine().replace(", ", " ");
            adicionaVertice(vert[i]);
        }
        int i=0;
        while(true){
            String line = readTab.readLine();
            if(line==null)
                break;
            System.out.println(line);
            System.out.println(vert[i]);
            if(vert[i].equals(line)){
                for(int j=0;j<qtdAresta;j++){
                    String[] str = readTab.readLine().split("/");
                    adicionaAresta(vert[i], str[0], str[1], str[2]);
                }
                i++;
            }
        }
    }

    public List<String> getBairros(){
        List<String> str = grafo.getVertices();
        for(String s:str){
            str.add((s.split(" "))[0]);
        }
        return str;
    }
    
    public MenorCaminho menorCaminho(String origem, String destino, boolean condicao){
        return grafo.menorCaminho(origem, destino, condicao);
    }
    
    public void salvaCorrida(Corrida corrida){
        corridas.add(corrida);
    }
    
    public GrafoJson getGrafoJson(){
        GrafoJson g = new GrafoJson();
        
        g=grafo.getJson();
        
        return g;
    }
    
    private void teste() throws EncontraVerticeException{
        adicionaVertice("A");
        adicionaVertice("B");
        adicionaVertice("C");
        adicionaVertice("D");
        adicionaVertice("E");
        
        adicionaAresta("A", "B", "6", "5");
        adicionaAresta("A", "C", "4", "8");
        adicionaAresta("A", "D", "10", "3");
        adicionaAresta("B", "C", "16", "2");
        adicionaAresta("B", "D", "20", "10");
        adicionaAresta("C", "E", "18", "9");
        adicionaAresta("D", "E", "8", "4");
    }
}
