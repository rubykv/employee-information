
angular.module('myApp').controller('UserController', ['$scope', 'CustomerService', function($scope, CustomerService) {
    var self = this;
    self.user={id:null,name:'',city:'',phoneNumber:''};
    self.users=[];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;


    retrieveAllEmployeeInfo();

    function retrieveAllEmployeeInfo(){
        CustomerService.retrieveAllEmployeeInfo()
            .then(
            function(d) {
                self.users = d;
            },
            function(errResponse){
                console.error('Error while fetching Users');
            }
        );
    }

    

}]);
