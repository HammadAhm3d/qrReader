# QR Code Scanner Application

This application allows users to scan QR codes using their device's camera. It is built with React Native for the frontend and a custom native Android module for QR code scanning using the ZXing library.

## Features

- Scan QR codes using the device's camera
- Display scanned QR code data and image
- Copy scanned data to clipboard

## Installation

### Prerequisites

- Node.js
- npm or yarn
- React Native development environment setup
- Android SDK and Android Studio

### Instructions

1. Clone the repository:

git clone https://github.com/HammadAhm3d/qrReader

2. Navigate to the project directory:

cd qrReader

3. Install dependencies:

npm install

4. Start the Metro bundler:

npx react-native start

5. Run the application on an Android device or emulator:

npx react-native run-android

## Usage

1. Launch the application on your Android device or emulator.
2. Tap the "Scan QR Code" button to open the scanner.
3. Point the camera at a QR code to scan it.
4. Once the QR code is scanned, the data and image will be displayed.
5. Long-press on the scanned data to copy it to the clipboard.

## Native Android Module

The QR code scanning functionality is implemented using a native Android module, which bridges the React Native JavaScript code with native Android code. The module uses the ZXing library to perform QR code scanning.

### Module Files

- **QRScannerModule.kt**: Kotlin file containing the implementation of the native module for QR code scanning.
- **QRScannerPackage.kt**: Kotlin file defining the package for the native module.
- **CustomScannerActivity.kt**: Kotlin file defining the custom activity for scanning QR codes with custom UI.
- **App.tsx**: TypeScript file containing the React Native application code, including the usage of the native module for QR code scanning.

### Demo

![Alt Text](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExYnpyZzNhbGUxbjhjZ3UyaHA2MzJkYWU1OXo5dThjaDNqOG4wb3M2diZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/N1xOdclPKwYnr4mlyb/giphy.gif)

## Credits

- [React Native](https://reactnative.dev/) - JavaScript framework for building mobile applications.
- [ZXing](https://github.com/zxing/zxing) - Library for QR code scanning and generation.
- [JourneyApps BarcodeScanner](https://github.com/journeyapps/zxing-android-embedded) - Embedded version of ZXing library for Android.
