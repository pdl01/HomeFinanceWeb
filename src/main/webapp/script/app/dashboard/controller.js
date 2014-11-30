angular.module('HFWApp.controllers', []).
controller('dashboardController', function ($scope,$http) {
    $scope.accountFormData = {};
    $scope.registryTransactions={}; 
    $scope.registryTransactionFormData={}; 
    $scope.selectedAccount = null;
    $scope.showAccountModal = false;
    $scope.showTransactionModal = false;
    $http.get("http://localhost:8080/HFW/services/api/v1/accounts/search/all")
    .success(function(response) {$scope.accounts = response;});    
   
    
    $scope.clickGoButton = function(x) {
        console.log(x.id);
        this.selectedAccount = x;
        $scope.registryTransactionFormData.primaryAccount=x.id;
        $scope.$emit('showRegisterTransactions', x);
        this.getTransactionsForAccount(x.id);
    };

    $scope.showNewAccount = function(x) {
        $scope.showAccountModal=true;
        //$("#accountDetailsForm").show();
    };

    $scope.clickNewAccountCancel = function(x) {
        $scope.showAccountModal=false;
        //$("#accountDetailsForm").hide();
    };
    
    $scope.showNewTransaction = function(x) {
        $("#transactionDetailsForm").show();
    };
    $scope.clickNewTransactionCancel=function(x) {
        $("#transactionDetailsForm").hide();
    }
    

    
    $scope.addAccount = function() {
	$http({
        method  : 'POST',
        url     : 'http://localhost:8080/HFW/services/api/v1/accounts/save',
        data    : JSON.stringify($scope.accountFormData),  // pass in data as strings
        headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
            })
        .success(function(data) {
            
            $scope.accounts.push(data);
            
            console.log(data);
      
        });
    };
    
    
    
    $scope.getTransactionsForAccount = function(accountId) {
        $http.get("http://localhost:8080/HFW/services/api/v1/register/get/all/"+accountId)
    .success(function(response) {$scope.registryTransactions = response;}); 
    };
    
    $scope.addRegistryTransaction = function() {
        
	$http({
        method  : 'POST',
        url     : 'http://localhost:8080/HFW/services/api/v1/register/save',
        data    : JSON.stringify($scope.registryTransactionFormData),  // pass in data as strings
        headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
            })
        .success(function(data) {
            
            $scope.registryTransactions.push(data);
            
            console.log(data);
      
        });
    };
    
});