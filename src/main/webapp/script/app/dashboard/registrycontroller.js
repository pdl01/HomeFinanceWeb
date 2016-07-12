angular.module('HFWApp').controller('accountRegistryController', function ($rootScope,$scope, $interval, $http, AccountService, RegistryService, ReportService, CategoryLookupService, DateService, ScheduledTransactionService, TransactionStatusLookupService, NotificationService, NameLookupService) {
    $scope.workingAccount = {};
    $scope.registryTransactionFormCategorySplits = [];
    $scope.txnDateControl = {};
    $scope.txnDateControl.year = "";
    $scope.txnDateControl.month = "";
    
    $scope.txnSortType = 'txnDate'; // set the default sort type
    $scope.txnSortReverse = false;  // set the default sort order

    $scope.registryTransactions = [];
    $scope.registryTransactionFormData = {};
    $scope.hideTxnRetrievedCategories = true;
    $scope.retrievedCategories = {};
    $scope.categoryTypingIndex = -1;

    $scope.txnIndex = 0;  //this is the start index of txns to retrieve 
    $scope.numberTxnsToRetrieve = 25;

    $scope.showTransactionModal = false;
    

    $rootScope.$on('account-selected', function (event, data) {
        $scope.workingAccount = angular.copy(data);
        $scope.filterToCurrentDate();
        $scope.registryTransactions = [];
        /*
        $scope.scheduledDateControl.year = $scope.serverDateArray[0];
        $scope.scheduledDateControl.month = $scope.serverDateArray[1];
        $scope.getScheduledTransactionsForMonth($scope.workingAccount.id);
        */
    });

    $rootScope.$on('transaction-operation-completed', function (event, data) {
        $scope.getTransactionsForMonth($scope.workingAccount.id);
    });


  $scope.arrFromTransactions = Object.keys($scope.registryTransactions).map(function(key) {
    return $scope.registryTransactions[key];
  });

    $scope.getTransactionsForAccount = function (accountId) {
        RegistryService.getRegistryBlockForAccount(accountId, $scope.txnIndex, $scope.numberTxnsToRetrieve).success(function (response) {
            for (idx in response) {
                var txn = response[idx];
                txn.txnDate = RegistryService.convertTextDateToJSDate(txn.txnDate);
                $scope.registryTransactions.push(txn);
                //$scope.registryTransactions[txn.id]=txn;
                //console.log(txn.id);
            }
            //$scope.registryTransactions. = response;
            $scope.txnIndex = $scope.txnIndex + $scope.numberTxnsToRetrieve;
        });
        /*
         $http.get("/HFW/services/api/v1/register/get/all/"+accountId)
         .success(function(response) {$scope.registryTransactions = response;}); 
         */
    };

    $scope.getTransactionsForMonth = function (accountId) {
        var dateToRetrieve = $scope.txnDateControl.year + "-" + $scope.txnDateControl.month;
        console.log(accountId);
        $scope.registryTransactions = [];
        RegistryService.getRegistryForAccountForMonth(accountId, dateToRetrieve).success(function (response) {
            for (idx in response) {
                var txn = response[idx];
                txn.txnDate = RegistryService.convertTextDateToJSDate(txn.txnDate);
                $scope.registryTransactions.push(txn);
                //$scope.registryTransactions[txn.id]=txn;
                //console.log(txn.id);
            }
            //$scope.registryTransactions. = response;
            $scope.txnIndex = $scope.txnIndex + $scope.numberTxnsToRetrieve;
        });
        /*
         $http.get("/HFW/services/api/v1/register/get/all/"+accountId)
         .success(function(response) {$scope.registryTransactions = response;}); 
         */
    };

    $scope.filterToCurrentDate = function () {
        $scope.txnDateControl.year = $scope.serverDateArray[0];
        $scope.txnDateControl.month = $scope.serverDateArray[1];
        $scope.getTransactionsForMonth($scope.workingAccount.id);
    };
    $scope.showTransactionForm = function(x) {
        $rootScope.$broadcast('transaction-operation-requested',x);
    };
});