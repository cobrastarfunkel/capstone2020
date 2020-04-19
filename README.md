# Malsim

## TOC
* [Project Setup](#Project-Setup)
* [Confluence Links](#Confluence-Links)

## Project Setup
###### Confluence Link [here](https://iancobia.atlassian.net/wiki/spaces/CC/pages/167084033/Project+Setup)

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




## Confluence Links
###### [Return to Top](#Malsim)
All the information above can also be found on Confluence [here](https://iancobia.atlassian.net/wiki/spaces/CC/pages/39911440/How-to+articles)
