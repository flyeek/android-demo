# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/flyeek/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# required by UMeng Analytics
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

# required by UMeng Analytics
-keep public class com.flyeek.dev.demo.R$*{
public static final int *;
}

# required by UMeng Analytics
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
