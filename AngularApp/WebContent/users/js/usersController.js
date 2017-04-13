var meetingApp = angular.module("usersModule", [ 'ui.bootstrap' ]);

meetingApp.filter('startFrom', function() {
	return function(input, start) {
		if (input) {
			start = +start; // parse to int
			return input.slice(start);
		}
		return [];
	}
});

var usersController = [
		'$scope',
		'$cookieStore',
		'$rootScope',
		'$http',
		'constantService',
		'userService',
		'roleService',
		function($scope, $cookieStore, $rootScope, $http, constantService,
				userService, roleService) {

			// to get the constant resources
			constantService.getResources().then(function(response) {
				$scope.resourceTypes = response.data;
			});

			var constants = constantService.getConstants();

			$rootScope.globals = $cookieStore.get('globals') || {};

			$scope.currentUser = $rootScope.globals.currentUser;

			userService.getUsers("uid", $scope.currentUser.userId).then(
					function(response) {
						if (response.data.length > 0)
							$scope.user = response.data[0];

					});

			$scope.userStatus = "ACTIVE";

			fetch();
			$scope.isAddFormVisible = false;
			$scope.isEditFormVisible = false;
			// setFormVisibility();
			$scope.pageSize = 5;
			$scope.currentPage = 1;
			$scope.maxSize = 5;

			$scope.getUsersByStatus = function() {
				fetch();
			}

			function fetch() {

				// fetch users
				userService.getUsers("searchByStatus", $scope.userStatus).then(
						function(response) {
							$scope.details = response.data;
						});

				// fetch roles
				roleService.getRole("searchOnlyActive", "").then(
						function(response) {
							$scope.roles = response.data;
						});

			}

			$scope.showAddForm = function() {
				$scope.formLabel = "Add Users";

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
				$scope.obj = {};
			}

			function setFormVisibility() {
				$('#empForm').slideToggle();
				// $scope.isAddFormVisible = !$scope.isAddFormVisible;
			}

			$scope.createObj = function(obj) {
				if ($scope.isAddFormVisible)
					insertObj(obj);
				else if ($scope.isEditFormVisible)
					$scope.updateObj(obj);
			}

			$scope.openItem = function(obj) {
				$rootScope.globals.selectedWedding = obj;
				$cookieStore.put('globals', $rootScope.globals);
				window.location = "#/group";
				// console.log($rootScope);
			}

			function insertObj(obj) {
				$http.post(constants.apiUrl + 'users/insert', {
					"uName" : obj.uName,
					"uDep" : obj.uDep,
					"uBuild" : obj.uBuild,
					"uFloor" : obj.uFloor,
					"role" : obj.role,
					"mobile" : obj.mobile,
					"email" : obj.email,
					"details" : obj.details,
					"common" : {
						"status" : 'ACTIVE',
						"createdById" : $scope.currentUser.userId
					},

					"login" : {
						"userName" : obj.login.userName,
						"password" : obj.login.password
					}

				}).success(function(data) {
					if (data.success == true) {
						fetch();
						setFormVisibility();
						$scope.isAddFormVisible = $scope.isAddFormVisible;

						showSuccessMsg(data.message);
					} else {
						console.log(data);

						showErrorMsg(data.message);
					}
				}).error(function(error) {

					showErrorMsg(error);
				});
			}

			function showSuccessMsg() {
				$('#a_success').show();
				$('#a_error').hide();
			}

			function showErrorMsg() {
				$('#a_success').hide();
				$('#a_error').show();
			}

			$scope.updateObj = function(obj) {
				$http.post(constants.apiUrl + '/users/update', {
					"uid" : obj.uid,
					"uName" : obj.uName,
					"uDep" : obj.uDep,
					"uBuild" : obj.uBuild,
					"uFloor" : obj.uFloor,
					"role" : obj.role,
					"mobile" : obj.mobile,
					"email" : obj.email,

					"details" : obj.details,
					"status" : obj.status,
					"common" : {
						"status" : obj.common.status,
						"updatedById" : $scope.currentUser.userId
					},

					"login" : {
						"userName" : obj.login.userName,
						"password" : obj.login.password
					}
				}).success(function(data) {
					if (data.success == true) {
						$scope.notifyUser = "Successfully Updated";
						fetch();
						setFormVisibility();
						$scope.isEditFormVisible = !$scope.isEditFormVisible;

						showSuccessMsg(data.message);
					} else {

						showErrorMsg(data.message);
					}

				}).error(function(error) {

					showErrorMsg(error);
				});
			}

			$scope.activateObj = function(obj) {
				$http.post(constants.apiUrl + '/users/activate', obj).success(
						function(data) {
							if (data.success == true) {
								fetch();

								showSuccessMsg(data.message);
							} else {

								showErrorMsg(data.message);

							}

						}).error(function(error) {

					showErrorMsg(error);
				});
			}

			$scope.inactivateObj = function(obj) {
				$http.post(constants.apiUrl + '/users/inactivate', obj)
						.success(function(data) {
							if (data.success == true) {
								fetch();
								showSuccessMsg(data.message);
							} else {

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

			$scope.deleteObj = function(obj) {
				// console.log(obj);
				$http.post('weddingMaster/db/delete.php', {
					"uid" : obj.uid
				}).success(function(data) {

					if (data == 1) {
						fetch();

					} else
						console.log(data);
				})
			}

			$scope.prepareToDelete = function(detail) {
				$scope.emp = detail;

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

		} ];

meetingApp.controller("usersController", usersController);