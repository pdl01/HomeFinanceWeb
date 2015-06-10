hfwApp.factory("AccountService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var AccountService = {};

    AccountService.getAccounts = function () {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/accounts/search/all'
        });
    };
    AccountService.getAccount = function (id) {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/accounts/getbyId/' + id
        });
    };
    AccountService.deleteAccount = function (id) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");

        return $http({
            method: 'DELETE',
            url: '/HFW/services/api/v1/accounts/delete/' + id,
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
            url: '/HFW/services/api/v1/accounts/save',
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
            url: '/HFW/services/api/v1/register/get/all/' + id
        });
    };
    RegistryService.getRegistryBlockForAccount = function (id, start, number) {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/register/get/' + id + '/' + start + '/' + number
        });
    };
    RegistryService.getRegistryForAccountForDate = function (id, date) {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/register/get/bydate/' + id + '/' + date
        });
    };
    RegistryService.getRegistryForAccountForMonth = function (id, date) {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/register/get/bymonth/' + id + '/' + date
        });
    };
    RegistryService.getPendingTransactionsForAccount = function (id) {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/register/get/pending/' + id
        });
    };
    RegistryService.matchPendingTransactionToExistingTransaction = function (pendingid, existingid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/HFW/services/api/v1/register/pending/match/' + pendingid + '/' + existingid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.dismissPendingTransaction = function (pendingid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/HFW/services/api/v1/register/pending/dismiss/' + pendingid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.acceptPendingTransactionAsNew = function (pendingid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/HFW/services/api/v1/register/pending/acceptasnew/' + pendingid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.getMatchingTransactionsForPendingTransaction = function (transactionId) {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/register/get/matched/' + transactionId
        });
    };
    RegistryService.saveTransaction = function (transaction) {
        delete transaction.$$hashKey;
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/HFW/services/api/v1/register/save',
            data: JSON.stringify(transaction), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    RegistryService.validateTransaction = function (transaction) {
        delete transaction.$$hashKey;
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/HFW/services/api/v1/register/validate',
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
            url: '/HFW/servlet/fileUpload/' + accountId, //Server script to process data
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
            url: '/HFW/services/api/v1/reports/' + reportType
        });
    };
    ReportService.getReportForPeriodForAccount = function (accountId, reportType, reportPeriod) {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/reports/' + accountId + '/' + reportType + "/" + reportPeriod
        });
    };

    ReportService.getReportForCustomPeriod = function (reportType, startDate, endDate) {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/reports/' + reportType + "/" + startDate + "/" + endDate
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
            url: '/HFW/services/api/v1/category/lookup/' + lookupValue
        });
    };


    return CategoryLookupService;
});

hfwApp.factory("DateService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var DateService = {};

    DateService.lookupCurrent = function () {
        return $http({
            method: 'GET',
            async: false,
            url: '/HFW/services/api/v1/date/current'
        });
    };

    DateService.lookupCurrentYear = function () {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/date/current/year'
        });
    };


    DateService.lookupCurrentMonth = function () {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/date/current/month'
        });
    };
    return DateService;
});


hfwApp.factory("BudgetService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var BudgetService = {};

    BudgetService.getBudgets = function () {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/budgets/search/all'
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
            url: '/HFW/services/api/v1/budgets/save',
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
            url: '--/HFW/services/api/v1/register/validate',
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
            url: '/HFW/services/api/v1/schedule/get/upcoming/' + id
        });
    };
    ScheduledTransactionService.getUpcomingScheduledTransactionsForAccountForDate = function (id,theDate) {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/schedule/get/upcoming/' + id + '/' + theDate
        });
    };
    

    ScheduledTransactionService.getOriginalScheduledTransactionsForAccount = function (id) {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/schedule/get/scheduled/' + id
        });
    };
    
    ScheduledTransactionService.skipTransaction = function (pendingid) {
        //var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        //var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        //return $http({
        //    method: 'POST',
        //    url: '/HFW/services/api/v1/register/pending/dismiss/' + pendingid,
        //    headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        //});
    };

    ScheduledTransactionService.saveTransaction = function (transaction) {
        delete transaction.$$hashKey;
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/HFW/services/api/v1/schedule/save',
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

