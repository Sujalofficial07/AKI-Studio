# Add project specific ProGuard rules here.

# Keep Kotlin Metadata
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**

# Keep Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Keep data classes
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
