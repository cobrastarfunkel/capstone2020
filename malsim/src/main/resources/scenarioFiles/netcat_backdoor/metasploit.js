var counter = 0;

function takeInput(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    let inputVal = document.getElementById("console-input-" + counter).value;
    let elemTag = "";
    let textLine = "";

    switch (inputVal) {
      case "?":
        elemTag = "p";
        textLine = "Type ? for this help text<br> Type use handler";
        break;

      case "use handler":
        elemTag = "p";
        textLine = "Another Line";
        break;

      default:
        elemTag = "p";
        textLine = "Command not found";
    }
    formatConsole(elemTag, textLine);
    addConsolePrompt();
  }
}

function formatConsole(elemTag, textLine) {
  let node = document.createElement(elemTag);
  let textNode = document.createTextNode(textLine);
  node.appendChild(textNode);
  document.getElementById("msf-console").appendChild(node);
}

function addConsolePrompt() {
  let mainDivNode = document.getElementById("msf-console-prompt-0");
  let prevWindow = counter;
  let nextWindow = ++counter;
  let clone = mainDivNode.cloneNode(true);
  let newId = "";
  console.log("prevWindow: " + prevWindow);

  newId = "msf-console-prompt-" + nextWindow;
  //document.getElementById("console-input-" + prevWindow).disabled = true;
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
