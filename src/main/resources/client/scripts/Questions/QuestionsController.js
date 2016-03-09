/*
 * @author ZOuhair
 */
(function() {
  'use strict';

  var app = angular.module('app.questions', []);

  app.factory('questionsFactory', function($http, $window){
            
    return {
      // renvoi la liste de tous les questions
      all: function() { 
    	  return $http.get('http://localhost:8090/questions');
      },
      // renvoi la question avec le code demandé
      get: function(code) { 
    	  return $http.get('http://localhost:8090/getQuestionById/' + code);    
      },
      set: function(question) {	
    	  return $http.post('http://localhost:8090/updateQuestion', question);
      },
      add: function(question) {
    	  console.log(question);
    	  return $http.post('http://localhost:8090/addQuestion', question)
      },
      delete: function(idQuestion) { 
    	  return $http.get('http://localhost:8090/deleteQuestionById-' + idQuestion);
      },
      getQualificatif: function(idQuestion){
    	  return $http.get('http://localhost:8090/getQualificatif/' + idQuestion);
      }
    };
  });
  
  
  app.controller('QuestionsController', 
    ['$scope', '$location','$http','$filter', 'questionsFactory',
    function($scope, $location,$http,$filter, questionsFactory){
      // la liste globale des questions
    	$scope.refresh = function (){
    		 var promiseQuestion = questionsFactory.all();          
    	      promiseQuestion.success(function(data) {
    	    	  console.log(data);
    	          $scope.questions = data;
    	      });
    	}
     
      // Crée la page permettant d'ajouter une question
      $scope.ajoutQuestion = function(){
        $location.path('/admin/question/nouveau'); 
      }

      // affiche les détails d'une question
      $scope.edit = function(question){
        $location.path('/admin/question/' + question.idQuestion);
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
			    	  var promisessuppression  = questionsFactory.delete(question.idQuestion);
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

  app.controller('QuestionDetailsController', 
    ['$scope', '$routeParams','$http', '$location','$filter', 'questionsFactory', 'qualificatifsFactory', 'toaster',
    function($scope, $routeParams, $http, $location,$filter, questionsFactory, qualificatifsFactory, toaster){      
      $scope.edit= false;    
      
      // si creation d'une nouvelle question
      if($routeParams.id == "nouveau"){
        $scope.question= { };
        $scope.edit= true;
 		var promiseQualificatifs = qualificatifsFactory.all();
 		promiseQualificatifs.success(function(data) {   
 			$scope.qualificatifs = data;
 			$scope.selectedOption = data[0];
 		});
	 } else { // sinon on edite une question existante
        var promisesFactory = questionsFactory.get($routeParams.id);
     	promisesFactory.success(function(data) {
     		$scope.isVisible = true;
     		$scope.question = data;   console.log("question: ", $scope.question);
     		var promiseQualificatifs = qualificatifsFactory.all();
     		promiseQualificatifs.success(function(data) {   
     			var promiseQualif = questionsFactory.getQualificatif($routeParams.id);
         		promiseQualif.success(function(result){
         			$scope.qualif = result;
	     			$scope.qualificatifs = data;
	     			$scope.selectedOption = result;
         		});
     		});
     		
     	});
     	
      }
      
      $scope.edition = function(){
    	  var promisessuppression = questionsFactory.set($scope.question);    	  
    	  questionsFactory.get($scope.question);
    	  
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
        	var promisesajout = questionsFactory.add(quesQual);
        	promisesajout.success(function(data, status, headers, config) {
        		if($routeParams.id === "nouveau") 
        			swal("Félicitation!", "La nouvelle question est ajoutée!", "success");
        		else
        			swal("Félicitation!", "La question est modifiée !", "success");
        			
        		$location.path('/admin/questions');
				
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
		// et rediriger vers /admin/questions


   // annule l'édition
      $scope.cancel = function(){
        if($routeParams.id == "nouveau"){
          $location.path('/admin/questions');
        } else {
        	$location.path('/admin/question/' + $routeParams.id);
          //var e = questionFactory.get($routeParams.id);
          $scope.edit = false;
        }
      } 

    }]
  );
}).call(this);

