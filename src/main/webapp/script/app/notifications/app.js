angular.module('HFWNotificationsApp', ['NotificationServiceModule']);


angular.module('HFWNotificationsApp').controller('notificationsController', function ($scope, $http, NotificationService) {
    $scope.notifications = [];
    $scope.showNotificationsModal = false;
    $scope.txnSortType = 'createdOn';
    $scope.txnSortReverse = false;

    $scope.getAllNotifications = function () {
        $scope.notifications = [];
        NotificationService.getNotificationsByStatus(0).success(function (response) {
            angular.forEach(response, function (value, key) {
                $scope.notifications.push(value);
            });
        });
        NotificationService.getNotificationsByStatus(1).success(function (response) {
            angular.forEach(response, function (value, key) {
                $scope.notifications.push(value);
            });
        });

    };
    $scope.getAllNotifications();
    
});