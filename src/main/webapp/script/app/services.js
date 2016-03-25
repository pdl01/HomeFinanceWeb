hfwApp.factory("AccountService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var AccountService = {};

    AccountService.getAccounts = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/accounts/search/all'
        });
    };
    AccountService.getAccount = function (id) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/accounts/getbyId/' + id
        });
    };
    AccountService.deleteAccount = function (id) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");

        return $http({
            method: 'DELETE',
            url: '/services/api/v1/accounts/delete/' + id,
            //data: JSON.stringify(account), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)

        });
    };

    AccountService.saveAccount = function (account) {
        delete account.$$hashKey;
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/accounts/save',
            data: JSON.stringify(account), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        })
    };

    return AccountService;
});

hfwApp.factory("RegistryService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var RegistryService = {};

    RegistryService.getRegistryForAccount = function (id) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/all/' + id
        });
    };
    RegistryService.getRegistryBlockForAccount = function (id, start, number) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/' + id + '/' + start + '/' + number
        });
    };
    RegistryService.getRegistryForAccountForDate = function (id, date) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/bydate/' + id + '/' + date
        });
    };
    RegistryService.getRegistryForAccountForMonth = function (id, date) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/bymonth/' + id + '/' + date
        });
    };
    RegistryService.getPendingTransactionsForAccount = function (id) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/pending/' + id
        });
    };
    RegistryService.matchPendingTransactionToExistingTransaction = function (pendingid, existingid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/pending/match/' + pendingid + '/' + existingid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.dismissPendingTransaction = function (pendingid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/pending/dismiss/' + pendingid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.acceptPendingTransactionAsNew = function (pendingid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/pending/acceptasnew/' + pendingid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.getMatchingTransactionsForPendingTransaction = function (transactionId) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/matched/' + transactionId
        });
    };
    RegistryService.acceptAllPendingTransactionAsNew = function (accountid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/pending/acceptallasnew/' + accountid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.saveTransaction = function (transaction) {
        delete transaction.$$hashKey;
        transaction.txnDate = this.convertJSDateToTextDate(transaction.txnDate);
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/save',
            data: JSON.stringify(transaction), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    RegistryService.convertJSDateToTextDate = function(date){
      var txnYear = date.getFullYear();
      var txnMonth = date.getMonth();
      if (txnMonth == 0) {
          txnMonth = "01";
      } else if(txnMonth == 1) {
          txnMonth = "02";
      } else if(txnMonth == 2) {
          txnMonth = "03";
      } else if(txnMonth == 3) {
          txnMonth = "04";
      } else if(txnMonth == 4) {
          txnMonth = "05";
      } else if(txnMonth == 5) {
          txnMonth = "06";
      } else if(txnMonth == 6) {
          txnMonth = "07";
      } else if(txnMonth == 7) {
          txnMonth = "08";
      } else if(txnMonth == 8) {
          txnMonth = "09";
      } else if(txnMonth == 9) {
          txnMonth = "10";
      } else if(txnMonth == 10) {
          txnMonth = "11";
      } else if(txnMonth == 11) {
          txnMonth = "12";
      }
      var txnDate = date.getDate()+"";
      if (txnDate.length < 2) {
          txnDate = "0"+txnDate;
      }
      return txnYear + "-" + txnMonth + "-" + txnDate;
    };

    RegistryService.convertTextDateToJSDate = function(date){
      var txnDate = new Date (date+'T01:00:01Z');
      return txnDate;
    };

    
    RegistryService.validateTransaction = function (transaction) {
        delete transaction.$$hashKey;
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/validate',
            data: JSON.stringify(transaction), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    RegistryService.uploadData = function (accountId, data) {

        var formData = new FormData($('#onlineDataUploadForm')[0]);
        console.log(formData);
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
//
//TODO: convert this all to angular form
        //$scope.uploadProgress="Uploading";
        $.ajax({
            url: '/servlet/fileUpload/' + accountId, //Server script to process data
            type: 'POST',
            //Ajax events

            // Form data
            data: formData,
            //Options to tell jQuery not to process data or worry about content-type.
            cache: false,
            headers: {'X-CSRF-TOKEN': csrfHeaderValue},
            success: function (returndata) {
                //$scope.uploadProgress="Complete";
                alert("Upload Complete");
            },
            contentType: false,
            processData: false
        });
        /*
         return $http({
         method  : 'POST',
         data: formData,
         
         url     : '/HFW/servlet/fileUpload/'+accountId,
         //data    : JSON.stringify(data),  // pass in data as strings
         headers : { 'Content-Type': undefined  }  // set the headers so angular passing info as form data (not request payload)
         });
         */
    };

    return RegistryService;
});

hfwApp.factory("ReportService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var ReportService = {};

    ReportService.getReport = function (reportType) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/reports/' + reportType
        });
    };
    ReportService.getReportForPeriodForAccount = function (accountId, reportType, reportPeriod) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/reports/' + accountId + '/' + reportType + "/" + reportPeriod
        });
    };

    ReportService.getReportForCustomPeriod = function (reportType, startDate, endDate) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/reports/' + reportType + "/" + startDate + "/" + endDate
        });
    };


    return ReportService;
});

hfwApp.factory("CategoryLookupService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var CategoryLookupService = {};

    CategoryLookupService.lookup = function (lookupValue) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/category/lookup/' + lookupValue
        });
    };


    return CategoryLookupService;
});

hfwApp.factory("NameLookupService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var NameLookupService = {};

    NameLookupService.lookup = function (lookupValue) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/name/lookup/' + lookupValue
        });
    };


    return NameLookupService;
});

hfwApp.factory("DateService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var DateService = {};

    DateService.lookupCurrent = function () {
        return $http({
            method: 'GET',
            async: false,
            url: '/services/api/v1/date/current'
        });
    };

    DateService.lookupCurrentYear = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/date/current/year'
        });
    };


    DateService.lookupCurrentMonth = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/date/current/month'
        });
    };
    DateService.convertJSDateToTextDate = function(date){
      var txnYear = date.getFullYear();
      var txnMonth = date.getMonth();
      if (txnMonth == 0) {
          txnMonth = "01";
      } else if(txnMonth == 1) {
          txnMonth = "02";
      } else if(txnMonth == 2) {
          txnMonth = "03";
      } else if(txnMonth == 3) {
          txnMonth = "04";
      } else if(txnMonth == 4) {
          txnMonth = "05";
      } else if(txnMonth == 5) {
          txnMonth = "06";
      } else if(txnMonth == 6) {
          txnMonth = "07";
      } else if(txnMonth == 7) {
          txnMonth = "08";
      } else if(txnMonth == 8) {
          txnMonth = "09";
      } else if(txnMonth == 9) {
          txnMonth = "10";
      } else if(txnMonth == 10) {
          txnMonth = "11";
      } else if(txnMonth == 11) {
          txnMonth = "12";
      }
      var txnDate = date.getDate()+"";
      if (txnDate.length < 2) {
          txnDate = "0"+txnDate;
      }
      return txnYear + "-" + txnMonth + "-" + txnDate;
    };
    return DateService;
});


hfwApp.factory("BudgetService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var BudgetService = {};

    BudgetService.getBudgets = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/budgets/search/all'
        });
    };
    BudgetService.saveBudget = function (budget) {
        delete budget.$$hashKey;
        angular.forEach(budget.incomeItems, function (value, key) {
            console.log(value);
            delete value.$$hashKey;

            //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        });
        angular.forEach(budget.expenseItems, function (value, key) {
            console.log(value);
            delete value.$$hashKey;

            //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        });
        angular.forEach(budget.transferItems, function (value, key) {
            console.log(value);
            delete value.$$hashKey;

            //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        });
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/budgets/save',
            data: JSON.stringify(budget), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    BudgetService.validateTransaction = function (transaction) {
        delete transaction.$$hashKey;
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/validate',
            data: JSON.stringify(transaction), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    return BudgetService;
});


hfwApp.factory("ScheduledTransactionService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var ScheduledTransactionService = {};

    ScheduledTransactionService.getUpcomingScheduledTransactionsForAccount = function (id) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/schedule/get/upcoming/' + id
        });
    };
    ScheduledTransactionService.getUpcomingScheduledTransactionsForAccountForDate = function (id,theDate) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/schedule/get/upcoming/' + id + '/' + theDate
        });
    };
    

    ScheduledTransactionService.getOriginalScheduledTransactionsForAccount = function (id) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/schedule/get/scheduled/' + id
        });
    };

    ScheduledTransactionService.getTransaction = function (id) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/schedule/byid/' + id
        });
    };

    
    ScheduledTransactionService.skipTransaction = function (txnid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/schedule/skip/'+txnid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    ScheduledTransactionService.payTransaction = function (txnid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/schedule/pay/'+txnid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    ScheduledTransactionService.saveTransaction = function (transaction) {
        delete transaction.$$hashKey;
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/schedule/save',
            data: JSON.stringify(transaction), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    ScheduledTransactionService.validateTransaction = function (transaction) {
        delete transaction.$$hashKey;
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        //return $http({
        //    method: 'POST',
        //    url: '/HFW/services/api/v1/register/validate',
        //    data: JSON.stringify(transaction), // pass in data as strings
        //    headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        //});
    };
    

    return ScheduledTransactionService;
});

hfwApp.factory("TransactionStatusLookupService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var TransactionStatusLookupService = {};

    TransactionStatusLookupService.getStatus = function (status_code) {
        if (status_code == 'k') {
            return "Skipped";
        } else if (status_code == 'p') {
            return "Paid";
        } else if (status_code == 'p') {
            return "Paid";
        } else if (status_code == 'v') {
            return "Void";
        } else if (status_code == 'c') {
            return "Cleared";
        } else if (status_code == 'x') {
            return "None";
        } else if (status_code == 'a') {
            return "Accepted";
        } else if (status_code == 'd') {
            return "Dismissed";
        } else if (status_code == 'i') {
            return "Imported";
        }
    };
    

    return TransactionStatusLookupService;
});

hfwApp.factory("NotificationService", function ($http) {
    var NotificationService = {};

    NotificationService.getNotificationsByStatus = function (status) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/notifications/getByStatus/'+status
        });
    };
    
    NotificationService.getNotificationCountByStatus = function (status) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/notifications/getCountByStatus/'+status
        });
    };    

    NotificationService.markRead = function (notificationId) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/notifications/markRead/'+notificationId,
            //data: JSON.stringify(account), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        })
    };

    return NotificationService;
});

