<#include "layoutmacros.ftl"/>
<!DOCTYPE html>
<html>

    <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
        	<meta name="_csrf" content="${_csrf.token}"/>
                <!-- default header name is X-CSRF-TOKEN -->
                <meta name="_csrf_header" content="${_csrf.headerName}"/>      

        <script src="/script/jquery-1.11.1.min.js"></script>
        <script src="/script/angular/1.2.26/angular.min.js"></script>
        <script src="/script/angular/1.2.26/angular-route.min.js"></script>
        <script src="/script/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href = "/style/bootstrap/3.2.0/bootstrap.min.css"/>
        <link rel="stylesheet" href = "/style/dashboard.css"/>
        <link rel="stylesheet" href = "/themes/${theme}/theme.css"/>
        <link rel="shortcut icon" href="favicon.ico" />
    </head>

    <body >


        <script src="/script/app/dashboard/app.js"></script>
        <script src="/script/app/services.js"></script>


        <script class="include" type="text/javascript" src="/jqplot.1.0.8r1250.dist/jquery.jqplot.min.js"></script>
        <link class="include" rel="stylesheet" type="text/css" href="/jqplot.1.0.8r1250.dist/jquery.jqplot.min.css" />
        <script type="text/javascript" src="/jqplot.1.0.8r1250.dist/plugins/jqplot.pieRenderer.min.js"></script>
        <@showLayoutHeader 'dashboard'/>

        <div ng-app="HFWApp" ng-controller="dashboardController" id="main">
            <div id="appMenu">
                <ul>
                    <li class="menuItem" id="menuItemNewAccount" ng-click="showNewAccount()">New Account</li>
                    <li class="menuItem" id="menuItemRegister" ng-click="showNewTransaction()">New Transaction</li>
                </ul>
            </div>
            
                <div id="accountList">
                    <div id="notificationsSection" ng-click="showNotifications()" ng-show="showNotificationsLink">Notifications (<span class="" id="notificationNumber">{{numNotifications}}</span>)</div>
                    <div>Accounts <a class="refresh" ng-click="refreshAccounts()">&nbsp;&nbsp;&nbsp;&nbsp;</a></div>
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('checking')">Checking</div>
                    <div ng-hide="hideAccounts['checking']" ng-repeat="x in checking_accounts"  id="checkingAccounts" class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                    </div> 
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('savings')">Savings</div>
                    <div ng-hide="hideAccounts['savings']" ng-repeat="x in savings_accounts"  class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                        
                    </div>
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('creditcard')">Credit Card</div>
                    <div ng-hide="hideAccounts['creditcard']" ng-repeat="x in creditcard_accounts"  class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                    </div>                    
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('investment')">Investment</div>
                    <div ng-hide="hideAccounts['investment']" ng-repeat="x in investment_accounts"  class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                    </div>                    
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('retirement')">Retirement</div>
                    <div ng-hide="hideAccounts['retirement']" ng-repeat="x in retirement_accounts"  class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                    </div>
                    <div class="accountListHeader" ng-click="toggleAccountGroupHide('other')">Other</div>
                    <div ng-hide="hideAccounts['other']" ng-repeat="x in other_accounts"  class="list-group-item accountEntry">
                        <div ng-click="clickGoButton(x)">
                            <span>{{ x.name}}</span>
                            <span>{{ x.currentBalance | currency }}</span>
                        </div>

                    </div>

                </div>
            
            <div id="appMain">
                <div id="appMainTabs">
                    <div id="appMainTabsAccountName">{{selectedAccount.name}}</div>

                    <ul>
                        <li class="tabItem" id="accountMenuItemRegister" ng-click="showRegistryTab()">Register</li>
                        <li class="tabItem" id="accountMenuItemSchedule" ng-click="showScheduleTab()">Schedule</li>
                        <li class="tabItem" id="accountMenuItemReport" ng-click="showReportTab()" >Report</li>
                        <li class="tabItem" id="accountMenuItemOnline" ng-click="showOnlineTab()">Online</li>
                        <li class="tabItem" id=accountEditLink" ng-click="clickEditAccount()">Edit</li>
                    </ul>
                </div>
                <div id="accountTransactionList">
                    
                    <div id="registryFilterControls"><a href="" ng-click="filterToCurrentDate()" class="registryFilter">Now</a>
                        Year:<select name="selectRegisterYear" ng-model="txnDateControl.year" class="registryFilter">
                            <option value="2016">2016</option>
                            <option value="2015">2015</option>
                            <option value="2014">2014</option>
                            <option value="2013">2013</option>
                            <option value="2012">2012</option>
                            <option value="2011">2011</option>
                            <option value="2010">2010</option>
                            <option value="2009">2009</option>
                            <option value="2008">2008</option>                             
                        </select>
                        Month:<select name="selectRegisterMonth" ng-model="txnDateControl.month" class="registryFilter">
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
                        <a href="" ng-click="getTransactionsForMonth(selectedAccount.id)" class="registryFilter">Go</a>
                    </div>
                    <div ng-repeat="x in registryTransactions" ng-click="showTransactionForm(x)" ng:class="{true:'list-group-item registryTransaction credit',false:'list-group-item registryTransaction'}[x.credit==true]">
                        <span>{{ x.txnDate}}</span>
                        <span>{{ x.payee}}</span>
                        <span>{{ x.txnAmount | currency }}</span>
                    </div>
                </div>
                <div id="accountSchedule">
                    <a href="" ng-click="showNewScheduledTransactionForm()">New Scheduled Transaction</a>
                    Upcoming or Overdue 
                    <div id="scheduledFilterControls"><button ng-click="filterScheduledToCurrentDate()" class="registryFilter">Now</button>
                        Year:<select name="selectScheduledYear" ng-model="scheduledDateControl.year" class="registryFilter">
                            <option value="2019">2019</option>
                            <option value="2018">2018</option>
                            <option value="2017">2017</option>
                            <option value="2016">2016</option>
                            <option value="2015">2015</option>
                            <option value="2014">2014</option>
                            <option value="2013">2013</option>
                            <option value="2012">2012</option>
                            <option value="2011">2011</option>
                            <option value="2010">2010</option>
                            <option value="2009">2009</option>
                            <option value="2008">2008</option>                             
                        </select>
                        Month:<select name="selectScheduledMonth" ng-model="scheduledDateControl.month" class="registryFilter">
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
                        <button ng-click="getScheduledTransactionsForMonth(selectedAccount.id)" class="registryFilter">Go</button>
                    </div>

                    <div ng-repeat="x in scheduledtransactions" class="list-group-item scheduledTransaction">
                        <a href="" ng-click="enterScheduledTxn(x)">Enter</a>
                        <a href="" ng-click="skipScheduledTxn(x)">Skip</a>
                        <a href="" ng-click="editOriginalScheduledTxn(x)">Edit Schedule</a>
                        <a href="" ng-click="editScheduledTxn(x)">Edit This</a>
                        <span>{{ x.scheduledDate}}</span>
                        <span>{{ x.payee}}</span>
                        <span>{{ x.txnAmount | currency }}</span>
                        <span>{{lookupStatus(x)}}</span>
                    </div>
                </div>
                <div id="accountReports">
                    <div id="reportControl">
                        <select name="reportType" id="reportType" ng-model="reportControl.reportType">

                            <option value="INCOME">Income</option>
                            <option value="expensebypayee">Expenses By Payee</option>
                            <option value="expensebycategory">Expenses By Category</option>
                            <option value="dailybalance">Daily Balance</option>
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
                    <div id="reportTransactions">
                        <div ng-repeat="x in report_transactions"  class="list-group-item accountEntry">
                        <div>
                            <span>{{ x.name}}</span>
                            <span>{{ x.value | currency }}</span>
                        </div>
                    </div>
                    </div>


                    </div>
                </div>
                <div id="accountOnlineFunctions">

                    <div>online Last Transaction Date:{{selectedAccount.lastImportedTransactionDate}} 
                   <div ng-if="selectedAccount.webAddress != null"><a href="{{selectedAccount.webAddress}}" target="_blank">Web Site</a></div>
                    </div>
                    <form enctype="multipart/form-data" id="onlineDataUploadForm">
                        Data:<br>
                        <button ng-click="selectFile()">Upload Your File</button>
                        <input type="hidden" name="accountId" value="{{selectedAccount.id}}">
                        <input type="file" style="display:none" 
                               id="file" name='file' />
                        <button ng-click="addOnlineData()">Submit!</button>
                    </form>
                    <div id="progress">{{uploadProgress}}</div>
                    <div>
                        <a href="#" ng-click="onlineSortType = 'txnDate' ; onlineSortReverse = ! onlineSortReverse">
                            Date 
                            <span ng-show="onlineSortType == 'txnDate' && !onlineSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="onlineSortType == 'txnDate' && onlineSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a href="#" ng-click="onlineSortType = 'payee' ; onlineSortReverse = ! onlineSortReverse ">
                            Payee 
                            <span ng-show="onlineSortType == 'payee' && !onlineSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="onlineSortType == 'payee' && onlineSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a href="#" ng-click="onlineSortType = 'txnAmount'  ; onlineSortReverse = ! onlineSortReverse">
                            Amount 
                            <span ng-show="onlineSortType == 'txnAmount' && !onlineSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="onlineSortType == 'txnAmount' && onlineSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a href="#" ng-click="onlineSortType = 'statusTxt'  ; onlineSortReverse = ! onlineSortReverse">
                            Status 
                            <span ng-show="onlineSortType == 'statusTxt' && !onlineSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="onlineSortType == 'statusTxt' && onlineSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a href="#" ng-click="onlineSortType = 'matches' ; onlineSortReverse = ! onlineSortReverse">
                            Matches
                            <span ng-show="onlineSortType == 'matches' && !onlineSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="onlineSortType == 'matches' && onlineSortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </div>
                    <div id="pendingTransactionList">
                        <div ng-repeat="x in pendingRegistryTransactions | orderBy:onlineSortType:onlineSortReverse" ng-click="" ng-class="{true:'list-group-item registryTransaction credit',false:'list-group-item registryTransaction'}[x.credit==true]">
                            <a href="" ng-click="acceptPendingTransactionAsNew(x)">New</a>
                            <a href="" ng-click="dismissMatchForPending(x)">Dismiss</a>
                            <a href="" ng-click="showOnlineMatchingDialog(x)">Match</a>
                            <span>{{ x.txnDate}}</span>
                            <span>{{ x.payee}}</span>
                            <span>{{ x.txnAmount | currency }}</span>
                            
                            <span>{{lookupStatus(x)}}</span>
                            <span>{{x.matches}}</span>
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
                    Web Site:<input type="text" ng-model="accountFormData.webAddress"><br>
                    <button ng-if="accountFormData.id == null" ng-click="addAccount()">Add the Account!</button>
                    <button ng-if="accountFormData.id != null" ng-click="addAccount()">Save Account!</button>
                    <button ng-click="clickNewAccountCancel()">Cancel</button>
                    <button ng-if="accountFormData.id != null" ng-click="clickDeleteAccount()">Delete Account</button>
                </div>
            </modal>


            <modal title="Transaction Details" visible="showTransactionModal">
                Account:  {{selectedAccount.name}}<br>
                <input type="hidden" ng-model="registryTransactionFormData.primaryAccount">
                <input type="hidden" ng-model="registryTransactionFormData.id">
                <div ng-if="selectedAccount.accountType == 'Checking'">Check #<input  ng-model="registryTransactionFormData.txnPersonalRefNumber"></div>
                Date:<input type="text" ng-model="registryTransactionFormData.txnDate"><br>
                Payee Name:<input typ="text" ng-model="registryTransactionFormData.payee"><br>
                Transaction Amount:<input typ="text" size="5" ng-model="registryTransactionFormData.txnAmount"><input type="checkbox" value="true" ng-model="registryTransactionFormData.credit">Is Credit?<br>

                Category <a ng-click="addSplit()">Add Split</a><br/>
                <div id="categorySplits">
                    <div class="categorySplit">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[0].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[0].txnAmount"><br></div>
                    <div class="categorySplit">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[1].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[1].txnAmount"><br></div>
                    <div class="categorySplit">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[2].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[2].txnAmount"><br></div>
                    <div class="categorySplit">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[3].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[3].txnAmount"><br></div>
                    <div class="categorySplit">Category:<input type="text" ng-model="registryTransactionFormCategorySplits[4].category"> Amount:<input type="text" size="5" ng-model="registryTransactionFormCategorySplits[4].txnAmount"><br></div>
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

            <modal title="Scheduled Transaction Details" visible="showScheduledTransactionModal">
                Account:  {{selectedAccount.name}}<br>
                <input type="hidden" ng-model="scheduledTransactionFormData.primaryAccount">
                <input type="hidden" ng-model="scheduledTransactionFormData.id">
                <input type="hidden" ng-model="scheduledTransactionFormData.originalTransactionId">
                Payee Name:<input typ="text" ng-model="scheduledTransactionFormData.payee"><br>
                Transaction Amount:<input typ="text" size="5" ng-model="scheduledTransactionFormData.txnAmount"><input type="checkbox" value="true" ng-model="scheduledTransactionFormData.credit">Is Credit?<br>

                Category <a ng-click="addSplit()">Add Split</a><br/>
                <div id="categorySplits">
                    <div class="categorySplit">Category:<input type="text" ng-model="scheduledTransactionFormCategorySplits[0].category"> Amount:<input type="text" size="5" ng-model="scheduledTransactionFormCategorySplits[0].txnAmount"><br></div>
                    <div class="categorySplit">Category:<input type="text" ng-model="scheduledTransactionFormCategorySplits[1].category"> Amount:<input type="text" size="5" ng-model="scheduledTransactionFormCategorySplits[1].txnAmount"><br></div>
                    <div class="categorySplit">Category:<input type="text" ng-model="scheduledTransactionFormCategorySplits[2].category"> Amount:<input type="text" size="5" ng-model="scheduledTransactionFormCategorySplits[2].txnAmount"><br></div>
                    <div class="categorySplit">Category:<input type="text" ng-model="scheduledTransactionFormCategorySplits[3].category"> Amount:<input type="text" size="5" ng-model="scheduledTransactionFormCategorySplits[3].txnAmount"><br></div>
                    <div class="categorySplit">Category:<input type="text" ng-model="scheduledTransactionFormCategorySplits[4].category"> Amount:<input type="text" size="5" ng-model="scheduledTransactionFormCategorySplits[4].txnAmount"><br></div>
                    <div class="categorySplit hidden">Category:<input type="text" ng-model="scheduledTransactionFormCategorySplits[5].category"> Amount:<input type="text" size="5" ng-model="scheduledTransactionFormCategorySplits[5].txnAmount"><br></div>
                    <div class="categorySplit hidden">Category:<input type="text" ng-model="scheduledTransactionFormCategorySplits[6].category"> Amount:<input type="text" size="5" ng-model="scheduledTransactionFormCategorySplits[6].txnAmount"><br></div>
                    <div class="categorySplit hidden">Category:<input type="text" ng-model="scheduledTransactionFormCategorySplits[7].category"> Amount:<input type="text" size="5" ng-model="scheduledTransactionFormCategorySplits[7].txnAmount"><br></div>
                    <div class="categorySplit hidden">Category:<input type="text" ng-model="scheduledTransactionFormCategorySplits[8].category"> Amount:<input type="text" size="5" ng-model="scheduledTransactionFormCategorySplits[8].txnAmount"><br></div>
                    <div class="categorySplit hidden">Category:<input type="text" ng-model="scheduledTransactionFormCategorySplits[9].category"> Amount:<input type="text" size="5" ng-model="scheduledTransactionFormCategorySplits[9].txnAmount"><br></div>
                </div>
                    <div ng-hide="hideTxnRetrievedCategories">
                        <ul ng-repeat="category in retrievedCategories">
                            <li class="retrievedCategoryItem"><a class="retrievedCategoryItem" ng-click="selectScheduledTxnRetrievedCategory(category)">{{category}}</a></li>
                        </ul>
                    </div>
                    <br/>
                    <div id="scheduleTimingControl" ng-if="scheduledTransactionFormData.original == true || scheduledTransactionFormData.id == null">
                        Frequency:<input type="radio" name="frequency" value="3" ng-model="scheduledTransactionFormData.frequency">Daily
                        <input type="radio" name="frequency" value="1" ng-model="scheduledTransactionFormData.frequency">Weekly
                        <input type="radio" name="frequency" value="2" ng-model="scheduledTransactionFormData.frequency">Monthly
                        <input type="radio" name="frequency" value="4" ng-model="scheduledTransactionFormData.frequency">Yearly
                        <input type="radio" name="frequency" value="0" ng-model="scheduledTransactionFormData.frequency">One Time
                        <br/>
                        Begin Date:<input type="text" ng-model="scheduledTransactionFormData.beginDate"><br>
                        End Date:<input type="text" ng-model="scheduledTransactionFormData.endDate"><br>
                        End After <input type="text" ng-model="scheduledTransactionFormData.numberOfOccurrences" size="3"> Occurrences:<br>
                    </div>
                    <br>
                    <button ng-click="addScheduledTransaction()">Save</button>
                    <button ng-click="clickScheduledTransactionCancel()">Cancel</button>
            </modal>
            
            <modal title="Budget Item Details" visible="showBudgetItemModal" >
                <div>
                    Category:<input type="text" ng-model="budgetFormData.category"><br>
                    Monthly Amount:<input type="text" ng-model="budgetFormData.amt"><br>

                    <button ng-click="addBudgetItem()">Save the Item</button>
                    <button ng-click="clickBudgetItemCancel()">Cancel</button>
                </div>
            </modal>
            <modal title="Notification Details" visible="showNotificationsModal" >
                    <div id="notificationList">
                        <div ng-repeat="x in notifications | orderBy:createdOn" ng-click="" class="list-group-item notificationList">
                            <span>{{ x.createdOnAsString }}
                            <span>{{ x.subject}}</span>
                            <span>{{ x.message}}</span>
                            <a class="markAsRead" ng-click="markNotificationAsRead(x.id)">&nbsp;&nbsp;&nbsp;&nbsp;</a>
                        </div>
                    </div>
                
            </modal>            
            <modal title="Online Transaction Matching" visible="showOnlineMatchingModal">
                <div>
                    <span>{{selectedPendingTransaction.txnDate}}</span>
                    <span>{{selectedPendingTransaction.payee}}</span>
                    <span>{{selectedPendingTransaction.txnAmount | currency }}</span>
                    <div id="pendingMatchedTransactionList">
                        <div ng-repeat="x in pendingMatchedTransactions" ng-click="" class="list-group-item registryTransaction">
                            <a href="" ng-click="acceptMatchForPending(selectedPendingTransaction,x)">Accept</a>
                    
                            <span>{{ x.txnDate}}</span>
                            <span>{{ x.payee}}</span>
                            <span>{{ x.txnAmount | currency }}</span>
              
                        </div>
                    </div>
                    <button ng-click="acceptPendingTransactionAsNew(selectedPendingTransaction)">New</button>
                    <button ng-click="hideOnlineMatchingDialog()">Cancel</button>
                </div>
            </modal>
        </div>
        <@showLayoutFooter 'dashboard'/>

    </body>
</html>