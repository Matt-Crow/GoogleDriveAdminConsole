
TODO: If credentials don't exist, tell them to put them in such and such place with a given name,
say "look at readme for instructions on how to create client secret for yourself"
Include copy of credentials in drive so Randy and camp don't have to go through that,
tell them as much when I share this


# California Aerospace Museum STEM Camp Administration Console
A small, extendable application for sharing Google Drive Files.

## Project Purpose ##
    Over the Summer of 2020, I worked as an independent contractor for the California Aerospace Museum.
I joined a team working on a STEM (Science, Technology, Engineering, and Math) camp, where campers
would use Minecraft as a tool to build a Mars colony, learning the science behind the various structures
they were told to build. Campers were given Google documents with instructions and information they would
need in their build process. However, manually distributing many files to many campers is not very scalable,
and making these documents public wasn't a preferred option. As such, I developed this administration console
to join tables of camper and file information, and distribute the files accordingly.

## Requirements ##
(*) This application uses Java to run. If you do not yet have Java installed, you'll need to download and install Java [here](https://www.java.com/en/).

## Installing the Application ##
1. First, go to the [release tab](https://github.com/IronHeart7334/AerospaceCampAdminConsole/releases) for the latest stable release, 
locate the most recent version, and download the source code under the 'assets' tab.
2. Once the application is done downloading, unzip it.
3. You will find the executable JAR file under build/libs. You can move this JAR file wherever you want. Feel free to delete the other files, as you only need this JAR file.
4. Double-click the JAR file to run it.
5. The first time you launch the application, it will crash, as it needs Google API credentials.
6. Any files the application creates are stored under [HOME]/Aerospace Camp Administration Console, where HOME is your home folder.
For PC users, this is usually '''C:\Users\NAME\Aerospace Camp Administration Console'''. The credentials will go in the "credentials" folder here.
7. 
## Helpful Links for Developers ##
* [CSV Package](https://commons.apache.org/proper/commons-csv/apidocs/index.html)
* [The Google API console](https://console.developers.google.com/apis/credentials?authuser=2&project=camp-administration-console)
* [Getting started with Drive API Java](https://developers.google.com/drive/api/v3/quickstart/java)
* [Getting started with Sheets API Java](https://developers.google.com/sheets/api/quickstart/java)
* [Drive Javadoc](https://developers.google.com/resources/api-libraries/documentation/drive/v3/java/latest/overview-summary.html)
* [Sheets Javadoc](https://developers.google.com/resources/api-libraries/documentation/sheets/v4/java/latest/)
* Use this to download a file's contents: https://drive.google.com/uc?export=download&id=ID
* https://cloud.google.com/compute/docs/api-rate-limits
* https://console.developers.google.com/apis/api/drive.googleapis.com/quotas?authuser=2&project=camp-administration-console
To check if something is a folder, check if its MIME type is "application/vnd.google-apps.folder"

## TODO ##
* Different name for application
* Implement drive.commands.utils.DriveCommandResult
* Make PluginLoader scour the classpath for plugins
