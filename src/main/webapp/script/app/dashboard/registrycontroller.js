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


    
    $rootScope.$on('account-selected', function (event, data) {
        $scope.workingAccount = angular.copy(data);
        $scope.txnDateControl.year = $scope.serverDateArray[0];
        $scope.txnDateControl.month = $scope.serverDateArray[1];
        $scope.getTransactionsForMonth($scope.workingAccount.id);
    });
    
    $scope.$watch('registryTransactionFormCategorySplits[0].category', function (oldValue, newValue) {
        //console.log(oldValue, newValue);
        //$scope.calcBudgetTotals();
        if (newValue != undefined && newValue.length > 3) {
            $scope.categoryTypingIndex = 0;
            $scope.getCategories(newValue);
        } else {
            $scope.categoryTypingIndex = -1;
            $scope.retrievedCategories = [];

        }
    });
    $scope.$watch('registryTransactionFormCategorySplits[1].category', function (oldValue, newValue) {
        //console.log(oldValue, newValue);
        //$scope.calcBudgetTotals();
        if (newValue != undefined && newValue.length > 3) {
            $scope.categoryTypingIndex = 1;
            $scope.getCategories(newValue);
        } else {
            $scope.categoryTypingIndex = -1;
            $scope.retrievedCategories = [];

        }

    });
    $scope.$watch('registryTransactionFormCategorySplits[2].category', function (oldValue, newValue) {
        //console.log(oldValue, newValue);
        //$scope.calcBudgetTotals();
        if (newValue != undefined && newValue.length > 3) {
            $scope.categoryTypingIndex = 2;
            $scope.getCategories(newValue);
        } else {
            $scope.categoryTypingIndex = -1;
            $scope.retrievedCategories = [];

        }

    });
    $scope.$watch('registryTransactionFormCategorySplits[3].category', function (oldValue, newValue) {
        //console.log(oldValue, newValue);
        //$scope.calcBudgetTotals();
        if (newValue != undefined && newValue.length > 3) {
            $scope.categoryTypingIndex = 3;
            $scope.getCategories(newValue);
        } else {
            $scope.categoryTypingIndex = -1;
            $scope.retrievedCategories = [];

        }

    });

    $scope.$watch('registryTransactionFormCategorySplits[4].category', function (oldValue, newValue) {
        //console.log(oldValue, newValue);
        //$scope.calcBudgetTotals();
        if (newValue != undefined && newValue.length > 3) {
            $scope.categoryTypingIndex = 4;
            $scope.getCategories(newValue);
        } else {
            $scope.categoryTypingIndex = -1;
            $scope.retrievedCategories = [];

        }

    });    
    $scope.$watch('registryTransactionFormData.txnAmount', function (oldValue, newValue) {
        //console.log(oldValue, newValue);
        //$scope.calcBudgetTotals();
        console.log($scope.registryTransactionFormCategorySplits[0]);
        if ($scope.registryTransactionFormCategorySplits[0] == undefined) {
            var obj = new Object();
            obj.category = "";
            obj.txnAmount = newValue;
            $scope.registryTransactionFormCategorySplits.push(obj);
        } else if ($scope.registryTransactionFormCategorySplits[0].category == "") {
            $scope.registryTransactionFormCategorySplits[0].txnAmount = $scope.registryTransactionFormData.txnAmount;
        }
        /*
         if (registryTransactionFormCategorySplits[0].category == undefined || registryTransactionFormCategorySplits[0].category == "") {
         //$scope.$emit('txnAmountEdited', newValue);
         registryTransactionFormCategorySplits[0].txnAmount = registryTransactionFormData.txnAmount;
         }
         */

    })
    $scope.$on('txnAmountEdited', function (event, data) {
        //if the txn on the first category split is empty enter it.
        if ($scope.registryTransactionFormCategorySplits[0].txnAmount == "") {

        }
    });

    $scope.$on('transactionSaved', function (event, data) {
        //refresh all
        $scope.refreshAccounts();
        //refresh one account
        console.log(data);
        /*
         if ($scope.registryTransactionFormData.txnDate == undefined || $scope.registryTransactionFormData.txnDate == "") {
         $scope.registryTransactionFormData.txnDate = $scope.currentDate;
         }
         */

    });
    $scope.showNewTransaction = function (x) {
        $scope.registryTransactionFormData = {};
        $scope.registryTransactionFormData.txnDate = $scope.currentDate;
        $scope.registryTransactionFormData.statusTxt = 'x';
        $scope.registryTransactionFormCategorySplits = [];
        var obj = new Object();
        obj.category = "";
        obj.txnAmount = "";
        $scope.registryTransactionFormCategorySplits.push(obj);
        $scope.showTransactionModal = true;
        //$("#transactionDetailsForm").show();
    };

    $scope.addRegistryTransaction = function () {
        //get the splits and turn it into a better list
        var newCategories = [];
        $scope.registryTransactionFormData.primaryAccount = $scope.selectedAccount.id;
        for (var i = 0; i < 10; i++) {
            if ($scope.registryTransactionFormCategorySplits[i] != undefined) {
                var categorySplit = new Object();

                var categoryEntered = $scope.registryTransactionFormCategorySplits[i].category;
                var txnAmountEntered = $scope.registryTransactionFormCategorySplits[i].txnAmount;
                categorySplit.category = categoryEntered;
                categorySplit.txnAmount = txnAmountEntered;
                newCategories.push(categorySplit);
                //console.log(categoryEntered + ":"+txnAmountEntered);    
            }
        }


        $scope.registryTransactionFormData.categorySplits = newCategories;

        console.log($scope.registryTransactionFormData);
        if ($scope.registryTransactionFormData.id == undefined) {
            RegistryService.saveTransaction($scope.registryTransactionFormData).success(function (response) {
                $scope.registryTransactions.push(response);
                if ($scope.payingScheduledTxnId != "") {
                    ScheduledTransactionService.payTransaction($scope.payingScheduledTxnId).success(function (response) {
                        $scope.payingScheduledTxnId = "";
                    }).error(function (response) {
                        $scope.payingScheduledTxnId = "";
                    });
                }
                $scope.showTransactionModal = false;
                $scope.$emit('transactionSaved', $scope.selectedAccount.id);
                console.log(response);
            });
        } else {
            RegistryService.saveTransaction($scope.registryTransactionFormData).success(function (response) {
                $scope.showTransactionModal = false;
                $scope.$emit('transactionSaved', $scope.selectedAccount.id);
                console.log(response);
            });

        }


        /*
         $http({
         method  : 'POST',
         url     : '/HFW/services/api/v1/register/save',
         data    : JSON.stringify($scope.registryTransactionFormData),  // pass in data as strings
         headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
         })
         .success(function(data) {
         
         $scope.registryTransactions.push(data);
         
         console.log(data);
         
         });
         */
    };

    $scope.getTransactionsForAccount = function (accountId) {
        RegistryService.getRegistryBlockForAccount(accountId, $scope.txnIndex, $scope.numberTxnsToRetrieve).success(function (response) {
            for (idx in response) {
                var txn = response[idx];
                txn.txnDate = RegistryService.convertTextDateToJSDate(txn.txnDate);
                $scope.registryTransactions.push(txn);
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
    $scope.showTransactionForm = function (x) {
        console.log(x);
        $scope.hideTxnRetrievedCategories = true;
        $scope.retrievedCategories = [];
        $scope.registryTransactionFormData = x;
        $scope.registryTransactionFormCategorySplits = [];
        angular.forEach(x.categorySplits, function (value, key) {
            //this.push(key + ': ' + value);
            if (value.category != '') {
                $scope.registryTransactionFormCategorySplits.push(value);
            }

        });


        $scope.showTransactionModal = true;
    };

    $scope.clickNewTransactionCancel = function (x) {
        $scope.showTransactionModal = false;
        //$("#transactionDetailsForm").hide();
    };

});