/*
* Author Kenza ABOUAKIL
* Script de controle des promotions
*/

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
      // renvoi la promotion avec l'anneeUniversitaire et codeFormation
		// demandés
      get: function(promotionPK) { 
    	  // TODO retourner les promotions
    	  console.log("TODO : get promotion",promotionPK);
    	  return $http.post("http://localhost:8090/getPromotion/", promotionPK);
   	  },

   	  /*
		 * { "enseignant":{ "noEnseignant":2 }, "promotion":{ "promotionPK": {
		 * "codeFormation":"M2KIM", "anneeUniversitaire":"82" },
		 * "siglePromotion":"sigle", "nbMaxEtudiant":2, "lieuRentree":"lieu",
		 * "processusStage":"mod" } }
		 */
      add: function(promotion, noEnseignant) {// ajout d'une nouvelle
												// promotion
        // La promotion à envoyé au controlleur possède une structure un peu
		// différente (promotion + noEnseignant)
    	var newPromotion= {"promotion": promotion, "enseignant": {"noEnseignant": noEnseignant}};
    	console.log("new promotion: ",newPromotion);
	    $http.post('http://localhost:8090/addPromotion',newPromotion);
      },
      set: function(promotion, noEnseignant) {// modification d'une promotion
												// existante
    	  // La promotion à envoyé au controlleur possède une structure un peu
			// différente (promotion + noEnseignant)
          var newPromotion= {"promotion": promotion, "enseignant": {"noEnseignant": noEnseignant}};
          console.log("newPromotion: ",newPromotion);
    	  $http.post('http://localhost:8090/updatePromotion',newPromotion);

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
      getFormations: function(){
    	  console.log("TODO : recuperation de la liste des formations");
		  return $http.get("http://localhost:8090/formations");
      }
    };
  }]);

  

  app.controller('PromotionsController', 
    ['$scope', '$filter','$location', 'promotionsFactory',
    function($scope, $filter, $location, promotionsFactory){
    	var init;
    	promotionsFactory.all()
		.success(function(data) {
		    $scope.promotions = data;
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
			 $scope.error = 'unable to get the poneys';
		  }
		);
      
      // Crée la page permettant d'ajouter une promotion
      $scope.ajoutPromotion = function(){
          $location.path('/admin/promotion/nouveau/nouveau'); 
       }
      
      // affiche les détail d'une promotion
      $scope.edit = function (promotionPK){
    	  $scope.ajout= true;
    	  $location.path("/admin/promotion/"+ promotionPK.anneeUniversitaire + "/" + promotionPK.codeFormation);
      }

      // supprime une promotion
      $scope.supprime = function(promotionPK){
    	  
    	  var promise= promotionsFactory.delete(promotionPK);
          promise.success(function(data,statut){
        	  $scope.currentPagePromotion.removeValue("promotionPK",promotionPK);
          })
          .error(function(data,statut){
        	  console.log("impossible de supprimer la promotion choisie");
          });
    	  
      }
    }]
  );

  app.controller('PromotionDetailsController', 
    ['$scope', '$routeParams', '$location', '$filter', 'promotionsFactory',
    function($scope, $routeParams, $location,$filter, promotionsFactory){      
      $scope.edit= false;    
      var promoPK = {anneeUniversitaire:  $routeParams.ann, codeFormation: $routeParams.form};
      // si creation d'une nouvelle promotion
      if($routeParams.ann == "nouveau"){
	        $scope.promotion= { };
	        // Récuperation des enseignants
	        var promise1= promotionsFactory.getEnseignants();
	        promise1.success(function(data,statut){
	        	$scope.enseignants= data;
	        	console.log("\tEnseignants récupérés: ", data);
	        })
	        .error(function(data,statut){
	      	  console.log("impossible de recuperer la liste des enseignants");
	        });
	        // Récuperation des formations
	        var promise2= promotionsFactory.getFormations();
	        promise2.success(function(data,statut){
	        	$scope.formations= data;
	        	console.log("\tFormations récupérées: ", data);
	        })
	        .error(function(data,statut){
	      	  console.log("impossible de recuperer la liste des formations");
	        });
	        $scope.ajout = true;
	        $scope.edit= true;
	        
      } else { // sinon on edite une promotion existante
			
            var promise1= promotionsFactory.get(promoPK);
            promise1.success(function(data,statut){
          	  $scope.promotion= data ;
          	  console.log("TODO: recuperation de la promotion: ", $scope.promotion);
	          	var promise2= promotionsFactory.getEtudiants(promoPK);
	            promise2.success(function(data,statut){
	            	$scope.promotion.etudiantCollection = data ;
	            	console.log("TODO: recuperation de la liste des étudiants pour la promotion selectionnée ",data);
	            })
	            .error(function(data,statut){
	          	  console.log("impossible de recuperer les étudiants de la promotion choisie");
	            });
	            var promise3= promotionsFactory.getEnseignants();
		        promise3.success(function(data,statut){
		        	$scope.enseignants= data;
		        	console.log("\tEnseignants récupérés: ", data);
		        })
		        .error(function(data,statut){
		      	  console.log("impossible de recuperer la liste des enseignants");
		        });
            })
            .error(function(data,statut){
          	  console.log("impossible de recuperer les details de la promotion choisie");
            });
            
      }

      $scope.edition = function(){
        $scope.edit = true;
      }

      // valide le formulaire d'édition d'une promotion
      $scope.submit = function(){
    	  $scope.promotion.promotionPK.codeFormation= $scope.formationSelected;
    	  if($routeParams.ann == "nouveau")
    		  promotionsFactory.add($scope.promotion, $scope.enseignantSelected);
    	  else// modification
    		  promotionsFactory.set($scope.promotion, $scope.enseignantSelected);
         $scope.edit = false;        
      }

      // annule l'édition
      $scope.cancel = function(){
        // si ajout d'une nouvelle promotion => retour à la liste des promotions
        if($routeParams.ann == "nouveau"){
          $location.path('/admin/promotions');
        } else {
          var promise = promotionsFactory.get(promoPK);
          promise.success(function(data,statut){
          	  $scope.promotion= data ;
            })
            .error(function(data,statut){
          	  console.log("impossible de recuperer les details de la promotion");
            });
          $scope.edit = false;
        }
      }
    }]
  );
}).call(this);
