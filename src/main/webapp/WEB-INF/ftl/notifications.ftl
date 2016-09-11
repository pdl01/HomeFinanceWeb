<#include "layoutmacros.ftl"/>
<!DOCTYPE html>
<html>

    <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
        	<meta name="_csrf" content="${_csrf.token}"/>
                <!-- default header name is X-CSRF-TOKEN -->
                <meta name="_csrf_header" content="${_csrf.headerName}"/>      
<title>Notifications</title>
        <script src="/script/jquery-1.11.1.min.js"></script>
        <script src="/script/angular/1.2.26/angular.min.js"></script>
        <script src="/script/angular/1.2.26/angular-route.min.js"></script>
        <script src="/script/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href = "/style/bootstrap/3.2.0/bootstrap.min.css"/>
        <link rel="stylesheet" href = "/style/dashboard.css"/>
        <link rel="stylesheet" href = "/themes/${theme}/theme.css"/>
        <link rel="shortcut icon" href="favicon.ico" />

        <script src="/script/app/notifications/app.js"></script>
        <script src="/script/app/services/notificationservice.js"></script>



    </head>

    <body>

        <@showLayoutHeader 'notifications'/>

        <div ng-app="HFWNotificationsApp" ng-controller="notificationsController" id="main">
        <@buildMenu 'notifications'/>
            <div id="appMain">
                
                <div id="notificationList">
                               
                    <div>
                        <a href="#" ng-click="txnSortType = 'createdOn'; txnSortReverse = ! txnSortReverse">
                            Date 
                            <span ng-show="txnSortType == 'createdOn' && !txnSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="txnSortType == 'createdOn' && txnSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a href="#" ng-click="txnSortType = 'subject'; txnSortReverse = ! txnSortReverse">
                            Notification 
                            <span ng-show="txnSortType == 'subject' && !txnSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="txnSortType == 'subject' && txnSortReverse" class="fa fa-caret-up"></span>
                        </a>
                        <a href="#" ng-click="txnSortType = 'subject'; txnSortReverse = ! txnSortReverse">
                            Message 
                            <span ng-show="txnSortType == 'message' && !txnSortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="txnSortType == 'message' && txnSortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </div>

                    <div ng-repeat="x in notifications | orderBy:txnSortType:txnSortReverse" ng-click="" ng:class="{true:'list - group - item notifications unread',false:'list - group - item notifications'}[x.status == 0]">
                        <span>{{x.createdOn | date: 'yyyy-MM-dd HH:mm'}}</span>
                        <span>{{x.subject}}</span>
                        <span>{{x.message}}</span>

                    </div>
                        
                </div>


            </div>
        </div>

    </body>
</html>