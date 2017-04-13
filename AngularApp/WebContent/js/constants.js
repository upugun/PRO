'use strict';
angular.module('constantModule')

.factory("constantService", ['$http', '$cookieStore', '$rootScope', function($http, $cookieStore, $rootScope){
	
	var service = {};
	var constant={
			appName:'CheckMyMeeting.com',
			appRoot:'/AngularApp',
			apiUrl : 'http://localhost:8080/MeetingAppSpring/',
			normalUserRoleId : 3
				};
        service.getConstants = function () {
        	return constant;	
				
		}
        
        service.getResources = function () {
        	var response;
        	
        	response = $http.get('json/resources.json').success(function(data){
    			
        		response =data;
    			
    			
    		
    		}).error(function(error){
    			console.error(error);
    		});
        	
        	return response;	
				
		}
		
		
		
	return service;	
}]);