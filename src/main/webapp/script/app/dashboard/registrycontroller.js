angular.module('HFWApp').controller('accountRegistryController', function ($rootScope,$scope, $interval, $http, AccountService, RegistryService, ReportService, CategoryLookupService, DateService, ScheduledTransactionService, TransactionStatusLookupService, NotificationService, NameLookupService) {
    $scope.workingAccount = {};
    $scope.registryTransactionFormCategorySplits = [];
    $scope.txnDateControl = {};
    $scope.txnDateControl.year = "";
    $scope.txnDateControl.month = "";
    //$scope.txnDateControl.yearmonth = "2016-08";
    $scope.txnDateControl.selectopened = false;
    
    $scope.registrymaxDate = new Date(2020, 5, 22);
    $scope.registryminDate = new Date(2000, 5, 22);
    $scope.registrydateOptions = {
        formatYear: 'yyyy',
        formatMonth: 'MM',
        startingDay: 1
    };

    
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
   
    $scope.$watch('txnDateControl.yearmonth', function (newValue, oldValue) {
        /*
        console.log("oldValue:"+oldValue);
        console.log("newValue:"+newValue);
        var xDate = new Date(newValue);
        $scope.txnDateControl.year =""+xDate.getFullYear();
        console.log($scope.txnDateControl.year);
        $scope.txnDateControl.month=xDate.getMonth()+1;
        if ($scope.txnDateControl.month < 10) {
            $scope.txnDateControl.month = "0"+$scope.txnDateControl.month;
        }
        console.log($scope.txnDateControl.month);
        */
       /*
        $scope.getTransactionsForMonth($scope.workingAccount.id);
        */
    });
   
    
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

    $rootScope.$on('scheduled-txn-entered', function (event, data) {
        //$scope.showTransactionModal=true;
        $rootScope.$broadcast('transaction-operation-requested',data);

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

    $scope.filterFromCalendar = function () {
        console.log($scope.txnDateControl);
    }
    $scope.openRegistryDateSelect = function() {
        $scope.txnDateControl.selectopened = true;
    };
    $scope.getTransactionsForMonth = function (accountId) {
        //var xDate = new Date(newValue);
        //$scope.txnDateControl.year =""+xDate.getFullYear();
        //console.log($scope.txnDateControl.year);
        //$scope.txnDateControl.month=xDate.getMonth()+1;
        //if ($scope.txnDateControl.month < 10) {
        //    $scope.txnDateControl.month = "0"+$scope.txnDateControl.month;
        //}

        var month =$scope.txnDateControl.yearmonth.getMonth()+1;
        if (month<10) {
            month = "0"+month;
        }
        var year =$scope.txnDateControl.yearmonth.getFullYear()+"";
        
        var dateToRetrieve = year + "-" + month;
        
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
        console.log($scope.serverDateArray);
        $scope.txnDateControl.year = $scope.serverDateArray[0];
        $scope.txnDateControl.month = $scope.serverDateArray[1];
        $scope.txnDateControl.yearmonth = $scope.serverDateArray[0]+"-"+$scope.serverDateArray[1];
        $scope.txnDateControl.yearmonth = new Date();
        console.log($scope.txnDateControl);
        $scope.getTransactionsForMonth($scope.workingAccount.id);
    };
    $scope.showTransactionForm = function(x) {
        $rootScope.$broadcast('transaction-operation-requested',x);
    };
    
    $scope.filterToNextMonth = function(x) {
        var nextYear = "";
        var nextMonth="";
        if ($scope.txnDateControl.yearmonth.getMonth() == 11){
            nextMonth = 0;
            nextYear = $scope.txnDateControl.yearmonth.getFullYear()+1;
        } else {
            nextMonth = $scope.txnDateControl.yearmonth.getMonth()+1;
            nextYear = $scope.txnDateControl.yearmonth.getFullYear();
        }        
        $scope.txnDateControl.yearmonth.setMonth(nextMonth);
        $scope.txnDateControl.yearmonth.setYear(nextYear);
        $scope.getTransactionsForMonth($scope.workingAccount.id);
    };

    $scope.filterToPrevMonth = function(x) {        
        var nextYear = "";
        var nextMonth="";
        if ($scope.txnDateControl.yearmonth.getMonth() == 0){
            nextMonth = 11;
            nextYear = $scope.txnDateControl.yearmonth.getFullYear()-1;
        } else {
            nextMonth = $scope.txnDateControl.yearmonth.getMonth()-1;
            nextYear = $scope.txnDateControl.yearmonth.getFullYear();
        }        
        $scope.txnDateControl.yearmonth.setMonth(nextMonth);
        $scope.txnDateControl.yearmonth.setYear(nextYear);
        $scope.getTransactionsForMonth($scope.workingAccount.id);
        $scope.$apply();
    };

    
});