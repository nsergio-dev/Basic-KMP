# Create basic projecct

1. Create project in https://kmp.jetbrains.com/?android=true&ios=true&iosui=compose&includeTests=true

2. Unzip

3. Import from Android Studio.
4. Check settings.gradle file an add maven("https://jogamp.org/deployment/maven") ...
      dependencyResolutionManagement {
        repositories {
            google {
                mavenContent {
                    ...
                }
            }
            ...
            maven("https://jogamp.org/deployment/maven")
        }
      }
      pluginManagement {
        repositories {
            google {
                ...
            }
            ...
            maven("https://jogamp.org/deployment/maven")
        }
      }
5. Compile and run app.
6. if app runs, edit as you wish.

