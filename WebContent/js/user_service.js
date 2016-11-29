app.factory('CustomerService',  function($http, $q){
    var WEBSERVICE_URI = 'http://localhost:8080/EmployeeInfoPortal/user/';
    
    var factory = {
            fetchAllUsers: fetchAllUsers,
            deleteUser:deleteUser,
            createUser:createUser,
            updateUser:updateUser
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
	
	function updateUser(user) {
        var deferred = $q.defer();
        $http.put(WEBSERVICE_URI, user)
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