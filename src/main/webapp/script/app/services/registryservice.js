angular.module('RegistryServiceModule',[]);

angular.module('RegistryServiceModule').service("RegistryService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var RegistryService = {};

    RegistryService.getRegistryForAccount = function (id) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/all/' + id
        });
    };
    RegistryService.getRegistryBlockForAccount = function (id, start, number) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/' + id + '/' + start + '/' + number
        });
    };
    RegistryService.getRegistryForAccountForDate = function (id, date) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/bydate/' + id + '/' + date
        });
    };
    RegistryService.getRegistryForAccountForMonth = function (id, date) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/bymonth/' + id + '/' + date
        })
    };
    RegistryService.getRegistryForAccountForTxnSet = function (id, txnIds) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/get/txnset/' + id ,
            data: JSON.stringify(txnIds),
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.getPendingTransactionsForAccount = function (id) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/pending/' + id
        });
    };
    RegistryService.matchPendingTransactionToExistingTransaction = function (pendingid, existingid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/pending/match/' + pendingid + '/' + existingid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.dismissPendingTransaction = function (pendingid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/pending/dismiss/' + pendingid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.acceptPendingTransactionAsNew = function (pendingid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/pending/acceptasnew/' + pendingid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.getMatchingTransactionsForPendingTransaction = function (transactionId) {
        return $http({
            method: 'GET',
            url: '/services/api/v1/register/get/matched/' + transactionId
        });
    };
    RegistryService.acceptAllPendingTransactionAsNew = function (accountid) {
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/pending/acceptallasnew/' + accountid,
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}
        });
    };
    RegistryService.saveTransaction = function (transaction) {
        delete transaction.$$hashKey;
        transaction.txnDate = this.convertJSDateToTextDate(transaction.txnDate);
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/save',
            data: JSON.stringify(transaction), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    RegistryService.convertJSDateToTextDate = function(date){
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

    RegistryService.convertTextDateToJSDate = function(date){
      //var txnDate = new Date (date+'T01:00:01Z');
      //the format is year-mm-dd
      var dt1   = parseInt(date.substring(8,10));
      var mon1  = parseInt(date.substring(5,7));
      var yr1   = parseInt(date.substring(0,5));
      var date1 = new Date(yr1, mon1-1, dt1);
      
      //console.log(dt1+":"+mon1+":"+yr1);
        //console.log("output:"+txnDate);
      //txnDate = Date.parse(date);
      //console.log("output1:"+txnDate);
        return date1;
    };

    
    RegistryService.validateTransaction = function (transaction) {
        delete transaction.$$hashKey;
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/register/validate',
            data: JSON.stringify(transaction), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    RegistryService.uploadData = function (accountId, data) {

        var formData = new FormData($('#onlineDataUploadForm')[0]);
        console.log(formData);
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
//
//TODO: convert this all to angular form
        //$scope.uploadProgress="Uploading";
        $.ajax({
            url: '/servlet/fileUpload/' + accountId, //Server script to process data
            type: 'POST',
            //Ajax events

            // Form data
            data: formData,
            //Options to tell jQuery not to process data or worry about content-type.
            cache: false,
            headers: {'X-CSRF-TOKEN': csrfHeaderValue},
            success: function (returndata) {
                //$scope.uploadProgress="Complete";
                alert("Upload Complete");
            },
            contentType: false,
            processData: false
        });
        /*
         return $http({
         method  : 'POST',
         data: formData,
         
         url     : '/HFW/servlet/fileUpload/'+accountId,
         //data    : JSON.stringify(data),  // pass in data as strings
         headers : { 'Content-Type': undefined  }  // set the headers so angular passing info as form data (not request payload)
         });
         */
    };

    return RegistryService;
});
