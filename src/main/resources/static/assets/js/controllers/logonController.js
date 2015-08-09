actionFigureApp.controller('LogonController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.username, $scope.password;
    $scope.login = function () {
        var credentials = JSON.stringify({ username: $scope.username, password: $scope.password });
        $http.post("action-figure-magic/logon/authorize", credentials, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'Authorization': 'Basic ' + window.btoa(unescape(encodeURIComponent($scope.username + ':' + $scope.password ))) }
        }).
        then(function(response) {
                $location.path('/eastereggs');
        }, function(response) {
                $location.path('/unauthorized');
        });
    };
}]);