# Malsim

## TOC
* [About This Program](#About-This-Program)
* [Project Setup](#Project-Setup)
* [Using the Database](#Using-the-Database)
* [Adding Tables to the Database](#Adding-Tables-to-the-Database)
* [Confluence Links](#Confluence-Links)

## About This Program
###### [Return to Top](#Malsim)
#### What it is:
The Malware Simulator is a training tool designed for use in an entry level cybersecurity
course to help teach students to recognize different types of malware and attacks. On the landing
page you will see a list of scenarios that you can choose from to learn about a particular type
of attack. You will see an on-screen-indicator in the bottom left of the screen that will be green
if you are in a VM indicating the program is completely safe to run. It will be red if you are
running the simulator on a physical machine. Proceed with caution.

#### How to use it:
Choose a scenario you would like to complete and select "Open Scenario" at the bottom of the screen
Wait a few moments as some scenarios need time to set things up in the background. Once in a scenario
page step through carefully and read everything thoroughly. Each scenario is unique and tasks you need
to complete will be described on that page. After completing your walkthrough indicate that you have
finished by selecting "Complete" at the top of the screen. Return to the home page by selecting "Home"
The "Run Malware" button is currently unused in the scenarios we have created so far.

## Project Setup
###### [Return to Top](#Malsim)
###### Confluence Link for this Section [here](https://iancobia.atlassian.net/wiki/spaces/CC/pages/167084033/Project+Setup)


The following should give you a runnable copy of this project.
##### Requirements:
* Windows 10 (With Admin Privileges)
* Java jdk-8u241
* eclipse
* mingw
* git bash (Optional but recommended)

### Windows10
You can get a free copy of Windows 10 by selecting “I don’t have a product key” during installation.  After that just install it like normal.  
1. Download the Windows10 Installation Media Creation Tool [here](https://www.microsoft.com/en-us/software-download/windows10)
1. Next follow the instructions labeled *"Using the tool to create installation media (USB flash drive, DVD, or ISO file) to install Windows 10 on a different PC (click to show more or less information)"* on the download page to create an iso and then use that iso to install Windows10 on a VM (Recommended, but you can use a computer with Windows 10 if you want).

### Java
1. Go [here](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) to download the correct verison of Java (If you don’t have an Oracle account you’ll need to create one) jdk-8u241 Windows x64
1. Once it downloads step through the installation procedure.  Install Java wherever you want
1. After install you can open a command line and type java to verify it was added to the Path and installed correctly

### Eclipse
1. Go [here](https://www.eclipse.org/downloads/) to download Eclipse
1. Install the Eclipse IDE forJava Developers version from the selection screen
1. Tell it where you installed Java on the next screen and then click Install
1. Right click on whatever Eclipse shortcut you plan to use to access it
1. Click **Properties → Compatibillity → Run** this program as an administrator
   1. Alternatively right click and click run as admini each time you open eclipse
1. Launch Eclipse and select your workspace.  This is where the project will be cloned
1. Click **Help → Eclipse MarketPlace**
1. In find type fx
1. Look for e(fx)clipse 3.6.0 and install it
1. Restart eclipse

### Mingw
1. Go [here](https://osdn.net/projects/mingw/downloads/68260/mingw-get-setup.exe/) and use the exe you download to install it.
1. Follow [these](http://www.mingw.org/wiki/Getting_Started) instructions.


*If you didn’t install git for windows, download the zip of the project from git hub or get the project into the eclipse workspace somehow and skip the next section.*

#### Git for Windows
1. Go [here](https://git-scm.com/downloads) to download
1. Install it (Defaults are fine)
1. run it
1. cd into the eclipse workspace and clone the repo

### Getting the Project in Eclipse
1. Open Eclipse if it isn’t already
1. On the left click **Import projects…**
1. Select **Gradle → Existing Gradle Project** from the list
1. Click next until it asks for a Project Root Directory
   1. Click **Browse** and select the location of the project we cloned before.  Make sure you select the malsim directory then click **Finish**
   1. You may need to close eclipse and re-open it after the project finishes importing
   1. If you get an error about ‘Auto share git projects’ ignore it
1. Expand the **src/main/java** tab then expand the **simulator** package
1. Right click **Main.java** and select **Run As → Java Application**

The project should now be running. The database will be located in **eclipse-workspace\capstone-project-conspicuous\malsim\src\main\resources\databases** and can be inspected with a sqlite browser like the one found here. 

Feel free to delete the database if you want to reset progress.  It will be created automatically once the program is run again.


*If anything is added to code outside of Java; so Scenarios mostly; the database must be deleted to reflect the changes.*


## Using the Database
###### [Return to Top](#Malsim)
###### Confluence Link for this Section [here](https://iancobia.atlassian.net/wiki/spaces/CC/pages/39845890/Using+the+Database)

#### Current Process
The process at the moment is a Scenario object is created using Jackson (A YAML Parser for Java).  The Scenarios are built using fields in a config file stored in **/src/main/resources/conifgFiles.**


The ScenarioBuilder class handles the creation of the Scenarios.


The SqliteDatabase class builds the database and the respective Tables classes will build the tables.

### To Add Scenarios
1. Drop a file in **/src/main/resources/conifgFiles** that follows the format of the example config file below
1. Place the Scenario files in the **/src/main/resources/scenarioFiles** directory under the path you defined in the config file. As an example in the file below all of your scenario files would go in the **/src/main/resources/scenarioFiles/unit_test2** directory.

**Ensure the ID number is unique to your Scenario.**

    ---
    # Configuration File for Unit Test 1
    
    # ID Number
    id: 0002
    
    # Name for the GUI screen
    scName: Unit Test Scenario
    
    # Relative path to the executable Scenario
    # Based from the java resources directory
    dMalware: unit_test2/scenario2.exe
    
    # Same as Deploy but for the reset script
    reset_file: unit_test2/scenario_reset.ps1
    
    # Language the Scenario is using
    language: c++
    
    # Type of Scenario
    type: artifact
    
    # Place documents in the docs directory inside the Scenarios directory
    # Then add them here as needed.
    documentation: unit_test/docs/help.pdf


The following will create the database (If it doesn’t already exist) and tables and fill them with data from config files:

    SqliteDatabase sqliteDB = new SqliteDatabase("filesDb.sqlite");
    sqliteDB.createTables();
    
### Viewing Database
To view the database, the easiest method is to download DB Browser Sqlite and open the .sqlite file located in **/src/main/resources/databases**

You can download a SQLite Browser [here](https://sqlitebrowser.org/dl/)

Another option is to run queries using sql in java.  

The following creates the database if it doesn’t exist, populates it with whatever Scenarios are in the configFiles directory, and queries everything from the scenarios table.

    SqliteDatabase sqliteDB = new SqliteDatabase("filesDb.sqlite");
    sqliteDB.createTables();
    
    // SQL query
    String sql = "SELECT * FROM scenarios";
    
    // Connect to the Database
    try (Connection conn = sqliteDB.connect();
    
        // Statement class is used for executing and returning SQL Queries
	     Statement stmt = conn.createStatement();
        
        // The results from the Query
	     ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through results
	         while (rs.next()) {
                // When printing Queries use the column name with the get methods below
		          System.out.printf("ID: %s\tName: %s\n", rs.getInt("idNumber"), rs.getString("scName"));
	         }
    } catch (Exception e) {
        printStackTrace();
    }
    
## Adding Tables to the Database
###### [Return to Top](#Malsim)
###### Confluence Link for this Section [here](https://iancobia.atlassian.net/wiki/spaces/CC/pages/71204865/Adding+Tables+to+Database)

### Create Schema
Start by creating a new Schema(all files are under **/src/main/java/database**):

1. Open AppSchemas.java
1. In the **AppSchemas** class create a new static final class using the format “newtablenameTableSchema”
   1. For example a table name User would be UserTableSchema
1. Then copy the format used for other tables
   1. All values will be Strings for this no matter what is being inserted into the table
   1. The **NAME** value is the name of the table
   1. The cols are the columns of the table
      1. The values in ““ here are what you use to query the column in the database
      
![Schema Code](db_schemas.PNG) 
      
### Create Table
Next we have to write the code that handles the Table creation (all files are under **/src/main/java/tables**):

1. Create a new java class named after your table
   1. For example UserTable.java
1. It needs a private DatabaseHelper
   1. If you need to encrypt something it also needs a private ScenarioHelper
1. The constructor needs to take a SqliteDatabase object
   1. Assign the private DatabaseHelper you made to the SqlitDatabase
   1. If you need a ScenarioHelper create a new one in the Constructor.
1. Create two private methods
   1. createTable and InsertIntoTable
1. Follow the format of the other Tables

![Table Creation](scenariosTable.PNG)

The above image doesn’t encrypt any files, for an example of that look at the MalwareTable.  You can basically copy and paste the above but you need to change all of the values to match the values of your tables Schema in the sql Strings and in the PreparedStatement.

#### Using Prepared Statement
The prepared statement will need to reflect the actual values of the data you plan to insert.  For example a name in sqlite would be TEXT but when you use the prepared statement you need it to be a String.

1. Change the pst.set statements to reflect your data.  The first one should always be an int and should be the Scenario Key

     
    ```The values pst uses are from whatever object you create, so all of these use the Scenario object but something new will need to     reflect that. So the DatabaseHelper will probably not be used to query information, just to establish a connection to the database.  I would suggest inserting objects into a HashTable and using that to loop through them and insert them into the database.```

## Confluence Links
###### [Return to Top](#Malsim)
All the information above can also be found on Confluence [here](https://iancobia.atlassian.net/wiki/spaces/CC/pages/39911440/How-to+articles)
