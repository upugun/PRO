'use strict';
angular.module('navigationProvider')

.factory("navigationService", ['$http', '$cookieStore', '$rootScope','constantService', function($http, $cookieStore, $rootScope, constantService){
	
	var constants = constantService.getConstants();
	
	var service = {};
        
        service.getMainOptionsTree = function (q) {
         	
        	var response;
        	
         	response = $http.post(constants.apiUrl+'permission/main-options', q).success(function(data){
     			
         		response =data;
     			
     			
     		
     		}).error(function(error){
     			console.error(error);
     		});
         	
         	return response;
		}
        
        service.getNaviGation = function (q) {
         	
        	var response;
        	
         	response = $http.post(constants.apiUrl+'permission/main-options-nav', q).success(function(data){
     			
         		response =data;
     			
     			
     		
     		}).error(function(error){
     			console.error(error);
     		});
         	
         	return response;
		}
		
		
		
	return service;	
}]);