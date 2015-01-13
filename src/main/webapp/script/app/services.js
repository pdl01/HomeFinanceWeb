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

hfwApp.factory("BudgetService", function($http) {
  //var users = ["Peter", "Daniel", "Nina"]
  var BudgetService = {};
  
  BudgetService.getBudgets = function() {
      return $http({
        method: 'GET', 
        url: '/HFW/services/api/v1/budgets/search/all'
      });
  };
 BudgetService.saveBudget = function(budget) {
     delete budget.$$hashKey;
     angular.forEach(budget.incomeItems, function(value, key) {
           console.log(value); 
            delete value.$$hashKey;  
         
           //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        });
     angular.forEach(budget.expenseItems, function(value, key) {
           console.log(value); 
            delete value.$$hashKey;  
         
           //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        });
        angular.forEach(budget.transferItems, function(value, key) {
           console.log(value); 
            delete value.$$hashKey;  
         
           //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        });
     return $http({
        method  : 'POST',
        url     : '/HFW/services/api/v1/budgets/save',
        data    : JSON.stringify(budget),  // pass in data as strings
        headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
            });
  };
  BudgetService.validateTransaction = function(transaction) {
     delete transaction.$$hashKey;
     return $http({
        method  : 'POST',
        url     : '--/HFW/services/api/v1/register/validate',
        data    : JSON.stringify(transaction),  // pass in data as strings
        headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
            });
  };
  return BudgetService;
});
