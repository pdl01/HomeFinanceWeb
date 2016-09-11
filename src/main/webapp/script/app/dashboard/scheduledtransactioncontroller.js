angular.module('HFWApp').controller('accountScheduleController', function ($rootScope,$scope, $interval, $http, AccountService, RegistryService, ReportService, CategoryLookupService, DateService, ScheduledTransactionService, TransactionStatusLookupService, NotificationService, NameLookupService) {
    $scope.workingAccount = {};
    $scope.scheduledtransactions = [];
    $scope.scheduledTransactionFormData = {};
    $scope.showScheduledTransactionModal = false;
    $scope.scheduledTransactionFormCategorySplits = [];
    $scope.scheduledDateControl = {};
    $scope.scheduledDateControl.year = "";
    $scope.scheduledDateControl.month = "";
    $scope.scheduledDateControl.yearmonth = "";
    $scope.scheduledDateControl.selectopened = false;

    $scope.payingScheduledTxnId = "";
    $scope.scheduledmaxDate = new Date(2020, 5, 22);
    $scope.scheduledminDate = new Date(2000, 5, 22);
    $scope.scheduleddateOptions = {
        formatYear: 'yyyy',
        formatMonth: 'MM',
        startingDay: 1
    };
    
    $rootScope.$on('account-selected', function (event, data) {
        $scope.workingAccount = angular.copy(data);
        $scope.filterScheduledToCurrentDate();
        /*
        $scope.scheduledDateControl.year = $scope.serverDateArray[0];
        $scope.scheduledDateControl.month = $scope.serverDateArray[1];
        $scope.getScheduledTransactionsForMonth($scope.workingAccount.id);
        */
    });
    
    $scope.$watch('scheduledDateControl.yearmonth', function (newValue, oldValue) {
        console.log(oldValue);
        console.log(newValue);
        var xDate = new Date(newValue);
        $scope.scheduledDateControl.year =""+xDate.getFullYear();
        console.log($scope.scheduledDateControl.year);
        $scope.scheduledDateControl.month=xDate.getMonth()+1;
        if ($scope.scheduledDateControl.month < 10) {
            $scope.scheduledDateControl.month = "0"+$scope.scheduledDateControl.month;
        }
        console.log($scope.scheduledDateControl.month);
        //$scope.getTransactionsForMonth($scope.workingAccount.id);
        $scope.getScheduledTransactionsForMonth($scope.workingAccount.id);
        
    });
    
    $scope.$watch('scheduledTransactionFormCategorySplits[0].category', function (oldValue, newValue) {
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
    $scope.$watch('scheduledTransactionFormCategorySplits[1].category', function (oldValue, newValue) {
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
    $scope.$watch('scheduledTransactionFormCategorySplits[2].category', function (oldValue, newValue) {
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
    $scope.$watch('scheduledTransactionFormCategorySplits[3].category', function (oldValue, newValue) {
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
    $scope.$watch('scheduledTransactionFormCategorySplits[4].category', function (oldValue, newValue) {
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

    $scope.editScheduledTxn = function (scheduledTxn) {
        //populate the scheduled form based on the passed in scheduledTxn
        ScheduledTransactionService.getTransaction(scheduledTxn.id).success(function (response) {
            //populate the scheduled form based on the result
            $scope.scheduledTransactionFormData = {};
            $scope.scheduledTransactionFormData.id = response.id;
            $scope.scheduledTransactionFormData.txnAmount = response.txnAmount;
            $scope.scheduledTransactionFormData.primaryAccount = response.primaryAccount;
            $scope.scheduledTransactionFormData.payee = response.payee;
            $scope.scheduledTransactionFormData.originalTransactionId = response.originalTransactionId;
            $scope.scheduledTransactionFormData.frequency = response.frequency;
            
            $scope.scheduledTransactionFormData.beginDate = ScheduledTransactionService.convertTextDateToJSDate(response.beginDate);
            $scope.scheduledTransactionFormData.endDate = ScheduledTransactionService.convertTextDateToJSDate(response.endDate);
            $scope.scheduledTransactionFormData.credit = response.credit;
            $scope.scheduledTransactionFormData.numberOfOccurrences = response.numberOfOccurrences;
            $scope.scheduledTransactionFormCategorySplits = [];
            angular.forEach(response.categorySplits, function (value, key) {
                //this.push(key + ': ' + value);
                if (value.category != '') {
                    $scope.scheduledTransactionFormCategorySplits.push(value);
                }

            });

            $scope.scheduledTransactionFormData.original = response.original;
        });
        $scope.hideTxnRetrievedCategories = true;
        $scope.retrievedCategories = [];
        $scope.showScheduledTransactionModal = true;

    };

    $scope.showScheduledTransactionForm = function (x) {
        $scope.hideTxnRetrievedCategories = true;
        $scope.retrievedCategories = [];
        $scope.showScheduledTransactionModal = true;
    };

    $scope.showNewScheduledTransactionForm = function (x) {
        $scope.hideTxnRetrievedCategories = true;
        $scope.retrievedCategories = [];
        $scope.scheduledTransactionFormData = {};
        $scope.scheduledTransactionFormCategorySplits = [];

        $scope.showScheduledTransactionModal = true;
    };

    $scope.clickScheduledTransactionCancel = function () {
        $scope.showScheduledTransactionModal = false;
    };
    
    $scope.skipScheduledTxn = function (scheduledTxn) {
        //call the servie to skip
        ScheduledTransactionService.skipTransaction(scheduledTxn.id).success(function (response) {
        $scope.getScheduledTransactionsForMonth($scope.workingAccount.id);
            //update the txn to denote skipped status
        });
    };
    $scope.editOriginalScheduledTxn = function (scheduledTxn) {
        //get the txn based on the original id

        ScheduledTransactionService.getTransaction(scheduledTxn.originalTransactionId).success(function (response) {
            //populate the scheduled form based on the result
            $scope.scheduledTransactionFormData = {};
            $scope.scheduledTransactionFormData.id = response.id;
            $scope.scheduledTransactionFormData.txnAmount = response.txnAmount;
            $scope.scheduledTransactionFormData.primaryAccount = response.primaryAccount;
            $scope.scheduledTransactionFormData.payee = response.payee;
            $scope.scheduledTransactionFormData.originalTransactionId = response.originalTransactionId;
            $scope.scheduledTransactionFormData.frequency = response.frequency;
            //$scope.scheduledTransactionFormData.beginDate = response.beginDate;
            $scope.scheduledTransactionFormData.beginDate = ScheduledTransactionService.convertTextDateToJSDate(response.beginDate);            
            $scope.scheduledTransactionFormData.endDate = response.endDate;
            $scope.scheduledTransactionFormData.credit = response.credit;

            $scope.scheduledTransactionFormData.numberOfOccurrences = response.numberOfOccurrences;
            $scope.scheduledTransactionFormData.original = response.original;
            $scope.scheduledTransactionFormCategorySplits = [];
            angular.forEach(response.categorySplits, function (value, key) {
                //this.push(key + ': ' + value);
                if (value.category != '') {
                    $scope.scheduledTransactionFormCategorySplits.push(value);
                }
            });
        });
        $scope.hideTxnRetrievedCategories = true;
        $scope.retrievedCategories = [];
        $scope.showScheduledTransactionModal = true;

    };
    $scope.getUpcomingSchedule = function (account) {
        $scope.scheduledtransactions = [];
        ScheduledTransactionService.getUpcomingScheduledTransactionsForAccount(account.id).success(function (response) {
            
            for (idx in response) {
                $scope.scheduledtransactions.push(response[idx]);
                //console.log(txn.id);
            }
            //$scope.registryTransactions. = response;
            //$scope.txnIndex = $scope.txnIndex + $scope.numberTxnsToRetrieve;
        });

    };
    $scope.openScheduledDateSelect = function() {
        $scope.scheduledDateControl.selectopened = true;
    };
    $scope.getScheduledTransactionsForMonth = function (accountId) {
        var dateToRetrieve = $scope.scheduledDateControl.year + "-" + $scope.scheduledDateControl.month;
        $scope.scheduledtransactions = [];
        ScheduledTransactionService.getUpcomingScheduledTransactionsForAccountForDate(accountId, dateToRetrieve).success(function (response) {
            for (idx in response) {
                $scope.scheduledtransactions.push(response[idx]);
                //console.log(txn.id);
            }
        });

    };

    $scope.enterScheduledTxn = function (scheduledTxn) {
        //populate the txn data with the information from the schedule
        $scope.registryTransactionFormData = {};
        $scope.registryTransactionFormData.txnAmount = scheduledTxn.txnAmount;
        $scope.registryTransactionFormData.txnDate = scheduledTxn.scheduledDate;
        $scope.registryTransactionFormData.payee = scheduledTxn.payee;
        $scope.registryTransactionFormData.primaryAccount = scheduledTxn.primaryAccount;
        $scope.registryTransactionFormData.statusTxt = "x";
        $scope.registryTransactionFormCategorySplits = [];
        angular.forEach(scheduledTxn.categorySplits, function (value, key) {
            //this.push(key + ': ' + value);
            if (value.category != '') {
                $scope.registryTransactionFormCategorySplits.push(value);
            }

        });
        $scope.payingScheduledTxnId = scheduledTxn.id;
        $scope.showTransactionModal = true;
    };
    $scope.addScheduledTransaction = function () {
        console.log($scope.scheduledTransactionFormData);
        //get the splits and turn it into a better list
        var newCategories = [];
        $scope.scheduledTransactionFormData.primaryAccount = $scope.selectedAccount.id;
        for (var i = 0; i < 10; i++) {
            if ($scope.scheduledTransactionFormCategorySplits[i] != undefined) {
                var categorySplit = new Object();

                var categoryEntered = $scope.scheduledTransactionFormCategorySplits[i].category;
                var txnAmountEntered = $scope.scheduledTransactionFormCategorySplits[i].txnAmount;
                categorySplit.category = categoryEntered;
                categorySplit.txnAmount = txnAmountEntered;
                newCategories.push(categorySplit);
                //console.log(categoryEntered + ":"+txnAmountEntered);    
            }
        }


        $scope.scheduledTransactionFormData.categorySplits = newCategories;

        console.log($scope.registryTransactionFormData);
        if ($scope.scheduledTransactionFormData.id == undefined) {
            ScheduledTransactionService.saveTransaction($scope.scheduledTransactionFormData).success(function (response) {
                //$scope.registryTransactions.push(response);
                $scope.showTransactionModal = false;
                $scope.$emit('transactionSaved', $scope.selectedAccount.id);
                console.log(response);
            });
        } else {
            ScheduledTransactionService.saveTransaction($scope.scheduledTransactionFormData).success(function (response) {
                //$scope.showTransactionModal = false;
                $scope.$emit('scheduledTransactionSaved', $scope.selectedAccount.id);
                console.log(response);
            });

        }

    };
    $scope.filterScheduledToCurrentDate = function () {
        $scope.scheduledDateControl.year = $scope.serverDateArray[0];
        $scope.scheduledDateControl.month = $scope.serverDateArray[1];
        $scope.getScheduledTransactionsForMonth($scope.workingAccount.id);

    };

});