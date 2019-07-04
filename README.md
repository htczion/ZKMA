# Zion Key Management API 

[![Build Status](https://travis-ci.com/htczion/ZKMA.svg?branch=master)](https://travis-ci.com/htczion/ZKMA)

Integration guide for creating a wallet using Zion Key Management APIs

https://github.com/htczion/ZKMA/wiki



## Change List:  
For HW wallet, SDK will check the compatibility between ROM,SDK and TrustZone. 
About the details of version number, you can find at section [Compatibility](https://github.com/htczion/ZKMA/wiki#compatibility).  

### ZKMS ver: 2.0.0:   

- 3.2.2  
    Update strings.xml for multi-lang  
  
- 3.2.1  
    Removed the conflicted res  
  
- 3.2.0  
    Open source on GitHub  
  
- 3.1.2  
   Support readTzDataSet/writeTzDataSet error handling  
  
- 3.1.1  
   Add readTzDataSet/writeTzDataSet APIs  
  
- 3.1.0  
   Add permission check for exported APIs.  
  
- 3.0.5  
   Support readTzDataSet and writeTzDataSet  
  
- 3.0.2  
   show error prompt in same thread  
  
- 3.0.1  
   print stacktrace instead of exception  
  
- 3.0.0  
   Upload to GitHub  
     
- 2.2.2 (0.0005.01000038)  
   update for social key TEE  
  
- 2.2.0  
   ZKMALog instead of Log  
   TEEKHelper support readTzDataSet/writeTzDataSet/setKeyboardType/changePIN_v2  
  
- 2.1.1  
   Remove SW wallet  
   ByteArrayHolder will auto adjust output size(MAX=16KB) if input size(2KB) is not enough.  
  
- 2.0.1 (0.0005.01000031)  
   ignore E_TEEKM_VC_CANCEL and E_TEEKM_VC_TIMEOUT  
  
- 2.0.0 , ZKMS_ver= 2.0.0  
   start to support ZKMS  
  
### Not support ZKMS:   
  
- 1.4.1  
  remove PII  
  add dummy SW security for social key  
  
- 1.4.0  
  remove SW wallet  
  support signMultipleTransaction  
  
- 1.3.0  
  support troubleshooting dialog with multi-lang  
  
- 1.2.9  
  all error dialog applied Zion default background  
  
- 1.2.8  
  fix show err dialog crash App at API27:Only fullscreen opaque activities can request orientation.  
  
- 1.2.7 (0.0005.01000017)  
  limit SDK run on ROM 1.55  
  
- 1.2.6  
  ignore E_TEEKM_CLIENT_SCREEN_OFF and E_TEEKM_CLIENT_TOUCH error case  
  Added the custom error message dialog  
  support getPartialSeed_v2 and combineSeeds_v2  
  
- 1.2.5 (0.0005.01000011)  
  support signMessage for ETH  
  
- 1.2.4  
  fix UI error ITS#197  
  
- 1.2.3 (0.0005.0100000e)  
  Add setEnvironment  
  support Notify for UI events  
  Notify TryTooOften events for APP check  
  
- 1.2.2  
  ignore -907 and -927  
  Enable setSdkProtectorListener  
  
- 1.2.1 (0.0004.01000006)  
  TZ Basic error code base from 0 to 300  
  error dialog bypass E_TEEKM_UI_REJECT  
  
- 1.2.0 (0.0004.01010005)  
  add seed_index param to enterVerificationCode  
  show error code UI prompt dialog if HW wallet  
  error dialog supported multi-lang  
  register API can return error code  
  try-catch protect service API for compatible mode  
  fix bugs  
  
- 1.1.3  
  Remove error code UI prompt dialog whatever SW or HW wallet  
  
- 1.1.2  
  Remove error code UI prompt dialog if SW wallet  
  
- 1.1.1  
  Disable SDK proguard since SW wallet init got an exception.  
  
- 1.1.0 (0.0003.01010001)  
  Add following APIs  
	showVerificationCode  
	enterVerificationCode  
	getEncAddr  
	getPartialSeedv2  
	combineSeedsv2  
	changePIN(4)  
	setERC20BGColor  
	getTZIDHash  
  b. Any error code will show error dialog.  
  c. AAR proguard  
  
- 1.0.1 (0.0001.01000000)  
  a. Add following APIs  
	getInstance  
	init  
	getModuleVersion  
	getApiVersion  
	register  
	getSendPublicKey  
	getSendPublicKey with idx  
	getSendReceiveKey  
	getSendReceiveKey with idx  
	isSeedExists  
	createSeed  
	clearSeed  
	showSeed  
	restoreSeed  
	signTransaction  
	isRooted  
	unregister  
	deinit  
	getPartialSeed  
	getPartialSeed with RESULT  
	combineSeeds  
	changePIN(1,2,3)  
  b. Try too often will show block UI activity.	  

