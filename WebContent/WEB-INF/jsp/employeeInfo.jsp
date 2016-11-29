<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
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
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script type="text/javascript" src="<c:url value='/js/app.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/user_controller.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/user_service.js' />"></script>
            
</head>
<body ng-app="myApp" class="ng-cloak">
	<div class="generic-container" ng-controller="myCtrl">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">List of Employee </span>
			</div>
			<div class="tablecontainer" ng-hide="editorEnabled">
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
									class="btn btn-danger custom-width">Delete</button>
							</td>
						</tr>
					</tbody>
				</table>
				 <button type="button" ng-click="add=true"
									class="btn btn-success custom-width">Add New Employee</button>
			</div>
			<div class="tablecontainer" ng-show="editorEnabled">
			<input type="hidden" ng-model="editableId" />
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
							<td><span ng-if="u._id == editedUser.editableId"><input ng-model="editedUser.editableTitle"></span>
								<span ng-if="u._id != editedUser.editableId" ng-bind="u.name"></span>
							</td>
							<td><span ng-if="u._id == editedUser.editableId"><input ng-model="editedUser.editableCity" ></span>
								<span ng-if="u._id != editedUser.editableId" ng-bind="u.city"></span>
							</td>
							<td><span ng-if="u._id == editedUser.editableId"><input ng-model="editedUser.editablephoneNum"></span>
								<span ng-if="u._id != editedUser.editableId" ng-bind="u.phoneNumber"></span>
							</td>
							<td><span ng-if="u._id == editedUser.editableId">
							
								 <button type="button" ng-click="save(editedUser.editableTitle,editedUser.editableCity,editedUser.editablephoneNum)"
									class="btn btn-success custom-width">Save</button>
								<button type="button" ng-click="cancelEdit()"
									class="btn btn-danger custom-width">Cancel</button>
							</span><span ng-if="u._id != editedUser.editableId">
								<button type="button" ng-click="edit(u._id)"
									class="btn btn-success custom-width">Edit</button>
								<button type="button" ng-click="deleteUser(u._id)"
									class="btn btn-danger custom-width">Delete</button>
							</span></td>
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
                              <button type="button" ng-click="cancel()" class="btn btn-danger custom-width">Cancel</button>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
	
	
</body>
</html>