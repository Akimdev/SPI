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
    	  return $http.post('http://localhost:8090/addQuestion', question)
      },
      delete: function(idQuestion) { 
    	  return $http.get('http://localhost:8090/supprimerQuestionBis?idQuestion=' + idQuestion);
      }
    };
  });
  
  app.factory('qualificatifsFactory', function($http, $window){
	  return {
		  all: function() {
			  return $http.get('http://localhost:8090/listerQualificatif');
		  }
	  }
  })
  
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
      
      // ajouter une question
      $scope.ajouter = function(question){
    	  var promiseAjout = questionsFactory.add(question);
    	  promiseAjout.success(function(data, status, headers, config) {
    		  $scope.refresh();
    	  });
    	  promiseAjout.error(function(data, status, headers, config) {
			alert( "failure message: " + JSON.stringify({data: data}));
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
 			$scope.qualificatifs = {
			    availableOptions: data,
			    selectedOption:  data[0]
		    };
 		});
      } else { // sinon on edite une question existante
        var f = questionsFactory.get($routeParams.id);
        var promisesFactory = questionsFactory.get($routeParams.id);
     	promisesFactory.success(function(data) {
     		$scope.isVisible = true;
     		$scope.question = data;   
     		var promiseQualificatifs = qualificatifsFactory.all();
     		promiseQualificatifs.success(function(data) {   			
     			$scope.qualificatifs = {
				    availableOptions: data,
				    selectedOption:  $scope.question.idQualificatif
			    };
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
        				idQualificatif : $scope.qualificatifs.selectedOption.idQualificatif
        			},
        			question : $scope.question
        	}
        	console.log(quesQual);
        	var promisesajout = questionsFactory.set(quesQual);
        	promisesajout.success(function(data, status, headers, config) {
        		swal("Félicitation!", "La nouvelle question est ajoutée!", "success");
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
    	  $location.path('/admin/questions');
//        if($routeParams.id == "nouveau"){
//          $location.path('/admin/questions');
//        } else {
//          $location.path('/admin/questions');
//        }
      } 

    }]
  );
}).call(this);

