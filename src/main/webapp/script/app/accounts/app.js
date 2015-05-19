var hfwApp = angular.module('HFWApp', []);




hfwApp.controller('accountController', function ($scope, AccountService) {

    $scope.accounts = {};
    $scope.checking_accounts = [];
    $scope.savings_accounts = [];
    $scope.creditcard_accounts = [];
    $scope.investment_accounts = [];
    $scope.retirement_accounts = [];
    $scope.debt_accounts = [];
    $scope.asset_accounts = [];
    $scope.other_accounts = [];
    /*
     AccountService.getAccounts().success(function(response) {
     $scope.accounts=response;
     });
     */

    $scope.clickGoButton = function (x) {
        console.log(x.id);
        this.selectedAccount = x;
        window.location.href = "/HFW/app/mobile/registry/?account=" + x.id;
    };



    $scope.getAccounts = function () {
        AccountService.getAccounts().success(function (response) {
            angular.forEach(response, function (value, key) {
                console.log(value);
                if (value.accountType == 'Checking') {
                    $scope.checking_accounts.push(value);
                } else if (value.accountType == 'Savings') {
                    $scope.savings_accounts.push(value);
                } else if (value.accountType == 'Credit Card') {
                    $scope.creditcard_accounts.push(value);
                } else {
                    $scope.other_accounts.push(value);
                }
                //this.push(key + ': ' + value);
                //if (value.category != '') {
                //    $scope.registryTransactionFormCategorySplits.push(value);    
                //}

            });
            $scope.accounts = response;
        });
    }
    $scope.getAccounts();

    $scope.refreshAccounts = function () {
        $scope.checking_accounts = [];
        $scope.savings_accounts = [];
        $scope.other_accounts = [];
        $scope.accounts = {};
        $scope.getAccounts();
    }






});




