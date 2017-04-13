'use strict';
angular.module('mailProvider')


.factory("mailService", ['$http', '$cookieStore', '$rootScope','constantService', function($http, $cookieStore, $rootScope, constantService){
	
	var constants = constantService.getConstants();
	
	var service = {};
        
        service.getMails = function (obj) {
         	
        	var response;
        	
         	response = $http.post(constants.apiUrl+'mail/fetch', { searchCode:obj.searchCode, condition1:obj.condition1 , condition2:obj.condition2 }).success(function(data){
     			
         		response =data;
     			
     			
     		
     		}).error(function(error){
     			console.error(error);
     		});
         	
         	return response;
		}
		
		
		
	return service;	
}]);