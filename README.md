# clarifai-android-starter
This is a simple project to get you started using the Clarifai API in Android apps. It uses the [Clarifai Java Client](https://github.com/Clarifai/clarifai-java) to perform image recognition on photos stored on your device. Full Clarifai API documentation can be found at [developer.clarifai.com](http://developer.clarifai.com/).

<img src="https://i.imgur.com/56EUw5D.jpg" width="200">

## Building and Running
This project can be built and run in Android Studio. No customization is required.

Alternately, you can build and install from the command-line with:

```./gradlew installDebug```


## RecognitionActivity
[RecognitionActivity](https://github.com/Clarifai/clarifai-android-starter/blob/master/app/src/main/java/com/clarifai/androidstarter/RecognitionActivity.java) is a simple  Activity that prompts the user to select a photo from their photo library and then sends it to the Clarifai API for tagging. This Activity demonstrates how to prepare the image for sending to Clarifai and how to handle the response.

## Next steps
Feel free to use this project as a base for building your app. You can also use the ClarifaiClient from other projects by adding the following dependency to your [app/build.gradle](https://github.com/Clarifai/clarifai-android-starter/blob/master/app/build.gradle) file:

```compile 'com.clarifai:clarifai-api-java:1.1.0'```

More information on the client and usage examples can be found [here](https://github.com/Clarifai/clarifai-java).
