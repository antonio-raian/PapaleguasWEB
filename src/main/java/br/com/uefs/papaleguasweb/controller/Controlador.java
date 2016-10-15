package br.com.uefs.papaleguasweb.controller;

import br.com.uefs.papaleguasweb.exception.EncontraVerticeException;
import br.com.uefs.papaleguasweb.util.Grafo;
import br.com.uefs.papaleguasweb.util.MenorCaminho;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Controlador {
    private final Grafo grafo;

    public Controlador() throws FileNotFoundException, IOException, EncontraVerticeException{
        grafo = new Grafo();
        //lerArquivo();
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

    public List<String> getVertices(){
        return grafo.getVertices();
    }
    
    public MenorCaminho menorCaminho(String origem, String destino, boolean condicao){
        return grafo.menorCaminho(origem, destino, condicao);
    }
}
