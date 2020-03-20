var counter = 0;

function takeInput(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    let inputVal = document.getElementById("console-input-" + counter).value;
    let elemTag = "";
    let textLine = "";

    switch (inputVal) {
      case "?":
        elementId = "help-text";
        break;

      case "use handler":
        elementId = "use-handler";
        break;

      case "search windows":
        elementId = "search-windows";
        break;

      case "use exploit":
        elementId = "use-exploit";
        break;

      default:
        elementId = "command-not-found";
    }
    formatConsole(elementId);
    addConsolePrompt();
  }
}

function formatConsole(elementId) {
  let node = document.getElementById(elementId);
  let clone = node.cloneNode(true);
  console.log(clone);
  clone.style.display = "inline";
  document.getElementById("msf-console").appendChild(clone);
}

function addConsolePrompt() {
  let mainDivNode = document.getElementById("msf-console-prompt-0");
  let prevWindow = counter;
  let nextWindow = ++counter;
  let clone = mainDivNode.cloneNode(true);
  let newId = "";
  console.log("prevWindow: " + prevWindow);

  newId = "msf-console-prompt-" + nextWindow;

  console.log("else: " + newId + " counter: " + prevWindow);

  clone.id = newId;
  document.getElementById("msf-console").appendChild(clone);
  let cloneChild = document.getElementById(newId).childNodes;
  cloneChild[3].firstChild.nextSibling.id = "console-input-" + nextWindow;
  document.getElementById("console-input-" + counter).focus();
  document.getElementById("console-input-" + counter).value = "";
  document.getElementById("console-input-" + counter).disabled = false;
  console.log("console-input-" + prevWindow);
}
