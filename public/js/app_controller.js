var my_proj_app = angular.module('myProjApp',['ngRoute']);
var base_url = ''



	
function url(route) {
	return base_url+route;
}

my_proj_app.config(function($routeProvider){
	$routeProvider
	.when('/', {
		templateUrl: "assets/start_page.html"
	})
	.when('/backlog', {
		templateUrl: "assets/backlog.html",
		controller: "TasksController"
	})
	.when('/tasks/new', {
		templateUrl: "assets/new_task.html",
		controller: 'NewTaskFormController'		
	})
	.when('/sprints/new',{
		templateUrl: "assets/new_sprint.html",
		controller: 'NewSprintFormController'
	})
	.when('/sprints/:sprint_id',{
		templateUrl: "assets/sprint_details.html",
		controller: 'SprintController'
	})
	.when('/logout', {
		templateUrl: "assets/logout.html",
		controller: 'LogoutController'
	});
});

my_proj_app.controller('LogoutController',['$rootScope','$location',function($rootScope, $location){
	console.log("enterd logout");
	console.log("sprints in logout 1 == "+$rootScope.sprints);
	$rootScope.sprints = null;
	console.log("sprints in logout 2 == "+$rootScope.sprints);
	$('#result').html("");
	window.location.href = "/logoutPage"
}]);


my_proj_app.controller('SprintController',['$scope','$routeParams', '$http',function($scope,$routeParams, $http){

	$http.get(url('/sprints/'+$routeParams.sprint_id))
	.success(function(data, status, headers, config){
		$scope.header = data.name;
		$scope.description = data.description;
		$scope.startDate = data.startDate;
		$scope.endDate = data.endDate;
	});
	
}]);

my_proj_app.controller('NewSprintFormController',['$scope', '$http', '$location',function($scope, $http, $location){
	$scope.submitForm = function() {
		var dataObj = {
			name : $scope.name,
			description : $scope.description,
			startDate: $scope.startDate,
			endDate: $scope.endDate
		}
		console.log("test in function "+ $scope.name);
		$http.post(url('/sprints'), dataObj)
		.success(function(data, status, headers, config) {
			alert(data.message);
			$location.path("/sprints/"+data.sprintId);
		});
	}
}]);

my_proj_app.controller('NewTaskFormController',['$scope', '$http', '$location',function($scope, $http, $location){
	$scope.submitForm = function() {
		var dataObj = {
			name : $scope.name,
			description : $scope.description,
			priority: $scope.priority
		}
		console.log("test in function "+ $scope.name);
		$http.post(url('/tasks'), dataObj)
		.success(function(data, status, headers, config) {
			alert(data.message);
			$location.path("/backlog");
		});
	}
}]);

my_proj_app.controller('TasksController',['$scope', '$rootScope','$http',function($scope, $rootScope, $http){

	$http.get(url("/backlog"))
	.success(function(data, status, headers, config) {
		$scope.tasks = data;
	})
	.error(function(data, status, headers, config, statusText) {
//		console.log("data == "+data);
//		console.log("status == "+ status);
		$rootScope.error = "Failed with code "+status;
	});
}]);

my_proj_app.controller('SprintsController',['$rootScope', '$http',function($rootScope, $http){

	$http.get(url("/sprints"))
	.success(function(data, status, headers, config){
		$rootScope.sprints = data;
	});
}]);


my_proj_app.controller('TaskController',['$scope', '$http',function($scope, $http){
	$scope.editTask = function() {
		console.log("test in function "+ JSON.stringify($scope.task));
	}
}]);