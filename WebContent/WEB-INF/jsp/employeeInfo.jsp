<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>

<script>
var app = angular.module('myApp',[]);

app.controller('myCtrl', function($scope,CustomerService) {
    var self = this;
    $scope.user={_id:null,name:'',city:'',phoneNumber:''};
    $scope.users;
    $scope.deleteUser = deleteUser;
    $scope.submit = submit;
    $scope.counter = 0;

    fetchAllUsers();
    
    function fetchAllUsers(){
    	CustomerService.fetchAllUsers()
            .then(
            function(d) {
            	$scope.users = d;
            },
            function(errResponse){
                console.error('Error while fetching Users');
            }
        );
    }
    function deleteUser(id){
    	CustomerService.deleteUser(id)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while deleting User');
            }
        );
    }
    function submit() {
            console.log('Saving New User', $scope.user);
            createUser($scope.user);
            reset();
    }
    function createUser(user){
    	CustomerService.createUser(user)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while creating User');
            }
        );
    }
    function reset(){
        $scope.user={_id:null,name:'',city:'',phoneNumber:''};
        $scope.myForm.$setPristine(); 
        $scope.myForm.uname.$setPristine(); 
        $scope.myForm.city.$setPristine(); 
        $scope.myForm.phoneNumber.$setPristine(); 

    }
 });
    
app.factory('CustomerService',  function($http, $q){
    var WEBSERVICE_URI = 'http://localhost:8080/EmployeeInfoPortal/user/';
    
    var factory = {
            fetchAllUsers: fetchAllUsers,
            deleteUser:deleteUser,
            createUser:createUser
    };
    return factory;
    
	function fetchAllUsers() {
        var deferred = $q.defer();
        $http.get(WEBSERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                deferred.reject(errResponse);
            }
        );
    return deferred.promise;
	}
	
	function deleteUser(id){
	        var deferred = $q.defer();
	        $http.delete(WEBSERVICE_URI+id)
	            .then(
	            function (response) {
	                deferred.resolve(response.data);
	            },
	            function(errResponse){
	                deferred.reject(errResponse);
	            }
	        );
	return deferred.promise;
	}
	
	function createUser(user) {
	        var deferred = $q.defer();
	        $http.post(WEBSERVICE_URI, user)
	            .then(
	            function (response) {
	                deferred.resolve(response.data);
	            },
	            function(errResponse){
	                console.error('Error while creating User');
	                deferred.reject(errResponse);
	            }
	        );
	        return deferred.promise;
	}
});
</script>

<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link href="<c:url value='/WEB-INF/css/app.css' />" rel="stylesheet"></link>
<link href="<c:url value='/WEB-INF/css/bootstrap.css' />" rel="stylesheet"></link>
<style>
.row {
	margin-right: -15px;
	margin-left: 10px
}
</style>
</head>

<body ng-app="myApp">
	<div class="generic-container" ng-controller="myCtrl">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">List of Employee </span>
			</div>
			<div class="tablecontainer">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>ID.</th>
							<th>Name</th>
							<th>City</th>
							<th>Email</th>
							<th width="20%"></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="u in users">
							<td><span>{{$index+1}}</span></td>
							<td><span ng-bind="u.name"></span></td>
							<td><span ng-bind="u.city"></span></td>
							<td><span ng-bind="u.phoneNumber"></span></td>
							<td>
								<button type="button" ng-click="edit(u._id)"
									class="btn btn-success custom-width">Edit</button>
								<button type="button" ng-click="deleteUser(u._id)"
									class="btn btn-danger custom-width">Remove</button>
							</td>
						</tr>
					</tbody>
				</table>
				 <button type="button" ng-click="add=true"
									class="btn btn-success custom-width">Add New Employee</button>
			</div>
			</div>
			<div class="panel panel-default" ng-show="add">
              <div class="panel-heading"><span class="lead">Add New Employee </span></div>
              <div class="formcontainer">
                  <form ng-submit="submit()" name="myForm" class="form-horizontal">
                      <input type="hidden" ng-model="user.id" />
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="user.name" name="uname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="4"/>
                                  <div class="has-error" ng-show="myForm.uname.$dirty" style="color:red">
                                      <span ng-show="myForm.uname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.uname.$error.minlength">Minimum 4 letters required</span>
                                      <span ng-show="myForm.uname.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                        
                      
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">City</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="user.city" name="city" class="form-control input-sm" placeholder="Enter your City" required ng-minlength="2"/>
                                  <div class="has-error" ng-show="myForm.city.$dirty" style="color:red">
                                      <span ng-show="myForm.city.$error.required" >This is a required field.</span>
                                      <span ng-show="myForm.city.$error.minlength">Minimum 2 letters required</span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Email</label>
                              <div class="col-md-7">
                                  <input type="email" ng-model="user.phoneNumber" name="email" class="email form-control input-sm" placeholder="Enter your Email" required/>
                                  <div class="has-error" ng-show="myForm.email.$dirty" style="color:red">
                                      <span ng-show="myForm.email.$error.required">This is a required field.</span>
                                      <span ng-show="myForm.email.$invalid">This field is invalid. </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="Add" class="btn btn-success custom-width" ng-disabled="myForm.$invalid">
                              <button type="button" ng-click="add=false" class="btn btn-danger custom-width">Cancel</button>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
	</div>
</body>
</html>