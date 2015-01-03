var hfwApp = angular.module('HFWApp', []);




hfwApp.controller('dashboardController', function ($scope,$http,AccountService,RegistryService,ReportService) {
    $scope.accountFormData = {};
    $scope.registryTransactions={}; 
    $scope.registryTransactionFormData={};
    $scope.reportControl = {};
    $scope.pieChart=null;
    $scope.registryTransactionFormCategorySplits=[];
    
    $scope.selectedAccount = null;
    $scope.showAccountModal = false;
    $scope.showTransactionModal = false;
    //$http.get("/HFW/services/api/v1/accounts/search/all")
    //.success(function(response) {$scope.accounts = response;});    
   $scope.checking_accounts = [];
   $scope.savings_accounts = [];
   $scope.creditcard_accounts = [];
   $scope.investment_accounts = [];
   $scope.retirement_accounts = [];
   $scope.debt_accounts = [];
   $scope.asset_accounts = [];
   $scope.other_accounts=[];


   AccountService.getAccounts().success(function(response) {
       angular.forEach(response, function(value, key) {
           console.log(value); 
           if (value.accountType == 'Checking') {
               $scope.checking_accounts.push(value);
           } else if (value.accountType == 'Savings') {
               $scope.savings_accounts.push(value);
           } else {
               $scope.other_accounts.push(value);
           }
           //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        });
       $scope.accounts=response;
   });
        
  
    $scope.clickGoButton = function(x) {
        console.log(x.id);
        $scope.selectedAccount = x;
        $scope.registryTransactionFormData.primaryAccount=x.id;
        $scope.$emit('showRegisterTransactions', x);
        this.getTransactionsForAccount(x.id);
        this.showRegistryTab();
    };

    $scope.clickEditAccount = function(x) {
        console.log(x.id);
        //$scope.selectedAccount = x;
        $scope.accountFormData = x;
        $scope.showAccountModal=true;
        //$scope.registryTransactionFormData.primaryAccount=x.id;
        //$scope.$emit('showRegisterTransactions', x);
        //this.getTransactionsForAccount(x.id);
    };

    $scope.showNewAccount = function(x) {
        $scope.showAccountModal=true;
        //$("#accountDetailsForm").show();
    };

    $scope.clickNewAccountCancel = function(x) {
        $scope.showAccountModal=false;
        //$("#accountDetailsForm").hide();
    };
    
    $scope.showNewTransaction = function(x) {
        $scope.registryTransactionFormData = {};
        $scope.registryTransactionFormCategorySplits = [];
        $scope.showTransactionModal=true;
        //$("#transactionDetailsForm").show();
    };
    
    $scope.showTransactionForm = function(x) {
        console.log(x);
        $scope.registryTransactionFormData = x;
        $scope.registryTransactionFormCategorySplits = [];
        angular.forEach(x.categorySplits, function(value, key) {
            //this.push(key + ': ' + value);
            if (value.category != '') {
                $scope.registryTransactionFormCategorySplits.push(value);    
            }

        });
        
        
        $scope.showTransactionModal=true;
    };
    
    $scope.clickNewTransactionCancel=function(x) {
        $scope.showTransactionModal=false;
        //$("#transactionDetailsForm").hide();
    }
    
    $scope.showRegistryTab=function() {
        $("#accountTransactionList").show();
        $("#accountReports").hide();
        $("#accountOnlineFunctions").hide();
        //$scope.showTransactionModal=false;
        //$("#transactionDetailsForm").hide();
    }
    
    $scope.addSplit=function() {
        console.log("clicked add");
        
    }
    
    $scope.showReportTab=function() {
        if ($scope.pieChart) {
            $scope.pieChart.destroy();
        }
        $scope.reportControl = {};
        $("#accountTransactionList").hide();
        $("#accountReports").show();
        $("#accountOnlineFunctions").hide();
        plot1=null;
        $("#pie1").html();
        //$scope.showTransactionModal=false;
        //$("#transactionDetailsForm").hide();
    }
    $scope.showOnlineTab=function(x) {
        $("#accountTransactionList").hide();   
        $("#accountReports").hide();
        $("#accountOnlineFunctions").show();
        //$scope.showTransactionModal=false;
        //$("#transactionDetailsForm").hide();
    }
    
    $scope.renderReport = function(plotData) {
        if ($scope.pieChart) {
            $scope.pieChart.destroy();
        }
        $scope.pieChart=$.jqplot('pie1', [plotData], {
        gridPadding: {top:0, bottom:38, left:0, right:0},
        seriesDefaults:{
            renderer:$.jqplot.PieRenderer, 
            trendline:{ show:false }, 
            rendererOptions: { padding: 8, showDataLabels: true }
        },
        legend:{
            show:true, 
            placement: 'inside', 
            rendererOptions: {
                
            }, 
            location:'e',
            marginTop: '15px'
        }       
    });
    }
    $scope.doReport=function (x) {
        console.log($scope.selectedAccount.id+":"+$scope.reportControl.reportType + ":" + $scope.reportControl.reportPeriod);
        //var plotData = [[['Income:Other',25],['Income:Net Pay',14],['c',7]]]
        
        if ($scope.reportControl.reportType == undefined) {
            $scope.reportControl.reportType = "INCOME";
        }
        if ($scope.reportControl.reportPeriod== undefined) {
            $scope.reportControl.reportPeriod = "currentMonth";
        }
        ReportService.getReportForPeriodForAccount($scope.selectedAccount.id,$scope.reportControl.reportType,$scope.reportControl.reportPeriod).success(function(response) {
            //parse the data into jqplot data format
            var plotData = [];
            //call the renderReport with the data
            angular.forEach(response.dataPoints, function(value, key) {
                var x = [];
                x.push(value.name);
                x.push(value.value);
                plotData.push(x);
                

            });
            $scope.renderReport(plotData);
        });
        
        
    }
    
    $scope.addAccount = function() {
	
        AccountService.saveAccount($scope.accountFormData).success(function(response) {
             $scope.accounts.push(response);
            
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
    
    
    
    $scope.getTransactionsForAccount = function(accountId) {
        RegistryService.getRegistryForAccount(accountId).success(function(response) {
       $scope.registryTransactions = response;
   });
   /*
        $http.get("/HFW/services/api/v1/register/get/all/"+accountId)
    .success(function(response) {$scope.registryTransactions = response;}); 
    */
        };
    
    $scope.addRegistryTransaction = function() {
        //get the splits and turn it into a better list
        var newCategories = [];
       $scope.registryTransactionFormData.primaryAccount = $scope.selectedAccount.id;
        for (var i=0;i<10;i++) {
            if ($scope.registryTransactionFormCategorySplits[i] != undefined) {
                var categorySplit = new Object();
                
                var categoryEntered = $scope.registryTransactionFormCategorySplits[i].category;
                var txnAmountEntered = $scope.registryTransactionFormCategorySplits[i].txnAmount;
                categorySplit.category = categoryEntered;
                categorySplit.txnAmount = txnAmountEntered;
                newCategories.push(categorySplit);
                //console.log(categoryEntered + ":"+txnAmountEntered);    
            }
        }
            //console.log(newCategories);
        
        $scope.registryTransactionFormData.categorySplits = newCategories;
       
        console.log($scope.registryTransactionFormData); 
        if ($scope.registryTransactionFormData.id == undefined){
            RegistryService.saveTransaction($scope.registryTransactionFormData).success(function(response) {
                $scope.registryTransactions.push(response);
            $scope.showTransactionModal=false;
                console.log(response);
            });
        } else {
            RegistryService.saveTransaction($scope.registryTransactionFormData).success(function(response) {
               $scope.showTransactionModal=false;
                console.log(response);
            });
            
        } 
        
       
 /*
	$http({
        method  : 'POST',
        url     : '/HFW/services/api/v1/register/save',
        data    : JSON.stringify($scope.registryTransactionFormData),  // pass in data as strings
        headers : { 'Content-Type': 'application/json'  }  // set the headers so angular passing info as form data (not request payload)
            })
        .success(function(data) {
            
            $scope.registryTransactions.push(data);
            
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
      replace:true,
      scope:true,
      link: function postLink(scope, element, attrs) {
        scope.title = attrs.title;

        scope.$watch(attrs.visible, function(value){
          if(value == true)
            $(element).modal('show');
          else
            $(element).modal('hide');
        });

        $(element).on('shown.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = true;
          });
        });

        $(element).on('hidden.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = false;
          });
        });
      }
    };
  });

    
