restMagicApp.controller('RegisteredApiCtrl', ['$scope', '$http', function($scope, $http) {

  $http.get('/restmagic/api/registry').
      then(function(response) {
        $scope.directory = response.data;
        $scope.serverPrefix = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');
      }, function(response) {
        alert("Error: " + response.status + " has occurred: " + response.statusText);
      });

}]);