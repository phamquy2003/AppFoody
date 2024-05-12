// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}
buildscript {
    repositories {
        // Thêm kho lưu trữ Maven Central
        mavenCentral()
        // Các kho lưu trữ khác nếu cần
        // maven {
        //     url "URL của kho lưu trữ Maven khác"
        // }
    }
    // Các cài đặt khác của buildscript
    // ...
}
