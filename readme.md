# CA Aerospace Museum STEM Camp Administration Console

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
