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

  app.controller('ConfigEvaluationController', ['$scope', '$routeParams', '$location', '$filter', 'configEvalFactory',
		    function($scope, $routeParams, $location,$filter, configEvalFactory){ 
	  
		$scope.cancel = function(){
			history.back();
		}
  }]);
 
}).call(this);
