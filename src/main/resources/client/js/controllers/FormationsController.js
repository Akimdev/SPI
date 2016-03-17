(function() {
  'use strict';

  var app = angular.module('app.formations', []);

  app.factory('formationsFactory', function($http, $window){
            
    return {
      // renvoi la liste de tous les enseignants
      all: function() { 
    	  return $http.get('http://localhost:8090/formations');
      },
      // renvoi l'enseignant avec le code demandé
      get: function(code) { 
    	  return $http.get('http://localhost:8090/formationByCode/' + code);    
      },
      
      set: function(formation) {	
    	  return $http.post('http://localhost:8090/formation/createFormation', formation);
      },
      
      delete: function(codeFormation) { 
    	  return $http.get('http://localhost:8090/formation/delete?codeFormation=' + codeFormation);
      }
    };
  });

  app.controller('FormationsController', 
    ['$scope', '$location','$http','$filter', 'formationsFactory',
    function($scope, $location,$http,$filter, formationsFactory){
      // la liste globale des formations
    	$scope.refresh = function (){
    		 var promiseFormation = formationsFactory.all();          
    	      promiseFormation.success(function(data) {
    	          $scope.formations = data;
    	         // $scope.formation.debutAccreditation = $filter('date')(data.debutAccreditation, "dd-MM-yyyy");
    			 // $scope.formation.finAccreditation = $filter('date')(data.finAccreditation, "dd-MM-yyyy");	
    	      });
    	}
     
      // Crée la page permettant d'ajouter une formation
      $scope.ajoutFormation = function(){
        $location.path("/formation/nouveau"); 
      }

      // affiche les détails d'une formation
      $scope.edit = function(formation){
        $location.path("/formation/"+ formation.codeFormation);
      }

      // supprime une formation
      $scope.supprime = function(formation){
    	  swal({   
			  title: "Voulez-vous vraiment supprimer cette formation ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "Oui, je veux le supprimer!",  
			  cancelButtonText: "Non, ignorer!",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
					  var promisessuppression  = formationsFactory.delete(formation.codeFormation);
			    	  promisessuppression.success(function(data, status, headers, config) {
						$scope.refresh();
						swal("Supprimé!", "la formation est supprimée", "success");
					});
			    	  promisessuppression.error(function(data, status, headers, config) {
			    		  swal("Erreur!", "vous pouvez pas supprimer cette formation", "error");
			  		});	
					  } else {     
						  swal("Ignorer", "", "error");
						  }
				  });  
      }
      $scope.refresh();
    }]
  );

  app.controller('FormationDetailsController', 
    ['$scope', '$routeParams','$http', '$location','$filter', 'formationsFactory',
    function($scope, $routeParams, $http, $location,$filter, formationsFactory){  

    		  $http.get('http://localhost:8090/getDomaine/DIPLOME').
    		    success(function(data, status, headers, config) {
    		      $scope.domaines = data._embedded.domaines;
    		      $scope.domaines = {
    					    availableOptions: data._embedded.domaines,
    					    selectedOption:  data._embedded.domaines[0]
    				    };
    		    }).
    		    error(function(data, status, headers, config) {
    		      // log error
    		    });
    		
    	
      $scope.edit= false;    

      // si creation d'une nouvelle formation
      if($routeParams.id == "nouveau"){
        $scope.formation= { };
        $scope.edit= true;    
      } else { // sinon on edite une formation existante
        var f = formationsFactory.get($routeParams.id);
        var promisesFactory = formationsFactory.get($routeParams.id);
     	promisesFactory.success(function(data) {
     		$scope.formation = data;   
				$scope.formation.debutAccreditation = $filter('date')(data.debutAccreditation, "dd/MM/yyyy");
				$scope.formation.finAccreditation = $filter('date')(data.finAccreditation, "dd/MM/yyyy");		
     	});
      }      
      
      $scope.edition = function(){
    	  var promisessuppression = formationsFactory.set($scope.formation);    	  
    	  formationsFactory.get($scope.formation);
          $scope.edit = true;
        }

        $scope.submit = function(){
        	if($scope.formation.debutAccreditation && $scope.formation.finAccreditation){
            	var date = $scope.formation.debutAccreditation.split('/');
            	$scope.formation.debutAccreditation = new Date(date[1] + '-' + date[0] + '-' + date[2]);
            	var date2 = $scope.formation.finAccreditation.split('/');
            	$scope.formation.finAccreditation = new Date(date2[1] + '-' + date2[0] + '-' + date2[2]);
        	}
        	var promisesajout = formationsFactory.set($scope.formation);
        	promisesajout.success(function(data, status, headers, config) {
        		$location.path('/formations');
				
			});
        	promisesajout.error(function(data, status, headers, config) {
				alert( "failure message: " + JSON.stringify({data: data}));
			});		
        	
			// Making the fields empty
			//				
			$scope.formation = {};
          $scope.edit = false;    
      // TODO coder une fonction submit permettant de modifier une formation et rediriger vers /formations 
        }

      $scope.edition = function(){
        $scope.edit = true;
      }

   // annule l'édition
      $scope.cancel = function(){
        if(!$scope.formation.codeFormation){
          $location.path('/formations');
        } else {
        	$location.path('/formations');
         // var e = formationFactory.get($routeParams.id);
          //$scope.formation = JSON.parse(JSON.stringify(e));
          $scope.edit = false;
        }
      } 

    }]
  );
})();
