(function() {
  'use strict';

  var app = angular.module('app.qualificatifs', []);

  app.factory('qualificatifsFactory', function($http, $window){
            
    return {
      // renvoi la liste de tous les enseignants
      all: function() { 
    	  return $http.get('http://localhost:8090/listerQualificatif');
      },
      // renvoi l'enseignant avec le code demandé
      get: function(code) { 
    	//  return $http.get('http://localhost:8090/formation/' + code);    
      },
      
      set: function(qualificatif) {	
    	  return $http.post('http://localhost:8090/ajouterQualificatif', qualificatif);
      },
      
      delete: function(idQualificatif) { 
    	  return $http.get('http://localhost:8090/supprimerQualificatif?idQualificatif=' + idQualificatif);
      }
    };
  });

  app.controller('QualificatifsController', 
    ['$scope', '$location','$http','$filter', 'qualificatifsFactory',
    function($scope, $location,$http,$filter, qualificatifsFactory){
      // la liste globale des formations
    	$scope.refresh = function (){
    		 var promiseFormation = qualificatifsFactory.all();          
    	      promiseFormation.success(function(data) {
    	          $scope.qualificatifs = data;
    	      });
    	}
     
      // Crée la page permettant d'ajouter une formation
      $scope.ajoutQualificatif = function(){
        $location.path('/admin/qualificatif/nouveau'); 
      }

      // affiche les détails d'une formation
      $scope.edit = function(qualificatif){
        $location.path('/admin/qualificatif/' + qualificatifs.idQualificatif);
      }

      // supprime une formation
      $scope.supprime = function(qualificatif){
    	  var promisessuppression  = qualificatifsFactory.delete(qualificatif.idQualificatif);
    	  promisessuppression.success(function(data, status, headers, config) {
			$scope.refresh();
		});
	  promisessuppression.error(function(data, status, headers, config) {
			alert( "failure message: " + JSON.stringify({data: data}));
		});	
	  
      }
      $scope.refresh();
    }]
  );

  app.controller('QualificatifDetailsController', 
    ['$scope', '$routeParams','$http', '$location','$filter', 'qualificatifsFactory',
    function($scope, $routeParams, $http, $location,$filter, qualificatifsFactory){      
      $scope.edit= false;    

      // si creation d'une nouvelle formation
      if($routeParams.id == "nouveau"){
        $scope.qualificatif= { };
        $scope.edit= true;    
      } else { // sinon on edite une formation existante
        var f = qualificatifsFactory.get($routeParams.id);
        var promisesFactory = qualificatifsFactory.get($routeParams.id);
     	promisesFactory.success(function(data) {
     		$scope.qualificatif = data;   
     	});
      }      
      
      $scope.edition = function(){
    	  var promisessuppression = qualificatifsFactory.set($scope.qualificatif);    	  
    	  qualificatifsFactory.get($scope.qualificatif);
          $scope.edit = true;
        }

        $scope.submit = function(){
        	var promisesajout = qualificatifsFactory.set($scope.qualificatif);
        	promisesajout.success(function(data, status, headers, config) {
        		$location.path('/admin/qualificatifs');
				
			});
        	promisesajout.error(function(data, status, headers, config) {
				alert( "failure message: " + JSON.stringify({data: data}));
			});		
        	
			// Making the fields empty
			//				
			$scope.qualificatifs = {};
          $scope.edit = false;  
        }

      $scope.edition = function(){
        $scope.edit = true;
      }

      // valide le formulaire d'édition d'une formation
      
      // TODO coder une fonction submit permettant de modifier une formation
		// et rediriger vers /admin/formations


   // annule l'édition
      $scope.cancel = function(){
        if(!$scope.qualificatifs.idQualificatif){
          $location.path('/admin/qualificatif');
        } else {
        	$location.path('/admin/qualificatifs');
          var e = qualificatifFactory.get($routeParams.id);
          $scope.qualificatifs = JSON.parse(JSON.stringify(e));
          $scope.edit = false;
        }
      } 

    }]
  );
})();
