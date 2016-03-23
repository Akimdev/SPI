/*
 * @author Othman
 */
 
(function() {
  'use strict';

  var app = angular.module('app.etudiantEvaluations', []);
  app.factory('etudiantEvaluationsFactory', function($http, $window){
            
    return {
      // renvoi la liste de tous les evaluations
      all: function(promotionPK) { 
    	  return $http.post('http://localhost:8090/getEvaluationsPromo', promotionPK);
      },
      //récupère la promotion dont appartient l'étudiant connecté
      getPromotion: function(){
    	  return $http.get('http://localhost:8090/getPromoPKByEtudiant');
      },
      // renvoi la evaluation avec le code demandé
      get: function(code) { 
    	  return $http.get('http://localhost:8090/findEvaluationById-' + code);    
      },
      getQualificatif: function(idevaluation){
    	  // return $http.get('http://localhost:8090/getQualificatif/' +
			// idevaluation);
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
   
      rubQueEvaluation : function(idEvaluation){
    	  return $http.get("http://localhost:8090/getRubriqueEvaluationByEvaluation-"+idEvaluation);
      }
      
    };
  });
    
  app.controller('EtudiantEvaluationsController', 
    ['$scope', '$location','$http','$filter', 'etudiantEvaluationsFactory',
    function($scope, $location,$http,$filter, etudiantEvaluationsFactory){
      // la liste globale des evaluations
    	var promisePromoEtu = etudiantEvaluationsFactory.getPromotion();
    	promisePromoEtu.success(function(data){
    		 $scope.promotion = data;
    		 console.log("data promo", data);
    		 var init= null;
    		 var promiseEvaluation = etudiantEvaluationsFactory.all($scope.promotion.promotionPK);          
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
    	})
    	.error(function(data) {
    			 $scope.error = 'unable to get the poneys';
    		  });
    	
     
      /** affiche les détails d'une evaluation * */
      $scope.affiche = function(idEvaluation){
    	 $location.path("/etudiantEvaluations/info/"+idEvaluation);
     }
    }]
  );

  app.controller('EtudiantEvaluationsDetailsController', 
    ['$scope', '$stateParams','$http', '$location','$filter', 'etudiantEvaluationsFactory', 'promotionsFactory', 'toaster',
    function($scope, $stateParams, $http, $location,$filter, etudiantEvaluationsFactory,  promotionsFactory, toaster){      
      $scope.edit= false;    
      
      if($stateParams.infos && $stateParams.id){ 
    	  /** sinon on veut voir les informations d'une evaluation existante * */
		$scope.edit=false;
        var promisesFactory = etudiantEvaluationsFactory.get($stateParams.id);
     		promisesFactory.success(function(data) {
     		data.debutReponse = $filter('date')(data.debutReponse, "dd/MM/yyyy");
     		data.finReponse = $filter('date')(data.finReponse, "dd/MM/yyyy");
     		$scope.evaluation = data;  
     		console.log($scope.evaluation);
     		$scope.etatSelected = data.etat;
     		$scope.ueSelected = data.code_ue;
     		$scope.ecSelected = data.code_ec;
     	})
     	.error(function(data,status){
      		 console.log("impossible de recuperer l'évaluation");
      	});
     	/** Récuperation du domaine **/
            var promise2= etudiantEvaluationsFactory.getDomain();
            promise2.success(function(data,statut){
            	$scope.etats= data;
            	console.log("\tEtats récupérés: ", data);
            })
            .error(function(data,statut){
          	  console.log("impossible de recuperer la liste des etats");
            });
        
        /** chargement des rubriques et questions d'une évaluation **/
        	var rubQuesEvapromise = etudiantEvaluationsFactory.rubQueEvaluation($stateParams.id);
        	rubQuesEvapromise.success(function(data,status){
        		$scope.rubriqueEvaluations = data;
        		console.log("rubriqueEvaluation : ",data);
        	})
        	.error(function(data,status){
       		 console.log("impossible de recuperer les rubriques et questions d'une évaluation");
       	});
     }
     
   /** bouton cancel * */
      $scope.cancel = function(){
    	 history.back();
      }
       

    }]
  );
}).call(this);

