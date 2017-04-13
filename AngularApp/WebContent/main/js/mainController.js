var meetingApp = angular.module("mainModule", ['ui.bootstrap','ui.calendar']);




var mainController = ['$scope', '$cookieStore', '$rootScope','$http','constantService', 'meetingRoomService', 'bookingService', 'facilitiesService','timeSlotService', function ($scope, $cookieStore, $rootScope,$http, constantService, meetingRoomService, bookingService,facilitiesService,timeSlotService,eventService, uiCalendarConfig) 
{   
	
	$rootScope.globals = $cookieStore.get('globals') || {};
	
	$scope.currentUser = $rootScope.globals.currentUser;
	 $scope.currentdate = new Date().toISOString().slice(0, 10);
	constantService.getResources().then(function (response) {
	    $scope.resourceTypes = response.data;
	});
	
	
	//fetch facilities
	facilitiesService.getFacilities("searchOnlyActive", "").then(function (response) {
	    $scope.facilities = response.data;
	 
	});
	
	var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
	
    newBooking();
    
    $scope.events		= [];
    $scope.eventSources = [];
    $scope.booking=[];
    
    $scope.selectmeetingRoom = function(obj)
    {
    	flushEvents();
    	$scope.obj = obj;
    	setSelectedResources();
    	fetch();
    	getSelectedResources();
    }
    
    function flushEvents()
    {
    	$scope.events.splice(0,$scope.events.length);
    	
    	for(var i=0; i<$scope.events.length; i++)
    	{
    		$scope.events.splice(i,1);
		}
    	
    }
    
    function newBooking()
    {
    	$scope.booking = {startDate: ""};
    }
    
    function fetch(){
    	
    	
    	var q = {"searchCode":"searchByStatusAndmeetingID", "condition1": "APPROVED", "condition2":$scope.obj.mId};
    	bookingService.getBookings(q).then(function (response) {
    		var data = response.data;
       	 	for(var i = 0; i < data.length; i++)
            {
       	 		$scope.events[i] = {id:data[i].bid, title: data[i].title,start: new Date(data[i].start), end: new Date(data[i].end),allDay: false,status:data[i].bookingStatus, booking:data[i], stick : true};
            }
       	 	
       	// fetch timeSlot

			timeSlotService.getTimeSlots().then(function(response) {
				$scope.timeSlots = response.data;
			});

       	 	
    	});
    }
    
    
    function getSelectedResources()
	{
    	
		var list   = $scope.obj.resourceList;
		$scope.availableList  =[];
		$scope.requestList    =[];
		
		for(var i=0; i<$scope.resourceTypes.length; i++)
		{
			var isAvailable = false;
			
			for(var j=0; j<list.length; j++)
			{
				
				if(list[j].resourceId == $scope.resourceTypes[i].resourceId)
				{
					isAvailable = true;
					$scope.availableList.push($scope.resourceTypes[i]);
					break;
				}
				else
					isAvailable = false;
			}
			
			if(!isAvailable && $scope.resourceTypes[i].requestable=="true")
				$scope.requestList.push($scope.resourceTypes[i]);
			
		}
	}

    
    function setSelectedResources()
	{
    	
		var list   = $scope.obj.resourceList;
		
		for(var i=0; i<$scope.resourceTypes.length; i++)
		{
			$scope.resourceTypes[i].selectedStatus = false;
			
			$scope.resourceTypes[i].disabledColor = " snf-disabled";
			
			for(var j=0; j<list.length; j++)
			{
				
				if(list[j].resourceId == $scope.resourceTypes[i].resourceId)
				{
					$scope.resourceTypes[i].selectedStatus 	= true;
					$scope.resourceTypes[i].disabledColor 	= "";
					break;
				}
			}
			
		}
		
	}
    		
    
	$scope.createObj=function (obj) {
		
		obj.meetingRoom = $scope.obj;
		
		var startDate = getSelectedStartDateAndTime(obj.startDate, obj.startTime);
		obj.start     = startDate;
		
		var endDate;
		if(obj.repeating)
			endDate   = getSelectedEndDateAndTime(obj.endDate, obj.endTime);
		else
			endDate   = getSelectedStartDateAndTime(obj.startDate, obj.endTime);
		obj.end		  = endDate;
		
		var reqList	  = arrangeResourcesForSaving();
		var facList	  = arrangeFacilitiesForSaving();
		
		$http.post(constants.apiUrl + 'booking/create', {
			
			"bid":obj.bid,
			"meetingRoom" : obj.meetingRoom,
			"title" : obj.title,
			"start" : obj.start,
			"end" : obj.end,
			"allDay" : false,
			"remarks" : obj.remarks,
			"repeating" : obj.repeating,
			"bookingStatus" : 'PENDING',
			"resourcesList" : reqList,
			"facilityList"  : facList,
			"common" : {
				"status" : 'ACTIVE',
				"createdById" : $scope.currentUser.userId
			}
		}).success(function(data) {
			if (data.success == true) {
				fetch();
				newBooking();
				showSuccessMsg(data.message);
				
			} else
			{
				showErrorMsg(data.message);
			}
		}).error(function(error) {

			showErrorMsg(error);
		});
		clearForm();
	}
	
	function showSuccessMsg(msg)
	{
		$('#a_success').modal();
		$scope.successMsg = msg;
		
	}
	
	function showErrorMsg(msg)
	{
		$('#a_error').modal();
		$scope.errorMsg = msg;
	}
	
	$scope.closeObj=function(obj){
		
		clearForm();
	}
	
	$scope.editItem = function(obj)
	{
		setDateFormatsForDisplay(obj);
		$scope.booking = obj;
		displayBookingSelectedResources();
		displaySelectedFacilities();
		$('#calendarModal').modal();
	}
	

	function clearForm() {
		var obj = {
			meetingRoomId : "",
			title : "",
			start : "",
			status : "",
			remarks : ""

		};
		clearDynamicCheckBoxes($scope.resourceTypes);
		clearDynamicCheckBoxes($scope.facilities);
		$scope.obj = obj;
	}
	
	function clearDynamicCheckBoxes(list)
	{
		for(var i=0; i<list.length; i++)
		{
			list[i].selectedStatus = false;
		}
	}
	function displayBookingSelectedResources() {
		var list = $scope.booking.resourcesList;

		for (var i = 0; i < $scope.requestList.length; i++) {
			$scope.requestList[i].selectedStatus = false;

			for (var j = 0; j < list.length; j++) {
				if (list[j].resourceId == $scope.requestList[i].resourceId)
					$scope.requestList[i].selectedStatus = true;
			}

		}

	}
	
	function displaySelectedFacilities() {
		var list = $scope.booking.facilityList;

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
	
	function setDateFormatsForDisplay(obj) {
		obj.startDate = new Date(obj.start);
		obj.endDate = new Date(obj.end);
	}
	
	function arrangeResourcesForSaving()
	{
		var list =[];
		for(var i=0;i<$scope.requestList.length; i++)
		{
			if($scope.requestList[i].selectedStatus == true)
			{
				var obj = {"bookingId":0, "resourceId":$scope.requestList[i].resourceId, "status":"ACTIVE"};
				list.push(obj);
			}
		}
		
		return list;
	}
	
	function arrangeFacilitiesForSaving()
	{
		var list =[];
		for(var i=0;i<$scope.facilities.length; i++)
		{
			if($scope.facilities[i].selectedStatus == true)
			{
				var obj = {"bookingId":0, "facilityId":$scope.facilities[i].fid, "count":$scope.facilities[i].count,"facilities":$scope.facilities[i], "status":"ACTIVE"};
				list.push(obj);
				console.log($scope.facilities[i]);
			}
		}
		
		return list;
	}
	
	function getSelectedStartDateAndTime(startDate, startTime)
	{
		var date1 = getDateToString(startDate) +' '+ startTime;
		
		return date1;
	}
	
	function getSelectedEndDateAndTime(endaDate, endTime)
	{
		var date1 = getDateToString(endaDate) +' '+ endTime;
		
		return date1;
	}
	
	function getDateToString(date)
    {
           var d = date.getDate();
           d = d>9 ? d : '0' + d;
           var m = date.getMonth()+1;
           m = m>9 ? m : '0' + m;
           var y = date.getFullYear();
           
           var sDate = y+'-'+m+'-'+d;
           return sDate;
     }

	
    // fetch meeting rooms
	meetingRoomService.getMeetingRooms("searchOnlyActive", "").then(function(response) {
		$scope.details = response.data;
	});

    
  //with this you can handle the click on the events
    $scope.eventClick = function(events){           
         // alert(event.title + ' is clicked');
          $('#eventModal').modal();
          $scope.bookingsAdded = events.booking;
         
         
          console.log($scope.currentdate);
          
         
    };
    
	 /* config object */
    $scope.uiConfig = {
      calendar:{
        height: 450,
        editable: false,
        header:{
          left: 'month,agendaWeek,agendaDay',
          center: 'title',
          right: 'today prev,next'
        	  
        },
        
        dayClick: function(date, jsEvent, view , start) {
        	
        	  var check =  moment(date).toDate();
        	  var today = new Date();
        	  
        	  var checkFormatedDate = new Date(getDateToString(check));
        	  var todayFormatedDate = new Date(getDateToString(today));
        	if($scope.obj != null && (checkFormatedDate.getTime()>=todayFormatedDate.getTime()))
        	{
	        	
	        	newBooking();
	        	$scope.booking.startDate = check;
	        	$scope.booking.endDate   = $scope.booking.startDate;
	            $('#calendarModal').modal();
        	}     
                 
        	else if($scope.obj != null && (checkFormatedDate.getTime()<=todayFormatedDate.getTime())){
        		console.log($scope.obj);
        		  $('#warningModal').modal();
        		  $(".content .value").html("World!");
        		  document.getElementById("MyEdit").innerHTML = "Please select a valid date";
        		  
        		
        	}  
        	
        	else{
        		
        		  $('#warningModal').modal();
        		  document.getElementById("MyEdit").innerHTML = "Please select a Meeting Room to proceed.";
        	}

          },
          
          
        eventClick: $scope.eventClick,
        eventDrop: $scope.alertOnDrop,
        eventResize: $scope.alertOnResize
      }
      
      
    };
    
    var constants = constantService.getConstants();
	var root	  = constants.appRoot;
	$scope.eventSources = [$scope.events];
}];





meetingApp.controller("mainController", mainController);