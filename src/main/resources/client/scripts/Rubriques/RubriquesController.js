/*
* Author BAQLOUL Soukaina
* Script de controle des Rubriques
*/
(function() {
  'use strict';

  var app = angular.module('app.rubriques', []);

  Array.prototype.removeValue = function(name, value){
	   var array = $.map(this, function(v,i){
	      return v[name] === value ? null : v;
	   });
	   this.length = 0; //clear original array
	   this.push.apply(this, array); //push all elements except the one we want to delete
	}
  
  app.factory('rubriquesFactory', ['$http',function($http){
    
    return {
    	// TODO Lister 
      listerRubriques:function(){
    	  return $http.get("http://localhost:8090/rubriques");
      },    
      delete: function(idRubrique) { 
        // TODO Supprimer 
    	  console.log("TODO : supprimer rubrique", idRubrique);
    	  return  $http.get('http://localhost:8090/deleteRubrique/'+ idRubrique)
      }

    };
  }]);

  

  app.controller('RubriquesController', 
    ['$scope', '$filter','$location', 'rubriquesFactory',
    function($scope, $filter, $location, rubriquesFactory){
    	var init;
    	var promise = rubriquesFactory.listerRubriques();
    	promise.success(function(data) {
		    $scope.rubriques = data;
		      $scope.searchKeywords = '';
		      $scope.filteredRubrique = [];
		      $scope.row = '';
		      $scope.select = function(page) {
		        var end, start;
		        start = (page - 1) * $scope.numPerPage;
		        end = start + $scope.numPerPage;
		        return $scope.currentPageRubrique = $scope.filteredRubrique.slice(start, end);
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
		        $scope.filteredRubrique = $filter('filter')($scope.rubriques, $scope.searchKeywords);
		        return $scope.onFilterChange();
		      };
		      $scope.order = function(rowName) {
		        if ($scope.row === rowName) {
		          return;
		        }
		        $scope.row = rowName;
		        $scope.filteredRubrique = $filter('orderBy')($scope.rubriques, rowName);
		        return $scope.onOrderChange();
		      };
		      $scope.numPerPageOpt = [3, 5, 10, 20];
		      $scope.numPerPage = $scope.numPerPageOpt[2];
		      $scope.currentPage = 1;
		      $scope.currentPageRubrique = [];
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
     
  $scope.ajoutRubrique = function(){
      $location.path('/admin/rubrique/nouveau'); 
   }
  
 
  $scope.edit = function (idRubrique){
	  $location.path("/admin/rubrique/"+ idRubrique);
	 
  }

      // supprime une Rubrique
      $scope.supprime = function(idRubrique){ 
    	// TODO Suppression d'une Rubrique de la liste
    	  
    	  var promise= rubriquesFactory.delete(idRubrique);
    	  promise.success(function(data,statut){
        	  //$scope.enseignant.promotions = data ;
        	  $scope.currentPageEnseignant.removeValue("idRubrique",idRubrique);
          })
          error(function(data,statut){
        	  console.log("impossible de supprimer la rubrique choisie");
          });
    	  
      }
    }]
  );
}).call(this);
  
