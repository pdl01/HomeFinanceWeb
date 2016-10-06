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

angular.module('HFWNotificationsApp').directive('modal', function () {
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
