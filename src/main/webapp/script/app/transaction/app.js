var hfwApp = angular.module('HFWApp', []);




hfwApp.controller('transactionController', function ($scope, $location, RegistryService, CategoryLookupService,DateService) {

    var accountId = $location.search().accountId;
    $scope.currentDate = "";
    $scope.registryTransactionFormData = {};
    $scope.hideTxnRetrievedCategories = true;
    $scope.retrievedCategories = {};
    $scope.categoryTypingIndex = -1;

    RegistryService.getRegistryForAccount(accountId).success(function (response) {
        $scope.registryTransactions = response;
    });


    $scope.clickNewTransactionButton = function (x) {
        console.log(x.id);
        window.location.href = "/HFW/mobile/transaction.jsp#/!?accountId=" + x.id;
    };

    $scope.init = function () {
        $scope.accountId = $location.search().accountId;
        $scope.getCurrentDate();
        console.log($scope.accountId);

    };

    $scope.addRegistryTransaction = function () {
        //get the splits and turn it into a better list
        var newCategories = [];
        $scope.registryTransactionFormData.primaryAccount = $scope.accountId;
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
                //$scope.registryTransactions.push(response);
                //$scope.showTransactionModal = false;
                //console.log(response);
            });
        } else {
            RegistryService.saveTransaction($scope.registryTransactionFormData).success(function (response) {
                //$scope.showTransactionModal = false;
                //console.log(response);
            });

        }
        window.location.href = "/HFW/mobile/registry.jsp#/!?accountId=" + $scope.accountId;

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


    $scope.clickNewTransactionCancel = function () {
        console.log($scope.accountId);
        window.location.href = "/HFW/mobile/registry.jsp#/!?accountId=" + $scope.accountId;
        //$scope.showTransactionModal = false;
        //$("#transactionDetailsForm").hide();
    }

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
    $scope.getCurrentDate = function () {
        DateService.lookupCurrent().success(function (response) {
            
            if (response.length > 0) {
                $scope.currentDate = response;    
                
            } else {
                $scope.currentDate = "2015-01-01";
            }
            $scope.$emit('dateResolved');
        });        
    }

    $scope.init();

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
    })
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

    })
    $scope.$watch('registryTransactionFormCategorySplits[2].category', function (oldValue, newValue) {
        //console.log(oldValue, newValue);
        //$scope.calcBudgetTotals();
        if (newValue != undefined && newValue.length > 3) {
            $scope.categoryTypingIndex = 1;
            $scope.getCategories(newValue);
        } else {
            $scope.categoryTypingIndex = -1;
            $scope.retrievedCategories = [];

        }

    })
    $scope.$watch('registryTransactionFormCategorySplits[3].category', function (oldValue, newValue) {
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
    $scope.$watch('registryTransactionFormCategorySplits[4].category', function (oldValue, newValue) {
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
    $scope.$on('dateResolved', function (event, data) {
        if ($scope.registryTransactionFormData.txnDate == undefined || $scope.registryTransactionFormData.txnDate == "") {
            $scope.registryTransactionFormData.txnDate = $scope.currentDate;    
        }
        
    });

});




