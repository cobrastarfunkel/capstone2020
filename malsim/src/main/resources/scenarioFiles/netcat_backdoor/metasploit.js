var counter = 0;

function takeInput(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    let inputVal = document.getElementById("console-input").value;
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

  let clone = mainDivNode.cloneNode(true);
  clone.id = "msf-console-prompt-" + counter;

  if (counter - 1 > 0) {
    document.getElementById(
      "msf-console-prompt-" + counter - 1
    ).disabled = true;
  } else {
    document.getElementById("msf-console-prompt-0").disabled = true;
  }

  document.getElementById("msf-console").appendChild(clone);
  counter++;
  console.log(clone.id);
  //mainDivNode.setAttribute("class", "msf-console-prompt");
  //mainDivNode.setAttribute("id", "msf-console-prompt-" + consoleCount());
}
