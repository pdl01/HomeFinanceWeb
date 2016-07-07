angular.module('CategoryLookupServiceModule',[]);

angular.module('CategoryLookupServiceModule').service("CategoryLookupService", function ($http) {
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
