<#include "inputforms.ftl"/>
<#include "layoutmacros.ftl"/>
<!DOCTYPE html>
<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>      
        <title>Version</title>
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
        <script src="/script/app/version/app.js"></script>

        <script src="/script/app/services/version.js"></script>

    </head>

    <body ng-app="HFWVersionApp">

        <@showLayoutHeader 'version'/>

        <div ng-controller="versionController" id="main">

            <@buildMenu 'version'/>

            <div id="appMain">
                <div ng-repeat="x in components" ng-click="" ng:class="">
                    {{x.name}} Version: {{x.version.majorVersion}}.{{x.version.minorVersion}} Valid Until:  {{x.validUntil}}
                </div>
            </div>
        </div>
    </body>
</html>
