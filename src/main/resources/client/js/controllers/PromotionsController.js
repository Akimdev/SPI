/*
* Author Kenza ABOUAKIL

* Script de controle des promotions
*/
var edit= false;

(function() {
  'use strict';

  var app = angular.module('app.promotions', []);

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
  
  app.factory('promotionsFactory', ['$http',function($http){
    var list = function() {
        return  $http.get('http://localhost:8090/promotions')
       };

    return {
    	// Méthode de renvoi la liste des promotions
      all:list, 
      // renvoi la promotion avec l'anneeUniversitaire et codeFormation demandés
      get: function(promotionPK) { 
    	  return $http.post("http://localhost:8090/getPromotion/", promotionPK);
   	  },
      add: function(promotion, noEnseignant) {//ajout d'une nouvelle promotion 
        //La promotion à envoyer au controlleur possède une structure un peu différente (promotion + noEnseignant)
      	var newPromotion= {"promotion": promotion, "enseignant": {"noEnseignant": noEnseignant}};
  	    return $http.post('http://localhost:8090/addPromotion',newPromotion);
      },
      set: function(promotion, noEnseignant) {// modification d'une promotion existante
    	  // La promotion à envoyé au controlleur possède une structure un peu différente (promotion + noEnseignant)
        var newPromotion= {"promotion": promotion, "enseignant": {"noEnseignant": noEnseignant}};
        console.log("newPromotion: ", newPromotion);
    	  return $http.post('http://localhost:8090/updatePromotion',newPromotion);
        },
      delete: function(promotionPK) {
        // TODO Supprimer une promotion
    	  console.log("TODO : supprimer promotion",promotionPK);
    	  return  $http.post('http://localhost:8090/deletePromotion/', promotionPK);
      },
      getEtudiants : function(promotionPK){
    	  console.log("TODO : recuperation des etudiants par promotion",promotionPK);
		    return $http.post("http://localhost:8090/getEtudiantByPromotion/",promotionPK);
      },
      getEnseignants: function(){
    	  console.log("TODO : recuperation de la liste des enseignants");
		    return $http.get("http://localhost:8090/ens");
      },
      getEnseignantResponsable: function(promotionPK){
    	  console.log("TODO : recuperation de l'enseignant responsable");
		  return $http.post("http://localhost:8090/promotion/getEnseignantResponsable", promotionPK);
      },
      getFormations: function(){
    	  console.log("TODO : recuperation de la liste des formations");
		    return $http.get("http://localhost:8090/formations");
      },
      getNomFormations: function (codeFormations){
    	  return $http.post("http://localhost:8090/formation/getNomFormations", codeFormations);
      }
    };
  }]);

  app.controller('PromotionsController', 
    ['$scope', '$filter','$location', 'promotionsFactory', 'toaster',
    function($scope, $filter, $location, promotionsFactory, toaster){
    	var init;
    	var codeFormations= [];
    	$scope.refresh = function(){
    	var promisePromo = promotionsFactory.all();
		promisePromo.success(function(data) {
		    $scope.promotions = data;
		    for(var i=0; i< data.length; i++){
		    	codeFormations[i]= data[i].promotionPK.codeFormation;
		    }
		    var maPromise = promotionsFactory.getNomFormations(codeFormations);
		    maPromise.success(function(data) {
		    	for(var i = 0; i < $scope.promotions.length; i++){
		    		$scope.promotions[i].designation = data[i];
		    	}
		    }).error(function(data) {
				 $scope.error = 'Impossible de récuperer la designation des formations';
			  }
			);
		      $scope.searchKeywords = '';
		      $scope.filteredPromotions = [];
		      $scope.row = '';
		      $scope.select = function(page) {
		        var end, start;
		        start = (page - 1) * $scope.numPerPage;
		        end = start + $scope.numPerPage;
		        return $scope.currentPagePromotion = $scope.filteredPromotions.slice(start, end);
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
		        $scope.filteredPromotions = $filter('filter')($scope.promotions, $scope.searchKeywords);
		        return $scope.onFilterChange();
		      };
		      $scope.order = function(rowName) {
		        if ($scope.row === rowName) {
		          return;
		        }
		        $scope.row = rowName;
		        $scope.filteredPromotions = $filter('orderBy')($scope.promotions, rowName);
		        return $scope.onOrderChange();
		      };
		      $scope.numPerPageOpt = [3, 5, 10, 20];
		      $scope.numPerPage = $scope.numPerPageOpt[2];
		      $scope.currentPage = 1;
		      $scope.currentPagePromotion = [];
		      init = function() {
		        $scope.search();
		        return $scope.select($scope.currentPage);
		      };
		      return init();
		  }
		)
		.error(function(data) {
			 $scope.error = 'Impossible de récuperer les promotions';
		  }
		);
    	}
      // Crée la page permettant d'ajouter une promotion
      $scope.ajoutPromotion = function(){
          $location.path('/promotion/nouveau/nouveau');
       }
      
      // modifie les détails d'une promotion
      $scope.edit = function (promotionPK){
    	  $scope.ajout= true;
    	  edit=true;
    	  $location.path("/promotion/"+ promotionPK.anneeUniversitaire + "/" + promotionPK.codeFormation);
      }
      // affiche les détails d'une promotion
      $scope.affiche= function(promotionPK){
    	  $scope.ajout= false;
    	  edit= false;
    	  $location.path("/promotion/"+ promotionPK.anneeUniversitaire + "/" + promotionPK.codeFormation);
      }

      // supprime une promotion
      $scope.supprime = function(promotionPK){
    	  swal({   
			  title: "Etes-vous sûr de vouloir supprimer cette promotion ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "OUI",  
			  cancelButtonText: "NON",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {
					  var promise= promotionsFactory.delete(promotionPK);
			          promise.success(function(data,statut, headers, config){
			        	  $scope.currentPagePromotion.removeValue("promotionPK",promotionPK);
			        	  $scope.refresh();
			        	  swal("Supprimé!", "la promotion est supprimée", "success");
			          })
			          .error(function(data,statut, headers, config){
			        	  swal("Erreur!", "Impossible de supprimer la promotion car elle est déjà referencée", "error");
			          });
				  } else {     
						  swal("Annulé", "", "error");
				  }
	  	 });
      }
      $scope.refresh();
    }]
  );

  app.controller('PromotionDetailsController',
    ['$scope', '$stateParams', '$location', '$filter', 'promotionsFactory', '$http',
    function($scope, $stateParams, $location,$filter, promotionsFactory, $http){      
    	$scope.edit= edit;
    	var initAjout= function(){
    		$scope.promotion= { };
	        $scope.ajout = true;
    	}
    	
    	var initEdition= function(){
    		var promoPK = {anneeUniversitaire:  $stateParams.ann, codeFormation: $stateParams.form};
    		//Recuperation de la promotion
            var promise1= promotionsFactory.get(promoPK);
            promise1.success(function(data3,statut){
	        	data3.dateRentree = $filter('date')(data3.dateRentree, "dd/MM/yyyy");
				$scope.promotion= data3;
	            //Recuperation de l'enseignant responsable
	            var promise3= promotionsFactory.getEnseignantResponsable(promoPK);
		        promise3.success(function(data1,statut){
		        	$scope.responsable = data1;
		        	//console.log("ensR: ",$scope.responsable.noEnseignant);
		        }).error(function(data1,statut){
		      	  console.log("impossible de recuperer l'enseignant resposable de la promotion");
		        });
				// Initialisation du processusStage dans le cas d'une consultation d'une promotion
				for(var i=0; i< $scope.processusStage.length; i++){
					if($scope.processusStage[i].rvAbbreviation== data3.processusStage)
						$scope.processusSignification= $scope.processusStage[i].rvMeaning;
				}
				//Recuperation des etudiants  
				var promise2= promotionsFactory.getEtudiants(promoPK);
	            promise2.success(function(data,statut){
	            	$scope.promotion.etudiantCollection = data ;
	            }).error(function(data,statut){
	          	  console.log("impossible de recuperer les étudiants de la promotion choisie");
	            });
	        }).error(function(data3,statut){
	      	  console.log("impossible de recuperer les details de la promotion choisie");
	        });
            $scope.ajout= true;
    	}
    	$http.get('http://localhost:8090/getDomaine/PROCESSUS_STAGE')
    	.success(function(data, status, headers, config) {
    		$scope.processusStage= [];
		    for(var i=0; i<data.length; i++){
		    	$scope.processusStage[i]={};
		    	$scope.processusStage[i].rvAbbreviation = data[i].rvAbbreviation;
		    	$scope.processusStage[i].rvMeaning = data[i].rvMeaning;
		    }
		    // Récuperation des enseignants
	        var promise1= promotionsFactory.getEnseignants();
	        promise1.success(function(data1,statut){
	        	$scope.enseignants= data1;
	        }).error(function(data1,statut){
	      	  console.log("impossible de recuperer la liste des enseignants");
	        });
	        
	        // Récuperation des formations
	        var promise2= promotionsFactory.getFormations();
	        promise2.success(function(data2,statut){
	        	$scope.formations= data2;
	        }).error(function(data2,statut){
	      	  console.log("impossible de recuperer la liste des formations");
	        });
		      // si creation d'une nouvelle promotion
		      if($stateParams.ann == "nouveau"){
		    	  initAjout();
		      } else { // sinon on edite une promotion existante
		    	  initEdition();
		      }
		      $scope.edit = edit;
	    }).error(function(data, status, headers, config) {
	      console.log("Impossible de récuperer le domaine PROCESSUS_STAGE");
	    });
    	

      $scope.edition = function(){
    	  $scope.ajout= true;
    	  $scope.edit=edit=true;
      }
      
      // valide le formulaire d'édition d'une promotion
      $scope.submit = function(){
    	  if($stateParams.ann == "nouveau"){
    		  $scope.promotion.promotionPK.codeFormation= $scope.formationSelected;
    		  if($scope.promotion.dateRentree) {
    			  var date = $scope.promotion.dateRentree.split('/');
    		      $scope.promotion.dateRentree = new Date(date[1] + '-' + date[0] + '-' + date[2]);  
    		  }
    		  if(!$scope.enseignantSelected) {
    			  $scope.enseignantSelected = null;
    		  }
    		  var promise = promotionsFactory.add($scope.promotion, $scope.enseignantSelected);
    		  promise.success(function(data){
    			  var promiseEnseignant = promotionsFactory.getEnseignantResponsable($scope.promotion.promotionPK);
    			  promiseEnseignant.success(function(data){
    				  $scope.responsable = data;
    				  swal("Félicitation!", "La nouvelle promotion est ajoutée!", "success");
    	    		  $location.path("/promotion/" + $scope.promotion.promotionPK.anneeUniversitaire + '/' + $scope.promotion.promotionPK.codeFormation);
    			  });
    			  promiseEnseignant.error(function(){
      				  swal("Erreur !", "La nouvelle promotion ne peut pas être ajoutée !", "error");
    			  })
    		  });
    	  }
    	  else{ // modification
    		  if($scope.promotion.dateRentree) {
    			  var date = $scope.promotion.dateRentree.split('/');
    		      $scope.promotion.dateRentree = new Date(date[1] + '-' + date[0] + '-' + date[2]);  
    		  }
    		  $scope.promotion.processusStage= $scope.processusStageSelected;
    		  var promise = promotionsFactory.set($scope.promotion, $scope.enseignantSelected);
    		  promise.success(function(){
    			  var promiseEnseignant = promotionsFactory.getEnseignantResponsable($scope.promotion.promotionPK);
    			  promiseEnseignant.success(function(data){
    				  $scope.responsable = data;
    				  swal("Félicitation!", "La promotion est modifiée !", "success");   
    	    		  $location.path("/promotion/" + $scope.promotion.promotionPK.anneeUniversitaire + '/' + $scope.promotion.promotionPK.codeFormation);
    			  });
    			  promiseEnseignant.error(function(data){
    				  swal("Erreur !", "La promotion ne peut pas être modifiée !", "error");    	        			  
    			  });
    		  });
    		  $scope.edit = false;
    	  }
      }

      // annule l'édition
      $scope.cancel = function(){
			history.back();
      }
      
      $scope.etudiantDetails = function(id){
    	  $location.path("/etudiant/"+id);
      }
    }]
  );
}).call(this);
