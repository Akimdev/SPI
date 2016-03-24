/*
* Author Kenza ABOUAKIL

* Script de controle des promotions
*/
var edit;

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
        return $http.post('http://localhost:8090/updatePromotion',newPromotion);
        },
      delete: function(promotionPK) {
        // Supprimer une promotion
    	return  $http.post('http://localhost:8090/deletePromotion/', promotionPK);
      },
      getEtudiants : function(promotionPK){
    	  	//recuperation des etudiants par promotion
			return $http.post("http://localhost:8090/getEtudiantByPromotion/",promotionPK);
      },
      getEnseignants: function(){
    	  return $http.get("http://localhost:8090/ens");
      },
      getEnseignantResponsable: function(promotionPK){
    	 return $http.post("http://localhost:8090/promotion/getEnseignantResponsable", promotionPK);
      },
      getFormations: function(){
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
    		$scope.ajout= true;
    	  edit = true;
          $location.path('/promotion/nouveau/nouveau/EDITION');
       }
      
      // modifie les détails d'une promotion
    	$scope.edit = function (promotionPK){
    		$scope.ajout= true;
    	  edit=true;
    	  $location.path("/promotion/"+ promotionPK.anneeUniversitaire + "/" + promotionPK.codeFormation+ "/EDITION");
      }
      // affiche les détails d'une promotion
    	$scope.affiche= function(promotionPK){
    		$scope.ajout= false;
    	  edit= false;
    	  $location.path("/promotion/"+ promotionPK.anneeUniversitaire + "/" + promotionPK.codeFormation+ "/c");
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
    	var ctrl = this;
    	var initAjout= function(){
    		ctrl.promotion= { };
    		ctrl.formationSelected= {};
    		if(edit)
    	  		$("select option").prop("selected", false);
    		ctrl.ajout = true;
    	}
    	
    	var initEdition= function(){

    		var promoPK = {anneeUniversitaire:  $stateParams.ann, codeFormation: $stateParams.form};
    		//Recuperation de la promotion
            return promotionsFactory.get(promoPK).then(
            		function(data3,statut){
            			ctrl.dateRentree= $filter('date')(data3.data.dateRentree, "dd/MM/yyyy");
//        	        	data3.data.dateRentree = $filter('date')(data3.data.dateRentree, "dd/MM/yyyy");
        	        	ctrl.promotion= data3.data;
        	        	console.log(ctrl.formations);
        				// select la formation;
        				for (var i=0; i < ctrl.formations.length; i++) {
        					ctrl.formationSelected = {};
        					console.log(""+ctrl.formations[i].codeFormation, ctrl.formations[i].codeFormation == ctrl.promotion.promotionPK.codeFormation);
        	        		if (ctrl.formations[i].codeFormation == ctrl.promotion.promotionPK.codeFormation) {
        	        			ctrl.formationSelected = ctrl.formations[i];
        	        			break;
        	        		}
        	        	}
        				
        	            //Recuperation de l'enseignant responsable
        	            return promotionsFactory.getEnseignantResponsable(promoPK);
        	        },
        	        function(data3,statut){
    		        }
            ).then(
            		function(data1,statut){
            			ctrl.enseignantSelected= {};
            			if(data1.data.nom)
            				data1.data.nom= data1.data.nom.toUpperCase();
            			ctrl.responsable = data1.data;
            			for (var  i=0; i< ctrl.enseignants.length; i++){
            				if(angular.equals(ctrl.enseignants[i], ctrl.responsable))
            					ctrl.enseignantSelected=ctrl.enseignants[i]; 
            			}
    		        	// Initialisation du processusStage dans le cas d'une consultation d'une promotion
        				for(var i=0; i< ctrl.processusStage.length; i++){
        					if(ctrl.processusStage[i].rvAbbreviation == ctrl.promotion.processusStage) {
        						ctrl.processusSignification= ctrl.processusStage[i].rvMeaning;
        						ctrl.processusStageSelected = ctrl.processusStage[i];
        					}
        				}
        				
        				//Recuperation des etudiants  
        				return promotionsFactory.getEtudiants(promoPK);
    		        },function(data1,statut){
    		        }
    		        
            ).then(
            		function(data,statut){
    	            	for(var i=0; i<data.data.length; i++)
    	            		data.data[i].dateNaissance = $filter('date')(data.data[i].dateNaissance, "dd/MM/yyyy");
    	            	ctrl.promotion.etudiantCollection = data.data ;
    	            },
    	            function(data,statut){
      	            }
            );
            ctrl.ajout= false;
    	}
    	
    	this.edit= edit= ($stateParams.edit== "CONSULTATION") ? false : true;
    	//Récupération du domaine des processus de stage
    	$http.get('http://localhost:8090/getDomaine/PROCESSUS_STAGE').then(
    			function(data, status, headers, config) {
    				ctrl.processusStage= [];
    			    for(var i=0; i<data.data.length; i++){
    			    	ctrl.processusStage[i]={};
    			    	ctrl.processusStage[i].rvAbbreviation = data.data[i].rvAbbreviation;
    			    	ctrl.processusStage[i].rvMeaning = data.data[i].rvMeaning;
    			    }
    			    // Récuperation des enseignants
    		        return promotionsFactory.getEnseignants();
    		    },
    		    function(data, status, headers, config) {
			    }
    		    
    	).then(
			    // Récuperation des enseignants
    			function(data1,statut){
		        	for (var d=0; d<data1.data.length; d++) {
		        		if(data1.data[d].nom)
            				data1.data[d].nom= data1.data[d].nom.toUpperCase();
		        	}
		        	ctrl.enseignants= data1.data;
		        	return promotionsFactory.getFormations();
		        },
		        function(data1,statut){
  		        }
		        
    	).then(
		        // Récuperation des formations
    			function(data2,statut){
    				ctrl.formations= data2.data;
		        },
		        function(data2,statut){
  		        }
    	).then(
    			function () {
    			// si creation d'une nouvelle promotion
			      if($stateParams.ann == "nouveau"){
			    	  $scope.ajout= true;
			    	  initAjout();
			      } else { // sinon on edite une promotion existante
		    		  $scope.ajout= false;
			    	  initEdition();
			      }
    			}
    	);

      this.edition = function(){
			$location.path("/promotion/"+ $stateParams.ann + "/" + $stateParams.form+ "/e");
			ctrl.ajout= false;
			ctrl.edit=edit=true;
      }
      
      // valide le formulaire d'édition d'une promotion
      this.submit = function(){
    	  if($stateParams.ann == "nouveau"){
    		  ctrl.promotion.promotionPK.codeFormation= ctrl.formationSelected.codeFormation;
    		  if(ctrl.dateRentree) {
    			  var date = ctrl.dateRentree.split('/');
    			  ctrl.promotion.dateRentree = new Date(date[1] + '-' + date[0] + '-' + date[2]);  
    		  }
    		  if(!ctrl.enseignantSelected) {
    			  ctrl.enseignantSelected= {noEnseignant : null};
    		  }
    		  var promise = promotionsFactory.add(ctrl.promotion, ctrl.enseignantSelected.noEnseignant);
    		  promise.success(function(data){
    			  var promiseEnseignant = promotionsFactory.getEnseignantResponsable(ctrl.promotion.promotionPK);
    			  promiseEnseignant.success(function(data){
    				  ctrl.responsable = data;
    				  swal("Félicitation!", "La nouvelle promotion est ajoutée!", "success");
    	    		  $location.path("/promotions");
    			  });
    			  promiseEnseignant.error(function(){
      				  swal("Erreur !", "La nouvelle promotion ne peut pas être ajoutée !", "error");
    			  })
    		  });
    	  }
    	  else{ // modification
    		  if(ctrl.promotion.dateRentree) {
    			  var date = ctrl.dateRentree.split('/');
    			  ctrl.promotion.dateRentree = new Date(date[1] + '-' + date[0] + '-' + date[2]);  
    		  }
    		  if (ctrl.processusStageSelected) {
    			  ctrl.promotion.processusStage= ctrl.processusStageSelected.rvAbbreviation;
    		  }
    		  var promise = promotionsFactory.set(ctrl.promotion, ctrl.enseignantSelected.noEnseignant);
    		  promise.success(function(){
    			  var promiseEnseignant = promotionsFactory.getEnseignantResponsable(ctrl.promotion.promotionPK);
    			  promiseEnseignant.success(function(data){
    				  ctrl.responsable = data;
    				  swal("Félicitation!", "La promotion est modifiée !", "success");   
    	    		  $location.path("/promotions");
    			  });
    			  promiseEnseignant.error(function(data){
    				  swal("Erreur !", "La promotion ne peut pas être modifiée !", "error");    	        			  
    			  });
    		  });
    	  }
      }
      
      this.etudiantDetails = function(id){
    	  $location.path("/etudiant/"+id);
      }
    }]
  );
}).call(this);
