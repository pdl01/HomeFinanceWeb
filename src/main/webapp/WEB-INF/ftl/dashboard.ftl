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

        <script src="/script/app/dashboard/app.js"></script>
        <script src="/script/app/dashboard/dashboardcontroller.js"></script>
        <script src="/script/app/dashboard/navcontroller.js"></script>
        <script src="/script/app/dashboard/onlinecontroller.js"></script>
        <script src="/script/app/dashboard/registrycontroller.js"></script>
        <script src="/script/app/dashboard/reportcontroller.js"></script>
        <script src="/script/app/dashboard/scheduledtransactioncontroller.js"></script>
        <script src="/script/app/dashboard/summarycontroller.js"></script>
        <script src="/script/app/dashboard/transactionformcontroller.js"></script>
        <script src="/script/app/services/accountservice.js"></script>
        <script src="/script/app/services/registryservice.js"></script>
        <script src="/script/app/services/reportservice.js"></script>
        <script src="/script/app/services/categorylookupservice.js"></script>
        <script src="/script/app/services/dateservice.js"></script>
        <script src="/script/app/services/notificationservice.js"></script>
        <script src="/script/app/services/transactionstatuslookupservice.js"></script>
        <script src="/script/app/services/scheduledtransactionservice.js"></script>
        <script src="/script/app/services/namelookupservice.js"></script>

        <script class="include" type="text/javascript" src="/jqplot.1.0.8r1250.dist/jquery.jqplot.min.js"></script>
        <link class="include" rel="stylesheet" type="text/css" href="/jqplot.1.0.8r1250.dist/jquery.jqplot.min.css" />
        <script type="text/javascript" src="/jqplot.1.0.8r1250.dist/plugins/jqplot.pieRenderer.min.js"></script>

    </head>

    <body ng-app="HFWApp" ng-cloak>



        <@showLayoutHeader 'dashboard'/>

        <div  ng-controller="dashboardController" id="main">
            <@buildMenu 'dashboard'/>            
            <!--<div id="appSidebar">
                            
            </div>
            -->


            <div id="appMain">
                <div id="appMainTabs">
                    <div id="appMainTabsAccountName">{{dashboardworkingAccount.name}}</div>

                    <ul class="tabRow">
                        
                        <li class="tabItem" ng-class="{true:'activeTab',false:'inactiveTab'}[isTab('summary') == true]" id="accountMenuItemSummary"  ng-click="setTab('summary')">Summary</li>
                        <li class="tabItem" ng-class="{true:'activeTab',false:'inactiveTab'}[isTab('registry') == true]" id="accountMenuItemRegister" ng-click="setTab('registry')">Register</li>
                        <li class="tabItem" ng-class="{true:'activeTab',false:'inactiveTab'}[isTab('schedule') == true]" id="accountMenuItemSchedule" ng-click="setTab('schedule')">Schedule</li>
                        <li class="tabItem" ng-class="{true:'activeTab',false:'inactiveTab'}[isTab('report') == true]" id="accountMenuItemReport"   ng-click="setTab('report')" >Report</li>
                        <li class="tabItem" ng-class="{true:'activeTab',false:'inactiveTab'}[isTab('online') == true]" id="accountMenuItemOnline"   ng-click="setTab('online')">Online</li>
                        <!--<li class="tabItem" id=accountEditLink"          ng-click="clickEditAccount()">Edit</li>-->
                        <li class="tabItem" id="accountNewTransaction"   ng-click="showNewTransaction()">New Transaction</li>
                        <li class="tabItem" ng-click="clickEditAccount()">Account</li>
                    </ul>
                    
                </div>
                <div id="accountSummary" class="dashboardSection" ng-show="isTab('summary')" ng-controller="accountSummaryController">
                    <@accountSummary pageTitle='dashboard' invokedFrom='registry'/>
                </div>
                <div id="accountTransactionList" class="dashboardSection" ng-show="isTab('registry')" ng-controller="accountRegistryController">
                    <@registryFilter pageTitle='dashboard' invokedFrom='registry'/>            
                    <div>
                        <a class="registryColumn header txnDate" href="#" ng-click="txnSortType = 'txnDate'; txnSortReverse = ! txnSortReverse">
                            Date 
                            <span ng-show="txnSortType == 'txnDate' && !txnSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="txnSortType == 'txnDate' && txnSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a class="registryColumn header txnPayee" href="#" ng-click="txnSortType = 'payee'; txnSortReverse = ! txnSortReverse">
                            Payee 
                            <span ng-show="txnSortType == 'payee' && !txnSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="txnSortType == 'payee' && txnSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a class="registryColumn header txnAmt" href="#" ng-click="txnSortType = 'txnAmount'; txnSortReverse = ! txnSortReverse">
                            Amount 
                            <span ng-show="txnSortType == 'txnAmount' && !txnSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="txnSortType == 'txnAmount' && txnSortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </div>

                    <div ng-repeat="x in registryTransactions | orderBy:txnSortType:txnSortReverse" ng-click="showTransactionForm(x)" ng:class="{true:'list - group - item registryTransaction credit',false:'list - group - item registryTransaction'}[x.credit == true]">
                        <span class="registryColumn detail txnDate">{{ convertJSDateToTextDate(x.txnDate)}}</span>
                        <span class="registryColumn detail txnPayee">{{ x.payee}}</span>
                        <span class="registryColumn detail txnAmt">{{ x.txnAmount | currency }}</span>
                    </div>
                        
                </div>
                <div id="accountSchedule" class="dashboardSection" ng-show="isTab('schedule')" ng-controller="accountScheduleController">
                     <a href="#" ng-click="showNewScheduledTransactionForm()">New Scheduled Transaction</a>
                    Upcoming or Overdue 
                    <@registryFilter pageTitle='dashboard' invokedFrom='schedule'/>            
                   
                    <div ng-repeat="x in scheduledtransactions" class="list-group-item scheduledTransaction">
                        <a href="javascript:void(0)" ng-click="enterScheduledTxn(x)">Enter</a>
                        <a href="javascript:void(0)" ng-click="skipScheduledTxn(x)">Skip</a>
                        <a href="javascript:void(0)" ng-click="markAsPaid(x)">Mark As Paid</a>
                        <a href="javascript:void(0)" ng-click="editOriginalScheduledTxn(x)">Edit Schedule</a>
                        <a href="javascript:void(0)" ng-click="editScheduledTxn(x)">Edit This</a>
                        <span>{{ x.scheduledDate}}</span>
                        <span>{{ x.payee}}</span>
                        <span>{{ x.txnAmount | currency }}</span>
                        <span>{{lookupStatus(x)}}</span>
                    </div>
                    <modal title="Scheduled Transaction Details" visible="showScheduledTransactionModal">
                        <@scheduledTransactionDetailsForm 'dashboard'/>
                    </modal>

                </div>

                <div id="accountReports" class="dashboardSection" ng-show="isTab('report')" ng-controller="accountReportController">
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

                                                                                            var plot1 = "";                        </script>
                        <div id="reportTransactions">
                            <div ng-repeat="x in report_transactions"  class="list-group-item accountEntry">
                                <div>
                                    <span><a href="#" ng-click="selectReportDataPoint(x)" class="report_transaction" id="">{{ x.name}}</a></span>
                                    <span>{{ x.value | currency }}</span>

                                </div>
                            </div>
                            <modal title="Report Data Details" visible="showReportDataModal">
                                <div ng-repeat="x in reportDataPointTxns" ng-click="showTransactionForm(x)" ng:class="{true:'list - group - item registryTransaction credit',false:'list - group - item registryTransaction'}[x.credit == true]">
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

                        </div>


                    </div>
                </div>
                <div id="accountOnlineFunctions" class="dashboardSection" ng-show="isTab('online')" ng-controller="accountOnlineController">

                    <div>online Last Transaction Date:{{workingAccount.lastImportedTransactionDate}} 
                        <div ng-if="workingAccount.webAddress != null"><a href="{{workingAccount.webAddress}}" target="_blank">Web Site</a></div>
                    </div>
                    <form enctype="multipart/form-data" id="onlineDataUploadForm">
                        Data:<br>
                        <button ng-click="selectFile()">Upload Your File</button>
                        <input type="hidden" name="accountId" value="{{workingAccount.id}}">
                        <input type="hidden" name="operation" value="importonlinetxnfile">
                        <input type="file" style="display:none" 
                               id="file" name='file' />
                        <button ng-click="addOnlineData()">Submit!</button>
                        <button ng-click="acceptAllOnlineTxns()">Accept All</button>
                        <button ng-click="getPendingTransactions()">Refresh</button>
                    </form>
                    <div id="progress">{{uploadProgress}}</div>
                    <div>
                        <a href="#" ng-click="onlineSortType = 'txnDate'; onlineSortReverse = ! onlineSortReverse">
                            Date 
                            <span ng-show="onlineSortType == 'txnDate' && !onlineSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="onlineSortType == 'txnDate' && onlineSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a href="#" ng-click="onlineSortType = 'payee'; onlineSortReverse = ! onlineSortReverse">
                            Payee 
                            <span ng-show="onlineSortType == 'payee' && !onlineSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="onlineSortType == 'payee' && onlineSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a href="#" ng-click="onlineSortType = 'txnAmount'; onlineSortReverse = ! onlineSortReverse">
                            Amount 
                            <span ng-show="onlineSortType == 'txnAmount' && !onlineSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="onlineSortType == 'txnAmount' && onlineSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a href="#" ng-click="onlineSortType = 'statusTxt'; onlineSortReverse = ! onlineSortReverse">
                            Status 
                            <span ng-show="onlineSortType == 'statusTxt' && !onlineSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="onlineSortType == 'statusTxt' && onlineSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a href="#" ng-click="onlineSortType = 'matches'; onlineSortReverse = ! onlineSortReverse">
                            Matches
                            <span ng-show="onlineSortType == 'matches' && !onlineSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="onlineSortType == 'matches' && onlineSortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </div>
                    <div id="pendingTransactionList">
                        <div ng-repeat="x in pendingTransactions|  orderBy:onlineSortType:onlineSortReverse" ng-click="" ng-class="{true:'list - group - item registryTransaction credit',false:'list - group - item registryTransaction'}[x.credit == true]">
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
                    <modal title="Online Transaction Matching" visible="showOnlineMatchingModal">
                        <div>
                            <span>{{selectedPendingTransaction.txnDate}}</span>
                            <span>{{selectedPendingTransaction.payee}}</span>
                            <span>{{selectedPendingTransaction.txnAmount| currency }}</span>
                            <div id="pendingMatchedTransactionList">
                                <div ng-repeat="x in pendingMatchedTransactions" ng-click="" class="list-group-item registryTransaction">
                                    <a href="" ng-click="acceptMatchForPending(selectedPendingTransaction, x)">Accept</a>

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

            </div>
            
            <modal title="Transaction Details" visible="showTransactionModal" >
                <div ng-controller="transactionFormController">
                <@transactionDetailsForm 'dashboard'/>
                </div>
            </modal>







            <modal title="Budget Item Details" visible="showBudgetItemModal" >
                <div>
                    Category:<input type="text" ng-model="budgetFormData.category"><br>
                    Monthly Amount:<input type="text" ng-model="budgetFormData.amt"><br>

                    <button ng-click="addBudgetItem()">Save the Item</button>
                    <button ng-click="clickBudgetItemCancel()">Cancel</button>
                </div>
            </modal>
            <!--
            <modal title="Notification Details" visible="showNotificationsModal" >
                <div id="notificationList">
                    <div ng-repeat="x in notifications| orderBy:createdOn" ng-click="" class="list-group-item notificationList">
                        <span>{{ x.createdOnAsString}}
                            <span>{{ x.subject}}</span>
                            <span>{{ x.message}}</span>
                            <a class="markAsRead" ng-click="markNotificationAsRead(x.id)">&nbsp;&nbsp;&nbsp;&nbsp;</a>
                    </div>
                </div>

            </modal>            
            -->
        </div>
        <@showLayoutFooter 'dashboard'/>

    </body>
</html>