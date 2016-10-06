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

    SettingsService.getLimitedUserSecurityConfig = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/settings/limitedusersecurity'
        });
    };

    SettingsService.saveLimitedUserSecurityConfig = function (settingsData) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");

        return $http({
            method: 'POST',
            url: '/services/api/v1/settings/limitedusersecurity',
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
    SettingsService.uploadData = function (data) {

        var formData = new FormData($('#backupDataUploadForm')[0]);
        console.log(formData);
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
//
//TODO: convert this all to angular form
        //$scope.uploadProgress="Uploading";
        $.ajax({
            url: '/servlet/fileUpload', //Server script to process data
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

    };


    return SettingsService;
});
