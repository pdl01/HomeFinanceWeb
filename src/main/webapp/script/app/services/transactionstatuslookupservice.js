angular.module('TransactionStatusLookupServiceModule',[]);

angular.module('TransactionStatusLookupServiceModule').service("TransactionStatusLookupService", function ($http) {
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
