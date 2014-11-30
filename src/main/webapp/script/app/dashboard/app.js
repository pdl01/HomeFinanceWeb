var hfwApp = angular.module('HFWApp', []);




hfwApp.controller('dashboardController', function ($scope,$http,AccountService,RegistryService) {
    $scope.accountFormData = {};
    $scope.registryTransactions={}; 
    $scope.registryTransactionFormData={}; 
    $scope.selectedAccount = null;
    $scope.showAccountModal = false;
    $scope.showTransactionModal = false;
    //$http.get("/HFW/services/api/v1/accounts/search/all")
    //.success(function(response) {$scope.accounts = response;});    
   $scope.accounts = {};
   AccountService.getAccounts().success(function(response) {
       $scope.accounts=response;
   });
        
  
    $scope.clickGoButton = function(x) {
        console.log(x.id);
        this.selectedAccount = x;
        $scope.registryTransactionFormData.primaryAccount=x.id;
        $scope.$emit('showRegisterTransactions', x);
        this.getTransactionsForAccount(x.id);
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
        $scope.showTransactionModal=true;
        //$("#transactionDetailsForm").show();
    };
    $scope.clickNewTransactionCancel=function(x) {
        $scope.showTransactionModal=false;
        //$("#transactionDetailsForm").hide();
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
        RegistryService.saveTransaction($scope.registryTransactionFormData).success(function(response) {
        $scope.registryTransactions.push(response);
            
            console.log(response);
   });
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


