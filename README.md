# I Love Animals
The I Love Animals Android Mobile Application has made it easier and faster to reach and help more stray animals. Users who register in the system can easily find stray animals in need of help near their location and provide health, food support, and adoption services. Additionally, screens have been developed to inform users about First Aid support, which is as important for animals as it is for us. And the application also includes many other features.

## Table of contents
1. [Screenshots](#screenshots)
2. [Tech Stack](#tech-stacks)
3. [Architecture](#architecture)

## Screenshots
<h4>Login/Register Screens</h4>
<p>
  <img src = "screenshots/Login.jpg" width = "216" height = "456"/>
  <img src = "screenshots/Register.jpg" width = "216" height = "456"/>
</p>

<h4>Advertisement List and Search Screens</h4>
<p>
  <img src="screenshots/advertisement-list.jpg" width = "216" height = "456" />
  <img src="screenshots/advertisement-list-filter.jpg" width = "216" height = "456" />
  <img src="screenshots/search-ad.jpg" width = "216" height = "456" />
  <img src="screenshots/search.jpg" width = "216" height = "456" />
</p>

<h4>Advertisement Detail and Clinic Screens </h4>
<p>
  <img src="screenshots/ad-detail.jpg" width = "216" height = "456"/>
  <img src="screenshots/ad-detail-comment.jpg" width = "216" height = "456"/>
  <img src="screenshots/clinics.jpg" width = "216" height = "456"/>
</p>

<h4>Help Screens </h4>
<p>
  <img src="screenshots/help-animal.jpg" width = "216" height = "456"/>
  <img src="screenshots/help-approved.jpg" width = "216" height = "456"/>
  <img src="screenshots/help-denied.jpg" width = "216" height = "456"/>
</p>

<h4>Create Advertisement Flow</h4>
<p>
  <img src="screenshots/create-ad.jpg" width = "216" height = "456" />
  <img src="screenshots/select-image-portrait.png" width = "240" height = "456" />
  <img src="screenshots/map-address.jpg" width = "216" height = "456"/>
  <img src="screenshots/confirm.jpg" width = "216" height = "456"/>
</p>

<h4>First Aid Guide Screens</h4>
<p>
  <img src="screenshots/guide-list.jpg" width = "216" height = "456" />
  <img src="screenshots/guide-detail.jpg" width = "240" height = "456" />
</p>

<h4>Others..</h4>
<p>
  <img src="screenshots/charity-score.jpg" width = "216" height = "456" />
  <img src="screenshots/fab.jpg" width = "240" height = "456" />
  <img src="screenshots/profile.jpg" width = "240" height = "456" />
</p>

## Tech Stacks
* [Kotlin](https://kotlinlang.org/)
* View based and used [Jetpack Compose](https://developer.android.com/jetpack/compose) on some new screens for UI.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) UI related data holder and lifecycle aware.
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding) for safely interacting with views.
* [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + [Kotlin Flow](https://kotlinlang.org/docs/flow.html) for asynchronous.
* [Jetpack Navigation](https://developer.android.com/guide/navigation) for navigating between screens.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for Dependency Injection.
* [Ktor Client](https://ktor.io/docs/create-client.html) for HTTP requests.
* [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore) for local storage that allows you to store key-value pairs.
* [Glide](https://github.com/bumptech/glide) for loading images.
* [Lottie Animation](https://lottiefiles.com/) for ready to use animations.
* [Kotlinx.Serialization](https://github.com/Kotlin/kotlinx.serialization) for serialization/deserialization.
* [Parcelize](https://developer.android.com/kotlin/parcelize)
* [Version Catalog](https://developer.android.com/build/migrate-to-catalogs) for managing dependencies and plugins.
* [CameraX](https://developer.android.com/media/camera/camerax)
* [Google Maps SDK](https://developers.google.com/maps/documentation/android-sdk/start?hl=tr)
* [Firebase](https://firebase.google.com/docs/cloud-messaging?hl=tr) Push Notification and Storage for user loaded images.
* [Gemini API](https://ai.google.dev/) AI Model to analyze images and confirm helping animals.
* [Chucker](https://github.com/ChuckerTeam/chucker) an HTTP inspector for Android.

## Architecture
* Clean Architecture
* MVVM
* Repository Pattern
<img src="screenshots/arch.png" width = "50%" height = "50%" />
