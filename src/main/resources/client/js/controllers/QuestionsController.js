/*
 * @author Youssef
 */
(function() {
  'use strict';

  var app = angular.module('app.questions', []);

  Array.prototype.removeValue = function(name, value){
	   var array = $.map(this, function(v,i){
	      return v[name] === value ? null : v;
	   });
	   this.length = 0; //clear original array
	   this.push.apply(this, array); //push all elements except the one we want to delete
	}
  
  app.factory('questionsFactory', function($http, $window){
            
    return {
      // renvoi la liste de tous les questions
      all: function() { 
    	  return $http.get('http://localhost:8090/questions');
      },
      // renvoi la question avec le code demandé
      get: function(code) { 
    	  return $http.get('http://localhost:8090/getQuestionById/' + code);    
      },
      set: function(question) {	
    	  return $http.post('http://localhost:8090/updateQuestion', question);
      },
      add: function(question) {
    	  return $http.post('http://localhost:8090/addQuestion', question)
      },
      delete: function(idQuestion) { 
    	  return $http.get('http://localhost:8090/deleteQuestionById-' + idQuestion);
      },
      getQualificatif: function(idQuestion){
    	  return $http.get('http://localhost:8090/getQualificatif/' + idQuestion);
      },
      getMaxIdQuestion: function(){
    	  return $http.get('http://localhost:8090/getMaxIdQuestion');
      },
      getQualificatifById: function(qualificatif){
    	  return $http.get('http://localhost:8090/qualificatif/' + qualificatif);
      }
    };
  });
  
  
  app.controller('QuestionsController', 
    ['$scope', '$filter','$location', 'questionsFactory',
    function($scope, $filter,$location, questionsFactory){
    	var init;
    	var promise = questionsFactory.all();
    	$scope.refresh = function() {
        	promise.success(function(data) {
    		    $scope.questions = data;
    		      $scope.searchKeywords = '';
    		      $scope.filteredQuestion = [];
    		      $scope.row = '';
    		      $scope.select = function(page) {
    		        var end, start;
    		        start = (page - 1) * $scope.numPerPage;
    		        end = start + $scope.numPerPage;
    		        return $scope.currentPageQuestion = $scope.filteredQuestion.slice(start, end);
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
    		        $scope.filteredQuestion = $filter('filter')($scope.questions, $scope.searchKeywords);
    		        return $scope.onFilterChange();
    		      };
    		      $scope.order = function(rowName) {
    		        if ($scope.row === rowName) {
    		          return;
    		        }
    		        $scope.row = rowName;
    		        $scope.filteredQuestion = $filter('orderBy')($scope.questions, rowName);
    		        return $scope.onOrderChange();
    		      };
    		      $scope.numPerPageOpt = [3, 5, 10, 20];
    		      $scope.numPerPage = $scope.numPerPageOpt[2];
    		      $scope.currentPage = 1;
    		      $scope.currentPageQuestion = [];
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
     
  $scope.ajoutQuestion = function(){
      $location.path('/question/nouveau'); 
   }
  
 
  $scope.edit = function (question){
	  $location.path("/question/"+ question.idQuestion);
	 
  }

      // supprime une question
      $scope.supprime = function(question){
    	  swal({   
			  title: "Voulez-vous vraiment supprimer cette question ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "OUI",  
			  cancelButtonText: "NON",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
			    	  var promisessuppression  = questionsFactory.delete(question.idQuestion);
			    	  promisessuppression.success(function(data, status, headers, config) {
						swal("Supprimé!", "La question est supprimée", "success");
			    		$scope.refresh();
			      	  });
			    	  promisessuppression.error(function(data, status, headers, config) {
			    		  swal("Erreur!", "Impossible de supprimer la question car elle est déjà referencée", "error");
			  		});	
				  } else {     
						  swal("Annulé", "", "error");
				  }
	  	 });    	  
      }
      $scope.refresh();
      
      $scope.edit = function (question){
    	  $location.path("/question/"+ question.idQuestion);
      }
      $scope.show=function(question){
    	  $location.path("/question/infos/"+ question.idQuestion);
      }
      
    }]
  );

  app.controller('QuestionDetailsController', 
    ['$scope', '$stateParams','$http', '$location','$filter', 'questionsFactory', 'qualificatifsFactory', 'toaster',
    function($scope, $stateParams, $http, $location,$filter, questionsFactory, qualificatifsFactory, toaster){      
      $scope.edit= false;    
      var promiseQualificatifs = qualificatifsFactory.all();
		promiseQualificatifs.success(function(data) {   
			$scope.qualificatifs = data;
		});  
      /** si creation d'une nouvelle question **/
      if($stateParams.id == "nouveau"){
        $scope.question= { };
        $scope.edit= true;
 	} else if($stateParams.infos) { 
	/** sinon on edite une question existante **/
		$scope.edit=false;
        var promisesFactory = questionsFactory.get($stateParams.id);
     	promisesFactory.success(function(data) {
     		$scope.question = data;   
     		var promiseQualif = questionsFactory.getQualificatif($stateParams.id);
         		promiseQualif.success(function(result){
         			$scope.qualif = result;
         			for(var i=0; i<$scope.qualificatifs.length; i++){
           				if(angular.equals($scope.qualificatifs[i], result))
           					$scope.qualifSelected= $scope.qualificatifs[i];
           			}
	     	}).error(function(data){
         			});
         	}).error(function(data) {   
            });
     }else{
    	/** modification d'une question **/
    	$scope.edit=true;
    	var promisesFactory = questionsFactory.get($stateParams.id);
       		promisesFactory.success(function(data) {
       		$scope.question = data;   
       		var promiseQualif = questionsFactory.getQualificatif($stateParams.id);
           		promiseQualif.success(function(result){
           			for(var i=0; i<$scope.qualificatifs.length; i++){
           				if(angular.equals($scope.qualificatifs[i], result))
           					$scope.qualifSelected= $scope.qualificatifs[i];
           			}
  	     		}).error(function(data){
                });
       		}).error(function(data){
       		});
         }
       	 
      
      
      $scope.edition = function(){
    	$scope.edit = true;
      }

	    $scope.submit = function(){
	    	var idQualificatif;
	    	var idQualNumber;
	    	idQualificatif = $scope.qualifSelected.idQualificatif;
//	    	if($scope.qualificatif){
//	    		idQualificatif = $scope.qualif.idQualificatif;
//	    		idQualNumber=parseInt(idQualificatif);
//	    	}
//	    	else{
//	    		//idQualificatif = $scope.qualificatif;
//	    	    idQualNumber=parseInt(idQualificatif);
//	    	}
	    	
	    	var quesQual = {
		    			"qualificatif" : {
		    				"idQualificatif" :idQualificatif
		    			},
		    			"question" : {
		    				"idQuestion" : $scope.question.idQuestion,
			    			"intitule" : $scope.question.intitule,
			    			"type" : $scope.question.type
			    		}
    			}
	    	
	    	console.log(quesQual);
	    	
	    var promisesajout;
	    	if($stateParams.id == "nouveau"){
	    		quesQual.question.idQuestion = 0;
	    		quesQual.question.type = "QUS";
	    		promisesajout = questionsFactory.add(quesQual);
	    	}else{
	    		promisesajout = questionsFactory.set(quesQual);
	    	}
	    		
	    	promisesajout.success(function(data, status) {
	    		if($stateParams.id == "nouveau"){
	    			swal("Félicitation!", "La nouvelle question est ajoutée !", "success");
	    			history.back();
	    		}else{
	    			swal("Félicitation!", "La question est modifiée!", "success");
	    			history.back();
	    		}
	    var promise = questionsFactory.getQualificatifById(idQualificatif);
	    		
	    		promise.success(function(data){
	    			$scope.qualif = data;
	    		})
	    		.error(function(data){
	    			swal("Erreur !", "Impossible de récupérer la question !", "error");
	        		$location.path('/questions');
	    		});
	    		$location.path('/question/' + $scope.question.idQuestion);	
			})
			.error(function(data, status, headers, config) {
				console.log("erreur");
        	});		
          $scope.edit = false;
        }

      $scope.edition = function(){
        $scope.edit = true;
      }
  /** annule l'édition **/
      $scope.cancel = function(){
    	  history.back();
      }
    }]
  );
}).call(this);
