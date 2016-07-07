angular.module('ReportServiceModule',[]);

angular.module('ReportServiceModule').service("ReportService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var ReportService = {};

    ReportService.getReport = function (reportType) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/reports/' + reportType
        });
    };
    ReportService.getReportForPeriodForAccount = function (accountId, reportType, reportPeriod) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/reports/' + accountId + '/' + reportType + "/" + reportPeriod
        });
    };

    ReportService.getReportForCustomPeriod = function (reportType, startDate, endDate) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/reports/' + reportType + "/" + startDate + "/" + endDate
        });
    };


    return ReportService;
});
