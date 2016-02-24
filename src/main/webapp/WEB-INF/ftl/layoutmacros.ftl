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
        <li><a href="/app/dashboard">Accounts</a></li><#if pageTitle=='dashboard'><@buildAccountMenu/></#if>
        <li><a href="/app/notifications">Notifications</a></li>
        <li><a href="/app/budget">Budget</a></li><#if pageTitle=='budget'><@buildBudgetMenu/></#if>
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
    <@buildAccountSection 'checking' 'Checking'/>
    <@buildAccountSection 'savings' 'Savings'/>
    <@buildAccountSection 'creditcard' 'Credit Card'/>
    <@buildAccountSection 'investment' 'Investment'/>
    <@buildAccountSection 'retirement' 'Retirement'/>
    <@buildAccountSection 'other' 'Other'/>

    
</#macro>

<#macro buildAccountSection headerGroup='' headerGroupName='' >
        <li class="accountListHeader" ng-click="toggleAccountGroupHide('${headerGroup}')">${headerGroupName}</li>
        <li ng-hide="hideAccounts['${headerGroup}']" ng-repeat="x in ${headerGroup}_accounts"  id="${headerGroup}Accounts" class="accountEntry" ng-click="clickGoButton(x)">{{ x.name}} {{ x.currentBalance | currency }}</li> 
</#macro>

<#macro buildBudgetMenu>
   <li><a class="newAccount" ng-click="showNewBudget()">New Budget</a></li>

</#macro>