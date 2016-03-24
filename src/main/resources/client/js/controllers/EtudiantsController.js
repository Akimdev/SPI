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
    // ajout d'une nouvel etudiant
   	add: function(etudiant) {
  	  return $http.post('http://localhost:8090/etudiants/addEtudiant', etudiant);
   	},
   	set: function(etudiant) {
   		return $http.post('http://localhost:8090/etudiants/updateEtudiant', etudiant);

	  },
// set: function(formation) {
// //return $http.post('http://localhost:8090/formations/nouveau', formation);
// },
      edit: function(formation) {
    	  // return $http.post('http://localhost:8090/formations/update',
			// formation);
      },
      delete: function(noEtudiant) { 
    	  return  $http.get('http://localhost:8090/etudiants/deleteEtudiant-'+ noEtudiant);
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
       getPromotions: function(codeFormation){
    	   return $http.get("http://localhost:8090/promotion/findByCodeFormation/" + codeFormation);
       },
       getDomaine: function(domaine){
    	   return $http.get("http://localhost:8090/getDomaine/" + domaine );
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
			// $scope.ajout = false;
    	  $location.path("/etudiant/"+ noEtudiant);
      	}
	$scope.show= function(noEtudiant){
    	  edit= false;
    	  // $scope.ajout=false
	$location.path("/etudiant/"+ noEtudiant);
		}
      // supprime un etudiant
	 $scope.supprime = function(noEtudiant){
     	swal({   
			  title: "Voulez-vous vraiment supprimer cet etudiant ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "Oui!",  
			  cancelButtonText: "Non!",   
			  closeOnConfirm: false,  closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
		        	etudiantsFactory.delete(noEtudiant).then(

		        			function(data){
				        		swal("Félicitation!", "L'etudiant est supprimé!", "success");
				        		$scope.refresh();
				        	},
				        	function(data){
				        		swal("Erreur!", "Impossible de supprimer cet etudiant!", "error");
				        	}
		        			);
		        	
// prom.success(function(data){
// swal("Félicitation!", "L'etudiant est supprimé!", "success");
// $scope.refresh();
// })
// .error(function(data){
// swal("Erreur!", "Impossible de supprimer cet etudiant!", "error");
// });
				  } else {     
					  	swal("Ignorer", "", "error");
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
    	$scope.promotions = [];
    	
    	var promise = etudiantsFactory.getFormations();
    	promise.success(function(data){
    		$scope.formations = data;
    	});
    	
    	var promise = etudiantsFactory.getDomaine("UNIVERSITE");
    	promise.success(function(data){
    		$scope.univ = data;
    	});
    	
    	var promise = etudiantsFactory.getDomaine("PAYS");
    	promise.success(function(data){
    		$scope.pays = data;
    	});
    	
	// si creation d'un nouvel etudiant
		if($stateParams.id == "nouveau"){
        $scope.etudiant= { };
        $scope.edit= true;   
        $scope.ajout = true;
        
      } else { // sinon on edite un etudiant existant
    	  $scope.edit= edit;
	    	var promise = etudiantsFactory.get($stateParams.id);
	        promise.success(function(data){
	        	$scope.etudiant = data;
	        	$scope.formationSelected = $scope.etudiant.promotion.promotionPK.codeFormation;
	        	$scope.promotionSelected = $scope.etudiant.promotion.promotionPK.anneeUniversitaire;
	        	$scope.paysSelected = $scope.etudiant.paysOrigine;
	        	$scope.universiteOrigineSelected = $scope.etudiant.universiteOrigine;
	        	$scope.anneeSelected = $scope.etudiant.promotion.promotionPK.anneeUniversitaire;
	        	
	        	$scope.etudiant.dateNaissance = $filter('date')(data.dateNaissance, "dd/MM/yyyy");
	
	        	var promisePays = etudiantsFactory.getPays($scope.etudiant.paysOrigine);
	        	promisePays.success(function(data){
	        		$scope.etudiant.paysOrigine = data.rvMeaning;
	        	});
	        	
        		for(var i = 0; i < $scope.formations.length; i++){
        			if($scope.formations[i].codeFormation === $scope.formationSelected){
        				$scope.formationSelected = $scope.formations[i];
        				$scope.getAnnee($scope.formationSelected);
        				break;
        			}
        		}
        		
        		for(var i = 0; i < $scope.pays.length; i++){
        			if($scope.pays[i].rvLowValue == $scope.paysSelected){
        				$scope.paysSelected = $scope.pays[i];
        				break;
        			}
        		}
        		
        		for(var i = 0; i < $scope.univ.length; i++){
        			if($scope.univ[i].rvLowValue === $scope.universiteOrigineSelected){
        				$scope.universiteOrigineSelected = $scope.univ[i];
        				break;
        			}
        		}
        		
	        });
      }
		
		$scope.retour = function(){
        	history.back();
        }
	
		$scope.edition = function(){
			$scope.ajout= false;
			$scope.edit=edit=true;
		}
		
	        $scope.submit = function(){
        	
	        	$scope.etudiant.promotion = {
	        			"promotionPK" : {
	        				"codeFormation" :  $scope.formationSelected.codeFormation,
	        				"anneeUniversitaire" : $scope.anneeSelected.promotionPK.anneeUniversitaire
	        			}
	        	};
	        	$scope.etudiant.paysOrigine = $scope.paysSelected.rvLowValue;
	        	$scope.etudiant.universiteOrigine = $scope.universiteOrigineSelected.rvLowValue;
                var date = $scope.etudiant.dateNaissance.split('/');
  		        $scope.etudiant.dateNaissance = new Date(date[1] + '-' + date[0] + '-' + date[2]); 
	        	
	        	var promo = $scope.etudiant.promotion;
	        	delete $scope.etudiant["promotion"];
	        	var etudiant = $scope.etudiant;
	        	var etudiantFullObject = {
	        			"promotion": {
	        				"promotionPK": promo.promotionPK 
	        			},
	        			"etudiant": etudiant
	        	};
	        	
	        	console.log(etudiantFullObject);
	        	
	  		if($stateParams.id == "nouveau"){
	  	        	var promisesajout = etudiantsFactory.add(etudiantFullObject);
	  	        	promisesajout.success(function(data, status) {
	  	        		swal("Félicitation!", "Le nouveau étudiant est ajouté!", "success");
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
	  	        	var promisesajout = etudiantsFactory.set(etudiantFullObject);
	  	        	promisesajout.success(function(data, status, headers, config) {
	  	        		swal("Félicitation!", "L'étudiant modifié!", "success");
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
	        
	     
	        $scope.getAnnee = function(formation){
	        	$scope.formationSelected = formation;
	        	var promise = etudiantsFactory.getPromotions(formation.codeFormation);
	        	promise.success(function(data){
	        		$scope.promotions = data;
	        		if($stateParams.id != "nouveau"){
	        			for(var i = 0; i < $scope.promotions.length; i++){
	            			if($scope.promotions[i].promotionPK.anneeUniversitaire[i] === $scope.anneeSelected){
	            				$scope.anneeSelected = $scope.promotions[i].promotionPK.anneeUniversitaire[i];
	            				break;
	            			}
	            		}
	        		}
	        	});
	        }
	        
	        $scope.notifyUniv = function(univ){
	        	$scope.universiteOrigineSelected = univ;
	        }
	        
	        $scope.notifyPays = function(pays){
	        	$scope.paysSelected = pays;
	        }
	        
	        $scope.notifyAnnee = function(annee){
	        	$scope.anneeSelected = annee;
	        }
	        
    }]
  );
  
}).call(this);
