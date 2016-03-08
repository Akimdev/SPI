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
        return  $http.get('http://localhost:8090/rubrique/delete/'+ idRubrique)
      },
      getRubrique : function(idRubrique){
      	return $http.get('http://localhost:8090/rubrique/'+idRubrique)
      },
      add : function(rubrique){
      	return $http.post('http://localhost:8090/rubrique/ajouterRubrique', rubrique);
      },
      set : function(rubrique){
      	return $http.post('http://localhost:8090/rubrique/modifierRubrique', rubrique);

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
      $location.path('/admin/rubrique/nouveau'); 
   }
  
 
  $scope.edit = function (idRubrique){
	  $location.path("/admin/rubrique/"+ idRubrique);
	 
  }


      // supprime une Rubrique
      $scope.supprime = function(idRubrique){ 
    	

    	  
          swal({   
			  title: "Voulez-vous vraiment supprimer ce qualificatif ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "Oui, je veux le supprimer!",  
			  cancelButtonText: "Non, ignorer!",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
					  var promise= rubriquesFactory.delete(idRubrique);
    	  			  promise.success(function(data,statut){
        	          swal("Supprimé!", "le qualificatif est supprimé", "success");
        	           $scope.refresh();
                  });
						promise.error(function(data,statut){
        	        	swal("Erreur!", "vous pouvez pas supprimer ce qualificatif", "error");
			  		});	
					  }else {     
						  swal("Ignorer", "", "error");
						  }
				  });  
      }

       $scope.refresh();
	}]

  );
  
  app.controller('RubriqueDetailsController', 
		    ['$scope', '$routeParams', '$location', '$filter', 'rubriquesFactory',
		    function($scope, $routeParams, $location,$filter, rubriquesFactory){  
   $scope.edit=false;
   var idRubrique = $routeParams.id;

   /* -Ajout- */
   if ($routeParams.id=="nouveau") {
	   $scope.rubrique={};
	   $scope.ajout=true;
	   $scope.edit=true;
   /* -Edit- */	
   }else{
	   var promise = rubriquesFactory.getRubrique(idRubrique);
	   promise.success(function(data,status){
	   	 $scope.rubrique=data;
	   }).error(function(data,status){
	   	console.log('erreur de récupérer '+idRubrique);
	   })
   }
   
   	$scope.edition = function(){
       $scope.edit = true;
     }
   	/* valide le formulaire d'édition d'une promotion */
    $scope.submit = function(){
   	  if($routeParams.id == "nouveau"){
   		  rubriquesFactory.add($scope.rubrique);
   		  swal("Félicitation!", "La nouvelle rubrique est ajouté!", "success");
   	  }
   	  else// modification
   		 rubriquesFactory.set($scope.rubrique);
   		swal("Félicitation!", "La nouvelle rubrique a été modifié", "success");
         $scope.edit = false;        
     }
    /* annule l'édition */
    $scope.cancel = function(){
       /* si ajout d'une nouvelle promotion => retour à la liste des promotions */
       if($routeParams.id == "nouveau"){
         	$location.path('/admin/rubriques');
       	}else {
       		$location.path('/admin/rubriques');
       	   var promise = rubriquesFactory.getRubrique(idRubrique);
         	promise.success(function(data,statut){
         	 	$scope.promotion= data ;
            })
            .error(function(data,statut){
         	    console.log("impossible de recuperer les details de la rubrique");
            });
    	$scope.edit = false;
       }
     };
	  
  }]);
}).call(this);
  

