var actionFigureApp = angular.module('actionFigureApp', [
    'ngRoute',
    'ngSanitize',
    'ui.bootstrap'
    ]
);

actionFigureApp.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
  when('/toylines', {
    templateUrl: 'assets/partials/toylines.html',
    controller: 'ToyLinesController'
  }).
  when('/eastereggs', {
    templateUrl: 'assets/partials/eastereggs.html',
    controller: 'EasterEggsController'
  }).
  when('/characters/:toylineId', {
    templateUrl: 'assets/partials/characters.html',
    controller: 'CharactersController'
  }).
  when('/figures/:toylineId/:figureId', {
    templateUrl: 'assets/partials/figure.html',
    controller: 'FigureController'
  }).
   when('/logon', {
    templateUrl: 'assets/partials/logon.html',
    controller: 'LogonController'
  }).
   when('/unauthorized', {
    templateUrl: 'assets/partials/unauthorized.html'
  }).
  otherwise({
    redirectTo: '/toylines'
  });
}]);