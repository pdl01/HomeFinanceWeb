<!DOCTYPE html>
<html>

    <head>



    </head>

    <body>


        <script src="script/app/settings/app.js"></script>
        <script src="script/app/settings/services.js"></script>

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
                <select id="theme">
                    <option value="default">Default</option>
                    <option value="dark">Dark</option>
                </select><br>
                <button ng-click="saveThemeInformation(">Save</button>
            </div>
            <div id="basic_security_config" class="settingsSection">
                Enabled:<input type="checkbox" value="true" ng-model="basicSecurityFormData.enabled"/><br/>
                Password:<input type="text" ng-model="basicSecurityFormData.password"/><br>
                <button ng-click="saveBasicSecurityInformation()">Save</button>
            </div>
            <div id="security_config" class="settingsSection">
            </div>

        </div>

    </body>
</html>