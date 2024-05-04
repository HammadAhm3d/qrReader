import React, {useState} from 'react';
import {Button, NativeModules, Text, View} from 'react-native';

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
  const [scannedData, setScannedData] = useState('');

  const handleScanQRCode = () => {
    QRScannerModule.openCustomScanner(
      true,
      true,
      '',
      barcodeTypes,
      onBarcodeRead,
    );
  };
  const onBarcodeRead = (barcode: string) => {
    setScannedData(barcode);
  };
  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Button title="Scan QR Code" onPress={handleScanQRCode} />
      <Text>Scanned Data: {scannedData}</Text>
    </View>
  );
};

export default App;
