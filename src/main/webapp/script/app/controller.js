angular.module('HFWApp.controllers', []).
controller('accountController', function ($scope,$http) {
    $scope.formData = {};
    
    $http.get("http://localhost:8080/HFW/services/api/v1/accounts/search/all")
    .success(function(response) {$scope.accounts = response;});    
   
    
    $scope.clickGoButton = function(x) {
        console.log(x);
        $scope.$emit('showRegisterTransactions', x);
    };
   
    $scope.addAccount = function() {
	$http({
        method  : 'POST',
        url     : 'http://localhost:8080/HFW/services/api/v1/accounts/save',
        data    : JSON.stringify($scope.formData),  // pass in data as strings
        headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
            })
        .success(function(data) {
            
            $scope.accounts.push(data);
            
            console.log(data);
      
        });
    };
    
});