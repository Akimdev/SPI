(function() {
  'use strict';
  var app = angular.module('app.rubriques',[]);
  
  app.factory('rubriqueFactory',function($http){
	  
	  return {
		  
		  all:function(){
			  return $http.get('')
		  },
		  
	  };
  
  })
  
  })();