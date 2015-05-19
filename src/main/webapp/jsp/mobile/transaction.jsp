<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

    <head>

        <style>

        </style>
    </head>
    <body >
        <script src="/HFW/script/app/transaction/app.js"></script>
        <script src="/HFW/script/app/services.js"></script>

        <div ng-app="HFWApp" ng-controller="transactionController" id="main">
            <input type="hidden" ng-model="accountId" ng-init="accountId='<c:out value="${account}"/>'">

            <input type="hidden" ng-model="registryTransactionFormData.primaryAccount">
            <input type="hidden" ng-model="registryTransactionFormData.id">
            Date:<input type="text" ng-model="registryTransactionFormData.txnDate"><br>
            Payee Name:<input typ="text" ng-model="registryTransactionFormData.payee"><br>
            Transaction Amount:<input typ="text" size="5" ng-model="registryTransactionFormData.txnAmount"><input type="checkbox" value="true" ng-model="registryTransactionFormData.credit">Is Credit?<br>

            Category <a ng-click="addSplit()">Add Split</a><br/>
            <div id="categorySplits">
                <div class="categorySplit">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[0].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[0].txnAmount"><br></div>
                <div class="categorySplit">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[1].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[1].txnAmount"><br></div>
                <div class="categorySplit">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[2].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[2].txnAmount"><br></div>
                <div class="categorySplit">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[3].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[3].txnAmount"><br></div>
                <div class="categorySplit hidden">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[4].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[4].txnAmount"><br></div>
                <div class="categorySplit hidden">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[5].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[5].txnAmount"><br></div>
                <div class="categorySplit hidden">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[6].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[6].txnAmount"><br></div>
                <div class="categorySplit hidden">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[7].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[7].txnAmount"><br></div>
                <div class="categorySplit hidden">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[8].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[8].txnAmount"><br></div>
                <div class="categorySplit hidden">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[9].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[9].txnAmount"><br></div>
                <div  ng-hide="hideTxnRetrievedCategories">
                    <ul ng-repeat="category in retrievedCategories">
                        <li class="retrievedCategoryItem"><a class="retrievedCategoryItem" ng-click="selectRetrievedCategory(category)">{{category}}</a></li>
                    </ul>
                </div>
                Status:<input type="radio" name="statusTxt" value="c" ng-model="registryTransactionFormData.statusTxt">Cleared
                <input type="radio" name="statusTxt" value="v" ng-model="registryTransactionFormData.statusTxt">Void
                <input type="radio" name="statusTxt" value="x" ng-model="registryTransactionFormData.statusTxt">None
                <br/>
                <button ng-click="addRegistryTransaction()">Save</button>
                <button ng-click="clickNewTransactionCancel()">Cancel</button>

            </div>
    </body>
</html>