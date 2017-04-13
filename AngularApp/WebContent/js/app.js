'use strict';

angular.module('meetingModule', []);
angular.module('loginModule', []);
angular.module('mainModule', []);
angular.module('locationModule', []);
angular.module('resourcesModule', []);
angular.module('bookingModule', []);
angular.module('usersModule', []);
angular.module('facilitiesModule', []);
angular.module('constantModule', []);
angular.module('messageModule', []);
angular.module('pollerModule', []);
angular.module('meetingRoomProvider', []);
angular.module('locationProvider', []);
angular.module('userProvider', []);
angular.module('resourceProvider', []);
angular.module('facilitiesProvider', []);
angular.module('mailProvider', []);
angular.module('navigationProvider', []);
angular.module('roleModule',[]);
angular.module('permissonModule',[]);
angular.module('roleProvider',[]);
angular.module('bookingProvider',[]);
angular.module('permissionProvider',[]);
angular.module('permissionMatrixProvider',[]);
angular.module('permissionMatrixModule',[]);
angular.module('timeSlotProvider',[]);
angular.module('myBookingModule', []);
angular.module('bookingFacilityreportModule', []);

var app = angular
		.module(
				"meetingApp",
				[ 'mainModule', 'meetingModule', 'loginModule',
						'locationModule', 'resourcesModule','facilitiesModule', 'bookingModule',
						'usersModule', 'constantModule', 'messageModule','bookingFacilityreportModule', 'pollerModule', 'meetingRoomProvider','locationProvider','bookingProvider','userProvider','resourceProvider','facilitiesProvider', 'mailProvider', 'navigationProvider','permissionProvider','myBookingModule', 'roleModule','permissionModule','roleProvider', 'permissionMatrixProvider', 'permissionMatrixModule','timeSlotProvider','ngRoute', 'ngCookies' ])

		    .config([ '$routeProvider', function($routeProvider) {

			$routeProvider.when('/', {
				controller : 'mainController',
				templateUrl : 'main/main.html',
				hideMenus : false
			})

			.when('/login', {
				controller : 'loginController',
				templateUrl : 'login/login.html',
				hideMenus : true
			})
			.when('/forgot-password', {
				controller : 'loginController',
				templateUrl : 'login/reset.html',
				hideMenus : true
			})
			.when('/reset-password', {
				controller : 'loginController',
				templateUrl : 'login/resetPassword.html',
				hideMenus : true
			})
			.when('/register', {
				controller : 'loginController',
				templateUrl : 'login/register.html',
				hideMenus : true
			})

			.when('/location', {
				controller : 'locationController',
				templateUrl : 'location/location.html',
				hideMenus : false
			})

			.when('/resources', {
				controller : 'resourcesController',
				templateUrl : 'resources/resources.html',
				hideMenus : false
			})

			.when('/booking', {
				controller : 'bookingController',
				templateUrl :'booking/booking.html',
				hideMenus : false
			})

			.when('/users', {
				controller : 'usersController',
				templateUrl : 'users/main.html',
				hideMenus : false
			})

			.when('/meeting-room', {
				controller : 'meetingRoomController',
				templateUrl : 'meetingRoom/main.html',
				hideMenus : false
			})
			
			.when('/user-profile', {
				controller : 'usersController',
				templateUrl : 'users/userProfile.html',
				hideMenus : false
			})
			
			.when('/facilities', {
				controller : 'facilitiesController',
				templateUrl : 'facilities/main.html',
				hideMenus : false
			})
			
			.when('/meetingRDetails', {
				controller : 'mainController',
				templateUrl : 'meetingRoom/details.html',
				hideMenus : false
			})
			
				.when('/user-details', {
				controller : 'usersController',
				templateUrl : 'users/userDetails.html',
				hideMenus : false
			})

			
			.when('/all-messages', {
				controller : 'messageController',
				templateUrl : 'message/main.html',
				hideMenus : false
			})
			
			.when('/message', {
				controller : 'messageController',
				templateUrl : 'message/message.html',
				hideMenus : false
			})
			
              .when('/role', {
				controller : 'roleController',
				templateUrl : 'role/role.html',
				hideMenus : false
			})
			
			.when('/permission', {
				controller : 'permissionController',
				templateUrl : 'permission/permisson.html',
				hideMenus : false
			})
			
			.when('/permission-matrix', {
				controller : 'permissionMatrixController',
				templateUrl : 'permissionMatrix/main.html',
				hideMenus : false
			})
			
				.when('/my-bookings', {
				controller : 'myBookingController',
				templateUrl : 'booking/myBooking.html',
				hideMenus : false
			
		    })
			
			.when('/booking-facilities', {
			controller : 'bookingFacilityreportController',
			templateUrl : 'bookingFacilityreport/main.html',
			hideMenus : false
			})
			
			
			.when('/access-denied', {
				templateUrl : 'unauthorised.html',
				hideMenus : false
			})
			
			.otherwise({
				redirectTo : '/login'
			});
		} ])

		.run(
				[
						'$rootScope',
						'$location',
						'$cookieStore',
						'$http',
						'navigationService',
						function($rootScope, $location, $cookieStore, $http, navigationService) {
							// keep user logged in after page refresh
							$rootScope.globals = $cookieStore.get('globals')
									|| {};
							if ($rootScope.globals.currentUser) {
								$http.defaults.headers.common['Authorization'] = 'Basic '
										+ $rootScope.globals.currentUser.authdata; // jshint
																					// ignore:line
							}
							
							$rootScope.$on('$routeChangeStart', function(
									event, next, current) {
									
								if($rootScope.globals.currentUser!=null)
								{
									var qr = {"searchCode":"searchMainByRole", "condition1": $rootScope.globals.currentUser.userId, "condition2":""};
									navigationService.getNaviGation(qr).then(function (response) {
										$rootScope.navBarOptions = response.data;
										
										if(!isAccessGranted($location.path()))
											$location.path('/access-denied');
								    });
								}
									
							});
							
							function isAccessGranted(userUrl)
							{
								var hasAccess = false;
								
								if($location.path()== '/login' || $location.path()== '/' || $location.path()== '/message'|| $location.path()== '/all-messages' || $location.path()== '/user-details' || $location.path() == '/user-profile'|| $location.path() == '/forgot-password' )
									hasAccess = true;
								else
									hasAccess = checkForPermission($rootScope.navBarOptions, userUrl);
								
								return hasAccess;
							}
							
							function checkForPermission(arry, userUrl)
							{
								var hasAccess = false;
								
								for(var i=0; i<arry.length; i++)
								{
									var checkUrl = "/"+arry[i].optionLink;
									
									if(arry[i].optionType == 'MAIN' && arry[i].suboptions.length>0)
									{
										hasAccess =  checkForPermission(arry[i].suboptions, userUrl);
										
										if(hasAccess)
											break;
									}
									else if(checkUrl == userUrl)
									{
										
										hasAccess = true;
										break;
									}
								}
								
								return hasAccess;
							}

							$rootScope.$on('$locationChangeStart', function(
									event, next, current) {

								if ($location.path() !== '/login'
										&& !$rootScope.globals.currentUser) {
									$location.path('/login');
								}
							});
							
							$rootScope.location = $location;

							$rootScope.loggedInUser = $rootScope.globals.currentUser;
						} ])
						
		.filter('cut', function () {
        return function (value, wordwise, max, tail) {
            if (!value) return '';

            max = parseInt(max, 10);
            if (!max) return value;
            if (value.length <= max) return value;

            value = value.substr(0, max);
            if (wordwise) {
                var lastspace = value.lastIndexOf(' ');
                if (lastspace !== -1) {
                  //Also remove . and , so its gives a cleaner result.
                  if (value.charAt(lastspace-1) === '.' || value.charAt(lastspace-1) === ',') {
                    lastspace = lastspace - 1;
                  }
                  value = value.substr(0, lastspace);
                }
            }

            return value + (tail || ' …');
        };
    })
    
    .directive('numbersOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^0-9]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }            
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
})
		.controller(
				'navController',
				[
						'$scope',
						'$route',
						'$location',
						'constantService', 'Poller', 'userService', 'navigationService',
						function($scope, $route, $location, CONSTANTS, poller, userService, navigationService) {
							$scope.$route = $route;
							$scope.$watch("$route.current.$$route.hideMenus",
									function(v) {
										$scope.hideMenus = v;
									})

							$scope.hasSubMenu = function(arry) {
								if (arry !== 'undefined') {
									if (arry.length > 0)
										return true;
									else
										return false;
								} else
									return false;
							}

							$scope.isManageServiceActive = function(viewUrl) {
								var temp = false;
								console.log(viewUrl);
								console.log($location.path());
								if ($location.path() != viewUrl)
									return temp = true;
								else
									return temp = false;

							}

							$scope.checkNavUrl = function(option) {
								if (option.optionLink != "#")
									$scope.navEnabled = true;
								else
									$scope.navEnabled = false;
							}
							
							

							var constants = CONSTANTS.getConstants();
							$scope.brandName = constants.appName;
							$scope.apRoot    = constants.appRoot;
							
							$scope.newMessages = poller.data;
							
							$scope.openMessage = function(obj)
							{
								$scope.message = obj.message;
								 $location.path('/all-messages');
								 $route.reload();
							}
							
							$scope.openProfile = function(user)
							{
								var userId = user.userId;
								 userService.getUsers("uid",userId).then(function (response) {
									    if(response.data.length>0)
									    	$scope.user = response.data[0];
									    
							    	    $location.path('/user-profile');
										 $route.reload();
							    	});
							}

						} ]);
