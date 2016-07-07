angular.module('BudgetServiceModule',[]);
angular.module('BudgetServiceModule').service("BudgetService", function ($http) {
    //var users = ["Peter", "Daniel", "Nina"]
    var BudgetService = {};

    BudgetService.getBudgets = function () {
        return $http({
            method: 'GET',
            url: '/services/api/v1/budgets/search/all'
        });
    };
    BudgetService.saveBudget = function (budget) {
        delete budget.$$hashKey;
        angular.forEach(budget.incomeItems, function (value, key) {
            console.log(value);
            delete value.$$hashKey;

            //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        });
        angular.forEach(budget.expenseItems, function (value, key) {
            console.log(value);
            delete value.$$hashKey;

            //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        });
        angular.forEach(budget.transferItems, function (value, key) {
            console.log(value);
            delete value.$$hashKey;

            //this.push(key + ': ' + value);
            //if (value.category != '') {
            //    $scope.registryTransactionFormCategorySplits.push(value);    
            //}

        });
        var csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        var csrfHeaderValue = $("meta[name='_csrf']").attr("content");
        return $http({
            method: 'POST',
            url: '/services/api/v1/budgets/save',
            data: JSON.stringify(budget), // pass in data as strings
            headers: {'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfHeaderValue}  // set the headers so angular passing info as form data (not request payload)
        });
    };
    BudgetService.validateTransaction = function (transaction) {
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
    return BudgetService;
});
