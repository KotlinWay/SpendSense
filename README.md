# SpendSense

SpendSense is Kotlin Multiplatform project with 99% shared code, built with Compose multiplatform,
Coroutines, Flow, Koin, Ktor, SqlDelight, that was developed step by step in the context of the
Kotlin Multiplatform course.<br>
The project's code can be run on Android, iOS, macOS, Windows, Linux. Strapi CMS is used for data
synchronization between devices.
<br>
<br>

![app_example](https://github.com/KotlinWay/SpendSense/assets/51969403/1d302a37-6a3e-41d4-b73e-80380a0c5782)

## Open-source libraries

- [Kotlin](https://kotlinlang.org/)
  based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
  for asynchronous.
- [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization): Kotlin multiplatform /
  multi-format serialization.
- [Compose multiplatform](https://github.com/JetBrains/compose-multiplatform): a modern UI framework
  for Kotlin.
- [Ktor](https://github.com/ktorio/ktor): for making network requests.
- [SqlDelight](https://github.com/cashapp/sqldelight): for caching data.
- [Koin](https://github.com/InsertKoinIO/koin): a pragmatic lightweight dependency injection
  framework.
- Architecture
    - MVVM Architecture
- [Material 3](https://m3.material.io/components): Material 3 components.

## Server side

To start the synchronization server, it's necessary
to [install all dependencies for Strapi](https://docs.strapi.io/dev-docs/quick-start#_1-install-strapi-and-create-a-new-project
), then navigate to the server directory and execute the command yarn install. <br>
After that, you can start the server from this directory using the command yarn develop for
development mode or yarn start for production mode.<br>

## Authors

- [@MaxKazantsev](https://www.github.com/KotlinWay)

## 🔗 Social Links

[Site](https://kmp.guru/kotlin-multiplatform-course/) <br>
[Telegram](https://t.me/twobeerspls)
