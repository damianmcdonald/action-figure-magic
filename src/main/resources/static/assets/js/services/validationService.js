actionFigureApp.factory('validation', function () {

    var validationService = {};

    validationService.validateToylineForm = function(mode) {
      // remove exisiting validaton errors
      removeValidationStateErrors('toylineManufacturerFormGroup');
      removeValidationStateErrors('toylineNameFormGroup');
      removeValidationStateErrors('toylineImageFormGroup');
      hideElement('toylineManufacturerIcon');
      hideElement('toylineNameIcon');
      hideElement('toylineImageIcon');

      var hasManufacturer = hasValue('toylineManufacturer');
      var hasName = hasValue('toylineName');
      var hasImage = containsImage('modalImage');

      if(mode === 'edit') {
        if(!hasManufacturer) {
          addValidationStateErrors('toylineManufacturerFormGroup');
          showElement('toylineManufacturerIcon');
          return false;
        }
        if(!hasName) {
          addValidationStateErrors('toylineNameFormGroup');
          showElement('toylineNameIcon');
          return false;
        }
      }

      if(mode === 'add') {
        if(!hasManufacturer) {
          addValidationStateErrors('toylineManufacturerFormGroup');
          showElement('toylineManufacturerIcon');
          return false;
        }
        if(!hasName) {
          addValidationStateErrors('toylineNameFormGroup');
          showElement('toylineNameIcon');
          return false;
        }
        if(!hasImage) {
          addValidationStateErrors('toylineImageFormGroup');
          showElement('toylineImageIcon');
          return false;
        }
      }

      return true;
  };

validationService.validateCharacterForm = function(mode) {

  // remove exisiting validaton errors
  removeValidationStateErrors('characterNameFormGroup');
  removeValidationStateErrors('characterImageFormGroup');
  removeValidationStateErrors('characterDescriptionFormGroup');
  hideElement('characterNameIcon');
  hideElement('characterImageIcon');
  hideElement('characterDescriptionIcon');

  var hasName = hasValue('characterName');
  var hasImage = containsImage('modalImage');
  var hasDescription = hasValue('characterDescription');

  if(mode === 'edit') {
    if(!hasName) {
      addValidationStateErrors('characterNameFormGroup');
      showElement('characterNameIcon');
      return false;
    }
    if(!hasDescription) {
      addValidationStateErrors('characterDescriptionFormGroup');
      showElement('characterDescriptionIcon');
      return false;
    }
  }

  if(mode === 'add') {
    if(!hasName) {
      addValidationStateErrors('characterNameFormGroup');
      showElement('characterNameIcon');
      return false;
    }
    if(!hasDescription) {
      addValidationStateErrors('characterDescriptionFormGroup');
      showElement('characterDescriptionIcon');
      return false;
    }
    if(!hasImage) {
      addValidationStateErrors('characterImageFormGroup');
      showElement('characterImageIcon');
      return false;
    }
  }

  return true;
};

function removeValidationStateErrors(elemId) {
  if ( document.getElementById(elemId) != null ) {
    document.getElementById(elemId).classList.remove("has-error");
    document.getElementById(elemId).classList.remove("has-feedback");
  }
}

function addValidationStateErrors(elemId) {
  if ( document.getElementById(elemId) != null ) {
    document.getElementById(elemId).classList.add("has-error");
    document.getElementById(elemId).classList.add("has-feedback");
  }
}

function hideElement(elemId) {
  if ( document.getElementById(elemId) != null ) {
    document.getElementById(elemId).classList.add("hide");
  }
}

function showElement(elemId) {
  if ( document.getElementById(elemId) != null ) {
    document.getElementById(elemId).classList.remove("hide");
  }
}

function hasValue(elemId) {
 var name = document.getElementById(elemId);
  if (name !== null 
          && typeof name.value !== 'undefined' 
          && name.value !== ""
          && name.value !== null) {
      return true;
  }
  return false;
}

function containsImage(elemId) {
  var name = document.getElementById(elemId);
  if (name !== null
      && typeof name !== 'undefined'
      && name.src !== null
      && typeof name.src !== 'undefined'
      && name.src.indexOf("data:image/") > -1) {
    return true;
  }
  return false;
}

validationService.hasImage = function(elemId) {
  return containsImage(elemId);
}

  return validationService;

});