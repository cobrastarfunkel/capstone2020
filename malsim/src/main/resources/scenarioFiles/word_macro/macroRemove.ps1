$macro_path = "C:\Users\" + $env:USERNAME + "\Desktop\Malsim_Macro.docm"
$report_path = "C:\Users\" + $env:USERNAME + "\Desktop\MALSIM.txt"

if (Test-Path $macro_path) {
  Remove-Item $macro_path
}

if (Test-Path $report_path) {
  Remove-Item $report_path
}