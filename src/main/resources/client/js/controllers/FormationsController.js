/*
 * @Author Kenza ABOUAKIL (Correction Complete)
 * 
 */
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
      
      getDomainDoubleDiplome:function(){
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
  
  app.service('conservationVariableService', function(){
	  this.edit= false;
	  this.ajout= false;
  });

  app.controller('FormationsController',
    ['$scope', '$location','$http','$filter', 'formationsFactory', 'conservationVariableService',
    function($scope, $location,$http,$filter, formationsFactory, conservationVariableService){
    	
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
    	$scope.afficheUEs = function(codeFormation){
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
	    	$scope.afficheECs = function(uePK){
		    	 var promiseEC = formationsFactory.getECsByCodeUe(uePK);
		    	    promiseEC.success(function(data){
		    	    	$scope.ecs = data;
		    	    	$scope.ec = true;
		    	    	})
		    	    .error(function(data){
		    	    	$scope.error = 'unable to get the poneys';
		    	    });
		    	}
	    	
		$scope.consulterFormation= function(codeFormation){
			conservationVariableService.edit= false;
	    	conservationVariableService.ajout= false;
			$location.path("/formation/"+ codeFormation);
		}
		
		$scope.consulterUE= function(uePK){
			$location.path("/ue/"+uePK.codeFormation+ "/"+uePK.codeUe);
		}
		
		$scope.consulterEC= function (ecPK){
			$location.path("/elementConstitutif/infos"+ ecPK.codeFormation +"/"+ ecPK.codeUe +"/"+ ecPK.codeEc);
		}
		
      // Crée la page permettant d'ajouter une formation
      $scope.ajoutFormation = function(){
    	  conservationVariableService.edit= true;
    	  conservationVariableService.ajout= true;
    	  $location.path("/formation/nouveau"); 
      }

      // affiche les détails d'une formation
      $scope.edit = function(formation){
    	  conservationVariableService.edit= true;
    	  conservationVariableService.ajout= false;
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
			    		  swal("Erreur!", "vous ne pouvez pas supprimer cette formation car elle est refferencée", "error");
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
    ['$scope', '$stateParams','$http', '$location','$filter', 'formationsFactory', 'conservationVariableService', 
    function($scope, $stateParams, $http, $location,$filter, formationsFactory, conservationVariableService){  
    	var ctrl= this;
    	
    	//Récuperation du domaine du double diplome
    	formationsFactory.getDomainDoubleDiplome().then(
    		function(data) {
    	    	ctrl.domaineDoubleDiplome = data.data;
    	    	//Récuperation du domaine des diplomes
    	    	return formationsFactory.getDomain();
    		},
    		function(){
    			console.log("Impossible de récuperer le domaine du double diplome !");
			}
		).then(
				function(data1) {
					ctrl.domaineDiplomes = data1.data;
					// Creation d'une nouvelle formation
					if($stateParams.id == "nouveau"){
						ctrl.formation= { };
						ctrl.diplomeSelected= {};
						ctrl.doubleDiplomeSelected= {};
						conservationVariableService.ajout= ctrl.ajout= true;
						conservationVariableService.edit= ctrl.edit= true;    
					} else { // Edition une formation existante
						conservationVariableService.ajout= ctrl.ajout= false;
						ctrl.edit= conservationVariableService.edit;
						//Récuperation de la formation
						formationsFactory.get($stateParams.id).then(
						function(data) {
							ctrl.formation = data.data;
							ctrl.dateDebut= $filter('date')(ctrl.formation.debutAccreditation, "dd/MM/yyyy");
							ctrl.dateFin= $filter('date')(ctrl.formation.finAccreditation, "dd/MM/yyyy");
							for (var i=0; i<ctrl.domaineDoubleDiplome.length; i++){
								if(ctrl.domaineDoubleDiplome[i].rvAbbreviation == ctrl.formation.doubleDiplome){
									ctrl.doubleDiplomeSelected= ctrl.domaineDoubleDiplome[i];
									break;
								}
							}
							for (var i=0; i<ctrl.domaineDiplomes.length; i++){
								if(ctrl.domaineDiplomes[i].rvAbbreviation == ctrl.formation.diplome){
									ctrl.diplomeSelected= ctrl.domaineDiplomes[i];
									break;
								}
							}
						},
						function(){
							console.log("Impossible de récuperer la formation !");
						});
					}
			},
			function(){
    			console.log("Impossible de récuperer le domaine des diplomes !");
			}
		);
      
      ctrl.submit = function(){
        	if(ctrl.dateDebut && ctrl.dateFin){
            	var date = ctrl.dateDebut.split('/');
            	ctrl.formation.debutAccreditation = new Date(date[1] + '-' + date[0] + '-' + date[2]);
            	var date2 = ctrl.dateFin.split('/');
            	ctrl.formation.finAccreditation = new Date(date2[1] + '-' + date2[0] + '-' + date2[2]);
            	console.log("debut: ", ctrl.formation.debutAccreditation);
            	console.log("fin: ", ctrl.formation.finAccreditation);
        	}
        	ctrl.formation.diplome = ctrl.diplomeSelected.rvAbbreviation;
        	ctrl.formation.doubleDiplome = ctrl.doubleDiplomeSelected.rvAbbreviation;
    	    if($stateParams.id == "nouveau"){
    	    	formationsFactory.addFormation(ctrl.formation).then(
    	    			function(data, status, headers, config) {
    	    				swal("Félicitation!", "La nouvelle formation est ajoutée!", "success");
    		        		$location.path('/formations');
    					},
    					function(data, status, headers, config) {
    						swal("Erreur !", "La nouvelle formation ne peut pas être ajoutée !", "error");
    					}
    	    	);
    	    }
    	    else{
    	    	var promiseUpdate = formationsFactory.updateFormation(ctrl.formation);
    	    	promiseUpdate.success(function(data, status, headers, config) {
    	    		swal("Félicitation!", "La formation est modifiée !", "success"); 
    	    		$location.path('/formations');
    			})
            	.error(function(data, status, headers, config) {
            		swal("Erreur !", "La formation ne peut pas être modifiée !", "error");
    			});	
    	    }
        }

      ctrl.edition = function(){
    	  ctrl.edit = true;
    	  ctrl.ajout= false;
      }

      // annule l'édition
      ctrl.cancel = function(){
    	  $location.path('/formations');
      } 
      
      ctrl.isFormDateSupOK= function() {
    	  console.log("ctrl.formation.debutAccreditation: "+ ctrl.formation.debutAccreditation);
    	  console.log("ctrl.formation.debutAccreditation: "+ctrl.formation.debutAccreditation);
    	  if(ctrl.formation.debutAccreditation && ctrl.formation.finAccreditation)
    		  return ctrl.formation.debutAccreditation < ctrl.formation.finAccreditation;
    	else
    		return true;
      }

    }]
  );
})();
