/*
 * @author ASSABBAR Soukaina
 */
(function() {
  'use strict';
  var app = angular.module('app.enseignants', []);
  Array.prototype.removeValue = function(name, value){
	   var array = $.map(this, function(v,i){
	      return v[name] === value ? null : v;
	   });
	   this.length = 0; //clear original array
	   this.push.apply(this, array); //push all elements except the one we want to delete
	}
  Array.prototype.retourValue = function(name, value){
	   var array = $.map(this, function(v,i){
	      return v[name] === value ? v : null;
	   });
	   return array[0];
	}
  app.factory('enseignantsFactory', ['$http',function($http){
    var list = function() {
        return  $http.get('http://localhost:8090/ens')
       };       
    var details = [ 
      // Constituer le délail de la liste des enseignants ici
    ];
    return {
      // renvoi la liste de tous les enseignants
      all:list,
      // renvoi l'enseignant avec le no_enseignant demandé
      get: function(noEnseignant) { 
    	  // TODO retourner les enseignants
    	  console.log("TODO : get enseignant",noEnseignant);
    	  //return list.retourValue("no_enseignant",idx);
    	  return  $http.get('http://localhost:8090/getens/'+noEnseignant);
   	  }, 
   	//ajout d'une nouvel enseignant
     	add: function(enseignant) {
    	  return $http.post('http://localhost:8090/ajouterEnseignant', enseignant);
     	},
    	set: function(enseignant) {
    	  	  return $http({
    	  		  method: 'POST',
    	  		  url: 'http://localhost:8090/updateEnseignant',
    	  		  data: enseignant,
    	  		  headers:{ 'Content-Type' : 'application/json'}
    	  	  });
    	    },
      delete: function(noEnseignant) { 
        // TODO Supprimer 
    	  console.log("TODO : supprimer enseignant",noEnseignant);
    	  return  $http.get('http://localhost:8090/deleteEnseignant/'+noEnseignant)
    	  //list.removeValue("no_enseignant",enseignant.no_enseignant);
    	  //return list;
      },
      getDomain : function(){
    	  return $http.get("http://localhost:8090/getDomaine/TYPE_ENSEIGNANT");
      },
      getPays : function(){
    	  return $http.get("http://localhost:8090/getDomaine/PAYS");
      },
      getPromotion : function(noEnseignant){
    	  var url = "http://localhost:8090/getpromotionenseignant/"+noEnseignant;
    	  return $http.get(url);
      },
      
      getUE : function(noEnseignant){
	      var url = "http://localhost:8090/getuebyenseignant/"+noEnseignant;
		  return $http.get(url);
      }
    };
  }]);
  app.controller('EnseignantsController', 
    ['$scope', '$filter','$location', 'enseignantsFactory',
    function($scope, $filter, $location, enseignantsFactory){
$scope.refresh = function(){
    	enseignantsFactory.all()
		.success(function(data) {
		    $scope.enseignants = data;
		      $scope.searchKeywords = '';
		      $scope.filteredEnseignant = [];
		      $scope.row = '';
		      $scope.select = function(page) {
		        var end, start;
		        start = (page - 1) * $scope.numPerPage;
		        end = start + $scope.numPerPage;
		        return $scope.currentPageEnseignant = $scope.filteredEnseignant.slice(start, end);
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
		        $scope.filteredEnseignant = $filter('filter')($scope.enseignants, $scope.searchKeywords);
		        return $scope.onFilterChange();
		      };
		      $scope.order = function(rowName) {
		        if ($scope.row === rowName) {
		          return;
		        }
		        $scope.row = rowName;
		        $scope.filteredEnseignant = $filter('orderBy')($scope.enseignants, rowName);
		        return $scope.onOrderChange();
		      };
		      $scope.numPerPageOpt = [3, 5, 10, 20];
		      $scope.numPerPage = $scope.numPerPageOpt[2];
		      $scope.currentPage = 1;
		      $scope.currentPageEnseignant = [];
			var init = null;
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
$scope.refresh();
      // Crée la page permettant d'ajouter un enseignant
      // TODO Lien vers la page permettant de créer un enseignant /enseignant/nouveau
      $scope.ajoutEnseignant = function(){
          $location.path('/enseignant/nouveau'); 
       }
      
      // affiche les détail d'un enseignant
      // TODO Lien vers la page permettant d'éditer un enseignant /enseignant/ + no_enseignant
      $scope.edit = function (noEnseignant){
			edit=true;
    	  $location.path("/enseignant/"+ noEnseignant);
    	  //alert(enseignant.no_enseignant);
      	}
	$scope.affiche= function(noEnseignant){
    	  edit= false;
	$location.path("/enseignant/"+ noEnseignant);
		}
      // supprime un enseignant
	$scope.supprime = function(noEnseignant){   
		  swal({   
			  title: "Voulez-vous vraiment supprimer cet enseignant ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "OUI",  
			  cancelButtonText: "NON",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
					  var promise= enseignantsFactory.delete(noEnseignant);
					  promise.success(function(data,statut, headers, config){
						$scope.refresh();
						$scope.currentPageEnseignant.removeValue("noEnseignant",noEnseignant);
						swal("Supprimé!", "l'enseignant est supprimé", "success");
					});
					  promise.error(function(data,statut, headers, config){
			    		  swal("Erreur!", "Impossiple de supprimer l'enseignant choisi car il est déja réferencé", "error");
			  		});	
					  } else {     
						  swal("Annulé", "", "error");
						  }
				  });  
      }
      $scope.refresh();
    }]
  );
  app.controller('EnsDetailsController', 
    ['$scope', '$stateParams','$http', '$location','$filter', 'enseignantsFactory','toaster',
    function($scope, $stateParams , $http, $location, $filter, enseignantsFactory, toaster){      
      $scope.edit= false;    
      var promise3= enseignantsFactory.getDomain();
      promise3.success(function(data,statut){
      	$scope.types= data;
      	$scope.typeSelected= $scope.enseignant.type;
      	console.log("\tTypes récupérés: ", data);
      })
      .error(function(data,statut){
    	  console.log("impossible de recuperer la liste des types");
      });
	var promisePays= enseignantsFactory.getPays();
		promisePays.success(function(data,statut){
      	$scope.payss= data;
      	$scope.paysSelected=$scope.enseignant.pays;
      	console.log("\tPays récupérés: ", data);
      })
      .error(function(data,statut){
    	  console.log("impossible de recuperer la liste des pays");
      });
      // si creation d'un nouvel enseignant
      if($stateParams.id == "nouveau"){
        $scope.enseignant= { };
        $scope.edit= true; 
      } else { // sinon on edite un enseignant existant
        var promise = enseignantsFactory.get($stateParams.id);
        promise.success(function(data){
      	  $scope.enseignant = data ;
            var promise= enseignantsFactory.getPromotion($stateParams.id);
            promise.success(function(data,statut){
          	  $scope.enseignant.promotions = data ;
            })
            .error(function(data,statut){
          	  console.log("impossible de recuperer les details de l'enseignant choisi");
            });
            
            var promise= enseignantsFactory.getUE($stateParams.id);
            promise.success(function(data,statut){
          	  $scope.enseignant.ue = data ;
            })
            .error(function(data,statut){
          	  console.log("impossible de recuperer les details de l'enseignant choisi");
            });
            
            for (var i=0; i< $scope.types.length; i++){
            	if($scope.types[i].rvLowValue == $scope.typeSelected){
            		$scope.typeSelected = $scope.types[i];
            		break;
            	}
            }
            
            for (var i=0; i< $scope.payss.length; i++){
            	if($scope.payss[i].rvLowValue == $scope.paysSelected){
            		$scope.paysSelected = $scope.payss[i];
            		break;
            	}
            }
        })
        .error(function(data){
      	  console.log("impossible de recuperer les details de l'enseignant choisi");
        });
		$scope.edit= edit;
      		}
   
    $scope.edition = function(){
        $scope.edit = true;
      }
$scope.submit = function(){
    	  $scope.enseignant.type= $scope.typeSelected.rvLowValue;
    	  $scope.enseignant.pays= $scope.paysSelected.rvLowValue;
    	  console.log($scope.enseignant);
		if($stateParams.id == "nouveau"){
	        	var promisesajout = enseignantsFactory.add($scope.enseignant);
	        	promisesajout.success(function(data, status) {
	        		swal("Félicitation!", "Le nouveau enseignant est ajouté!", "success");
	        		history.back();
				});
	        	promisesajout.error(function(data, status, headers, config) {
	        		toaster.pop({
	                    type: 'error',
	                    title: 'Insertion ou modification impossible. noEnseignant déjà	 existant',
	                    positionClass: 'toast-bottom-right',
	                    showCloseButton: true
	                });
				});
								        }
			 else{ // modification
	        	var promisesajout = enseignantsFactory.set($scope.enseignant);
	        	promisesajout.success(function(data, status, headers, config) {
	        		swal("Félicitation!", "Enseignant modifié!", "success");
	        		history.back();
				});
	        	promisesajout.error(function(data, status, headers, config) {
	        		toaster.pop({
	                    type: 'error',
	                    title: 'Modification echouée',
	                    positionClass: 'toast-bottom-right',
	                    showCloseButton: true
	                });
				});
	        	
	        	$scope.edit = false;
	        }
    }
	  $scope.notifyType = function(type){
		  $scope.typeSelected = type;
	  }
	  
	  $scope.notifyPays = function(pays){
		  $scope.paysSelected = pays;
	  }


      $scope.edition = function(){
          $scope.edit = true;
          $scope.button_clicked = true;
        }
      // annule l'édition
      $scope.cancel = function(){
    	  history.back();
      }      
    }]
  );
}).call(this);
