$dest = "C:\Users\" + $env:USERNAME + "\Desktop\"
Copy-Item ".\src\main\resources\scenarioFiles\word_macro\Malsim_Macro.docm" -Destination $dest