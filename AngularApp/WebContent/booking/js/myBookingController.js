var meetingApp = angular.module("myBookingModule", [ 'ui.bootstrap' ]);

meetingApp.filter('startFrom', function() {
	return function(input, start) {
		if (input) {
			start = +start; // parse to int
			return input.slice(start);
		}
		return [];
	}
});

var myBookingController = [
		'$scope',
		'$cookieStore',
		'$rootScope',
		'$http',
		'constantService',
		'meetingRoomService',
		'facilitiesService',
		'bookingService',
		'timeSlotService',
		function($scope, $cookieStore, $rootScope, $http, constantService,
				meetingRoomService, facilitiesService, bookingService,
				timeSlotService) {

			// to get the constant resources
			constantService.getResources().then(function(response) {
				$scope.resourceTypes = response.data;
			});

			var constants = constantService.getConstants();

			$rootScope.globals = $cookieStore.get('globals') || {};

			$scope.currentUser = $rootScope.globals.currentUser;
			$scope.statusBooking = "PENDING";
			fetch();
			$scope.isAddFormVisible = false;
			$scope.isEditFormVisible = false;

			$scope.pageSize = 5;
			$scope.currentPage = 1;
			$scope.maxSize = 5;

			$scope.selectmeetingRoom = function(obj) {
				getSelectedResources(obj);
			}

			function getSelectedResources(obj) {
				var list = obj.resourceList;
				$scope.availableList = [];
				$scope.requestList = [];

				for (var i = 0; i < $scope.resourceTypes.length; i++) {
					var isAvailable = false;

					for (var j = 0; j < list.length; j++) {

						if (list[j].resourceId == $scope.resourceTypes[i].resourceId) {
							isAvailable = true;
							$scope.availableList.push($scope.resourceTypes[i]);
							break;
						} else
							isAvailable = false;
					}

					if (!isAvailable
							&& $scope.resourceTypes[i].requestable == "true")
						$scope.requestList.push($scope.resourceTypes[i]);

				}
			}

			// setFormVisibility();
			$scope.pageSize = 5;
			$scope.currentPage = 1;
			$scope.maxSize = 5;

			$scope.getBookingBycreatedByIds = function() {
				fetch();
			}

			function fetch() {
				var q = {
					"searchCode" : "searchBycreatedById",
					"condition1" : $scope.statusBooking,
					"condition2" : $scope.currentUser.userId
				};
				// fetch booking
				bookingService.getBookings(q).then(function(response) {
					$scope.booking = response.data;
				});

				// fetch meeting rooms
				meetingRoomService.getMeetingRooms("searchOnlyActive", "").then(function(response) {
					$scope.meetingrooms = response.data;
				});
				// fetch Facilities

				facilitiesService.getFacilities("searchOnlyActive", "").then(function(response) {
					$scope.facilities = response.data;
				});

				// fetch timeSlot

				timeSlotService.getTimeSlots().then(function(response) {
					$scope.timeSlots = response.data;
				});

			}

			$scope.showAddForm = function() {
				$scope.formLabel = "Add booking";

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
					meetingRoomId : "",
					title : "",
					start : "",
					status : "",
					remarks : ""

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
			
			}

			function insertObj(obj) {
				
							
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

			function arrangeResourcesForSaving() {
				var list = [];
				for (var i = 0; i < $scope.requestList.length; i++) {
					if ($scope.requestList[i].selectedStatus == true) {
						var obj = {
							"bookingId" : 0,
							"resourceId" : $scope.requestList[i].resourceId,
							"status" : "ACTIVE"
						};
						list.push(obj);
					}
				}

				return list;
			}

			function arrangeFacilitiesForSaving() {
				var list = [];
				for (var i = 0; i < $scope.facilities.length; i++) {
					if ($scope.facilities[i].selectedStatus == true) {
						var obj = {
							"bookingId" : 0,
							"facilityId" : $scope.facilities[i].fid,
							"status" : "ACTIVE"
						};
						list.push(obj);
					}
				}

				return list;
			}

			function getSelectedStartDateAndTime(startDate, startTime) {
				var date1 = getDateToString(startDate) + ' ' + startTime;

				return date1;
			}

			function getSelectedEndDateAndTime(endaDate, endTime) {
				var date1 = getDateToString(endaDate) + ' ' + endTime;

				return date1;
			}

			function getDateToString(date) {
				var d = date.getDate();
				d = d > 9 ? d : '0' + d;
				var m = date.getMonth() + 1;
				m = m > 9 ? m : '0' + m;
				var y = date.getFullYear();

				var sDate = y + '-' + m + '-' + d;
				return sDate;
			}

			function updateObj(obj) {

				var startDate = getSelectedStartDateAndTime(obj.startDate,
						obj.startTime);
				obj.start = startDate;

				var endDate;
				if (obj.repeating)
					endDate = getSelectedEndDateAndTime(obj.endDate,
							obj.endTime);
				else
					endDate = getSelectedStartDateAndTime(obj.startDate,
							obj.endTime);
				obj.end = endDate;

				var reqList = arrangeResourcesForSaving();
				var facList = arrangeFacilitiesForSaving();

				$http.post(constants.apiUrl + '/booking/update', {
					"bid" : obj.bid,
					"meetingRoom" : obj.meetingRoom,
					"title" : obj.title,
					"start" : obj.start,
					"end" : obj.end,
					"allDay" : false,
					"remarks" : obj.remarks,
					"repeating" : obj.repeating,
					"bookingStatus" : obj.bookingStatus,
					"resourcesList" : reqList,
					"facilityList" : facList,
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
					
					}
					
					else{
						showErrorMsg(data.message);
					}
				}).error(function(error) {
					showErrorMsg(error);
				});
			}

			$scope.deleteObj = function(obj) {
			
				$http.post(constants.apiUrl + '/booking/delete', {
					"bid" : obj.bid
				
				}).success(function(data) {
					console.log(data);
					if (data.success == true) {
						
						fetch();
						showSuccessMsg(data.message);

					} else
						{}
				}).error(function(error) {
					showErrorMsg(error);
				});
			}

			$scope.prepareToDelete = function(detail) {
				$scope.obj = detail;

			}

			$scope.activateObj = function(obj) {

				$http.post(constants.apiUrl + '/booking/activate', obj)
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

				$http.post(constants.apiUrl + '/booking/inactivate', obj)
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

			$scope.editObj = function(obj) {
				$scope.formLabel = "Update Booking";
				
				if (!$scope.isAddFormVisible) {
					setFormVisibility();
					$scope.isEditFormVisible = !$scope.isEditFormVisible;
				} else {
					$scope.isEditFormVisible = true;
					$scope.isAddFormVisible = false;
				}
				setDateFormatsForDisplay(obj);
				$scope.obj = obj;
				getSelectedResources(obj.meetingRoom);
				setSelectedResources();
				setSelectedFacilities();

			}

			function setDateFormatsForDisplay(obj) {
				obj.startDate = new Date(obj.start);
				obj.endDate = new Date(obj.end);
			}

			function setSelectedResources() {
				var list = $scope.obj.resourcesList;

				for (var i = 0; i < $scope.requestList.length; i++) {
					$scope.requestList[i].selectedStatus = false;

					for (var j = 0; j < list.length; j++) {
						if (list[j].resourceId == $scope.requestList[i].resourceId)
							$scope.requestList[i].selectedStatus = true;
					}

				}

			}

			function setSelectedFacilities() {
				var list = $scope.obj.facilityList;

				for (var i = 0; i < $scope.facilities.length; i++) {
					$scope.facilities[i].selectedStatus = false;

					for (var j = 0; j < list.length; j++) {
						if (list[j].facilityId == $scope.facilities[i].fid)
							{
							$scope.facilities[i].selectedStatus = true;
							$scope.facilities[i].count			= list[j].count;
							}
						}

				}

			}

		} ];

meetingApp.controller("myBookingController", myBookingController);