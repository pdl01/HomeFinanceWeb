<!DOCTYPE html>
<html>

    <head>

    </head>

    <body >


        <script src="script/app/dashboard/app.js"></script>
        <script src="script/app/services.js"></script>


        <script class="include" type="text/javascript" src="jqplot.1.0.8r1250.dist/jquery.jqplot.min.js"></script>
        <link class="include" rel="stylesheet" type="text/css" href="jqplot.1.0.8r1250.dist/jquery.jqplot.min.css" />
        <script type="text/javascript" src="jqplot.1.0.8r1250.dist/plugins/jqplot.pieRenderer.min.js"></script>
        <!--
        <div id="header">
            <ul>
                <li class="headerItem"><a href="/HFW">Account Dashboard</a></li>
                <li class="headerItem"><a href="/HFW/budget.html">Budget</a></li>
                <li class="headerItem"><a href="/HFW/settings.html">Settings</a></li>
            </ul>
        </div>
        -->
        <div ng-app="HFWApp" ng-controller="dashboardController" id="main">
            <div id="appMenu">
                <ul>
                    <li class="menuItem" id="menuItemNewAccount" ng-click="showNewAccount()">New Account</li>
                    <li class="menuItem" id="menuItemRegister" ng-click="showNewTransaction()">New Transaction</li>
                </ul>
            </div>
            <div id="appSidebar">
                <div id="accountList">
                    <div>Accounts <a class="refresh" ng-click="refreshAccounts()">&nbsp;&nbsp;&nbsp;&nbsp;</a></div>
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('checking')">Checking</div>
                    <div ng-hide="hideAccounts['checking']" ng-repeat="x in checking_accounts"  id="checkingAccounts" class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                        <div ><a href="#" ng-click="clickEditAccount(x)">Edit</a></div>
                    </div> 
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('savings')">Savings</div>
                    <div ng-hide="hideAccounts['savings']" ng-repeat="x in savings_accounts"  class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                        <div ><a href="#" ng-click="clickEditAccount(x)">Edit</a></div>
                    </div>
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('creditcard')">Credit Card</div>
                    <div ng-hide="hideAccounts['creditcard']" ng-repeat="x in creditcard_accounts"  class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                        <div ><a href="#" ng-click="clickEditAccount(x)">Edit</a></div>
                    </div>                    
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('investment')">Investment</div>
                    <div ng-hide="hideAccounts['investment']" ng-repeat="x in investment_accounts"  class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                        <div ><a href="#" ng-click="clickEditAccount(x)">Edit</a></div>
                    </div>                    
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('retirement')">Retirement</div>
                    <div ng-hide="hideAccounts['retirement']" ng-repeat="x in retirement_accounts"  class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                        <div ><a href="#" ng-click="clickEditAccount(x)">Edit</a></div>
                    </div>
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('other')">Other</div>
                    <div ng-hide="hideAccounts['other']" ng-repeat="x in other_accounts"  class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                        <div ><a href="#" ng-click="clickEditAccount(x)">Edit</a></div>
                    </div>

                </div>
            </div>
            <div id="appMain">
                <div id="appMainTabs">
                    <ul>
                        <li class="tabItem" id="accountMenuItemRegister" ng-click="showRegistryTab()">Register</li>
                        <li class="tabItem" id="accountMenuItemReport" ng-click="showReportTab()" >Report</li>
                        <li class="tabItem" id="accountMenuItemOnline" ng-click="showOnlineTab()">Online</li>
                    </ul>
                </div>
                <div id="accountTransactionList">
                    <div>Year:<select name="selectRegisterYear" ng-model="txnDateControl.year">
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
                        <button ng-click="getTransactionsForMonth(selectedAccount.id)">Go</button>
                    </div>
                    <div ng-repeat="x in registryTransactions" ng-click="showTransactionForm(x)" class="list-group-item registryTransaction">
                        <span>{{ x.txnDate}}</span>
                        <span>{{ x.payee}}</span>
                        <span>{{ x.txnAmount | currency }}</span>
                    </div>
                </div>

                <div id="accountReports">
                    <div id="reportControl">
                        <select name="reportType" id="reportType" ng-model="reportControl.reportType">

                            <option value="INCOME">Income</option>
                            <option value="expensebypayee">Expenses By Payee</option>
                            <option value="expensebycategory">Expenses By Category</option>
                        </select>
                        <select name="reportPeriod" id="reportPeriod" ng-model="reportControl.reportPeriod">

                            <option value="currentMonth">Current Month</option>
                            <option value="previousMonth">Previous Month</option>
                            <option value="currentYear">Current Year</option>
                            <option value="previousYear">Previous Year</option>
                            <option value="customeDates">Custom</option>
                        </select>


                        <button ng-click="doReport(x)">Show Report</button>
                        <a href="/HFW/reports.html">Advanced</a>
                    </div>
                    <div id="reportData">
                        <div id="pie1" style="margin-top:20px; margin-left:20px; width:70%;"></div>




                        <script class="code" type="text/javascript">

                                    var plot1 = "";


                        </script>


                    </div>
                </div>
                <div id="accountOnlineFunctions">

                    online
                    <form enctype="multipart/form-data" id="onlineDataUploadForm">
                        Data:<br>
                        <button ng-click="selectFile()">Upload Your File</button>
                        <input type="hidden" name="accountId" value="{{selectedAccount.id}}">
                        <input type="file" style="display:none" 
                               id="file" name='file' />
                        <button ng-click="addOnlineData()">Submit!</button>
                    </form>
                    <div id="progress">{{uploadProgress}}</div>
                    <div id="pendingTransactionList">
                        <div ng-repeat="x in pendingRegistryTransactions" ng-click="" class="list-group-item registryTransaction">
                            <button ng-click="showOnlineMatchingEditTxnDialog(x)">Accept</button>
                            <button>Dismiss</button>
                            <button ng-click="showOnlineMatchingDialog(x)">Match</button>
                            <span>{{ x.txnDate}}</span>
                            <span>{{ x.payee}}</span>
                            <span>{{ x.txnAmount | currency }}</span>
                        </div>
                    </div>

                </div>

            </div>

            <modal title="Account Details" visible="showAccountModal" >
                <div>
                    Account Name:<input typ="text" ng-model="accountFormData.name"><br>
                    Account Number:<input typ="text" ng-model="accountFormData.accountNumber"><br>
                    Opening Balance:<input typ="text" ng-model="accountFormData.startingBalance"><br>
                    Account Type:<input type="text" ng-model="accountFormData.accountType"><br>
                    <button ng-click="addAccount()">Add the Account!</button>
                    <button ng-click="clickNewAccountCancel()">Cancel</button>
                </div>
            </modal>


            <modal title="Transaction Details" visible="showTransactionModal">

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
                </div>
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
            </modal>

            <modal title="Budget Item Details" visible="showBudgetItemModal" >
                <div>
                    Category:<input type="text" ng-model="budgetFormData.category"><br>
                    Monthly Amount:<input type="text" ng-model="budgetFormData.amt"><br>

                    <button ng-click="addBudgetItem()">Save the Item</button>
                    <button ng-click="clickBudgetItemCancel()">Cancel</button>
                </div>
            </modal>
            <modal title="Online Transaction Matching" visible="showOnlineMatchingModal">
                <div>
                    <div id="pendingMatchedTransactionList">
                        <div ng-repeat="x in pendingMatchedTransactions" ng-click="" class="list-group-item registryTransaction">
                            <button ng-click="">Accept</button>
                            <button>Dismiss</button>
                            <span>{{ x.txnDate}}</span>
                            <span>{{ x.payee}}</span>
                            <span>{{ x.txnAmount | currency }}</span>
                        </div>
                    </div>

                    <button ng-click="hideOnlineMatchingDialog()">Cancel</button>
                </div>
            </modal>
        </div>

    </body>
</html>