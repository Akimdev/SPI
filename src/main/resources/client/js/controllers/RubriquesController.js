/*
* Author BAQLOUL Soukaina
* Script de controle des Rubriques
*/
(function() {
  'use strict';

  var app = angular.module('app.rubriques', []);

  /*Array.prototype.removeValue = function(name, value){
	   var array = $.map(this, function(v,i){
	      return v[name] === value ? null : v;
	   });
      this.length = 0; //clear original array
	   this.push.apply(this, array); //push all elements except the one we want to delete
	}*/

  
  app.factory('rubriquesFactory', ['$http',function($http){
    
    return {
    	// TODO Lister
      listerRubriques:function(){
    	  return $http.get("http://localhost:8090/rubriques");
      },    
      delete: function(idRubrique) { 
        // TODO Supprimer
    	  console.log("TODO : supprimer rubrique", idRubrique);
        return  $http.get('http://localhost:8090/deleteRubrique/'+ idRubrique)
      },
      getRubrique : function(idRubrique){
      	return $http.get('http://localhost:8090/rubrique/'+idRubrique)
      },
      add : function(rubrique){
      	return $http.post('http://localhost:8090/addRubrique', rubrique);
      },
      set : function(rubrique){
      	return $http.post('http://localhost:8090/updateRubrique', rubrique);
      },
      getMaxIdRubrique: function(){
    	  return $http.get("http://localhost:8090/getMaxIdRubrique");
      }

    };
  }]);

  

  app.controller('RubriquesController', 
    ['$scope', '$filter','$location', 'rubriquesFactory',
    function($scope, $filter, $location, rubriquesFactory){
    	

    	$scope.refresh=function(){
    	var init = null;
		  var promise = rubriquesFactory.listerRubriques();
              promise.success(function(data) {
		      $scope.rubriques = data;
		      $scope.searchKeywords = '';
		      $scope.filteredRubrique = [];
		      $scope.row = '';
		      $scope.select = function(page) {
		        var end, start;
		        start = (page - 1) * $scope.numPerPage;
		        end = start + $scope.numPerPage;
		        return $scope.currentPageRubrique = $scope.filteredRubrique.slice(start, end);
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
		        $scope.filteredRubrique = $filter('filter')($scope.rubriques, $scope.searchKeywords);
		        return $scope.onFilterChange();
		      };
		      $scope.order = function(rowName) {
		        if ($scope.row === rowName) {
		          return;
		        }
		        $scope.row = rowName;
		        $scope.filteredRubrique = $filter('orderBy')($scope.rubriques, rowName);
		        return $scope.onOrderChange();
		      };
		      $scope.numPerPageOpt = [3, 5, 10, 20];
		      $scope.numPerPage = $scope.numPerPageOpt[2];
		      $scope.currentPage = 1;
		      $scope.currentPageRubrique = [];
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
     
  $scope.ajoutRubrique = function(){
      $location.path('/rubrique/nouveau/edit'); 
   }
  
 
  $scope.show = function (idRubrique){
	  $location.path("/rubrique/"+ idRubrique);
	 
  }
  
  $scope.edit= function (idRubrique){
	  $location.path("/rubrique/"+ idRubrique +"/edit");
	 
  }


      // supprime une Rubrique
      $scope.supprime = function(idRubrique){ 
          swal({   
			  title: "Voulez-vous vraiment supprimer cette Rubrique ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "Oui",  
			  cancelButtonText: "Non",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
					  var promise= rubriquesFactory.delete(idRubrique);
    	  			  promise.success(function(data,statut){
        	          swal("Supprimé!", "la rubrique a été supprimée avec succès.", "success");
        	           $scope.refresh();
                  });
						promise.error(function(data,statut){
        	        	swal("Erreur!", "Cette rubrique ne peut pas être supprimer car elle est déjà référencée.", "error");
			  		});	
					  }else {     
						  swal("Annulé", "", "error");
						  }
				  });  
      }

       $scope.refresh();
	}]

  );
  
  app.controller('RubriqueDetailsController', 
		    ['$scope', '$stateParams', '$location', '$filter', 'rubriquesFactory',
		    function($scope, $stateParams, $location,$filter, rubriquesFactory){  
   $scope.edit=false;
   $scope.editer= function (idRubrique){
		  $location.path("/rubrique/"+ idRubrique +"/edit");
		 
	  }
   /* -Ajout- */
   if ($stateParams.id=="nouveau") {
	   $scope.rubrique={};
	   var promise = rubriquesFactory.getMaxIdRubrique();
	   promise.success(function(data,status){
			$scope.rubrique.idRubrique= data +1;
			$scope.rubrique.type= "Rubrique Standard";
	   }).error(function(data,status){
	   		console.log('Erreur de récupéreration de la rubrique: '+$stateParams.id);
	   });
	   $scope.ajout=true;
	   $scope.edit=true;
   /* -Edit- */	
   }else{
	   var promise = rubriquesFactory.getRubrique($stateParams.id);
	   promise.success(function(data,status){
		   $scope.idrub = $stateParams.id;
	   	 $scope.rubrique=data;
	   	$scope.rubrique.type= ($scope.rubrique.type =="RBS") ? "Rubrique Standard" : "Rubrique Personnelle";
	   }).error(function(data,status){
	   		console.log('Erreur de récupéreration de la rubrique: '+$stateParams.id);
	   });
   }
   
   	$scope.edition = function(){
       $scope.edit = true;
     }
   	/* valide le formulaire d'édition d'une promotion */
    $scope.submit = function(){
		$scope.rubrique.type= ($scope.rubrique.type =="Rubrique Standard") ? "RBS" : "RBP";
   	  if($stateParams.id == "nouveau"){
   		var promise = rubriquesFactory.add($scope.rubrique);
     	promise.success(function(data,statut){
     		swal("Félicitation!", "La nouvelle rubrique est ajoutée!", "success");
     		history.back();
        })
        .error(function(data,statut){
        	swal("Erreur!", "Impossible d'ajouter la rubrique", "error");
        });
   	  }
   	  else{// modification
   		var promise = rubriquesFactory.set($scope.rubrique);
     	promise.success(function(data,statut){
     		swal("Félicitation!", "La rubrique a été modifiée", "success");
     		history.back();
        })
        .error(function(data,statut){
        	swal("Erreur!", "Impossible de modifier la rubrique", "error");
        });
   	  }
   	  $scope.edit = false;
     }
    /* annule l'édition */
    $scope.cancel = function(){
       /* si ajout d'une nouvelle promotion => retour à la liste des promotions */
//       if($stateParams.id == "nouveau"){
//         	$location.path('/rubriques');
//       	}else {
//       		$location.path('/rubrique/'+$stateParams.id);
//       		var promise = rubriquesFactory.getRubrique($stateParams.id);
//         	promise.success(function(data,statut){
//         	 	$scope.rubrique = data ;
//            })
//            .error(function(data,statut){
//         	    console.log("Impossible de récuperer les details de la rubrique");
//            });
//    	$scope.edit = false;
//       }
    	history.back();
     };
	  
  }]);
}).call(this);
  