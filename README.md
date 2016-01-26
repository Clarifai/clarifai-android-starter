# clarifai-android-starter
This is a simple project to get you started using the Clarifai API in Android apps. It uses the [Clarifai Java Client](https://github.com/Clarifai/clarifai-java) to perform image recognition on photos stored on your device. Full Clarifai API documentation can be found at [developer.clarifai.com](http://developer.clarifai.com/).

<img src="https://i.imgur.com/56EUw5D.jpg" width="200">

## Building and Running

1. Go to [developer.clarifai.com/applications](https://developer.clarifai.com/applications), click
   on your application, and copy the "Client ID" and "Client Secret" values (if you don't already
   have an account or application, you'll need to create them first).

   Replace the values of `CLIENT_ID` and `CLIENT_SECRET` in
   [Credentials.java](app/src/main/java/com/clarifai/androidstarter/Credentials.java) with the ones
   you copied.

2. Open the project in Android Studio and press the "Play" button in the toolbar to build,
   install, and run the app.

   Alternately, you can build and install from the command-line with:
  ```./gradlew installDebug```

## RecognitionActivity
[RecognitionActivity](app/src/main/java/com/clarifai/androidstarter/RecognitionActivity.java) is a simple  Activity that prompts the user to select a photo from their photo library and then sends it to the Clarifai API for tagging. This Activity demonstrates how to prepare the image for sending to Clarifai and how to handle the response.

## Next steps
Feel free to use this project as a base for building your app. You can also use the ClarifaiClient from other projects by adding the following dependency to your [app/build.gradle](app/build.gradle) file:

```compile 'com.clarifai:clarifai-api-java:1.2.0'```

More information on the client and usage examples can be found [here](https://github.com/Clarifai/clarifai-java).
