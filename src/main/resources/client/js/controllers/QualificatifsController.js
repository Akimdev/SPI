var edit = false;
(function() {
  'use strict';

  var app = angular.module('app.qualificatifs', []);

  app.factory('qualificatifsFactory', function($http, $window){
            
    return {
      all: function() { 
    	  return $http.get('http://localhost:8090/listerQualificatif');
      },
      get: function(idQualificatif) { 
    	  return $http.get('http://localhost:8090/qualificatif/' + idQualificatif); 
      },
      add: function(qualificatif){
    	  return $http({
    		  method: 'POST',
    		  url: 'http://localhost:8090/ajouterQualificatif',
    		  data: qualificatif,
    		  headers:{ 'Content-Type' : 'application/json'}
    	  });
      },
      set: function(qualificatif) {
    	  return $http({
    		  method: 'POST',
    		  url: 'http://localhost:8090/updateQualificatif',
    		  data: qualificatif,
    		  headers:{ 'Content-Type' : 'application/json'}
    	  });
      },
      delete: function(idQualificatif) { 
    	  return $http.get('http://localhost:8090/supprimerQualificatif?idQualificatif=' + idQualificatif);
      },
      getMaxIdQualificatif: function(){
    	  return $http.get('http://localhost:8090/getMaxIdQualificatif');
      }
    };
  });

  app.controller('QualificatifsController', 
    ['$scope', '$location','$http','$filter', 'qualificatifsFactory',
    function($scope, $location,$http,$filter, qualificatifsFactory){

    	$scope.refresh = function (){
    	      var promiseFormation = qualificatifsFactory.all();
    	      promiseFormation.success(function(data) {
    	          $scope.listQualificatifs = data;
    			      
    			      $scope.searchKeywords = '';
    			      $scope.filteredQualificatif = [];
    			      $scope.row = '';
    			      
    			      $scope.select = function(page) {
    			        var end, start;
    			        start = (page - 1) * $scope.numPerPage;
    			        end = start + $scope.numPerPage;
    			        return $scope.qualificatifs = $scope.filteredQualificatif.slice(start, end);
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
    			        $scope.filteredQualificatif = $filter('filter')($scope.listQualificatifs, $scope.searchKeywords);
    			        return $scope.onFilterChange();
    			      };
    			      $scope.order = function(rowName) {
    			        if ($scope.row === rowName) {
    			          return;
    			        }
    			        $scope.row = rowName;
    			        $scope.filteredQualificatif = $filter('orderBy')($scope.listQualificatifs, rowName);
    			        return $scope.onOrderChange();
    			      };
    			      $scope.numPerPageOpt = [3, 5, 10, 20];
    			      $scope.numPerPage = $scope.numPerPageOpt[2];
    			      $scope.currentPage = 1;
    			      $scope.qualificatifs = [];
    			      var init = null;
    			      init = function() {
    			        $scope.search();
    			        $scope.order('idQualificatif');
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
     
      $scope.ajoutQualificatif = function(){
        $location.path('/qualificatifs/nouveau');
        edit = true;
        $scope.edit = edit;
      }

      $scope.edit = function(qualificatif){
        $location.path('/qualificatifs/' + qualificatif.idQualificatif);
        edit = true;
        $scope.edit = edit;
      }
      
    // Affiche les détails des qualificatifs
  	$scope.afficheQualificatif = function(qualificatif){
  		 $location.path('/qualificatifs/' + qualificatif.idQualificatif);
         edit = false;
         $scope.edit = edit;
  	} 
      
      $scope.supprime = function(qualificatif){
		  swal({   
			  title: "Voulez-vous vraiment supprimer ce qualificatif ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "Oui",  
			  cancelButtonText: "Non",
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
					  var promisessuppression  = qualificatifsFactory.delete(qualificatif.idQualificatif);
			    	  promisessuppression.success(function(data, status, headers, config) {
						$scope.refresh();
						swal("Supprimé!", "le qualificatif est supprimé", "success");
					});
			    	  promisessuppression.error(function(data, status, headers, config) {
			    		  swal("Erreur!", "Ce qualificatif ne peut pas être supprimer, car il est référencé", "error");
			  		});	
				  } else {     
						  swal("Ignorer", "", "error");
				  }
	  	 });  
	}
      
    $scope.refresh();
    }]
  );

  app.controller('QualificatifDetailsController', 
    ['$scope', '$stateParams','$http', '$location','$filter', 'qualificatifsFactory', 'toaster',
    function($scope, $stateParams , $http, $location,$filter, qualificatifsFactory , toaster){ 

      $scope.edit= edit; 
    	
      if($stateParams.id == "nouveau"){
			$scope.qualificatif= { };
			var promisesFactory = qualificatifsFactory.getMaxIdQualificatif();
			promisesFactory.success(function(data) {
				$scope.qualificatif.idQualificatif = data +1;
			});  
			$scope.edit=true;
      } else {
    	  	var promisesFactory = qualificatifsFactory.get($stateParams.id);
	     	promisesFactory.success(function(data) {
	       		$scope.isVisible = true;
	     		$scope.qualificatif = data;
	     		console.log("\tQualificatif récupéré: ", data);
	     	});
      }      
      
      $scope.edition = function(){
    	  $scope.edit = true;
    	  //$scope.edit = edit;
        }

        $scope.submit = function(){
        	if($stateParams.id == "nouveau"){
	        	var promisesajout = qualificatifsFactory.add($scope.qualificatif);
	        	promisesajout.success(function(data, status, headers, config) {
	        		swal("Félicitation!", "Le nouveau qualificatif est ajouté!", "success");
				});
	        	promisesajout.error(function(data, status, headers, config) {
	        		toaster.pop({
	                    type: 'error',
	                    title: 'Insertion ou modification impossible. ID Qualificatif déjà	 existant',
	                    positionClass: 'toast-bottom-right',
	                    showCloseButton: true
	                });
				});
	        	$location.path('/qualificatifs');
	        }
	        else{ // modification
	        	var promisesajout = qualificatifsFactory.set($scope.qualificatif);
	        	promisesajout.success(function(data, status, headers, config) {
	        		swal("Félicitation!", "Qualificatif modifié!", "success");
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
        	//$scope.refresh();
      }
      $scope.edition = function(){
        $scope.edit = true;
        $scope.button_clicked = true;
      }
      
   // annule l'édition
      $scope.cancel = function(){
          history.back();
      } 
//      $scope.cancel = function(){
//          if($routeParams.id == "nouveau"){
//            $location.path('/admin/qualificatifs');
//          } else {
//          	$location.path('qualificatif/'+$stateParams.id);
//          	$scope.edit = false;
//          }
//        } 
    }]
  );
}).call(this);
