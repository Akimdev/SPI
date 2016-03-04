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
	   this.length = 0; //clear original array
	   this.push.apply(this, array); //push all elements except the one we want to delete
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
    	  // TODO retourner les promotions
    	  console.log("TODO : get promotion",promotionPK);
    	  return $http.post("http://localhost:8090/getPromotion/", promotionPK);
   	  },
      set: function(promotion) {
        var idx = promotion.promotionPK.anneeUniversitaire;
        
        if(idx){// si modification d'une promotion existante     	  
  	        	$http.post('http://localhost:8090/updatePromotion',promotion);
        } else { // si ajout d'une nouvelle promotion 	  
	        	$http.post('http://localhost:8090/addPromotion',promotion);
        }
      },
      delete: function(promotionPK) {
        // TODO Supprimer une promotion
    	  console.log("TODO : supprimer promotion",promotionPK);
    	  return  $http.post('http://localhost:8090/deletePromotion/', promotionPK);
      },
      
      getEtudiants : function(promotionPK){
    	  console.log("TODO : recuperation des etudiants par promotion",promotionPK);
		  return $http.post("http://localhost:8090/getEtudiantByPromotion/",promotionPK);
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
        $scope.edit= true;    
      } else { // sinon on edite une promotion existante
			
            var promise1= promotionsFactory.get(promoPK);
            promise1.success(function(data,statut){
          	  $scope.promotion= data ;
          	$scope.promotion.dateRentree = $filter('date')(data.dateRentree, "dd/MM/yyyy");
	          	var promise2= promotionsFactory.getEtudiants(promoPK);
	            promise2.success(function(data,statut){
	            	console.log("etd: ",data);
	          	  $scope.promotion.etudiantCollection = data ;
	          	  
	            })
	            .error(function(data,statut){
	          	  console.log("impossible de recuperer les étudiants de la promotion choisie");
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
        promotionsFactory.set($scope.promotion);        
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
