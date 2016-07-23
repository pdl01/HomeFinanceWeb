angular.module('SettingsServiceModule',[]);

angular.module('SettingsServiceModule').service("SettingsService", function ($http) {
//hfwApp.factory("SettingsService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var SettingsService = {};

    SettingsService.getRepositoryConfig = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/settings/db'
        });
    };
    SettingsService.validateRepositoryConfig = function (settingsData) {
        delete account.$$hashKey;
                var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");

        return $http({
            method: 'POST',
            url: '/HFWservices/api/v1/settings/db/validate',
            data: JSON.stringify(settingsData), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        })
    };
    SettingsService.saveRepositoryConfig = function (settingsData) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");

        return $http({
            method: 'POST',
            url: '/services/api/v1/settings/db',
            data: JSON.stringify(settingsData), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        })
    };
    SettingsService.getBasicSecurityConfig = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/settings/basicsecurity'
        });
    };
    SettingsService.saveBasicSecurityConfig = function (settingsData) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");

        return $http({
            method: 'POST',
            url: '/services/api/v1/settings/basicsecurity',
            data: JSON.stringify(settingsData), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        })
    };

    SettingsService.getThemeConfig = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/settings/theme'
        });
    };
    SettingsService.saveThemeConfig = function (settingsData) {
                var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");

        return $http({
            method: 'POST',
            url: '/services/api/v1/settings/theme',
            data: JSON.stringify(settingsData), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        })
    };
    SettingsService.getAllCategories = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/category/all'
        });
    };


    return SettingsService;
});
