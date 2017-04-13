var meetingApp = angular.module("bookingFacilityreportModule", [ 'ui.bootstrap' ]);

meetingApp.filter('startFrom', function() {
	return function(input, start) {
		if (input) {
			start = +start; // parse to int
			return input.slice(start);
		}
		return [];
	}
});

var bookingFacilityreportController = [
		'$scope',
		'$cookieStore',
		'$rootScope',
		'$http',
		'constantService',
		'facilitiesService',
		
		
		function($scope, $cookieStore, $rootScope, $http, constantService,
				facilitiesService) {

			// to get the constant resources
			constantService.getResources().then(function(response) {
				$scope.resourceTypes = response.data;
			});

			var constants = constantService.getConstants();

			$rootScope.globals = $cookieStore.get('globals') || {};

			$scope.currentUser = $rootScope.globals.currentUser;
			$scope.locationStatus = "ACTIVE";
			fetch();
			$scope.isAddFormVisible = false;
			$scope.isEditFormVisible = false;
			// setFormVisibility();
			$scope.pageSize = 5;
			$scope.currentPage = 1;
			$scope.maxSize = 5;

			$scope.getLocationByStatus = function() {
				fetch();
			}
			
			
			
			function fetch() {
				

				facilitiesService.getFacilities("searchOnlyActive", "").then(function(response) {
					$scope.facilities = response.data;
				});	
			}

			$scope.showAddForm = function() {
				$scope.formLabel = "Add Location";

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
				$http.post(constants.apiUrl + 'location/insert', {
					"locationName" : obj.locationName,
					"building" : obj.building,
					"floor" : obj.floor,
					"address" : obj.address,
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
				$http.post(constants.apiUrl + '/location/update', {
					"lid" : obj.lid,
					"locationName" : obj.locationName,
					"building" : obj.building,
					"floor" : obj.floor,
					"address" : obj.address,
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
					} else {

						showErrorMsg(data.message);
					}
				}).error(function(error) {

					showErrorMsg(error);
				});
			}

			$scope.deleteObj = function(obj) {
				// console.log(obj);
				$http.post('weddingMaster/db/delete.php', {
					"lid" : obj.lid
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

				$http.post(constants.apiUrl + '/location/activate', obj)
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

				$http.post(constants.apiUrl + '/location/inactivate', obj)
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

meetingApp.controller("bookingFacilityreportController", bookingFacilityreportController);