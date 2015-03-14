var hfwApp = angular.module('HFWApp', []);




hfwApp.controller('registryController', function ($scope, $location, RegistryService, DateService) {

    $scope.txnDateControl = {};
    $scope.txnDateControl.year = "";
    $scope.txnDateControl.month = "";

    $scope.accountId = "";

    $scope.registryTransactions = [];


    $scope.init = function () {
        $scope.accountId = $location.search().accountId;
        console.log($scope.accountId);
        $scope.getCurrentDate();
        //$scope.getTransactionsForMonth($scope.accountId);

    };

    $scope.$on('dateResolved', function (event, data) {
        $scope.getTransactionsForMonth($scope.accountId);
    })

    $scope.getTransactionsForMonth = function (accountId) {
        console.log(accountId);
        var dateToRetrieve = $scope.txnDateControl.year + "-" + $scope.txnDateControl.month;
        console.log(dateToRetrieve);
        $scope.registryTransactions = [];
        RegistryService.getRegistryForAccountForMonth(accountId, dateToRetrieve).success(function (response) {
            for (idx in response) {
                $scope.registryTransactions.push(response[idx]);
                //console.log(txn.id);
            }
            //$scope.registryTransactions. = response;
            //$scope.txnIndex = $scope.txnIndex + $scope.numberTxnsToRetrieve;
        });
        /*
         $http.get("/HFW/services/api/v1/register/get/all/"+accountId)
         .success(function(response) {$scope.registryTransactions = response;}); 
         */
    };

    $scope.clickNewTransactionButton = function (x) {
        window.location.href = "/HFW/mobile/transaction.jsp#/!?accountId=" + x;
    };
    $scope.getCurrentDate = function () {
        DateService.lookupCurrent().success(function (response) {

            if (response.length > 0) {

                //parse out the date and get the year and month out; format should be YYYY-MM-DD
                console.log(response);
                var retrievedDate = response.split("-");
                $scope.txnDateControl.year = retrievedDate[0];
                $scope.txnDateControl.month = retrievedDate[1];
            } else {
                $scope.txnDateControl.year = "2015";
                $scope.txnDateControl.month = "01";
            }
            $scope.$emit('dateResolved');

        });
    }

    $scope.init();






});




