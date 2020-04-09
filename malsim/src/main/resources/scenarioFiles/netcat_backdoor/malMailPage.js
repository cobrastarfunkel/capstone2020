function goToEmails() {
  // When using with java netcat_backdoor/malMailView.html
  // vsCode malMailView.html
  window.location.href = "netcat_backdoor/malMailView.html";
}

function changeMailTab(evt, tabName) {
  let x = document.getElementsByClassName("mail-tab");
  for (let i = 0; i < x.length; i++) {
    x[i].style.display = "none";
  }
  for (let i = 2; i >= 0; i--) {
    document.getElementById(tabName + "-" + i).style.display = "table-row";
  }
  let tabHeaders = document.getElementsByClassName("tab-header");
  for (let i = 0; i < tabHeaders.length; i++) {
    tabHeaders[i].className = tabHeaders[i].className.replace(" active", "");
  }
  evt.currentTarget.className += " active";
}

function openMail(mailId) {
  document.getElementById("mail-table").style.display =
    document.getElementById("mail-table").style.display == "none"
      ? "table"
      : "none";

  document.getElementById(mailId).style.display =
    document.getElementById(mailId).style.display == "grid" ? "none" : "grid";
}

function installMalware() {
  document.getElementById("download-popup").style.display = "flex";
}

function closeDownload() {
  document.getElementById("download-popup").style.display = "none";
}

function nextPage() {
  window.location.href = "metasploitExplanation.html";
}
