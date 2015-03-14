hfwApp.factory("SettingsService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var SettingsService = {};

    SettingsService.getRepositoryConfig = function () {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/settings/db'
        });
    };
    SettingsService.validateRepositoryConfig = function (settingsData) {
        delete account.$$hashKey;
        return $http({
            method: 'POST',
            url: '/HFWservices/api/v1/settings/db/validate',
            data: JSON.stringify(settingsData), // pass in data as strings
            headers: {'Content-Type': 'application/json'}  // set the headers so angular passing info as form data (not request payload)
        })
    };
    SettingsService.saveRepositoryConfig = function (settingsData) {

        return $http({
            method: 'POST',
            url: '/HFW/services/api/v1/settings/db',
            data: JSON.stringify(settingsData), // pass in data as strings
            headers: {'Content-Type': 'application/json'}  // set the headers so angular passing info as form data (not request payload)
        })
    };
    SettingsService.getBasicSecurityConfig = function () {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/settings/basicsecurity'
        });
    };
    SettingsService.saveBasicSecurityConfig = function (settingsData) {
        return $http({
            method: 'POST',
            url: '/HFW/services/api/v1/settings/basicsecurity',
            data: JSON.stringify(settingsData), // pass in data as strings
            headers: {'Content-Type': 'application/json'}  // set the headers so angular passing info as form data (not request payload)
        })
    };

    SettingsService.getThemeConfig = function () {
        return $http({
            method: 'GET',
            url: '/HFW/services/api/v1/settings/theme'
        });
    };
    SettingsService.saveThemeConfig = function (settingsData) {
        return $http({
            method: 'POST',
            url: '/HFW/services/api/v1/settings/theme',
            data: JSON.stringify(settingsData), // pass in data as strings
            headers: {'Content-Type': 'application/json'}  // set the headers so angular passing info as form data (not request payload)
        })
    };



    return SettingsService;
});
