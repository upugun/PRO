'use strict';
angular.module('resourceProvider')

.factory("resourceService", ['$http', '$cookieStore', '$rootScope','constantService', function($http, $cookieStore, $rootScope, constantService){
	
	var constants = constantService.getConstants();
	
	var service = {};
        
        service.getResources = function () {
         	
        	var response;
        	
         	response = $http.post(constants.apiUrl+'resources/fetch', { searchCode:'searchOnlyActive', condition1:'' }).success(function(data){
     			
         		response =data;
     			
     			
     		
     		}).error(function(error){
     			console.error(error);
     		});
         	
         	return response;
		}
		
		
		
	return service;	
}]);