(function() {
  'use strict';

  var app = angular.module('app.formations', []);

  app.factory('formationsFactory', function($http, $window){
            
    return {
      // renvoi la liste de tous les enseignants
      all: function() { 
    	  return $http.get('http://localhost:8090/formations');
      },
      // renvoi l'enseignant avec le code demandé
      get: function(code) { 
    	  return $http.get('http://localhost:8090/formation/getFormation/' + code);    
      },
      
      addFormation: function(formation) {	
    	  return $http.post('http://localhost:8090/formation/addFormation', formation);
      },
      
      updateFormation: function(formation){
    	  return $http.post('http://localhost:8090/formation/updateFormation', formation);
      },
      
      delete: function(codeFormation) { 
    	  return $http.get('http://localhost:8090/formation/deleteFormation/' + codeFormation);
      },
      
      getDomain:function(){
    	  return $http.get('http://localhost:8090/getDomaine/DIPLOME');
      },
      
      getDomainDiplome:function(){
    	  return $http.get('http://localhost:8090/getDomaine/OUI_NON');
      },
      
      getUEsByCodeFormation(codeFormation) {
    	  return $http.get("http://localhost:8090/getUEsByFormation-"+codeFormation);
      },
      
      getECsByCodeUe(uePK){
    	  return $http.post("http://localhost:8090/getECByUE",uePK);
      }
    };
  });

  app.controller('FormationsController', 
    ['$scope', '$location','$http','$filter', 'formationsFactory',
    function($scope, $location,$http,$filter, formationsFactory){
      // la liste globale des formations
    	$scope.ue = false;
    	$scope.ec = false;
    	$scope.refresh = function (){
    		var init = null;
    		 var promise = formationsFactory.all();          
    	      promise.success(function(data) {
    	    	  $scope.formations = data;
    		      $scope.searchKeywords = '';
    		      $scope.filteredFormation = [];
    		      $scope.row = '';
    		      $scope.select = function(page) {
    		        var end, start;
    		        start = (page - 1) * $scope.numPerPage;
    		        end = start + $scope.numPerPage;
    		        return $scope.currentPageFormation = $scope.filteredFormation.slice(start, end);
    		      };
    		      $scope.onFilterChange = function() {
    		        $scope.select(1);
    		        $scope.currentPage = 1;
    		        return $scope.row = '';
    		      };
    		      $scope.onNumPerPageChange = function() {
    		        $scope.select(1);
    		        return $scope.currentPage = 1;
    		      };
    		      $scope.onOrderChange = function() {
    		        $scope.select(1);
    		        return $scope.currentPage = 1;
    		      };
    		      $scope.search = function() {
    		        $scope.filteredFormation = $filter('filter')($scope.formations, $scope.searchKeywords);
    		        return $scope.onFilterChange();
    		      };
    		      $scope.order = function(rowName) {
    		        if ($scope.row === rowName) {
    		          return;
    		        }
    		        $scope.row = rowName;
    		        $scope.filteredFormation = $filter('orderBy')($scope.formations, rowName);
    		        return $scope.onOrderChange();
    		      };
    		      $scope.numPerPageOpt = [3, 5, 10, 20];
    		      $scope.numPerPage = $scope.numPerPageOpt[2];
    		      $scope.currentPage = 1;
    		      $scope.currentPageFormation = [];
    		      init = function() {
    		        $scope.search();
    		        return $scope.select($scope.currentPage);
    		      };
    		      return init();
    		  }
    		)
        
    		.error(function(data) {
    			 $scope.error = 'unable to get the poneys';
    		  }
    		);
    	      
    	   
    	}
    	//Affiche les informations sur les unités d'enseignement concernant une formation
    	$scope.afficheUE = function(codeFormation){
	    	 var promiseUE = formationsFactory.getUEsByCodeFormation(codeFormation);
	    	    promiseUE.success(function(data){
	    	    	$scope.ues = data;
	    	    	$scope.ue = true;
	    	    })
	    	    .error(function(data){
	    	    	$scope.error = 'unable to get the poneys';
	    	    });
	    	}   
	    	
    	//Affiche les informations sur les elements constitutifs concernant une formation
	    	$scope.afficheEC = function(uePK){
	    		console.log(uePK);
		    	 var promiseEC = formationsFactory.getECsByCodeUe(uePK);
		    	    promiseEC.success(function(data){
		    	    	$scope.ecs = data;
		    	    	$scope.ec = true;
		    	    	})
		    	    .error(function(data){
		    	    	$scope.error = 'unable to get the poneys';
		    	    });
		    	} 
      // Crée la page permettant d'ajouter une formation
      $scope.ajoutFormation = function(){
        $location.path("/formation/nouveau"); 
      }

      // affiche les détails d'une formation
      $scope.edit = function(formation){
        $location.path("/formation/"+ formation.codeFormation);
      }

      // supprime une formation
      $scope.supprime = function(formation){
    	  swal({   
			  title: "Voulez-vous vraiment supprimer cette formation ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "Oui",  
			  cancelButtonText: "Non",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
					  var promisessuppression  = formationsFactory.delete(formation.codeFormation);
			    	  promisessuppression.success(function(data, status, headers, config) {
						$scope.refresh();
						swal("Supprimé!", "la formation est supprimée", "success");
					});
			    	  promisessuppression.error(function(data, status, headers, config) {
			    		  swal("Erreur!", "vous ne pouvez pas supprimer cette formation", "error");
			  		});	
					  } else {     
						  swal("Ignorer", "", "error");
						  }
				  });  
      }
      $scope.refresh();
    }]
  );

  app.controller('FormationDetailsController', 
    ['$scope', '$stateParams','$http', '$location','$filter', 'formationsFactory',
    function($scope, $stateParams, $http, $location,$filter, formationsFactory){  
    	$scope.edit= false;    
      // si creation d'une nouvelle formation
      if($stateParams.id == "nouveau"){
        $scope.formation= { };
        var promiseDomaines = formationsFactory.getDomain();
	    promiseDomaines.success(function(data) {
	      $scope.domaines = data;
	    });
	    
	    var promiseDomainesDiplome = formationsFactory.getDomainDiplome();
	    promiseDomainesDiplome.success(function(data) {
	      $scope.domainesDiplome = data;
	    });
	    
        $scope.edit= true;    
      } else { // sinon on edite une formation existante
        var f = formationsFactory.get($stateParams.id);
        var promisesFactory = formationsFactory.get($stateParams.id);
     	promisesFactory.success(function(data) {
     		$scope.formation = data;   
				$scope.formation.debutAccreditation = $filter('date')(data.debutAccreditation, "dd/MM/yyyy");
				$scope.formation.finAccreditation = $filter('date')(data.finAccreditation, "dd/MM/yyyy");		
     	});
     	var promiseDomaines = formationsFactory.getDomain();
	    promiseDomaines.success(function(data) {
	      $scope.domaines = data;
	    });
	    
	    var promiseDomainesDiplome = formationsFactory.getDomainDiplome();
	    promiseDomainesDiplome.success(function(data) {
	      $scope.domainesDiplome = data;
	    });
      }      
      
      $scope.edition = function(){
    	  var promisessuppression = formationsFactory.addFormation($scope.formation);    	  
    	  formationsFactory.get($scope.formation);
          $scope.edit = true;
        }
        $scope.submit = function(){
        	if($scope.formation.debutAccreditation && $scope.formation.finAccreditation){
            	var date = $scope.formation.debutAccreditation.split('/');
            	$scope.formation.debutAccreditation = new Date(date[1] + '-' + date[0] + '-' + date[2]);
            	var date2 = $scope.formation.finAccreditation.split('/');
            	$scope.formation.finAccreditation = new Date(date2[1] + '-' + date2[0] + '-' + date2[2]);
        	}
        	$scope.formation.diplome = $scope.diplomeSelected;
    	    $scope.formation.doubleDiplome = $scope.doubleDiplomeSelected;
    	    console.log($scope.diplomeSelected);
    	    console.log("objet en question",$scope.formation);
    	    if($stateParams.id == "nouveau"){
        	var promisesajout = formationsFactory.addFormation($scope.formation);
        	promisesajout.success(function(data, status, headers, config) {
        		$location.path('/formations');
				
			});
        	promisesajout.error(function(data, status, headers, config) {
				alert( "failure message: " + JSON.stringify({data: data}));
			});		
    	    }
    	    else{
    	    	var promiseUpdate = formationsFactory.updateFormation($scope.formation);
    	    	promiseUpdate.success(function(data, status, headers, config) {
            		$location.path('/formations');
    				
    			})
            	.error(function(data, status, headers, config) {
    				alert( "failure message: " + JSON.stringify({data: data}));
    			});	
    	    }
			// Making the fields empty
			//				
			$scope.formation = {};
          $scope.edit = false;    
      // TODO coder une fonction submit permettant de modifier une formation et rediriger vers /formations 
        }

      $scope.edition = function(){
        $scope.edit = true;
      }

   // annule l'édition
      $scope.cancel = function(){
        if(!$scope.formation.codeFormation){
          $location.path('/formations');
        } else {
        	$location.path('/formations');
         // var e = formationFactory.get($stateParams.id);
          //$scope.formation = JSON.parse(JSON.stringify(e));
          $scope.edit = false;
        }
      } 

    }]
  );
})();
