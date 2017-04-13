var meetingApp = angular.module("roleModule", [ 'ui.bootstrap' ]);

meetingApp.filter('startFrom', function() {
	return function(input, start) {
		if (input) {
			start = +start; // parse to int
			return input.slice(start);
		}
		return [];
	}
});

var roleController = [
		'$scope',
		'$cookieStore',
		'$rootScope',
		'$http',
		'constantService',
		'roleService',
		'navigationService',
		'permissionMatrixService',
		function($scope, $cookieStore, $rootScope, $http, constantService,
				roleService, navigationService, permissionMatrixService) {
			$scope.matrixList = [];

			// to get the constant resources
			constantService.getResources().then(function(response) {
				$scope.resourceTypes = response.data;
			});

			var constants = constantService.getConstants();

			$rootScope.globals = $cookieStore.get('globals') || {};

			$scope.currentUser = $rootScope.globals.currentUser;
			$scope.roleStatus = "ACTIVE";
			fetch();
			$scope.isAddFormVisible = false;
			$scope.isEditFormVisible = false;

			// setFormVisibility();
			$scope.pageSize = 5;
			$scope.currentPage = 1;
			$scope.maxSize = 5;

			$scope.getRolesByStatus = function() {
				fetch();
			}
			
			
			function fetch() {
				// fetch roles
				
				roleService.getRole("searchByStatus", $scope.roleStatus).then(function(response) {
					$scope.roles = response.data;
				});

			}

			$scope.showAddForm = function() {
				$scope.formLabel = "Add Role";

				if (!$scope.isEditFormVisible) {
					setFormVisibility();
					$scope.isAddFormVisible = !$scope.isAddFormVisible;
				} else {
					$scope.isEditFormVisible = false;
					$scope.isAddFormVisible = true;
				}

				clearForm();

			}

			function clearForm() {
				var obj = {};
				$scope.obj = obj;
			}

			function setFormVisibility() {
				$('#empForm').slideToggle();
				// $scope.isAddFormVisible = !$scope.isAddFormVisible;
			}

			$scope.createObj = function(obj) {
				if ($scope.isAddFormVisible)
					insertObj(obj);
				else if ($scope.isEditFormVisible)
					updateObj(obj);
			}

			$scope.openItem = function(obj) {
				$rootScope.globals.selectedWedding = obj;
				$cookieStore.put('globals', $rootScope.globals);
				window.location = "#/group";
				// console.log($rootScope);
			}

			function insertObj(obj) {
				$http.post(constants.apiUrl + 'role/insert', {
					"roleName" : obj.roleName,
					"common" : {
						"status" : obj.common.status,
						"createdById" : $scope.currentUser.userId
					}
				}).success(function(data) {
					if (data.success == true) {
						fetch();
						setFormVisibility();
						$scope.isAddFormVisible = !$scope.isAddFormVisible;

						showSuccessMsg(data.message);
					} else {
						console.log(data);

						showErrorMsg(data.message);
					}
				}).error(function(error) {

					showErrorMsg(error);
				});
			}

			function showSuccessMsg(msg) {
				$('#a_success').show();
				$('#a_error').hide();
				$scope.successMsg = msg;
			}

			function showErrorMsg(msg) {
				$('#a_success').hide();
				$('#a_error').show();
				$scope.errorMsg = msg;
			}

			function updateObj(obj) {
				$http
						.post(constants.apiUrl + '/role/update', {
							"roleId" : obj.roleId,
							"roleName" : obj.roleName,
							"common" : {
								"status" : obj.common.status,
								"updatedById" : $scope.currentUser.userId
							}
						})
						.success(
								function(data) {
									if (data.success == true) {
										fetch();
										setFormVisibility();
										$scope.isEditFormVisible = !$scope.isEditFormVisible;
										
										showSuccessMsg(data.message);
									} else {
										console.log(data);
										
										showErrorMsg(data.message);
									}

								}).error(function(error) {

									showErrorMsg(error);
								});
			}

			$scope.deleteObj = function(obj) {
				// console.log(obj);
				$http.post('weddingMaster/db/delete.php', {
					"roleId" : obj.roleId
				}).success(function(data) {

					if (data == 1) {
						fetch();

					} else
						console.log(data);
				})
			}

			$scope.prepareToDelete = function(detail) {
				$scope.obj = detail;

			}

			$scope.editObj = function(obj) {
				$scope.formLabel = "Update Meeting Room";
				if (!$scope.isAddFormVisible) {
					setFormVisibility();
					$scope.isEditFormVisible = !$scope.isEditFormVisible;
				} else {
					$scope.isEditFormVisible = true;
					$scope.isAddFormVisible = false;
				}
				$scope.obj = obj;

			}
			
			
			$scope.activateObj = function(obj) {

				$http.post(constants.apiUrl + '/role/activate', obj)
						.success(function(data) {
							if (data.success == true) {
								fetch();
								showSuccessMsg(data.message);
							} else{
								showErrorMsg(data.message);
							}

						}).error(function(error) {
							showErrorMsg(error);
						});
			}

			$scope.inactivateObj = function(obj) {

				$http.post(constants.apiUrl + '/role/inactivate', obj)
						.success(function(data) {
							if (data.success == true) {
								fetch();
								showSuccessMsg(data.message);
								
							} else{
								showErrorMsg(data.message);
							}
						}).error(function(error) {
							showErrorMsg(error);
						});
			}


		} ];

meetingApp.controller("roleController", roleController);