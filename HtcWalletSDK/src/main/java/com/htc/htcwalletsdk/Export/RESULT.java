package com.htc.htcwalletsdk.Export;

/**
 * Result for check by API users
 *
 * The latest TZ ERROR code in teekm_error.h at:
 * http://git.htc.com:8081/gitweb?p=common/htc/proprietary/securemsm.git;a=tree;f=teekm;hb=refs/heads/bc/o-rel-qct845-exodus
*/ 

public class RESULT {
    ///////////////////////////// POSITIVE NUMBER is for GENERIC //////////////////////////
    // Generic Errors
    public static  final int SUCCESS        =  0; //
    public static  final int NOT_ROOTED     =  0; //
    public static  final int REGISTER_FAILED =  0; // register API return non-ZERO value as unique_id

    public static  final int E_SDK_GENERIC_ERROR  =  -10000;
    public static  final int E_SDK_INVALID_ARG    =  E_SDK_GENERIC_ERROR - 303;
    public static  final int E_SDK_DOWNLOAD_TZTABL = E_SDK_GENERIC_ERROR - 100;


    public static  final int TEST_GENERIC_ERROR   =  -11000;
    public static  final int TEST_TYPE1_ERROR     =  TEST_GENERIC_ERROR - 1;
    public static  final int TEST_TYPE2_ERROR     =  TEST_GENERIC_ERROR - 2;
    public static  final int TEST_TYPE3_ERROR     =  TEST_GENERIC_ERROR - 3;
    public static  final int TEST_TYPE4_ERROR     =  TEST_GENERIC_ERROR - 4;
    public static  final int TEST_TYPE5_ERROR     =  TEST_GENERIC_ERROR - 5;
    public static  final int TEST_TYPE6_ERROR     =  TEST_GENERIC_ERROR - 6;
    public static  final int TEST_TYPE7_ERROR     =  TEST_GENERIC_ERROR - 7;
    public static  final int TEST_TYPE8_ERROR     =  TEST_GENERIC_ERROR - 8;
    public static  final int TEST_TYPE9_ERROR     =  TEST_GENERIC_ERROR - 9;
    public static  final int TEST_TYPE10_ERROR    =  TEST_GENERIC_ERROR - 10;



    public static  final int E_SDK_MODULE_FAILURE =  -20000;
    public static  final int E_SDK_SERVICE_LOAD_FAILURE =  E_SDK_MODULE_FAILURE - 1;

    public static  final int E_ZKMS_CALLER_FAILURE = -40000;
    public static  final int E_ZKMS_PACKAGE_NOT_FOUND      = E_ZKMS_CALLER_FAILURE - 1;
    public static  final int E_ZKMS_AUTHOR_NOT_FOUND       = E_ZKMS_CALLER_FAILURE - 2;
    public static  final int E_ZKMS_PERMISSION_NOT_GRANTED = E_ZKMS_CALLER_FAILURE - 3;
    public static  final int E_ZKMS_SIGNATURE_NOT_MATCH    = E_ZKMS_CALLER_FAILURE - 4;
    public static  final int E_ZKMS_PERMISSION_DENIED      = E_ZKMS_CALLER_FAILURE - 5;

    public static  final int E_SDK_COMPATIBLE_FAILURE =  -50000;
    public static  final int E_SDK_VERISON_UNKNOWN =  E_SDK_COMPATIBLE_FAILURE - 1;
    public static  final int E_SDK_ROM_SERVICE_TOO_OLD =  E_SDK_COMPATIBLE_FAILURE - 2;
    public static  final int E_SDK_ROM_TZAPI_TOO_OLD =  E_SDK_COMPATIBLE_FAILURE - 3;
    public static  final int E_ZKMA_TOO_OLD =  E_SDK_COMPATIBLE_FAILURE - 4;
    public static  final int E_ZKMS_TOO_OLD =  E_SDK_COMPATIBLE_FAILURE - 5;

    public static  final int E_SDK_CUSTOM_ERRORMSG =  -70000;

    public static  final int UNKNOWN        =  -99999;

    ///////////////////////////// NEGATIVE NUMBER is only for TZ //////////////////////////
    // TZ API ERRORS
    public static  final int E_TEEKM_OK = 0;
    public static  final int E_TEEKM_REGISTER_ALREADY = 1;

    public static  final int E_TEEKM_UNKNOWN_ERROR = -1;

	// TZ UI
    public static  final int E_TEEKM_UI_GENERAL = -100;
    public static  final int E_TEEKM_UI_CANCEL = (E_TEEKM_UI_GENERAL-1);	// users press UI Cancel
    public static  final int E_TEEKM_UI_BACK = (E_TEEKM_UI_GENERAL-2);	    // users press UI Back
    public static  final int E_TEEKM_UI_EDIT = (E_TEEKM_UI_GENERAL-3);	    // users press UI Edit in sign-transaction page
    public static  final int E_TEEKM_UI_REJECT = (E_TEEKM_UI_GENERAL-4);    // users press UI Reject in show-verification-code page
    public static  final int E_TEEKM_VC_CANCEL = (E_TEEKM_UI_GENERAL-5);    // users press UI Cancel in ShowVerificationCode page

	// TZ UI related
    public static  final int E_TEEKM_UI_RELATED = -200;
    public static  final int E_TEEKM_INVALID_PIN = (E_TEEKM_UI_RELATED-1);	// users enter wrong pin
    public static  final int E_TEEKM_RETRY_15M = (E_TEEKM_UI_RELATED-2);	// users has entered wrong pin 3 times; need to wait 15 minutes
    public static  final int E_TEEKM_RETRY_30M = (E_TEEKM_UI_RELATED-3);	// users has entered wrong pin 6 times; need to wait 30 minutes
    public static  final int E_TEEKM_RETRY_45M = (E_TEEKM_UI_RELATED-4);	// users has entered wrong pin 9 times; need to wait 45 minutes
    public static  final int E_TEEKM_RETRY_RESET = (E_TEEKM_UI_RELATED-5);	// users has entered wrong pin 12 times; the registered data is cleared
    public static  final int E_TEEKM_NULL_LAYOUT = (E_TEEKM_UI_RELATED-6);  // loading layout error
    public static  final int E_TEEKM_INIT_FAIL = (E_TEEKM_UI_RELATED-7);    // general init fail
    public static  final int E_TEEKM_LOAD_LAYOUT_FAIL = (E_TEEKM_UI_RELATED-8); // layout_mgr_load_layout fail
    public static  final int E_TEEKM_IMAGE_BACKGROUND_FAIL = (E_TEEKM_UI_RELATED-9); // image_background_init_and_set fail
    public static  final int E_TEEKM_KEYBOARD_BACKGROUND_FAIL = (E_TEEKM_UI_RELATED-10); // keyboard_background_init+and_set fail
    public static  final int E_TEEKM_SET_INPUT_BUFFER_FAIL = (E_TEEKM_UI_RELATED-11); // layout_mgr_set_buffer_for_input fail
    public static  final int E_TEEKM_SET_SHOW_FALG_FAIL = (E_TEEKM_UI_RELATED-12); // layout_mgr_set_show_flag fail
    public static  final int E_TEEKM_SET_IMAGE_FAIL = (E_TEEKM_UI_RELATED-13); // image_init_and_set fail
    public static  final int E_TEEKM_RANDOM_KEYBOARD_FAIL = (E_TEEKM_UI_RELATED-14); // layout_mgr_randomize_keyboard fail
    public static  final int E_TEEKM_SPLIT_STRING_FAIL = (E_TEEKM_UI_RELATED-15); // split_string_by_space fail
    public static  final int E_TEEKM_SET_FOCUS_INPUT_FAIL = (E_TEEKM_UI_RELATED-16); // layout_mgr_set_focus_input fail
    public static  final int E_TEEKM_TX_COIN_TYPE_ERROR = (E_TEEKM_UI_RELATED-17); // transaction coinType error
    public static  final int E_TEEKM_SET_TEXT_FONT_FAIL = (E_TEEKM_UI_RELATED-18); // layout_mgr_set_text_font fail
    public static  final int E_TEEKM_EXCEED_TEXT_RECTANGLE = (E_TEEKM_UI_RELATED-19); // transaction lbe_amCrypt length exceed max width
    public static  final int E_TEEKM_GET_TEXT_FAIL = (E_TEEKM_UI_RELATED-20); // layout_mgr_get_text fail
    public static  final int E_TEEKM_SET_PIN_FAIL = (E_TEEKM_UI_RELATED-21); // TEESetPin fail
    public static  final int E_TEEKM_GET_FOCUS_INPUT_FAIL = (E_TEEKM_UI_RELATED-22); // layout_mgr_get_focus_input fail
    public static  final int E_TEEKM_SET_TEXT_COLOR_FAIL = (E_TEEKM_UI_RELATED-23); // layout_mgr_set_text_color fail
    public static  final int E_TEEKM_INPUT_BACKGROUND_FAIL = (E_TEEKM_UI_RELATED-24); // input_background_init_and_set fail
    public static  final int E_TEEKM_GET_PIN_FAIL = (E_TEEKM_UI_RELATED-25); // TEEGetPin fail
    public static  final int E_TEEKM_RESTORE_SEED_FAIL = (E_TEEKM_UI_RELATED-26);	// TEERestoreSeed fail
    public static  final int E_TEEKM_SET_BUTTON_STATE_FAIL = (E_TEEKM_UI_RELATED-27);        // layout_mgr_set_button_state fail
    public static  final int E_TEEKM_FREE_RESOURCES_FAIL = (E_TEEKM_UI_RELATED-28);  // Free UI resources fail
    public static  final int E_TEEKM_API_FAIL = (E_TEEKM_UI_RELATED-29);     // Execute TEEKM API fail

    // Basic
    public static  final int E_TEEKM_GENERAL = -300;
    public static  final int E_TEEKM_FAILURE = (E_TEEKM_GENERAL-1);
    public static  final int E_TEEKM_NO_MEMORY = (E_TEEKM_GENERAL-2);
    public static  final int E_TEEKM_INVALID_ARG = (E_TEEKM_GENERAL-3);
    public static  final int E_TEEKM_TAMPERED = (E_TEEKM_GENERAL-4);     // device has been tampered
    public static  final int E_TEEKM_ALLOC = (E_TEEKM_GENERAL-5);        // failed in allcoting
    public static  final int E_TEEKM_BUFFER_REG = (E_TEEKM_GENERAL-6);   // qsee register shared buffer failed
    public static  final int E_TEEKM_BUFFER_PREP_S = (E_TEEKM_GENERAL-7);    // qsee prepare shared buffer for secure failed
    public static  final int E_TEEKM_BUFFER_PREP_NS = (E_TEEKM_GENERAL-8);   // qsee prepare shared buffer for non-secure failed
    public static  final int E_TEEKM_BUFFER_DEREG = (E_TEEKM_GENERAL-9); // qsee deregister shared buffer failed
    
	// TZ Register
    public static  final int E_TEEKM_REGISTER_GENERAL = -400;
    public static  final int E_TEEKM_REGISTER_NOT_AVALIABLE = (E_TEEKM_REGISTER_GENERAL-1);	// package data is over4K, only can register 512 different packages
    public static  final int E_TEEKM_FUSE_NOT_ENABLED = (E_TEEKM_REGISTER_GENERAL-2);	    // the fuse of devices have not been blown yet
    public static  final int E_TEEKM_UID_NOT_FOUND = (E_TEEKM_REGISTER_GENERAL-3);		        // unique id not found
    public static  final int E_TEEKM_UID_IS_WRONG = (E_TEEKM_REGISTER_GENERAL-4);		    // unique id does not match
    public static  final int E_TEEKM_CMDID_IS_WRONG = (E_TEEKM_REGISTER_GENERAL-5);		// cmd id does not match
    public static  final int E_TEEKM_MNEMONIC_IS_NULL = (E_TEEKM_REGISTER_GENERAL-6);    // mnemonic is null

	// TZ Seed
    public static  final int E_TEEKM_SEED_GENERAL = -500;
    public static  final int E_TEEKM_SEED_EXISTS = (E_TEEKM_SEED_GENERAL-1);		// seed already exists
    public static  final int E_TEEKM_SEED_NOT_FOUND = (E_TEEKM_SEED_GENERAL-2);	    // seed not found
    public static  final int E_TEEKM_PIN_NOT_FOUND = (E_TEEKM_SEED_GENERAL-3);	// pin not found 
    public static  final int E_TEEKM_INVALID_SEED = (E_TEEKM_SEED_GENERAL-4);	// wrong mnemonic words 
    public static  final int E_TEEKM_PARSE_PATH = (E_TEEKM_SEED_GENERAL-5);		// failed in parsing path 
    public static  final int E_TEEKM_HD_DEPTH = (E_TEEKM_SEED_GENERAL-6);		// depth is less than 2 
    public static  final int E_TEEKM_EXTKEY_SIZE = (E_TEEKM_SEED_GENERAL-7);		// key size is wrong 
    public static  final int E_TEEKM_COIN_INFO = (E_TEEKM_SEED_GENERAL-8);		// failed in getting coin info 
    public static  final int E_TEEKM_COIN_TYPE = (E_TEEKM_SEED_GENERAL-9);		// coin type is not supported 
    public static  final int E_TEEKM_KEYBOARD_TYPE = (E_TEEKM_SEED_GENERAL-10);  // keyboard type not 0 or 1
    public static  final int E_TEEKM_PASSCODE_LENGTH = (E_TEEKM_SEED_GENERAL-11); // passcode length error, 6 <= length <= 16
    public static  final int E_TEEKM_PASSCODE_NOT_FOUND = (E_TEEKM_SEED_GENERAL-12); // passcode not found

    // Store
    public static  final int E_TEEKM_STORE_GENERAL = -600;
    public static  final int E_TEEKM_STORE_INIT = (E_TEEKM_STORE_GENERAL-1);		// failed in initing rpmb
    public static  final int E_TEEKM_STORE_OPEN = (E_TEEKM_STORE_GENERAL-2);		// failed in opening rpmb partition
    public static  final int E_TEEKM_STORE_ADD = (E_TEEKM_STORE_GENERAL-3);		// failed in adding rpmb partition
    public static  final int E_TEEKM_STORE_KDF = (E_TEEKM_STORE_GENERAL-4);		// failed in generating key
    public static  final int E_TEEKM_STORE_READ = (E_TEEKM_STORE_GENERAL-5);		// failed in read sectors
    public static  final int E_TEEKM_STORE_WRITE = (E_TEEKM_STORE_GENERAL-6);	// failed in write sectors
    public static  final int E_TEEKM_STORE_INFO = (E_TEEKM_STORE_GENERAL-7);		// failed in getting info
    public static  final int E_TEEKM_STORE_CIPHER = (E_TEEKM_STORE_GENERAL-8);	// failed in cipher
    public static  final int E_TEEKM_STORE_CRC = (E_TEEKM_STORE_GENERAL-9);		// failed in cchecksum

	// Client 
    public static  final int E_TEEKM_CLIENT_GENERAL = -700;
    public static  final int E_TEEKM_CLIENT_HANDLE = (E_TEEKM_CLIENT_GENERAL-1);	// QSEEComHandle NULL
    public static  final int E_TEEKM_CLIENT_EVENT = (E_TEEKM_CLIENT_GENERAL-2);	// eventfd failed
    public static  final int E_TEEKM_CLIENT_TOUCH = (E_TEEKM_CLIENT_GENERAL-3);	// UseSecureTouch failed
    public static  final int E_TEEKM_CLIENT_BIO_NEW = (E_TEEKM_CLIENT_GENERAL-4);	// BIO_new_mem_buf failed
    public static  final int E_TEEKM_CLIENT_BIO_D2I = (E_TEEKM_CLIENT_GENERAL-5);	// d2i_PUBKEY_bio failed
    public static  final int E_TEEKM_CLIENT_PUB_KEY = (E_TEEKM_CLIENT_GENERAL-6);	// EVP_PKEY_get1_RSA failed
    public static  final int E_TEEKM_CLIENT_PUB_EXP = (E_TEEKM_CLIENT_GENERAL-7);	// public key exponent is not 65537
    public static  final int E_TEEKM_CLIENT_PUB_SIZE = (E_TEEKM_CLIENT_GENERAL-8);	// public key size is wrong
    public static  final int E_TEEKM_CLIENT_STARTUP = (E_TEEKM_CLIENT_GENERAL-9);	// failed in starting up qsee ap
    public static  final int E_TEEKM_CLIENT_BW = (E_TEEKM_CLIENT_GENERAL-10);	// failed in setting up bandwidth
    public static  final int E_TEEKM_CLIENT_TEECMD = (E_TEEKM_CLIENT_GENERAL-11);	// failed in sending cmds
    public static  final int E_TEEKM_CLIENT_SCREEN_OFF = (E_TEEKM_CLIENT_GENERAL-12);        // Screen is OFF

	// Time 
    public static  final int E_TEEKM_TIME_GENERAL = -800;
    public static  final int E_TEEKM_TIME_GET_SYSTEM_ERROR = (E_TEEKM_CLIENT_GENERAL-1);	// get system fail
    public static  final int E_TEEKM_TIME_CHECK_LEAP_YEAR_ERROR = (E_TEEKM_CLIENT_GENERAL-2);	// get system fail
    public static  final int E_TEEKM_TIMESTAMP_DIFF_IS_NEGATIVE = (E_TEEKM_CLIENT_GENERAL-3);	// get system fail
    public static  final int E_TEEKM_NOT_TIME_YET = (E_TEEKM_TIME_GENERAL-4); // It's not the time to unlock; it means need to wait
    public static  final int E_TEEKM_TIME_TIMEOUT = (E_TEEKM_TIME_GENERAL-5); // User didn't touch the display so abort
    public static  final int E_TEEKM_VC_TIMEOUT = (E_TEEKM_TIME_GENERAL-6);   // User didn't touch the display so abort, for ShowVerificationCode only
    // SET_WRITE_TEXT_ORDER
    public static  final int E_TEEKM_SET_WRITE_TEXT_ORDER_GENERAL = -890;
    public static  final int E_TEEKM_SET_WRITE_TEXT_ORDER_VAL = (E_TEEKM_SET_WRITE_TEXT_ORDER_GENERAL-0);   // setWriteTextOrder value error
    public static  final int E_TEEKM_SET_WRITE_TEXT_ORDER_OPEN = (E_TEEKM_SET_WRITE_TEXT_ORDER_GENERAL-1);  // setWriteTextOrder sfs open error
    public static  final int E_TEEKM_SET_WRITE_TEXT_ORDER_WRITE = (E_TEEKM_SET_WRITE_TEXT_ORDER_GENERAL-2); // setWriteTextOrder sfs write error
    public static  final int E_TEEKM_SET_WRITE_TEXT_ORDER_READ = (E_TEEKM_SET_WRITE_TEXT_ORDER_GENERAL-3);  // getWriteTextOrder sfs read error

    // SSS
    public static  final int E_TEEKM_SSS_GENERAL = -900;
    public static  final int E_TEEKM_SSS_SAVE_VERIFY_CODE = (E_TEEKM_SSS_GENERAL-1); // sfs write error
    public static  final int E_TEEKM_SSS_LOAD_VERIFY_CODE = (E_TEEKM_SSS_GENERAL-2); // sfs read error
    public static  final int E_TEEKM_SSS_UNIQUE_ID = (E_TEEKM_SSS_GENERAL-3); // unique_id error
    public static  final int E_TEEKM_SSS_RSA_RT_DECRYPT = (E_TEEKM_SSS_GENERAL-4); // luck key decrypt error
    public static  final int E_TEEKM_SSS_AES_DECRYPT = (E_TEEKM_SSS_GENERAL-5); // aes decrypt error
    public static  final int E_TEEKM_SSS_AES_VERIFY_CODE = (E_TEEKM_SSS_GENERAL-6); // aes verify code decrypt error
    public static  final int E_TEEKM_SSS_VERIFY_CODE_NOT_MATCH = (E_TEEKM_SSS_GENERAL-7); // verify code not match
    public static  final int E_TEEKM_SSS_BASE64_DECODE = (E_TEEKM_SSS_GENERAL-8); // base64 decode error
    public static  final int E_TEEKM_SSS_ASN1_PARSER = (E_TEEKM_SSS_GENERAL-9); // asn1 parser error
    public static  final int E_TEEKM_SSS_COMBINE_SHARES = (E_TEEKM_SSS_GENERAL-10); // sss combine shares error
    public static  final int E_TEEKM_SSS_ENC_VERIFY_CODE_LEN = (E_TEEKM_SSS_GENERAL-11);
    public static  final int E_TEEKM_SSS_SET_VERIFY_CODE_LEN = (E_TEEKM_SSS_GENERAL-12);
    public static  final int E_TEEKM_SSS_SET_VERIFY_CODE_ID = (E_TEEKM_SSS_GENERAL-13);
    public static  final int E_TEEKM_SSS_LOAD_VERIFY_CODE_OPEN = (E_TEEKM_SSS_GENERAL-14);
    public static  final int E_TEEKM_SSS_LOAD_VERIFY_CODE_READ = (E_TEEKM_SSS_GENERAL-15);
    public static  final int E_TEEKM_SSS_SAVE_VERIFY_CODE_OPEN = (E_TEEKM_SSS_GENERAL-16);
    public static  final int E_TEEKM_SSS_SAVE_VERIFY_CODE_LEN = (E_TEEKM_SSS_GENERAL-17);
    public static  final int E_TEEKM_SSS_SAVE_VERIFY_CODE_WRITE = (E_TEEKM_SSS_GENERAL-18);
    public static  final int E_TEEKM_SSS_RSA_SIGN_VERIFY = (E_TEEKM_SSS_GENERAL-19);
    public static  final int E_TEEKM_SSS_ENT_VERIFY_BUF_SIZE = (E_TEEKM_SSS_GENERAL-20);
    public static  final int E_TEEKM_SSS_ENT_ENCRYPT_SK = (E_TEEKM_SSS_GENERAL-21);
    public static  final int E_TEEKM_SSS_ENT_SIGN_RK = (E_TEEKM_SSS_GENERAL-22);
    public static  final int E_TEEKM_SSS_SAVE_VC_RETRY_OPEN = (E_TEEKM_SSS_GENERAL-23);
    public static  final int E_TEEKM_SSS_SAVE_VC_RETRY_WRITE = (E_TEEKM_SSS_GENERAL-24);
    public static  final int E_TEEKM_SSS_LOAD_VC_RETRY_OPEN = (E_TEEKM_SSS_GENERAL-25);
    public static  final int E_TEEKM_SSS_LOAD_VC_RETRY_READ = (E_TEEKM_SSS_GENERAL-26);
    public static  final int E_TEEKM_SSS_RE_TRY_COUNT_0 = (E_TEEKM_SSS_GENERAL-27);
    public static  final int E_TEEKM_SSS_SAVE_ENVIRONMENT_OPEN = (E_TEEKM_SSS_GENERAL-28);
    public static  final int E_TEEKM_SSS_SAVE_ENVIRONMENT_WRITE = (E_TEEKM_SSS_GENERAL-29);
    public static  final int E_TEEKM_SSS_SET_ENVIRONMENT_VAL = (E_TEEKM_SSS_GENERAL-30);
    public static  final int E_TEEKM_SSS_SAVE_SHARES = (E_TEEKM_SSS_GENERAL-31);

    //EXCHANGE 
    public static  final int E_TEEKM_EXCHANGE_GENERAL = -950;
    public static  final int E_TEEKM_EXCHANGE_GET_EXT_PK = (E_TEEKM_EXCHANGE_GENERAL-1); // exchange get ext public key error
    public static  final int E_TEEKM_EXCHANGE_ENCRYPT_SK = (E_TEEKM_EXCHANGE_GENERAL-2); // rsa encrypt by server key error
    public static  final int E_TEEKM_EXCHANGE_RSA_SIGN_RK = (E_TEEKM_EXCHANGE_GENERAL-3); // rsa signed by luck key error
    public static  final int E_TEEKM_EXCHANGE_ADDR_COINTYPE = (E_TEEKM_EXCHANGE_GENERAL-4);
    
    //JSON 
    public static  final int E_TEEKM_JSON_GENERAL = -1000;
    public static  final int E_TEEKM_JSON_ROOT_VALUE = (E_TEEKM_JSON_GENERAL-1);
    public static  final int E_TEEKM_JSON_ROOT_VALUE_TYPE = (E_TEEKM_JSON_GENERAL-2);
    public static  final int E_TEEKM_JSON_ROOT_VALUE_OBJECT = (E_TEEKM_JSON_GENERAL-3);
    public static  final int E_TEEKM_JSON_TX_VERSION = (E_TEEKM_JSON_GENERAL-4);
    public static  final int E_TEEKM_JSON_LOCK_TIME = (E_TEEKM_JSON_GENERAL-5);
    public static  final int E_TEEKM_JSON_NO_CURRENCY = (E_TEEKM_JSON_GENERAL-6);
    public static  final int E_TEEKM_JSON_NO_TX_INPUT = (E_TEEKM_JSON_GENERAL-7);
    public static  final int E_TEEKM_JSON_INPUT_NO = (E_TEEKM_JSON_GENERAL-8);
    public static  final int E_TEEKM_JSON_IN_OBJECT = (E_TEEKM_JSON_GENERAL-9);
    public static  final int E_TEEKM_JSON_NO_TX_ID = (E_TEEKM_JSON_GENERAL-10);
    public static  final int E_TEEKM_JSON_NO_TX_INDEX = (E_TEEKM_JSON_GENERAL-11);
    public static  final int E_TEEKM_JSON_NO_SEQUENCE = (E_TEEKM_JSON_GENERAL-12);
    public static  final int E_TEEKM_JSON_NO_SCRIPT_SIG = (E_TEEKM_JSON_GENERAL-13);
    public static  final int E_TEEKM_JSON_NO_IN_PATH = (E_TEEKM_JSON_GENERAL-14);
    public static  final int E_TEEKM_JSON_IN_PATH_LEN = (E_TEEKM_JSON_GENERAL-15);
    public static  final int E_TEEKM_JSON_IN_NO_AMOUNT = (E_TEEKM_JSON_GENERAL-16);
    public static  final int E_TEEKM_JSON_NO_TX_OUTPUT = (E_TEEKM_JSON_GENERAL-17);
    public static  final int E_TEEKM_JSON_OUTPUT_NO = (E_TEEKM_JSON_GENERAL-18);
    public static  final int E_TEEKM_JSON_OUT_OBJECT = (E_TEEKM_JSON_GENERAL-19);
    public static  final int E_TEEKM_JSON_OUT_NO_AMOUNT = (E_TEEKM_JSON_GENERAL-20);
    public static  final int E_TEEKM_JSON_OUT_NO_ADDRESS = (E_TEEKM_JSON_GENERAL-21);
    public static  final int E_TEEKM_JSON_OUT_SECRET_ADDR_VERIFY = (E_TEEKM_JSON_GENERAL-22);
    public static  final int E_TEEKM_JSON_OUT_SECRET_ADDR_DECRYPT = (E_TEEKM_JSON_GENERAL-23);
    public static  final int E_TEEKM_JSON_OUT_SECRET_ADDR_LEN = (E_TEEKM_JSON_GENERAL-24);
    public static  final int E_TEEKM_JSON_OUT_ADDR_LEN = (E_TEEKM_JSON_GENERAL-25);
    public static  final int E_TEEKM_JSON_OUT_SCRIPT_ADDRESS = (E_TEEKM_JSON_GENERAL-26);
    public static  final int E_TEEKM_JSON_NO_TX_CHANGES = (E_TEEKM_JSON_GENERAL-27);
    public static  final int E_TEEKM_JSON_CHANGES_NO = (E_TEEKM_JSON_GENERAL-28);
    public static  final int E_TEEKM_JSON_CHG_NO_AMOUNT = (E_TEEKM_JSON_GENERAL-29);
    public static  final int E_TEEKM_JSON_CHG_NO_PATH = (E_TEEKM_JSON_GENERAL-30);
    public static  final int E_TEEKM_JSON_CHG_PATH_LEN = (E_TEEKM_JSON_GENERAL-31);
    public static  final int E_TEEKM_JSON_CHG_SCRIPT_PATH = (E_TEEKM_JSON_GENERAL-32);
    public static  final int E_TEEKM_JSON_VALUE = (E_TEEKM_JSON_GENERAL-33);
    public static  final int E_TEEKM_JSON_ETH_PATH = (E_TEEKM_JSON_GENERAL-34);
    public static  final int E_TEEKM_JSON_ETH_PATH_LEN = (E_TEEKM_JSON_GENERAL-35);
    public static  final int E_TEEKM_JSON_ETH_TX_OBJECT = (E_TEEKM_JSON_GENERAL-36);
    public static  final int E_TEEKM_JSON_ETH_TX_NONCE = (E_TEEKM_JSON_GENERAL-37);
    public static  final int E_TEEKM_JSON_ETH_TX_NONCE_LEN = (E_TEEKM_JSON_GENERAL-38);
    public static  final int E_TEEKM_JSON_ETH_TX_GAS_PRICE = (E_TEEKM_JSON_GENERAL-39);
    public static  final int E_TEEKM_JSON_ETH_TX_GAS_PRICE_LEN = (E_TEEKM_JSON_GENERAL-40);
    public static  final int E_TEEKM_JSON_ETH_TX_GAS_LIMIT = (E_TEEKM_JSON_GENERAL-41);
    public static  final int E_TEEKM_JSON_ETH_TX_GAS_LIMIT_LEN = (E_TEEKM_JSON_GENERAL-42);
    public static  final int E_TEEKM_JSON_ETH_TX_SECRET_TO = (E_TEEKM_JSON_GENERAL-43);
    public static  final int E_TEEKM_JSON_ETH_TX_SECRET_TO_SIGN = (E_TEEKM_JSON_GENERAL-44);
    public static  final int E_TEEKM_JSON_ETH_TX_SECRET_TO_VERIFY = (E_TEEKM_JSON_GENERAL-45);
    public static  final int E_TEEKM_JSON_ETH_TX_SECRET_TO_DECRYPT = (E_TEEKM_JSON_GENERAL-46);
    public static  final int E_TEEKM_JSON_ETH_TX_SECRET_TO_LEN = (E_TEEKM_JSON_GENERAL-47);
    public static  final int E_TEEKM_JSON_ETH_TX_TO = (E_TEEKM_JSON_GENERAL-48);
    public static  final int E_TEEKM_JSON_ETH_TX_VALUE = (E_TEEKM_JSON_GENERAL-49);
    public static  final int E_TEEKM_JSON_ETH_TX_VALUE_LEN = (E_TEEKM_JSON_GENERAL-50);
    public static  final int E_TEEKM_JSON_ETH_TX_DATA = (E_TEEKM_JSON_GENERAL-51);
    public static  final int E_TEEKM_JSON_ETH_TX_DATA_LEN = (E_TEEKM_JSON_GENERAL-52);
    public static  final int E_TEEKM_JSON_ETH_TX_CHAIN_ID = (E_TEEKM_JSON_GENERAL-53);
    public static  final int E_TEEKM_JSON_ETH_TX_CHAIN_ID_LEN = (E_TEEKM_JSON_GENERAL-54);
    public static  final int E_TEEKM_JSON_ETH_TX_ERC_FLAG = (E_TEEKM_JSON_GENERAL-55);
    public static  final int E_TEEKM_JSON_ETH_TX_ERC20 = (E_TEEKM_JSON_GENERAL-56);
    public static  final int E_TEEKM_JSON_ETH_NO_MSG = (E_TEEKM_JSON_GENERAL-57);
    public static  final int E_TEEKM_JSON_ETH_MSG_VERSION = (E_TEEKM_JSON_GENERAL-58);
    public static  final int E_TEEKM_JSON_ETH_MSG_VERSION_LEN = (E_TEEKM_JSON_GENERAL-59);
    public static  final int E_TEEKM_JSON_ETH_MSG_DATA = (E_TEEKM_JSON_GENERAL-60);
    public static  final int E_TEEKM_JSON_ETH_MSG_DATA_LEN = (E_TEEKM_JSON_GENERAL-61);
    public static  final int E_TEEKM_JSON_STELLAR_PATH = (E_TEEKM_JSON_GENERAL-62);
    public static  final int E_TEEKM_JSON_STELLAR_PATH_LEN = (E_TEEKM_JSON_GENERAL-63);
    public static  final int E_TEEKM_JSON_STELLAR_XDR = (E_TEEKM_JSON_GENERAL-64);
    public static  final int E_TEEKM_JSON_STELLAR_XDR_BASE64 = (E_TEEKM_JSON_GENERAL-65);

    public static  final int E_TEEKM_JSON_ERC20_VERSION = (E_TEEKM_JSON_GENERAL-80);
    public static  final int E_TEEKM_JSON_ERC20_VERSION_LEN = (E_TEEKM_JSON_GENERAL-81);
    public static  final int E_TEEKM_JSON_ERC20_ARRAY = (E_TEEKM_JSON_GENERAL-82);
    public static  final int E_TEEKM_JSON_ERC20_CONTRACT = (E_TEEKM_JSON_GENERAL-83);
    public static  final int E_TEEKM_JSON_ERC20_CONTRACT_LEN = (E_TEEKM_JSON_GENERAL-84);
    public static  final int E_TEEKM_JSON_ERC20_CONTRACT_NO_FOUND = (E_TEEKM_JSON_GENERAL-85);
    public static  final int E_TEEKM_JSON_ETH_KYBER_APPROVE = (E_TEEKM_JSON_GENERAL-90);
    public static  final int E_TEEKM_JSON_ETH_KYBER_TRADE = (E_TEEKM_JSON_GENERAL-91);
    public static  final int E_TEEKM_JSON_ETH_KYBER_TRADE_DATA_LEN = (E_TEEKM_JSON_GENERAL-92);
    public static  final int E_TEEKM_JSON_ETH_KYBER_TRADE_FUNCTION = (E_TEEKM_JSON_GENERAL-93);
    public static  final int E_TEEKM_JSON_ETH_KYBER_APPROVE_DATA_LEN = (E_TEEKM_JSON_GENERAL-94);
    public static  final int E_TEEKM_JSON_ETH_KYBER_APPROVE_FUNCTION = (E_TEEKM_JSON_GENERAL-95);

    public static  final int E_TEEKM_JSON_ETH721_DB_GENERAL = (E_TEEKM_JSON_GENERAL-100);
    public static  final int E_TEEKM_JSON_ETH721_DB_NO_VERSION = (E_TEEKM_JSON_ETH721_DB_GENERAL-1);
    public static  final int E_TEEKM_JSON_ETH721_DB_NO_ARRAY = (E_TEEKM_JSON_ETH721_DB_GENERAL-2);
    public static  final int E_TEEKM_JSON_ETH721_DB_ITEM = (E_TEEKM_JSON_ETH721_DB_GENERAL-3);
    public static  final int E_TEEKM_JSON_ETH721_DB_CONTRACT = (E_TEEKM_JSON_ETH721_DB_GENERAL-4);
    public static  final int E_TEEKM_JSON_ETH721_DB_CONTRACT_LEN = (E_TEEKM_JSON_ETH721_DB_GENERAL-5);
    public static  final int E_TEEKM_JSON_ETH721_DB_ISSUER = (E_TEEKM_JSON_ETH721_DB_GENERAL-6);
    public static  final int E_TEEKM_JSON_ETH721_DB_ISSUER_LEN = (E_TEEKM_JSON_ETH721_DB_GENERAL-7);
    public static  final int E_TEEKM_JSON_ETH721_DB_SYMBOL = (E_TEEKM_JSON_ETH721_DB_GENERAL-8);
    public static  final int E_TEEKM_JSON_ETH721_DB_SYMBOL_LEN = (E_TEEKM_JSON_ETH721_DB_GENERAL-9);
    public static  final int E_TEEKM_JSON_ETH721_DB_DISPLAYNAME = (E_TEEKM_JSON_ETH721_DB_GENERAL-10);
    public static  final int E_TEEKM_JSON_ETH721_DB_DISPLAYNAME_LEN = (E_TEEKM_JSON_ETH721_DB_GENERAL-11);
    public static  final int E_TEEKM_JSON_ETH721_DB_DECIMAL = (E_TEEKM_JSON_ETH721_DB_GENERAL-12);
    public static  final int E_TEEKM_JSON_ETH721_DB_DECIMAL_LEN = (E_TEEKM_JSON_ETH721_DB_GENERAL-13);
    public static  final int E_TEEKM_JSON_ETH721_DB_ERC_FLAG = (E_TEEKM_JSON_ETH721_DB_GENERAL-14);
    public static  final int E_TEEKM_JSON_ETH721_DB_ERC_FLAG_LEN = (E_TEEKM_JSON_ETH721_DB_GENERAL-15);
    public static  final int E_TEEKM_JSON_ETH721_DB_ICON = (E_TEEKM_JSON_ETH721_DB_GENERAL-16);
    public static  final int E_TEEKM_JSON_ETH721_DB_ICON_LEN = (E_TEEKM_JSON_ETH721_DB_GENERAL-17);
    public static  final int E_TEEKM_JSON_ETH721_DB_COLOR_INDEX_LEN = (E_TEEKM_JSON_ETH721_DB_GENERAL-18);

    public static  final int E_TEEKM_JSON_ETH721_DB_DECIAML_NOT_MATCH = (E_TEEKM_JSON_ETH721_DB_GENERAL-19);     // -1119
    public static  final int E_TEEKM_JSON_ETH721_DB_SYMBOL_NOT_MATCH = (E_TEEKM_JSON_ETH721_DB_GENERAL-20);      // -1120

    //SIGN 
    public static  final int E_TEEKM_SIGN_GENERAL = -1200;
    public static  final int E_TEEKM_SIGN_GET_PRIVATE_KEY = (E_TEEKM_SIGN_GENERAL-1);
    public static  final int E_TEEKM_SIGN_LOCKING_SCRIPT = (E_TEEKM_SIGN_GENERAL-2);
    public static  final int E_TEEKM_SIGN_VERITY_TYPE = (E_TEEKM_SIGN_GENERAL-3);
    public static  final int E_TEEKM_SIGN_VERIFY_ERROR = (E_TEEKM_SIGN_GENERAL-4);
    public static  final int E_TEEKM_SIGN_SCRIPT_DER_SIGNATURE = (E_TEEKM_SIGN_GENERAL-5);
    public static  final int E_TEEKM_SIGN_PARSE_SCRIPT_DER_SIGNATURE_1 = (E_TEEKM_SIGN_GENERAL-6);
    public static  final int E_TEEKM_SIGN_PARSE_SCRIPT_DER_SIGNATURE_2 = (E_TEEKM_SIGN_GENERAL-7);
    public static  final int E_TEEKM_SIGN_PARSE_SCRIPT_DER_SIGNATURE_3 = (E_TEEKM_SIGN_GENERAL-8);
    public static  final int E_TEEKM_SIGN_PARSE_SCRIPT_DER_SIGNATURE_4 = (E_TEEKM_SIGN_GENERAL-9);
    public static  final int E_TEEKM_SIGN_PARSE_SCRIPT_DER_SIGNATURE_5 = (E_TEEKM_SIGN_GENERAL-10);
    public static  final int E_TEEKM_SIGN_PARSE_SCRIPT_DER_SIGNATURE_6 = (E_TEEKM_SIGN_GENERAL-11);
    public static  final int E_TEEKM_SIGN_SCRIPT_GET_PUBLIC_KEY = (E_TEEKM_SIGN_GENERAL-12);
    public static  final int E_TEEKM_SIGN_SERIALIZE = (E_TEEKM_SIGN_GENERAL-13);
    public static  final int E_TEEKM_SIGN_SCRIPT_ADDRESS_DECODE = (E_TEEKM_SIGN_GENERAL-14);
    public static  final int E_TEEKM_SIGN_SCRIPT_ADDRESS_NOT_SUPPORT = (E_TEEKM_SIGN_GENERAL-15);

    public static  final int E_TEEKM_SIGN_ETH_V_VALUE = (E_TEEKM_SIGN_GENERAL-51);
    public static  final int E_TEEKM_SIGN_ETH_ECDSA_SIGN = (E_TEEKM_SIGN_GENERAL-52);
    public static  final int E_TEEKM_SIGN_ETH_PARSE_RLP = (E_TEEKM_SIGN_GENERAL-53);
    public static  final int E_TEEKM_SIGN_ETH_VERIFY = (E_TEEKM_SIGN_GENERAL-54);
    public static  final int E_TEEKM_SIGN_ETH_VERIFY_LEN = (E_TEEKM_SIGN_GENERAL-55);
    public static  final int E_TEEKM_SIGN_ETH_PARSE_TX_RLP = (E_TEEKM_SIGN_GENERAL-56);
    public static  final int E_TEEKM_SIGN_ETH_RLP_BUILD_SIGN_HASH_1 = (E_TEEKM_SIGN_GENERAL-57);
    public static  final int E_TEEKM_SIGN_ETH_RLP_BUILD_SIGN_HASH_2 = (E_TEEKM_SIGN_GENERAL-58);
    public static  final int E_TEEKM_SIGN_ETH_ECDSA_VERIFY = (E_TEEKM_SIGN_GENERAL-59);
    public static  final int E_TEEKM_SIGN_ETH_VERIFY_DIGEST_RECOVER = (E_TEEKM_SIGN_GENERAL-60);
    public static  final int E_TEEKM_SIGN_ETH_MSG_TX_OUT_LEN = (E_TEEKM_SIGN_GENERAL-61);

    // Stellar
    public static  final int E_TEEKM_STELLAR_XDR_GENERAL = -1300;
    public static  final int E_TEEKM_STELLAR_XDR_ACCOUNT = (E_TEEKM_STELLAR_XDR_GENERAL) - 1;
    public static  final int E_TEEKM_STELLAR_XDR_MEMO = (E_TEEKM_STELLAR_XDR_GENERAL) - 2;
    public static  final int E_TEEKM_STELLAR_XDR_OP_TYPE = (E_TEEKM_STELLAR_XDR_GENERAL) - 3;
    public static  final int E_TEEKM_STELLAR_XDR_ASSET_TYPE = (E_TEEKM_STELLAR_XDR_GENERAL) - 4;
    public static  final int E_TEEKM_STELLAR_XDR_LENGTH = (E_TEEKM_STELLAR_XDR_GENERAL) - 5;
    public static  final int E_TEEKM_STELLAR_ADDR_LEN = (E_TEEKM_STELLAR_XDR_GENERAL) - 6;
    public static  final int E_TEEKM_SIGN_STELLAR_VERIFY_LEN = (E_TEEKM_STELLAR_XDR_GENERAL) - 7;
    public static  final int E_TEEKM_STELLAR_XDR_ACCOUNT_NOT_MATCH = (E_TEEKM_STELLAR_XDR_GENERAL) - 8;
    public static  final int E_TEEKM_STELLAR_XDR_OP_NO = (E_TEEKM_STELLAR_XDR_GENERAL) - 9;

    // erc721 db
    public static  final int E_TEEKM_DATA_DB_GENERAL = -1400;
    public static  final int E_TEEKM_DATA_DB_KEY_FILE_OPEN = (E_TEEKM_DATA_DB_GENERAL-1);            // sfs open error
    public static  final int E_TEEKM_DATA_DB_KEY_FILENAME = (E_TEEKM_DATA_DB_GENERAL-2);             // filename error
    public static  final int E_TEEKM_DATA_DB_WRITE_HDR = (E_TEEKM_DATA_DB_GENERAL-3);                // sfs write header error
    public static  final int E_TEEKM_DATA_DB_WRITE_DB_ITEM = (E_TEEKM_DATA_DB_GENERAL-4);            // sfs write db item error
    public static  final int E_TEEKM_DATA_DB_ITEM_NULL = (E_TEEKM_DATA_DB_GENERAL-5);                // try to write a NULL item
    public static  final int E_TEEKM_DATA_DB_HND_NULL = (E_TEEKM_DATA_DB_GENERAL-6);                 // try to close a null handle file
    public static  final int E_TEEKM_DATA_DB_WRITE_KEYDB_CONTRACT = (E_TEEKM_DATA_DB_GENERAL-7);     // write contract error
    public static  final int E_TEEKM_DATA_DB_WRITE_KEYDB_POS = (E_TEEKM_DATA_DB_GENERAL-8);          // write postion error
    public static  final int E_TEEKM_DATA_DB_SEEK = (E_TEEKM_DATA_DB_GENERAL-9);                     // sfs seek error
    public static  final int E_TEEKM_DATA_DB_BUFFER_SIZE = (E_TEEKM_DATA_DB_GENERAL-10);             // buffer size error
    public static  final int E_TEEKM_DATA_DB_KEY_FILE_SIZE = (E_TEEKM_DATA_DB_GENERAL-11);           // db key file size error
    public static  final int E_TEEKM_DATA_DB_KEY_FILE_READ = (E_TEEKM_DATA_DB_GENERAL-12);           // db key read error
    public static  final int E_TEEKM_DATA_DB_KEY_CONTRACT_SIZE = (E_TEEKM_DATA_DB_GENERAL-13);       // contract size error
    public static  final int E_TEEKM_DATA_DB_KEY_NOT_FOUND = (E_TEEKM_DATA_DB_GENERAL-14);           // db key index not found
    public static  final int E_TEEKM_DATA_DB_FILE_SEEK = (E_TEEKM_DATA_DB_GENERAL-15);               // db file seek error
    public static  final int E_TEEKM_DATA_DB_FILE_READ = (E_TEEKM_DATA_DB_GENERAL-16);               // db key read error
    public static  final int E_TEEKM_DATA_DB_HASH = (E_TEEKM_DATA_DB_GENERAL-17);                    // db item hash error
    public static  final int E_TEEKM_DATA_DB_READ_BUFFER_SIZE = (E_TEEKM_DATA_DB_GENERAL-18);        // buffer size error
    public static  final int E_TEEKM_DATA_DB_MAGIC = (E_TEEKM_DATA_DB_GENERAL-19);                   // db magic error
    public static  final int E_TEEKM_DATA_DB_DATASET = (E_TEEKM_DATA_DB_GENERAL-20);                 // dataset not support
    public static  final int E_TEEKM_DATA_DB_RSA_SIGN_VERIFY = (E_TEEKM_DATA_DB_GENERAL-21);         // json verify fail
    public static  final int E_TEEKM_DATA_DB_WRITE_TEMP_FILE = (E_TEEKM_DATA_DB_GENERAL-22);         // db write temp file error
    
    
    // Monkey
    public static  final int E_TEEKM_NO_PROMPT_ERROR = -9000;
    public static  final int E_TEEKM_NO_PROMPT_ERROR_MONKEY = (E_TEEKM_NO_PROMPT_ERROR) - 1;

}
