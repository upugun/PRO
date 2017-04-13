'use strict';
angular.module('bookingFacilityreportProvider')

.factory("bookingFacilityreportService", ['$http', '$cookieStore', '$rootScope','constantService', function($http, $cookieStore, $rootScope, constantService){
	
	var constants = constantService.getConstants();
	
	var service = {};
        
        service.getFacilities = function (searchCode, condition) {
         	
        	var response;
        	
         	response = $http.post(constants.apiUrl+'booking-facility-report/fetch', { searchCode:searchCode, condition1:condition}).success(function(data){
     			
         		response =data;
     			
     			
     		
     		}).error(function(error){
     			console.error(error);
     		});
         	
         	return response;
		}
		
		
		
	return service;	
}]);