angular.module('ScheduledTransactionServiceModule',[]);

angular.module('ScheduledTransactionServiceModule').service("ScheduledTransactionService", function ($http) {
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
