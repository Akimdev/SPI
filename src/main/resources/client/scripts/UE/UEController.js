(function() {
  'use strict';

  var app = angular.module('app.ue', []);

  app.factory('ueFactory', function($http){      
    return {
      // TODO
    	all: function() { 
         	  return $http.get("http://localhost:8090/UEs")
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
      // la liste globale des ue
      // TODO
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
    }]
  );

  app.controller('UEDetailsController', 
    ['$scope', '$routeParams', '$location', 'ueFactory',
    function($scope, $routeParams, $location, ueFactory){      
      // TODO
    }]
  );
})();
