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
    	  return $http.get('http://localhost:8090/listerQuestions');
      },
      // renvoi la question avec le code demandé
      get: function(code) { 
    	  return $http.get('http://localhost:8090/question/' + code);    
      },
      set: function(question) {	
    	  return $http.post('http://localhost:8090/modifierQuestion', question);
      },
      add: function(question) {
    	  return $http.post('http://localhost:8090/ajouterQuestion', question)
      },
      delete: function(idQuestion) { 
    	  return $http.get('http://localhost:8090/supprimerQuestionBis?idQuestion=' + idQuestion);
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
    	  var promisessuppression  = questionsFactory.delete(question.idQuestion);
    	  promisessuppression.success(function(data, status, headers, config) {
			$scope.refresh();
    	  });
    	  promisessuppression.error(function(data, status, headers, config) {
			alert( "failure message: " + JSON.stringify({data: data}));
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
    ['$scope', '$routeParams','$http', '$location','$filter', 'questionsFactory',
    function($scope, $routeParams, $http, $location,$filter, questionsFactory){      
      $scope.edit= false;    

      // si creation d'une nouvelle question
      if($routeParams.id == "nouveau"){
        $scope.question= { };
        $scope.edit= true;
        $scope.ajout= true;
      } else { // sinon on edite une question existante
        var f = questionsFactory.get($routeParams.id);
        var promisesFactory = questionsFactory.get($routeParams.id);
     	promisesFactory.success(function(data) {
     		$scope.question = data;
     		console.log("question: ",$scope.question);
     	});
     	$scope.ajout= false;
      }
      
      $scope.edition = function(){
    	  var promisessuppression = questionsFactory.set($scope.question);    	  
    	  questionsFactory.get($scope.question);
          $scope.edit = true;
        }

        $scope.submit = function(){
        	var promisesajout = questionsFactory.set($scope.question);
        	promisesajout.success(function(data, status, headers, config) {
        		$location.path('/admin/questions');
				
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

      // valide le formulaire d'édition d'une question
      
      // TODO coder une fonction submit permettant de modifier une question
		// et rediriger vers /admin/questions


   // annule l'édition
      $scope.cancel = function(){
        if(!$scope.questions.idQuestion){
          $location.path('/admin/questions');
        } else {
        	$location.path('/admin/questions');
          var e = questionFactory.get($routeParams.id);
          $scope.questions = JSON.parse(JSON.stringify(e));
          $scope.edit = false;
        }
      } 

    }]
  );
}).call(this);

