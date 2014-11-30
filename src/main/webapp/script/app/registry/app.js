var hfwApp = angular.module('HFWApp', []);




hfwApp.controller('registryController', function ($scope,$location,RegistryService) {
   
    var accountId = $location.search().accountId;
   console.log(accountId);
   $scope.registryTransactions = {};
   RegistryService.getRegistryForAccount(accountId).success(function(response) {
       $scope.registryTransactions = response;
   });
        
  $scope.clickGoButton = function(x) {
        console.log(x.id);
        this.selectedAccount = x;
        window.location.href="/HFW/registery.html#/!?accountId="+x.id;
    };
    
    

    
    
  
    
 
});




