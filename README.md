# ZKMA (Zion Key Management Api)

## 1. Introduction
ZKMA (Zion Key Management API) is a SDK which allows users to control the seed more safety. Currently, HTC Exodus’s build-in Zion Vault App has been applied ZKMA, all the secure operation (enter pin, show seed, sign transaction) will be operated on the trust OS and all secure data will not be exposed on android world.

## 2. Environment setup
ZKMA SDK packaged as an AAR file that can be imported to your android App project easily.

### 2.1 Put ZKMA SDK .AAR to lib folder
Copy the .AAR file which provided by HTC to your APP project lib path
```
<Project Name>\app\libs\ZKMA-release.aar
```

### 2.2 Config and Sync your build.gradle
Set dependencies in build.gradle
```
dependencies {
...
compile(name:'ZKMA-release', ext:'aar')
...
}
```

### 2.3 Build your wallet APP
Press the “Make project” button or following command below to build your APP
```
./gradlew assembleRelease
```
