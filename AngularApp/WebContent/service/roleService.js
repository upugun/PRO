'use strict';
angular.module('roleProvider')

.factory("roleService", ['$http', '$cookieStore', '$rootScope','constantService', function($http, $cookieStore, $rootScope, constantService){
	
	var constants = constantService.getConstants();
	
	var service = {};
        
        service.getRole = function (searchCode, condition) {
         	
        	var response;
        	
         	response = $http.post(constants.apiUrl+'role/fetch', {searchCode:searchCode, condition1:condition }).success(function(data){
     			
         		response =data;
     			
     			
     		
     		}).error(function(error){
     			console.error(error);
     		});
         	
         	return response;
		}
		
		
		
	return service;	
}]);