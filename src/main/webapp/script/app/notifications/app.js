var hfwApp = angular.module('HFWNotificationsApp', []);


hfwApp.controller('notificationsController', function ($scope, $http, NotificationService) {
    $scope.notifications = []; 
    $scope.showNotificationsModal = false;
});