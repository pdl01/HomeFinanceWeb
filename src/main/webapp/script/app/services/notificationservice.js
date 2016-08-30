angular.module('NotificationServiceModule',[]);

angular.module('NotificationServiceModule').service('NotificationService', function ($http) {
    var NotificationService = {};

    NotificationService.getNotificationsByStatus = function (status) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/notifications/getByStatus/'+status
        });
    };
    
    NotificationService.getNotificationCountByStatus = function (status) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/notifications/getCountByStatus/'+status
        });
    };    

    NotificationService.markRead = function (notificationId) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/notifications/markRead/'+notificationId,
            //data: JSON.stringify(account), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        })
    };

    return NotificationService;
});
