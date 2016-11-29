app.controller('myCtrl', function($scope,CustomerService) {
    var self = this;
    $scope.user={_id:null,name:'',city:'',phoneNumber:''};
    $scope.editedUser={_id:null,name:'',city:'',phoneNumber:''}
    $scope.users;
    $scope.deleteUser = deleteUser;
    $scope.submit = submit;
    $scope.cancel = cancel;
    $scope.counter = 0;
    $scope.editorEnabled = false;
    $scope.edit = edit;
    $scope.cancelEdit = cancelEdit;
    $scope.save=save;

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
    function cancel() {
        console.log('Cancel User');
        $scope.add=false;
        reset();
	}
    function edit(id) {
        $scope.editorEnabled = true;

    	 for(var i = 0; i < $scope.users.length; i++){
             if($scope.users[i]._id == id) {
            	 var editUser = angular.copy($scope.users[i]);
            	 $scope.editedUser.editableId = editUser._id;
            	 $scope.editedUser.editableTitle= editUser.name;
            	 $scope.editedUser.editableCity = editUser.city;
            	 $scope.editedUser.editablephoneNum = editUser.phoneNumber;
                 break;
             }
         }  
    }
    function cancelEdit() {
        $scope.editorEnabled=false;
	}
    function save(title,city,email){
        $scope.editorEnabled=false;
		$scope.editedUser={_id:$scope.editedUser.editableId,name:title,city:city,phoneNumber:email}

    	CustomerService.updateUser($scope.editedUser)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while updating User');
            }
        );
    }
 });