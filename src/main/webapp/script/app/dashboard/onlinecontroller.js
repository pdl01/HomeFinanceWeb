angular.module('HFWApp').controller('accountOnlineController', function ($rootScope,$scope, $interval, $http, AccountService, RegistryService, ReportService, CategoryLookupService, DateService, ScheduledTransactionService, TransactionStatusLookupService, NotificationService, NameLookupService) {
    $scope.workingAccount = {};
    $scope.onlineData = {};
    $scope.uploadProgress = "";
    $scope.pendingRegistryTransactions = [];
    $scope.showOnlineMatchingModal = false;
    $scope.onlineTxnMatches = [];

    $scope.pendingMatchedTransactions = [];
    $scope.selectedPendingTransaction = [];


    $scope.onlineSortType = 'txnDate'; // set the default sort type
    $scope.onlineSortReverse = false;  // set the default sort order

    
    $rootScope.$on('account-selected', function (event, data) {
        $scope.workingAccount = data;
    });

    $scope.selectFile = function () {
        $("#file").click();
    };
    $scope.showOnlineMatchingDialog = function (x) {
        console.log(x);
        //find some transactions that could match the one that was entered
        $scope.selectedPendingTransaction = x;
        $scope.pendingMatchedTransactions = [];
        RegistryService.getMatchingTransactionsForPendingTransaction(x.id).success(function (response) {
            $scope.pendingMatchedTransactions = response;
        });
        $scope.showOnlineMatchingModal = true;
    };

    $scope.acceptPendingTransactionAsNew = function (pendingTransaction) {
        RegistryService.acceptPendingTransactionAsNew(pendingTransaction.id).success(function (response) {
            //$scope.pendingMatchedTransactions = response;
            //remove the transaction from the pending list or change the display to denote its been accepted
            alert("Transaction Created");
            $scope.showTransactionForm(response);
            //do the transaction modal based on the response
        });

    };
    $scope.acceptMatchForPending = function (pendingTransaction, enteredtransaction) {
        //update the status to cleared of the enteredTransaction
        //update the status to accepted of the pendingTransaction
        RegistryService.matchPendingTransactionToExistingTransaction(pendingTransaction.id, enteredtransaction.id).success(function (response) {
            //$scope.pendingMatchedTransactions = response;
            //remove the transaction from the pending list or change the display to denote its been accepted
        });

        $scope.showOnlineMatchingModal = false;
    };

    $scope.selectScheduledTxnRetrievedCategory = function (val, element) {
        console.log(val);
        console.log($scope.categoryTypingIndex);
        if ($scope.categoryTypingIndex != "-1") {
            //$scope.budgetItemFormData.category = val;
            $scope.scheduledTransactionFormCategorySplits[$scope.categoryTypingIndex].category = val;
            $scope.retrievedCategories = [];
            $scope.hideTxnRetrievedCategories = true;
            $scope.categoryTypingIndex = -1;
        }
        console.log($scope.categoryTypingIndex);

    };


    $scope.fileNameChanged = function () {
        console.log($('#file'));
    }
    $scope.addOnlineData = function () {
        console.log($scope.onlineData);
        console.log($('#file'));
        RegistryService.uploadData($scope.selectedAccount.id, $('#file'));
        /*RegistryService.uploadData($scope.selectedAccount.id,$('#file')).success(function(response) {
         $scope.onlineData = "";
         console.log("Successful");
         });
         */
    };
    $scope.acceptAllOnlineTxns = function () {
        if ($scope.selectedAccount == null) {
            alert("Please choose an account");
            return;
        }
        RegistryService.acceptAllPendingTransactionAsNew($scope.selectedAccount.id).success(function (response) {
            //$scope.registryTransactions.push(response);
            $scope.$emit('transactionSaved', $scope.selectedAccount.id);
            console.log(response);
        });
    };
    $scope.dismissMatchForPending = function (pendingTransaction) {
        //update the status to cleared of the enteredTransaction
        //update the status to accepted of the pendingTransaction
        RegistryService.dismissPendingTransaction(pendingTransaction.id).success(function (response) {
            //$scope.pendingMatchedTransactions = response;
            //remove the transaction from the pending list or change the display to denote its been dismissed
        });

        $scope.showOnlineMatchingModal = false;
    };
    $scope.showOnlineMatchingEditTxnDialog = function (x) {
        console.log(x);
        $scope.showTransactionForm(x);
    };
    $scope.hideOnlineMatchingDialog = function () {
        $scope.showOnlineMatchingModal = false;
    };

});