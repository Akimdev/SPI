var edit = false;
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
        getDomain : function(){
      	  return $http.get("http://localhost:8090/getDomaine/SEMESTRE");
        },
        update: function(ue){
			return $http.post('http://localhost:8090/updateUE', ue);
		},
		add: function(ue){
			return $http.post('http://localhost:8090/addUE', ue);
		},
	    delete:function(uniteEnseignementPK){
	    	return $http.post('http://localhost:8090/deleteUE', uniteEnseignementPK);;
	    },
	    getEc : function(elementConstitutifPK) {
	    	return $http.post("http://localhost:8090/elementConstitutif/findByUE", elementConstitutifPK);
	    },
	    getAllFormations : function(){
	    	return $http.get("http://localhost:8090/formations");
	    },
	    getAllEnseignants : function(){
	    	return $http.get("http://localhost:8090/ens");
	    }
    };
  });

  

  app.controller('UEController', 
    ['$scope', '$filter', '$location', 'ueFactory', 
    function($scope, $filter, $location, ueFactory){
    	var init;
        $scope.refresh = function(){
        	var promise= ueFactory.all();
        	promise.success(function(data) {
        		$scope.ue = data ;
    		      $scope.searchKeywords = '';
    		      $scope.filteredUE = [];
    		      $scope.row = '';
    		      $scope.select = function(page) {
    		        var end, start;
    		        start = (page - 1) * $scope.numPerPage;
    		        end = start + $scope.numPerPage;
    		        return $scope.currentPageUE = $scope.filteredUE.slice(start, end);
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
    		        $scope.filteredUE = $filter('filter')($scope.ue, $scope.searchKeywords);
    		        return $scope.onFilterChange();
    		      };
    		      $scope.order = function(rowName) {
    		        if ($scope.row === rowName) {
    		          return;
    		        }
    		        $scope.row = rowName;
    		        $scope.filteredUE = $filter('orderBy')($scope.ue, rowName);
    		        return $scope.onOrderChange();
    		      };
    		      $scope.numPerPageOpt = [3, 5, 10, 20];
    		      $scope.numPerPage = $scope.numPerPageOpt[2];
    		      $scope.currentPage = 1;
    		      $scope.currentPageUE = [];
    		      init = function() {
    		        $scope.search();
    		        return $scope.select($scope.currentPage);
    		      };
    		      return init();
        	});
        }
        
        $scope.ajoutUE = function(){
        	$scope.ajout = true;
            $location.path('/ue/nouveau/nouveau'); 
         }
        
        $scope.afficher = function(ue){
        	edit = false;
        	$location.path('/ue/' + ue.uniteEnseignementPK.codeFormation + "/" + ue.uniteEnseignementPK.codeUe);
        }
        
        $scope.modifier = function(ue){
        	edit = true;
        	$location.path('/ue/' + ue.uniteEnseignementPK.codeFormation + "/" + ue.uniteEnseignementPK.codeUe);
        	
        }
        
        $scope.ajoutUe = function(){
        	edit = true;
        	$scope.ajout = true;
        	$location.path('/ue/nouveau/nouveau');

        }
        
        $scope.supprime = function(uniteEnseignementPK){
        	swal({   
  			  title: "Voulez-vous vraiment supprimer cette unité d'enseignement ?",      
  			  type: "warning",   
  			  showCancelButton: true,   
  			  confirmButtonColor: "#DD6B55",   
  			  confirmButtonText: "Oui",  
  			  cancelButtonText: "Non",   
  			  closeOnConfirm: false,   closeOnCancel: false },
  			  function(isConfirm){
  				  if (isConfirm) {  
  		        	var prom = ueFactory.delete(uniteEnseignementPK);
  		        	prom.success(function(data){
  		        		swal("", "L'unité d'enseignement est supprimée!", "success");
  		        		$scope.refresh();
  		        	})
  		        	.error(function(data){
  		        		swal("Erreur", "Impossible de supprimer cette unité d'enseignement!", "error");
  		        	});
  				  } else {     
  					  	swal("Ignorer", "", "error");
  				  }
  	  	   }); 
        }
        
        $scope.refresh();
    }]
  );

  app.controller('UEDetailsController', 
    ['$scope', '$stateParams', '$location', 'ueFactory', 'toaster',
    function($scope, $stateParams, $location, ueFactory, toaster){      
    	$scope.edit = edit;
    	$scope.show = function(){
    		var uniteEnseignementPK = {
            		"codeFormation" : $stateParams.codeFormation,
            		"codeUe" : $stateParams.codeUe
            };
    	
    		
            var promisesFactory = ueFactory.get(uniteEnseignementPK);
            promisesFactory.success(function(data) {
         		$scope.ue = data;
         		
         		$scope.ue.semestre = data.semestre;
         		$scope.semestreSelected = $scope.ue.semestre;
         		$scope.formations = [{
         				"codeFormation" : data.uniteEnseignementPK.codeFormation
         		}];
         		
         		var promiseEc = ueFactory.getEc(uniteEnseignementPK);
         		promiseEc.success(function(data) {
         			$scope.ec = data;
         			if(data.length > 0)
         				$scope.filled = true;
         			else
         				$scope.filled = false;
         			
         			$scope.getEnseignants();
         		});
            });
    	}
    	
    	$scope.showEc = function(){
        	//$location.path('/ec' + ec);
    	};
    	
    	$scope.getEnseignants = function(){
    		var promiseEns = ueFactory.getAllEnseignants();
 	    	promiseEns.success(function(data){
 	    		$scope.enseignants = data;
 	    		if($stateParams.codeUe != "nouveau"){
 	    			$scope.responsable = $scope.ue.noEnseignant;
 	    			$scope.enseignant = $scope.responsable.noEnseignant;
 	    		}
 	    	});
    	}
    	
    	$scope.edit = edit;
    	
    	if($stateParams.codeUe == "nouveau"){
            $scope.ue= { };
            $scope.edit= true;   
            $scope.ajout = true;
            var prom = ueFactory.getAllFormations();
            prom.success(function(data){
            	$scope.formations = data;
            	$scope.getEnseignants();
            });
        } else { 
            $scope.show();
        }     
    	
    	 var promiseGetSemestre= ueFactory.getDomain();
    	 promiseGetSemestre.success(function(data,statut){
         	$scope.semestres= data;
         	for (var i=0; i<$scope.semestres.length; i++){
         		console.log("compare " + $scope.semestres[i].rvAbbreviation + " to " + $scope.semestreSelected);
         		console.log("ue.abbreviation : ", $scope.ue.semestre);
         		if($scope.semestres[i].rvAbbreviation === $scope.ue.semestre.trim()){
         			$scope.semestreSelected= $scope.semestres[i];
         		}
         	}
         })
         
         .error(function(data,statut){
       	  console.log("impossible de recuperer la liste des types");
         });
          
        $scope.edition = function(){
        	edit = true;
            $scope.edit = edit;
        }

        $scope.submit = function(){
        	console.log("ens: ", $scope.enseignant);
        	var ue = {
        			"uniteEnseignement" : {
        				"uniteEnseignementPK" : {
        					"codeFormation" : $scope.ue.uniteEnseignementPK.codeFormation,
        					"codeUe" : $scope.ue.uniteEnseignementPK.codeUe
        				},
        				"designation" : $scope.ue.designation,
        				"semestre" : $scope.semestreSelected.rvAbbreviation,
        				"description" : $scope.ue.description,
        				"nbhCm" : $scope.ue.nbhCm,
        				"nbhTd" : $scope.ue.nbhTd,
        				"nbhTp" : $scope.ue.nbhTp
        			},
        			"enseignant" : {
        				"noEnseignant" : $scope.enseignant
        			}
        	}
        	        	
        	if($stateParams.codeUe == "nouveau"){
        		var promiseAdd = ueFactory.add(ue);
            	promiseAdd.success(function(data){
            		swal("", "L'unité d'enseignement est ajoutée!", "success");
            		edit = false;
                    $scope.edit = edit;
                	$location.path('/ue');
            	})
            	.error(function(data){
            		swal({
            			title: "Error!",
            			text: "Veuillez remplir les champs obligatoires !",
            			type: "error",
            			confirmButtonText: "Ok" 
            		});
            	});
            }else {
            	var promiseUpdate = ueFactory.update(ue);
            	promiseUpdate.success(function(data){
            		swal("Félicitation!", "L'unité d'enseignement est modifiée!", "success");
            		edit = false;
                    $scope.edit = edit;
                    $location.path('/ue');
            	})
            	.error(function(data){
            		swal({
            			title: "Error!",
            			text: "Veuillez remplir les champs obligatoires !",
            			type: "error",
            			confirmButtonText: "Ok" 
            		});
            	});
            }
        }
        
        $scope.cancel = function(){
        	$location.path('/ue');
        }
        
    }]
  );
})();
