<#macro transactionDetailsForm pageTitle=''>
                Account:  {{workingAccount.name}}<br>
                <input type="hidden" ng-model="workingTransaction.primaryAccount">
                <input type="hidden" ng-model="workingTransaction.id">
                <div ng-if="workingAccount.accountType == 'Checking'">Check #<input  ng-model="workingTransaction.txnPersonalRefNumber"></div>
                <!--<input type="text" ng-model="workingTransaction.txnDate"><br>-->
                    <div class="date-input">
                    Date<div class="input-group">
                    <input type="text" class="form-control " uib-datepicker-popup="yyyy-MM-dd" ng-model="workingTransaction.txnDate" is-open="registryTransactionDateField.opened" min-date="minDate" max-date="maxDate" datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close" />
                        <span class="input-group-btn">
                        <button type="button" class="btn btn-default" ng-click="openRegistryTransactionDateField()"><i class="glyphicon glyphicon-calendar"></i></button>
                        </span>
                    </div>
                    </div>
                    Payee Name:<input typ="text" ng-model="workingTransaction.payee"><br>
                Transaction Amount:<input typ="text" size="5" ng-model="workingTransaction.txnAmount"><input type="checkbox" value="true" ng-model="workingTransaction.credit">Is Credit?<br>

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
                    Status:<input type="radio" name="statusTxt" value="c" ng-model="workingTransaction.statusTxt">Cleared
                    <input type="radio" name="statusTxt" value="v" ng-model="workingTransaction.statusTxt">Void
                    <input type="radio" name="statusTxt" value="x" ng-model="workingTransaction.statusTxt">None
                    <br/>
                    <button ng-click="addRegistryTransaction()">Save</button>
                    <button ng-click="clickNewTransactionCancel()">Cancel</button>


</#macro>

<#macro scheduledTransactionDetailsForm pageTitle=''>
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
                        <div class="date-input">
                            Begin Date:
                            <div class="input-group">
                                <input type="text" class="form-control " uib-datepicker-popup="yyyy-MM-dd" ng-model="scheduledTransactionFormData.beginDate" is-open="scheduledTransactionDateField.beginopened" min-date="minDate" max-date="maxDate" datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close" />
                                <span class="input-group-btn">
                                    <button type="button" class="btn btn-default" ng-click="openScheduledTransactionDateField('begin')"><i class="glyphicon glyphicon-calendar"></i></button>
                                </span>
                            </div>
                        </div>
                        <!--<input type="text" ng-model="scheduledTransactionFormData.beginDate">--><br>
                        <div class="input-group">
                        End Date:<div class="input-group">
                        <input type="text" class="form-control " uib-datepicker-popup="yyyy-MM-dd" ng-model="scheduledTransactionFormData.endDate" is-open="scheduledTransactionDateField.endopened" min-date="minDate" max-date="maxDate" datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close" />
                            <span class="input-group-btn">
                            <button type="button" class="btn btn-default" ng-click="openScheduledTransactionDateField('end')"><i class="glyphicon glyphicon-calendar"></i></button>
                            </span>
                        </div><!--<input type="text" ng-model="scheduledTransactionFormData.endDate">-->
                        </div><br>
                        End After <input type="text" ng-model="scheduledTransactionFormData.numberOfOccurrences" size="3"> Occurrences:<br>
                    </div>
                    <br>
                    <button ng-click="addScheduledTransaction()">Save</button>
                    <button ng-click="clickScheduledTransactionCancel()">Cancel</button>

</#macro>

<#macro registryFilter pageTitle='' invokedFrom=''>

<#if invokedFrom == 'registry'>
                          <div id="registryFilterControls">
                            
                        <a href="" ng-click="filterToCurrentDate()" class="registryFilter">Now</a>


<#elseif invokedFrom == 'schedule'>
              <div id="scheduledFilterControls"><button ng-click="filterScheduledToCurrentDate()" class="registryFilter">Now</button>
                        Year:<select name="selectScheduledYear" ng-model="scheduledDateControl.year" class="registryFilter">
      
                            <option value="2020">2020</option>
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
</#if> 

<#if invokedFrom == 'registry'>
                              
<#elseif invokedFrom == 'schedule'>
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
</#if> 
<#if invokedFrom == 'registry'>
                       <a href="" ng-click="getTransactionsForMonth(workingAccount.id)" class="registryFilter">Go</a>
                       <a href="" ng-click="filterToPrevMonth(workingAccount.id)" class="registryFilter">Previous</a>
                       <a href="" ng-click="filterToNextMonth(workingAccount.id)" class="registryFilter">Next</a>
                        <div class="date-input">
                            <div class="input-group">
                                <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM" ng-model="txnDateControl.yearmonth" is-open="txnDateControl.selectopened" min-date="registryminDate" max-date="registrymaxDate" datepicker-options="registrydateOptions" date-disabled="disabled(date, mode)" datepicker-mode="'month'" ng-required="true" close-text="Close" />
                                <span class="input-group-btn">
                                    <button type="button" class="btn btn-default" ng-click="openRegistryDateSelect()"><i class="glyphicon glyphicon-calendar"></i></button>
                                </span>
                            </div>
                        </div>

<#elseif invokedFrom == 'schedule'>

                        <button ng-click="getScheduledTransactionsForMonth(workingAccount.id)" class="registryFilter">Go</button>
                        <div class="date-input">
                            <div class="input-group">
                                <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM" ng-model="scheduledDateControl.yearmonth" is-open="scheduledDateControl.selectopened" min-date="scheduledminDate" max-date="scheduledmaxDate" datepicker-options="scheduleddateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close" />
                                <span class="input-group-btn">
                                    <button type="button" class="btn btn-default" ng-click="openScheduledDateSelect()"><i class="glyphicon glyphicon-calendar"></i></button>
                                </span>
                            </div>
                        </div>

</#if> 
                    </div>
</#macro>

<#macro accountSummary pageTitle="" invokedFrom=''>
    <div id="sectionCurrentBalance">Current Balance</div>
    <div id="sectionLastUpdated">Last Updated</div>
    <div id="sectionFutureBalance">Upcoming Balances</div>
</#macro>


<#macro accountEntryForm>

            <modal title="Account Details" visible="showAccountModal" >
                <div>
                    Account Name:<input typ="text" ng-model="accountFormData.name"><br>
                    Account Number:<input typ="text" ng-model="accountFormData.accountNumber"><br>
                    Opening Balance:<input typ="text" ng-model="accountFormData.startingBalance"><br>
                    Account Type:<input type="text" ng-model="accountFormData.accountType"><br>
                    Web Site:<input type="text" ng-model="accountFormData.webAddress"><br>
                    <button ng-if="accountFormData.id == null" ng-click="addAccount()">Add the Account!</button>
                    <button ng-if="accountFormData.id != null" ng-click="addAccount()">Save Account!</button>
                    <button ng-click="clickAccountCancel()">Cancel</button>
                    <button ng-if="accountFormData.id != null" ng-click="clickDeleteAccount()">Delete Account</button>
                </div>
            </modal>
</#macro>
