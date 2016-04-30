<#macro transactionDetailsForm pageTitle=''>
                Account:  {{selectedAccount.name}}<br>
                <input type="hidden" ng-model="registryTransactionFormData.primaryAccount">
                <input type="hidden" ng-model="registryTransactionFormData.id">
                <div ng-if="selectedAccount.accountType == 'Checking'">Check #<input  ng-model="registryTransactionFormData.txnPersonalRefNumber"></div>
                <!--<input type="text" ng-model="registryTransactionFormData.txnDate"><br>-->
                    <div class="date-input">
                    Date<div class="input-group">
                    <input type="text" class="form-control " uib-datepicker-popup="yyyy-MM-dd" ng-model="registryTransactionFormData.txnDate" is-open="registryTransactionDateField.opened" min-date="minDate" max-date="maxDate" datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close" />
                        <span class="input-group-btn">
                        <button type="button" class="btn btn-default" ng-click="openRegistryTransactionDateField()"><i class="glyphicon glyphicon-calendar"></i></button>
                        </span>
                    </div>
                    </div>
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
                        Begin Date:<input type="text" ng-model="scheduledTransactionFormData.beginDate"><br>
                        End Date:<input type="text" ng-model="scheduledTransactionFormData.endDate"><br>
                        End After <input type="text" ng-model="scheduledTransactionFormData.numberOfOccurrences" size="3"> Occurrences:<br>
                    </div>
                    <br>
                    <button ng-click="addScheduledTransaction()">Save</button>
                    <button ng-click="clickScheduledTransactionCancel()">Cancel</button>

</#macro>

<#macro registryFilter pageTitle='' invokedFrom=''>

<#if invokedFrom == 'registry'>
                          <div id="registryFilterControls"><a href="" ng-click="filterToCurrentDate()" class="registryFilter">Now</a>
                        Year:<select name="selectRegisterYear" ng-model="txnDateControl.year" class="registryFilter">

<#elseif invokedFrom == 'schedule'>
              <div id="scheduledFilterControls"><button ng-click="filterScheduledToCurrentDate()" class="registryFilter">Now</button>
                        Year:<select name="selectScheduledYear" ng-model="scheduledDateControl.year" class="registryFilter">
      
</#if> 
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
<#if invokedFrom == 'registry'>
                              Month:<select name="selectRegisterMonth" ng-model="txnDateControl.month" class="registryFilter">
<#elseif invokedFrom == 'schedule'>
Month:<select name="selectScheduledMonth" ng-model="scheduledDateControl.month" class="registryFilter">      
</#if> 

                        
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
<#if invokedFrom == 'registry'>
                       <a href="" ng-click="getTransactionsForMonth(selectedAccount.id)" class="registryFilter">Go</a>

<#elseif invokedFrom == 'schedule'>

                        <button ng-click="getScheduledTransactionsForMonth(selectedAccount.id)" class="registryFilter">Go</button>

</#if> 
                    </div>
</#macro>
