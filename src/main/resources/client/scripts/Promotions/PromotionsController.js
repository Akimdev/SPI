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
        //var defer = $q.defer();

        return  $http.get('http://localhost:8090/ens')//promotions')
        /*.then(function(response) {
           defer.resolve(response.data);
         });

         return defer.promise;*/
       };
            
    var details = [ 
      // Constituer le délail de la liste des enseignants ici
    ];

    return {
      // renvoi la liste de tous les enseignants
      all:list,// function() { //return list; 

     // },
      // renvoi l'enseignant avec le no_enseignant demandé
      get: function(promotionPK) { 
    	  // TODO retourner les enseignants
    	  console.log("TODO : get promotion",promotionPK);
    	  //return list.retourValue("no_enseignant",idx);
    	  return  $http.get('http://localhost:8090/getPromotion/'+promotionPK);
    	  
   	  },
      set: function(promotion) {//------------------------------------------------------------------------------
        var idx = promotion.promotionPK.anneeUniversitaire;
        
        if(idx){// si modification d'un enseignant existant
          // TODO alimenter l'objet enseignant trouvé
        	console.log("TODO : update enseignant",idx);
        	//list.removeValue("no_enseignant",enseignant.no_enseignant);
        	//return list.push(enseignant);
        	
        	var newEnseignant = {
          		      "noEnseignant" : idx,
         			  "nom" : enseignant.nom,
         			  //....
         		  };      	  
  	        	$http.post('http://localhost:8090/updatePromotion',promotion);
        } else { // si ajout d'un nouvel enseignant
          // TODO ajouter un enseignant à la liste
        	
        	  var newEnseignant = {
        		  "noEnseignant" : "8",
       			  "nom" : enseignant.nom,
       			  //....
       		  };      	  
	        	$http.post('http://localhost:8090/ajouterPromotion',newPromotion);
        	
        	//return list.push(enseignant);
        }
      },
      delete: function(promotionPK) { 
        // TODO Supprimer 
    	  console.log("TODO : supprimer enseignant",promotionPK);
    	  return  $http.get('http://localhost:8090/deletePromotion/'+promotionPK)
    	  //list.removeValue("no_enseignant",enseignant.no_enseignant);
    	  //return list;
      },

      getPromotion : function(promotionPK){
    	  var url = "http://localhost:8090/getPromotion/"+promotionPK;
    	  return $http.get(url);
      },
      
      getEtudiant : function(promotionPK){
	      var url = "http://localhost:8090/getEtudiantsByPromotion/"+promotionPK;
		  return $http.get(url);
      }
    };
  }]);

  

  app.controller('PromotionsController', 
    ['$scope', '$filter','$location', 'promotionsFactory',
    function($scope, $filter, $location, promotionsFactory){
    	var init;
    	promotionsFactory.all()
		.success(function(data) {
		    $scope.enseignants = data;
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
      
      // Crée la page permettant d'ajouter un enseignant
      // TODO Lien vers la page permettant de créer un enseignant /admin/enseignant/nouveau
      $scope.ajoutPromotion = function(){
          $location.path('/admin/promotion/nouveau'); 
       }
      
      // affiche les détail d'une promotion
      $scope.edit = function (promotionPK){
    	  $location.path("/admin/promotion/"+ promotionPK);
    	  alert(promotionPK);
      }

      // supprime une promotion
      $scope.supprime = function(promotionPK){
    	  
    	  
    	  var promise= enseignantsFactory.delete(promotionPK);
          promise.success(function(data,statut){
        	  //$scope.enseignant.promotions = data ;
        	  $scope.currentPagePromotion.removeValue("promotionPK",promotionPK);
          })
          .error(function(data,statut){
        	  console.log("impossible de supprimer la promotion choisie");
          });
    	  
      }
    }]
  );

  app.controller('PromotionDetailsController', 
    ['$scope', '$routeParams', '$location', 'promotionsFactory',
    function($scope, $routeParams, $location, promotionsFactory){      
      $scope.edit= false;    

      // si creation d'une nouvelle promotion
      if($routeParams.id == "nouveau"){
        $scope.promotion= { };
        $scope.edit= true;    
      } else { // sinon on edite une promotion existante
    	  
        var promise = promotionsFactory.get($routeParams.id);
        promise.success(function(data){
      	  $scope.promotion = data ;
            var promise= promotionsFactory.getPromotion($routeParams.id);
            promise.success(function(data,statut){
          	  $scope.promotion.promotions = data ;
            })
            .error(function(data,statut){
          	  console.log("impossible de recuperer les details de la promotion choisie");
            });
            
            var promise= enseignantsFactory.getUE($routeParams.id);
            promise.success(function(data,statut){
          	  $scope.enseignant.ue = data ;
            })
            .error(function(data,statut){
          	  console.log("impossible de recuperer les details de la promotion choisie");
            });
        })
        .error(function(data){
      	  console.log("impossible de recuperer les details de la promotion choisie");
        });
      }

      $scope.edition = function(){
        $scope.edit = true;
      }

      // valide le formulaire d'édition d'un enseignant
      $scope.submit = function(){    	 
        enseignantsFactory.set($scope.promotion);        
        $scope.edit = false;        
      }

      // annule l'édition
      $scope.cancel = function(){
        // si ajout d'un nouvel enseignant => retour à la liste des enseignants
        if(!$scope.enseignant.noEnseignant){
          $location.path('/admin/enseignants');
        } else {
          var e = enseignantsFactory.get($routeParams.id);
          $scope.enseignant = JSON.parse(JSON.stringify(e));
          $scope.edit = false;
        }
      }      
    }]
  );
}).call(this);
