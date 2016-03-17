// @Author Zouhair

'use strict';
app.factory('informationFactory',['$http',function($http){
	  return{
		  
		  getRubriques:function(){
			  return $http.get("http://localhost:8090/nombreRubriques");
		  },
		  getEtudiants:function(){
			  return $http.get("http://localhost:8090/nombreEtudiants");
		  },
		  getEnseignants:function(){
			  return $http.get("http://localhost:8090/nombreEnseignants");
		  },
		  getQuestions:function(){
			  return $http.get("http://localhost:8090/nombreQuestions");
		  },
		  getEvaluations:function(){
			  return $http.get("http://localhost:8090/nombreEvaluations");
		  },
		  getPromotions:function(){
			  return $http.get("http://localhost:8090/nombrePromotions");
		  },
		  getQualificatifs:function(){
			  return $http.get("http://localhost:8090/nombreQualificatifs");
		  },
	  };
  }]);
app.controller('DashboardController', function($rootScope, $scope, informationFactory, $http, $timeout) {
	var promise= informationFactory.getEtudiants();
    promise.success(function(data){
    	console.log(data);
  	  $scope.nombreEtudiants = data ;
    })
    .error(function(data){
  	  console.log("impossible de recuperer le nombre d'étudiants");
    });
    
   /* var promise= informationFactory.getUEs();
    promise.success(function(data){
  	  $scope.nombreUEs = data ;
    })
    .error(function(data){
  	  console.log("impossible de recuperer le nombre des unités d'enseignement");
    });*/
    
    var promise= informationFactory.getQuestions();
    promise.success(function(data){
    console.log(data);
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
    
    var promise= informationFactory.getEvaluations();
    promise.success(function(data){
  	  $scope.nombreEvaluations = data ;
    })
    .error(function(data){
  	  console.log("impossible de recuperer le nombre d'evaluations");
    });
    
    var promise= informationFactory.getRubriques();
    promise.success(function(data){
  	  $scope.nombreRubriques = data ;
    })
    .error(function(data){
  	  console.log("impossible de recuperer le nombre des rubriques");
    });
    
    var promise= informationFactory.getPromotions();
    promise.success(function(data){
  	  $scope.nombrePromotions = data ;
    })
    .error(function(data){
  	  console.log("impossible de recuperer le nombre de promotions");
    });
    var promise= informationFactory.getQualificatifs();
    promise.success(function(data){
  	  $scope.nombreQualificatifs = data ;
    })
    .error(function(data){
  	  console.log("impossible de recuperer le nombre de qualificatif");
    });
    
    $scope.$on('$viewContentLoaded', function() {   
        // initialize core components
        Metronic.initAjax();
    });
});