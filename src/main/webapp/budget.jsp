<!DOCTYPE html>

<html>
    <head>
    </head>
    <body>
  <script src="script/app/budget/app.js"></script>
        <script src="script/app/services.js"></script>
       
        
        <div ng-app="HFWBudgetApp" ng-controller="budgetController" id="budgetMain">
             <div id="appMenu">
                <ul>
                    <li class="menuItem" id="menuItemNewAccount" ng-click="showNewBudget()">New Budget</li>
                </ul>
            </div>
            <div id="budgetSelectForm">
                <select ng-model="selectedBudget" ng-options="item.name for item in budgets">
                    <option value="">Select Budget</option>
                </select>
                <p>Currently selected: {{selectedBudget.id}} </p>
               
            </div>
            <div id="budgetTotals">
                Income Totals: {{ incomeTotal | currency}}<br/>
                Expense Totals: {{ expenseTotal | currency }}<br/>
                Transfer Totals:{{ transferTotal | currency }}<br/>
                Balance: {{ balanceTotal | currency }}<br/>
            </div>
            <div id="incomeSection">Income  <a ng-click="addItem('income')">New Item</a>
                <div id="incomeItemList">

                    <div ng-repeat="x in selectedBudget.incomeItems" ng-click="showBudgetForm(x)" class="list-group-item budgetItem">
                        <span>{{ x.category}}</span>
                        <span>{{ x.txnAmount | currency }}</span>
                        <a ng-click="editItem($index, 'income', x)">Edit</a> | <a ng-click="deleteItem($index, 'income', x)">Remove</a>
                    </div>
                </div>
            </div>
            <div id="expenseSection">Expense <a ng-click="addItem('expense')">New Item</a>
                <div id="expenseItemList">

                    <div ng-repeat="x in selectedBudget.expenseItems" ng-click="showBudgetForm(x)" class="list-group-item budgetItem">
                        <span>{{ x.category}}</span>
                        <span>{{ x.txnAmount | currency }}</span>
                        <a ng-click="editItem($index, 'expense', x)">Edit</a> | <a ng-click="deleteItem($index, 'expense', x)">Remove</a>
                    </div>
                </div>
            </div>
            <div id="transferSection">Transfers <a ng-click="addItem('transfer')">New Item</a>
                <div id="transferItemList">

                    <div ng-repeat="x in selectedBudget.transferItems" ng-click="showBudgetForm(x)" class="list-group-item budgetItem">
                        <span>{{ x.category}}</span>
                        <span>{{ x.txnAmount | currency }}</span>
                        <a ng-click="editItem($index, 'transfer', x)">Edit</a> | <a ng-click="deleteItem($index, 'transfer', x)">Remove</a>
                    </div>
                </div>
            </div>
    <modal title="Budget Item Details" visible="showBudgetItemModal" >
        <div>
            Category:<input type="text" ng-model="budgetItemFormData.category">
            <div  ng-hide="hideBudgetItemRetrievedCategories">
        <ul ng-repeat="category in retrievedCategories">
            <li class="retrievedCategoryItem"><a class="retrievedCategoryItem" ng-click="selectRetrievedCategory(category)">{{category}}</a></li>
           
        </ul>
    </div><br>
            
            Monthly Amount:<input type="text" ng-model="budgetItemFormData.txnAmount"><br>

            <button ng-click="saveBudgetItem()">Save the Item</button>
            <button ng-click="clickBudgetItemCancel()">Cancel</button>
        </div>
    </modal>
    <modal title="Budget Details" visible="showBudgetDetailsModal" >
        <div>
            Name:<input type="text" ng-model="budgetFormData.name"><br>
            Start Date:<input type="text" ng-model="budgetFormData.startingOnDate"><br>
            Based Period:<input type="text" ng-model="budgetFormData.timePeriod"><br/>

            Number of Periods: <input type="text" ngmodel="budgetFormData.numberOfTimePeriods"><br/>
            Active<input type="checkbox" value="true" ng-model="budgetFormData.active"><br/>
            <button ng-click="saveBudget()">Save the Budget</button>
            <button ng-click="clickBudgetFormCancel()">Cancel</button>
        </div>
    </modal>
        </div>

</body>
</html>
