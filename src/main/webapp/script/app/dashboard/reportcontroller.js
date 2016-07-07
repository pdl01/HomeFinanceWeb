angular.module('HFWApp').controller('accountReportController', function ($rootScope,$scope, $interval, $http, AccountService, RegistryService, ReportService, CategoryLookupService, DateService, ScheduledTransactionService, TransactionStatusLookupService, NotificationService, NameLookupService) {
    $scope.workingAccount = {};
      $scope.reportControl = {};
    $scope.pieChart = null;
    $scope.report_transactions = [];
    $scope.showReportDataModal = false;
    $scope.reportDataPointTxns = [];

    $rootScope.$on('account-selected', function (event, data) {
        $scope.workingAccount = data;
    });

    $scope.selectReportDataPoint = function (reportDataPoint) {
        //txn ids are stored on the data point
        //pull the full transactions for the set.


        RegistryService.getRegistryForAccountForTxnSet($scope.selectedAccount.id, reportDataPoint.transactions).success(function (response) {
            $scope.reportDataPointTxns = [];
            for (idx in response) {
                var txn = response[idx];
                txn.txnDate = RegistryService.convertTextDateToJSDate(txn.txnDate);
                $scope.reportDataPointTxns.push(txn);
            }

//
//$scope.showTransactionModal = false;
            //$scope.$emit('transactionSaved',$scope.selectedAccount.id);
            //console.log(response);
        });
        $scope.showReportDataModal = true;
    };
    $scope.renderReport = function (chartType, plotData) {
        if ($scope.pieChart) {
            $scope.pieChart.destroy();
        }
        if (chartType == 'pie') {
            $scope.pieChart = $.jqplot('pie1', [plotData], {
                gridPadding: {top: 0, bottom: 38, left: 0, right: 0},
                seriesDefaults: {
                    renderer: $.jqplot.PieRenderer,
                    trendline: {show: false},
                    rendererOptions: {padding: 8, showDataLabels: true}
                },
                legend: {
                    show: true,
                    placement: 'inside',
                    rendererOptions: {numberColumns: 2
                    },
                    location: 'e',
                    marginTop: '15px'
                }
            });

        } else if (chartType == 'bar') {

        }


    };
    $scope.doReport = function (x) {
        console.log($scope.selectedAccount.id + ":" + $scope.reportControl.reportType + ":" + $scope.reportControl.reportPeriod);
        //var plotData = [[['Income:Other',25],['Income:Net Pay',14],['c',7]]]

        if ($scope.reportControl.reportType == undefined) {
            $scope.reportControl.reportType = "INCOME";
        }
        if ($scope.reportControl.reportPeriod == undefined) {
            $scope.reportControl.reportPeriod = "currentMonth";
        }
        ReportService.getReportForPeriodForAccount($scope.selectedAccount.id, $scope.reportControl.reportType, $scope.reportControl.reportPeriod).success(function (response) {
            //parse the data into jqplot data format
            var plotData = [];
            chartType = "pie";
            //call the renderReport with the data
            angular.forEach(response.dataPoints, function (value, key) {
                var x = [];
                x.push(value.name);
                x.push(value.value);
                plotData.push(x);
                if (response.reportType == 'DailyBalance') {
                    chartType = "bar";
                }

            });

            $scope.renderReport(chartType, plotData);
            //push the plotDate into report transactions
            //and sort
            $scope.report_transactions = response.dataPoints;
        });


    };


});