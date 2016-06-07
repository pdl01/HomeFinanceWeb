<#include "layoutmacros.ftl"/>
<#include "inputforms.ftl"/>

<!DOCTYPE html>
<html>

    <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
        	<meta name="_csrf" content="${_csrf.token}"/>
                <!-- default header name is X-CSRF-TOKEN -->
                <meta name="_csrf_header" content="${_csrf.headerName}"/>      
<title>Dashboard</title>
        <script src="/script/jquery-1.11.1.min.js"></script>
        <script src="/script/angular/1.5/angular.min.js"></script>
        <script src="/script/angular/1.5/angular-route.min.js"></script>
        <script src="/script/angular/1.5/angular-animate.min.js"></script>

        <script src="/script/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <script src="/script/ui-bootstrap-tpls-1.1.2.min.js"></script>
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
            <@buildMenu 'dashboard'/>            
            <!--<div id="appSidebar">
                            
            </div>
            -->
                
            
            <div id="appMain">
                <div id="appMainTabs">
                    <div id="appMainTabsAccountName">{{selectedAccount.name}}</div>

                    <ul>
                        <li class="tabItem" id="accountMenuItemSummary" ng-click="showSummaryTab()">Summary</li>
                        <li class="tabItem" id="accountMenuItemRegister" ng-click="showRegistryTab()">Register</li>
                        <li class="tabItem" id="accountMenuItemSchedule" ng-click="showScheduleTab()">Schedule</li>
                        <li class="tabItem" id="accountMenuItemReport" ng-click="showReportTab()" >Report</li>
                        <li class="tabItem" id="accountMenuItemOnline" ng-click="showOnlineTab()">Online</li>
                        <li class="tabItem" id=accountEditLink" ng-click="clickEditAccount()">Edit</li>
                        <li class="tabItem" id="accountNewTransaction" ng-click="showNewTransaction()">New Transaction</li>
                    </ul>
                </div>
                <div id="accountSummary">
                    <@accountSummary pageTitle='dashboard' invokedFrom='registry'/>
                </div>
                <div id="accountTransactionList">
                    <@registryFilter pageTitle='dashboard' invokedFrom='registry'/>            
                    
                    <div ng-repeat="x in registryTransactions" ng-click="showTransactionForm(x)" ng:class="{true:'list-group-item registryTransaction credit',false:'list-group-item registryTransaction'}[x.credit==true]">
                        <span>{{ convertJSDateToTextDate(x.txnDate)}}</span>
                        <span>{{ x.payee}}</span>
                        <span>{{ x.txnAmount | currency }}</span>
                    </div>
                </div>
                <div id="accountSchedule">
                    <a href="" ng-click="showNewScheduledTransactionForm()">New Scheduled Transaction</a>
                    Upcoming or Overdue 
                    <@registryFilter pageTitle='dashboard' invokedFrom='schedule'/>            

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
                        <a href="/app/reports">Advanced</a>
                    </div>
                    <div id="reportData">
                        <div id="pie1" style="margin-top:20px; margin-left:20px; width:70%;"></div>




                        <script class="code" type="text/javascript">

                                    var plot1 = "";


                        </script>
                    <div id="reportTransactions">
                        <div ng-repeat="x in report_transactions"  class="list-group-item accountEntry">
                        <div>
                            <span><a href="#" ng-click="selectReportDataPoint(x)" class="report_transaction" id="">{{ x.name}}</a></span>
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
                        <button ng-click="acceptAllOnlineTxns()">Accept All</button>
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
                <@transactionDetailsForm 'dashboard'/>
            </modal>

            <modal title="Scheduled Transaction Details" visible="showScheduledTransactionModal">
                <@scheduledTransactionDetailsForm 'dashboard'/>
            </modal>

            <modal title="Report Data Details" visible="showReportDataModal">
                    <div ng-repeat="x in reportDataPointTxns" ng-click="showTransactionForm(x)" ng:class="{true:'list-group-item registryTransaction credit',false:'list-group-item registryTransaction'}[x.credit==true]">
                        <span>{{ convertJSDateToTextDate(x.txnDate)}}</span>
                        <span>{{ x.payee}}</span>
                        <span>{{ x.txnAmount | currency }}</span>
                    </div>                

                <#--
                <div ng-repeat="x in reportDataPointTxns" ng-click="showTransactionForm(x)">
                    <span>{{ x.txnDate}}</span>
                    <span>{{ x.payee}}</span>
                    <span>{{ x.txnAmount | currency }}</span>
                </div>
                -->
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