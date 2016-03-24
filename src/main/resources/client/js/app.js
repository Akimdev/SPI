/*******************************************************************************
 * @Author Zouhair Je
 ******************************************************************************/

var app = angular.module("app", ['bootstrap.formGroup', 'ngMask','ngRoute',"ui.router", "ui.bootstrap", "oc.lazyLoad",'app.controllers','app.ec', 'app.configEvaluation',
		"ngSanitize", 'app.enseignants', 'app.formations', 'app.ue','ngAnimate','toaster', 'app.task',
		'app.etudiants', 'app.qualificatifs', 'app.questions','app.evaluations', 'app.rubriques', 'easypiechart',
		'app.promotions', 'app.auth', 'app.etudiantEvaluations']);

/* To configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
app.config([ '$ocLazyLoadProvider', function($ocLazyLoadProvider) {
	$ocLazyLoadProvider.config({
		cssFilesInsertBefore : 'ng_load_plugins_before' // load the above css
														// files before a LINK
														// element with this ID.
														// Dynamic CSS files
														// must be loaded
														// between core and
														// theme css files
	});
} ]);

// AngularJS v1.3.x workaround for old style controller declarition in HTML
app.config([ '$controllerProvider', function($controllerProvider) {
	// this option might be handy for migrating old apps, but please don't use
	// it
	// in new ones!
	$controllerProvider.allowGlobals();
} ]);

/* Setup global settings */
app.factory('settings', [ '$rootScope', function($rootScope) {
	// supported languages
	var settings = {
		layout : {
			pageSidebarClosed : false, // sidebar state
			pageAutoScrollOnLoad : 1000
		// auto scroll to top on page load
		},
		layoutImgPath : Metronic.getAssetsPath() + 'admin/layout/img/',
		layoutCssPath : Metronic.getAssetsPath() + 'admin/layout/css/'
	};

	$rootScope.settings = settings;

	return settings;
} ]);

/* Setup App Main Controller */
app.controller('appController', [ '$scope', '$rootScope',
		function($scope, $rootScope) {
			$scope.$on('$viewContentLoaded', function() {
				Metronic.initComponents(); // init core components
				// Layout.init(); // Init entire layout(header, footer, sidebar,
				// etc) on page load if the partials included in server side
				// instead of loading with ng-include directive
			});
		} ]);

/* Setup Layout Part - Header */
app.controller('HeaderController', [ '$scope', function($scope) {
	$scope.$on('$includeContentLoaded', function() {
		Layout.initHeader(); // init header
	});
} ]);

/* Setup Layout Part - Sidebar */
app.controller('SidebarController', [ '$scope', function($scope) {
	$scope.$on('$includeContentLoaded', function() {
		Layout.initSidebar(); // init sidebar
	});
} ]);

/* Setup Layout Part - Sidebar */
app.controller('PageHeadController', [ '$scope', function($scope) {
	$scope.$on('$includeContentLoaded', function() {
		Demo.init(); // init theme panel
	});
} ]);

/* Setup Layout Part - Footer */
app.controller('FooterController', [ '$scope', function($scope) {
	$scope.$on('$includeContentLoaded', function() {
		Layout.initFooter(); // init footer
	});
} ]);

/* Setup Rounting For All Pages */
app
		.config([
				'$stateProvider',
				'$urlRouterProvider',
				function($stateProvider, $urlRouterProvider) {

					// Redirect any unmatched url
					$urlRouterProvider.otherwise("/pages/signin.html");

					$stateProvider

					// Signin
					.state(
							'signin',
							{
								url : "/pages/signin.html",
								templateUrl : "views/pages/signin.html",
								hideSideBar: true,
								data : {
									pageTitle : 'Authentification'
								},
								resolve : {
									deps : [
											'$ocLazyLoad',
											function($ocLazyLoad) {
												return $ocLazyLoad
														.load({
															name : 'app',
															insertBefore : '#ng_load_plugins_before', // load
																										// the
																										// above
																										// css
																										// files
																										// before
																										// '#ng_load_plugins_before'
															files : [
																	 ]
														});
											} ]
								}
							})
							// Dashboard
							.state(
									'dashboard',
									{
										url : "/dashboard.html",
										templateUrl : "views/dashboard.html",
										data : {
											pageTitle : 'Tableau de bord',
											pageSubTitle : 'statistics & reports'
										},
										controller : "DashboardController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',
																			
																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/DashboardController.js' ]
																});
													} ]
										}
									})
							

							// AngularJS plugins
							.state(
									'fileupload',
									{
										url : "/file_upload.html",
										templateUrl : "views/file_upload.html",
										data : {
											pageTitle : 'AngularJS File Upload',
											pageSubTitle : 'angularjs file upload'
										},
										controller : "GeneralPageController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load([
																		{
																			name : 'angularFileUpload',
																			files : [
																					'assets/global/plugins/angularjs/plugins/angular-file-upload/angular-file-upload.min.js', ]
																		},
																		{
																			name : 'app',
																			files : [ 'js/controllers/GeneralPageController.js' ]
																		} ]);
													} ]
										}
									})
							// UI Select
							.state(
									'uiselect',
									{
										url : "/ui_select.html",
										templateUrl : "views/ui_select.html",
										data : {
											pageTitle : 'AngularJS Ui Select',
											pageSubTitle : 'select2 written in angularjs'
										},
										controller : "UISelectController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load([
																		{
																			name : 'ui.select',
																			insertBefore : '#ng_load_plugins_before', // load
																														// the
																														// above
																														// css
																														// files
																														// before
																														// '#ng_load_plugins_before'
																			files : [
																					'assets/global/plugins/angularjs/plugins/ui-select/select.min.css',
																					'assets/global/plugins/angularjs/plugins/ui-select/select.min.js' ]
																		},
																		{
																			name : 'app',
																			files : [ 'js/controllers/UISelectController.js' ]
																		} ]);
													} ]
										}
									})

							// UI Bootstrap
							.state(
									'uibootstrap',
									{
										url : "/ui_bootstrap.html",
										templateUrl : "views/ui_bootstrap.html",
										data : {
											pageTitle : 'AngularJS UI Bootstrap',
											pageSubTitle : 'bootstrap components written in angularjs'
										},
										controller : "GeneralPageController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load([ {
																	name : 'app',
																	files : [ 'js/controllers/GeneralPageController.js' ]
																} ]);
													} ]
										}
									})

							// Tree View
							.state(
									'tree',
									{
										url : "/tree",
										templateUrl : "views/tree.html",
										data : {
											pageTitle : 'jQuery Tree View',
											pageSubTitle : 'tree view samples'
										},
										controller : "GeneralPageController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load([ {
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/jstree/dist/themes/default/style.min.css',

																			'assets/global/plugins/jstree/dist/jstree.min.js',
																			'assets/admin/pages/scripts/ui-tree.js',
																			'js/controllers/GeneralPageController.js' ]
																} ]);
													} ]
										}
									})

							// Form Tools
							.state(
									'formtools',
									{
										url : "/form-tools",
										templateUrl : "views/form_tools.html",
										data : {
											pageTitle : 'Form Tools',
											pageSubTitle : 'form components & widgets sample'
										},
										controller : "GeneralPageController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load([ {
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css',
																			'assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css',
																			'assets/global/plugins/jquery-tags-input/jquery.tagsinput.css',
																			'assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css',
																			'assets/global/plugins/typeahead/typeahead.css',

																			'assets/global/plugins/fuelux/js/spinner.min.js',
																			'assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js',
																			'assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js',
																			'assets/global/plugins/jquery.input-ip-address-control-1.0.min.js',
																			'assets/global/plugins/bootstrap-pwstrength/pwstrength-bootstrap.min.js',
																			'assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js',
																			'assets/global/plugins/jquery-tags-input/jquery.tagsinput.min.js',
																			'assets/global/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js',
																			'assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js',
																			'assets/global/plugins/typeahead/handlebars.min.js',
																			'assets/global/plugins/typeahead/typeahead.bundle.min.js',
																			'assets/admin/pages/scripts/components-form-tools.js',

																			'js/controllers/GeneralPageController.js' ]
																} ]);
													} ]
										}
									})

							// Date & Time Pickers
							.state(
									'pickers',
									{
										url : "/pickers",
										templateUrl : "views/pickers.html",
										data : {
											pageTitle : 'Date & Time Pickers',
											pageSubTitle : 'date, time, color, daterange pickers'
										},
										controller : "GeneralPageController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load([ {
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/clockface/css/clockface.css',
																			'assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css',
																			'assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css',
																			'assets/global/plugins/bootstrap-colorpicker/css/colorpicker.css',
																			'assets/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css',
																			'assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css',

																			'assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js',
																			'assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js',
																			'assets/global/plugins/clockface/js/clockface.js',
																			'assets/global/plugins/bootstrap-daterangepicker/moment.min.js',
																			'assets/global/plugins/bootstrap-daterangepicker/daterangepicker.js',
																			'assets/global/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js',
																			'assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js',

																			'assets/admin/pages/scripts/components-pickers.js',

																			'js/controllers/GeneralPageController.js' ]
																} ]);
													} ]
										}
									})

							// Custom Dropdowns
							.state(
									'dropdowns',
									{
										url : "/dropdowns",
										templateUrl : "views/dropdowns.html",
										data : {
											pageTitle : 'Custom Dropdowns',
											pageSubTitle : 'select2 & bootstrap select dropdowns'
										},
										controller : "GeneralPageController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load([ {
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/bootstrap-select/bootstrap-select.min.css',
																			'assets/global/plugins/select2/select2.css',
																			'assets/global/plugins/jquery-multi-select/css/multi-select.css',

																			'assets/global/plugins/bootstrap-select/bootstrap-select.min.js',
																			'assets/global/plugins/select2/select2.min.js',
																			'assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js',

																			'assets/admin/pages/scripts/components-dropdowns.js',

																			'js/controllers/GeneralPageController.js' ]
																} ]);
													} ]
										}
									})

							// Advanced Datatables
							.state(
									'datatablesAdvanced',
									{
										url : "/datatables/advanced.html",
										templateUrl : "views/datatables/advanced.html",
										data : {
											pageTitle : 'Advanced Datatables',
											pageSubTitle : 'advanced datatables samples'
										},
										controller : "GeneralPageController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/select2/select2.css',
																			'assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css',
																			'assets/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css',
																			'assets/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css',

																			'assets/global/plugins/select2/select2.min.js',
																			'assets/global/plugins/datatables/all.min.js',
																			'js/scripts/table-advanced.js',

																			'js/controllers/GeneralPageController.js' ]
																});
													} ]
										}
									})

							// Ajax Datetables
							.state(
									'datatablesAjax',
									{
										url : "/datatables/ajax.html",
										templateUrl : "views/datatables/ajax.html",
										data : {
											pageTitle : 'Ajax Datatables',
											pageSubTitle : 'ajax datatables samples'
										},
										controller : "GeneralPageController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/select2/select2.css',
																			'assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css',
																			'assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css',

																			'assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js',
																			'assets/global/plugins/select2/select2.min.js',
																			'assets/global/plugins/datatables/all.min.js',

																			'assets/global/scripts/datatable.js',
																			'js/scripts/table-ajax.js',

																			'js/controllers/GeneralPageController.js' ]
																});
													} ]
										}
									})

							// User Profile
							.state(
									"profile",
									{
										url : "/profile",
										templateUrl : "views/profile/main.html",
										data : {
											pageTitle : 'User Profile',
											pageSubTitle : 'user profile sample'
										},
										controller : "UserProfileController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css',
																			'assets/admin/pages/css/profile.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/jquery.sparkline.min.js',
																			'assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js',

																			'assets/admin/pages/scripts/profile.js',

																			'js/controllers/UserProfileController.js' ]
																});
													} ]
										}
									})

							// User Profile Dashboard
							.state(
									"profile.dashboard",
									{
										url : "/dashboard",
										templateUrl : "views/profile/dashboard.html",
										data : {
											pageTitle : 'User Profile',
											pageSubTitle : 'user profile dashboard sample'
										}
									})

							// User Profile Account
							.state(
									"profile.account",
									{
										url : "/account",
										templateUrl : "views/profile/account.html",
										data : {
											pageTitle : 'User Account',
											pageSubTitle : 'user profile account sample'
										}
									})

							// User Profile Help
							.state("profile.help", {
								url : "/help",
								templateUrl : "views/profile/help.html",
								data : {
									pageTitle : 'User Help',
									pageSubTitle : 'user profile help sample'
								}
							})

							// Todo
							.state(
									'todo',
									{
										url : "/todo",
										templateUrl : "views/todo.html",
										data : {
											pageTitle : 'Todo',
											pageSubTitle : 'user todo & tasks sample'
										},
										controller : "TodoController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/bootstrap-datepicker/css/datepicker3.css',
																			'assets/global/plugins/select2/select2.css',
																			'assets/admin/pages/css/todo.css',

																			'assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js',
																			'assets/global/plugins/select2/select2.min.js',

																			'assets/admin/pages/scripts/todo.js',

																			'js/controllers/TodoController.js' ]
																});
													} ]
										}
									})
									
							// Etudiants
							.state(
									'etudiants',
									{
										url : "/etudiants",
										templateUrl : "views/etudiants/list.html",
										data : {
											pageTitle : 'Liste des étudiants',
											pageSubTitle : 'Liste des étudiants'
										},
										controller : "EtudiantsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EtudiantsController.js' ]
																});
													} ]
										}
									})
									
							// Liste detaillée des Etudiants
							.state(
									'etudiantsdetails',
									{
										url : "/etudiant/:id",
										templateUrl : "views/etudiants/details.html",
										data : {
											pageTitle : 'Détails des étudiants',
											pageSubTitle : 'Détails des étudiants'
										},
										controller : "EtudiantDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EtudiantsController.js' ]
																});
													} ]
										}
									})
									
							// Formations
							.state(
									'formations',
									{
										url : "/formations",
										templateUrl : "views/formations/list.html",
										data : {
											pageTitle : 'Liste des formations',
											pageSubTitle : 'Liste des formations'
										},
										controller : "FormationsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/FormationsController.js' ]
																});
													} ]
										}
									})
									
							//  Details des Formations
							.state(
									'detailsformations',
									{
										url : "/formation/:id",
										templateUrl : "views/formations/details.html",
										data : {
											pageTitle : 'Détails des formations',
											pageSubTitle : 'Détails des formations'
										},
										controller : "FormationDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/FormationsController.js' ]
																});
													} ]
										}
									})
									
							// UE
							.state(
									'ue',
									{
										url : "/ue",
										templateUrl : "views/ue/list.html",
										data : {
											pageTitle : 'Liste des UE',
											pageSubTitle : 'Liste des UE'
										},
										controller : "UEController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/UEController.js' ]
																});
													} ]
										}
									})
							
							// UE détaillée
							.state(
									'detailsUE',
									{
										url : "/ue/:codeFormation/:codeUe",
										templateUrl : "views/ue/details.html",
										data : {
											pageTitle : 'Détails des UE',
											pageSubTitle : 'Détails des UE'
										},
										controller : "UEDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/UEController.js' ]
																});
													} ]
										}
									})
							
							// EC liste
							.state(
									'ec',
									{
										url : "/ec",
										templateUrl : "views/elementConstitutif/list.html",
										data : {
											pageTitle : 'Liste des EC',
											pageSubTitle : 'Liste des EC'
										},
										controller : "ecController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EcController.js' ]
																});
													} ]
										}
									})
							
							// EC détaillée
							.state(
									'detailsec',
									{
										url : "/elementConstitutif/:infos/:id/:id2/:id3",
										templateUrl : "views/elementConstitutif/detail.html",
										data : {
											pageTitle : 'Détails des EC',
											pageSubTitle : 'Détails des EC'
										},
										controller : "EcDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EcController.js' ]
																});
													} ]
										}
									})
									// EC détaillée
							.state(
									'detailsec2',
									{
										url : "/elementConstitutif/:id/:id2/:id3",
										templateUrl : "views/elementConstitutif/detail.html",
										data : {
											pageTitle : 'Détails des EC',
											pageSubTitle : 'Détails des EC'
										},
										controller : "EcDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EcController.js' ]
																});
													} ]
										}
									})
									// EC détaillée
							.state(
									'detailsec3',
									{
										url : "/elementConstitutif/:new",
										templateUrl : "views/elementConstitutif/detail.html",
										data : {
											pageTitle : 'Détails des EC',
											pageSubTitle : 'Détails des EC'
										},
										controller : "EcDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EcController.js' ]
																});
													} ]
										}
									})
									
							// Promotions
							.state(
									'promotions',
									{
										url : "/promotions",
										templateUrl : "views/promotions/list.html",
										data : {
											pageTitle : 'Liste des promotions',
											pageSubTitle : 'Liste des promotions'
										},
										controller : "PromotionsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/PromotionsController.js' ]
																});
													} ]
										}
									})
							
							// Details Promotions
							.state(
									'detailspromotions',
									{
										url : "/promotion/:ann/:form/:edit",
										templateUrl : "views/promotions/details.html",
										data : {
											pageTitle : 'Detail des promotions',
											pageSubTitle : 'Detail des promotions'
										},
										controller : "PromotionDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/PromotionsController.js' ]
																});
													} ]
										}
									})
									
							// Questions
							.state(
									'questions',
									{
										url : "/questions",
										templateUrl : "views/questions/list.html",
										data : {
											pageTitle : 'Liste des questions',
											pageSubTitle : 'Liste des questions'
										},
										controller : "QuestionsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/QuestionsController.js' ]
																});
													} ]
										}
									})
									
							// Details Questions
							.state(
									'detailsquestions',
									{
										url : "/question/:id",
										templateUrl : "views/questions/details.html",
										data : {
											pageTitle : 'Details des questions',
											pageSubTitle : 'Details des questions'
										},
										controller : "QuestionDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/QuestionsController.js' ]
																});
													} ]
										}
									})
							.state(
									'infosQuestion',
									{
										url : "/question/:infos/:id",
										templateUrl : "views/questions/details.html",
										data : {
											pageTitle : 'Details des questions',
											pageSubTitle : 'Details des questions'
										},
										controller : "QuestionDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/QuestionsController.js' ]
																});
													} ]
										}
									})
							
							// Qualificatifs
							.state(
									'qualificatifs',
									{
										url : "/qualificatifs",
										templateUrl : "views/qualificatifs/list.html",
										data : {
											pageTitle : 'Liste des qualificatifs',
											pageSubTitle : 'Liste des qualificatifs'
										},
										controller : "QualificatifsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [


																			'js/controllers/QualificatifsController.js' ]
																});
													} ]
										}
									})
									
							// Details Qualificatifs
							.state(
									'detailsqualificatifs',
									{
										url : "/qualificatifs/:id",
										templateUrl : "views/qualificatifs/details.html",
										data : {
											pageTitle : 'Details des qualificatifs',
											pageSubTitle : 'Details des qualificatifs'
										},
										controller : "QualificatifDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/QualificatifsController.js' ]
																});
													} ]
										}
									})
									
							// Evaluations
							.state(
									'evalutations',
									{
										url : "/evaluations",
										templateUrl : "views/evaluations/list.html",
										data : {
											pageTitle : 'Liste des évaluations',
											pageSubTitle : 'Liste des évaluations'
										},
										controller : "EvaluationsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EvaluationsController.js' ]
																});
													} ]
										}
									})
							
							// Details Evaluations
							.state(
									'Editevalutations',
									{
										url : "/evaluations/:id",
										templateUrl : "views/evaluations/details.html",
										data : {
											pageTitle : 'détail dune évaluations',
											pageSubTitle : 'détail dune évaluations'
										},
										controller : "EvasDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EvaluationsController.js' ]
																});
													} ]
										}
									})
									
							.state(
									'Detailevalutations',
									{
										url : "/evaluations/:infos/:id",
										templateUrl : "views/evaluations/details.html",
										data : {
											pageTitle : 'Détail dune évaluations',
											pageSubTitle : 'détail d'/'une évaluations'
										},
										controller : "EvasDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EvaluationsController.js' ]
																});
													} ]
										}
									})
						// EtudiantEvaluations
							.state(
									'etudiantEvaluations',
									{
										url : "/etudiantEvaluations",
										templateUrl : "views/etudiantEvaluation/list.html",
										data : {
											pageTitle : 'Liste des évaluations concernant une promotion donnée',
											pageSubTitle : 'Liste des évaluations concernant une promotion donnée'
										},
										controller : "EtudiantEvaluationsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EvaluationsController.js' ]
																});
													} ]
										}
									})
							
							// Details etudiantEvaluations
							.state(
									'consulterEvalutations',
									{
										url : "/etudiantEvaluations/:infos/:id",
										templateUrl : "views/etudiantEvaluation/details.html",
										data : {
											pageTitle : 'détail d évaluation concernant une promotion donnée',
											pageSubTitle : 'détail d évaluation concernant une promotion donnée'
										},
										controller : "EtudiantEvaluationsDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EvaluationsController.js' ]
																});
													} ]
										}
									})
									
							
							// Rubriques
							.state(
									'rubriques',
									{
										url : "/rubriques",
										templateUrl : "views/rubriques/list.html",
										data : {
											pageTitle : 'Liste des rubriques',
											pageSubTitle : 'Liste des rubriques'
										},
										controller : "RubriquesController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/RubriquesController.js' ]
																});
													} ]
										}
									})
									
							// Details Rubriques
							.state(
									'detailsrubriques',
									{
										url : "/rubrique/:id",
										templateUrl : "views/rubriques/details.html",
										data : {
											pageTitle : 'Details des rubriques',
											pageSubTitle : 'Details des rubriques'
										},
										controller : "RubriqueDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/RubriquesController.js' ]
																});
													} ]
										}
									})
									
							// Edit Rubriques
							.state(
									'editrubriques',
									{
										url : "/rubrique/:id/edit",
										templateUrl : "views/rubriques/edit.html",
										data : {
											pageTitle : 'Edition des rubriques',
											pageSubTitle : 'Edition des rubriques'
										},
										controller : "RubriqueDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/RubriquesController.js' ]
																});
													} ]
										}
									})
									
							// Liste des Enseignants
							.state(
									'enseignants',
									{
										url : "/enseignants",
										templateUrl : "views/enseignants/list.html",
										data : {
											pageTitle : 'Liste des enseignants',
											pageSubTitle : 'Liste des enseignants'
										},
										controller : "EnseignantsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EnseignantsController.js' ]
																});
													} ]
										}
									})
									
									// Configurer evaluation
									.state(
											'configEvaluation',
											{
												url : "/evaluation/config/:id",
												templateUrl : "views/evaluations/config.html",
												data : {
													pageTitle : 'Configurer Evaluation',
													pageSubTitle : ''
												},
												controller : "ConfigEvaluationController",
												resolve : {
													deps : [
													        '$ocLazyLoad',
													        function($ocLazyLoad) {
													        	return $ocLazyLoad
													        	.load({
													        		name : 'app',
													        		insertBefore : '#ng_load_plugins_before', // load
													        		// the
													        		// above
													        		// css
													        		// files
													        		// before
													        		// '#ng_load_plugins_before'
													        		files : [
													        		         'assets/global/plugins/morris/morris.css',
													        		         'assets/admin/pages/css/tasks.css',
													        		         
													        		         'assets/global/plugins/morris/morris.min.js',
													        		         'assets/global/plugins/morris/raphael-min.js',
													        		         'assets/global/plugins/jquery.sparkline.min.js',
													        		         
													        		         'assets/admin/pages/scripts/index3.js',
													        		         'assets/admin/pages/scripts/tasks.js',
													        		         
													        		         'js/controllers/EnseignantsController.js' ]
													        	});
													        } ]
												}
											})
									
							// Liste detaillée des Enseignants
							.state(
									'detailsenseignants',
									{
										url : "/enseignant/:id",
										templateUrl : "views/enseignants/details.html",
										data : {
											pageTitle : 'Details des enseignants',
											pageSubTitle : 'Details des enseignants'
										},
										controller : "EnsDetailsController",
										resolve : {
											deps : [
													'$ocLazyLoad',
													function($ocLazyLoad) {
														return $ocLazyLoad
																.load({
																	name : 'app',
																	insertBefore : '#ng_load_plugins_before', // load
																												// the
																												// above
																												// css
																												// files
																												// before
																												// '#ng_load_plugins_before'
																	files : [
																			'assets/global/plugins/morris/morris.css',
																			'assets/admin/pages/css/tasks.css',

																			'assets/global/plugins/morris/morris.min.js',
																			'assets/global/plugins/morris/raphael-min.js',
																			'assets/global/plugins/jquery.sparkline.min.js',

																			'assets/admin/pages/scripts/index3.js',
																			'assets/admin/pages/scripts/tasks.js',

																			'js/controllers/EnseignantsController.js' ]
																});
													} ]
										}
									})
								$urlRouterProvider.otherwise(function($injector, $location) {
										var AuthService = $injector.get('AuthService');
										var temp = AuthService.getUser();
										AuthService.getUser().success(function(data){
											if (data) {
												$location.path("/dashboard.html");
											} else {
												$location.path("/pages/signin.html");
											}

										}).error(function(data) {
											$location.path("/pages/signin.html");
										});

									}); 
				} ]).run([ "$rootScope", "settings", "$state", "$route", "$location", "AuthService",

		function($rootScope, settings, $state, $route, $location, AuthService) {
			$rootScope.$state = $state; // state to be accessed from view
			$rootScope.$on("$locationChangeStart", function(e, to) {	

				if (to.notLoggedNeeded) {
					return;
				}
				AuthService.getUser().success(function(data) {
					$rootScope.user = data.role;
					$rootScope.userNum = data;
					if (data.role === "ENS"){
						$rootScope.nameUser = data.noEnseignant.prenom;
						$rootScope.sexeUser = data.noEnseignant.sexe;
					}
					if (data.role === "ETU"){
						$rootScope.nameEtudiant = data.noEtudiant.prenom;	
					}
					if (!data) {
						$location.path("/pages/signin.html");
					}
				}).error(function(data) {
					$location.path("/pages/signin.html");
				});
			});
		} ]);