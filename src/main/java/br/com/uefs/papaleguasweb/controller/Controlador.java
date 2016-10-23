package br.com.uefs.papaleguasweb.controller;

import br.com.uefs.papaleguasweb.exception.EncontraVerticeException;
import br.com.uefs.papaleguasweb.model.Cliente;
import br.com.uefs.papaleguasweb.model.Corrida;
import br.com.uefs.papaleguasweb.model.Motorista;
import br.com.uefs.papaleguasweb.util.jsons.GrafoJson;
import br.com.uefs.papaleguasweb.util.Grafo;
import br.com.uefs.papaleguasweb.util.MenorCaminho;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Classe responsável por fazer a ligação entro o back-end e o Rest
public class Controlador {
    private final Grafo grafo; //Grafo
    private final List<Corrida> corridas;//Todas as corridas
    private final List<Cliente> clientes;//Todos os cliente
    private final List<Motorista> motoristas;//Todos os motoristas

    //Construtor
    public Controlador() throws FileNotFoundException, IOException, EncontraVerticeException{
        grafo = new Grafo(); 
        corridas = new ArrayList<>();
        clientes = new ArrayList<>();
        motoristas = new ArrayList<>();
        lerArquivo();//ler a tabela de informações e gera o grafo
        init();//inicia criando os motoristas e usuario padrão
    }
    
    //Adiciona um vertice
    public void adicionaVertice(String dado) throws EncontraVerticeException{
        grafo.insereVertice(dado);//Chama o metodo insereVertice da classe Grafo
    }
    
    //Adiciona uma aresta
    public void adicionaAresta(String origem, String destino, String distancia, String tempo) throws EncontraVerticeException{
        //converte o tempo em inteiro----
        String[] str = tempo.split(" ");
        int temp = Integer.parseInt(str[0]);
        //---------------------------------
        //converte a distancia em double---
        str = distancia.split(" ");
        double dist = Double.parseDouble(str[0].replace(",", "."));
        //----------------------------------
        grafo.insereAresta(origem, destino, dist, temp); //invoca o metodo insereAresta da classe Grafo
    }
    
    //Coleta matriz de adjacencia do grafo
    public int[][] mostraGrafo(){
        return grafo.matriz();//invoca o metodo que gera a matris da classe Grafo
    }

    //Ler a tabela de bairros e ligações
    private void lerArquivo() throws FileNotFoundException, IOException, EncontraVerticeException {
        //abre arquivo com o nome dos bairros
        FileReader bairros = new FileReader("/home/netsrak/UEFSProjetos/PapaleguasWEB2.0/bairros.txt");
        BufferedReader readBairro = new BufferedReader(bairros);
        
        //abre tabela com as informações de ligações entre os bairros
        FileReader tabela = new FileReader("/home/netsrak/UEFSProjetos/PapaleguasWEB2.0/data.txt");
        BufferedReader readTab = new BufferedReader(tabela);
        
        int qtdVer =Integer.parseInt(readTab.readLine()); //A primeira linha da tabela é a quantidade de vertices
        int qtdAresta = Integer.parseInt(readTab.readLine());//A segunda linha é a quantidade de ligações para cada vertice
        String[] vert = new String[qtdVer];//array para armazenar os vertices
      
        for(int i=0;i<qtdVer;i++){//laço para adicionar os vertices no grafo
            vert[i] = readBairro.readLine().replace(", ", " ");//ler uma linha e muda as ',' por espaços em branco
            adicionaVertice(vert[i]);//adiciona um vertice
            
        }
        int i=0;
        while(true){//laço para criação de arestas
            String line = readTab.readLine();//Ler linha da tabela
            if(line==null)//se a linha for nula a tabela já foi lida por completo
                break;
            if(vert[i].equals(line)){//para todas as posições do array de vertices verifica se a linha é compativel
                for(int j=0;j<qtdAresta;j++){//laço para a inserção da aresta
                    String[] str = readTab.readLine().split("/");//ler a linha e divide pelas '/'
                    adicionaAresta(vert[i], str[0], str[1], str[2]);//invca o metodo passando cada posição do array da divisão
                }
                i++;
            }
        }
    }

    //Coleta os bairros em forma de Array
    public List<String> getBairros(){
        List<String> str = grafo.getVertices(); //invoca o metodo para pegar os vertices da classe Grafo
        int i = 0;
        for(String s:str){//para toda String s em str (lita de vertices)
            String[] split = s.split(" ");//faz a divisão pelos espaços
            //como tempos nomes compostos até 3, usa-se um switch para armazena-los na string de retorno
            switch (split.length){
                case 3 : {
                    str.set(i++,split[0]);
                    break;
                }
                case 4:{
                    str.set(i++, split[0]+" "+split[1]);
                    break;
                }
                case 5:{
                    str.set(i++, split[0]+" "+split[1]+" "+split[2]);
                    break;
                }
            }            
        }
        return str;
    }
    
    //Metodo que retorna um objeto com as informações de menor caminho
    public MenorCaminho menorCaminho(String origem, String destino, boolean condicao){
        return grafo.menorCaminho(origem, destino, condicao);//invoca o metodo de menor caminho do Grafo
    }
    
    //Metodo que adicina uma corrida a lista de corridas
    public void salvaCorrida(Corrida corrida){
        corridas.add(corrida);
    }
    
    //Metodo que adiciona um cliente a lista de clientes
    void salvaCliete(String nome) {
        Cliente cliente = new Cliente(nome);
        clientes.add(cliente);
    }
    
    //Metodo que coleta um grafo em formato Json
    public GrafoJson getGrafoJson(MenorCaminho menor){
        return grafo.getJson(menor);//invoca o metodo que transforma o grafo em json
    }
    
    //Metodo que inicia os motoristas e o usuario padrão
    private void init(){
        Motorista m1 = new Motorista("João", 1223, grafo.getVertices().get((int) Math.round(Math.random()*10)));
        Motorista m2 = new Motorista("José", 1221, grafo.getVertices().get((int) Math.round(Math.random()*10)));
        Motorista m3 = new Motorista("Maria", 1233, grafo.getVertices().get((int) Math.round(Math.random()*10)));
        Motorista m4 = new Motorista("Claudio", 2223, grafo.getVertices().get((int) Math.round(Math.random()*10)));
        Motorista m5 = new Motorista("Sara", 3223, grafo.getVertices().get((int) Math.round(Math.random()*10)));
        Motorista m6 = new Motorista("Karol", 4223, grafo.getVertices().get((int) Math.round(Math.random()*10)));
        Motorista m7 = new Motorista("Tereza", 4323, grafo.getVertices().get((int) Math.round(Math.random()*10)));
        Motorista m8 = new Motorista("Thiago", 2233, grafo.getVertices().get((int) Math.round(Math.random()*10)));
        Motorista m9 = new Motorista("Matheus", 1123, grafo.getVertices().get((int) Math.round(Math.random()*10)));
        Motorista m10 = new Motorista("Pedro", 1523, grafo.getVertices().get((int) Math.round(Math.random()*10)));
        
        motoristas.add(m1);motoristas.add(m2);motoristas.add(m3);motoristas.add(m4);motoristas.add(m5);
        motoristas.add(m6);motoristas.add(m7);motoristas.add(m8);motoristas.add(m9);motoristas.add(m10);
        
        
        Cliente c1 = new Cliente("Antonio");
  
        clientes.add(c1);
    }
    
    //Metodo que pega a lista de motoristas
    public List<Motorista> motoristas(){
        return motoristas;
    }
    
    //Metodo que pega a lista de corridas
    public List<Corrida> corridas(){
        return corridas;
    }

    //Metodo que pega a lista de clientes
    public List<Cliente> clientes(){
        return clientes;
    }
    
    //Metodo que altera o tempo entre dois bairros
    public boolean alteraTempo(String origem, String destino, String tempo){  
        origem = origem.concat("Salvador Bahia"); //Concatena a cidade e estado ao nome da cidade
        origem = (String) origem.subSequence(1, origem.length()); //Retira o primeiro espaço da string
        destino = destino.concat("Salvador Bahia");//Concatena a cidade e estado ao nome da cidade
        destino = (String) destino.subSequence(1, destino.length());//Retira o primeiro espaço da string
        tempo = tempo.trim();//retira todos os espaços da string tempo
        
        List<String> adjacentes = grafo.getAdjacentes(origem);//Coleta todos os adjacentes
        for(String adj:adjacentes){//laço para verificar se os bairros são vizinhos
            if(adj.equals(destino)){//se tiver
                grafo.alteraTempo(origem, destino, Integer.parseInt(tempo)); //Chama o metodo da classe Grafo
                return true;//retorna verdadeiro se alterar
            }
        }
        return false; //retorna false se não forem vizinhos
    }
}
