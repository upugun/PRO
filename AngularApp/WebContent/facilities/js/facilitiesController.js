var meetingApp = angular.module("facilitiesModule", [ 'ui.bootstrap' ]);

meetingApp.filter('startFrom', function() {
	return function(input, start) {
		if (input) {
			start = +start; // parse to int
			return input.slice(start);
		}
		return [];
	}
});

var facilitiesController = [
		'$scope',
		'$cookieStore',
		'$rootScope',
		'$http',
		'constantService',
		'facilitiesService',
		'userService',
	
		
		function($scope, $cookieStore, $rootScope, $http, constantService,
				facilitiesService,userService) {
			var constants = constantService.getConstants();

			$rootScope.globals = $cookieStore.get('globals') || {};

			$scope.currentUser = $rootScope.globals.currentUser;
			
			$scope.userStatus = "ACTIVE";
			$scope.facilityStatus = "ACTIVE";
			fetch();
			
			$scope.isAddFormVisible = false;
			$scope.isEditFormVisible = false;
			// setFormVisibility();
			$scope.pageSize = 5;
			$scope.currentPage = 1;
			$scope.maxSize = 5;

			

			$scope.getFacilitiesByStatus = function() {
				fetch();
			}
			
			
			function fetch() {
				// fetch facilities
				facilitiesService.getFacilities("searchByStatus", $scope.facilityStatus).then(function(response) {
					$scope.details = response.data;
				});
				
				{
					// fetch users
					userService.getUsers("searchOnlyActive", '').then(
							function(response) {
								$scope.users = response.data;
							});
				}
	
			}

			$scope.showAddForm = function() {
				$scope.formLabel = "Add Facilities";

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
				var obj = {
					fName : "",
					owner : "",
					ext : "",
					status : ""
				};
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

				$http.post(constants.apiUrl + 'facilities/insert', {
					"fName" : obj.fName,
					"admin" : obj.admin,
					"ext" : obj.admin.mobile,
					"email" : obj.admin.email,
					"details" : obj.details,
					"countable":obj.countable,
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
						
					} else{
				
						showErrorMsg(data.message);
					}
				}).error(function(error) {
					
					showErrorMsg(error);
				});

			}

			function updateObj(obj) {
				console.log(obj);
				$http.post(constants.apiUrl + '/facilities/update', {
					"fid" : obj.fid,
					"fName" : obj.fName,
					"admin" : obj.admin,
					"ext" :  obj.admin.mobile,
					"email" : obj.admin.email,
					"details" : obj.details,
					"countable":obj.countable,
					"common" : {
						"status" : obj.common.status,
						"updatedById" : $scope.currentUser.userId
						
					}
				
				}).success(function(data) {
					
					if (data.success == true) {
						fetch();
						setFormVisibility();
						$scope.isEditFormVisible = !$scope.isEditFormVisible;
						
						showSuccessMsg(data.message);
					} else{
					
						showErrorMsg(data.message);
					}
				}).error(function(error) {
					
					showErrorMsg(error);
				});
			}
			
			function showSuccessMsg(msg)
			{
				$('#a_success').show();
				$('#a_error').hide();
				$scope.successMsg = msg;
			}
			
			function showErrorMsg(msg)
			{
				$('#a_success').hide();
				$('#a_error').show();
				$scope.errorMsg = msg;
			}

			$scope.deleteObj = function(obj) {
				// console.log(obj);
				$http.post('weddingMaster/db/delete.php', {
					"fid" : obj.fid
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
			
			$scope.activateObj = function(obj) {

				$http.post(constants.apiUrl + '/facilities/activate', obj)
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

				$http.post(constants.apiUrl + '/facilities/inactivate', obj)
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

meetingApp.controller("facilitiesController", facilitiesController);