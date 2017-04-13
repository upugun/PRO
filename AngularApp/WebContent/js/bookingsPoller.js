'use strict';
angular.module('bookingPollerModule')

.factory('bookingPoller',['$http', '$timeout',  '$rootScope', 'constantService', function($http, $timeout,$rootScope, constantService) {
  var constants = constantService.getConstants();	
  var data = [];
  var q = {"searchCode":"searchByStatusAndmeetingID", "condition1": "", "condition2":""};
  
  var poller = function() {
    $http.post(constants.apiUrl+'booking/fetch', q).then(function(r) {
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