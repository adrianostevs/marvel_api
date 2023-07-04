# Android App Marvel API
<h1 align="center">Marvel API</h1>
<p align="center">  
A simple Marvel App getting API with Retrofit, maintaining data using LiveData, and Material Design based on architecture.
</p>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous
- [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData)
- Jetpack
  - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - ViewBindings - Binds UI components class for each XML layout file present in that module.
  - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
  - [Hilt](https://dagger.dev/hilt/): for dependency injection.
- Architecture
  - Clean Architecture Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Glide](https://github.com/bumptech/glide)
- [Modularization] - Organizing a codebase into loosely coupled and self contained parts.
- [Dynamic Feature] - Separate certain features and resources from the base module of app and include them in app bundle.

## Architecture
Marvel App is based on the Clean Architecture

## Open API
Marvel App using the [MarvelAPI](https://developer.marvel.com/docs) for constructing RESTful API.<br>
MarvelAPI provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Marvel Universe.
