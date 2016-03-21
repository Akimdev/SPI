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
	      }
	  };
   }]);

  app.controller('ConfigEvaluationController', ['$scope', '$stateParams', '$location', '$filter', 'configEvalFactory',
		    function($scope, $stateParams, $location,$filter, configEvalFactory){ 
	  
	  	$scope.rubriques = [];
	  
	  	var promise = configEvalFactory.get($stateParams.id);
	  	promise.success(function(data){
	  		console.log(data);
	  		$scope.eval = data;
	  	});
	  	
		$scope.cancel = function(){
			history.back();
		}
		
		$scope.addRubrique = function(){
			var rubrique = {
					'idRubrique' : 1,
					'designation' : "designation",
					'questions' : []
			}
			$scope.rubriques.push(rubrique); 
		}
		
		$scope.addQuestion = function(id){
			var rubrique = $scope.getRubriqueById(id);
			if(rubrique[0] != null)
				rubrique[0].questions.push({'idQuestion' : 5, 'intitule': "Some question", qualificatif : {"minimal" : "Faible", "maximal" : "Excellent"}});
		}
		
		$scope.getRubriqueById = function(id){
			for(var i = 0; i < $scope.rubriques.length; i++){
				if($scope.rubriques[i].idRubrique === id)
					return $scope.rubriques;
			}
			return null;
		}
		
		$scope.removeQuestion = function(idRubrique, idQuestion){	
			var indexQues = -1;		
			var comArr = $scope.getRubriqueById(idRubrique)[0].questions;
			for( var i = 0; i < comArr.length; i++ ) {
				console.log("current : ", comArr[i].idQuestion);
				if( comArr[i].idQuestion === idQuestion ) {
					indexQues = i;
					break;
				}
			}
			if( indexQues === -1 ) {
				alert( "Something gone wrong" );
			}
			
			var indexRub = -1;
			for(var i = 0; i < $scope.rubriques.length; i++){
				if($scope.rubriques[i].idRubrique === idRubrique)
					indexRub = i;
			}

			$scope.rubriques[indexRub].questions.splice(indexQues, 1);		
		};
		
		$scope.removeRubrique = function(idRubrique){	
			var indexRub = -1;		
			var comArr = $scope.getRubriqueById(idRubrique);
			for( var i = 0; i < comArr.length; i++ ) {
				console.log("current : ", comArr[i].idRubrique);
				if( comArr[i].idRubrique === idRubrique ) {
					indexRub = i;
					break;
				}
			}
			if( indexRub === -1 ) {
				alert( "Something gone wrong" );
			}

			$scope.rubriques.splice(indexRub, 1);		
		};
  }]);

 
}).call(this);
