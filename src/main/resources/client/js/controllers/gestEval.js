/*
* Author Ait Errami Abdelhakim

* Script de controle des elements constitutifs
*/

(function() {
  'use strict';

  var app = angular.module('app.gestEval', []);

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
  
  app.factory('gestEvalFactory', ['$http',function($http){
	  return {
		  get: function(code) { 
	    	  return $http.get('http://localhost:8090/findEvaluationById-' + code);    
	      }
	  };
   }]);

  app.controller('gestEvalController', 
		    ['$scope', '$routeParams', '$location', '$filter', 'gestEvalFactory',
		    function($scope, $routeParams, $location,$filter, gestEvalFactory){ 
		    	
  var promiseEvaluation = gestEvalFactory.get($routeParams.id);
  	  promiseEvaluation.success(function(data){
  		  $scope.evaluation=data;
  		  console.log(data);
  	  }).error(function(data){
  		  console.log("erreur recupération de l'evaluation");
  	  });
		
}]);
 
}).call(this);
