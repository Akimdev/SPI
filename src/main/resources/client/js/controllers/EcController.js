/*
* Author Ait Errami Abdelhakim

* Script de controle des elements constitutifs
*/

(function() {
  'use strict';

  var app = angular.module('app.ec', []);

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
  
  app.factory('ecFactory', ['$http',function($http){
	  return {
		  all:function(){
			  return $http.get("http://localhost:8090/ECs");
		  },
		  get:function(ecPK){
			  return $http.post("http://localhost:8090/getEC",ecPK);
		  },
		  delete:function(ecPK){
		  	  return $http.post("http://localhost:8090/deleteEC",ecPK);
		  },
		  add:function(ecUtil){
			  return $http.post("http://localhost:8090/addEC",ecUtil);
		  },
		  update:function(ecUtil){
			  return $http.post("http://localhost:8090/updateEC",ecUtil);
		  },
		  getEnseignants:function(){
			  return $http.get("http://localhost:8090/ens");
		  },
		  getUEs:function(){
			  return $http.get("http://localhost:8090/UEs");
		  },
		  getFormations:function(){
			  return $http.get("http://localhost:8090/formations");
		  }
		  
	}
   }]);

  app.controller('ecController', 
    ['$scope', '$filter','$location', 'ecFactory', 'toaster',
    function($scope, $filter, $location, ecFactory, toaster){
    	var init;
    	edit = false;
    	$scope.refresh = function(){
    	var promiseEc = ecFactory.all();
    	promiseEc.success(function(data){
    		$scope.elementConstitutifs = data;
		      $scope.searchKeywords = '';
		      $scope.filteredEcs = [];
		      $scope.row = '';
		      $scope.select = function(page) {
		        var end, start;
		        start = (page - 1) * $scope.numPerPage;
		        end = start + $scope.numPerPage;
		        return $scope.currentPageEc = $scope.filteredEcs.slice(start, end);
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
		        $scope.filteredEcs = $filter('filter')($scope.elementConstitutifs, $scope.searchKeywords);
		        return $scope.onFilterChange();
		      };
		      $scope.order = function(rowName) {
		        if ($scope.row === rowName) {
		          return;
		        }
		        $scope.row = rowName;
		        $scope.filteredEcs = $filter('orderBy')($scope.elementConstitutifs, rowName);
		        return $scope.onOrderChange();
		      };
		      $scope.numPerPageOpt = [3, 5, 10, 20];
		      $scope.numPerPage = $scope.numPerPageOpt[2];
		      $scope.currentPage = 1;
		      $scope.currentPageEc = [];
		      init = function() {
		        $scope.search();
		        return $scope.select($scope.currentPage);
		      };
		      return init();
    	}).error(function(data) {
			 $scope.error = 'erreur de récupération ecs';
		  });
    	}
    	$scope.refresh();


	$scope.ajoutEc=function(){
		$location.path("/elementConstitutif/nouveau");
	}
	$scope.edit=function(elementConstitutifPK){
		$location.path("/elementConstitutif/"+elementConstitutifPK.codeFormation+"/"+elementConstitutifPK.codeUe+"/"+
						elementConstitutifPK.codeEc);
	}
	$scope.detail=function(elementConstitutifPK){
		$location.path("/elementConstitutif/infos/"+elementConstitutifPK.codeFormation+"/"+elementConstitutifPK.codeUe+"/"+
						elementConstitutifPK.codeEc);
	}
	$scope.supprime=function(elementConstitutifPK){
		swal({   
			  title: "Etes-vous sûr de vouloir supprimer cet Element Constitutif ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "Oui",  
			  cancelButtonText: "Non",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {
					  var promiseDelete= ecFactory.delete(elementConstitutifPK);
			          promiseDelete.success(function(data,statut, headers, config){
			        	  $scope.currentPageEc.removeValue("elementConstitutifPK",elementConstitutifPK);
			        	  $scope.refresh();
			        	  swal("Supprimé!", "l'Element Constitutif a été supprimée", "success");
			          })
			          .error(function(data,statut, headers, config){
			        	  swal("Erreur!", "Impossible de supprimer l'Element Constitutif choisie", "error");
			          });
				  } else {     
						  swal("Ignorer", "", "error");
				  }
	  	 });
	}
 	
    }]);

  app.controller('EcDetailsController', 
		    ['$scope', '$stateParams', '$location', '$filter', 'ecFactory',
		    function($scope, $stateParams, $location,$filter, ecFactory){ 
	/** recuperer toutes les formations**/
	var ctrl = this;
	var promiseFormations = ecFactory.getFormations();
		promiseFormations.success(function(data){
			ctrl.formations=data;
		}).error(function(data){
			error("error formations");
		})
	/**récupérer tous les enseignants **/ 
	var promiseEnseignants = ecFactory.getEnseignants();
	 promiseEnseignants.success(function(data){
			 ctrl.enseignants=data;
		 }).error(function(data){
			 console.log("error enseignants");
		 });
	/** récupérer tous les UE**/
	 var promiseUe = ecFactory.getUEs();
	 promiseUe.success(function(data){
		 ctrl.ues=data;
	 }).error(function(data){
		 console
	 })
	/** a la base , l'edition est false**/	    	
		ctrl.edit=false;
	/** pour créer un ec**/	
		if($stateParams.new=="nouveau"){
			ctrl.edit=true;
			ctrl.ec={};
			ctrl.ajout=true;
	/** pour voir les détails d'un ec**/		
		}else if($stateParams.infos){
			var elementConstitutifPK = {"codeFormation":$stateParams.id,
										"codeUe":$stateParams.id2,
										"codeEc":$stateParams.id3};
			/**lors d'un détail , on récupère l'ec désigne **/
			var promise =ecFactory.get(elementConstitutifPK);
			promise.success(function(data){
				ctrl.ec=data;
				ctrl.responsable=ctrl.ec.noEnseignant.noEnseignant;
				console.log(ctrl.ec);
			}).error(function(data){
				console.log("error recuperation one");
			});
	/** pour modifier un ec  **/		 
		}else{
			var elementConstitutifPK = {"codeFormation":$stateParams.id,
					"codeUe":$stateParams.id2,
					"codeEc":$stateParams.id3};
			var promise =ecFactory.get(elementConstitutifPK);
			promise.success(function(data){
				ctrl.ec=data;
				ctrl.responsable=ctrl.ec.noEnseignant.noEnseignant;
				console.log(ctrl.ec);
			}).error(function(data){
				console.log("error recuperation one");
			});
			ctrl.edit=true;
		}
	/** la fonction qui permet de modifier un ec **/	
	ctrl.edition=function(){
		ctrl.edit= true;
		var elementConstitutifPK = {"codeFormation":$stateParams.id,
									"codeUe":$stateParams.id2,
									"codeEc":$stateParams.id3};
		$location.path("/elementConstitutif/infos/"+elementConstitutifPK.codeFormation+"/"+elementConstitutifPK.codeUe+"/"+
				elementConstitutifPK.codeEc);
	}
	/** afin d'annuler **/
	ctrl.cancel=function(){
		history.back();
	}
	/** fonction pour ajouter **/
	ctrl.submit=function(){
		var ecUtil = {
				"enseignant":{
					"noEnseignant":ctrl.responsable
				},
				"elementConstitutif":{
					"description": ctrl.ec.description,
						"designation": ctrl.ec.designation,
						"elementConstitutifPK": {
						"codeFormation": ctrl.ec.elementConstitutifPK.codeFormation,
						"codeUe": ctrl.ec.elementConstitutifPK.codeUe,
						"codeEc": ctrl.ec.elementConstitutifPK.codeEc
						},
						"nbhCm": ctrl.ec.nbhCm,
						"nbhTd": ctrl.ec.nbhTd,
						"nbhTp": ctrl.ec.nbhTd
				}
		};
		/** si c'est un simple ajout **/
		if($stateParams.new=="nouveau"){
		/** on fait un controle sur le result de la http **/
		var promiseAdd =ecFactory.add(ecUtil);
		promiseAdd.success(function(status){
			swal("Félicitation!", "Le nouveau ElementConstituf est ajoutée!", "success");
		}).error(function(data,status){
			swal("Erreur !", "Le nouveau ElementConstituf n'a pas pu etre ajoute!", "error");
			console.log(status+""+data);
		});
		/** si c'est une modification **/
		}else{
			var promiseUpdate=ecFactory.update(ecUtil);
			console.log(ecUtil);
			promiseUpdate.success(function(status){
				swal("Félicitation!", "Le nouveau ElementConstituf a été modifié avec succés!", "success");
			}).error(function(status){
				swal("Erreur!", "Le nouveau ElementConstituf n'a pas pu etre modifié!", "error");
			});
		}
		$location.path("/ec");
	}
	
	
}]);
 
}).call(this);
