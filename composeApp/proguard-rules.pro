# Keep all classes and their members in this library
-keep class org.kodein.di.** { *; }
-keep interface org.kodein.di.** { *; }

# Keep classes that are bound in DI containers
-keep class my.krzyjan.documentmgr.** { *; }

# Prevent warnings for the DI internals
-dontwarn org.kodein.di.**

# If using Kodein with AndroidX
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Keep attributes that might be needed (e.g., for annotations, generics)
-keepattributes Signature, InnerClasses, EnclosingMethod, Exceptions, *Annotation*

-keep class kotlin.Metadata { *; }

# Keep Kotlin-generated lambdas and companions
-keepclassmembers class * {
    public <init>(...);
}
-keepclassmembers class **$Companion { *; }
-keepclassmembers class **$*Lambda* { *; }
