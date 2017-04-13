'use strict';
angular.module('pollerModule')

.factory('Poller',['$http', '$timeout',  '$rootScope', 'constantService', function($http, $timeout,$rootScope, constantService) {
  var constants = constantService.getConstants();	
  var data = [];
  
  var userId;
  
  if($rootScope.globals.currentUser!=null)
	  userId = $rootScope.globals.currentUser.userId;
  
  var obj  = { searchCode:"searchByRecieverIdAndReadStatus", condition1:userId , condition2:'1' };
  var poller = function() {
  if($rootScope.globals.currentUser!=null)
	  obj.condition1 = $rootScope.globals.currentUser.userId;
    $http.post(constants.apiUrl+'mail/fetch', { searchCode:obj.searchCode, condition1:obj.condition1 , condition2:obj.condition2 }).then(function(r) {
      data.response = r.data;
      data.calls++;
      $timeout(poller, 1000);
    });
    
  };
  poller();
  
  return {
    data: data
  };
}]);