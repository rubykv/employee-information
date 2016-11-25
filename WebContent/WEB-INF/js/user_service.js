
angular.module('myApp').factory('CustomerService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8080/EmployeeInfoPortal/user/';

    var factory = {
    		retrieveAllEmployeeInfo: retrieveAllEmployeeInfo,
     
    };

    return factory;

    function retrieveAllEmployeeInfo() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Users');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

   

}]);
