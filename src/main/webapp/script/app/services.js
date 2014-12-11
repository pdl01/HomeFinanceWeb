hfwApp.factory("AccountService", function($http) {
  //var users = ["Peter", "Daniel", "Nina"]
  var AccountService = {};
  
  AccountService.getAccounts = function() {
      return $http({
        method: 'GET', 
        url: '/HFW/services/api/v1/accounts/search/all'
      });
  };
  AccountService.getAccount = function(id) {
      return $http({
        method: 'GET', 
        url: '/HFW/services/api/v1/accounts/getbyId/'+id
      });
  };
  
  AccountService.saveAccount = function(account) {
      return $http({
        method  : 'POST',
        url     : '/HFW/services/api/v1/accounts/save',
        data    : JSON.stringify(account),  // pass in data as strings
        headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
            })
  };
  
  return AccountService;
});

hfwApp.factory("RegistryService", function($http) {
  //var users = ["Peter", "Daniel", "Nina"]
  var RegistryService = {};
  
  RegistryService.getRegistryForAccount = function(id) {
      return $http({
        method: 'GET', 
        url: '/HFW/services/api/v1/register/get/all/'+id
      });
  };
 RegistryService.saveTransaction = function(transaction) {
     delete transaction.$$hashKey;
     return $http({
        method  : 'POST',
        url     : '/HFW/services/api/v1/register/save',
        data    : JSON.stringify(transaction),  // pass in data as strings
        headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
            });
  };
  return RegistryService;
});