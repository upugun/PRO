var meetingApp = angular.module("messageModule", ['ui.bootstrap']);

meetingApp.filter('startFrom', function() {
    return function(input, start) {
        if(input) {
            start = +start; //parse to int
            return input.slice(start);
        }
        return [];
    }
});



var messageController = ['$scope', '$cookieStore', '$rootScope','$http' ,'constantService', 'mailService' , function ($scope, $cookieStore, $rootScope,$http , constantService, mailService) 
{
	
	// to get the constant resources
	constantService.getResources().then(function (response) {
	    $scope.resourceTypes = response.data;
	});
	
	var constants = constantService.getConstants();
	
	$('.fa-star').on('click', function () {
	      $(this).toggleClass('star-checked');
	    });
	
	$rootScope.globals = $cookieStore.get('globals') || {};
	
	$scope.currentUser = $rootScope.globals.currentUser;
	
	fetch();
	//setFormVisibility();
	$scope.pageSize = 5;
	$scope.currentPage = 1;
	$scope.maxSize = 5;
	
	$scope.message;
	
	function fetch()
	{
		var obj  = { searchCode:"searchByRecieverId", condition1:$scope.currentUser.userId , condition2:'' };
		mailService.getMails(obj).then(function (response) {
		    $scope.details = response.data;
		});
	}
	
	$scope.getStartClass = function(obj)
	{
		var startClass = "snf-star-empty";
		if(obj.readStatus)
			startClass = "snf-star-yellow";
		
		return startClass;
	}
	
	$scope.showMessage = function(obj)
	{
		$scope.message = obj.message;
		markAsRead(obj);
	}
	
	function clearForm()
	{
		var obj = {weddingName:"", weddingDate:"", startTime:"", status:""};
		$scope.obj = obj;
	}
	
	function markAsRead(obj)
	{
		obj.readStatus = 0;
		
		$http.post(constants.apiUrl+'mail/set-read-status',{"mailId":obj.mailId, "readStatus":obj.readStatus}).success(function(data){
			if (data.success == true) 
			{
				$scope.getStartClass(obj)
			}
			else
				console.log(data);
		}).err
	}
	
	
	$scope.deleteItem = function(obj)
	{
	//console.log(obj);
		$http.post(constants.apiUrl+'mail/delete',{"mailId":obj.mailId}).success(function(data){
			
			if (data.success == true) 
			{
				fetch();
				$scope.message = "";
			}
			else
				console.log(data);
		})
	}
	
	
}];

meetingApp.controller("messageController", messageController);