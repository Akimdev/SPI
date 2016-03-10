/*
 * @author LAKRAA
 */
(function() {
  'use strict';

  var app = angular.module('app.evaluations', []);

  app.factory('evaluationsFactory', function($http, $window){
            
    return {
      // renvoi la liste de tous les evaluations
      all: function() { 
    	  return $http.get('http://localhost:8090/evaluations');
      },
      // renvoi la evaluation avec le code demandé
      get: function(code) { 
    	  return $http.get('http://localhost:8090//findEvaluationById-' + code);    
      },
      set: function(evaluation) {	
    	  return $http.post('http://localhost:8090/updateEvaluation', evaluation);
      },
      add: function(evaluation) {
    	  return $http.post('http://localhost:8090/addEvaluation', evaluation)
      },
      delete: function(idEvaluation) { 
    	  return $http.get('http://localhost:8090/deleteEvaluation-' + idEvaluation);
      },
      getQualificatif: function(idevaluation){
    	  //return $http.get('http://localhost:8090/getQualificatif/' + idevaluation);
      },
      getDomain : function(){
    	  return $http.get("http://localhost:8090/getDomaine/ETAT-EVALUATION");
      },
      findByCodeFormation: function(codeFormation){
    	  console.log("code: ", codeFormation);
    	  return $http.get("http://localhost:8090/elementConstitutif/findByCodeFormation/"+codeFormation);
      }
    };
  });
    
  app.controller('EvaluationsController', 
    ['$scope', '$location','$http','$filter', 'evaluationsFactory',
    function($scope, $location,$http,$filter, evaluationsFactory){
      // la liste globale des evaluations
    	$scope.refresh = function (){
    		 var promiseEvaluation = evaluationsFactory.all();          
    		 promiseEvaluation.success(function(data) {
    			  $scope.evaluations = data;
    		      $scope.searchKeywords = '';
    		      $scope.filteredEvaluation = [];
    		      $scope.row = '';
    		      $scope.select = function(page) {
    		        var end, start;
    		        start = (page - 1) * $scope.numPerPage;
    		        end = start + $scope.numPerPage;
    		        return $scope.currentPageEvaluation = $scope.filteredEvaluation.slice(start, end);
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
    		        $scope.filteredEvaluation = $filter('filter')($scope.evaluations, $scope.searchKeywords);
    		        return $scope.onFilterChange();
    		      };
    		      $scope.order = function(rowName) {
    		        if ($scope.row === rowName) {
    		          return;
    		        }
    		        $scope.row = rowName;
    		        $scope.filteredEvaluation = $filter('orderBy')($scope.evaluations, rowName);
    		        return $scope.onOrderChange();
    		      };
    		      $scope.numPerPageOpt = [3, 5, 10, 20];
    		      $scope.numPerPage = $scope.numPerPageOpt[2];
    		      $scope.currentPage = 1;
    		      $scope.currentPageEvaluation = [];
    		      var init = function() {
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
    	}
     
      // Crée la page permettant d'ajouter une evaluation
      $scope.ajoutEvaluation = function(){
        $location.path('/admin/evaluation/nouveau'); 
      }

      // affiche les détails d'une evaluation
      $scope.edit = function(evaluation){
        //$location.path('/admin/evaluation/' + evaluation.idevaluation);
      }

      // supprime une evaluation
      $scope.supprime = function(evaluation){

    	  swal({   
			  title: "Voulez-vous vraiment supprimer cette evaluation ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "Oui, je veux le supprimer!",  
			  cancelButtonText: "Non, ignorer!",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
			    	  var promisessuppression  = evaluationsFactory.delete(evaluation.idEvaluation);
			    	  promisessuppression.success(function(data, status, headers, config) {
			  			$scope.refresh();
						swal("Supprimé!", "la evaluation est supprimée", "success");
			      	  });
			    	  promisessuppression.error(function(data, status, headers, config) {
			    		  swal("Erreur!", "vous pouvez pas supprimer cette evaluation", "error");
			  		});	
				  } else {     
						  swal("Ignorer", "", "error");
				  }
	  	 });
      }
      
      $scope.refresh();
    }]
  );

  app.controller('EvasDetailsController', 
    ['$scope', '$routeParams','$http', '$location','$filter', 'evaluationsFactory', 'qualificatifsFactory', 'promotionsFactory', 'toaster',
    function($scope, $routeParams, $http, $location,$filter, evaluationsFactory, qualificatifsFactory, promotionsFactory, toaster){      
      $scope.edit= false;    
      
      // si creation d'une nouvelle evaluation
      if($routeParams.id == "nouveau"){
        $scope.evaluation= { };
        $scope.edit= true;
        var promiseDomain = evaluationsFactory.getDomain();
 		promiseDomain.success(function(data) { 
 			console.log(data);
 			$scope.domains = data;
 			//$scope.selectedOption = data[0];
 		});
 		var promiseQualificatifs = qualificatifsFactory.all();
 		promiseQualificatifs.success(function(data) {   
 			$scope.qualificatifs = data;
 			$scope.selectedOption = data[0];
 		});
 		// Récuperation des formations
        var promise2= promotionsFactory.getFormations();
        promise2.success(function(data,statut){
        	$scope.formations= data;
        	console.log("\tFormations récupérées: ", data);
        })
        .error(function(data,statut){
      	  console.log("impossible de recuperer la liste des formations");
        });
 		// Récuperation du domaine
        var promise2= evaluationsFactory.getDomain();
        promise2.success(function(data,statut){
        	$scope.etats= data;
        	console.log("\tEtats récupérés: ", data);
        })
        .error(function(data,statut){
      	  console.log("impossible de recuperer la liste des etats");
        });
        
        
	 } else { // sinon on edite une evaluation existante
        var promisesFactory = evaluationsFactory.get($routeParams.id);
     	promisesFactory.success(function(data) {
     		$scope.isVisible = true;
     		$scope.evaluation = data;   console.log("evaluation: ", $scope.evaluation);
     		var promiseQualificatifs = qualificatifsFactory.all();
     		promiseQualificatifs.success(function(data) {   
     			var promiseQualif = evaluationsFactory.getQualificatif($routeParams.id);
         		promiseQualif.success(function(result){
     			$scope.qualificatifs = data;
     			$scope.selectedOption = result;
         		});
     		});
     		
     	});
     	
      }
      $scope.edition = function(){
    	  var promisessuppression = evaluationsFactory.set($scope.evaluation);    	  
    	  evaluationsFactory.get($scope.evaluation);
    	  
          $scope.edit = true;
        }

        $scope.submit = function(){console.log("true :",$scope.evaluation);
        $scope.evaluation.code_formation= $scope.formationSelected;
        	var promiseajout = evaluationsFactory.add($scope.evaluation);
        	promiseajout.success(function(data, status, headers, config) {
        		console.log("true :",$scope.evaluation);
        		if($routeParams.id === "nouveau") 
        			swal("Félicitation!", "La nouvelle evaluation est ajoutée!", "success");
        		else
        			swal("Félicitation!", "La evaluation est modifiée !", "success");
        			
        		$location.path('/admin/evaluations');
				
			});
        	promiseajout.error(function(data, status, headers, config) {
        		toaster.pop({
                    type: 'error',
                    title: 'Insertion ou modification impossible. ID evaluation existe déja !',
                    positionClass: 'toast-bottom-right',
                    showCloseButton: true
                });
        	});		
        	
			// Making the fields empty
			//				
			$scope.qualificatifs = {};
          $scope.edit = false;  
        }

      $scope.edition = function(){
        $scope.edit = true;
      }
      $scope.changeFormation= function(code){
    	//Récuperation des EC
//          var promise2= evaluationsFactory.findByCodeFormation(code);
//          promise2.success(function(data,statut){
//          	$scope.etats= data;
//          	console.log("\tEtats récupérés: ", data);
//          })
//          .error(function(data,statut){
//        	  console.log("impossible de recuperer la liste des etats");
//          });
      }
      // valide le formulaire d'édition d'une evaluation
      
      // TODO coder une fonction submit permettant de modifier une evaluation
		// et rediriger vers /admin/evaluations


   // annule l'édition
      $scope.cancel = function(){
        if(!$scope.evaluations.idevaluation){
          $location.path('/admin/evaluations');
        } else {
        	$location.path('/admin/evaluations');
          var e = evaluationFactory.get($routeParams.id);
          $scope.evaluations = JSON.parse(JSON.stringify(e));
          $scope.edit = false;
        }
      } 

    }]
  );
}).call(this);

