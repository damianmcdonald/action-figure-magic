function handleFileSelect(elemId, renderTo) {
    var f = document.getElementById(elemId).files[0];
    if (!f.type.match('image.*')) return;
    var reader = new FileReader();
    // Closure to capture the file information.
    reader.onload = (function(theFile) {
      return function(e) {
        // Render thumbnail.
        document.getElementById(renderTo).src = e.target.result;
      };
    })(f);
    // Read in the image file as a data URL.
    reader.readAsDataURL(f);
}

function successMessage(item) {
  // TODO replace with server response message
  var msgText = (function() {
      if(item.mode === "edit") return { "name": item.name, "mode": "edited" };
      if(item.mode === "add") return { "name": item.name, "mode": "added" };
      if(item.mode === "delete") return { "name": item.name, "mode": "deleted" };
      return { "name": "unknown", "mode": "unknown" };;
  })();
 return msgText.name + " has been successfully " + msgText.mode;
}

function errorMessage(status, errorMsg) {
 return "Error " + status + " has occured: " + errorMsg;
}

function toylineIdToName(id) {
  switch (id) {
      case "1":
          return "Kenner's Star Wars"
       case "2":
          return "Mattel's Master of the Universe"
      case "3":
          return "Hasbro's Transformers"
      case "4":
          return "Kenner's M.A.S.K."
      case "5":
          return "Hasbro's G.I. Joe"
      case "6":
          return "Playmates' Teenage Mutant Ninja Turtles"
      default:
          return "Kenner's Star Wars"
  }
}