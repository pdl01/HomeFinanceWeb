angular.module('NameLookupServiceModule',[]);

angular.module('NameLookupServiceModule').service("NameLookupService", function ($http) {
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
