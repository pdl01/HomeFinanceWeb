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
    ScheduledTransactionService.convertTextDateToJSDate = function(date){
        if (date == undefined || date == null || date == '') {
            return date;
        }
//var txnDate = new Date (date+'T01:00:01Z');
      //the format is year-mm-dd
      var dt1   = parseInt(date.substring(8,10));
      var mon1  = parseInt(date.substring(5,7));
      var yr1   = parseInt(date.substring(0,5));
      var date1 = new Date(yr1, mon1-1, dt1);
      
      //console.log(dt1+":"+mon1+":"+yr1);
        //console.log("output:"+txnDate);
      //txnDate = Date.parse(date);
      //console.log("output1:"+txnDate);
        return date1;
    };
    

    return ScheduledTransactionService;
});
