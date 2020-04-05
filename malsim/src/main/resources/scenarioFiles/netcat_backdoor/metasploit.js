var counter = 0;
var hasUploadednc = false;

function takeInput(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    let inputVal = document.getElementById("console-input-" + counter).value;

    switch (inputVal) {
      case "?":
        formatConsole("help-text");
        addConsolePrompt("msf-console-prompt");
        break;

      case "use handler":
        formatConsole("use-handler");
        addConsolePrompt("meterpreter-console-prompt");
        break;

      case "search windows":
        formatConsole("search-windows");
        addConsolePrompt("msf-console-prompt");
        break;

      default:
        formatConsole("command-not-found");
        addConsolePrompt("msf-console-prompt");
    }
  }
}

function takeMeterpreterInput(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    let inputVal = document.getElementById("console-input-" + counter).value;

    switch (inputVal) {
      case "?":
        formatConsole("meterpreter-help-text");
        addConsolePrompt("meterpreter-console-prompt");
        break;

      case "back":
        formatConsole("help-text");
        addConsolePrompt("msf-console-prompt");
        break;

      case "upload netcat":
        formatConsole("meterpreter-netcat");
        addConsolePrompt("meterpreter-console-prompt");
        hasUploadednc = true;
        break;

      case "reg setval":
        if (hasUploadednc) {
          formatConsole("meterpreter-regkey");
          addConsolePrompt("meterpreter-console-prompt");
          break;
        }
        formatConsole("meterpreter-help-upload");
        addConsolePrompt("meterpreter-console-prompt");
        break;

      default:
        formatConsole("command-not-found");
        addConsolePrompt("meterpreter-console-prompt");
    }
  }
}

function formatConsole(elementId) {
  let node = document.getElementById(elementId);
  let clone = node.cloneNode(true);
  clone.style.display = "inline";
  document.getElementById("msf-console").appendChild(clone);
}

function addConsolePrompt(promptName) {
  let mainDivNode = document.getElementById(promptName + "-0");
  mainDivNode.style.display = "";
  let nextWindow = ++counter;

  let clone = mainDivNode.cloneNode(true);
  let newId = "";
  newId = promptName + "-" + nextWindow;
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