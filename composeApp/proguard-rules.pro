proguard
# --- Rules for com.example.thelibrary ---
# Keep all classes and their members in this library
-keep class org.kodein.di.** { *; }
-keep interface org.kodein.di.** { *; }

# Optionally, if you want to be absolutely sure names are preserved (often covered by the above)
# -keepnames class com.example.thelibrary.** { *; }

# Keep attributes that might be needed (e.g., for annotations, generics)
-keepattributes Signature, InnerClasses, EnclosingMethod, Exceptions, *Annotation*

# If the library uses reflection on its own classes heavily,
# you might also need to ensure constructors are kept if they are reflectively called.
# The `{ *; }` generally covers this, but being explicit can sometimes help:
# -keepclassmembers class com.example.thelibrary.** {
#   <init>(...); # Keep all constructors
# }

# --- Other rules for your application ---
# ... your app-specific rules ...