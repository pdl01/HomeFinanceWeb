var hfwApp = angular.module('HFWSettingsApp', []);


hfwApp.controller('settingsController', function ($scope, $http, SettingsService) {
    $scope.repositoryFormData = {};
    
    

    SettingsService.getRepositoryConfig().success(function (response) {
        console.log(response);
        console.log(response.settings.host);
        if (response.settings.host) {
            $scope.repositoryFormData.host=response.settings.host;    
        }
        if (response.settings.port) {
            $scope.repositoryFormData.port=response.settings.port;    
        }
        if (response.settings.database) {
            $scope.repositoryFormData.database=response.settings.database;    
        }
        if (response.settings.username) {
            $scope.repositoryFormData.username=response.settings.username;    
        }
        if (response.settings.password) {
            $scope.repositoryFormData.password=response.settings.password;    
        }
        
        //angular.forEach(response, function (value, key) {
            //console.log(value);
          //  $scope.repositoryFormData = value;

            //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        //});

    });
    
    $scope.saveRepositoryInformation = function () {
        var repoInfo = {};
        repoInfo.typeOfSetting = "db";
        repoInfo.settings={};
        repoInfo.settings.host=$scope.repositoryFormData.host;
        repoInfo.settings.database=$scope.repositoryFormData.database;
        repoInfo.settings.username=$scope.repositoryFormData.username;
        repoInfo.settings.password=$scope.repositoryFormData.password;
        repoInfo.settings.port=$scope.repositoryFormData.port;
        SettingsService.saveRepositoryConfig(repoInfo).success(function (response) {
           console.log(response); 
        });
    };
   
    $scope.saveBudget = function () {

        BudgetService.saveBudget($scope.budgetFormData).success(function (response) {
            $scope.budgets.push(response);

            console.log(response);
        });
        /*
         $http({
         method  : 'POST',
         url     : '/HFW/services/api/v1/accounts/save',
         data    : JSON.stringify($scope.accountFormData),  // pass in data as strings
         headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
         })
         .success(function(data) {
         
         $scope.accounts.push(data);
         
         console.log(data);
         
         });
         */
    };
});

hfwApp.directive('modal', function () {
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

