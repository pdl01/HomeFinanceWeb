<!DOCTYPE html>
<html>

    <head>



    </head>

    <body>
        <script src="/HFW/script/app/settings/app.js"></script>
        <script src="/HFW/script/app/settings/services.js"></script>

        <div ng-app="HFWSettingsApp" ng-controller="settingsController" id="main">
            <div id="appMenu">
                <ul>
                    <li class="menuItem" id="menuItemNewAccount" >&nbsp;</li>
                </ul>
            </div>
            <div></div>
            <div id="application_repository_config" class="settingsSection">
                <select id="type">
                    <option value="mongo">Mongo</option>
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

        </div>

    </body>
</html>