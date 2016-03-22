/*
 * @Author ASSABBAR && Youssef 
 * EtudiantController Angular
 */

var edit= false;
(function() {
  'use strict';

  var app = angular.module('app.etudiants', []);
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
  app.factory('etudiantsFactory', function($http){
	  return {
	  all: function(){
    	  return $http.get('http://localhost:8090/etudiants');
	  },	  
      get: function(noEtudiant) { 
    	  return $http.get('http://localhost:8090/etudiants/' + noEtudiant);
      },
    //ajout d'une nouvel etudiant
   	add: function(etudiant) {
  	  return $http.post('http://localhost:8090/etudiants/addEtudiant', etudiant);
   	},
   	set: function(etudiant) {
	  	  return $http({
	  		  method: 'POST',
	  		  url: 'http://localhost:8090/etudiants/updateEtudiant',
	  		  data: etudiant,
	  		  headers:{ 'Content-Type' : 'application/json'}
	  	  });
	    },
      set: function(formation) {
    	  //return $http.post('http://localhost:8090/formations/nouveau', formation);
      },
      edit: function(formation) {
    	  //return $http.post('http://localhost:8090/formations/update', formation);
      },
      delete: function(code) { 
    	  //return $http.get('http://localhost:8090/formations/delete', {params: {codeFormation: code}});
      },
      getPays: function(pays){
    	  return $http.get('http://localhost:8090/getDomaine/pays/' + pays);
      },
      getNomFormations: function (codeFormations){
    	  return $http.post("http://localhost:8090/formation/getNomFormations", codeFormations);
      },
      getFormations: function(){
     	 return $http.get("http://localhost:8090/formations");
       },
      };
  });
  
  app.controller('EtudiantsController', 
    ['$scope', '$filter','$location', 'etudiantsFactory',
    function($scope, $filter, $location, etudiantsFactory){
    	$scope.refresh = function(){
    		etudiantsFactory.all()
    		.success(function(data){
		      $scope.etudiants = data;
		      $scope.searchKeywords = '';
		      $scope.filteredEtudiant = [];
		      $scope.row = '';
		      $scope.select = function(page) {
		        var end, start;
		        start = (page - 1) * $scope.numPerPage;
		        end = start + $scope.numPerPage;
		        return $scope.currentPageEtudiant = $scope.filteredEtudiant.slice(start, end);
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
		        $scope.filteredEtudiant = $filter('filter')($scope.etudiants, $scope.searchKeywords);
		        return $scope.onFilterChange();
		      };
		      $scope.order = function(rowName) {
		        if ($scope.row === rowName) {
		          return;
		        }
		        $scope.row = rowName;
		        $scope.filteredEtudiant = $filter('orderBy')($scope.etudiants, rowName);
		        return $scope.onOrderChange();
		      };
		      $scope.numPerPageOpt = [3, 5, 10, 20];
		      $scope.numPerPage = $scope.numPerPageOpt[2];
		      $scope.currentPage = 1;
		      $scope.currentPageEtudiant = [];
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
   // Crée la page permettant d'ajouter un etudiant
      $scope.ajoutEtudiant = function(){
          $location.path('/etudiant/nouveau'); 
       }
   // affiche les détail d'un etudiant
      $scope.edit = function (noEtudiant){
			edit=true;
			//$scope.ajout = false;
    	  $location.path("/etudiant/"+ noEtudiant);
      	}
	$scope.show= function(noEtudiant){
    	  edit= false;
    	  //$scope.ajout=false
	$location.path("/etudiant/"+ noEtudiant);
		}
      // supprime un etudiant
  	$scope.supprime = function(noEtudiant){   
  		  swal({   
  			  title: "Voulez-vous vraiment supprimer cet etudiant ?",      
  			  type: "warning",   
  			  showCancelButton: true,   
  			  confirmButtonColor: "#DD6B55",   
  			  confirmButtonText: "OUI",  
  			  cancelButtonText: "NON",   
  			  closeOnConfirm: false,   closeOnCancel: false },
  			  function(isConfirm){
  				  if (isConfirm) {  
  					  var promise= etudiantsFactory.delete(noEtudiant);
  					  promise.success(function(data,statut, headers, config){
  						$scope.refresh();
  						$scope.currentPageEtudiant.removeValue("noEtudiant",noEtudiant);
  						swal("Supprimé!", "l'etudiant est supprimé", "success");
  					});
  					  promise.error(function(data,statut, headers, config){
  			    		  swal("Erreur!", "Impossiple de supprimer l'etudiant choisi car il est déja réfferencié", "error");
  			  		});	
  					  } else {     
  						  swal("Annulé", "", "error");
  					  }
  					  });  
  	      }
  	      $scope.refresh();
  	    }]
  	  );

  app.controller('EtudiantDetailsController', 
    ['$scope', '$stateParams', '$location', '$filter', 'etudiantsFactory','toaster',
    function($scope, $stateParams, $location, $filter, etudiantsFactory,toaster){   
    	$scope.edit= false;
//    	etudiantsFactory.getFormations().then(
//		        // Récuperation des formations
//    			function(data2,statut){
//		        	$scope.formations= data2.data;
//		        },
//		        function(data2,statut){
//  		      	  console.log("Impossible de recuperer la liste des formations");
//  		        }
//    	).then(
//    			function(){
    			// si creation d'un nouvel etudiant
    				if($stateParams.id == "nouveau"){
        	        $scope.etudiant= { };
        	        $scope.edit= true;   
                    $scope.ajout = true;
                    var prom = etudiantsFactory.getFormations();
                    prom.success(function(data){
                    	$scope.formations = data;
                    });
        	      } else { // sinon on edite un etudiant existant
        	    	  $scope.edit= edit;
//        	    	  for( var i=0; i< $scope.formations.length; i++){
//  		        			if($scope.formations[i].codeFormation== $scope.etudiant.promotion.promotionPK.codeFormation)
//	  		        			$scope.formationSelected= $scope.formations[i];
//	  		        	}
    			    	var promise = etudiantsFactory.get($stateParams.id);
    			        promise.success(function(data){
    			        	$scope.etudiant = data;
    			        	var promisePays = etudiantsFactory.getPays($scope.etudiant.paysOrigine);
    			        	promisePays.success(function(data){
    			        		$scope.etudiant.paysOrigine = data.rvMeaning;
    			        	});
    			        	
    			        });
    			        $scope.retour = function(){
    			        	history.back();
    			        }
        	      }
						$scope.edition = function(){
							$scope.ajout= false;
							$scope.edit=edit=true;
						}
						
    			        $scope.submit = function(){
    			      	  console.log($scope.etudiant);
    			  		if($stateParams.id == "nouveau"){
    			  	        	var promisesajout = etudiantsFactory.add($scope.etudiant);
    			  	        	promisesajout.success(function(data, status) {
    			  	        		swal("Félicitation!", "Le nouveau etudiant est ajouté!", "success");
    			  	        		$location.path('/etudiants');
    			  				});
    			  	        	promisesajout.error(function(data, status, headers, config) {
    			  	        		toaster.pop({
    			  	                    type: 'error',
    			  	                    title: 'Insertion ou modification impossible. noEtudiant déjà existant',
    			  	                    positionClass: 'toast-bottom-right',
    			  	                    showCloseButton: true
    			  	                });
    			  				});
    			  			}
    			  			 else{ // modification
    			  	        	var promisesajout = etudiantsFactory.set($scope.etudiant);
    			  	        	promisesajout.success(function(data, status, headers, config) {
    			  	        		swal("Félicitation!", "etudiant modifié!", "success");
    			  	        		$location.path('/etudiants');
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

//    	
//			        $scope.edition = function(){
//			            edit = true;
//			            $scope.ajout= false;
//			            $scope.button_clicked = true;
//			          }
			        // annule l'édition
    			        $scope.cancel = function(){
    			            if(!$scope.etudiant.noEtudiant){
    			              $location.path('/etudiants');
    			            } else {
    			              var e = etudiantsFactory.get($stateParams.id);
    			              $scope.etudiant = JSON.parse(JSON.stringify(e));
    			              $scope.edit = false;
    			            }
    			          } 
    }]
  );
  
}).call(this);
