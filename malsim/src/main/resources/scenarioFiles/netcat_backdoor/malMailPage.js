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
