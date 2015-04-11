<!DOCTYPE html>
<html>

    <head>


        <style>


        </style>
    </head>

    <body >
        <script src="/HFW/script/app/accounts/app.js"></script>
        <script src="/HFW/script/app/services.js"></script>
        <div ng-app="HFWApp" ng-controller="accountController" id="main">
            <div id="accountList">
                <div class="accountListHeader">Checking</div>
                <div ng-repeat="x in checking_accounts"  class="list-group-item accountEntry">
                    <div ng-click="clickGoButton(x)">
                        <span>{{ x.name}}</span>
                        <span>{{ x.currentBalance | currency }}</span>
                    </div>

                    <div ><a href="#" ng-click="clickEditAccount(x)">Edit</a></div>
                </div> 
                <div class="accountListHeader">Savings</div>
                <div ng-repeat="x in savings_accounts"  class="list-group-item accountEntry">
                    <div ng-click="clickGoButton(x)">
                        <span>{{ x.name}}</span>
                        <span>{{ x.currentBalance | currency }}</span>
                    </div>

                    <div ><a href="#" ng-click="clickEditAccount(x)">Edit</a></div>
                </div>
                <div class="accountListHeader">Credit Card</div>
                <div ng-repeat="x in creditcard_accounts"  class="list-group-item accountEntry">
                    <div ng-click="clickGoButton(x)">
                        <span>{{ x.name}}</span>
                        <span>{{ x.currentBalance | currency }}</span>
                    </div>

                    <div ><a href="#" ng-click="clickEditAccount(x)">Edit</a></div>
                </div>

                <div class="accountListHeader">Other</div>
                <div ng-repeat="x in other_accounts"  class="list-group-item accountEntry">
                    <div ng-click="clickGoButton(x)">
                        <span>{{ x.name}}</span>
                        <span>{{ x.currentBalance | currency }}</span>
                    </div>

                    <div ><a href="#" ng-click="clickEditAccount(x)">Edit</a></div>
                </div>

            </div>               
        </div>
    </body>
</html>