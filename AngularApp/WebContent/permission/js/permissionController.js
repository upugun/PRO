var meetingApp = angular.module("permissionModule", ['ui.bootstrap']);

meetingApp.filter('startFrom', function() {
    return function(input, start) {
        if(input) {
            start = +start; // parse to int
            return input.slice(start);
        }   
        return [];
    }
});



var permissionController = ['$scope', '$cookieStore', '$rootScope','$http', 'constantService', 'permissionService', function ($scope, $cookieStore, $rootScope,$http, constantService, permissionService) 
{
        
        // to get the constant resources
        constantService.getResources().then(function (response) {
            $scope.resourceTypes = response.data;
        });
        
        var constants = constantService.getConstants();
        
        $rootScope.globals = $cookieStore.get('globals') || {};
        
        $scope.currentUser = $rootScope.globals.currentUser;
        
    	$scope.permissionStatus = "ACTIVE";
        
        fetch();
        $scope.isAddFormVisible  = false;
        $scope.isEditFormVisible = false;
        // setFormVisibility();
        $scope.pageSize = 5;
        $scope.currentPage = 1;
        $scope.maxSize = 5;
        
    	
		$scope.getPermissionByStatus = function() {
			fetch();
		}
        
        
          function fetch()
            {
        	  $scope.mainOptions = [];
                    // fetch meeting rooms
        	  var q = {"searchCode":"searchByStatus", "condition1": $scope.permissionStatus, "condition2":""};
        	  permissionService.getPermissions(q).then(function (response) {
                        $scope.details = response.data;
                        getAllMainOptions();
                    });
        	  
            }
          
        function getAllMainOptions()
        {
        	for(var i=0;i<$scope.details.length; i++)
        		{
        			if($scope.details[i].optionType == 'MAIN')
        				$scope.mainOptions.push($scope.details[i]);
        		}
        }

        $scope.showAddForm = function()
        {
                $scope.formLabel = "Add Option";
                
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
                // $scope.isAddFormVisible = !$scope.isAddFormVisible;
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
                window.location = "#/group";
                // console.log($rootScope);
        }
        
        function insertObj(obj)
        {
        	var parentID = null;
    		if(obj.parentOption!=null)
    			parentID = obj.parentOption.oId;
                $http.post(constants.apiUrl+'permission/insert',{"optionName":obj.optionName,"optionLink":obj.optionLink,"optionType":obj.optionType,"optionIcon":obj.optionIcon,"optionOrder":obj.optionOrder,"parentID":parentID,"common":{"status":obj.common.status, "createdById":$scope.currentUser.userId}}).success(function(data){
                        if (data.success == true) 
                        {
                                fetch();
                                setFormVisibility();
                                $scope.isAddFormVisible = !$scope.isAddFormVisible;
                            
                            	showSuccessMsg(data.message);
                        }
                        else{
                         
                            	showErrorMsg(data.message);
                        }   }).error(function(error) {
        					
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
        
        
        
        
        function updateObj(obj)
        {
        		var parentID = null;
        		if(obj.parentOption!=null)
        			parentID = obj.parentOption.oId;
                $http.post(constants.apiUrl+'/permission/update',{"oId":obj.oId,"optionName":obj.optionName,"optionLink":obj.optionLink,"optionType":obj.optionType,"optionIcon":obj.optionIcon,"optionOrder":obj.optionOrder,"parentID":parentID,"common":{"status":obj.common.status, "updatedById":$scope.currentUser.userId}}).success(function(data){
                        if (data.success == true) 
                        {
                                fetch();
                                setFormVisibility();
                                $scope.isEditFormVisible = !$scope.isEditFormVisible;
                            
                            	showSuccessMsg(data.message);
                        }
                        else{
                              
                        	showErrorMsg(data.message);
                        	
                    	showErrorMsg();
                        } }).error(function(error){
                       
                    	showErrorMsg(error);
                });
        }
        
        $scope.deleteObj= function(obj)
        {
        // console.log(obj);
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
                setSelectedParenObj(obj);
                $scope.obj = obj;
        }
        
        function setSelectedParenObj(obj)
        {
        	for(var i=0; i<$scope.mainOptions.length; i++)
        	{
        		if($scope.mainOptions[i].oId == obj.parentID)
        		{
        			obj.parentOption = $scope.mainOptions[i];
        			break;
        		}
        	}
        	
        }
        
        
    	$scope.activateObj = function(obj) {

			$http.post(constants.apiUrl + '/permission/activate', obj)
					.success(function(data) {
						if (data.success == true) {
							fetch();
							showSuccessMsg(data.message);
						} else{
							showErrorMsg(data.message);
						}

					}).error(function(error) {
						showErrorMsg(error);
					});
		}

		$scope.inactivateObj = function(obj) {

			$http.post(constants.apiUrl + '/permission/inactivate', obj)
					.success(function(data) {
						if (data.success == true) {
							fetch();
							showSuccessMsg(data.message);
							
						} else{
							showErrorMsg(data.message);
						}
					}).error(function(error) {
						showErrorMsg(error);
					});
		}
                
}];

meetingApp.controller("permissionController", permissionController);
