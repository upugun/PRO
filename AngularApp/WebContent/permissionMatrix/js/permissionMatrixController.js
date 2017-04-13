var meetingApp = angular.module("permissionMatrixModule", ['ui.bootstrap']);

meetingApp.filter('startFrom', function() {
    return function(input, start) {
        if(input) {
            start = +start; //parse to int
            return input.slice(start);
        }   
        return [];
    }
});



var permissionMatrixController = ['$scope', '$cookieStore', '$rootScope','$http', 'constantService', 'roleService', 'navigationService', 'permissionMatrixService', function ($scope, $cookieStore, $rootScope,$http, constantService, roleService, navigationService, permissionMatrixService) 
{
	$scope.matrixList = [];
	
	// to get the constant resources
	constantService.getResources().then(function (response) {
	    $scope.resourceTypes = response.data;
	});
	
	
	var constants = constantService.getConstants();
	
	$rootScope.globals = $cookieStore.get('globals') || {};
	
	$scope.currentUser = $rootScope.globals.currentUser;
	
	fetch();
	$scope.isAddFormVisible  = false;
	$scope.isEditFormVisible = false;
	
	//setFormVisibility();
	$scope.pageSize = 5;
	$scope.currentPage = 1;
	$scope.maxSize = 5;
	
	  function fetch()
	    {
	    	// fetch meeting rooms
	        roleService.getRole("searchOnlyActive", "").then(function (response) {
	    	    $scope.roles = response.data;
	    	});
	        var qr = {"searchCode":"searchMain", "condition1": "", "condition2":""};
	        navigationService.getMainOptionsTree(qr).then(function (response) {
		    	$scope.permissions = response.data;
	        });
	        
	        var q = {"searchCode":"searchOnlyActive", "condition1": "", "condition2":""};
	        permissionMatrixService.getPermisionMatrix(q).then(function (response) {
		    	$scope.matrixList = response.data;
	        });
	    }
	  
	$scope.isPermissionAllocated = function(permissionId, roleId)
	{
		var isAllocated = false;
		
		for(var  i=0; i<$scope.matrixList.length;i++)
		{
			if(permissionId == $scope.matrixList[i].permissionId && roleId == $scope.matrixList[i].roleId)
			{
				isAllocated = true;
				break;
			}
		}
		return isAllocated;
	}
	
	$scope.setPermission = function(permissionId, roleId, selectedStatus)
	{
		
		var obj = {"roleId":roleId, "permissionId":permissionId, "common":{"createdById":$scope.currentUser.userId, "status":"ACTIVE"}};
		
		if(selectedStatus)
			insertObj(obj);
		else
			deleteObj(obj)
	}

	$scope.showAddForm = function()
	{
		$scope.formLabel = "Add Role";
		
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
	
	
	$scope.openItem = function(obj)
	{
		$rootScope.globals.selectedWedding = obj;
		$cookieStore.put('globals', $rootScope.globals);
		window.location = "#/group";
		
	}
	
	function insertObj(obj)
	{
		$http.post(constants.apiUrl+'permission-matrix/insert',obj).success(function(data){
			if (data.success == true) 
			{
				fetch();
				
				showSuccessMsg(data.message);
			}
			else{
				
				 showErrorMsg(data.message);
				
				}
		}).error(function(error) {

			showErrorMsg(error);
		});
	}
	
	function showSuccessMsg(msg)
	{
		$('#a_success').show();
		$('#a_error').hide();
		$scope.successMsg = msg;
	}
	
	function showErrorMsg(msg)
	{
		$('#a_success').hide();
		$('#a_error').show();
		$scope.errorMsg = msg;
	}
	
	
	
	
	function deleteObj(obj)
	{
		
		$http.post(constants.apiUrl+'permission-matrix/delete',obj).success(function(data){
			if (data.success == true) 
			{
				fetch();
				
				showSuccessMsg(data.message);
			}
			else
			{
			
				
				ShowErrorMsg(data.message);
			}
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

meetingApp.controller("permissionMatrixController", permissionMatrixController);