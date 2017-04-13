var meetingApp = angular.module("meetingModule", [ 'ui.bootstrap' ]);

meetingApp.filter('startFrom', function() {
	return function(input, start) {
		if (input) {
			start = +start; // parse to int
			return input.slice(start);
		}
		return [];
	}
});

var meetingRoomController = [
		'$scope',
		'$cookieStore',
		'$rootScope',
		'$http',
		'constantService',
		'meetingRoomService',
		'locationService',
		'userService',
		function($scope, $cookieStore, $rootScope, $http, constantService,
				meetingRoomService, locationService, userService) {

			// to get the constant resources
			constantService.getResources().then(function(response) {
				$scope.resourceTypes = response.data;
			});

			var constants = constantService.getConstants();

			$rootScope.globals = $cookieStore.get('globals') || {};

			$scope.currentUser = $rootScope.globals.currentUser;
 
			
			$scope.mRStatus = "ACTIVE";
			fetch();
			$scope.isAddFormVisible = false;
			$scope.isEditFormVisible = false;
			// setFormVisibility();
			$scope.pageSize = 5;
			$scope.currentPage = 1;
			$scope.maxSize = 5;
		
			
			
			$scope.getMeetingRoomByStatus = function() {
				fetch();
			}


			function fetch() {
				// fetch meeting rooms
				meetingRoomService.getMeetingRooms("searchByStatus", $scope.mRStatus).then(function(response) {
					$scope.details = response.data;
				});

				{
					// fetch location
					locationService.getLocations("searchOnlyActive", "").then(function(response) {
						$scope.locations = response.data;
					});
				}

				{
					// fetch users
					userService.getUsers("searchOnlyActive", '').then(
							function(response) {
								$scope.users = response.data;
							});
				}
			}

			$scope.showAddForm = function() {
				$scope.formLabel = "Add Meeting Room";

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
				
				fetch()
				var obj = {
					mRoomName : "",
					seatingCapacity : "",
					adminId : "",
					tp : "",
					notes : "",
					approval : "",
					status : "",
				};
				
				clearDynamicCheckBoxes($scope.resourceTypes);
				
				$scope.obj = obj;
			}
			
			function clearDynamicCheckBoxes(list)
			{
				for(var i=0; i<list.length; i++)
				{
					list[i].selectedStatus = false;
				}
			}

			function setFormVisibility() {
				$('#empForm').slideToggle();
				// $scope.isAddFormVisible = !$scope.isAddFormVisible;
			}

			$scope.create = function(obj) {
				if ($scope.isAddFormVisible)
					insert(obj);
				else if ($scope.isEditFormVisible)
					update(obj);
			}

			$scope.openItem = function(obj) {
				$rootScope.globals.selectedWedding = obj;
				$cookieStore.put('globals', $rootScope.globals);
				window.location = "#/group";
			
			}

			function insert(obj) {
				var selectedResources = getSelectedResources();

				$http.post(constants.apiUrl + 'meetingroom/insert', {
					"mRoomName" : obj.mRoomName,
					"location" : obj.location,
					"seatingCapacity" : obj.seatingCapacity,
					"admin" : obj.admin,
					"resourceList" : selectedResources,
					"tp" : obj.tp,
					"notes" : obj.notes,
					"approval" : obj.approval,
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

			function getSelectedResources() {
				var list = $scope.resourceTypes;
				var newList = [];

				for (var i = 0; i < list.length; i++) {
					if (list[i].selectedStatus == true)
						newList.push(list[i]);

				}

				return newList;
			}

			function update(obj) {
				var selectedResources = getSelectedResources();
			

				$http
						.post(constants.apiUrl + '/meetingroom/update', {
							"mId" : obj.mId,
							"mRoomName" : obj.mRoomName,
							"location" : obj.location,
							"seatingCapacity" : obj.seatingCapacity,
							"admin" : obj.admin,
							"resourceList" : selectedResources,
							"tp" : obj.tp,
							"notes" : obj.notes,
							"approval" : obj.approval,
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
									
										$scope.errorMsg = "Transaction failed. Please check your inputs and try again";
										showErrorMsg(data.message);
									}
								}).error(function(error) {
						
							showErrorMsg(error);

						});
			}

			$scope.deleteItem = function(obj) {
		
				$http.post('weddingMaster/db/delete.php', {
					"uuid" : obj.uuid
				}).success(function(data) {

					if (data == 1) {
						fetch();

					} else{}
				
				})
			}

			$scope.prepareToDelete = function(detail) {
				$scope.emp = detail;

			}

			$scope.editItem = function(obj) {
				$scope.formLabel = "Update Meeting Room";
				if (!$scope.isAddFormVisible) {
					setFormVisibility();
					$scope.isEditFormVisible = !$scope.isEditFormVisible;
				} else {
					$scope.isEditFormVisible = true;
					$scope.isAddFormVisible = false;
				}
				$scope.obj = obj;
				setSelectedResources();

			}

			function setSelectedResources() {
				var list = $scope.obj.resourceList;

				for (var i = 0; i < $scope.resourceTypes.length; i++) {
					$scope.resourceTypes[i].selectedStatus = false;

					for (var j = 0; j < list.length; j++) {
						if (list[j].resourceId == $scope.resourceTypes[i].resourceId)
							$scope.resourceTypes[i].selectedStatus = true;
					}

				}

			}
			
			$scope.activateObj = function(obj) {

				$http.post(constants.apiUrl + '/meetingroom/activate', obj)
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

				$http.post(constants.apiUrl + '/meetingroom/inactivate', obj)
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

meetingApp.controller("meetingRoomController", meetingRoomController);