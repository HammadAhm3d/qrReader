import React, {useState} from 'react';
import {
  Alert,
  Clipboard,
  Image,
  NativeModules,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';

const {QRScannerModule} = NativeModules;

var barcodeTypes = [
  'QR_CODE',
  'DATA_MATRIX',
  'UPC_A',
  'UPC_E',
  'EAN_8',
  'EAN_13',
  'RSS_14',
  'CODE_39',
  'CODE_93',
  'CODE_128',
  'ITF',
  'RSS_EXPANDED',
  'QR_CODE',
  'DATA_MATRIX',
  'PDF_417',
];

const App = () => {
  const [scannedData, setScannedData] = useState({
    data: '',
    image: '',
  });

  const handleScanQRCode = () => {
    QRScannerModule.openCustomScanner(
      true,
      true,
      'Scan a QR Code',
      barcodeTypes,
      onBarcodeRead,
    );
  };
  const onBarcodeRead = (data: string, image: string) => {
    setScannedData({data, image});
  };
  const handleCopy = () => {
    Clipboard.setString(scannedData?.data);
    Alert.alert('Copied!', 'Text has been copied to clipboard.');
  };
  return (
    <>
      <View style={styles.header}>
        <Text style={styles.title}>{'QR Code Reader'}</Text>
      </View>
      <View style={styles.container}>
        {scannedData?.data ? (
          <View style={{width: '80%'}}>
            <View style={styles.imageContainer}>
              <Image
                style={styles.image}
                source={{uri: `data:image/png;base64,${scannedData?.image}`}}
              />
            </View>
            <Text style={[styles.title, {fontSize: 24}]}>Scanner details</Text>
            <Text style={{color: 'black', fontWeight: '500'}}>Text</Text>
            <TouchableOpacity
              onLongPress={handleCopy}
              style={styles.textContainer}>
              <Text style={styles.textContent}>{scannedData?.data}</Text>
            </TouchableOpacity>
          </View>
        ) : null}
        <TouchableOpacity style={styles.scanButton} onPress={handleScanQRCode}>
          <Text style={styles.buttonText}>
            {scannedData?.data ? 'Scan Again' : 'Scan QR Code'}
          </Text>
        </TouchableOpacity>
      </View>
    </>
  );
};
const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'space-around',
    backgroundColor: 'white',
  },
  header: {
    padding: 15,
    alignItems: 'center',
    backgroundColor: 'white',
  },
  imageContainer: {
    width: '100%',
    height: 150,
    borderWidth: 2,
    borderRadius: 10,
    backgroundColor: 'black',
    overflow: 'hidden',
    justifyContent: 'center',
    alignItems: 'center',
    alignSelf: 'center',
    marginVertical: 10,
  },
  image: {
    width: 150,
    height: 150,
  },
  title: {
    textAlign: 'center',
    fontSize: 28,
    fontWeight: 'bold',
    color: 'black',
  },
  scanButton: {
    width: '80%',
    height: 50,
    backgroundColor: 'black',
    borderRadius: 5,
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonText: {
    color: 'white',
    fontWeight: '500',
    fontSize: 24,
    textAlign: 'center',
  },
  textContainer: {
    borderBottomWidth: 1,
    paddingVertical: 10,
  },
  textContent: {
    fontSize: 20,
    fontWeight: '400',
    color: 'black',
  },
});
export default App;
