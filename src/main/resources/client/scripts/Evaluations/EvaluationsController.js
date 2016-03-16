/*
 * @author LAKRAA
 */
(function() {
  'use strict';

  var app = angular.module('app.evaluations', []);

  app.factory('evaluationsFactory', function($http, $window){
            
    return {
      // renvoi la liste de tous les evaluations
      all: function() { 
    	  return $http.get('http://localhost:8090/evaluations');
      },
      // renvoi la evaluation avec le code demandé
      get: function(code) { 
    	  return $http.get('http://localhost:8090/findEvaluationById-' + code);    
      },
      set: function(evaluation) {	
    	  return $http.post('http://localhost:8090/updateEvaluation', evaluation);
      },
      add: function(evaluation) {
    	  return $http.post('http://localhost:8090/addEvaluation', evaluation)
      },
      delete: function(idEvaluation) { 
    	  return $http.get('http://localhost:8090/deleteEvaluation-' + idEvaluation);
      },
      getQualificatif: function(idevaluation){
    	  //return $http.get('http://localhost:8090/getQualificatif/' + idevaluation);
      },
      getDomain : function(){
    	  return $http.get("http://localhost:8090/getDomaine/ETAT-EVALUATION");
      },
      ECfindByCodeFormation: function(codeFormation){
    	  console.log("elementConstitutif -> code: ", codeFormation);
    	  return $http.get("http://localhost:8090/elementConstitutif/findByCodeFormation/"+codeFormation);
      },
      UEfindByCodeFormation: function(codeFormation){
    	  console.log("UE -> code: ", codeFormation);
    	  return $http.get("http://localhost:8090/UE/findByCodeFormation/"+codeFormation);
      },
      
      promotionfindByCodeFormation: function(codeFormation){
    	  console.log("Promotion -> code: ", codeFormation);
    	  return $http.get("http://localhost:8090/promotion/findByCodeFormation/"+codeFormation);
      },
      listeUE: function(){
    	  return $http.get("http://localhost:8090/getUEByNoEnseignant");
      },
      
      listeEC:function(uePK){
    	  console.log("uePK:",uePK);
    	  return $http.post("http://localhost:8090/getECByUE",uePK);
      },
      listePromotion:function(){
    	  return $http.get("http://localhost:8090/getPromoByNoEnseignant");
      }
      
    };
  });
    
  app.controller('EvaluationsController', 
    ['$scope', '$location','$http','$filter', 'evaluationsFactory',
    function($scope, $location,$http,$filter, evaluationsFactory){
      // la liste globale des evaluations
    	$scope.refresh = function (){
    		var init= null;
    		 var promiseEvaluation = evaluationsFactory.all();          
    		 promiseEvaluation.success(function(data) {
    			  $scope.evaluations = data;
    		      $scope.searchKeywords = '';
    		      $scope.filteredEvaluation = [];
    		      $scope.row = '';
    		      $scope.select = function(page) {
    		        var end, start;
    		        start = (page - 1) * $scope.numPerPage;
    		        end = start + $scope.numPerPage;
    		        return $scope.currentPageEvaluation = $scope.filteredEvaluation.slice(start, end);
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
    		        $scope.filteredEvaluation = $filter('filter')($scope.evaluations, $scope.searchKeywords);
    		        return $scope.onFilterChange();
    		      };
    		      $scope.order = function(rowName) {
    		        if ($scope.row === rowName) {
    		          return;
    		        }
    		        $scope.row = rowName;
    		        $scope.filteredEvaluation = $filter('orderBy')($scope.evaluations, rowName);
    		        return $scope.onOrderChange();
    		      };
    		      $scope.numPerPageOpt = [3, 5, 10, 20];
    		      $scope.numPerPage = $scope.numPerPageOpt[2];
    		      $scope.currentPage = 1;
    		      $scope.currentPageEvaluation = [];
    		      var init = function() {
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
     
      // Crée la page permettant d'ajouter une evaluation
      $scope.ajoutEvaluation = function(){
        $location.path('/admin/evaluation/nouveau'); 
      }

      // affiche les détails d'une evaluation
      $scope.edit = function(evaluation){
    	  console.log("avant modification "+evaluation.idEvaluation);
        $location.path('/admin/evaluation/' + evaluation.idEvaluation);
        console.log("après modification ",evaluation);
      }

      // supprime une evaluation
      $scope.supprime = function(evaluation){

    	  swal({   
			  title: "Voulez-vous vraiment supprimer cette evaluation ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "Oui, je veux le supprimer!",  
			  cancelButtonText: "Non, ignorer!",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
			    	  var promisessuppression  = evaluationsFactory.delete(evaluation.idEvaluation);
			    	  promisessuppression.success(function(data, status, headers, config) {
			  			$scope.refresh();
						swal("Supprimé!", "la evaluation est supprimée", "success");
			      	  });
			    	  promisessuppression.error(function(data, status, headers, config) {
			    		  swal("Erreur!", "vous pouvez pas supprimer cette evaluation", "error");
			  		});	
				  } else {     
						  swal("Ignorer", "", "error");
				  }
	  	 });
      }
      
      $scope.refresh();
    }]
  );

  app.controller('EvasDetailsController', 
    ['$scope', '$routeParams','$http', '$location','$filter', 'evaluationsFactory', 'promotionsFactory', 'toaster',
    function($scope, $routeParams, $http, $location,$filter, evaluationsFactory,  promotionsFactory, toaster){      
      $scope.edit= false;    
      
      // si creation d'une nouvelle evaluation
      if($routeParams.id == "nouveau"){
        $scope.evaluation= {};
        $scope.edit= true;
        var promiseDomain = evaluationsFactory.getDomain();
 		promiseDomain.success(function(data) { 
 			console.log("etats: ",data);
 			$scope.etats = data;
 			$scope.domains = data;
 			//$scope.selectedOption = data[0];
 		})
 		// Récuperation des formations
        var promise2= promotionsFactory.getFormations();
        promise2.success(function(data,statut){
        	$scope.formations= data;
        	console.log("\tFormations récupérées: ", data);
        })
        .error(function(data,statut){
      	  console.log("impossible de recuperer la liste des formations");
        });
 		// Récuperation du domaine
        var promise2= evaluationsFactory.getDomain();
        promise2.success(function(data,statut){
        	$scope.etats= data;
        	console.log("\tEtats récupérés: ", data);
        })
        .error(function(data,statut){
      	  console.log("impossible de recuperer la liste des etats");
        });
        var Promotionpromise = evaluationsFactory.listePromotion();
    	Promotionpromise.success(function(data,status){
    		$scope.promotionevaluation = data ; 
    	})
          .error(function(data,status){
                	console.log("impossible de recuperer les promotions") ;
          }); 
    	var Uepromise = evaluationsFactory.listeUE(); 
    	Uepromise.success(function(data,status){ 	    		
    		$scope.ues = data ;
    	}) 
    	 .error(function(data,status){
    		 console.log("impossible de recuperer la liste des UE");
    	});
		   
	 } else { // sinon on edite une evaluation existante
		 $scope.edit=true;
        var promisesFactory = evaluationsFactory.get($routeParams.id);
     	promisesFactory.success(function(data) {
     		$scope.isVisible = false;
     		console.log("les données",data);
     		$scope.evaluation = data;   
     		console.log("evaluation: ", $scope.evaluation);
     		$scope.etatSelected = data.etat;
     		$scope.ueSelected = data.code_ue;
     		console.log("ueSelect ",$scope.ueSelected);
     		$scope.ecSelected = data.code_ec;
     	})
     	.error(function(data,status){
      		 console.log("impossible de recuperer l'évaluation");
      	});
     	var promise2= promotionsFactory.getFormations();
        promise2.success(function(data,statut){
        	$scope.formations= data;
        	console.log("\tFormations récupérées: ", data);
        })
        .error(function(data,statut){
      	  console.log("impossible de recuperer la liste des formations");
        });
 		// Récuperation du domaine
        var promise2= evaluationsFactory.getDomain();
        promise2.success(function(data,statut){
        	$scope.etats= data;
        	console.log("\tEtats récupérés: ", data);
        })
        .error(function(data,statut){
      	  console.log("impossible de recuperer la liste des etats");
        });
        var Promotionpromise = evaluationsFactory.listePromotion();
    	Promotionpromise.success(function(data,status){
    		$scope.promotionevaluation = data ; 
    	})
          .error(function(data,status){
                	console.log("impossible de recuperer les promotions") ;
          }); 
    	var Uepromise = evaluationsFactory.listeUE(); 
    	Uepromise.success(function(data,status){ 	    		
    		$scope.ues = data ;
    	}) 
    	 .error(function(data,status){
    		 console.log("impossible de recuperer la liste des UE");
    	});
      }
      $scope.edition = function(){
    	  var promisessuppression = evaluationsFactory.set($scope.evaluation);    	  
    	  evaluationsFactory.get($scope.evaluation);
    	  
          $scope.edit = true;
        }

        $scope.submit = function(){
        	
	        $scope.evaluation.code_formation= $scope.selectedPromotion.promotionPK.codeFormation;
	        console.log($scope.selectedPromotion.promotionPK.codeFormation);
	        console.log($scope.selectedPromotion);
	        if($scope.ecSelected == undefined){
	        	$scope.evaluation.code_ec = null;
	        }else{
	        $scope.evaluation.code_ec= $scope.ecSelected.elementConstitutifPK.codeEc;
	        }
	        console.log($scope.ecSelected);
	        $scope.evaluation.code_ue= $scope.ueSelected.uniteEnseignementPK.codeUe;
	        console.log($scope.ueSelected);
	        $scope.evaluation.etat= $scope.etatSelected;
	        $scope.evaluation.annee= $scope.selectedPromotion.promotionPK.anneeUniversitaire;
	        console.log($scope.selectedPromotion.promotionPK.anneeUniversitaire);
	        console.log("submit :",$scope.evaluation);
//	        var date1 = $scope.evaluation.debutReponse.split('/');
//	        $scope.evaluation.debutReponse = new Date(date1[2]+'-'+date1[1]+'-'+date1[0]);
//	        var date2 = $scope.evaluation.finReponse.split('/');
//	        $scope.evaluation.finReponse = new Date(date2[2]+'-'+date2[1]+'-'+date2[0]);
	        if($routeParams.id == "nouveau"){
        	var promiseajout = evaluationsFactory.add($scope.evaluation);}
	        else{var promiseajout = evaluationsFactory.set($scope.evaluation);}
        	promiseajout.success(function(data, status, headers, config) {
        		console.log("true :",$scope.evaluation);
        		if($routeParams.id === "nouveau") 
        			swal("Félicitation!", "La nouvelle evaluation est ajoutée!", "success");
        		else
        			swal("Félicitation!", "La evaluation est modifiée !", "success");
        			
        		$location.path('/admin/evaluations');
				
			});
        	promiseajout.error(function(data, status, headers, config) {
        		toaster.pop({
                    type: 'error',
                    title: 'Insertion ou modification impossible. ID evaluation existe déja !',
                    positionClass: 'toast-bottom-right',
                    showCloseButton: true
                });
        	});
			$scope.edit = false;  
        }

      $scope.edition = function(){
        $scope.edit = true;
      }
      
      // valide le formulaire d'édition d'une evaluation
      
      // TODO coder une fonction submit permettant de modifier une evaluation
		// et rediriger vers /admin/evaluations

      $scope.onchange = function(){
			$scope.ue = $scope.ueSelected.uniteEnseignementPK;
			console.log("change ue"+$scope.ue);
			var UCpromise = evaluationsFactory.listeEC($scope.ue); 
	    	UCpromise.success(function(data,status){
	    		console.log("on success ucpromise");
	    		$scope.ecs = data ;
	    	}) .error(function(data,status){
	    		 console.log("impossible de recuperer la liste des UE");
	    	 });
			
		};
		
		$scope.onchangeEC = function(){
			$scope.ec = $scope.ecSelected;
			console.log("onchangeEc : "+$scope.ec);
		}
		
		$scope.onchangePro = function(){
			$scope.promotion = $scope.selectedPromotion;
		}
   // annule l'édition
      $scope.cancel = function(){
    	  //console.log($scope.evaluation);
        if(!$scope.evaluations){
          $location.path('/admin/evaluations');
        } else {
        	$location.path('/admin/evaluations');
          var e = evaluationFactory.get($routeParams.id);
          $scope.evaluations = JSON.parse(JSON.stringify(e));
          $scope.edit = false;
        }
      } 

    }]
  );
}).call(this);

