# Clarifai Android Starter

This is a simple project showing how to use the Clarifai API in Android. It uses the [Clarifai Java Client](https://github.com/Clarifai/clarifai-java) to perform Concept recognition.

<img src="http://i.imgur.com/D782NYS.png" />

## Building and Running

To set your environment up for Android development, you'll need to install the
[Java SE Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
and [Android Studio](https://developer.android.com/studio/index.html).

Replace `YOUR_API_KEY_HERE` with your [Clarifai API Key](http://blog.clarifai.com/introducing-api-keys-a-safer-way-to-authenticate-your-applications/) in [`strings.xml`](app/src/main/res/values/strings.xml).

This project will compile in the standard manner through Android Studio or `./gradlew clean build` in your terminal.

## Where to look

`RecognizeConceptsActivity` contains most of the non-boilerplate code. In particular, `RecognizeConceptsActivity.onImagePicked` makes the API call to Clarifai.

You can also look at `RecognizeConceptsAdapter.onBindViewHolder` to see how we display the information that the API returns to the user.
