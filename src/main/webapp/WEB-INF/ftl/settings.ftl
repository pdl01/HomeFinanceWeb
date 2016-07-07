<#include "layoutmacros.ftl"/>
<!DOCTYPE html>
<html>

    <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
        	<meta name="_csrf" content="${_csrf.token}"/>
                <!-- default header name is X-CSRF-TOKEN -->
                <meta name="_csrf_header" content="${_csrf.headerName}"/>      
<title>Settings</title>
        <script src="/script/jquery-1.11.1.min.js"></script>
        <script src="/script/angular/1.2.26/angular.min.js"></script>
        <script src="/script/angular/1.2.26/angular-route.min.js"></script>
        <script src="/script/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href = "/style/bootstrap/3.2.0/bootstrap.min.css"/>
        <link rel="stylesheet" href = "/style/dashboard.css"/>
        <link rel="stylesheet" href = "/themes/${theme}/theme.css"/>
        <link rel="shortcut icon" href="favicon.ico" />



    </head>

    <body>
        <script src="/script/app/settings/app.js"></script>
        <script src="/script/app/settings/services.js"></script>
        <@showLayoutHeader 'settings'/>

        <div ng-app="HFWSettingsApp" ng-controller="settingsController" id="main">

            <@buildMenu 'settings'/>

<div id="appMain">
            <div id="application_repository_config" class="settingsSection">
                <select id="type" ng-model="repositoryFormData.type">
                    <option value="mongo">Mongo</option>
                    <option value="LWDataStore">LW Datastore</option>
                </select>
                <br>
                Host: <input type="text" ng-model="repositoryFormData.host"/><br>
                Port: <input type="text" ng-model="repositoryFormData.port"/><br>
                Database:<input type="text" ng-model="repositoryFormData.database"/><br>
                User:<input type="text" ng-model="repositoryFormData.username"/><br>
                Password:<input type="text" ng-model="repositoryFormData.password"/><br>
                <button ng-click="saveRepositoryInformation()">Save</button>
            </div>
            <div id="theme_config" class="settingsSection">
                <select id="theme"ng-model="themeFormData.theme">
                    <option value="default">Default</option>
                    <option value="dark">Dark</option>
                    <option value="arctic">Arctic</option>
                </select><br>
                <button ng-click="saveThemeInformation()">Save</button>
            </div>
            <div id="basic_security_config" class="settingsSection">
                Enabled:<input type="checkbox" value="true" ng-model="basicSecurityFormData.enabled"/><br/>
                Password:<input type="text" ng-model="basicSecurityFormData.password"/><br>
                <button ng-click="saveBasicSecurityInformation()">Save</button>
            </div>
            <div id="limited_user_config">
                <div ng-repeat="x in users" class="list-group-item limitedUsers">
                    User<input type="text" value="{{x.username}}"/> Password<input type="text" value="{{x.password}}"/>
                </div>
                
            </div>
            <div id="category_config" class="settingsSection">
                <div ng-repeat="x in categories" class="list-group-item registryTransaction">
                    <span>{{x}}</span>
                </div>
            </div>
            <div id="security_config" class="settingsSection">
            </div>
            <div id="version_config" class="settingsSection">
                <div id="sectionVersion"></section>
            </div>
            <div id="license_config" class="settingsSection">
            </div>

        </div>
</div>
    </body>
</html>