/** Start of ToyLinesController **/

actionFigureApp.controller('EasterEggsController', ['$scope', '$http', function($scope, $http) {

$http.get('action-figure-magic/eastereggs').
  then(function(response) {
    $scope.toylines = response.data.toylines;
  }, function(response) {
      var alertMsg = errorMessage(response.status, response.statusText);
      $scope.alerts.push({ type: 'danger', msg: alertMsg });
  });

}]);
