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
        //var defer = $q.defer();

        return  $http.get('http://localhost:8090/ens')
        
       };
    	
            
    var details = [ 
      // Constituer le délail de la liste des enseignants ici
    ];

    return {
      // renvoi la liste de tous les enseignants
      all:list,// function() { //return list; 

     // },
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
//   	 add: function(enseignant){
//   	  return $http({
//   		  method: 'POST',
//   		  url: 'http://localhost:8090/ajouterEnseignant',
//   		  data: enseignant,
//   		  headers:{ 'Content-Type' : 'application/json'}
//   	  });
//   	},
//     
   	set: function(enseignant) {
  	  return $http({
  		  method: 'POST',
  		  url: 'http://localhost:8090/updateEnseignant',
  		  data: enseignant,
  		  headers:{ 'Content-Type' : 'application/json'}
  	  });
    },
//      set: function(enseignant) {
//        var idx = enseignant.noEnseignant;
//        // si modification d'un enseignant existant
//        if(idx){
//          // TODO alimenter l'objet enseignant trouvé
//        	console.log("TODO : update enseignant",idx);
//        	//list.removeValue("no_enseignant",enseignant.no_enseignant);
//        	//return list.push(enseignant);
//        	
//        	var newEnseignant = {
//          		      "noEnseignant" : idx,
//         			  "nom" : enseignant.nom,
//         			  "prenom" : enseignant.prenom,
//         			  "type" : enseignant.type,
//         			  "sexe" : enseignant.sexe,
//         			  "adresse" : enseignant.adresse,
//         			  "codePostal" : enseignant.codePostal,
//         			  "ville" : enseignant.ville,
//         			  "pays" : enseignant.pays,
//         			  "mobile" : enseignant.mobile,
//         			  "telephone" : enseignant.telephone,
//         			  "emailPerso" : enseignant.emailPerso,
//         			  "emailUbo" : enseignant.emailUbo,
//         		  };      	  
//  	        	$http.post('http://localhost:8090/updateEnseignant',newEnseignant);
//        } else { // si ajout d'un nouvel enseignant
//          // TODO ajouter un enseignant à la liste
//        	
//        	  var newEnseignant = {
//        		  "noEnseignant" : "8",
//       			  "nom" : enseignant.nom,
//       			  "prenom" : enseignant.prenom,
//       			  "type" : enseignant.type,
//       			  "sexe" : enseignant.sexe,
//       			  "adresse" : enseignant.adresse,
//       			  "codePostal" : enseignant.codePostal,
//       			  "ville" : enseignant.ville,
//       			  "pays" : enseignant.pays,
//       			  "mobile" : enseignant.mobile,
//       			  "telephone" : enseignant.telephone,
//       			  "emailPerso" : enseignant.emailPerso,
//       			  "emailUbo" : enseignant.emailUbo,
//       		  };      	  
//	        	$http.post('http://localhost:8090/ajouterEnseignant',newEnseignant);
//        	
//        	//return list.push(enseignant);
//        }
//      },
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
      // TODO Lien vers la page permettant de créer un enseignant /admin/enseignant/nouveau
      $scope.ajoutEnseignant = function(){
          $location.path('/admin/enseignant/nouveau'); 
       }
      
      // affiche les détail d'un enseignant
      // TODO Lien vers la page permettant d'éditer un enseignant /admin/enseignant/ + no_enseignant
      $scope.edit = function (noEnseignant){
    	  $location.path("/admin/enseignant/"+ noEnseignant);
    	  //alert(enseignant.no_enseignant);
      }

      // supprime un enseignant					
//      $scope.supprime = function(noEnseignant){ 
//    	// TODO Suppression d'un enseignant de la liste
//    	  
//    	  
//    	  var promise= enseignantsFactory.delete(noEnseignant);
//          promise.success(function(data,statut){
//        	  //$scope.enseignant.promotions = data ;
//        	  $scope.currentPageEnseignant.removeValue("noEnseignant",noEnseignant);
//          })
//          .error(function(data,statut){
//        	  console.log("impossible de supprimer l'enseignant choisi");
//          });
//    	  
//      }
//    }]
//  );
  
  $scope.supprime = function(noEnseignant){
	  swal({   
		  title: "Etes-vous sûr de vouloir supprimer cet enseignant ?",      
		  type: "warning",   
		  showCancelButton: true,   
		  confirmButtonColor: "#DD6B55",   
		  confirmButtonText: "Oui!",  
		  cancelButtonText: "Non!",   
		  closeOnConfirm: false,   closeOnCancel: false },
		  function(isConfirm){
			  if (isConfirm) {
				  var promise= enseignantsFactory.delete(noEnseignant);
		          promise.success(function(data,statut, headers, config){
		        	 // $scope.filteredEnseignant.removeValue("noEnseignant",noEnseignant);
		        	  //$scope.refresh();
		        	  swal("Supprimé!", "l'enseignant est supprimé", "success");
		        	  $scope.refresh();
		          });
		          promise.error(function(data,statut, headers, config){
		        	  swal("Erreur!", "Impossible de supprimer l'enseignant choisi", "error");
		          });
			  } else {     
					  swal("Ignorer", "", "error");
			  }
  	 });
  }
  $scope.refresh();
}]
);

  app.controller('EnsDetailsController', 
    ['$scope', '$routeParams','$http', '$location','$filter', 'enseignantsFactory','toaster',
    function($scope, $routeParams , $http, $location, $filter, enseignantsFactory, toaster){      
      $scope.edit= false;    

      // si creation d'un nouvel enseignant
      if($routeParams.id == "nouveau"){
        $scope.enseignant= { };
        $scope.edit= true;  
//        var promiseDomain = enseignantsFactory.getDomain();
// 		promiseDomain.success(function(data) { 
// 			console.log("types: ",data);
// 			$scope.types = data;
// 			$scope.domains = data;
// 			//$scope.selectedOption = data[0];
// 		});
// 		var promise2= enseignantsFactory.getDomain();
//        promise2.success(function(data,statut){
//        	$scope.types= data;
//        	console.log("\tTypes récupérés: ", data);
//        })
//        .error(function(data,statut){
//      	  console.log("impossible de recuperer la liste des types");
//        });
//        
//        var promisePays = enseignantsFactory.getPays();
//        promisePays.success(function(data) { 
// 			console.log("payss: ",data);
// 			$scope.payss = data;
// 			$scope.domains = data;
// 			//$scope.selectedOption = data[0];
// 		});
// 		var promisePays= enseignantsFactory.getPays();
// 		promisePays.success(function(data,statut){
//        	$scope.payss= data;
//        	console.log("\tPays récupérés: ", data);
//        })
//        .error(function(data,statut){
//      	  console.log("impossible de recuperer la liste des pays");
//        });
      } else { // sinon on edite un enseignant existant
    	
        var promise = enseignantsFactory.get($routeParams.id);
        promise.success(function(data){
      	  $scope.enseignant = data ;
            var promise= enseignantsFactory.getPromotion($routeParams.id);
            promise.success(function(data,statut){
          	  $scope.enseignant.promotions = data ;
            })
            .error(function(data,statut){
          	  console.log("impossible de recuperer les details de l'enseignant choisi");
            });
            
            var promise= enseignantsFactory.getUE($routeParams.id);
            promise.success(function(data,statut){
          	  $scope.enseignant.ue = data ;
            })
            .error(function(data,statut){
          	  console.log("impossible de recuperer les details de l'enseignant choisi");
            });
        })
        .error(function(data){
      	  console.log("impossible de recuperer les details de l'enseignant choisi");
        });
      }
//      var promiseDomain = enseignantsFactory.getDomain();
//		promiseDomain.success(function(data) { 
//			console.log("types: ",data);
//			$scope.types = data;
//			$scope.domains = data;
//			//$scope.selectedOption = data[0];
//		});
		var promise2= enseignantsFactory.getDomain();
      promise2.success(function(data,statut){
      	$scope.types= data;
      	console.log("\tTypes récupérés: ", data);
      })
      .error(function(data,statut){
    	  console.log("impossible de recuperer la liste des types");
      });
      
//      var promisePays = enseignantsFactory.getPays();
//      promisePays.success(function(data) { 
//			console.log("payss: ",data);
//			$scope.payss = data;
//			$scope.domains = data;
//			//$scope.selectedOption = data[0];
//		});
		var promisePays= enseignantsFactory.getPays();
		promisePays.success(function(data,statut){
      	$scope.payss= data;
      	console.log("\tPays récupérés: ", data);
      })
      .error(function(data,statut){
    	  console.log("impossible de recuperer la liste des pays");
      });

      $scope.edition = function(){
        $scope.edit = true;
      }
     // $scope.submit = function(){    	 
	//		        enseignantsFactory.add($scope.enseignant);        
	//		        $scope.edit = false;        
	//		      }
   // valide le formulaire d'édition d'un enseignant
      $scope.submit = function(){
    	  $scope.enseignant.type= $scope.typeSelected;
    	  $scope.enseignant.pays= $scope.paysSelected;
		if($routeParams.id == "nouveau"){
	        	var promisesajout = enseignantsFactory.add($scope.enseignant);
	        	console.log($scope.enseignant);
	        	promisesajout.success(function(data, status, headers, config) {
	        		swal("Félicitation!", "Le nouveau enseignant est ajouté!", "success");
				});
	        	promisesajout.error(function(data, status, headers, config) {
	        		toaster.pop({
	                    type: 'error',
	                    title: 'Insertion ou modification impossible. noEnseignant déjà	 existant',
	                    positionClass: 'toast-bottom-right',
	                    showCloseButton: true
	                });
				});
	        	
	        	$location.path('/admin/enseignants');
								        }
			 else{ // modification
	        	var promisesajout = enseignantsFactory.set($scope.enseignant);
	        	promisesajout.success(function(data, status, headers, config) {
	        		swal("Félicitation!", "Enseignant modifié!", "success");
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
      $scope.edition = function(){
          $scope.edit = true;
          $scope.button_clicked = true;
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
