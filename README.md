
## Tech Stack

[![JDK](https://img.shields.io/badge/openjdk-21.0.3-437291?style=for-the-badge&logo=openJdk&logoColor=white)](https://openjdk.org/)
[![Android Studio](https://img.shields.io/badge/Android_Studio-2024.2.1_Patch_3-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)](https://developer.android.com/studio)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.24-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](http://kotlinlang.org)
[![KSP](https://img.shields.io/badge/KSP-1.9.21--1.0.15-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://github.com/google/ksp)
[![Gradle](https://img.shields.io/badge/gradle-8.9-02303A?style=for-the-badge&logo=gradle&logoColor=white)](https://developer.android.com/studio/releases/gradle-plugin)
[![Navigation](https://img.shields.io/badge/Navigation-2.8.5-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/navigation)
[![lifecycle](https://img.shields.io/badge/Lifecycle-2.8.7-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/lifecycle)
[![Data Store](https://img.shields.io/badge/Data_Store-1.1.1-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/datastore)
[![Room](https://img.shields.io/badge/Room-2.6.1-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/room)
[![Glide](https://img.shields.io/badge/Glide-4.16.0-18BED4?style=for-the-badge&logo=glide&logoColor=white)](https://github.com/bumptech/glide#proguard)
# Diary App

Diary App adalah aplikasi yang memungkinkan pengguna untuk mencatat dan mengelola harian. Aplikasi ini dirancang untuk memudahkan pencatatan, pengeditan, dan penghapusan dengan antarmuka yang ramah pengguna.


## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Tree / Folder Structure](#tree--folder-structure)
- [Todos](#todos)
- [Flowchart](#flowchart)
- [Preview](#preview)
- [Demo](#demo)
- [Credit / Contributor(s)](#credit--contributors)
## Features

>- **Input Catatan Harian**: Pengguna dapat memasukan detail catatan harian seperti judul dan deskripsi kegiatan
>- **Penyimpanan Catatan**: Aplikasi secara otomatis menyimpan catatan berdasarkan tanggal
>- **Riwayat Catatan**: Menampilkan riwayat semua catatan harian yang telah dimasukan
>- **Sortir dan Pencarian**: Memungkinkan pengguna untuk mencari dan menyortir catatan berdasarkan judul atau tanggal
>- **Notifikasi Pengingat**: Aplikasi dapat mengirim notifikasi pengingat pengguna tentang catatan atau kegiatan yang akan datang


## Installation (How to run the project)

To run the project locally, follow these steps:

### 1. Clone the repository
>- ```https://github.com/zenmobiledev/diary-app.git```
>- ```cd diary-app```

### 2. Open the project
>- Launch your preferred Integrated Development Environment (IDE), such as Android Studio or IntelliJ IDEA. Then, open the ```diary-app``` project directory within the IDE.

### 3. Build the project
Ensure that all necessary dependencies are installed. In Android Studio or IntelliJ IDEA, you can typically do this by:

>- **Syncing the Project**: The IDE should automatically prompt you to sync the project with the Gradle files. If not, you can manually sync by clicking on the "Sync Project with Gradle Files" button.
>- **Building the Project:** Navigate to the ```Build``` menu and select ```Build Project```. This process will compile the code and prepare the application for running.

### 4. Run the application
After the build process completes successfully:

>- **Select a Device**: Choose an emulator or a physical device connected to your computer where you want to run the application.

>- **Launch the App**: Click on the green 'Run' button (usually depicted as a play icon) in the IDE toolbar, or navigate to ```Run``` > ```Run 'app'```. This action will install and start the application on the selected device.
## Tree / Folder Structure
```
├── app
│   ├── src
│   │   ├── androidTest
│   │   │   └── java
│   │   │       └── com
│   │   │           └── mobbelldev
│   │   │               └── diaryapp
│   │   │                   └── ExampleInstrumentedTest.kt
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── mobbelldev
│   │   │   │           ├── apk
│   │   │   │           │   └── app-debug.apk
│   │   │   │           ├── assets
│   │   │   │           │   ├── flow
│   │   │   │           │   │   ├── Diary App-Page-1.png
│   │   │   │           │   │   ├── Diary App-Page-2.png
│   │   │   │           │   │   ├── Diary App-Page-3.png
│   │   │   │           │   │   └── Diary App-Page-4.png
│   │   │   │           │   └── preview
│   │   │   │           │       ├── add_diary.png
│   │   │   │           │       ├── alarm.png
│   │   │   │           │       ├── edit_diary.png
│   │   │   │           │       ├── latest_page.png
│   │   │   │           │       ├── main_page.png
│   │   │   │           │       ├── search.png
│   │   │   │           │       └── sort.png
│   │   │   │           └── diaryapp
│   │   │   │               ├── data
│   │   │   │               │   ├── DiaryDao.kt
│   │   │   │               │   ├── DiaryDatabase.kt
│   │   │   │               │   ├── DiaryEntity.kt
│   │   │   │               │   └── SettingItem.kt
│   │   │   │               ├── ui
│   │   │   │               │   ├── feature
│   │   │   │               │   │   ├── diary
│   │   │   │               │   │   │   ├── adapter
│   │   │   │               │   │   │   │   └── ListDiaryAdapter.kt
│   │   │   │               │   │   │   └── DiaryFragment.kt
│   │   │   │               │   │   ├── latest
│   │   │   │               │   │   │   └── LatestFragment.kt
│   │   │   │               │   │   └── settings
│   │   │   │               │   │       ├── adapter
│   │   │   │               │   │       │   └── SettingsAdapter.kt
│   │   │   │               │   │       ├── notification
│   │   │   │               │   │       │   └── NotificationReminderReceiver.kt
│   │   │   │               │   │       └── SettingsFragment.kt
│   │   │   │               │   └── main
│   │   │   │               │       └── MainActivity.kt
│   │   │   │               └── utils
│   │   │   │                   └── FormatDate.kt
│   │   │   ├── res
│   │   │   │   ├── drawable
│   │   │   │   │   ├── baseline_description_24.xml
│   │   │   │   │   ├── diary_amico_illustration.png
│   │   │   │   │   ├── ic_launcher_background.xml
│   │   │   │   │   ├── ic_launcher_foreground.xml
│   │   │   │   │   ├── outline_cancel_24.xml
│   │   │   │   │   ├── outline_delete_24.xml
│   │   │   │   │   ├── outline_exit_to_app_24.xml
│   │   │   │   │   ├── outline_latest_24.xml
│   │   │   │   │   ├── outline_notifications_active_24.xml
│   │   │   │   │   ├── outline_save_24.xml
│   │   │   │   │   ├── outline_settings_24.xml
│   │   │   │   │   ├── outline_sort_24.xml
│   │   │   │   │   ├── outline_title_24.xml
│   │   │   │   │   ├── rounded_add_24.xml
│   │   │   │   │   ├── rounded_date_24.xml
│   │   │   │   │   ├── rounded_diary_24.xml
│   │   │   │   │   ├── rounded_filter_24.xml
│   │   │   │   │   └── rounded_search_24.xml
│   │   │   │   ├── layout
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── custom_alert_dialog.xml
│   │   │   │   │   ├── fragment_diary.xml
│   │   │   │   │   ├── fragment_latest.xml
│   │   │   │   │   ├── fragment_settings.xml
│   │   │   │   │   ├── item_list.xml
│   │   │   │   │   └── list_settings_item.xml
│   │   │   │   ├── menu
│   │   │   │   │   ├── bottom_nav_menu.xml
│   │   │   │   │   ├── sort_menu.xml
│   │   │   │   │   └── top_bar_menu.xml
│   │   │   │   ├── mipmap-anydpi-v26
│   │   │   │   │   ├── ic_launcher.xml
│   │   │   │   │   └── ic_launcher_round.xml
│   │   │   │   ├── mipmap-hdpi
│   │   │   │   │   ├── ic_launcher.webp
│   │   │   │   │   └── ic_launcher_round.webp
│   │   │   │   ├── mipmap-mdpi
│   │   │   │   │   ├── ic_launcher.webp
│   │   │   │   │   └── ic_launcher_round.webp
│   │   │   │   ├── mipmap-xhdpi
│   │   │   │   │   ├── ic_launcher.webp
│   │   │   │   │   └── ic_launcher_round.webp
│   │   │   │   ├── mipmap-xxhdpi
│   │   │   │   │   ├── ic_launcher.webp
│   │   │   │   │   └── ic_launcher_round.webp
│   │   │   │   ├── mipmap-xxxhdpi
│   │   │   │   │   ├── ic_launcher.webp
│   │   │   │   │   └── ic_launcher_round.webp
│   │   │   │   ├── navigation
│   │   │   │   │   └── mobile_navigation.xml
│   │   │   │   ├── values
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── dimens.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   ├── values-night
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   └── xml
│   │   │   │       ├── backup_rules.xml
│   │   │   │       └── data_extraction_rules.xml
│   │   │   └── AndroidManifest.xml
│   │   └── test
│   │       └── java
│   │           └── com
│   │               └── mobbelldev
│   │                   └── diaryapp
│   │                       └── ExampleUnitTest.kt
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle
│   ├── wrapper
│   │   ├── gradle-wrapper.jar
│   │   └── gradle-wrapper.properties
│   └── libs.versions.toml
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
└── settings.gradle.kts
```
## Todos

- [x]  Input Catatan Harian
- [x]  Penyimpanan Catatan
- [x]  Riwayat Catatan
- [x]  Sortir dan Pencarian
- [x]  Notifikasi Pengingat
## Flowchart
|1                                 |2                                 |3                                |4                                |
|----------------------------------|----------------------------------|---------------------------------|---------------------------------|
|![1](app/src/main/java/com/mobbelldev/assets/flow/Diary%20App-Page-1.png)|![2](app/src/main/java/com/mobbelldev/assets/flow/Diary%20App-Page-2.png)|![3](app/src/main/java/com/mobbelldev/assets/flow/Diary%20App-Page-3.png)|![4](app/src/main/java/com/mobbelldev/assets/flow/Diary%20App-Page-4.png)|
## Preview
|Main Page                         |Add Diary                         |Search                            |Sort                              |Edit Diary                        |
|----------------------------------|----------------------------------|----------------------------------|----------------------------------|----------------------------------|
|![Main Page](https://github.com/zenmobiledev/diary-app/blob/master/app/src/main/java/com/mobbelldev/assets/preview/main_page.png)|![Add Diary](https://github.com/zenmobiledev/diary-app/blob/master/app/src/main/java/com/mobbelldev/assets/preview/add_diary.png)|![Search](https://github.com/zenmobiledev/diary-app/blob/master/app/src/main/java/com/mobbelldev/assets/preview/search.png)|![Sort](https://github.com/zenmobiledev/diary-app/blob/master/app/src/main/java/com/mobbelldev/assets/preview/sort.png)|![Add Diary](https://github.com/zenmobiledev/diary-app/blob/master/app/src/main/java/com/mobbelldev/assets/preview/add_diary.png)|![Search](https://github.com/zenmobiledev/diary-app/blob/master/app/src/main/java/com/mobbelldev/assets/preview/search.png)|![Edit Diary](https://github.com/zenmobiledev/diary-app/blob/master/app/src/main/java/com/mobbelldev/assets/preview/edit_diary.png)|

|Latest Page                       |Alarm                             |
|----------------------------------|----------------------------------|
|![Latest Page](https://github.com/zenmobiledev/diary-app/blob/master/app/src/main/java/com/mobbelldev/assets/preview/latest_page.png)|![Alarm](https://github.com/zenmobiledev/diary-app/blob/master/app/src/main/java/com/mobbelldev/assets/preview/alarm.png)|

## Demo

https://github.com/user-attachments/assets/3001c741-0b3f-43f5-9164-150c584478e6

## Credit / Contributor(s)

- [Zaenal Arif](https://github.com/zenmobiledev)

