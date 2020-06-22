# CA Aerospace Museum STEM Camp Administration Console
This application is currently in development, and is not ready for release yet!

## Running the Application ##
1. The easiest way to run the application is through the JAR file under build/libs.
2. Make sure to sign in using the Aerospace museum GMail account!
3. You can find property files under src/main/resources/properties. Use these if you want to parse the certification form.

## Helpful Links ##
* [The Google API console](https://console.developers.google.com/apis/credentials?authuser=2&project=camp-administration-console)
* [Getting started with Drive API Java](https://developers.google.com/drive/api/v3/quickstart/java)
* [Getting started with Sheets API Java](https://developers.google.com/sheets/api/quickstart/java)
* [Drive Javadoc](https://developers.google.com/resources/api-libraries/documentation/drive/v3/java/latest/overview-summary.html)
* [Sheets Javadoc](https://developers.google.com/resources/api-libraries/documentation/sheets/v4/java/latest/)
* Use this to download a file's contents: https://drive.google.com/uc?export=download&id=ID
To check if something is a folder, check if its MIME type is "application/vnd.google-apps.folder"

## TODO ##
* Add a command queue thread to command factories to prevent multiple commands from running at one time, and also make it easier to abort all active commands.
* Add a component factory
