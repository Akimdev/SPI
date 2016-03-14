(function() {
  'use strict';

  var app = angular.module('app.ue', []);

  app.factory('ueFactory', function($http){      
    return {
    	all: function() { 
         	  return $http.get("http://localhost:8090/UEs")
        },
        get: function(uniteEnseignementPK) {
        	return $http.post("http://localhost:8090/getUE", uniteEnseignementPK);
        },
        set: function(ue){
        	var id = ue.uniteEnseignementPK;
        	if(id){
        		return $http({
                    method: 'POST',
                    url: 'http://localhost:8090/modifUE',
                    data: ue,
                    headers: { 'Content-Type': 'application/json' }
                });
        	}
        	else{
        		return $http({
                    method: 'POST',
                    url: 'http://localhost:8090/ajoutUE',
                    data: ue,
                    headers: { 'Content-Type': 'application/json' }
                });
        	}
        },
         delete:function(ue){
        	 console.log("supprimer UE ", ue.uniteEnseignementPK);
        	 return $http({
                 method: 'POST',
                 url: 'http://localhost:8090/supprimerUE',
                 data: ue.uniteEnseignementPK,
                 headers: { 'Content-Type': 'application/json' }
             });
         }
    };
  });

  

  app.controller('UEController', 
    ['$scope', '$location', 'ueFactory',
    function($scope, $location, ueFactory){
    	var promise= ueFactory.all();
        promise.success(function(data){
      	  $scope.ue = data ;
        })
        .error(function(data){
      	  console.log("impossible de recuperer la liste des UEs");
        });
        $scope.ajoutUE = function(){
            $location.path('/admin/ue/nouveau'); 
         }
        
        $scope.edit = function(codeFormation, codeUe){
        	$location.path('/admin/ue/' + codeFormation + "/" + codeUe);
        }
    }]
  );

  app.controller('UEDetailsController', 
    ['$scope', '$routeParams', '$location', 'ueFactory',
    function($scope, $routeParams, $location, ueFactory){      
    	
    	if($routeParams.id == "nouveau"){
            $scope.ue= { };
            $scope.edit= true;    
        } else { // sinon on edite une formation existante
            var f = ueFactory.get($routeParams.id);
            var uniteEnseignementPK = {
            		"codeFormation" : $routeParams.codeFormation,
            		"codeUe" : $routeParams.codeUe
            }
            var promisesFactory = ueFactory.get(uniteEnseignementPK);
         	promisesFactory.success(function(data) {
         		console.log(data);
         		$scope.ue = data;   
          });
        }      
          
        $scope.edition = function(){
            $scope.edit = true;
        }

        $scope.submit = function(){
            $scope.edit = false;  
        }
    }]
  );
})();
