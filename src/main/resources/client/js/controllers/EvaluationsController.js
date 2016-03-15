/*
 * @author LAKRAA
 */
(function() {
  'use strict';

  var app = angular.module('app.evaluations', []);

  app.factory('evaluationsFactory', function($http, $window){
            
    return {
      // renvoi la liste de tous les questions
      all: function() { 
    	  return $http.get('http://localhost:8090/evaluations');
      },
      // renvoi la question avec le code demandé
      get: function(code) { 
    	 // return $http.get('http://localhost:8090/getQuestionById/' + code);    
      },
      set: function(question) {	
    	 // return $http.post('http://localhost:8090/updateQuestion', question);
      },
      add: function(question) {
    	  //return $http.post('http://localhost:8090/addQuestion', question)
      },
      delete: function(idQuestion) { 
    	  //return $http.get('http://localhost:8090/deleteQuestionById-' + idQuestion);
      },
      getQualificatif: function(idQuestion){
    	  //return $http.get('http://localhost:8090/getQualificatif/' + idQuestion);
      },
      getDomain : function(){
    	  return $http.get("http://localhost:8090/domaines/search/findByRvDomain?rvDomain=ETAT-EVALUATION");
      }
    };
  });
    
  app.controller('EvaluationsController', 
    ['$scope', '$location','$http','$filter', 'evaluationsFactory',
    function($scope, $location,$http,$filter, evaluationsFactory){
      // la liste globale des questions
    	$scope.refresh = function (){
    		 var promiseEvaluation = evaluationsFactory.all();          
    		 promiseEvaluation.success(function(data) {
    	    	  console.log(data);
    	          $scope.evaluations = data;
    	      });
    	}
     
      // Crée la page permettant d'ajouter une question
      $scope.ajoutEvaluation = function(){
        $location.path('/evaluations/nouveau'); 
      }

      // affiche les détails d'une question
      $scope.edit = function(question){
        //$location.path('/question/' + question.idQuestion);
      }

      // supprime une question
      $scope.supprime = function(question){

    	  swal({   
			  title: "Voulez-vous vraiment supprimer cette question ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "Oui, je veux le supprimer!",  
			  cancelButtonText: "Non, ignorer!",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
			    	  var promisessuppression  = evaluationsFactory.delete(question.idQuestion);
			    	  promisessuppression.success(function(data, status, headers, config) {
			  			$scope.refresh();
						swal("Supprimé!", "la question est supprimée", "success");
			      	  });
			    	  promisessuppression.error(function(data, status, headers, config) {
			    		  swal("Erreur!", "vous pouvez pas supprimer cette question", "error");
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
    ['$scope', '$stateParams','$http', '$location','$filter', 'evaluationsFactory', 'qualificatifsFactory', 'toaster',
    function($scope, $stateParams, $http, $location,$filter, evaluationsFactory, qualificatifsFactory, toaster){      
      $scope.edit= false;    
      
      // si creation d'une nouvelle question
      if($stateParams.id == "nouveau"){
        $scope.question= { };
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
	 } else { // sinon on edite une question existante
        var promisesFactory = evaluationsFactory.get($stateParams.id);
     	promisesFactory.success(function(data) {
     		$scope.isVisible = true;
     		$scope.question = data;   console.log("question: ", $scope.question);
     		var promiseQualificatifs = qualificatifsFactory.all();
     		promiseQualificatifs.success(function(data) {   
     			var promiseQualif = evaluationsFactory.getQualificatif($stateParams.id);
         		promiseQualif.success(function(result){
     			$scope.qualificatifs = data;
     			$scope.selectedOption = result;
         		});
     		});
     		
     	});
     	
      }
      
      $scope.edition = function(){
    	  var promisessuppression = evaluationsFactory.set($scope.question);    	  
    	  evaluationsFactory.get($scope.question);
    	  
          $scope.edit = true;
        }

        $scope.submit = function(){
        	var quesQual = {
        			qualificatif : {
        				idQualificatif : $scope.qualificatif
        			},
        			question : $scope.question
        	}
        	console.log(quesQual);
        	var promisesajout = evaluationsFactory.add(quesQual);
        	promisesajout.success(function(data, status, headers, config) {
        		if($stateParams.id === "nouveau") 
        			swal("Félicitation!", "La nouvelle question est ajoutée!", "success");
        		else
        			swal("Félicitation!", "La question est modifiée !", "success");
        			
        		$location.path('/evaluations');
				
			});
        	promisesajout.error(function(data, status, headers, config) {
        		toaster.pop({
                    type: 'error',
                    title: 'Insertion ou modification impossible. ID Question existe déja !',
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

      // valide le formulaire d'édition d'une question
      
      // TODO coder une fonction submit permettant de modifier une question
		// et rediriger vers /questions


   // annule l'édition
      $scope.cancel = function(){
        if(!$scope.questions.idQuestion){
          $location.path('/evaluations');
        } else {
        	$location.path('/evaluations');
          var e = questionFactory.get($stateParams.id);
          $scope.questions = JSON.parse(JSON.stringify(e));
          $scope.edit = false;
        }
      } 

    }]
  );
}).call(this);

