var hfwApp = angular.module('HFWApp', []);




hfwApp.controller('dashboardController', function ($scope, $http, AccountService, RegistryService, ReportService, CategoryLookupService, DateService,ScheduledTransactionService) {
    $scope.accountFormData = {};

    $scope.onlineData = {};

    //$scope.onlineData.src="";
    $scope.uploadProgress = "";
    $scope.pendingRegistryTransactions = {};
    $scope.showOnlineMatchingModal = false;
    $scope.onlineTxnMatches = {};

    $scope.registryTransactions = {};
    $scope.registryTransactionFormData = {};
    $scope.hideTxnRetrievedCategories = true;
    $scope.retrievedCategories = {};
    $scope.categoryTypingIndex = -1;

    $scope.pendingMatchedTransactions = [];
    $scope.selectedPendingTransaction = [];

    $scope.reportControl = {};
    $scope.pieChart = null;
    $scope.registryTransactionFormCategorySplits = [];


    $scope.selectedAccount = null;
    $scope.showAccountModal = false;
    $scope.showTransactionModal = false;
    //$http.get("/HFW/services/api/v1/accounts/search/all")
    //.success(function(response) {$scope.accounts = response;});    
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


    $scope.txnIndex = 0;  //this is the start index of txns to retrieve 
    $scope.numberTxnsToRetrieve = 25;

    $scope.txnDateControl = {};
    $scope.txnDateControl.year = "";
    $scope.txnDateControl.month = "";

    $scope.report_transactions = {};

    $scope.scheduledtransactions=[];
    $scope.scheduledTransactionFormData = {}
    $scope.showScheduledTransactionModal=false;
    $scope.scheduledTransactionFormCategorySplits = [];
    $scope.scheduledDateControl = {};
    $scope.scheduledDateControl.year = "";
    $scope.scheduledDateControl.month = "";
    
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

    })
    
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

    })
    $scope.$watch('registryTransactionFormData.txnAmount', function (oldValue, newValue) {
        //console.log(oldValue, newValue);
        //$scope.calcBudgetTotals();
        console.log($scope.registryTransactionFormCategorySplits[0]);
        if ($scope.registryTransactionFormCategorySplits[0] == undefined) {
            var obj = new Object();
            obj.category = "";
            obj.txnAmount=newValue;
            $scope.registryTransactionFormCategorySplits.push(obj);
        } else if ($scope.registryTransactionFormCategorySplits[0].category=="") {
            $scope.registryTransactionFormCategorySplits[0].txnAmount = $scope.registryTransactionFormData.txnAmount;
        }
        /*
        if (registryTransactionFormCategorySplits[0].category == undefined || registryTransactionFormCategorySplits[0].category == "") {
            //$scope.$emit('txnAmountEdited', newValue);
            registryTransactionFormCategorySplits[0].txnAmount = registryTransactionFormData.txnAmount;
        }
        */

    })
    $scope.$on('txnAmountEdited',function(event,data) {
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

    $scope.toggleAccountGroupHide = function (accountType) {
        console.log(accountType);

        $scope.hideAccounts[accountType] = !$scope.hideAccounts[accountType];
    }

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
    }
    $scope.init = function () {
        $scope.getCurrentDate();
        $scope.getAccounts();
    }
    $scope.getCurrentDate = function () {
        DateService.lookupCurrent().success(function (response) {

            if (response.length > 0) {

                //parse out the date and get the year and month out; format should be YYYY-MM-DD
                var retrievedDate = response.split("-");
                $scope.currentDate = response;
                $scope.txnDateControl.year = retrievedDate[0];
                $scope.txnDateControl.month = retrievedDate[1];
                $scope.scheduledDateControl.year = retrievedDate[0];
                $scope.scheduledDateControl.month = retrievedDate[1];

            } else {
                $scope.txnDateControl.year = "2015";
                $scope.txnDateControl.month = "01";
                $scope.scheduledDateControl.year = "2015";
                $scope.scheduledDateControl.month = "01";

                $scope.currentDate = "2015-01-01";

            }

        });
    }
    $scope.init();

    $scope.refreshAccounts = function () {
        $scope.checking_accounts = [];
        $scope.savings_accounts = [];
        $scope.retirement_accounts = [];
        $scope.investment_accounts = [];
        $scope.creditcard_accounts = [];
        $scope.other_accounts = [];
        $scope.accounts = {};
        $scope.getAccounts();
    }
    $scope.selectFile = function ()
    {
        $("#file").click();
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

    $scope.clickEditAccount = function (x) {
        console.log(x.id);
        //$scope.selectedAccount = x;
        $scope.accountFormData = x;
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


    $scope.showNewTransaction = function (x) {
        $scope.registryTransactionFormData = {};
        $scope.registryTransactionFormData.txnDate = $scope.currentDate;
        $scope.registryTransactionFormData.statusTxt = 'x';
        $scope.registryTransactionFormCategorySplits = [];
        var obj = new Object();
        obj.category = "";
        obj.txnAmount="";
        $scope.registryTransactionFormCategorySplits.push(obj);
        $scope.showTransactionModal = true;
        //$("#transactionDetailsForm").show();
    };
    
    $scope.showScheduledTransactionForm = function(x) {
        $scope.showScheduledTransactionModal=true;
    };
    
    $scope.clickScheduledTransactionCancel = function() {
        $scope.showScheduledTransactionModal=false;
    };
    
    $scope.addScheduledTransaction = function() {
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
                $scope.$emit('transactionSaved',$scope.selectedAccount.id);
                console.log(response);
            });
        } else {
            ScheduledTransactionService.saveTransaction($scope.scheduledTransactionFormData).success(function (response) {
                //$scope.showTransactionModal = false;
                $scope.$emit('scheduledTransactionSaved',$scope.selectedAccount.id);
                console.log(response);
            });

        }
        
    };
    
    $scope.showTransactionForm = function (x) {
        console.log(x);
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
    }

    $scope.showRegistryTab = function () {
        if ($scope.selectedAccount == null) {
            alert ("Please choose an account");
            return;
        }
        $("#accountTransactionList").show();
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
            alert ("Please choose an account");
            return;
        }
        if ($scope.pieChart) {
            $scope.pieChart.destroy();
            
        }
        $scope.reportControl = {};
        $("#accountTransactionList").hide();
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
            alert ("Please choose an account");
            return;
        }
        $scope.showOnlineMatchingModal = false;
        $("#accountTransactionList").hide();
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

    }

    $scope.showOnlineMatchingDialog = function (x) {
        console.log(x);
        //find some transactions that could match the one that was entered
        $scope.selectedPendingTransaction = x;
        $scope.pendingMatchedTransactions = [];
        RegistryService.getMatchingTransactionsForPendingTransaction(x.id).success(function (response) {
            $scope.pendingMatchedTransactions = response;
        });
        $scope.showOnlineMatchingModal = true;
    }
    $scope.acceptMatchForPending = function (pendingTransaction, enteredtransaction) {
        //update the status to cleared of the enteredTransaction
        //update the status to accepted of the pendingTransaction
        RegistryService.matchPendingTransactionToExistingTransaction(pendingTransaction.id, enteredtransaction.id).success(function (response) {
            //$scope.pendingMatchedTransactions = response;
            //remove the transaction from the pending list or change the display to denote its been accepted
        });

        $scope.showOnlineMatchingModal = false;
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
    $scope.filterToCurrentDate = function () {
        var retrievedDate = $scope.currentDate.split("-");

        $scope.txnDateControl.year = retrievedDate[0];
        $scope.txnDateControl.month = retrievedDate[1];
        $scope.getTransactionsForMonth($scope.selectedAccount.id);

    };
    $scope.filterScheduledToCurrentDate = function () {
        var retrievedDate = $scope.currentDate.split("-");

        $scope.scheduledDateControl.year = retrievedDate[0];
        $scope.scheduledDateControl.month = retrievedDate[1];
        $scope.getScheduledTransactionsForMonth($scope.selectedAccount.id);

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
    $scope.renderReport = function (plotData) {
        if ($scope.pieChart) {
            $scope.pieChart.destroy();
        }
        $scope.pieChart = $.jqplot('pie1', [plotData], {
            gridPadding: {top: 0, bottom: 38, left: 0, right: 0},
            seriesDefaults: {
                renderer: $.jqplot.PieRenderer,
                trendline: {show: false},
                rendererOptions: {padding: 8, showDataLabels: true}
            },
            legend: {
                show: true,
                placement: 'inside',
                rendererOptions: {
                },
                location: 'e',
                marginTop: '15px'
            }
        });
        
        
    }
    $scope.doReport = function (x) {
        console.log($scope.selectedAccount.id + ":" + $scope.reportControl.reportType + ":" + $scope.reportControl.reportPeriod);
        //var plotData = [[['Income:Other',25],['Income:Net Pay',14],['c',7]]]

        if ($scope.reportControl.reportType == undefined) {
            $scope.reportControl.reportType = "INCOME";
        }
        if ($scope.reportControl.reportPeriod == undefined) {
            $scope.reportControl.reportPeriod = "currentMonth";
        }
        ReportService.getReportForPeriodForAccount($scope.selectedAccount.id, $scope.reportControl.reportType, $scope.reportControl.reportPeriod).success(function (response) {
            //parse the data into jqplot data format
            var plotData = [];
            //call the renderReport with the data
            angular.forEach(response.dataPoints, function (value, key) {
                var x = [];
                x.push(value.name);
                x.push(value.value);
                plotData.push(x);


            });
            
            $scope.renderReport(plotData);
            //push the plotDate into report transactions
            //and sort
            $scope.report_transactions = response.dataPoints;
        });


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



    $scope.getTransactionsForAccount = function (accountId) {
        RegistryService.getRegistryBlockForAccount(accountId, $scope.txnIndex, $scope.numberTxnsToRetrieve).success(function (response) {
            for (idx in response) {
                $scope.registryTransactions.push(response[idx]);
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
        $scope.registryTransactions = [];
        RegistryService.getRegistryForAccountForMonth(accountId, dateToRetrieve).success(function (response) {
            for (idx in response) {
                $scope.registryTransactions.push(response[idx]);
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
                $scope.showTransactionModal = false;
                $scope.$emit('transactionSaved',$scope.selectedAccount.id);
                console.log(response);
            });
        } else {
            RegistryService.saveTransaction($scope.registryTransactionFormData).success(function (response) {
                $scope.showTransactionModal = false;
                $scope.$emit('transactionSaved',$scope.selectedAccount.id);
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
    $scope.showScheduleTab = function () {
        if ($scope.selectedAccount == null) {
            alert ("Please choose an account");
            return;
        }
        $scope.getUpcomingSchedule($scope.selectedAccount);
        
        $("#accountTransactionList").hide();
        $("#accountReports").hide();
        $("#accountOnlineFunctions").hide();
        $("#accountSchedule").show();


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
    $scope.getScheduledTransactionsForMonth = function (accountId) {
        var dateToRetrieve = $scope.scheduledDateControl.year + "-" + $scope.scheduledDateControl.month;
        $scope.scheduledtransactions = [];
        ScheduledTransactionService.getUpcomingScheduledTransactionsForAccountForDate(accountId,dateToRetrieve).success(function (response) {
            for (idx in response) {
                $scope.scheduledtransactions.push(response[idx]);
                //console.log(txn.id);
            }
        });

    };


});

hfwApp.directive('modal', function () {
    return {
        template: '<div class="modal fade">' +
                '<div class="modal-dialog">' +
                '<div class="modal-content">' +
                '<div class="modal-header">' +
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                '<h4 class="modal-title">{{ title }}</h4>' +
                '</div>' +
                '<div class="modal-body" ng-transclude></div>' +
                '</div>' +
                '</div>' +
                '</div>',
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: true,
        link: function postLink(scope, element, attrs) {
            scope.title = attrs.title;

            scope.$watch(attrs.visible, function (value) {
                if (value == true)
                    $(element).modal('show');
                else
                    $(element).modal('hide');
            });

            $(element).on('shown.bs.modal', function () {
                scope.$apply(function () {
                    scope.$parent[attrs.visible] = true;
                });
            });

            $(element).on('hidden.bs.modal', function () {
                scope.$apply(function () {
                    scope.$parent[attrs.visible] = false;
                });
            });
        }
    };
});


