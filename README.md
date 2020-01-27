### Initial Database Setup
Creating Database works, Table can be created, Files can be imported into table by storing them in a directory.  Set the path in the Scenario class.
/src/main/resources/configFiles have examples of how we can use yaml to build scenarios.  These work, if we need to add / remove fields we can.  At the moment the database only has the encrypted executable defined in the config file and an ID number.

**TODO:** 
* ~~Encrypt File before storing~~
* ~~Decrypt file when retrieving~~
* ~~Convert file back to String from byte[]~~ 
* More Test cases
* Have entire scenario object exist in Database and use the database to build scenarios as they are called?  That way executables can be removed from the resources directory.  Everything else can be stored as a Path in the DB.
