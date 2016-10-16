/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


angular.module("Aplicativo",[]).value('urlUse','http://localhost:8080/Papaleguas/app/')
    .controller("ControllerCorrida", function ($http,urlUse){
        var self = this;
        self.bairros = [];
        self.corrida = undefined;
        self.dados = undefined;
        
        self.novo = function(){
          alert = ("Chegou no Novo!");
          self.corrida = {};
        };
        
        self.atualizaGrafo = function () {
            
        };
        
        self.pegaBairros = function () {
            alert = ("Chegou no pega Bairros!");
            $http({
                method:'GET',
                url: urlUse+"corrida/"
            }).then(function successCallback(response){
                self.bairros = response.data;
            }),then (function errorCallback(response){
                self.erro();
            });
        };
        
        self.cancela = function(){
            self.corrida = undefined;
        };
        
        self.calcular = function (){};
        
        self.alterar = function(){};
        
        self.erro = function(){
            alert = ("Erro da porra!");
        };
        
        self.activate = function (){
            self.pegaBairros();
            self.atualizaGrafo();
        };
        
        self.activate();
    });