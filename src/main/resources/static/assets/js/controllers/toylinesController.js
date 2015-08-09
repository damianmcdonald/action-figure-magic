/** Start of ToyLinesController **/

actionFigureApp.controller('ToyLinesController', ['$scope', '$http', '$modal', '$log', 'validation', 
                                                    function($scope, $http, $modal, $log, validation) {

    $scope.alerts = new Array();

    function postMultiPartFormData(file, payload, successMsg) {
        var fd = new FormData();
        fd.append('cover', file);
        fd.append('toyline', JSON.stringify(payload));
        $http.post("action-figure-magic/toylines", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function(response) {
            $scope.alerts.push({ type: 'success', msg: successMsg });
        }, function(response) {
            var alertMsg = errorMessage(response.status, response.data);
            $scope.alerts.push({ type: 'danger', msg: alertMsg });
        });
    }

    function deleteToyline(toyline) {
        $http.delete("action-figure-magic/toylines/"+toyline.id).
        then(function(response) {
            var alertMsg = successMessage(toyline);
            $scope.alerts.push({ type: 'success', msg: alertMsg });
        }, function(response) {
            var alertMsg = errorMessage(response.status, response.data);
            $scope.alerts.push({ type: 'danger', msg: alertMsg });
        });
    }

    function postToyline(toyline) {
        var payload;
        if(toyline.mode === 'edit') {
            payload = { "id": toyline.id, "name": toyline.name, "manufacturer": toyline.manufacturer }
        } else {
            payload = { "name": toyline.name, "manufacturer": toyline.manufacturer }
        }
        var successMsg = successMessage(toyline);
        var f = document.getElementById("toylineImageFile").files[0];
        postMultiPartFormData(f, payload, successMsg);
    }

    function putToyline(toyline) {
        var payload = { "name": toyline.name, "manufacturer": toyline.manufacturer }
        $http.put("action-figure-magic/toylines/"+toyline.id, payload).
        then(function(response) {
            var alertMsg = successMessage(toyline);
            $scope.alerts.push({ type: 'success', msg: alertMsg });
        }, function(response) {
            var alertMsg = errorMessage(response.status, response.data);
            $scope.alerts.push({ type: 'danger', msg: alertMsg });
        });
    }

    $scope.open = function (mode, toyline) {
        $scope.toyline = toyline;
        $scope.toyline.mode = mode;
        var modalInstance = $modal.open({
          animation: true,
          templateUrl: 'modalContent.html',
          controller: 'ToylinesModalInstanceController',
          size: 'lg',
          resolve: {
            toyline: function () {
              return $scope.toyline;
            }
          }
        });

        modalInstance.result.then(function (toyline) {
           if(toyline.mode === "delete") {
              deleteToyline(toyline);
              return;
           }

           if(toyline.mode === "add") {
               postToyline(toyline);
              return;
           }

           if(toyline.mode === "edit") {
            if(validation.hasImage('modalImage')) {
              postToyline(toyline);
              return;
            }
              putToyline(toyline);
              return;
           }
        }, function () {
          $log.info('Modal dismissed at: ' + new Date());
        });
  };

  $scope.closeAlert = function(index) {
    $scope.alerts.splice(index, 1);
  };

  $http.get('action-figure-magic/toylines').
  then(function(response) {
    $scope.toylines = response.data.toylines;
  }, function(response) {
      var alertMsg = errorMessage(response.status, response.data);
      $scope.alerts.push({ type: 'danger', msg: alertMsg });
  });

}]);

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.
actionFigureApp.controller('ToylinesModalInstanceController', function ($scope, $modalInstance, validation, toyline) {
  $scope.toyline = toyline;
  $scope.ok = function () {
    if(toyline.mode === 'delete') {
      $modalInstance.close($scope.toyline);
      return;
    }

    // validate the form
    var isFormValid = validation.validateToylineForm(toyline.mode);
    if(!isFormValid) return;
    $modalInstance.close($scope.toyline);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});

/** End of ToyLinesController **/