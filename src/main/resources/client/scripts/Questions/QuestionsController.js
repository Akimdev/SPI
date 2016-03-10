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
      $location.path('/admin/question/nouveau'); 
   }
  
 
  $scope.edit = function (question){
	  $location.path("/admin/question/"+ question.idQuestion);
	 
  }

      // supprime une question
      $scope.supprime = function(question){
    	  swal({   
			  title: "Voulez-vous vraiment supprimer cette question ?",      
			  type: "warning",   
			  showCancelButton: true,   
			  confirmButtonColor: "#DD6B55",   
			  confirmButtonText: "Oui, je veux le supprimer!",  
			  cancelButtonText: "Non, ignorer!",   
			  closeOnConfirm: false,   closeOnCancel: false },
			  function(isConfirm){
				  if (isConfirm) {  
			    	  var promisessuppression  = questionsFactory.delete(question.idQuestion);
			    	  promisessuppression.success(function(data, status, headers, config) {
						swal("Supprimé!", "La question est supprimée", "success");
			    		$scope.refresh();
			      	  });
			    	  promisessuppression.error(function(data, status, headers, config) {
			    		  swal("Erreur!", "Vous ne pouvez pas supprimer cette question", "error");
			  		});	
				  } else {     
						  swal("Ignorer", "", "error");
				  }
	  	 });    	  
      }
      
      
    }]
  );

  app.controller('QuestionDetailsController', 
    ['$scope', '$routeParams','$http', '$location','$filter', 'questionsFactory', 'qualificatifsFactory', 'toaster',
    function($scope, $routeParams, $http, $location,$filter, questionsFactory, qualificatifsFactory, toaster){      
      $scope.edit= false;    
      
      // si creation d'une nouvelle question
      if($routeParams.id == "nouveau"){
        $scope.question= { };
        $scope.edit= true;
 		var promiseQualificatifs = qualificatifsFactory.all();
 		promiseQualificatifs.success(function(data) {   
 			$scope.qualificatifs = data;
 		});
 		
 		var promise = questionsFactory.getMaxIdQuestion();
		promise.success(function(data){
			$scope.question.idQuestion = data + 1;
		});
 		
	 } else { // sinon on edite une question existante
        var promisesFactory = questionsFactory.get($routeParams.id);
     	promisesFactory.success(function(data) {
     		$scope.isVisible = true;
     		$scope.question = data;   
     		var promiseQualificatifs = qualificatifsFactory.all();
     		promiseQualificatifs.success(function(data) {   
     			var promiseQualif = questionsFactory.getQualificatif($routeParams.id);
         		promiseQualif.success(function(result){
         			$scope.qualif = result;
	     			$scope.qualificatifs = data;
	     			$scope.selectedOption = result;
         		})
         		.error(function(data){
         			toaster.pop({
                        type: 'error',
                        title: 'Impossible de récupérer le qualificatif de cette question !',
                        positionClass: 'toast-bottom-right',
                        showCloseButton: true
                    });
         		});
     		})
     		.error(function(data) {   
     			toaster.pop({
                    type: 'error',
                    title: 'Impossible de récupérer les qualificatifs !',
                    positionClass: 'toast-bottom-right',
                    showCloseButton: true
                });
     		});
     		
     	});
      }
      
      $scope.edition = function(){
    	  // var promisessuppression = questionsFactory.set($scope.question);    	  
    	  // questionsFactory.get($scope.question);
          $scope.edit = true;
      }

	    $scope.submit = function(){
	    	var idQualificatif;
	    	if(typeof $scope.qualificatif === 'undefined')
	    		idQualificatif = $scope.qualif.idQualificatif;
	    	else
	    		idQualificatif = $scope.qualificatif;
	    	
	    	var quesQual = {
		    			"qualificatif" : {
		    				"idQualificatif" :idQualificatif
		    			},
		    			"question" : {
		    				"idQuestion" : $scope.question.idQuestion,
			    			"intitulé" : $scope.question.intitule,
			    			"type" : $scope.question.type
			    		}
    			}
	    	
	    	console.log(quesQual);
	    	
	    	var promisesajout;
	    	if($routeParams.id == "nouveau")
		    	promisesajout = questionsFactory.add(quesQual);
	    	else
	    		promisesajout = questionsFactory.set(quesQual);
	    	   	
        	promisesajout.success(function(data, status, headers, config) {
	    		if($routeParams.id == "nouveau")
	    			swal("Félicitation!", "La question est ajoutée!", "success");
	    		else
	    			swal("Félicitation!", "La question est modifiée!", "success");
        		
	    		var promise = questionsFactory.getQualificatifById(idQualificatif);
	    		
	    		promise.success(function(data){
	    			$scope.qualif = data;
	    		})
	    		.error(function(data){
	    			swal("Erreur !", "Impossible de récupérer la question !", "error");
	        		$location.path('/admin/questions');
	    		});
	    		$location.path('/admin/question/' + $scope.question.idQuestion);	
			})
			.error(function(data, status, headers, config) {
        		toaster.pop({
                    type: 'error',
                    title: 'Insertion ou modification impossible !',
                    positionClass: 'toast-bottom-right',
                    showCloseButton: true
                });
        	});		
		    
          $scope.edit = false;
        }

      $scope.edition = function(){
        $scope.edit = true;
      }

      // valide le formulaire d'édition d'une question
      
      // TODO coder une fonction submit permettant de modifier une question
		// et rediriger vers /admin/questions


   // annule l'édition
      $scope.cancel = function(){
        if($routeParams.id == "nouveau"){
          $location.path('/admin/questions');
        } else {
        	$location.path('/admin/question/' + $routeParams.id);
          //var e = questionFactory.get($routeParams.id);
          $scope.edit = false;
        }
      }
    }]
  );
}).call(this);

