(function() {
  'use strict';
  angular.module('app.controllers', ['app.auth']).controller('AppCtrl', [
    '$scope', '$location', 'AuthService', function($scope, $location, AuthService) {
      $scope.isSpecificPage = function() {
        var path;
        path = $location.path();
        return _.contains(['/404', '/pages/500', '/pages/login', '/pages/signin', '/pages/signin1', '/pages/signin2', '/pages/signup', '/pages/signup1', '/pages/signup2', '/pages/forgot', '/pages/lock-screen'], path);
      };
      $scope.deconnexion = function() {
    	  AuthService.deconnexion().success(function() {
    		  $location.path('/pages/signin.html');
    	  });
    	  
      };
      
      $scope.login = function() {
    	  $location.path('/pages/signin');
      };
      
      return $scope.main = {
        brand: 'Square',
        name: 'Lisa Doe'
      };
    }
  ]).controller('NavCtrl', [
    '$scope','$http', 'taskStorage', 'filterFilter', function($scope, $http, taskStorage, filterFilter) {
    	
   
      var tasks;
      tasks = $scope.tasks = taskStorage.get();
      $scope.taskRemainingCount = filterFilter(tasks, {
        completed: false
      }).length;
      return $scope.$on('taskRemaining:changed', function(event, count) {
        return $scope.taskRemainingCount = count;
      });
    }
  ]).factory('informationFactory',['$http',function($http){
	  return{
		  getEtudiants:function(){
			  return $http.get("http://localhost:8090/nombreEtudiants");
		  },
		  getFormations:function(){
			  return $http.get("http://localhost:8090/nombreFormations");
		  },
		  getUEs:function(){
			  return $http.get("http://localhost:8090/nombreUEs");
		  },
		  getQuestions:function(){
			  return $http.get("http://localhost:8090/lengthQuestion");
		  },
		  getEnseignants:function(){
			  return $http.get("http://localhost:8090/nombreEnseignants");
		  }
	  };
  }
  ]).controller('DashboardCtrl', ['$scope','informationFactory',function($scope, informationFactory) {
	  var promise= informationFactory.getEtudiants();
      promise.success(function(data){
    	  $scope.nombreEtudiants = data ;
      })
      .error(function(data){
    	  console.log("impossible de recuperer le nombre d'étudiants");
      });
      var promise= informationFactory.getFormations();
      promise.success(function(data){
    	  $scope.nombreFormations = data ;
      })
      .error(function(data){
    	  console.log("impossible de recuperer le nombre de formations");
      });
      
      var promise= informationFactory.getUEs();
      promise.success(function(data){
    	  $scope.nombreUEs = data ;
      })
      .error(function(data){
    	  console.log("impossible de recuperer le nombre des unités d'enseignement");
      });
      
      var promise= informationFactory.getQuestions();
      promise.success(function(data){
    	  $scope.nombreQuestions = data ;
      })
      .error(function(data){
    	  console.log("impossible de recuperer le nombre de questions");
      });
      
      var promise= informationFactory.getEnseignants();
      promise.success(function(data){
    	  $scope.nombreEnseignants = data ;
      })
      .error(function(data){
    	  console.log("impossible de recuperer le nombre d'enseignants");
      });
  }]);

}).call(this);

