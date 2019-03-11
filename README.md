

# Unofficial ArmsList Android App

![ArmsList App Icon](https://imgur.com/PUlSdLR.png)

A instant android app using the bottom navigation bar for navigation, MVVM and MVP architectures when needed and Coroutines for threading and optimizations. Uses JSOUP to parse data but could be easily transfered to a thrid party API if given using Retrofit. Room is used to store data in a database in memory. Besides filtering, data is reset on relaunch of the app and filters are cleared out every launch. 

## MVVM & MVP

I used each where they made the most sense. MVVM for the Feed and MVP for the Filter code. Feed uses LiveData to keep the adapter updated from Room.

MVP to clean up the Geocode API out of the FilterFragment. 

Used a few different ideas here from observer pattern, LiveData, interface callbacks, eventbus and more in this app. Just to test and play with each type.

## Libraries used

* [Room](https://developer.android.com/topic/libraries/architecture/room) - For in memory db
* [Greenrobot.Eventbus](https://github.com/greenrobot/EventBus) - Decouple UI and backend
* [ConstraintLayout](https://developer.android.com/training/constraint-layout/) - finally got to learning it. 
* [Play Services Locations](https://developers.google.com/android/guides/setup) - For automatic location grabbing.
* [JSoup](https://jsoup.org/) - for webscrapping.
* [RetrofitRssConverter](https://github.com/faruktoptas/RetrofitRssConverterFactory) - Tried to just use the RSS feed first but it didn't work out. Still awesome library.
* [Retrofit](https://square.github.io/retrofit/) - For webcalls
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For threading
* [Androidx Preferences](https://developer.android.com/reference/kotlin/androidx/preference/package-summary) - Because its super handy.


## Information

This app was just another app for me to test my skills (Kotlin, Retrofit Room, MVVM+MVP and instant app) and make something I wanted for myself and potentially others. ArmsList.com mobile website and searching on mobile is a bit poor as of Nov 2018, so I went about making an app for them potentially to instantly load on a phone and help simplify their mobile app experience. If you are from ArmsList and want to chat more, contact me and we can make this more official. Would love to help with that. Yes this app scraps the website. I would absolutely love to have an API endpoint to hit and it wouldn't take me long to change that.

## In App Photos

[App Photos](https://imgur.com/a/QNSACdR)
