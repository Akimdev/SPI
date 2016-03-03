/*
 * Author Youssef
 * EtudiantController Angular
 */

(function() {
  'use strict';

  var app = angular.module('app.etudiants', []);

  app.factory('etudiantsFactory', function($http){
	  
	  return {
	  all: function(){
    	  return $http.get('http://localhost:8090/etudiants');
	  },	  
      get: function(noEtudiant) { 
    	  //return $http.get('http://localhost:8090/etudiant/', {params: {noEtudiant: noEtudiant}});
    	  
    	  var etudiant = {
    			  noEtudiant : "21406961",
    			  codeFormation : "M2DOSI",
    			  nom : "AFKIR",
    			  prenom :"Zakaria",
    			  sexe :"M",
    			  dateNaissance :697590000000,
    			  lieuNaissance :"Tanger",
    			  nationalite :"Marocaine",
    			  telephone : null,
    			  mobile :"06 61 13 60 05",
    			  email :"afkir.zakaria@gmail.com",
    			  emailUbo : "afkir.zakaria@univ-brest.fr",
    			  adresse : "2 rue des Archives",
    			  codePostal : "29287",
    			  ville : "BREST",
    			  paysOrigine : "MA",
    			  universiteOrigine : "UAE",
    			  groupeTp : 1
    	  }
    	  return etudiant;
    	  
      },
      set: function(formation) {
    	  //return $http.post('http://localhost:8090/formations/nouveau', formation);
      },
      edit: function(formation) {
    	  //return $http.post('http://localhost:8090/formations/update', formation);
      },
      delete: function(code) { 
    	  //return $http.get('http://localhost:8090/formations/delete', {params: {codeFormation: code}});
      }
    };
  });
  
  app.controller('EtudiantsController', 
    ['$scope', '$filter','$location', 'etudiantsFactory',
    function($scope, $filter, $location, etudiantsFactory){
    	var init;
    	var promiseEtudiants = etudiantsFactory.all();
		promiseEtudiants.success(function(data, status) {
		      $scope.etudiants = data;
		      
		      $scope.searchKeywords = '';
		      $scope.filteredEtudiant = [];
		      $scope.row = '';
		      
		      $scope.select = function(page) {
		        var end, start;
		        start = (page - 1) * $scope.numPerPage;
		        end = start + $scope.numPerPage;
		        return $scope.currentPageEtudiant = $scope.filteredEtudiant.slice(start, end);
		      };
		      
		      $scope.onFilterChange = function() {
		        $scope.select(1);
		        $scope.currentPage = 1;
		        return $scope.row = '';
		      };
		      
		      $scope.onNumPerPageChange = function() {
		        $scope.select(1);
		        return $scope.currentPage = 1;
		      };
		      
		      $scope.onOrderChange = function() {
		        $scope.select(1);
		        return $scope.currentPage = 1;
		      };
		      
		      $scope.search = function() {
		        $scope.filteredEtudiant = $filter('filter')($scope.etudiants, $scope.searchKeywords);
		        return $scope.onFilterChange();
		      };
		      $scope.order = function(rowName) {
		        if ($scope.row === rowName) {
		          return;
		        }
		        $scope.row = rowName;
		        $scope.filteredEtudiant = $filter('orderBy')($scope.etudiants, rowName);
		        return $scope.onOrderChange();
		      };
		      $scope.numPerPageOpt = [3, 5, 10, 20];
		      $scope.numPerPage = $scope.numPerPageOpt[2];
		      $scope.currentPage = 1;
		      $scope.currentPageEtudiant = [];
		      init = function() {
		        $scope.search();
		        return $scope.select($scope.currentPage);
		      };
		      return init();
		  }
		)
		.error(function(data) {
			 $scope.error = 'unable to get the poneys';
		  }
		);
      

      $scope.editEtudiant = function (noEtudiant){
    	  $location.path("/admin/etudiant/"+ noEtudiant);
      }

      $scope.supprime = function(noEtudiant){ 
    	  var promise= etudiantsFactory.delete(noEtudiant);
          promise.success(function(data,statut){
        	  $scope.currentPageEtudiant.removeValue("noEtudiant",noEtudiant);
          })
          .error(function(data,statut){
        	  console.log("impossible de supprimer l'enseignant choisi");
          });
    	  
      }
    }]
  );

  app.controller('EtudiantDetailsController', 
    ['$scope', '$routeParams', '$location', '$filter', 'etudiantsFactory',
    function($scope, $routeParams, $location, $filter, etudiantsFactory){      
      
    	$scope.etudiant = etudiantsFactory.get($routeParams.noEtudiant);
      
    }]
  );
  
}).call(this);
