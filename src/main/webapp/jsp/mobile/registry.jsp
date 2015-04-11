<!DOCTYPE html>
<html>

    <head>

        <style>
            
        </style>
    </head>
    <body >
        <script src="/HFW/script/app/registry/app.js"></script>
        <script src="/HFW/script/app/services.js"></script>
            
        <div ng-app="HFWApp" ng-controller="registryController" id="main">
            <div><button ng-click="clickNewTransactionButton(accountId)">New</button> Year:<select name="selectRegisterYear" ng-model="txnDateControl.year">
                            <option value="2015">2015</option>
                            <option value="2014">2014</option>
                            <option value="2013">2013</option>
                            <option value="2012">2012</option>
                            <option value="2011">2011</option>
                            <option value="2010">2010</option>
                            <option value="2009">2009</option>
                            <option value="2008">2008</option>                             
                        </select>
                        Month:<select name="selectRegisterMonth" ng-model="txnDateControl.month">
                            <option value="01">01</option>
                            <option value="02">02</option>
                            <option value="03">03</option>
                            <option value="04">04</option>
                            <option value="05">05</option>
                            <option value="06">06</option>
                            <option value="07">07</option>
                            <option value="08">08</option>
                            <option value="09">09</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                        </select>
                        <button ng-click="getTransactionsForMonth(accountId)">Go</button>
                    </div>    

            
            <div id="accountTransactionList">
                   
                        <div ng-repeat="x in registryTransactions" class="list-group-item registryTransaction">
                            <span>{{ x.txnDate }}</span>
                            <span>{{ x.payee}}</span>
                            <span>{{ x.txnAmount | currency }}</span>
                        </div>
                  

                </div>

            </div>

    </body>
</html>