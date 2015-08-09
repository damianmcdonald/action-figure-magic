actionFigureApp.controller('AutoCompleteController', ['$scope', '$http', '$location', function ($scope, $http, $location) {

    $scope.selected = undefined;

    $http.get("action-figure-magic/figures").success(function(data) {
        var figures = new Array();
        for(var i=0; i<data.figures.length; i++) {
            figures.push({ "name": data.figures[i].name, "image": data.figures[i].image, "id": data.figures[i].id, "toylineId": data.figures[i].toylineId });
        }
        $scope.figures = figures
    });

    $scope.onSelect = function ($item, $model, $label) {
        $scope.$item = $item;
        $scope.$model = $model;
        $scope.$label = $label;
        console.log("Redirecting to: " + 'index.html#/figures/'+$scope.$item.toylineId+'/'+$scope.$item.id);
        $location.path('/figures/'+$scope.$item.toylineId+'/'+$scope.$item.id);
    };

}]);