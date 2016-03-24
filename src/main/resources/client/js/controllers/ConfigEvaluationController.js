/*
* Author Ait Errami Abdelhakim

* Script de controle des elements constitutifs
*/

(function() {
  'use strict';

  var app = angular.module('app.configEvaluation', []);

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
  
  app.factory('configEvalFactory', ['$http',function($http){
	  return {
		  get: function(code) { 
	    	  return $http.get('http://localhost:8090/findEvaluationById-' + code);    
	      },
	      getRubriques: function(){
	    	  return $http.get('http://localhost:8090/rubriques');
	      },
	      getQuestions: function(){
	    	  return $http.get('http://localhost:8090/questions');
	      },
	      getRubrique: function(rubrique){
	    	  return $http.get('http://localhost:8090/rubrique/' + rubrique);
	      },
	      getQuestion: function(question){
	    	  return $http.get('http://localhost:8090/getQuestionById/' + question);
	      },
	      addRubriqueEval: function(rubriqueEval) {
	    	  return $http.post('http://localhost:8090/addRubriqueEvaluation', rubriqueEval);
	      },
	      getRubriqueEval: function(evaluation){
	    	  return $http.get('http://localhost:8090/getRubriqueEvaluationByEvaluation-' + evaluation);
	      },
	      deleteRubrique: function(idRubriqueEvaluation){
	    	  return $http.get('http://localhost:8090/deleteRubriqueEvaluation-' + idRubriqueEvaluation);
	      },
	      addQuestionEval: function(questionEval){
	    	  return $http.post('http://localhost:8090/addQuestionEvaluation', questionEval);
	      },
	      deleteQuestionEval: function(questionEval){
	    	  return $http.get('http://localhost:8090/deleteQuestionEvaluation-' + questionEval);
	      }
	  };
   }]);

  app.controller('ConfigEvaluationController', ['$scope', '$stateParams', '$location', '$filter', 'configEvalFactory',
		    function($scope, $stateParams, $location,$filter, configEvalFactory){ 

	  $scope.rubriques = [];
	  $scope.rubriquesEvaluation = [];
	  $scope.rubriquesSelected = [];
	  $scope.questionsOptions = [];
	  $scope.questionsSelected = [];
	  $scope.questions = [];
	  $scope.currentRubrique = null;
	  var ordre = 0;
	  
	  var promiseRub = configEvalFactory.getRubriques();
	  promiseRub.success(function(data){
		  $scope.rubriquesOptions = data;
	  });
	  
	  var promise = configEvalFactory.getRubriqueEval($stateParams.id);
	  promise.success(function(data){
		  for(var i = 0; i < data.length; i++){
			  $scope.rubriquesSelected.push(data[i].idRubrique);
			  $scope.rubriques.push(data[i].idRubrique);
			  $scope.rubriquesEvaluation.push(data[i]);
			  if(data[i].ordre >= ordre) ordre = data[i].ordre;
		  }
	  });
	  
	  	var promiseQues = configEvalFactory.getQuestions();
	  	promiseQues.success(function(data){
	  		$scope.listQuestionsOptions = data;
	  		
	  		$scope.searchKeywords = '';
		      $scope.filteredQuestion = [];
		      $scope.row = '';
		      
		      $scope.select = function(page) {
		        var end, start;
		        start = (page - 1) * $scope.numPerPage;
		        end = start + $scope.numPerPage;
		        return $scope.questionsOptions = $scope.filteredQuestion.slice(start, end);
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
		        $scope.filteredQuestion = $filter('filter')($scope.listQuestionsOptions, $scope.searchKeywords);
		        return $scope.onFilterChange();
		      };
		      $scope.order = function(rowName) {
		        if ($scope.row === rowName) {
		          return;
		        }
		        $scope.row = rowName;
		        $scope.filteredQuestion = $filter('orderBy')($scope.listQuestionsOptions, rowName);
		        return $scope.onOrderChange();
		      };
		      $scope.numPerPageOpt = [3, 5, 10];
		      $scope.numPerPage = $scope.numPerPageOpt[2];
		      $scope.currentPage = 1;
		      $scope.questionsOptions = [];
		      var init = null;
		      init = function() {
		        $scope.search();
		        $scope.order('ordre');
		        return $scope.select($scope.currentPage);
		      };
		      
		      return init();
	  	});
	  	
	  	var promise = configEvalFactory.get($stateParams.id);
	  	promise.success(function(data){
	  		$scope.eval = data;
	  	});
	  	
		$scope.cancel = function(){
			history.back();
		}
		
		$scope.addRubrique = function(){
			var rubrique = [];
			rubrique = $scope.rubriquesSelected;
			//var ordre = 1;
			for(var rub in rubrique){
				if(rub != "retourValue" && rub != "removeValue"){
					if($scope.rubriquesSelected[rub] === true) {
						var promiseRub = configEvalFactory.getRubrique(parseInt(rub));
						promiseRub.success(function(data){
							ordre++;
							data.ordre = ordre
							$scope.rubriques.push(data); 
							$scope.rubriquesSelected = [];
							
							var rubriqueEva = {
									"evaluation" : {
										"idEvaluation" : parseInt($stateParams.id)
									},
									"rubrique" : {
										"idRubrique" : data.idRubrique
									},
									"rubriqueEvaluation" : {
										"ordre" : data.ordre,
										"designation" : data.designation
									}
							};
							
							var promise = configEvalFactory.addRubriqueEval(rubriqueEva);
							promise.success(function(data){
								$scope.rubriquesEvaluation.push(data);
							})
							.error(function(){})
						})
						.error(function(){
						})					
					}
				}
			}
		}
		
		$scope.checkIfSelected = function(rubrique){
			var id = parseInt(rubrique);
			for(var i = 0; i < $scope.rubriques.length; i++){
				if($scope.rubriques[i].idRubrique === id){
					return true;					
				}
			}
			return false;
		}
		
		$scope.checkQuesIfSelected = function(question){
			if($scope.currentRubrique){
				if($scope.getRubriqueEvaluationById($scope.currentRubrique).questionEvaluationCollection){
					var comArr = $scope.getRubriqueEvaluationById($scope.currentRubrique).questionEvaluationCollection;
					var id = parseInt(question);
					for(var i = 0; i < comArr.length; i++){
						if(comArr[i].idQuestion.idQuestion === id){
							return true;					
						}
					}
					return false;
				}
				else 
					return false;
			}
			else return false;
		}
		
		$scope.addQuestion = function(id){
			var rubrique = $scope.getRubriqueById(id);
			$scope.currentRubrique = id;
		}
		
		$scope.getRubriqueById = function(id){
			for(var i = 0; i < $scope.rubriques.length; i++){
				if($scope.rubriques[i].idRubrique === id)
					return $scope.rubriques[i];
			}
			return null;
		}
		
		$scope.getRubriqueEvaluationById = function(id){
			for(var i = 0; i < $scope.rubriquesEvaluation.length; i++){
				if($scope.rubriquesEvaluation[i].idRubrique.idRubrique === id.idRubrique)
					return $scope.rubriquesEvaluation[i];
			}
			return null;
		}
		
		$scope.addQuestions = function(){
			var rubrique = $scope.getRubriqueEvaluationById($scope.currentRubrique);
			if(!rubrique.questionEvaluationCollection) rubrique.questionEvaluationCollection = [];
			var i = 1;
			for(var ques in $scope.questionsSelected){
				if(ques != "removeValue" && ques != "retourValue"){
					if($scope.questionsSelected[ques] === true){
						var promiseQues = configEvalFactory.getQuestion(ques);
						promiseQues.success(function(data){
							rubrique.questionEvaluationCollection.push(data);
							var QuesEval = {
									"question": {
										"idQuestion" : data.idQuestion
									},
									"qualificatif" : null,
									"rubriqueEvaluation": {
										"idRubriqueEvaluation": rubrique.idRubriqueEvaluation
									},
									"questionEvaluation": {
										"idQuestionEvaluation" : 0,
										"ordre": i,
										"intitule": data.intitule
									}
							};
							var promiseQuesEval = configEvalFactory.addQuestionEval(QuesEval);
							promiseQuesEval.success(function(data){
								
							})
							.error(function(data){
							});
						});
					}
				}
				i++;
			}
			$scope.questionsSelected = [];
			$location.path("/evaluation/config/"+ $stateParams.id);
		};
		
		$scope.removeQuestion = function(idRubrique, idQuestion){	
			var indexQues = -1;		
			var comArr = $scope.getRubriqueEvaluationById(idRubrique).questionEvaluationCollection;
			for( var i = 0; i < comArr.length; i++ ) {
				if( comArr[i].idQuestion === idQuestion ) {
					indexQues = i;
					break;
				}
			}
			if( indexQues === -1 ) {
				alert( "Something gone wrong" );
			}
			else{
				var indexRub = -1;
				for(var i = 0; i < $scope.rubriquesEvaluation.length; i++){
					if($scope.rubriquesEvaluation[i].idRubrique.idRubrique === idRubrique.idRubrique)
						indexRub = i;
				}
				if(indexRub === -1)
					alert( "Something gone wrong" );
				else{
					var promise = configEvalFactory.deleteQuestionEval($scope.rubriquesEvaluation[indexRub].questionEvaluationCollection[indexQues].idQuestionEvaluation);
					promise.success(function(data){
						$scope.rubriquesEvaluation[indexRub].questionEvaluationCollection.splice(indexQues, 1);		
					});
				}
			}
		};
		
		$scope.removeRubrique = function(idRubrique){	
			var indexRub = -1;		
			for( var i = 0; i < $scope.rubriques.length; i++ ) {
				if( $scope.rubriques[i].idRubrique === idRubrique.idRubrique ) {
					indexRub = i;
					break;
				}
			}
			if(indexRub === -1 ) {
				swal("Erreur!", "Impossible de supprimer cette rubrique, elle contient des questions !", "error");
			} 
			else{
				var promise = configEvalFactory.deleteRubrique($scope.rubriquesEvaluation[indexRub].idRubriqueEvaluation);
				promise.success(function(data){
					$scope.rubriques.splice(indexRub, 1);
					$scope.rubriquesEvaluation.splice(indexRub, 1);
				})
				$scope.currentRubrique = null;
			}
		};
		
		$scope.submit = function(){
			history.back();
		}
		
  }]);

 
}).call(this);
