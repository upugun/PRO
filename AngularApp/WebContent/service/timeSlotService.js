'use strict';
angular.module('timeSlotProvider')

.factory("timeSlotService", ['$http', '$cookieStore', '$rootScope','constantService', function($http, $cookieStore, $rootScope, constantService){
	
	var constants = constantService.getConstants();
	
	var service = {};
        
        service.getTimeSlots = function () {
         	
        	var response;
        	
         	response = $http.post('json/timeSlot.json').success(function(data){
     			
         		response =data;
     			
     			
     		
     		}).error(function(error){
     			console.error(error);
     		});
         	
         	return response;
		}
		
		
		
	return service;	
}]);