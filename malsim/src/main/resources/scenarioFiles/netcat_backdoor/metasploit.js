function takeInput(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    var inputVal = document.getElementById("console-input").value;

    switch (inputVal) {
      case "?":
        alert(inputVal);
    }
  }
}
