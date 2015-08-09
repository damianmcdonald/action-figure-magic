actionFigureApp.controller('CharactersController', ['$scope', '$http','$routeParams', '$modal', '$log', 'validation', function($scope, $http, $routeParams, $modal, $log, validation) {

    $scope.alerts = new Array();

    function getApi(isHero) {
        var api;
        if(isHero) {
            api = "action-figure-magic/characters";
        } else {
            api = "action-figure-magic/characters/error";
        }
        return api;
    }

    function postMultiPartFormData(file, payload, successMsg, isHero) {
        var fd = new FormData();
        fd.append('cover', file);
        fd.append('character', JSON.stringify(payload));
        $http.post(getApi(isHero), fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function(response) {
            $scope.alerts.push({ type: 'success', msg: successMsg });
        }, function(response) {
            var alertMsg = errorMessage(response.status, response.data);
            $scope.alerts.push({ type: 'danger', msg: alertMsg });
        });
    }

    function deleteCharacter(character) {
        $http.delete(getApi(character.isHero)+"/"+character.id).
        then(function(response) {
            var alertMsg = successMessage(character);
            $scope.alerts.push({ type: 'success', msg: alertMsg });
        }, function(response) {
            var alertMsg = errorMessage(response.status, response.data);
            $scope.alerts.push({ type: 'danger', msg: alertMsg });
        });
    }

    function postCharacter(character) {
        var payload;
        if(character.mode === 'edit') {
            payload = { "id": character.id, "name": character.name, "isHero": character.isHero, "description": character.description }
        } else {
            payload = { "name": character.name, "isHero": character.isHero, "description": character.description }
        }
        var successMsg = successMessage(character);
        var f = document.getElementById("characterImageFile").files[0];
        postMultiPartFormData(f, payload, successMsg, character.isHero);
    }

    function putCharacter(character) {
        var payload = { "name": character.name, "isHero": character.isHero, "description": character.description }
        $http.put(getApi(character.isHero)+"/"+character.id, payload).
        then(function(response) {
            var alertMsg = successMessage(character);
            $scope.alerts.push({ type: 'success', msg: alertMsg });
        }, function(response) {
            var alertMsg = errorMessage(response.status, response.data);
            $scope.alerts.push({ type: 'danger', msg: alertMsg });
        });
    }

    $scope.open = function (mode, character) {
        $scope.character = character;
        $scope.character.mode = mode;
        var modalInstance = $modal.open({
          animation: true,
          templateUrl: 'modalContent.html',
          controller: 'CharactersModalInstanceController',
          size: 'lg',
          resolve: {
            character: function () {
              return $scope.character;
            }
          }
        });

        modalInstance.result.then(function (character) {
           if(character.mode === "delete") {
              deleteCharacter(character);
              return;
           }

           if(character.mode === "add") {
              postCharacter(character);
              return;
           }

           if(character.mode === "edit") {
            if(validation.hasImage('modalImage')) {
              postCharacter(character);
              return;
            }
              putCharacter(character);
              return;
           }
        }, function () {
          $log.info('Modal dismissed at: ' + new Date());
        });
  };

  $scope.closeAlert = function(index) {
    $scope.alerts.splice(index, 1);
  };

  // Pagination
  $scope.filteredCharacters = []
  ,$scope.currentPage = 1
  ,$scope.numPerPage = 6;

  $http.get("action-figure-magic/characters/"+$routeParams.toylineId).
  then(function(response) {
     $scope.characters = response.data;
     registerPagination();
  }, function(response) {
      var alertMsg = errorMessage(response.status, response.data);
      $scope.alerts.push({ type: 'danger', msg: alertMsg });
  });

  function registerPagination() {
     $scope.$watch('currentPage + numPerPage', function() {
    var begin = (($scope.currentPage - 1) * $scope.numPerPage)
    , end = begin + $scope.numPerPage;
    
    $scope.filteredCharacters = $scope.characters.characters.slice(begin, end);

  });
}
  
  // End of pagination

}]);


actionFigureApp.controller('CharactersModalInstanceController', function ($scope, $modalInstance, validation, character) {
  $scope.character = character
  $scope.ok = function () {
    if(character.mode === 'delete') {
      $modalInstance.close($scope.character);
      return;
    }

    // validate the form
    var isFormValid = validation.validateCharacterForm(character.mode);
    if(!isFormValid) return;
    $modalInstance.close($scope.character);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});
