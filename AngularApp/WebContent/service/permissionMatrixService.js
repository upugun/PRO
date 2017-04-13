'use strict';
angular.module('permissionMatrixProvider')


.factory("permissionMatrixService", ['$http', '$cookieStore', '$rootScope','constantService', function($http, $cookieStore, $rootScope, constantService){
	
	var constants = constantService.getConstants();
	
	var service = {};
        
        service.getPermisionMatrix = function (obj) {
         	
        	var response;
        	
         	response = $http.post(constants.apiUrl+'permission-matrix/fetch', obj).success(function(data){
     			
         		response =data;
     			
     			
     		
     		}).error(function(error){
     			console.error(error);
     		});
         	
         	return response;
		}
		
		
		
	return service;	
}]);