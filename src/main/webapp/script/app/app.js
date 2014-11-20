angular.module('HFWApp', [
  'HFWApp.controllers',
  'ngRoute'
]).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/accounts', {templateUrl: '/HFW/script/app/partials/accounts.html',   controller: "accountController"}).
      otherwise({redirectTo: '/accounts'});
}]);