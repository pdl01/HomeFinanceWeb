angular.module('HFWApp').controller('transactionFormController', function ($rootScope, $scope, $interval, $http, AccountService, RegistryService, ReportService, CategoryLookupService, DateService, ScheduledTransactionService, TransactionStatusLookupService, NotificationService, NameLookupService) {
    $scope.workingAccount={};
    $scope.workingTransaction = {};
    $scope.registryTransactionFormCategorySplits = [];
    
    $rootScope.$on('transaction-operation-requested',function(event,data) {
        $scope.workingTransaction = {};
        $scope.registryTransactionFormCategorySplits = [];
        $scope.workingTransaction = angular.copy(data);
        var obj = new Object();
        obj.category = "";
        obj.txnAmount = "";
        //$scope.registryTransactionFormCategorySplits.push(obj);
        angular.forEach($scope.workingTransaction.categorySplits, function (value, key) {
            //this.push(key + ': ' + value);
            if (value.category != '') {
                $scope.registryTransactionFormCategorySplits.push(value);
            }

        });

    
    });

    $rootScope.$on('account-selected', function (event, data) {
        $scope.workingAccount = angular.copy(data);
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
        //console.log($scope.registryTransactionFormCategorySplits[0]);
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

    });


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
        //$scope.showTransactionModal = false;
        //$("#transactionDetailsForm").hide();
        $rootScope.$broadcast('transaction-operation-completed', x);
    };

    $scope.addRegistryTransaction = function () {
        //get the splits and turn it into a better list
        var newCategories = [];
        $scope.workingTransaction.primaryAccount = $scope.workingAccount.id;
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


        $scope.workingTransaction.categorySplits = newCategories;

        console.log($scope.workingTransaction);
        if ($scope.workingTransaction.id == undefined) {
            RegistryService.saveTransaction($scope.workingTransaction).success(function (response) {
                //$scope.registryTransactions.push(response);
                /*
                if ($scope.payingScheduledTxnId != "") {
                    ScheduledTransactionService.payTransaction($scope.payingScheduledTxnId).success(function (response) {
                        $scope.payingScheduledTxnId = "";
                    }).error(function (response) {
                        $scope.payingScheduledTxnId = "";
                    });
                }
                */
               /*
                $scope.showTransactionModal = false;
                
                $scope.$emit('transactionSaved', $scope.selectedAccount.id);
                */
                console.log(response);
                $rootScope.$broadcast('transaction-operation-completed',{});

            });
        } else {
            RegistryService.saveTransaction($scope.workingTransaction).success(function (response) {
                /*$scope.showTransactionModal = false;*/
                /*$scope.$emit('transactionSaved', $scope.selectedAccount.id);*/
                console.log(response);
                $rootScope.$broadcast('transaction-operation-completed',{});
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

    $scope.getCategories = function (val) {
        CategoryLookupService.lookup(val).success(function (response) {

            if (response.length > 0) {
                $scope.hideTxnRetrievedCategories = false;
                $scope.retrievedCategories = response;
            } else {
                $scope.hideTxnRetrievedCategories = true;
                $scope.retrievedCategories = [];
            }

        });
    };

    $scope.selectRetrievedCategory = function (val, element) {
        console.log(val);
        console.log($scope.categoryTypingIndex);
        if ($scope.categoryTypingIndex != "-1") {
            //$scope.budgetItemFormData.category = val;
            $scope.registryTransactionFormCategorySplits[$scope.categoryTypingIndex].category = val;
            $scope.retrievedCategories = [];
            $scope.hideTxnRetrievedCategories = true;
            $scope.categoryTypingIndex = -1
        }
        console.log($scope.categoryTypingIndex);

    };


});




