### Initial Database Setup
Creating Database works, Table can be created, Files can be imported into table by storing them in a directory.  Set the path in the Scenario class.
/src/main/resources/configFiles have examples of how we can use yaml to build scenarios.  These work, if we need to add / remove fields we can.

### Using Current Code
The process at the moment is a Scenario object is created using Jackson (A YAML Parser for Java).  The Scenarios are built using fields in a config file stored in /src/main/resources/conifgFiles  (Example Attached To Jira Ticket CC-13 and in Confluence)

Drop a file in there that follows the format of the two example Scenario config files and then place the Scenario files in the /src/main/resources/scenarioFiles directory under the path you defined in the config file.

The ScenarioBuilder class handles the creation of the Scenarios.  
The SqliteDatabse class builds the databse and the respective Tables classes will build the tables (Not all of the tables are built yet). 
<br/><br/>The following will create the database and two tables from the config files:

    SqliteDatabase sqliteDB = new SqliteDatabase("filesDb.sqlite");
    sqliteDB.createDatabase();
    sqliteDB.createTables();
