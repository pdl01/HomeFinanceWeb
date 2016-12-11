angular.module('VersionServiceModule',[]);

angular.module('VersionServiceModule').service("VersionService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var VersionService = {};

    VersionService.getVersions = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/versions/modules'
        });
    };


    return VersionService;
});
