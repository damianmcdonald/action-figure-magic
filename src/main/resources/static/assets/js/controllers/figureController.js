actionFigureApp.controller('FigureController', ['$scope', '$http','$routeParams', '$modal', '$log', function($scope, $http, $routeParams, $modal, $log) {

var toylineName = toylineIdToName($routeParams.toylineId);

$http.get("action-figure-magic/figures").
  then(function(response) {
    var id = $routeParams.figureId
    var figure = (function () {
      var _figure = null;
      for(var i=0; i<response.data.figures.length; i++) {
        if(response.data.figures[i].id == id) {
          _figure = response.data.figures[i];
          break;
        }
      }
      return _figure;
    })();
    if(figure == null) {
      console.log("Unable to find figure for id: " + id);
    }
    $scope.figure = figure;
    $scope.figure.toylineId = $routeParams.toylineId;
    $scope.figure.toylineName = toylineName;
  }, function(response) {
      var alertMsg = errorMessage(response.status, response.statusText);
      $scope.alerts.push({ type: 'danger', msg: alertMsg });
  });

}]);
