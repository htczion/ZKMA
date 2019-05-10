# Zion Key Management API 

Integration guide for creating a wallet using Zion Key Management APIs

# 1. Introduction

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ZKMA (Zion Key Management API) is a SDK which allows users to control the seed more safety. Currently, HTC Exodus’s build-in Zion Vault App has been applied ZKMA, all the secure operation (enter pin, show seed, sign transaction) will be operated on the trust OS and all secure data will not be exposed on android world.
<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/1.png" align="middle" width="700"></p>
    <center><b>
   Figure 1. ZKMA API call flow</b></cemter>
  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Depending your purpose, ZKMA can support the caller to call ZKMA APIs directly or bind service which is ZKMS of HTC Zion Vault. For performance, our suggestion is you can call ZKMA by ZKMA.aar. Another way which is ZKMS can be used for reducing your APK size. This document will focus on ZKMA API call flow via ZKMA.aar.

<table align="center">
  <tr>
    <th> </th>
    <th>ZKMA</th>
    <th>ZKMS</th>
  </tr>
  <tr>
    <td><center>Library name</td>
    <td><center>ZKMA.aar</td>
    <td><center>ZKMS.aar</td>
  </tr>
  <tr>
    <td><center>File Size</td>
    <td><center>7 MB~</td>
    <td><center>20 KB~</td>
  </tr>
  <tr>
    <td><center>Architecture</td>
    <td><center>Direct call API via ZKMA library</td>
    <td><center>Call API via ZKMS service</td>
  </tr>
  <tr>
    <td><center>HTC Zion Vault App</td>
    <td><center>Unnecessary</td>
    <td><center>Mandatory</td>
  </tr>
</table>

<pr><pr>

<center><b>Table 1. A comparison between ZKMA and ZKMS library</center></b>

## 1.1 Get ZKMA files and documents at GitHub

Any collaborator can get the latest ZKMA file and document at https://github.com/htczion/ZKMA.

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/2.png" align="middle" width="700"></p>
    <center><b>
   Figure 2. Release ZKMA</b></cemter>
  
 ## 1.2 ZKMA SDK document

The developer can find all ZKMA information for development in the release document files.

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/3.png" align="middle" width="700"></p>
    <center><b>
   Figure 3. ZKMA document</b></cemter>

## 1.3 API execution result

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Most of methods in ZKMA can return a result value, and you can find the definitions in RESULT class. If method can’t work properly, please check the return value of RESULT class.

# 2. Environment setup

ZKMA packaged as an AAR file that can be imported to your android App project easily.

## 2.1  Put ZKMA.AAR to lib folder

Copy this .AAR file to your APP project lib path

    <Project Name>\app\libs\ZKMA-release.aar
## 2.2 Config and Sync your build.gradle

```java
dependencies{
    
...
compile(name:'ZKMA-release', ext:'aar')
...
    
}

```

## 2.3 Build your wallet APP

Press the “Make project” button or following command below to build your APP

    ./gradlew assembleRelease

# 3. Build-up your wallet steps by steps
## 3.1 Acquire ZKMA manager instance
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The HTC wallet SDK manager is an agent which wraps all seed operations, you must get the instance (singleton) before do any operation related to seed access.

```java
HtcWalletSdkManager mHtcWalletSdkManager = HtcWalletSdkManager.getInstance();
```

## 3.2 Initialize ZKMA

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Initialization operation is to prepare all the necessary materials and make sure that all API function calls can work well. For example, if you got RESULT.E_SDK_SERVICE_TOO_OLD during initialing, it means system service is too old to run, please inform user to do the ROM update first, otherwise all API will give you a fail result and RuntimeException.
```java
  mHtcWalletSdkManager = HtcWalletSdkManager.getInstance();

  int result = mHtcWalletSdkManager.init(getApplicationContext());

  switch (result) {

    case RESULT.E_SDK_ROM_SERVICE_TOO_OLD:

    case RESULT.E_SDK_ROM_TZAPI_TOO_OLD:

      // App should prompt the user to update ROM

      showUpdateDialog(mActivity, "PLEASE UPDATE YOUR SYSTEM");

      break;

    case RESULT.E_TEEKM_TAMPERED:

      // App should prompt the user it’s rooted device

      showUpdateDialog(mActivity, "SDK can't support Rooted device");

      break;

    default:

      Log.w(TAG, "init() result="+intValue);

}
```
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;For ZKMA and ROM compatibility, History.txt described each ZKMA version and its compatible with ApiVersion in ROM as the format below.

    

     1.1.0 (0.0003.01010001)
        
     1.1.1
        
     1.1.2
        
     1.1.3
        
     1.2.0 (0.0004.01010005)
        
     1.2.1 (0.0004.01000006)

For example, 1.1.0 (0.0003.01010001) is one of the version TAG in history.txt.

&nbsp;&nbsp;&nbsp;&nbsp;`1.1.0` is ZKMA version which depending on ZKMA.AAR update

&nbsp;&nbsp;&nbsp;&nbsp;`0.0003.01010001` is a combo Api version which depending on ROM update.

&nbsp;&nbsp;&nbsp;&nbsp;About the combo Api version, it can be separate as three number as below.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0: is HW wallet.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0003: A HEX format number which is meaning currentServiceVer in ROM.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;01010001: A HEX format number which is meaning currentTzapiVer in ROM.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If this field is empty like 1.1.2, it is meaning ZKMA is compatible with the previous version, and don’t need to ROM update.

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/4.png" align="middle" width="700"></p>
    <center><b>
   Figure 4. Init API check sequence</b></cemter>

 ## 3.3 Get ZKMA version and API version
Using getModuleVersion method, you can get the current ZKMA version.

 ```java
 String SdkVersion= mHtcWalletSdkManager.getModuleVersion();
```

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Another version is Api Version which means the Api level supported by your current Exodus hardware device. If API level is small than the minimal Api version defined in ZKMA, App should show a dialog to prompt the user to update ROM.

  ```java
  String apiVersion= mHtcWalletSdkManager.getApiVersion();
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You can get more ZKMA information via getExportFields method, such as bTZ_support, minServiceVer, minTzApiVer… by getExportFields method.

  ```java
  ExportFields mExportFields= mHtcWalletSdkManager.getExportFields();
```
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.
## 3.4 Check your device
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;For security, ZKMA is not support the rooted S-ON device. If ZKMA detected this situation, it will return an ERROR code which is RESULT.E_TEEKM_TAMPERED to the developers. isRooted method is for the developer to check if the device rooted anytime.

   ```java
   int isRooted(); // 0: not root yet(RESULT.NOT_ROOTED), others: rooted or other errors
```
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.
## 3.5 Register your wallet

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Using register method to acquire a unique id, this id is to tell the ZKMA manager who is the target caller to request seed operations, we can treat this id as a wallet's identity. Also you can register more times to generate different wallet id if your Wallet App will need to control multiple seed.

  ```java
  long unique_id = mHtcWalletSdkManager.register(wallet_name, sha256);
```
If unique_id is 0 that means the registration is failed, and the wallet_name max length is 32 char.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To prevent from the trust zone blocked UI thread, the developer must call this method at background thread.
## 3.6 Create a new seed for your wallet

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If the user has not used cryptocurrency before, he will need a new wallet, the createSeed method will help to do this. After invoke this function, user will be asked to input passcode, the passcode is very important, because any methods which related to seed will all need user to input passcode.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Once the passcode has setup completed, the user will be asked to write down 12 words (we call this “recovery phrase”), recovery phrase is also import too, in order to prevent from miss recording, this method requests user to input recovery phase to confirm the correctness of 12 words recovery phrase.

  ```java
  int result = mHtcWalletSdkManager.createSeed(unique_id);
```

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/5.png" align="middle" width="700"></p>
    <center><b>
   Figure 5. A guide to show the security UI for user to create a new seed </b></cemter>
   
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.

## 3.7 Restore an existing seed for your wallet

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If the user has used cryptocurrency before, he may want to restore an existing wallet instead of creating a new one. The restoreSeed method provides the way for this, so that user just need to type 12 words recovery phrase to restore his wallet. In following steps, user will be asked to setup passcode again.

  ```java
  int result = mHtcWalletSdkManager.restoreSeed(unique_id);
```

Guide the user to input 12 words

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/6.png" align="middle" width="700"></p>
    <center><b>
   Figure 6. A guide to show the security UI for user to restore their seed </b></cemter>
   
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.
## 3.8 Check whether wallet seed has already existed or not.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;In some use cases, wallet app needs to guide the user to create a new wallet, restore an existing wallet, or simply let the user see the current wallet details. We can use this method to check the seed status of wallet. If the seed does not exist, it means that the user has not setup wallet completely, so it is a good decision to guide the user to create or restore his wallet.

  ```java
  int result = mHtcWalletSdkManager.isSeedExists(unique_id);
```
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.
## 3.9 Show the seed of your wallet

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;When create a new wallet, the user will be asked to write down 12 words recovery phrase. If the user forgot the recovery phrase, wallet app could use this method to display the recovery phrase again to let user have a chance to write down the it again.

  ```java
  int result = mHtcWalletSdkManager.showSeed(unique_id);
```

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/7.png" align="middle" width="700"></p>
    <center><b>
   Figure 7. Show the security UI for user to show their seed </b></cemter>
   

To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.
## 3.10 Retrieves the public key of seed of wallet for generating account address.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ZKMA does not provide a way to get the cryptocurrency account address. Because there are too many cryptocurrency types, each coin type may have a different algorithm to generate its coin address. ZKMA provides a way to retrieve the public key. Once the public key is obtained, you can generate the cryptocurrency account address by yourself.
```java
    // A holder for store your key
    
    PublicKeyHolder sendPublicKeyHolder, receivePublicKeyHolder;
    
    // You can get a public key for send or receive purpose, index can be increased by SDK or parameter.
    
    // coin_type: 0=BitCoin, 2=LiteCoin, 60=Ethereum
    
    sendPublicKeyHolder = mHtcWalletSdkManager.getSendPublicKey(unique_id, coin_type);
    
    sendPublicKeyHolder = mHtcWalletSdkManager.getSendPublicKey(unique_id, coin_type, keyIdx);
    
    receivePublicKeyHolder = mHtcWalletSdkManager.getReceivePublicKey(unique_id, coin_type);
    
    receivePublicKeyHolder = mHtcWalletSdkManager.getReceivePublicKey(unique_id, coin_type, keyIdx);
```
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.
## 3.11 Sign your transaction


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Wallet App could use this method to transfer cryptocurrency to others account address, user will be asked to input the correct passcode before execute the sign operation. Note: Signing operation will not upload the transaction on chains. You must upload it by yourself.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If you got seed and public key both, you can start to sign a transaction by your coin type. To sign a transaction, you will need to provide a JSON data format included all raw transaction data for signTransaction method, and then the signed raw transaction data bytes will be returned by the byteArrayHolder parameter.
```java
    int result = mHtcWalletSdkManager.signTransaction(unique_id, coin_type, rates, strJson, byteArrayHolder);

    // a byte array holder to receive the transaction data
    
    public class ByteArrayHolder {
    
       private static final int DEFAULT_ARRAY_SIZE = 2*1024; // 2KB
    
       public byte[] byteArray;
    
       public long receivedLength;
    
       public ByteArrayHolder() {
    
           byteArray = new byte[DEFAULT_ARRAY_SIZE];
    
           receivedLength = 0;
    
        }
    
    }
```

To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.
```js
    // A bitcoin JSON sample
    
    {
    
           "tx_version": "01",
    
           "tx_inputs_count": "01",
    
           "tx_inputs": [
    
            {
    
               "path": "m/44'/02'/0'/0/0",
    
               "tx_id": "9c6700b994e811bc6c4ca6c83a3776fafa4f720f06a2240d3efb451791aa7b8b",
    
               "tx_index": "00",
    
               "scriptSig": "4104e0bf225f8998c7e3dc99899ce2f08d87969ee1bdc0f0759941134f375fd1e8eecba4db44e3f76dce7fd16262c2ca9e93628eafde3d5a6ffa99ae13e74bbc54f6ac",
    
               "sequence": "ffffffff",
    
               "amount": "e86f7932"
    
              }
    
             ],
    
             "tx_outputs_count": "01",
    
             "tx_outputs": [
    
              {
    
               "amount": "48bc4631",
    
               "address": "3P14159f73E4gFr7JterCCQh9QjiTjiZrG"
    
              }
             ],

             "tx_changes_count": "01",
    
             "tx_changes": [
    
                 {
    
                     "path": "m/44'/02'/0'/0/0",
    
                     "amount": "002d3101"
    
                 }
    
              ],
    
              "lock_time": "1234"
    
    }
   ```
   ```js
    // A litecoin JSON sample
    
    {
    
          "tx_version": "01",
    
          "tx_inputs_count": "01",
    
          "tx_inputs": [
    
            {
    
             "path": "m/44'/02'/0'/0/0",
    
             "tx_id": "9c6700b994e811bc6c4ca6c83a3776fafa4f720f06a2240d3efb451791aa7b8b",
    
             "tx_index": "00",
    
             "scriptSig": "76a914feb5f43851477b22f68db0523351c4debbc67a7588ac",
    
             "sequence": "ffffffff",
    
             "amount": "e86f7932"
    
            }
    
          ],
    
    "tx_outputs_count": "01",
    
         "tx_outputs": [
    
          {
    
               "amount": "48bc4631",
    
               "address": "n4jjuARyw1nnHhta1fR5khPFS5i1BDakX6"
    
          }
    
         ],
    
    "tx_changes_count": "01",
    
      "tx_changes": [
    
       {
    
         "path": "m/44'/02'/0'/0/0",
    
         "amount": "002d3101"
    
       }
    
      ],
    
    "lock_time": "00"
    
    }
    
   ```
   ```js
    // A ethereum JSON sample
    
    {
    
      "path": "m/44'/60'/0'/0/0",
    
         "tx": {
    
           "nonce": "01",
    
           "gas_price": "09184e72a000",
    
           "gas_limit": "493e0",
    
           "to": "d8A7297522A2e30bE59f66e8CB2B06c89a50490c",
    
           "value": "38d7ea4c68000",
    
           "erc_flag": "0",
    
          "data": "",
    
           "chain_id": "04"
    
         }
    
    }
    
   ```
   ```js
    // A Ethereum ERC20 JSON sample
    
    {
    
       "path": "m/44'/60'/0'/0/0",
    
         "tx": {
    
             "nonce": "04",
    
             "gas_price": "0165a0bc00",
    
             "gas_limit": "928a",
    
             "to": "B8c77482e45F1F44dE1745F52C74426C631bDD52",
    
             "value": "",
    
             "erc_flag": "20",
    
             "data": "a9059cbb00000000000000000000000058a61a7144c8c7545794447a9a3e9a3d56066c200000000000000000000000000000000000000000000000001bc16d674ec80000",
    
             "chain_id": "04"
    
          }
    
    }
```

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/8.png" align="middle" width="700"></p>
    <center><b>
   Figure 8. Show the security UI for sign a transaction with BTC/LTC/ETH coin type </b></cemter>
   
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.

**Sign transaction for Ethereum ERC20:**
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;For Ethereum ERC20 secure UI, the parameters of extra tag can be used for customizing the icon, background color, token name, display full name of token, amount decimal for secure UI. For example, if I assign my parameters of extra tag as following JSON data format, and then the secure UI will show UI as these parameters.
```js
{

   "path":"m\/44'\/60'\/0'\/0\/0",

     "tx":{

        "nonce":"5",

        "gas_price":"12a05f200",

        "gas_limit":"5d82",

        "to":"0d8775f648430679a709e98d2b0cb6250d2887ef",

        "value":"",

        "erc_flag":"20",

        "data":"a9059cbb000000000000000000000000c24c375a77baf82750c46f1c76c809d5ad4c6769000000000000000000000000000000000000000000000003db72f26a08530000",

        "chain_id":"1"

     },

"currency":"USD",


   "extra": {

        "erc20_icon": "**<144*144 png image file raw data converted as HEX string>**",

        "erc20_symbol": "HAK",

        "erc20_displayname": "HAWK_TOKEN",

        "erc20_decimal": "18"

   }

}
```
The parameters of extra tag are specific as:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;erc20_icon: ERC20 Token icon binary data, the icon is png file format and don’t exceed 144 X 144 pixels, please convert to HEX String before put on json field.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;erc20_symbol: ERC20 token name

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;erc20_displayname: full display name of ERC20 token

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;erc20_decimal: A number divider for display (each token has its decimal define, please must fill the correct decimal of target token)

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/9.png" align="middle" width="700"></p>
    <center><b>
   Figure 9. Show the security UI with ERC20 extra tag for sign a transaction </b></cemter>
   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;For Ethereum [ERC20 smart contract methods](https://theethereum.wiki/w/index.php/ERC20_Token_Standard), ZKMA support ERC20 transfer method with data parsing, for other methods, sign TUI just showing unknown method with raw HEX data for user confirming.
```js
contract ERC20Interface {

    function totalSupply() public view returns (uint);

    function balanceOf(address tokenOwner) public view returns (uint balance);

    function allowance(address tokenOwner, address spender) public view returns (uint remaining);

    function transfer(address to, uint tokens) public returns (bool success); // ZKMA support it now.

    function approve(address spender, uint tokens) public returns (bool success);

    function transferFrom(address from, address to, uint tokens) public returns (bool success);

  
  

    event Transfer(address indexed from, address indexed to, uint tokens);

    event Approval(address indexed tokenOwner, address indexed spender, uint tokens);

}
```
**Sign transaction for Ethereum ERC721:**
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;For Ethereum ERC721, the parameters of extra tag can be used for customizing the icon, full name of ERC721 token for secure UI. Unlike ERC20, erc_flag and erc20_symbol of ERC721 tag can’t be used for customizing the secure UI. For secure UI display, erc_flag must be “721” and erc20_symbol must be "ERC-721". For example, if I assign my parameters of extra tag as following JSON data format, and then the secure UI will show UI as these parameters.
```js
{

   "path": "m/44'/60'/0'/0/0",

     "tx" : {

        "nonce": "04",

        "gas_price": "0165a0bc00",

        "gas_limit": "493e0",

        "to": "B8c77482e45F1F44dE1745F52C74426C631bDD52",

        "value": "",

        "erc_flag": "721",

        "data": "a9059cbb00000000000000000000000058a61a7144c8c7545794447a9a3e9a3d56066c200000000000000000000000000000000000000000000000001bc16d674ec80000",

        "chain_id": "03"

     },

   "extra": {

        "erc20_icon": "**<144*144 png image file raw data converted as HEX string>**",

        "erc20_symbol": "ERC-721",

        "erc20_displayname": "Crypto Kitties"

   }

}
```

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/10.png" align="middle" width="700"></p>
    <center><b>
   Figure 10. Show the security UI with ERC721 extra tag for sign a transaction </b></cemter>
   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;For Ethereum [ERC721 smart contract methods](http://erc721.org/), ZKMA only support ERC20 transfer method now. For those data format not supported by ZKMA, sign TUI just showing unknown method with raw HEX data for user confirming

## 3.12  Clear current seed of your wallet


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If the user want to create another new wallet, the wallet App needs to clear current wallet first, because it is an important thing to protect the previous wallet, the clearSeed method will clear all secure data which related to current wallet.
```java
int result = mHtcWalletSdkManager.clearSeed(unique_id);
```

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/11.png" align="middle" width="700"></p>
    <center><b>
   Figure 11. Show the security UI for clear an existing seed</b></cemter>
   
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.

## 3.13 Change the passcode
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;When you create a new wallet or restore an existing wallet, you will be asked to create the passcode to protect the seed access, once you think the passcode is not good enough and would like to use another passcode, this method will help to do this.

```java
int result = mHtcWalletSdkManager.changePIN(unique_id);
```

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/12.png" align="middle" width="700"></p>
    <center><b>
   Figure 12. Show the security UI for user to change their PIN code</b></cemter>
   

To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.
## 3.14 Confirm the passcode
Any PIN confirm action will also show a security UI for user to input PIN.
```java
int result = mHtcWalletSdkManager.confirmPIN(unique_id, resId = 3);
```
<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/13.png" align="middle" width="700"></p>
    <center><b>
   Figure 13. Show the security UI for user to confirm their PIN code</b></cemter>
   
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.

## 3.15 Unregister your wallet
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Using unregister method to release the acquired unique id, and it will also release the seed data which stored by ZKMA. If this method is called, the seed data will be cleared also.
```java
int result = mHtcWalletSdkManager.unregister(wallet_name, sha256, unique_id);
```
To prevent Trust Zone blocked UI thread, the developer must call this API in background thread.
## 3.16 De-Initialize
Release all resources which allocated by ZKMA, and all ZKMA methods will not be executed anymore.
```java
int result = mHtcWalletSdkManager.deinit();
```
## 3.17 Error Handling
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The developer can get the error code from the returned value. If any error code occurred, ZKMA will check and handle it by three types. The first type can be ignored since it is just a normal behavior, like E_TEEKM_UI_BACK, E_TEEKM_UI_CANCEL and so on. The second type is silent error type will not show any UI prompt by ZKMA since this silent error required App handle without ZKMA error prompt, like E_TEEKM_SEED_NOT_FOUND, E_TEEKM_TIME_TIMEOUT, E_SDK_ROM_SERVICE_TOO_OLD, E_SDK_ROM_TZAPI_TOO_OLD and so on. If the error code is excepted the listed in type1 and type2 both, ZKMA will handle it by ZKMA’s generic error dialog on the screen.

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/14.png" align="middle" width="700"></p>
    <center><b>
   Figure 14. Default error dialogs if an error detected by ZKMA</b></cemter>
   
All error code is defined in RESULT.java and can be found its meaning by name.
## 3.18 Sign Message
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ZKMA supports the message signing. If message contains non-ascii character, TUI showing hex data instead of the readable string
```java
int result = mHtcWalletSdkManager.signMessage(unique_id, coin_type, strJson, ByteArrayHolder);
```
### A. Ethereum coin
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A Ethereum sample JSON message format for signing, the data field in JSON can only support ASCII string. In the sample JSON data field, “48656c6c6f” is HEX string data of “Hello”. In other word, you must convert your ASCII string to HEX string data first, then put it into the data TAG for composing the input strJson parameter. About version TAG, this is implemented by [EIP191](https://eips.ethereum.org/EIPS/eip-191) and must set to 0x45.
```js
{

   "path": "m/44'/60'/0'/0/0",

     "message" : {

        "version": "45",

        "data": "48656c6c6f"

     }

}
```
Basically, a message data in JSON should contain version and data tags.

<p align="center"><img src="https://raw.githubusercontent.com/wiki/htczion/ZKMA/image/15.png" align="middle" width="700"></p>
    <center><b>
   Figure 15. Show the security UI for user to confirm the signing message operation</b></cemter>
   
