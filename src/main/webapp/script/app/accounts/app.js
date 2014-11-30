var hfwApp = angular.module('HFWApp', []);




hfwApp.controller('accountController', function ($scope,AccountService) {
    
   $scope.accounts = {};
   AccountService.getAccounts().success(function(response) {
       $scope.accounts=response;
   });
        
  $scope.clickGoButton = function(x) {
        console.log(x.id);
        this.selectedAccount = x;
        window.location.href="/HFW/registry.html#/!?accountId="+x.id;
    };
    
    

    
    
  
    
 
});




