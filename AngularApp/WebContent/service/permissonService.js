'use strict';
angular.module('permissionProvider')

.factory("permissionService", ['$http', '$cookieStore', '$rootScope','constantService', function($http, $cookieStore, $rootScope, constantService){
	
	var constants = constantService.getConstants();
	
	var service = {};
        
        service.getPermissions = function (q) {
         	
        	var response;
        	
         	response = $http.post(constants.apiUrl+'permission/fetch', q).success(function(data){
     			
         		response =data;
     			
     			
     		
     		}).error(function(error){
     			console.error(error);
     		});
         	
         	return response;
		}
		
		
		
	return service;	
}]);