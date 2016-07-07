angular.module('DateServiceModule',[]);

angular.module('DateServiceModule').service("DateService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var DateService = {};

    DateService.lookupCurrent = function () {
        return $http({
            method: 'GET',
            async: false,
            url: '/services/api/v1/date/current'
        });
    };

    DateService.lookupCurrentYear = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/date/current/year'
        });
    };


    DateService.lookupCurrentMonth = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/date/current/month'
        });
    };
    DateService.convertJSDateToTextDate = function(date){
      var txnYear = date.getFullYear();
      var txnMonth = date.getMonth();
      if (txnMonth == 0) {
          txnMonth = "01";
      } else if(txnMonth == 1) {
          txnMonth = "02";
      } else if(txnMonth == 2) {
          txnMonth = "03";
      } else if(txnMonth == 3) {
          txnMonth = "04";
      } else if(txnMonth == 4) {
          txnMonth = "05";
      } else if(txnMonth == 5) {
          txnMonth = "06";
      } else if(txnMonth == 6) {
          txnMonth = "07";
      } else if(txnMonth == 7) {
          txnMonth = "08";
      } else if(txnMonth == 8) {
          txnMonth = "09";
      } else if(txnMonth == 9) {
          txnMonth = "10";
      } else if(txnMonth == 10) {
          txnMonth = "11";
      } else if(txnMonth == 11) {
          txnMonth = "12";
      }
      var txnDate = date.getDate()+"";
      if (txnDate.length < 2) {
          txnDate = "0"+txnDate;
      }
      return txnYear + "-" + txnMonth + "-" + txnDate;
    };
    return DateService;
});