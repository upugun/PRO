var meetingApp = angular.module("resourcesModule", ['ui.bootstrap']);

meetingApp.filter('startFrom', function() {
    return function(input, start) {
        if(input) {
            start = +start; //parse to int
            return input.slice(start);
        }
        return [];
    }
});



var resourcesController = ['$scope', '$cookieStore', '$rootScope','$http', 'constantService','resourceService', function ($scope, $cookieStore, $rootScope,$http, constantService,resourceService) 
{
	
	var constants = constantService.getConstants();
	
	// to get the constant resources
	constantService.getResources().then(function (response) {
	    $scope.resourceTypes = response.data;
	});
	
	
	fetch();
	$scope.isAddFormVisible  = false;
	$scope.isEditFormVisible = false;
	//setFormVisibility();
	$scope.pageSize = 5;
	$scope.currentPage = 1;
	$scope.maxSize = 5;
	
	function fetch( )
	{
		{
	    	// fetch resources
	        resourceService.getResources().then(function (response) {
	    	    $scope.details = response.data;
	    	});
	    }
		

	}
	
	$scope.showAddForm = function()
	{
		$scope.formLabel = "Add Resources";
		
		if(!$scope.isEditFormVisible)
		{
			setFormVisibility();
			$scope.isAddFormVisible = !$scope.isAddFormVisible;
		}
		else
		{
			$scope.isEditFormVisible= false;	
			$scope.isAddFormVisible = true;	
		}	
			
		clearForm();	
		
	}
	
	function clearForm()
	{
		var obj = {};
		$scope.obj = obj;
	}
	
	function setFormVisibility()
	{
		$('#empForm').slideToggle();
		//$scope.isAddFormVisible = !$scope.isAddFormVisible;
	}
	
	$scope.createObj = function(obj)
	{
		if($scope.isAddFormVisible)
			insertObj(obj);
		else if($scope.isEditFormVisible)
			updateObj(obj);
	}
	
	$scope.openItem = function(obj)
	{
		$rootScope.globals.selectedWedding = obj;
		$cookieStore.put('globals', $rootScope.globals);
		window.resources = "#/group";
		//console.log($rootScope);
	}
	
	function insertObj(obj)
	{
		$http.post(constants.apiUrl+'resources/insert',{"rName":obj.rName,"rType":obj.rType,"owner":obj.owner,"detail":obj.detail,"common":{"status":obj.common.status, "createdById":'1'}}).success(function(data){
			if (data.success == true) 
			{
				fetch();
				setFormVisibility();
				$scope.isAddFormVisible = !$scope.isAddFormVisible;
			}
			else
				console.log(data);
		}).err
	}
	
	function updateObj(obj)
	{
		$http.post(constants.apiUrl+'/resources/update',{"rid":obj.rid,"rName":obj.rName,"rType":obj.rType,"owner":obj.owner,"detail":obj.detail,"common":{"status":obj.common.status, "updatedById":'1'}}).success(function(data){
			if (data.success == true) 
			{
				fetch();
				setFormVisibility();
				$scope.isEditFormVisible = !$scope.isEditFormVisible;
			}
			else
				console.log(data);
			
		}).error(function(error){
			console.error(error);
		});
	}
	
	$scope.deleteObj= function(obj)
	{
	//console.log(obj);
		$http.post('weddingMaster/db/delete.php',{"lid":obj.lid}).success(function(data){
			
			if (data == 1) 
			{
				fetch();
				
			}
			else
				console.log(data);
		})
	}
	
	$scope.prepareToDelete = function(detail)
	{
		$scope.obj = detail;
		
	}
	
	$scope.editObj = function(obj)
	{
		$scope.formLabel = "Update Meeting Room";
		if(!$scope.isAddFormVisible)
		{
			setFormVisibility();
			$scope.isEditFormVisible = !$scope.isEditFormVisible;
		}
		else
		{
			$scope.isEditFormVisible= true;	
			$scope.isAddFormVisible = false;	
		}			
		$scope.obj = obj;
				
	}
		
}];
meetingApp.controller("resourcesController", resourcesController);