'use strict';
angular.module('bookingProvider')

.factory("bookingService", ['$http', '$cookieStore', '$rootScope','constantService', function($http, $cookieStore, $rootScope, constantService){
	
	var constants = constantService.getConstants();
	
	var service = {};
        
        service.getBookings = function (q) {
         	
        	var response;
        	
         	response = $http({
         		method: 'POST',
         		url: constants.apiUrl+'booking/fetch', 
         		data: q,
         		cache: false
         		}).success(function(data){
     			
         		response =data;
     			
     			
     		
     		}).error(function(error){
     			console.error(error);
     		});
         	
         	return response;
		}
		
		
		
	return service;	
}]);