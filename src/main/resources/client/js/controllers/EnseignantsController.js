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
        /*.then(function(response) {
           defer.resolve(response.data);
         });

         return defer.promise;*/
       };
    	/*[ 
      // TODO Constituer la liste des enseignants ici
      {
    	  no_enseignant : "1", nom : "SALIOU", prenom : "Philippe", 
    	  email :"philippe.saliou@gmail.com", sexe : "H", adresse :"6 rue de l'Argoat" ,
    	  code_postal : "29860", ville : "LE DRENNEC", telephone : "06.29.24.01.00",
    	  pays : "FR", type : "MCF"
    		  
      },
      {
    	  no_enseignant : "2", nom : "LALLALI", prenom : "Mounir", 
    	  email :"mouni.lallali@gmail.com", sexe : "H", adresse :"18rue Jean Jaurès" ,
    	  code_postal : "29200", ville : "BREST", telephone : "06.32.03.56.32",
    	  pays : "FR", type : "MCF"
      },
      {
    	  no_enseignant : "3", nom : "LEROUX", prenom : "Pierre", 
    	  email :"pileroux@gmail.com" , sexe : "H", adresse :"65 route de Gouesnou" ,
    	  code_postal : "29200", ville : "BREST", telephone : "06.45.95.47.29",
    	  pays : "FR", type : "INT"
      }
    ];*/
            
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
      // la liste globale des enseignants
      //$scope.enseignants = enseignantsFactory.all();          

      //$scope.formations = [];
      
      /*listfrm.fetchPopular(function(data) {
        	  console.log("TODO 1: entrer fetchpopular");
    			$scope.formations = data;
    			console.log("TODO 2: donnee " + data);
    			console.log("TODO 3: fin fetchpopular");
    		});*/
      
      // Crée la page permettant d'ajouter un enseignant
      // TODO Lien vers la page permettant de créer un enseignant /enseignant/nouveau
      $scope.ajoutEnseignant = function(){
          $location.path('/enseignant/nouveau'); 
       }
      
      // affiche les détail d'un enseignant
      // TODO Lien vers la page permettant d'éditer un enseignant /enseignant/ + no_enseignant
      $scope.edit = function (noEnseignant){
			edit=true;
    	  $location.path("/enseignant/"+ noEnseignant);
    	  //alert(enseignant.no_enseignant);
      	}
	$scope.affiche= function(noEnseignant){
    	  edit= false;
	$location.path("/enseignant/"+ noEnseignant);
		}
      // supprime un enseignant
	$scope.supprime = function(noEnseignant){   
		  swal({   
			  title: "Voulez-vous vraiment supprimer cet enseignant ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "OUI",  
			  cancelButtonText: "NON",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
					  var promise= enseignantsFactory.delete(noEnseignant);
					  promise.success(function(data,statut, headers, config){
						$scope.refresh();
						$scope.currentPageEnseignant.removeValue("noEnseignant",noEnseignant);
						swal("Supprimé!", "l'enseignant est supprimé", "success");
					});
					  promise.error(function(data,statut, headers, config){
			    		  swal("Erreur!", "Impossiple de supprimer l'enseignant choisi car il est déja réfferencié", "error");
			  		});	
					  } else {     
						  swal("Annulé", "", "error");
						  }
				  });  
      }
      $scope.refresh();
    }]
  );

  app.controller('EnsDetailsController', 
    ['$scope', '$stateParams','$http', '$location','$filter', 'enseignantsFactory','toaster',
    function($scope, $stateParams , $http, $location, $filter, enseignantsFactory, toaster){      
      $scope.edit= false;    
     
      // si creation d'un nouvel enseignant
      if($stateParams.id == "nouveau"){
        $scope.enseignant= { };
        $scope.edit= true; 
      } else { // sinon on edite un enseignant existant
        var promise = enseignantsFactory.get($stateParams.id);
        promise.success(function(data){
      	  $scope.enseignant = data ;
            var promise= enseignantsFactory.getPromotion($stateParams.id);
            promise.success(function(data,statut){
          	  $scope.enseignant.promotions = data ;
            })
            .error(function(data,statut){
          	  console.log("impossible de recuperer les details de l'enseignant choisi");
            });
            
            var promise= enseignantsFactory.getUE($stateParams.id);
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
		$scope.edit= edit;
}
/*
        var e = enseignantsFactory.get($routeParams.id);
        //alert(e.nom);
        // clone de l'objet pour conserver l'objet initial
        //$scope.enseignant= JSON.parse(JSON.stringify(e));
        
    	e
		.success(function(data) {
		    $scope.enseignants = data;
		    var promisepromotion = enseignantsFactory.getPromotion(data.noEnseignant);
		    promisepromotion.success(function(data){
		    	$scope.enseignant.promotions = data.promotion;
		    })
		    .error(function(data){
		    	$scope.error = 'unable to get the poneys';
		    })
		    //$scope.enseignant= JSON.parse(JSON.stringify(data));
		  }
		)
		.error(function(data) {
			 $scope.error = 'unable to get the poneys';
		  }
		);*/
   var promise3= enseignantsFactory.getDomain();
      promise3.success(function(data,statut){
      	$scope.types= data;
      	$scope.typeSelected= $scope.enseignant.type;
      	console.log("\tTypes récupérés: ", data);
      })
      .error(function(data,statut){
    	  console.log("impossible de recuperer la liste des types");
      });
	var promisePays= enseignantsFactory.getPays();
		promisePays.success(function(data,statut){
      	$scope.payss= data;
      	$scope.paysSelected=$scope.enseignant.pays;
      	console.log("\tPays récupérés: ", data);
      })
      .error(function(data,statut){
    	  console.log("impossible de recuperer la liste des pays");
      });
    $scope.edition = function(){
        $scope.edit = true;
      }

      // valide le formulaire d'édition d'un enseignant
      /*$scope.submit = function(){ 
	  	$scope.enseignant.type= $scope.typeSelected;
    	$scope.enseignant.pays= $scope.paysSelected;   	 
        enseignantsFactory.set($scope.enseignant);        
        $scope.edit = false;        
      }*/
    		$scope.submit = function(){
    	  $scope.enseignant.type= $scope.typeSelected;
    	  $scope.enseignant.pays= $scope.paysSelected;
    	  console.log($scope.enseignant);
		if($stateParams.id == "nouveau"){
	        	var promisesajout = enseignantsFactory.add($scope.enseignant);
	        	promisesajout.success(function(data, status) {
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
	        	$location.path('/enseignants');
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
        if(!$scope.enseignant.noEnseignant){
          $location.path('/enseignants');
        } else {
          var e = enseignantsFactory.get($stateParams.id);
          $scope.enseignant = JSON.parse(JSON.stringify(e));
          $scope.edit = false;
        }
      }      
    }]
  );
}).call(this);
