/*
* Author Ait Errami Abdelhakim

* Script de controle des elements constitutifs
*/

(function() {
  'use strict';

  var app = angular.module('app.configEvaluation', []);

  Array.prototype.removeValue = function(name, value){
	   var array = $.map(this, function(v,i){
	      return v[name] === value ? null : v;
	   });
	   this.length = 0; // clear original array
	   this.push.apply(this, array); // push all elements except the one we
										// want to delete
	}
  
  Array.prototype.retourValue = function(name, value){
	   var array = $.map(this, function(v,i){
	      return v[name] === value ? v : null;
	   });
	   return array[0];
	}
  
  app.factory('configEvalFactory', ['$http',function($http){
	  return {
		  get: function(code) { 
	    	  return $http.get('http://localhost:8090/findEvaluationById-' + code);    
	      },
	      getRubriques: function(){
	    	  return $http.get('http://localhost:8090/rubriques');
	      },
	      getQuestions: function(){
	    	  return $http.get('http://localhost:8090/questions');
	      },
	      getRubrique: function(rubrique){
	    	  return $http.get('http://localhost:8090/rubrique/' + rubrique);
	      },
	      getQuestion: function(question){
	    	  return $http.get('http://localhost:8090/getQuestionById/' + question);
	      }
	  };
   }]);

  app.controller('ConfigEvaluationController', ['$scope', '$stateParams', '$location', '$filter', 'configEvalFactory',
		    function($scope, $stateParams, $location,$filter, configEvalFactory){ 
	  
	  	$scope.rubriques = [];
	  	$scope.rubriquesSelected = [];
	  	$scope.questionsOptions = [];
	  	$scope.questionsSelected = [];
	  	$scope.questions = [];
	  	$scope.currentRubrique = null;
	  	
	  	var promiseRub = configEvalFactory.getRubriques();
	  	promiseRub.success(function(data){
	  		$scope.rubriquesOptions = data;
	  	});
	  	
	  	var promiseQues = configEvalFactory.getQuestions();
	  	promiseQues.success(function(data){
	  		$scope.questionsOptions = data;
	  	});
	  	
	  	var promise = configEvalFactory.get($stateParams.id);
	  	promise.success(function(data){
	  		$scope.eval = data;
	  	});
	  	
		$scope.cancel = function(){
			history.back();
		}
		
		$scope.addRubrique = function(){
			var rubrique = $scope.rubriquesSelected;
			for(var rub in rubrique){
				if(rub != 'removeValue' && rub != 'retourValue'){
					var promiseRub = configEvalFactory.getRubrique(rub);
					promiseRub.success(function(data){
						$scope.rubriques.push(data); 
						$scope.rubriquesSelected = [];
					})
					.error(function(){
					})					
				}
			}
		}
		
		$scope.checkIfSelected = function(rubrique){
			var id = parseInt(rubrique);
			for(var i = 0; i < $scope.rubriques.length; i++){
				if($scope.rubriques[i].idRubrique === id){
					return true;					
				}
			}
			return false;
		}
		
		$scope.checkQuesIfSelected = function(question){
			if($scope.currentRubrique){
				if($scope.getRubriqueById($scope.currentRubrique).questions){
					var comArr = $scope.getRubriqueById($scope.currentRubrique).questions;
					var id = parseInt(question);
					for(var i = 0; i < comArr.length; i++){
						if(comArr[i].idQuestion === id){
							return true;					
						}
					}
					return false;
				}
				else 
					return false;
			}
			else return false;
		}
		
		$scope.addQuestion = function(id){
			var rubrique = $scope.getRubriqueById(id);
			$scope.currentRubrique = id;
		}
		
		$scope.getRubriqueById = function(id){
			for(var i = 0; i < $scope.rubriques.length; i++){
				if($scope.rubriques[i].idRubrique === id)
					return $scope.rubriques[i];
			}
			return null;
		}
		
		$scope.addQuestions = function(){
			var rubrique = $scope.getRubriqueById($scope.currentRubrique);
			if(!rubrique.questions)
				rubrique.questions = [];
			
			for(var ques in $scope.questionsSelected){
				if(ques != 'removeValue' && ques != 'retourValue'){
					var promiseQues = configEvalFactory.getQuestion(ques);
					promiseQues.success(function(data){
						rubrique.questions.push(data);
					});
				}
			}
			$scope.questionsSelected = [];
		};
		
		$scope.removeQuestion = function(idRubrique, idQuestion){	
			var indexQues = -1;		
			var comArr = $scope.getRubriqueById(idRubrique).questions;
			for( var i = 0; i < comArr.length; i++ ) {
				if( comArr[i].idQuestion === idQuestion ) {
					indexQues = i;
					break;
				}
			}
			if( indexQues === -1 ) {
				alert( "Something gone wrong" );
			}
			else{
				var indexRub = -1;
				for(var i = 0; i < $scope.rubriques.length; i++){
					if($scope.rubriques[i].idRubrique === idRubrique)
						indexRub = i;
				}
				$scope.rubriques[indexRub].questions.splice(indexQues, 1);		
			}
		};
		
		$scope.removeRubrique = function(idRubrique){	
			var indexRub = -1;		
			for( var i = 0; i < $scope.rubriques.length; i++ ) {
				if( $scope.rubriques[i].idRubrique === idRubrique ) {
					indexRub = i;
					break;
				}
			}
			if( indexRub === -1 ) {
				alert( "Something gone wrong" );
			} 
			else{
				$scope.rubriques.splice(indexRub, 1);		
				$scope.currentRubrique = null;
			}
		};
		
		$scope.submit = function(){
			console.log($scope.rubriques);
		}
		
  }]);

 
}).call(this);
