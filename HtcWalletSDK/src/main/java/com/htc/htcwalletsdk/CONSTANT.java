package com.htc.htcwalletsdk;

/**
 * Created by hawk_wei on 2018/9/7.
 */

public class CONSTANT {

    public static final String PERMISSION_ACCESS_ZION = "com.htc.wallet.permission.ACCESS_ZION";

    public class ENVIRONMENT {
        public static final int SHIP         = 0;
        public static final int TEST         = 1;
        public static final int OTHER        = 2;
    }


    public class UI_ACTION {
        public static final String UI_CREATE_PIN = "UICreatePin";
        public static final String UI_SHOW_AND_VERIFY_WORDS = "UIShowAndVerifyWords";
        public static final String UI_RESTORE_WORDS = "UIRestoreWords";
        public static final String UI_VERIFY_PIN = "UIVerifyPin";
        public static final String UI_SHOW_WORDS = "UIShowWords";
        public static final String UI_RESET_CONFIRM = "UIResetConfirm";
        public static final String UI_GET_PUBLICKEY_CONFIRM = "UIGetPublicKeyConfirm";
        public static final String UI_TX_SIGN_CONFIRM = "UITxSignConfirm";
    }

    public class UI_IN_KEY {
        public static final String COIN_TYPE = "Coin type";
        public static final String COLOR_CODE = "Color code";
        public static final String PFD_CURRENCY = "Preferred_Currency";
        public static final String TO = "TO";
        public static final String FEE = "FEE";
        public static final String FEE_PFD = "FEE_Preferred";
        public static final String AMOUNT = "AMOUNT";
        public static final String AMOUNT_PFD = "AMOUNT_Preferred";
        public static final String KEY = "INPUT_KEY";
        public static final String LAST_ERR_PIN = "Last error pin";
        public static final String MINS = "LOCK_MINS";
        public static final String NAME = "NAME";
        public static final String CODE = "CODE";
        public static final String INFORMATION = "INFORMATION";
        public static final String FLAG = "flag";
        public static final String ERR_EN = "error_enable";
        public static final String ADDRESS = "address";
        public static final String MESSAGE = "message";
    }

    public class UI_OUT_KEY {
        public static final String PIN = "OUTPUT_PIN";
        public static final String KEY = "OUTPUT_KEY";
        public static final String RESET_WALLET = "RESET_WALLET";
        public static final String RESULT_KEY = "RESULT_KEY";
        public static final String SOCIAL_RECOVERY_CODE = "SOCIAL_RECOVERY_CODE";
    }

    public class UI_RESULT {
        public static final int CRASH = -1;
        public static final int SUCCESS = 0;
        public static final int ON_BACK = 1;
        public static final int ON_CANCEL = 2;
        public static final int ERROR = 3;
        public static final int REJECT = 4;
        public static final int CLOSE = 6;
    }

    public class REVIEW_COIN_TYPE {
        public static final int BTC = 0;
        public static final int LTC = 2;
        public static final int ETH = 60;
        public static final int BAT = 1;
        public static final int ERC20 = 5;
        public static final int DEFAULT = -1;
    }

    public class FLAG_UI_CREATE_PIN {
        public static final String CREATE_PIN = "create_pin";
        public static final String CREATE_NEW_PIN = "create_new_pin";
        public static final String CONFIRM_PIN = "confirm_pin";
        public static final String CONFIRM_NEW_PIN = "confirm_new_pin";
        public static final String CREATE_PIN_SHOW_ERROR_MESSAGE = "create_pin_show_error_message";
        public static final String CREATE_NEW_PIN_SHOW_ERROR_MESSAGE = "create_new_pin_show_error_message";
        public static final String CHANGE_PIN_CREATE_PIN = "change_pin_create_pin";
        public static final String CHANGE_PIN_CONFIRM_PIN = "change_pin_confirm_pin";
        public static final String CHANGE_PIN_CREATE_PIN_SHOW_ERROR_MESSAGE = "change_pin_create_pin_show_error_message";
    }

    public class FLAG_UI_VERIFY_PIN {
        public static final String LAUNCH_APP = "launch_app";
        public static final String LAUNCH_APP_SHOW_ERROR_MESSAGE = "launch_app_show_error_message";
        public static final String ENTER_PIN_REMOVE_CONTACT = "enter_pin_remove_contact";
        public static final String ENTER_PIN_REMOVE_CONTACT_SHOW_ERROR_MESSAGE = "enter_pin_remove_contact_show_error_message";
        public static final String ENTER_PIN_GENERATE_CODE = "enter_pin_generate_code";
        public static final String ENTER_PIN_GENERATE_CODE_SHOW_ERROR_MESSAGE = "enter_pin_generate_code_show_error_message";
        public static final String ENTER_PIN_CHANGE = "enter_pin_change";
        public static final String ENTER_PIN_CHANGE_SHOW_ERROR_MESSAGE = "enter_pin_change_show_error_message";
        public static final String ENTER_PIN_SHOW = "enter_pin_show";
        public static final String ENTER_PIN_SHOW_SHOW_ERROR_MESSAGE = "enter_pin_show_show_error_message";
        public static final String ENTER_PIN_CONTINUE = "enter_pin_continue";
        public static final String ENTER_PIN_CONTINUE_SHOW_ERROR_MESSAGE = "enter_pin_continue_show_error_message";
        public static final String ENTER_PIN_BACKUP_SECURITY_CHECK = "enter_pin_backup_security_check";
        public static final String ENTER_PIN_BACKUP_SECURITY_CHECK_SHOW_ERROR_MESSAGE = "enter_pin_backup_security_check_show_error_message";
        public static final String ENTER_PIN_LOGOUT = "enter_pin_confirm_log_out";
        public static final String ENTER_PIN_LOGOUT_SHOW_ERROR_MESSAGE = "enter_pin_confirm_log_out_show_error_message";
        public static final String ENTER_PIN_RESET_WALLET = "enter_pin_reset_wallet";
        public static final String ENTER_PIN_RESET_WALLET_SHOW_ERROR_MESSAGE = "enter_pin_reset_wallet_show_error_message";
        public static final String CONFIRM_PIN_REQUIRED = "confirm_pin_required";
        public static final String CONFIRM_PIN_REQUIRED_SHOW_ERROR_MESSAGE = "confirm_pin_required_show_error_message";
    }

    public class FLAG_UI_TRY_OFTEN {
        public static final String TRY_LATER = "try_later";
        public static final String TRY_TOO_OFTEN = "try_too_often";
    }

    public class FLAG_UI_VERIFICATION_CODE {
        public static final String GENERATE_VERIFICATION_CODE = "generate_verification_code";
        public static final String REJECT_VERIFICATION_CODE = "reject_verification_code";
    }

    public enum HW_SECURITY_UI {
        CREATE_PIN,
        CONFIRM_PIN,
        PIN_NOT_MATCH,
        RECOVERY_KEY_INTRODUCTION,
        WRITE_RECOVERY_KEY,
        VERIFY_RECOVERY_KEY_1,
        VERIFY_RECOVERY_KEY_2,
        VERIFY_RECOVERY_KEY_3,
        ENTER_RECOVERY_KEY_1,
        ENTER_RECOVERY_KEY_2,
        ENTER_RECOVERY_KEY_3,
        ENTER_SOCIAL_RESTORE,
        SECURITY_CHECK,
        CHANGE_PIN_A,
        CHANGE_PIN_B,
        CHANGE_PIN_C,
        BACKUP_SECURITY_CHECK,
        REMOVE_CONTACT,
        GENERATE_CODE,
        VERIFY_SOCIAL_CODE,
        SIGN_MSG_ETH_BG,
        SIGN_MSG_BTC_BG,
        SIGN_MSG_LTC_BG,
        SIGN_MSG_BAT_BG,
        RESET_WALLET_1,
        RESET_WALLET_2,
        SHOW_RECOVERY_KEY_1,
        SHOW_RECOVERY_KEY_2,
        CONFIRM_LOGOUT,
        NEXT_BUTTON,
        REVIEW_ETH_BG,
        REVIEW_BTC_BG,
        REVIEW_LTC_BG,
        REVIEW_BAT_BG,
        REVIEW_ERC_BG,
        REVIEW_ERC_DEFAULT_BG,
        REVIEW_ERROR_MSG,
        BTC_CANCEL_BUTTON,
        BTC_SEND_BUTTON,
        ETH_CANCEL_BUTTON,
        ETH_SEND_BUTTON,
        LTC_CANCEL_BUTTON,
        LTC_SEND_BUTTON,
        BAT_CANCEL_BUTTON,
        BAT_SEND_BUTTON,
        CREATE_PIN_ERROR_MSG,
        CONFIRM_PIN_ERROR_MSG,
        RECOVERY_ERROR_MSG,
        RECOVERY_INVALID_MSG,
        CONFIRM_PIN_REQUIRED,
    }

    public enum ShowPINUI {
        INITIAL_PIN,
        CREATE_PIN,
        CREATE_NEW_PIN,
        CONFIRM_PIN,
        CONFIRM_NEW_PIN,
        CREATE_PIN_SHOW_ERROR_MESSAGE,
        CREATE_NEW_PIN_SHOW_ERROR_MESSAGE,
        ENTER_PIN_UNLOCK_WALLET,
        ENTER_PIN_UNLOCK_WALLET_SHOW_ERROR_MESSAGE,
        ENTER_PIN_BACKUP_SECURITY_CHECK,
        ENTER_PIN_BACKUP_SECURITY_CHECK_SHOW_ERROR_MESSAGE,
        ENTER_PIN_LOGOUT,
        ENTER_PIN_LOGOUT_SHOW_ERROR_MESSAGE,
        ENTER_PIN_REMOVE_CONTACT,
        ENTER_PIN_REMOVE_CONTACT_SHOW_ERROR_MESSAGE,
        ENTER_PIN_GENERATE_CODE,
        ENTER_PIN_GENERATE_CODE_SHOW_ERROR_MESSAGE,
        ENTER_PIN_CONTINUE,
        ENTER_PIN_CONTINUE_SHOW_ERROR_MESSAGE,
        ENTER_PIN_CHANGE,
        ENTER_PIN_CHANGE_SHOW_ERROR_MESSAGE,
        CHANGE_PIN_CREATE_PIN,
        CHANGE_PIN_CONFIRM_PIN,
        CHANGE_PIN_CREATE_PIN_SHOW_ERROR_MESSAGE,
        ENTER_PIN_SHOW,
        ENTER_PIN_SHOW_SHOW_ERROR_MESSAGE,
        ENTER_PIN_RESET_WALLET,
        ENTER_PIN_RESET_WALLET_SHOW_ERROR_MESSAGE,
        SHOW_RESET_CONFIRM,
        CONFIRM_PIN_REQUIRED,    //UI layout follow LAUNCH APP(ENTER_PIN_UNLOCK_WALLET)
        CONFIRM_PIN_REQUIRED_SHOW_ERROR_MESSAGE
    }

    public enum ResultMsg {
        MSG_SUCCESS,
        MSG_FAILED
    }

    // data Set
    static public final int TABLE_ERC20_ERC721 = 20721;

}
