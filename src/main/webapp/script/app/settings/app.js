angular.module('HFWSettingsApp', ['ui.bootstrap','SettingsServiceModule']);
angular.module('HFWSettingsApp').controller('settingsController', function ($rootScope,$scope,SettingsService) {
//var hfwApp = angular.module('HFWSettingsApp', []);


//hfwApp.controller('settingsController', function ($scope, $http, SettingsService) {
    $scope.repositoryFormData = {};
    $scope.basicSecurityFormData = {};
    $scope.themeFormData = {};
    $scope.categories = [];
    $scope.users = [];
    $scope.limitedSecurityUserData = {};
    $scope.emailConfigurationData = {};
    $scope.testEmailAddress = {};
    $scope.testEmailAddress.emailAddress="user@local";
    $scope.importFormShown = false;
    
    $scope.init = function () {
        $scope.getRepositoryConfig();
        $scope.getThemeConfig();
        $scope.getBasicSecurityConfig();
        $scope.getCategoryInformation();
        $scope.getLimitedSecurityUsers();
        $scope.getEmailConfig();
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
    $scope.getLimitedSecurityUsers = function() {
        $scope.users = [];
        SettingsService.getLimitedUserSecurityConfig().success(function (response) {
            console.log(response);
            $scope.limitedSecurityUserData = response.settings;
            /*
            var user={};
            if (response.settings['user1.name'] != undefined){
                user.username = response.settings['user1.name'];
                user.password = response.settings['user1.password'];
            } else {
            
                user.username="";
                user.password="";
            }

            $scope.users.push(user);
            
            user={};
            if (response.settings['user2.name'] != undefined){
                user.username = response.settings['user2.name'];
                user.password = response.settings['user2.password'];
            } else {
            
                user.username="";
                user.password="";
            }
            $scope.users.push(user);

            user={};
            if (response.settings['user3.name'] != undefined){
                user.username = response.settings['user3.name'];
                user.password = response.settings['user3.password'];
            } else {
            
                user.username="";
                user.password="";
            }
            $scope.users.push(user);
            */
        });        
        
    };
    
    $scope.saveLimitedUserSecurityConfig = function () {
        console.log($scope.limitedSecurityUserData);
        var userSecurityData = {};
        userSecurityData.typeOfSetting = "limitedusersecurity";
        userSecurityData.settings = $scope.limitedSecurityUserData;
        SettingsService.saveLimitedUserSecurityConfig(userSecurityData).success(function (response) {
            console.log(response);
        });
        
        /*
        //normalize the users
        var tempUsers = [];
        if ($scope.users[0] != undefined && $scope.users[0].username != '') {
            tempUsers.push($scope.users[0]);
        }
        if ($scope.users[1] != undefined && $scope.users[1].username != '') {
            tempUsers.push($scope.users[1]);
        }
        if ($scope.users[2] != undefined && $scope.users[2].username != '') {
            tempUsers.push($scope.users[2]);
        }
        
        
        var userInfo = {};
        if (tempUsers[0] != undefined) {
            userInfo['user1.name'] = tempUsers[0].username;
            userInfo['user1.password'] = tempUsers[0].password;
        }
        if (tempUsers[1] != undefined) {
            userInfo['user2.name'] = tempUsers[1].username;
            userInfo['user2.password'] = tempUsers[1].password;
        }
        if (tempUsers[2] != undefined) {
            userInfo['user3.name'] = tempUsers[2].username;
            userInfo['user3.password'] = tempUsers[2].password;
        }
        
        
        console.log(userInfo);
        */
       
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
    
    $scope.getEmailConfig = function() {
        SettingsService.getEmailConfig().success(function (response) {
            console.log(response);
            //console.log(response.settings.host);
            if (response.settings) {
                $scope.emailConfigurationData = response.settings;
            }
        });        
    
    };
    
    $scope.saveEmailConfig = function() {
        var emailConfigData = {};
        emailConfigData.typeOfSetting = "email";
        emailConfigData.settings = $scope.emailConfigurationData;
        SettingsService.saveEmailConfig(emailConfigData).success(function(response) {
            console.log(response);
        });
    };
    
    $scope.validateEmailConfig = function() {
        var emailConfigData = {};
        emailConfigData.typeOfSetting = "email";
        emailConfigData.settings = $scope.emailConfigurationData;
        SettingsService.validateEmailConfig(emailConfigData).success(function(response) {
            console.log(response);
        });        
    };
    
    $scope.sendTestEmail = function() {
        //var testEmailData = {};
        $scope.testEmailAddress.emailAddress;
        SettingsService.sendTestEmail($scope.testEmailAddress.emailAddress).success(function(response) {
            console.log(response);
        });
    }
    
    $scope.showImportForm = function() {
        $scope.importFormShown = true;
    };
    $scope.selectImportFile = function() {
        $("#file").click();
    };
    
    $scope.uploadBackupFile = function() {
        SettingsService.uploadData($('#file'));
    };
    
    $scope.cancelUploadBackupFile = function () {
        $scope.importFormShown = false;
    };
    
    $scope.init();

});

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


