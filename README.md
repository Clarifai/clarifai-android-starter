# clarifai-android-starter
This is a simple project to get you started using the Clarifai API from Android. It uses the [Clarifai Java Client](https://github.com/Clarifai/clarifai-java) to perform image recognition on photos stored on your device. Full Clarifai API documentation can be found at [developer.clarifai.com](http://developer.clarifai.com/).

<img src="https://i.imgur.com/hcyCM3q.jpg" width="200">

## Building and Running
Clone this repo. Then, in Android Studio, go to "File > Open..." and select this directory. Press the "Play" button in the toolbar to build and run.

Alternately, you can build and run from the command-line:

```./gradlew  installDebug```


## RecognitionActivity

[RecognitionActivity](https://github.com/Clarifai/clarifai-android-starter/blob/master/app/src/main/java/com/clarifai/androidstarter/RecognitionActivity.java) is a simple Android Activity that prompts the user to select a photo from their photo library and then sends it to the Clarifai API for tagging. This Activity demonstrates how to prepare the image for sending to Clarifai and how to handle the response.

## Next steps
Feel free to use this project as a base for building your app. Alternately, you can create a new project and add the following dependency to your app/build.gradle:

```compile 'com.clarifai:clarifai-api-java:1.1.0'```

More information on the client can be found [here](https://github.com/Clarifai/clarifai-java).


