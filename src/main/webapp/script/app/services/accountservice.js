angular.module('AccountServiceModule',[]);
angular.module('AccountServiceModule').factory("AccountService", function ($http) {
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
