plugins {
    id("com.android.application")
    id("com.google.gms.google-services")


}

android {
    namespace = "com.example.medhelp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.medhelp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        dataBinding = true;
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }






}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    for slider
    // Material Components for Android. Replace the version with the latest version of Material Components library.
//    implementation ("com.google.android.material:material:1.5.0")

    // Circle Indicator (To fix the xml preview "Missing classes" error)
    implementation ("me.relex:circleindicator:2.1.6")
    implementation ("org.imaginativeworld.whynotimagecarousel:whynotimagecarousel:2.1.0")
    implementation ("com.google.android.material:material:1.4.0")
//    implementation ("com.github.mancj:MaterialSearchBar:0.8.5")
//    implementation ("com.github.JitPackUser:LibraryName:Version")
//    implementation ("com.github.User:Repo:Tag")
//    retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    gson converter
   implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    swipe refresh layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

//Fix for "Duplicate class kotlin.collections.jdk8.CollectionsJDK8Kt found in modules ..." error
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.10"))

//    glide image loader dependency
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    // Skip this if you don't want to use integration libraries or configure Glide.
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")


        implementation ("com.github.hishd:TinyCart:1.0.1")
//    implementation ("com.github.hishd:TinyCart:1.0.4")
//    volley liberary
    implementation("com.android.volley:volley:1.2.1")
//    advance webView library   for payment method
        implementation ("com.github.delight-im:Android-AdvancedWebView:v3.2.1")

//    animated material search bar
    implementation ("com.github.mancj:MaterialSearchBar:0.8.5")


//    // Material Components for Android. Replace the version with the latest version of Material Components library.
//    implementation ("com.google.android.material:material:1.5.0")
//
//    // Circle Indicator (To fix the xml preview "Missing classes" error)
//    implementation ("me.relex:circleindicator:2.1.6")
//
//    implementation ("org.imaginativeworld.whynotimagecarousel:whynotimagecarousel:2.1.0")
//
    implementation ("com.airbnb.android:lottie:6.1.0")

    implementation ("com.google.firebase:firebase-auth:21.0.1")

    implementation ("com.squareup.picasso:picasso:2.5.2")
//    implementation ("com.google.firebase:firebase-storage:20.0.0")
    implementation ("com.google.firebase:firebase-core:17.0.0")

// Add Razorpay SDK Dependency:
    implementation ("com.razorpay:checkout:1.6.33")


}