angular.module('HFWSettingsApp', ['ui.bootstrap','SettingsServiceModule']);
angular.module('HFWSettingsApp').controller('settingsController', function ($rootScope,$scope,SettingsService) {
//var hfwApp = angular.module('HFWSettingsApp', []);


//hfwApp.controller('settingsController', function ($scope, $http, SettingsService) {
    $scope.repositoryFormData = {};
    $scope.basicSecurityFormData = {};
    $scope.themeFormData = {};
    $scope.categories = [];

    $scope.init = function () {
        $scope.getRepositoryConfig();
        $scope.getThemeConfig();
        $scope.getBasicSecurityConfig();
        $scope.getCategoryInformation();
    };

    $scope.getRepositoryConfig = function () {

        SettingsService.getRepositoryConfig().success(function (response) {
            console.log(response);
            console.log(response.settings.host);
            if (response.settings.host) {
                $scope.repositoryFormData.host = response.settings.host;
            }
            if (response.settings.port) {
                $scope.repositoryFormData.port = response.settings.port;
            }
            if (response.settings.database) {
                $scope.repositoryFormData.database = response.settings.database;
            }
            if (response.settings.username) {
                $scope.repositoryFormData.username = response.settings.username;
            }
            if (response.settings.password) {
                $scope.repositoryFormData.password = response.settings.password;
            }


        });



    };
    $scope.getThemeConfig = function () {
        SettingsService.getThemeConfig().success(function (response) {
            console.log(response);
            //console.log(response.settings.host);
            if (response.settings.theme) {
                $scope.themeFormData.theme = response.settings.theme;
            }
        });        
    };
    
    $scope.getBasicSecurityConfig = function () {
        SettingsService.getBasicSecurityConfig().success(function (response) {
            console.log(response);
            //console.log(response.settings.host);
            if (response.settings.enabled) {
                $scope.basicSecurityFormData.enabled = response.settings.enabled;
            }
            if (response.settings.password) {
                $scope.basicSecurityFormData.password = response.settings.password;
            }
        });        
    };
    
    $scope.saveRepositoryInformation = function () {
        var repoInfo = {};
        repoInfo.typeOfSetting = "db";
        repoInfo.settings = {};
        repoInfo.settings.host = $scope.repositoryFormData.host;
        repoInfo.settings.database = $scope.repositoryFormData.database;
        repoInfo.settings.username = $scope.repositoryFormData.username;
        repoInfo.settings.password = $scope.repositoryFormData.password;
        repoInfo.settings.port = $scope.repositoryFormData.port;
        repoInfo.settings.type=$scope.repositoryFormData.type;
        SettingsService.saveRepositoryConfig(repoInfo).success(function (response) {
            console.log(response);
        });
    };
    $scope.getCategoryInformation = function () {
    
        SettingsService.getAllCategories().success(function (response) {
            $scope.categories = response;
        });
    };
    $scope.saveBasicSecurityInformation = function () {
        var basicSecurityInfo = {};
        basicSecurityInfo.typeOfSetting = "basicSecurity";
        basicSecurityInfo.settings = {};
        basicSecurityInfo.settings.enabled = $scope.basicSecurityFormData.enabled;
        basicSecurityInfo.settings.password = $scope.basicSecurityFormData.password;
        SettingsService.saveBasicSecurityConfig(basicSecurityInfo).success(function (response) {
            console.log(response);
        });
    };

    $scope.saveThemeInformation = function () {
        var themeInfo = {};
        themeInfo.typeOfSetting = "theme";
        themeInfo.settings = {};
        themeInfo.settings.theme = $scope.themeFormData.theme;
        SettingsService.saveThemeConfig(themeInfo).success(function (response) {
            console.log(response);
        });
    };
    
    $scope.init();

});
/*
angular.module('HFWSettingsApp').directive('modal', function () {
    return {
        template: '<div class="modal fade">' +
                '<div class="modal-dialog">' +
                '<div class="modal-content">' +
                '<div class="modal-header">' +
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                '<h4 class="modal-title">{{ title }}</h4>' +
                '</div>' +
                '<div class="modal-body" ng-transclude></div>' +
                '</div>' +
                '</div>' +
                '</div>',
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: true,
        link: function postLink(scope, element, attrs) {
            scope.title = attrs.title;

            scope.$watch(attrs.visible, function (value) {
                if (value == true)
                    $(element).modal('show');
                else
                    $(element).modal('hide');
            });

            $(element).on('shown.bs.modal', function () {
                scope.$apply(function () {
                    scope.$parent[attrs.visible] = true;
                });
            });

            $(element).on('hidden.bs.modal', function () {
                scope.$apply(function () {
                    scope.$parent[attrs.visible] = false;
                });
            });
        }
    };
    
});
*/
