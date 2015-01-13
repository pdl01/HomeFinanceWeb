var hfwApp = angular.module('HFWBudgetApp', []);




hfwApp.controller('budgetController', function ($scope, $http, BudgetService) {
    $scope.budgets = [];
    $scope.selectedBudget = {};
    $scope.budgetFormData = {};
    $scope.budgetItemFormData = {};

    $scope.budgetItemMode = "income";
    $scope.budgetItemAction = "";
    $scope.budgetItemEditIndex = -1;
    $scope.incomeBudgetItems = [];
    $scope.expenseBudgetItems = [];
    $scope.transferBudgetItems = [];

    $scope.showBudgetDetailsModal = false;
    $scope.showBudgetItemModal = false;

    $scope.incomeTotal = 0;
    $scope.expenseTotal = 0;
    $scope.transferTotal = 0;
    $scope.balanceTotal = 0;

    $scope.$watch('selectedBudget', function (oldValue, newValue) {
        //console.log(oldValue, newValue);
        $scope.calcBudgetTotals();

    })

    BudgetService.getBudgets().success(function (response) {
        angular.forEach(response, function (value, key) {
            $scope.budgets.push(value);
        });
    });
    $scope.calcBudgetTotals = function () {
        $scope.incomeTotal = 0.0;
        $scope.expenseTotal = 0;
        $scope.transferTotal = 0;
        $scope.balanceTotal = 0;
        console.log($scope.incomeTotal);
        if ($scope.selectedBudget == undefined) {
            return;
        }
        if ($scope.selectedBudget.incomeItems == undefined) {
            return;
        }
        angular.forEach($scope.selectedBudget.incomeItems, function (value, key) {
            console.log(value.category + ":" + value.txnAmount);
            $scope.incomeTotal = $scope.incomeTotal + parseFloat(value.txnAmount);
        });
        console.log($scope.incomeTotal);
        angular.forEach($scope.selectedBudget.expenseItems, function (value, key) {

            $scope.expenseTotal = $scope.expenseTotal + parseFloat(value.txnAmount);
        });

        angular.forEach($scope.selectedBudget.transferItems, function (value, key) {
            $scope.transferTotal = $scope.transferTotal + parseFloat(value.txnAmount);
        });

        $scope.balanceTotal = $scope.incomeTotal - $scope.expenseTotal - $scope.transferTotal;

    };

    $scope.showNewBudget = function (x) {
        $scope.showBudgetDetailsModal = true;
        //$("#accountDetailsForm").show();
    };
    $scope.clickBudgetFormCancel = function (x) {
        $scope.showBudgetDetailsModal = false;
        //$("#accountDetailsForm").hide();
    };
    $scope.addItem = function (type, x) {
        $scope.budgetItemMode = type;
        $scope.budgetItemAction = "add";
        $scope.showBudgetItemModal = true;
        //$("#accountDetailsForm").show();
    };
    $scope.editItem = function (index, type, x) {
        $scope.budgetItemEditIndex = index;
        $scope.budgetItemMode = type;
        $scope.budgetItemAction = "edit";

        $scope.budgetItemFormData.category = x.category;
        $scope.budgetItemFormData.txnAmount = x.txnAmount;
        $scope.showBudgetItemModal = true;
        //$("#accountDetailsForm").show();
    };
    $scope.deleteItem = function (index, type, x) {
        $scope.budgetItemMode = type;
        //$scope.showBudgetItemModal = true;
        if (type == 'income') {
            $scope.selectedBudget.incomeItems.splice(index, 1);

        } else if (type == "expense") {
            $scope.selectedBudget.expenseItems.splice(index, 1);

        } else if (type == "transfer") {
            $scope.selectedBudget.transferItems.splice(index, 1);

        }

        BudgetService.saveBudget($scope.selectedBudget).success(function (response) {
            //$scope.budgets.push(response);
            console.log(response);
            $scope.selectedBudget = response;
            $scope.budgetItemFormData = {};
            $scope.showBudgetItemModal = false;
            $scope.calcBudgetTotals();
            console.log(response);
        });
        //$("#accountDetailsForm").show();
    };
    $scope.addnewExpenseItem = function (x) {
        $scope.budgetItemMode = "expense";

        $scope.showBudgetItemModal = true;
        //$("#accountDetailsForm").show();
    };
    $scope.addnewTransferItem = function (x) {
        $scope.budgetItemMode = "transfer";

        $scope.showBudgetItemModal = true;
        //$("#accountDetailsForm").show();
    };
    $scope.clickBudgetItemCancel = function (x) {
        $scope.showBudgetItemModal = false;
        //$("#accountDetailsForm").hide();
    };
    $scope.saveBudgetItem = function (x) {
        var tempBudgetItem = $scope.budgetItemFormData;

        if ($scope.budgetItemAction == 'add') {
            delete tempBudgetItem.$$hashKey;
            if ($scope.budgetItemMode == "income") {
                if ($scope.selectedBudget.incomeItems == undefined) {
                    $scope.selectedBudget.incomeItems = [];
                }
                $scope.selectedBudget.incomeItems.push(tempBudgetItem);
            }

            if ($scope.budgetItemMode == "expense") {
                if ($scope.selectedBudget.expenseItems == undefined) {
                    $scope.selectedBudget.expenseItems = [];

                }
                $scope.selectedBudget.expenseItems.push(tempBudgetItem);
            }
            if ($scope.budgetItemMode == "transfer") {
                if ($scope.selectedBudget.transferItems == undefined) {
                    $scope.selectedBudget.transferItems = [];

                }
                $scope.selectedBudget.transferItems.push(tempBudgetItem);
            }
        } else if ($scope.budgetItemAction == 'edit') {
            if ($scope.budgetItemMode == "income") {
                $scope.selectedBudget.incomeItems[$scope.budgetItemEditIndex].category = tempBudgetItem.category;
                $scope.selectedBudget.incomeItems[$scope.budgetItemEditIndex].txnAmount = tempBudgetItem.txnAmount;
            } else if ($scope.budgetItemMode == "expense") {
                $scope.selectedBudget.expenseItems[$scope.budgetItemEditIndex].category = tempBudgetItem.category;
                $scope.selectedBudget.expenseItems[$scope.budgetItemEditIndex].txnAmount = tempBudgetItem.txnAmount;
            }
            if ($scope.budgetItemMode == "transfer") {
                $scope.selectedBudget.transferItems[$scope.budgetItemEditIndex].category = tempBudgetItem.category;
                $scope.selectedBudget.transferItems[$scope.budgetItemEditIndex].txnAmount = tempBudgetItem.txnAmount;
            }
        }



        BudgetService.saveBudget($scope.selectedBudget).success(function (response) {
            //$scope.budgets.push(response);
            // $scope.budgets = [];
            //BudgetService.getBudgets().success(function (response1) {
            //        angular.forEach(response1, function (value, key) {
            //            $scope.budgets.push(value);
            //    });
            //});
            
            $scope.selectedBudget = response;
            $scope.budgetItemFormData = {};
            $scope.showBudgetItemModal = false;
            $scope.calcBudgetTotals();
            console.log(response);
        });
    }
    $scope.saveBudget = function () {

        BudgetService.saveBudget($scope.budgetFormData).success(function (response) {
            $scope.budgets.push(response);
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

