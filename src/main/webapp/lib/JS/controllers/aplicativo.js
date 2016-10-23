//Criação de um modulo com o nome Aplicativo
//Criação de um valor fixo de urlUse
angular.module("Aplicativo").value('urlUse', 'http://localhost:8080/Papaleguas/app/corridaController/')
        //criação do controller CorridaCtrl, e injeção do $scope, $http e da url padrão
        .controller("CorridaCtrl", function ($scope, $http, urlUse) {
            //Variaveis para armazenar informações "cache"
            $scope.bairroOrigem = undefined;
            $scope.bairroDestino = undefined;
            $scope.bairros = [];
            $scope.tempo = undefined;
            $scope.corrida = undefined;
            $scope.corridas = [];
            $scope.usuario = undefined;
            $scope.clientes = [];
            $scope.taxista = undefined;
            $scope.motoristas = [];
            $scope.menorCaminho = undefined;
            $scope.valorKm = 2.50;
            $scope.tempo = undefined;
            
            //Variaveis para controle da tela
            $scope.error = undefined;
            $scope.error304 = undefined;
            $scope.sucesso = undefined;
            $scope.historico = undefined;
            $scope.alterar = undefined;
            $scope.usuarioCad = undefined;
            $scope.grafo = undefined;

            //Metodo usado para abrir div de criação de corrida
            $scope.novaCorrida = function () {
                $scope.corrida = {data: new Date(), origem: undefined, destino: undefined, distancia: undefined,
                                    tempo: undefined, valor: undefined, cliente:undefined, 
                                    motorista:undefined, numeroCarro:undefined};
                //Coleta do grafo padrão
                pegaGrafo(undefined,undefined);
                $scope.grafo = {};//Variavel responsavel por mostrar o div grafo
            };

            //Metodo usado para abrir o div de novo cliente
            $scope.novoCliente = function() {
                $scope.usuarioCad = {};//Variavel usada para controlar a divNovoCliente
            };

            //Metodo para limpar todo tipo de entrada
            $scope.cancelar = function () {
                delete $scope.corrida;
                delete $scope.error;
                delete $scope.grafo;
                delete $scope.alterar;
                delete $scope.bairroOrigem;
                delete $scope.bairroDestino;
                delete $scope.tempo;
                delete $scope.corrida;
                delete $scope.usuario;
                delete $scope.taxista;
                delete $scope.menorCaminho;
                delete $scope.historico;
                delete $scope.tempo;
                delete $scope.sucesso;
                delete $scope.error304;
                delete $scope.usuarioCad;
            };

            //metodo para salvar a corrida
            $scope.salvarCorrida = function (corrida) {
                corrida.cliente = $scope.usuario.nome;
                corrida.numeroCarro = $scope.taxista.numeroCarro;
                corrida.motorista = $scope.taxista.nome;
                
                //Metodo para a conexão com o back-end
                $http({
                    method:'POST',
                    url: urlUse+'salvarCorrida/',
                    data : corrida
                }).then(function successCallback(response){ //se obtiver sucesso
                    if (!$scope.corridas) {
                        $scope.corridas = [corrida];
                    } else {
                        $scope.corridas.push(corrida);
                    }
                    $scope.corrida = undefined;
                    $scope.grafo = undefined;
                }, function  errorCallback(response){//se der erro
                    $scope.error={};
                    erro("SalvarCorrida");
                });
            };
            
            //Metodo para salar cliente
            $scope.salvarCliente = function (nome){ 
                obj = {nome:nome};
                
                //Metodo para a conexão com o back-end
                $http({
                    method:'POST',
                    url: urlUse+'salvarCliente/',
                    data : obj.nome
                }).then(function successCallback(response){
                    $scope.clientes.push(obj);
                    document.getElementById('nome').value="";
                    $scope.sucesso = {};
                }, function  errorCallback(response){
                    if(response.status === 304){
                        $scope.error={};
                    }
                    erro("AlteraTempo");
                });
            };
            
            //Metodo para a criação do grafo na tela
            var criaGrafo = function (urlCreation) {
                //A biblioteca do d3 já lê a informação direto da URL
                d3.json(urlCreation, function (json) {
                    console.log(json.nodes);
                    limpar('divgrafo');
                    //atribui valores padrão à altura e largura
                    var width = 1200,height = 900;
                
                    //Cria um campo "divgrafo" e atribui um svg com a altura e largura padrão
                    var svg = d3.select('#divgrafo')
                            .append('svg')
                            .attr('width', width)
                            .attr('height', height);

                    // Desenha as arestas
                    var link = svg.selectAll("line")//Cria uma linha na tela
                            .data(json.links) //Diz que o conjunto de linhas advem do json
                            .enter().append("line")
                            .attr("class", "link") //Atribui a classe link
                            .style("stroke", function (d) {
                                return d.cor;
                            });//cor da linha vem do json

                    //Desenha os nós na tela
                    var node = svg.selectAll("circle") // cria um circulo
                            .data(json.nodes) //Diz q os dados para criação dos circulos é os dados do json
                            .enter()
                            .append("circle")
                            .attr("class", "node") //diz que a classe que usa é o .node do svg
                            .attr("r", "15") //Valor do Raio
                            .style("fill", function (d) {
                                return d.cor;
                            }); //atribui cor ao nó

                    //Cria um tipo texto
                    var text = svg.selectAll("text")
                            .data(json.nodes)
                            .enter()
                            .append("text");

                    //atribui algumas propriedades aos textos dos nós
                    var nodeText = text
                            .text(function (d) {
                                return d.nome;
                            })
                            .attr("font-family", "sans-serif")
                            //.attr("font-size", "100px")
                            .attr("fill", "black");

                    // create the layout
                    var force = d3.layout.force()
                            .charge(-1200)
                            .linkDistance(500)
                            .size([width, height])
                            .nodes(json.nodes)
                            .links(json.links)
                            .start();

                    // define what to do one each tick of the animation
                    force.on("tick", function () {

                        link.attr("x1", function (d) {
                            return d.source.x;
                        })
                                .attr("y1", function (d) {
                                    return d.source.y;
                                })
                                .attr("x2", function (d) {
                                    return d.target.x;
                                })
                                .attr("y2", function (d) {
                                    return d.target.y;
                                });

                        node.attr("cx", function (d) {
                            return d.x;
                        })
                                .attr("cy", function (d) {
                                    return d.y;
                                });

                        nodeText.attr("x", function (d) {
                            return (d.x);
                        })
                                .attr("y", function (d) {
                                    return (d.y);
                                });
                    });
                    // bind the drag interaction to the nodes
                    node.call(force.drag);
                });
            };
            
            //Metodo para calcular a corrida
            $scope.calcular = function (bairroOrigem, bairroDestino, valorKm) {
                if (bairroOrigem == bairroDestino) {
                    $scope.corrida.origem = undefined;
                    $scope.corrida.destino = undefined;
                    $scope.corrida.distancia = undefined;
                    $scope.corrida.tempo = undefined;
                    $scope.corrida.valorTotal = undefined;
                    $scope.corrida.cliente = undefined;
                    $scope.corrida.motorista = undefined; 
                    $scope.corrida.numeroCarro = undefined;
                    erro("TudoIgual");
                } else {
                    //metodo para a coneção com o back-end
                    //Aqui ele calcula o menor caminho
                    $http({
                        methode: 'GET',
                        url: urlUse+'menorCaminho/'+bairroOrigem+'/'+bairroDestino+'/'
                    }).then(function successCallback(response){
                        $scope.menorCaminho = response.data;
                        $scope.corrida.distancia = $scope.menorCaminho.distancia;
                        $scope.corrida.tempo = $scope.menorCaminho.tempo;
                        $scope.corrida.valor = $scope.menorCaminho.distancia * $scope.valorKm;                    
                        $scope.corrida.cliente = $scope.usuario;
                        $scope.corrida.motorista = $scope.taxista.nome; 
                        $scope.corrida.numeroCarro = $scope.taxista.numeroCarro;
                        
                        //Se obtiver sucesso ele cria o grafo para o menor caminho
                        pegaGrafo(bairroOrigem, bairroDestino);
                    }, function  errorCallback(response){
                        erro("MenorCamino");
                    });
                };
            };

            //Metodo para alterar o tempo entre dois bairros
            $scope.alterarTempo = function (origem, destino, tempo) {
                obj = {origem:origem, destino:destino, tempo:tempo };
                $http({
                    method:'POST',
                    url: urlUse+'alteraTempo/',
                    data : obj
                }).then(function successCallback(response){
                    $scope.sucesso = {};
                    $scope.error304 = undefined;
                }, function  errorCallback(response){
                    if(response.status === 304){//Tratamento apra mostrar a informação correta
                        $scope.error304 = {};
                    }
                    erro("AlteraTempo");
                });
            };

            // Metodos para coleta de informações do service------------------------
            //Metodo para carregar os usuarios
            var pegaClientes = function (){
                $http({
                    methode: 'GET',
                    url: urlUse+'clientes/'
                }).then(function successCallback(response){
                    $scope.clientes = response.data;//Armazena em clientes o json q receber
                }, function  errorCallback(response){
                    erro("pegaClientes");
                });
            };
            
            //Metodo para pegar gerar a url para criar o grafo
            var pegaGrafo = function (urlOrigem, urlDestino) {
                if(urlOrigem===undefined || urlDestino===undefined){//se os valores passados forem invalidos
                    criaGrafo(urlUse+'grafo/'); //pega grafo padrão
                }else{//se tiver valores
                    criaGrafo(urlUse+'menorCaminhoGrafo/'+urlOrigem+'/'+urlDestino+'/');//Pega grafo para o menor caminho
                }
            };
            
            //Metodo para carregar os bairros
            var pegaBairros = function () {
                $http({
                    methode: 'GET',
                    url: urlUse+'bairros/'
                }).then(function successCallback(response){
                    $scope.bairros = response.data;//Armazena em bairros o json q receber
                }, function  errorCallback(response){
                    erro("pegaBairros");
                });
            };
            
            //Metodo para carregar os motoristas
            var pegaMotoristas = function (){
                $http({
                    methode: 'GET',
                    url: urlUse+'motoristas/'
                }).then(function successCallback(response){
                    $scope.motoristas = response.data; //Armazena em motoristas o json q receber
                }, function  errorCallback(response){
                    erro('pegaMotoristas');
                });
            };
            
            //Metodo para carregar as corridas
            var pegaCorridas = function (){
                $http({
                    methode: 'GET',
                    url: urlUse+'corridas/'
                }).then(function successCallback(response){
                    $scope.corridas = response.data;//Armazena em corridas o json q receber
                }, function  errorCallback(response){
                    erro('pegaCorrida');
                });
            };         

            //-----------------------
            var start = function () { //Metodo que carrega tudo a primeira vez
                pegaBairros();
                pegaClientes();
                pegaMotoristas();
                pegaCorridas();
            };
            
            //Metodo adicional para limpar um elemento da tela
            var limpar = function (d) {
                document.getElementById(d).innerHTML = "";
                document.getElementById(d).focus();
            };
            
            //Metodo que lança exceção
            var erro = function (d) {
                $scope.error = {};
                console.log("Erro em: "+d);
            };

            start();
        });