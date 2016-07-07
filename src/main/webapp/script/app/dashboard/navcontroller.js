angular.module('HFWApp').controller('navController', function ($rootScope,$scope, $interval, $http, AccountService, RegistryService, ReportService, CategoryLookupService, DateService, ScheduledTransactionService, TransactionStatusLookupService, NotificationService, NameLookupService) {
    $scope.workingAccount = {};
    $scope.checking_accounts = [];
    $scope.savings_accounts = [];
    $scope.creditcard_accounts = [];
    $scope.investment_accounts = [];
    $scope.retirement_accounts = [];
    $scope.debt_accounts = [];
    $scope.asset_accounts = [];
    $scope.other_accounts = [];

    $scope.hideAccounts = [];
    $scope.hideAccounts['checking'] = false;
    $scope.hideAccounts['savings'] = false;
    $scope.hideAccounts['retirement'] = false;
    $scope.hideAccounts['investment'] = false;

    $scope.hideAccounts['creditcard'] = false;
    $scope.hideAccounts['other'] = false;

    $scope.notifications = [];

    $scope.numNotifications = 0;
    $scope.showNotificationsLink = false;
    $scope.showNotificationsModal = false;
    $scope.selectednotification = null;


    $scope.setAsCurrent = function (x) {
        $scope.workingAccount = x;
        $rootScope.$broadcast('account-selected', x);
    }
    
        $scope.addAccount = function () {

        if ($scope.accountFormData.id == undefined) {  //doing add
            AccountService.saveAccount($scope.accountFormData).success(function (response) {

                if ($scope.accountFormData.accountType == 'Checking') {
                    $scope.checking_accounts.push(response);
                } else if ($scope.accountFormData.accountType == 'Savings') {
                    $scope.savings_accounts.push(response);
                } else if ($scope.accountFormData.accountType == 'Retirement') {
                    $scope.retirement_accounts.push(response);
                } else if ($scope.accountFormData.accountType == 'Investment') {
                    $scope.investment_accounts.push(response);
                } else if ($scope.accountFormData.accountType == 'Credit Card') {
                    $scope.creditcard_accounts.push(response);
                } else {
                    $scope.other_accounts.push(value);
                }

                console.log(response);
            });
        } else {  //doing update

            AccountService.saveAccount($scope.accountFormData).success(function (response) {

                if ($scope.accountFormData.accountType == 'Checking') {
                    //$scope.checking_accounts.push(response);
                } else if ($scope.accountFormData.accountType == 'Savings') {
                    //$scope.savings_accounts.push(response);
                } else if ($scope.accountFormData.accountType == 'Retirement') {
                    //$scope.retirement_accounts.push(response);
                } else if ($scope.accountFormData.accountType == 'Investment') {
                    //$scope.investment_accounts.push(response);
                } else if ($scope.accountFormData.accountType == 'Credit Card') {
                    //$scope.creditcard_accounts.push(response);
                } else {
                    //$scope.other_accounts.push(value);
                }

                console.log(response);
            });
        }
        /*
         $http({
         method  : 'POST',
         url     : '/HFW/services/api/v1/accounts/save',
         data    : JSON.stringify($scope.accountFormData),  // pass in data as strings
         headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
         })
         .success(function(data) {
         
         $scope.accounts.push(data);
         
         console.log(data);
         
         });
         */
    };
    $scope.toggleAccountGroupHide = function (accountType) {
        console.log(accountType);

        $scope.hideAccounts[accountType] = !$scope.hideAccounts[accountType];
    };
    
    $scope.getAccounts = function () {
        AccountService.getAccounts().success(function (response) {
            angular.forEach(response, function (value, key) {
                console.log(value);
                if (value.accountType == 'Checking') {
                    $scope.checking_accounts.push(value);
                } else if (value.accountType == 'Savings') {
                    $scope.savings_accounts.push(value);
                } else if (value.accountType == 'Retirement') {
                    $scope.retirement_accounts.push(value);
                } else if (value.accountType == 'Investment') {
                    $scope.investment_accounts.push(value);
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
    };
    $scope.refreshAccounts = function () {
        $scope.checking_accounts = [];
        $scope.savings_accounts = [];
        $scope.retirement_accounts = [];
        $scope.investment_accounts = [];
        $scope.creditcard_accounts = [];
        $scope.other_accounts = [];
        $scope.accounts = {};
        $scope.getAccounts();
    };

    $scope.showNotifications = function () {
        NotificationService.getNotificationsByStatus("0").success(function (response) {
            console.log(response);
            $scope.notifications = response;
            $scope.showNotificationsModal = true;

        });
    };

    $scope.getNotificationCount = function () {
        NotificationService.getNotificationCountByStatus("0").success(function (response) {
            //console.log(response);
            if (response != '0') {
                $scope.showNotificationsLink = true;
                $scope.numNotifications = response;
            }

            //var responseHTML = "<a href=\"#\" class=\"notificationCountLink\">"+response+"</a>";
            //$("#headerLinkNotifications").html(responseHTML);    
        });

    };
    $scope.markNotificationAsRead = function (notificationId) {
        NotificationService.markRead(notificationId).success(function (response) {
            $scope.getNotificationCount();
        });
    };

    //TODO:rename this to click account
    $scope.clickGoButton = function (x) {
        
        
        console.log(x.id);
        $scope.selectedAccount = x;
        $scope.txnIndex = 0;
        $scope.registryTransactions = [];
        $scope.registryTransactionFormData.primaryAccount = x.id;
        $scope.$emit('showRegisterTransactions', x);
        //this.getTransactionsForAccount(x.id);
        this.getTransactionsForMonth(x.id);
        this.showRegistryTab();
    };

    $scope.clickEditAccount = function () {
        //console.log(x.id);
        if ($scope.selectedAccount == null) {
            alert("Please choose an account");
            return;
        }
        //$scope.selectedAccount = x;
        $scope.accountFormData = $scope.selectedAccount;
        $scope.showAccountModal = true;
        //$scope.registryTransactionFormData.primaryAccount=x.id;
        //$scope.$emit('showRegisterTransactions', x);
        //this.getTransactionsForAccount(x.id);
    };

    $scope.showNewAccount = function (x) {
        $scope.accountFormData = {};
        $scope.showAccountModal = true;
        //$("#accountDetailsForm").show();
    };

    $scope.clickNewAccountCancel = function (x) {
        $scope.showAccountModal = false;
        //$("#accountDetailsForm").hide();
    };

    $scope.clickDeleteAccount = function (x) {
        if ($scope.accountFormData.id != undefined) {
            AccountService.deleteAccount($scope.accountFormData.id).success(function (response) {
                alert("Account Deleted");
                $scope.showAccountModal = false;
            });
        }

        //$("#accountDetailsForm").hide();
    };
    
    $scope.init = function () {
        //$scope.getCurrentDate();
        $scope.getAccounts();

        $scope.getNotificationCount();
        $interval(function () {
            $scope.getNotificationCount()
        }, 30000, 0);
    };

    $scope.init();
});