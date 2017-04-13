'use strict';

angular
		.module("loginModule")

		.directive("passwordVerify", function() {
			return {
				require : "ngModel",
				scope : {
					otherModelValue : "=passwordVerify"
				},
				link : function(scope, element, attributes, ngModel) {
					ngModel.$validators.compareTo = function(modelValue) {
						if (scope.otherModelValue !== modelValue) {
							ngModel.$setValidity("passwordVerify", false);
							return undefined;
						} else {
							ngModel.$setValidity("passwordVerify", true);
							return modelValue;
						}
					};

					scope.$watch("otherModelValue", function() {
						ngModel.$validate();
					});
				},
			};
		})

		.controller(
				"loginController",
				[
						'$scope',
						'$route',
						'$rootScope',
						'$location',
						'loginService',
						'$http',
						'constantService',
						'userService',
						function($scope, $route, $rootScope, $location,
								loginService, $http, constantService,
								userService) {
							var constants = constantService.getConstants();

							$scope.error = "";

							loginService.ClearCredentials();

							$scope.login = function() {

								loginService
										.login(
												$scope.username,
												$scope.password,
												function(response) {

													$rootScope.loginMsg = "";

													if (response.success == true) {

														loginService
																.SetCredentials(
																		$scope.username,
																		response.message);
														$location.path('/');
													} else {
														$scope.error = "Username or password is incorrect";
														// $scope.dataLoading =
														// false;
													}

												});
							}

							$scope.submitButtonValidator = function(
									isFormInValid, isPasswordNotMatched) {
								var istoEnableButton = true;
								if (isFormInValid === true
										&& isPasswordNotMatched === true)
									istoEnableButton = true;
								else
									istoEnableButton = false;

								return istoEnableButton;
							}

							$scope.registerUser = function(obj) {
								$http
										.post(
												constants.apiUrl
														+ 'meeting/register',
												{
													"uName" : obj.uName,
													"uDep" : obj.uDep,
													"uBuild" : obj.uBuild,
													"uFloor" : obj.uFloor,
													"role" : {
														"roleId" : constants.normalUserRoleId
													},
													"mobile" : obj.mobile,
													"email" : obj.email,
													"details" : obj.detail,
													"common" : {
														"status" : 'ACTIVE',
														"createdById" : '-1'
													},

													"login" : {
														"userName" : obj.login.userName,
														"password" : obj.login.password
													}

												})
										.success(
												function(data) {
													console.log(data);

													if (data.success == true) {
														$location
																.path('/login');
														$route.reload();
														$rootScope.loginMsg = "Registration Successfull.";
													} else {
														$scope.error = "Registration failed.";
													}
												}).err
							}

							$scope.resetPassword = function() {
								$http.post(constants.apiUrl + 'users/reset', {
									"searchCode" : "searchByEmail",
									"condition1" : $scope.email
								}

								).success(function(data) {
									console.log(data);

									if (data.success == true) {
										$location.path('/login');
										$route.reload();

										$rootScope.loginMsg = data.message;
									} else {
										$scope.error = data.message;
									}
								}).error(function(error) {

									showErrorMsg(error);
								});
							}

							$scope.register = function() {
								$location.path('/register');
								$route.reload();
							}
							
							$scope.forGotPass = function() {
								$location.path('/forgot-password');
								$route.reload();
							}

							$scope.goTologin = function() {
								$location.path('/login');
								$route.reload();
							}

							$scope.logout = function() {
								$location.path('/');
								$route.reload();
							}


						} ]);