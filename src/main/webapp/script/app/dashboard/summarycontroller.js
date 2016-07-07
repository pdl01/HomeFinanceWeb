angular.module('HFWApp').controller('accountSummaryController', function ($rootScope,$scope, $interval, $http, AccountService, RegistryService, ReportService, CategoryLookupService, DateService, ScheduledTransactionService, TransactionStatusLookupService, NotificationService, NameLookupService) {
    $scope.workingAccount = {};
    $rootScope.$on('account-selected', function (event, data) {
        $scope.workingAccount = data;
    });
});