angular.module('HFWApp').controller('dashboardController', function ($rootScope,$scope, $interval, $http, AccountService, RegistryService, ReportService, CategoryLookupService, DateService, ScheduledTransactionService, TransactionStatusLookupService, NotificationService, NameLookupService) {
    $scope.dashboardworkingAccount = {};
    $scope.tab = "summary";
    $scope.showTransactionModal = false;    
    $scope.setTab = function(x) {
      $scope.tab=x;  
      console.log($scope.tab);
    };
    
    $scope.isTab = function(x) {
        return $scope.tab == x;
    };
    $rootScope.$on('account-selected', function (event, data) {
        $scope.dashboardworkingAccount = data;
    });

    $rootScope.$on('transaction-operation-completed', function (event, data) {
        $scope.showTransactionModal = false;
    });

    $rootScope.$on('transaction-operation-requested', function (event, data) {
        $scope.showTransactionModal = true;
    });
    
    $scope.accountFormData = {};


    //$scope.onlineData.src="";



  

//    $scope.selectedAccount = null;
    $scope.showAccountModal = false;

    //$http.get("/HFW/services/api/v1/accounts/search/all")
    //.success(function(response) {$scope.accounts = response;});    



    $scope.currentDate = "";
    $scope.maxDate = new Date(2020, 5, 22);
    $scope.minDate = new Date(2000, 5, 22);
    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };

    $scope.openRegistryTransactionDateField = function () {
        $scope.registryTransactionDateField.opened = true;
    };

    $scope.init = function () {
        $scope.getCurrentDate();
//        $scope.getAccounts();

//        $scope.getNotificationCount();
//        $interval(function () {
//            $scope.getNotificationCount()
//        }, 30000, 0);
    };
    $scope.getCurrentDate = function () {
        DateService.lookupCurrent().success(function (response) {

            if (response.date.length > 0) {

                //parse out the date and get the year and month out; format should be YYYY-MM-DD
                $scope.serverDateArray = response.date.split("-");
                $scope.currentDate = new Date(response.date + "T00:00:00");
                //$scope.txnDateControl.year = retrievedDate[0];
                //$scope.txnDateControl.month = retrievedDate[1];
                //$scope.scheduledDateControl.year = retrievedDate[0];
                //$scope.scheduledDateControl.month = retrievedDate[1];

            } else {
                //$scope.txnDateControl.year = "2015";
                //$scope.txnDateControl.month = "01";
                //$scope.scheduledDateControl.year = "2015";
                //$scope.scheduledDateControl.month = "01";
                
                $scope.currentDate = new Date("2015-01-01" + "T00:00:00");
                $scope.serverDateArray = "2015-01-01".split("-");

            }

        }).error(function (response) {
            console.log(response);
        });
    };
    $scope.init();
    $scope.registryTransactionDateField = {
        opened: false
    };










    $scope.showSummaryTab = function () {
        if ($scope.selectedAccount == null) {
            alert("Please choose an account");
            return;
        }
        $("#accountSummary").show();
        $("#accountTransactionList").hide();
        $("#accountReports").hide();
        $("#accountOnlineFunctions").hide();
        $("#accountSchedule").hide();

    };
    $scope.showRegistryTab = function () {
        if ($scope.selectedAccount == null) {
            alert("Please choose an account");
            return;
        }
        $("#accountTransactionList").show();
        $("#accountSummary").hide();
        $("#accountReports").hide();
        $("#accountOnlineFunctions").hide();
        $("#accountSchedule").hide();
        //$scope.showTransactionModal=false;
        //$("#transactionDetailsForm").hide();
    }

    $scope.addSplit = function () {
        console.log("clicked add");

    }

    $scope.showReportTab = function () {
        if ($scope.selectedAccount == null) {
            alert("Please choose an account");
            return;
        }
        if ($scope.pieChart) {
            $scope.pieChart.destroy();

        }
        $scope.reportControl = {};
        $scope.report_transactions = [];
        $("#accountTransactionList").hide();
        $("#accountSummary").hide();
        $("#accountReports").show();
        $("#accountOnlineFunctions").hide();
        $("#accountSchedule").hide();

        plot1 = null;
        $("#pie1").html();
        //$scope.showTransactionModal=false;
        //$("#transactionDetailsForm").hide();
    }
    $scope.showOnlineTab = function (x) {
        if ($scope.selectedAccount == null) {
            alert("Please choose an account");
            return;
        }
        $scope.showOnlineMatchingModal = false;
        $("#accountTransactionList").hide();
        $("#accountSummary").hide();
        $("#accountReports").hide();
        $("#accountSchedule").hide();
        $("#accountOnlineFunctions").show();



        $scope.pendingRegistryTransactions = {};
        //$scope.showTransactionModal=false;
        //$("#transactionDetailsForm").hide();

        //get the pending registry transactions

        RegistryService.getPendingTransactionsForAccount($scope.selectedAccount.id).success(function (response) {
            $scope.pendingRegistryTransactions = response;
        });

    };









    $scope.showScheduleTab = function () {
        if ($scope.selectedAccount == null) {
            alert("Please choose an account");
            return;
        }
        //$scope.filterScheduledToCurrentDate();//getUpcomingSchedule($scope.selectedAccount);

        $("#accountTransactionList").hide();
        $("#accountReports").hide();
        $("#accountOnlineFunctions").hide();
        $("#accountSchedule").show();


    };

    $scope.convertJSDateToTextDate = function (date) {
        var txnYear = date.getFullYear();
        var txnMonth = date.getMonth();
        if (txnMonth == 0) {
            txnMonth = "01";
        } else if (txnMonth == 1) {
            txnMonth = "02";
        } else if (txnMonth == 2) {
            txnMonth = "03";
        } else if (txnMonth == 3) {
            txnMonth = "04";
        } else if (txnMonth == 4) {
            txnMonth = "05";
        } else if (txnMonth == 5) {
            txnMonth = "06";
        } else if (txnMonth == 6) {
            txnMonth = "07";
        } else if (txnMonth == 7) {
            txnMonth = "08";
        } else if (txnMonth == 8) {
            txnMonth = "09";
        } else if (txnMonth == 9) {
            txnMonth = "10";
        } else if (txnMonth == 10) {
            txnMonth = "11";
        } else if (txnMonth == 11) {
            txnMonth = "12";
        }
        var txnDate = date.getDate() + "";
        if (txnDate.length < 2) {
            txnDate = "0" + txnDate;
        }
        return txnYear + "-" + txnMonth + "-" + txnDate;
    };

    $scope.convertTextDateToJSDate = function (date) {
        var txnDate = new Date(date + 'T01:00:01Z');
        return txnDate;
    };

    $scope.lookupStatus = function (scheduledTxn) {
        return TransactionStatusLookupService.getStatus(scheduledTxn.statusTxt);
    };

    $scope.showNewTransaction = function (x) {
        //$scope.registryTransactionFormData = {};
        //$scope.registryTransactionFormData.txnDate = $scope.currentDate;
        //$scope.registryTransactionFormData.statusTxt = 'x';
        //$scope.registryTransactionFormCategorySplits = [];
        //var obj = new Object();
        //obj.category = "";
        //obj.txnAmount = "";
        //$scope.registryTransactionFormCategorySplits.push(obj);
        //$scope.showTransactionModal = true;
        $rootScope.$broadcast('transaction-operation-requested',{});
        //$("#transactionDetailsForm").show();
    };


});