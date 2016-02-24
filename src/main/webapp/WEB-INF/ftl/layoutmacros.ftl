<#macro showLayoutHeader pageTitle=''>

        <div id="pageHeader">  
                <#--
                <ul id="SystemActions">
                    <li class="headerItem" id="headerLinkAccounts"><a class="headerItem" href="/app/dashboard">Accounts</a></li>
                    <li class="headerItem" id="headerLinkBudget"><a class="headerItem" href="/app/budget">Budget</a></li>
                    <li class="headerItem" id="headerLinkSettings"><a class="headerItem" href="/app/settings">Settings</a></li>
                </ul>

                
                <ul id="UserActions">
                    <li class="headerItem" id="headerLinkLogout"><a class="headerItem" href="">Logout</a></li>
                    <li class="headerItem" id="headerLinkHelp"><a class="headerItem" href="">Help</a></li>
                <li class="headerItem" id="headerLinkAbout"><a class="headerItem" href="">About</a></li>
                </ul>
                -->
        </div>

</#macro>


<#macro showLayoutFooter pageTitle=''>
    <div id="pageFooter"></div>
</#macro>

<#macro buildMenu pageTitle=''>
            
    <nav id="appNav">
        <ul>
        <li><a href="/app/dashboard">Accounts</a><#if pageTitle=='dashboard'><@buildAccountMenu/></#if></li>
        <li><a href="/app/notifications">Notifications</a></li>
        <li><a href="/app/budget">Budget</a><#if pageTitle=='budget'></#if></li>
        <li><a href="/app/settings">Settings</a></li>
                    
        </nav>
        <div id="nav-trigger">
            <span>Menu</span>
        </div>
        <nav id="nav-mobile"></nav>
        <script>
$(document).ready(function(){
    $("#nav-mobile").html($("#appNav").html());
    $("#nav-trigger span").click(function(){
        if ($("nav#nav-mobile ul").hasClass("expanded")) {
            $("nav#nav-mobile ul.expanded").removeClass("expanded").slideUp(250);
            $(this).removeClass("open");
        } else {
            $("nav#nav-mobile ul").addClass("expanded").slideDown(250);
            $(this).addClass("open");
        }
    });
});
        </script>
</#macro>


<#macro buildAccountMenu>
    
    
        <li><a class="newAccount" ng-click="showNewAccount()">New Account</a></li>
        
        <li class="accountListHeader" ng-click="toggleAccountGroupHide('checking')">Checking</li>
        <li ng-hide="hideAccounts['checking']" ng-repeat="x in checking_accounts"  id="checkingAccounts" class="accountEntry">
            <span ng-click="clickGoButton(x)">
                {{ x.name}}
                {{ x.currentBalance | currency }}
            </span>
        </li> 
        
        <li class="accountListHeader" ng-click="toggleAccountGroupHide('savings')">Savings</li>
        <li ng-hide="hideAccounts['savings']" ng-repeat="x in savings_accounts"  class="accountEntry">
            <span ng-click="clickGoButton(x)">
                {{ x.name}}
                {{ x.currentBalance | currency }}
            </span>
        </li>
        
        <li class="accountListHeader" ng-click="toggleAccountGroupHide('creditcard')">Credit Card</li>
        <li ng-hide="hideAccounts['creditcard']" ng-repeat="x in creditcard_accounts"  class="accountEntry">
            <span ng-click="clickGoButton(x)">
                {{ x.name}}
                {{ x.currentBalance | currency }}
            </span>
        </li>
        
        <li class="accountListHeader" ng-click="toggleAccountGroupHide('investment')">Investment</li>
        <li ng-hide="hideAccounts['investment']" ng-repeat="x in investment_accounts"  class="accountEntry">
            <span ng-click="clickGoButton(x)">
                {{ x.name}}
                {{ x.currentBalance | currency }}
            </span>
        </li>                    

        <li class="accountListHeader" ng-click="toggleAccountGroupHide('retirement')">Retirement</li>
        <li ng-hide="hideAccounts['retirement']" ng-repeat="x in retirement_accounts"  class="accountEntry">
            <span ng-click="clickGoButton(x)">
                {{ x.name}}
                {{ x.currentBalance | currency }}
            </span>
        </li>

        <li class="accountListHeader" ng-click="toggleAccountGroupHide('other')">Other</li>
        <li ng-hide="hideAccounts['other']" ng-repeat="x in other_accounts"  class="accountEntry">
            <span ng-click="clickGoButton(x)">
                {{ x.name}}<
                {{ x.currentBalance | currency }}
            </span>
        </li>

    
</#macro>
<#macro buildBudgetMenu>
   <div><a class="newAccount" ng-click="showNewBudget()">New Budget</a></div>

</#macro>