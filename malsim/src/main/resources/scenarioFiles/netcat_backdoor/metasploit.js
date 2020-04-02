var counter = 0;

function takeInput(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    let inputVal = document.getElementById("console-input-" + counter).value;
    let elemTag = "";
    let textLine = "";

    switch (inputVal) {
      case "?":
        formatConsole("help-text");
        addConsolePrompt();
        break;

      case "use handler":
        formatConsole("use-handler");
        meterpreterPrompt();
        break;

      case "search windows":
        formatConsole("search-windows");
        addConsolePrompt();
        break;

      case "use exploit":
        formatConsole("use-exploit");
        addConsolePrompt();
        break;

      default:
        formatConsole("command-not-found");
        addConsolePrompt();
    }
  }
}

function formatConsole(elementId) {
  let node = document.getElementById(elementId);
  let clone = node.cloneNode(true);
  clone.style.display = "inline";
  document.getElementById("msf-console").appendChild(clone);
}

function addConsolePrompt() {
  let mainDivNode = document.getElementById("msf-console-prompt-0");
  let prevWindow = counter;
  let nextWindow = ++counter;

  let clone = mainDivNode.cloneNode(true);
  let newId = "";
  newId = "msf-console-prompt-" + nextWindow;
  clone.id = newId;
  document.getElementById("msf-console").appendChild(clone);

  let cloneChild = document.getElementById(newId).childNodes;
  cloneChild[3].firstChild.nextSibling.id = "console-input-" + nextWindow;

  let consoleInput = document.getElementById("console-input-" + counter);
  consoleInput.focus();
  consoleInput.value = "";
  consoleInput.disabled = false;
}

function meterpreterPrompt() {

}