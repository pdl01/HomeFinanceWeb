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
      delete account.$$hashKey;
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
  RegistryService.validateTransaction = function(transaction) {
     delete transaction.$$hashKey;
     return $http({
        method  : 'POST',
        url     : '/HFW/services/api/v1/register/validate',
        data    : JSON.stringify(transaction),  // pass in data as strings
        headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
            });
  };
  return RegistryService;
});

hfwApp.factory("ReportService", function($http) {
  //var users = ["Peter", "Daniel", "Nina"]
  var ReportService = {};
  
  ReportService.getReport = function(reportType) {
      return $http({
        method: 'GET', 
        url: '/HFW/services/api/v1/reports/'+reportType
      });
  };
  ReportService.getReportForPeriodForAccount = function(accountId,reportType,reportPeriod) {
      return $http({
        method: 'GET', 
        url: '/HFW/services/api/v1/reports/'+accountId+'/'+reportType +"/"+reportPeriod
      });
  };
  
  ReportService.getReportForCustomPeriod = function(reportType,startDate,endDate) {
      return $http({
        method: 'GET', 
        url: '/HFW/services/api/v1/reports/'+reportType+"/"+startDate+"/"+endDate
      });
  };

  
  return ReportService;
});
