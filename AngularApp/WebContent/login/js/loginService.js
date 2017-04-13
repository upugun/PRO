'use strict';
angular.module('loginModule')

.factory("loginService", ['$http', '$cookieStore', '$rootScope', 'constantService', function($http, $cookieStore, $rootScope, constantService){
	
	var service = {};
	
	var constants = constantService.getConstants();

        service.login = function (username, password, callback) {
			
			$http.post(constants.apiUrl+'meeting/login', { searchCode:'loginId', condition1: username, condition2: password })
                .success(function (data) {
					
                	var response =data;
                	//console.log(response);
                	
                    callback(response);
                }).error(function(error){
			//console.log(error);
		});
				
		}
		
		service.SetCredentials = function (username, message) {
            var authdata = username + ':' + message;
 
            $rootScope.globals = {
                currentUser: {
                    username: username,
                    userId  : message,
                    authdata: authdata
                }
            };
 
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $cookieStore.put('globals', $rootScope.globals);
        };
		
		 service.ClearCredentials = function () {
            $rootScope.globals = {};
            $cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
        };
		
	return service;	
}]);