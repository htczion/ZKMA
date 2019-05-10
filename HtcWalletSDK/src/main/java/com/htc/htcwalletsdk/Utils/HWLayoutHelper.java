package com.htc.htcwalletsdk.Utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.htc.htcwalletsdk.CONSTANT.HW_SECURITY_UI;

import static android.view.View.MeasureSpec.EXACTLY;

public class HWLayoutHelper {
    static final String TAG = "HWLayoutHelper";

    private Context mContext;
    private HW_SECURITY_UI mHwSecurityUi;
    private int mWidth;
    private int mHeight;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mSubtitle;
    private ProgressBar mProgBar1;
    private ProgressBar mProgBar2;
    private ProgressBar mProgBar3;
    private FrameLayout mDynamicContent;
    private ImageButton mBackButton;
    private ViewGroup mView;
    private TextView mDialogTitle;
    private TextView mMessage;
    private Button mPosButton;
    private Button mNegButton;
    private Button mTransactionButton;
    private Drawable mTransactionBtnBackground;

    BasePageHolder pageHolder;

    public HWLayoutHelper(Context context, HW_SECURITY_UI hwSecurityUi, int width, int height){
        this(context,hwSecurityUi,width,height,0);
    }

    public HWLayoutHelper(Context context, HW_SECURITY_UI hwSecurityUi, int width, int height, int color_table_index){
        this(context,hwSecurityUi,width,height,color_table_index,null);
    }

    public HWLayoutHelper(Context context, HW_SECURITY_UI hwSecurityUi, int width, int height, String name){
        this(context,hwSecurityUi,width,height,0,name);
    }

    public HWLayoutHelper(Context context, HW_SECURITY_UI hwSecurityUi, int width, int height, int color_table_index, String name){
        ZKMALog.i(TAG,"HW_SECURITY_UI:" + hwSecurityUi+ ", width:" + width + ". height:" + height);
        mContext = context;
        mHwSecurityUi = hwSecurityUi;
        mWidth = width;
        mHeight = height;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (mHwSecurityUi == HW_SECURITY_UI.SECURITY_CHECK || mHwSecurityUi == HW_SECURITY_UI.CONFIRM_PIN_REQUIRED) {
            mView = (ViewGroup)inflater.inflate(
                    getResourceId("activity_unlock_wallet","layout"), null);
            mTitle = mView.findViewById(getResourceId("title","id") );
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mTitle.getLayoutParams());
            int mLeft = mContext.getResources().getDimensionPixelOffset(getResourceId("double_margin_l","dimen"));
            int mTop = mContext.getResources().getDimensionPixelOffset(getResourceId("unlock_title_marginTop_HW","dimen"));
            int mRight = mContext.getResources().getDimensionPixelOffset(getResourceId("double_margin_l","dimen"));
            lp.setMargins(mLeft, mTop, mRight, 0);
            mTitle.setLayoutParams(lp);
        } else if (mHwSecurityUi == HW_SECURITY_UI.RESET_WALLET_2) {
            mView = (ViewGroup)inflater.inflate(
                    getResourceId("alert_dialog","layout"), null);
            mDialogTitle = mView.findViewById(getResourceId("alertTitle","id"));
            mMessage = mView.findViewById(getResourceId("message","id"));
            mPosButton = mView.findViewById(getResourceId("button2","id"));
            mNegButton = mView.findViewById(getResourceId("button3","id"));
        } else if (mHwSecurityUi == HW_SECURITY_UI.NEXT_BUTTON) {
            mView = (ViewGroup)inflater.inflate(
                    getResourceId("layout_next_button_for_exodus","layout"), null);
        } else if(mHwSecurityUi == HW_SECURITY_UI.REVIEW_ETH_BG ||
                mHwSecurityUi == HW_SECURITY_UI.REVIEW_BTC_BG ||
                mHwSecurityUi == HW_SECURITY_UI.REVIEW_LTC_BG ||
                mHwSecurityUi == HW_SECURITY_UI.REVIEW_BAT_BG ||
                mHwSecurityUi == HW_SECURITY_UI.REVIEW_ERC_DEFAULT_BG ||
                mHwSecurityUi == HW_SECURITY_UI.REVIEW_ERC_BG) {
            pageHolder = new ReviewPageHolder(inflater,context,width,height,color_table_index);
        } else if (mHwSecurityUi == HW_SECURITY_UI.SIGN_MSG_ETH_BG||
                mHwSecurityUi == HW_SECURITY_UI.SIGN_MSG_BTC_BG||
                mHwSecurityUi == HW_SECURITY_UI.SIGN_MSG_LTC_BG||
                mHwSecurityUi == HW_SECURITY_UI.SIGN_MSG_BAT_BG){
            pageHolder = new VerifyMessagePageHolder(inflater,context,width,height);
        } else if (mHwSecurityUi == HW_SECURITY_UI.VERIFY_SOCIAL_CODE) {
            pageHolder = new VerifySocialPageHolder(inflater,context,width,height,name);
        } else if (mHwSecurityUi == HW_SECURITY_UI.ENTER_SOCIAL_RESTORE) {
            pageHolder = new SocialRestorePageHolder(inflater,context,width,height);
        } else if (mHwSecurityUi == HW_SECURITY_UI.CREATE_PIN_ERROR_MSG) {
            mView = (ViewGroup)inflater.inflate(
                    getResourceId("layout_create_pin_error_msg_for_exodus","layout"), null);
        } else if (mHwSecurityUi == HW_SECURITY_UI.CONFIRM_PIN_ERROR_MSG) {
            mView = (ViewGroup)inflater.inflate(
                    getResourceId("layout_confirm_pin_error_msg_for_exodus","layout"), null);
        } else if (mHwSecurityUi == HW_SECURITY_UI.RECOVERY_ERROR_MSG) {
            mView = (ViewGroup)inflater.inflate(
                    getResourceId("layout_recovery_error_msg_for_exodus","layout"), null);
        } else if (mHwSecurityUi == HW_SECURITY_UI.RECOVERY_INVALID_MSG) {
            mView = (ViewGroup)inflater.inflate(
                    getResourceId("layout_recovery_invalid_msg_for_exodus","layout"), null);
        } else if (mHwSecurityUi == HW_SECURITY_UI.REVIEW_ERROR_MSG) {
            mView = (ViewGroup)inflater.inflate(
                    getResourceId("layout_review_error_msg_for_exodus","layout"), null);
        } else if (mHwSecurityUi == HW_SECURITY_UI.BTC_CANCEL_BUTTON || mHwSecurityUi == HW_SECURITY_UI.BTC_SEND_BUTTON
                || mHwSecurityUi == HW_SECURITY_UI.ETH_CANCEL_BUTTON || mHwSecurityUi == HW_SECURITY_UI.ETH_SEND_BUTTON
                || mHwSecurityUi == HW_SECURITY_UI.LTC_CANCEL_BUTTON || mHwSecurityUi == HW_SECURITY_UI.LTC_SEND_BUTTON
                || mHwSecurityUi == HW_SECURITY_UI.BAT_CANCEL_BUTTON || mHwSecurityUi == HW_SECURITY_UI.BAT_SEND_BUTTON) {
            mView = (ViewGroup)inflater.inflate(
                    getResourceId("layout_transaction_button_for_exodus","layout"), null);
            mTransactionButton = mView.findViewById(getResourceId("review_button","id"));
        } else {
            mView = (ViewGroup)inflater.inflate(
                    getResourceId("activity_ui_template","layout"), null);
            mTitle = mView.findViewById(getResourceId("title","id"));
            mDescription = mView.findViewById(getResourceId("description","id"));
            mSubtitle = mView.findViewById(getResourceId("subtitle","id"));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mSubtitle.getLayoutParams());
            int mLeft = mContext.getResources().getDimensionPixelOffset(getResourceId("double_margin_l","dimen"));
            int mTop = mContext.getResources().getDimensionPixelOffset(getResourceId("subtitle_margin_top_HW","dimen"));
            int mRight = mContext.getResources().getDimensionPixelOffset(getResourceId("double_margin_l","dimen"));
            lp.setMargins(mLeft, mTop, mRight, 0);
            mSubtitle.setLayoutParams(lp);
            mProgBar1 = mView.findViewById(getResourceId("progressBar1","id"));
            mProgBar2 = mView.findViewById(getResourceId("progressBar2","id"));
            mProgBar3 = mView.findViewById(getResourceId("progressBar3","id"));
            mBackButton = mView.findViewById(getResourceId("backButton","id"));
            mDynamicContent = mView.findViewById(getResourceId("dynamic_content","id"));
        }
    }

    private int getResourceId(String resourceName, String type) {
        return mContext.getResources().getIdentifier(resourceName, type, mContext.getPackageName());
    }
    public ViewGroup getView() {

        View layout_view = null;

        switch (mHwSecurityUi) {
            case CREATE_PIN:
                setAllView(mContext.getResources().getString(getResourceId("create_your_pin_code_title","string")),
                        mContext.getResources().getString(getResourceId("pin_open_app_desc","string")),
                        null,
                        layout_view,
                        true);
                break;
            case CONFIRM_PIN:
                setAllView(mContext.getResources().getString(getResourceId("confirm_your_pin_code_title","string")),
                        mContext.getResources().getString(getResourceId("pin_open_app_desc","string")),
                        null,
                        layout_view,
                        false);
                break;
            case PIN_NOT_MATCH:
                setAllView(mContext.getResources().getString(getResourceId("create_your_pin_code_title","string")),
                        mContext.getResources().getString(getResourceId("pin_unlock_wallet_desc","string")),
                        null,
                        layout_view,
                        true);
                break;
            case RECOVERY_KEY_INTRODUCTION:
                layout_view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(getResourceId("layout_recovery_key_introduction_for_exodus","layout"), null);
                setAllView(mContext.getResources().getString(getResourceId("recovery_key_introduction_title","string")),
                        mContext.getResources().getString(getResourceId("recovery_key_introduction_description","string")),
                        null,
                        layout_view,
                        true);
                break;
            case WRITE_RECOVERY_KEY:
                layout_view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(getResourceId("layout_write_recovery_key_for_exodus","layout"), null);

                setAllView(mContext.getResources().getString(getResourceId("write_recovery_key_title","string")),
                        mContext.getResources().getString(getResourceId("write_recovery_key_description","string")),
                        null,
                        layout_view,
                        true);
                break;
            case VERIFY_RECOVERY_KEY_1:
                layout_view = getRecoveryLayoutWithInvisibleComponent();

                setAllView(String.format(mContext.getString(
                        getResourceId("text_recovery_verify_title","string")), 1),
                        mContext.getResources().getString(getResourceId("text_recovery_verify_description","string")),
                        null,
                        layout_view,
                        true);
                break;
            case VERIFY_RECOVERY_KEY_2:
                layout_view = getRecoveryLayoutWithInvisibleComponent();

                setAllView(String.format(mContext.getString(
                        getResourceId("text_recovery_verify_title","string")), 2),
                        mContext.getResources().getString(getResourceId("text_recovery_verify_description","string")),
                        null,
                        layout_view,
                        true);
                break;
            case VERIFY_RECOVERY_KEY_3:
                layout_view = getRecoveryLayoutWithInvisibleComponent();

                setAllView(String.format(mContext.getString(
                        getResourceId("text_recovery_verify_title","string")), 3),
                        mContext.getResources().getString(getResourceId("text_recovery_verify_description","string")),
                        null,
                        layout_view,
                        true);
                break;
            case ENTER_RECOVERY_KEY_1:
                layout_view = getRecoveryLayoutWithInvisibleComponent();

                setAllView(String.format(mContext.getString(
                        getResourceId("text_recovery_enter_title","string")), 1),
                        mContext.getResources().getString(getResourceId("text_recovery_enter_description","string")),
                        null,
                        layout_view,
                        true);
                break;
            case ENTER_RECOVERY_KEY_2:
                layout_view = getRecoveryLayoutWithInvisibleComponent();

                setAllView(String.format(mContext.getString(
                        getResourceId("text_recovery_enter_title","string")), 2),
                        mContext.getResources().getString(getResourceId("text_recovery_enter_description","string")),
                        null,
                        layout_view,
                        true);
                break;
            case ENTER_RECOVERY_KEY_3:
                layout_view = getRecoveryLayoutWithInvisibleComponent();

                setAllView(String.format(mContext.getString(
                        getResourceId("text_recovery_enter_title","string")), 3),
                        mContext.getResources().getString(getResourceId("text_recovery_enter_description","string")),
                        null,
                        layout_view,
                        true);
                break;
            case SECURITY_CHECK:
                setAllView(mContext.getResources().getString(getResourceId("enter_pin_to_unlock_your_wallet_title","string")),
                        null,
                        null,
                        layout_view,
                        true);
                break;
            case CONFIRM_PIN_REQUIRED:
                setAllView(mContext.getResources().getString(getResourceId("passcode_required","string")),
                        null,
                        null,
                        layout_view,
                        true);
                break;
            case CHANGE_PIN_A:
                setAllView(mContext.getResources().getString(getResourceId("enter_pin_change_pin_title","string")),
                        null,
                        mContext.getResources().getString(getResourceId("enter_pin_change_subtitle","string")),
                        layout_view,
                        true);
                break;
            case CHANGE_PIN_B:
                setAllView(mContext.getResources().getString(getResourceId("create_your_pin_code_title","string")),
                        mContext.getResources().getString(getResourceId("pin_open_app_desc","string")),
                        null,
                        layout_view,
                        true);
                break;
            case CHANGE_PIN_C:
                setAllView(mContext.getResources().getString(getResourceId("confirm_your_pin_code_title","string")),
                        mContext.getResources().getString(getResourceId("pin_open_app_desc","string")),
                        null,
                        layout_view,
                        false);
                break;
            case BACKUP_SECURITY_CHECK:
                setAllView(mContext.getResources().getString(getResourceId("enter_pin_backup_security_check_title","string")),
                        mContext.getResources().getString(getResourceId("enter_pin_backup_security_check_desc","string")),
                        mContext.getResources().getString(getResourceId("enter_pin_to_continue_title","string")),
                        layout_view,
                        true);
                break;
            case CONFIRM_LOGOUT:
                setAllView(mContext.getResources().getString(getResourceId("enter_pin_log_out_title","string")),
                        mContext.getResources().getString(getResourceId("enter_pin_log_out_desc","string")),
                        mContext.getResources().getString(getResourceId("enter_pin_log_out_subtitle","string")),
                        layout_view,
                        true);
                break;
            case REMOVE_CONTACT:
                setAllView(mContext.getResources().getString(getResourceId("confirm_remove_trusted_contact_title","string")),
                        null,
                        mContext.getResources().getString(getResourceId("enter_pin_to_remove_desc","string")),
                        layout_view,
                        true);
                break;
            case GENERATE_CODE:
                setAllView(mContext.getResources().getString(getResourceId("enter_pin_to_generate_verification_code_title","string")),
                        null,
                        null,
                        layout_view,
                        true);
                break;
            case SHOW_RECOVERY_KEY_1:
                setAllView(mContext.getResources().getString(getResourceId("enter_pin_show_recovery_key_title","string")),
                        null,
                        mContext.getResources().getString(getResourceId("enter_pin_show_subtitle","string")),
                        layout_view,
                        true);
                break;
            case SHOW_RECOVERY_KEY_2:
                layout_view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(getResourceId("layout_write_recovery_key_for_exodus","layout"), null);

                setAllView(mContext.getResources().getString(getResourceId("show_recovery_key_title","string")),
                        mContext.getResources().getString(getResourceId("write_recovery_key_description","string")),
                        null,
                        layout_view,
                        true);
                break;
            case RESET_WALLET_1:
                setAllView(mContext.getResources().getString(getResourceId("enter_pin_reset_wallet_title","string")),
                        null,
                        mContext.getResources().getString(getResourceId("enter_pin_reset_wallet_subtitle","string")),
                        layout_view,
                        true);
                break;
            case RESET_WALLET_2:
                mDialogTitle.setText(
                        getResourceId("reset_wallet_dialog_title","string"));
                mMessage.setText(
                        getResourceId("reset_wallet_dialog_description","string"));
                mPosButton.setText(
                        getResourceId("reset_wallet_confirm","string"));
                mNegButton.setText(
                        getResourceId("reset_wallet_cancel","string"));
                break;
            case REVIEW_ETH_BG:
            case REVIEW_BTC_BG:
            case REVIEW_LTC_BG:
            case REVIEW_BAT_BG:
            case REVIEW_ERC_BG:
            case REVIEW_ERC_DEFAULT_BG:
            case VERIFY_SOCIAL_CODE:
            case SIGN_MSG_ETH_BG:
            case SIGN_MSG_BTC_BG:
            case SIGN_MSG_LTC_BG:
            case SIGN_MSG_BAT_BG:
            case ENTER_SOCIAL_RESTORE:
                if(pageHolder !=null){
                    return pageHolder.getView(mHwSecurityUi);
                } else {
                    ZKMALog.i(TAG,"pageHolder is null!!!");
                }
                break;
            case BTC_CANCEL_BUTTON:
            case ETH_CANCEL_BUTTON:
            case LTC_CANCEL_BUTTON:
            case BAT_CANCEL_BUTTON:
                setTransactionBtnText ("cancel");
                setTransactionBtnBackground (mHwSecurityUi);
                break;
            case BTC_SEND_BUTTON:
            case ETH_SEND_BUTTON:
            case LTC_SEND_BUTTON:
            case BAT_SEND_BUTTON:
                setTransactionBtnText ("text_review_send_button");
                setTransactionBtnBackground (mHwSecurityUi);
                break;
            default:
                break;
        }

        if (mHwSecurityUi == HW_SECURITY_UI.RESET_WALLET_2) {
            createLinearLayout("parentPanel");
        } else if (mHwSecurityUi == HW_SECURITY_UI.NEXT_BUTTON) {
            createRelativeLayout("next_button_layout");
        } else if (mHwSecurityUi == HW_SECURITY_UI.CREATE_PIN_ERROR_MSG || mHwSecurityUi == HW_SECURITY_UI.CONFIRM_PIN_ERROR_MSG) {
            createTextViewLayout("pin_error_message");
        } else if (mHwSecurityUi == HW_SECURITY_UI.RECOVERY_ERROR_MSG) {
            createTextViewLayout("recovery_error_message");
        } else if (mHwSecurityUi == HW_SECURITY_UI.RECOVERY_INVALID_MSG) {
            createTextViewLayout("recovery_invalid_message");
        } else if (mHwSecurityUi == HW_SECURITY_UI.REVIEW_ERROR_MSG) {
            createTextViewLayout("review_error_message");
        } else if (mHwSecurityUi == HW_SECURITY_UI.BTC_CANCEL_BUTTON || mHwSecurityUi == HW_SECURITY_UI.BTC_SEND_BUTTON
                || mHwSecurityUi == HW_SECURITY_UI.ETH_CANCEL_BUTTON || mHwSecurityUi == HW_SECURITY_UI.ETH_SEND_BUTTON
                || mHwSecurityUi == HW_SECURITY_UI.LTC_CANCEL_BUTTON || mHwSecurityUi == HW_SECURITY_UI.LTC_SEND_BUTTON
                || mHwSecurityUi == HW_SECURITY_UI.BAT_CANCEL_BUTTON || mHwSecurityUi == HW_SECURITY_UI.BAT_SEND_BUTTON) {
            createRelativeLayout("button_layout");
        } else {
            mView.measure(View.MeasureSpec.makeMeasureSpec(mWidth, EXACTLY), View.MeasureSpec.makeMeasureSpec(mHeight, EXACTLY));
            mView.layout(0, 0, mView.getMeasuredWidth(), mView.getMeasuredHeight());
        }
        return mView;
    }

    public void setAllView(@Nullable String title,
                           @Nullable String description,
                           @Nullable String subtitle,
                           @Nullable View layout_view,
                           @Nullable boolean hasBackButton){

        //Set Title
        if(mTitle!=null){
            if(title!=null){
                setViewTitle(title);
            }
        }

        //Set Description
        if(mDescription!=null){
            if(description==null){
                setViewDescriptionInvisible();
            }
            else {
                setViewDescription(description);
            }
        }

        //Set SubTitle
        if(mSubtitle!=null){
            if(subtitle!=null){
                setViewSubtitleVisible();
                setViewSubtitle(subtitle);
            }
        }

        //Set ProgressBar
        if(mProgBar1!=null && mProgBar2!=null && mProgBar3!=null){
            setProgressInvisible();
        }

        //Set FrameLayout
        if(mDynamicContent!=null){
            if(layout_view!=null){
                setFrameLayout(layout_view);
            }
        }

        //Set SecureBar
        setSecureBarVisible();

        //Set backButton invisible
        if(mBackButton!=null) {
            if (!hasBackButton) {
                mBackButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setViewTitle(String title) {
        mTitle.setText(title);
    }

    private void setViewDescription (String description) {
        mDescription.setText(description);
    }

    private void setViewDescriptionInvisible () {
        mDescription.setVisibility(View.INVISIBLE);
    }

    private void setViewSubtitleVisible () {
        mSubtitle.setVisibility(View.VISIBLE);
    }

    private void setViewSubtitle(String subtitle) {
        mSubtitle.setText(subtitle);
    }

    private void setFrameLayout (View view) {
        mDynamicContent.removeAllViews();
        mDynamicContent.addView(view);
    }

    private void setProgressvalue (int progressvalue1, int progressvalue2, int progressvalue3) {
        mProgBar1.setProgress(progressvalue1);
        mProgBar2.setProgress(progressvalue2);
        mProgBar3.setProgress(progressvalue3);
    }

    private void setProgressInvisible() {
        mProgBar1.setVisibility(View.INVISIBLE);
        mProgBar2.setVisibility(View.INVISIBLE);
        mProgBar3.setVisibility(View.INVISIBLE);
    }

    private void setSecureBarVisible(){
        mView.findViewById(getResourceId("secure_layout","id") )
                .setVisibility(View.VISIBLE);
    }

    private View getRecoveryLayoutWithInvisibleComponent() {
        View mTempView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                getResourceId("layout_recovery_for_exodus","layout"), null);
//        setRecoveryLayoutComponentInvisible(mTempView);
        return mTempView;
    }

    private abstract class BasePageHolder{
        private ViewGroup main;
        Context context;
        int width;
        int height;

        BasePageHolder(Context context, int width, int height){
            this.context = context;
            this.width = width;
            this.height = height;
        }

        void setMainView(LayoutInflater inflater, String layout_name){

            main = (ViewGroup)inflater.inflate(
                    getResourceId(layout_name,"layout"), null);
        }

        View getContentViewById(String id_name){
            return main.findViewById(getResourceId(id_name,"id"));
        }

        ViewGroup returnMainViewBySize(int size_x, int size_y){
            main.measure(View.MeasureSpec.makeMeasureSpec(size_x, EXACTLY), View.MeasureSpec.makeMeasureSpec(size_y, EXACTLY));
            main.layout(0, 0, main.getMeasuredWidth(), main.getMeasuredHeight());
            return main;
        }

        ViewGroup getView(HW_SECURITY_UI hw_security_ui){
            return null;
        }
    }

    private class ReviewPageHolder extends BasePageHolder{
        private ImageView card_background;
        private View statusbar_background;
        private RelativeLayout toolbar_layout;
        private Button sendButton;
        private Button cancelButton;
        private int mColorTableIndex;

        ReviewPageHolder(LayoutInflater inflater, Context context, int width, int height, int color_table_index){
            super(context,width,height);
            mColorTableIndex = color_table_index;

            setMainView(inflater,"activity_review_for_exodus");

            card_background = (ImageView) getContentViewById("review_info_background");
            statusbar_background = (View) getContentViewById("review_statusbar_background");
            toolbar_layout = (RelativeLayout) getContentViewById("review_toolbar");
            sendButton = (Button) getContentViewById("review_send_button");
            cancelButton = (Button) getContentViewById("review_cancel_button");
        }

        @Override
        ViewGroup getView(HW_SECURITY_UI hw_security_ui)
        {
            Drawable review_background = null;
            Drawable review_button = getDrawable("review_button_background");
            int review_color_code;
            switch (hw_security_ui){
                case REVIEW_ETH_BG:
                    review_background = getDrawable("wallet_card_eth");
                    review_color_code = getColorCode("colorReviewETH");
                    break;
                case REVIEW_BTC_BG:
                    review_background = getDrawable("wallet_card_btc");
                    review_color_code = getColorCode("colorReviewBTC");
                    break;
                case REVIEW_LTC_BG:
                    review_background = getDrawable("wallet_card_ltc");
                    review_color_code = getColorCode("colorReviewLTC");
                    break;
                case REVIEW_BAT_BG:
                    review_background = getDrawable("wallet_card_bat");
                    review_color_code = getColorCode("colorReviewBAT");
                    break;
                case REVIEW_ERC_BG:
                    review_color_code = getColorCodeFromTable(mColorTableIndex);
                    review_background = getDrawable("wallet_card_default");
                    break;
                case REVIEW_ERC_DEFAULT_BG:
                    review_background = getDrawable("wallet_card_default");
                    review_color_code = getColorCode("colorReviewDefault");
                    break;
                default:
                    return null;
            }

            card_background.setImageDrawable(review_background);
            if(review_button!=null){
                review_button.setColorFilter(review_color_code, PorterDuff.Mode.MULTIPLY);
            }
            sendButton.setBackground(review_button);
            cancelButton.setBackground(review_button);
            statusbar_background.setBackgroundColor(review_color_code);
            toolbar_layout.setBackgroundColor(review_color_code);

            return returnMainViewBySize(width,height) ;
        }

        Drawable getDrawable(String drawableId){
            Drawable ret = null;
            if (Build.VERSION.SDK_INT>=21) {
                ret = mContext.getDrawable(
                        getResourceId(drawableId,"drawable"));
            } else {
                ret = mContext.getResources().getDrawable(
                        getResourceId(drawableId,"drawable"));
            }
            return ret;
        }

        Drawable getDrawableFromColor(int colorCode){
            Drawable ret = null;
            if (Build.VERSION.SDK_INT>=21) {
                ret = mContext.getDrawable(
                        getResourceId("wallet_card_white","drawable"));
            } else {
                ret = mContext.getResources().getDrawable(
                        getResourceId("wallet_card_white","drawable"));
            }
            if(ret!=null){
                ret.setColorFilter(colorCode, PorterDuff.Mode.MULTIPLY);
            }
            return ret;
        }

        int getColorCode(String colorCodeId){
            int ret = 0;
            if (Build.VERSION.SDK_INT>=23) {
                ret = mContext.getColor(
                        getResourceId(colorCodeId,"color"));
            } else {
                ret = mContext.getResources().getColor(
                        getResourceId(colorCodeId,"color"));
            }
            return ret;
        }

        int getColorCodeFromTable(int idx){
            int color_table[] = mContext.getResources().getIntArray(getResourceId("colorReviewERC20","array"));
            return color_table[idx];
        }
    }

    private class VerifyMessagePageHolder extends BasePageHolder{
        private ImageView card_background;
        private View statusbar_background;
        private RelativeLayout toolbar_layout;
        private RelativeLayout transaction_layout;
        private RelativeLayout message_layout;
        private Button sendButton;
        private Button cancelButton;
        private TextView actionbar_title;
        private Button editButton;
        private TextView simple_title;
        private TextView detail_title;
        private TextView addr_title;

        VerifyMessagePageHolder(LayoutInflater inflater, Context context, int width, int height){
            super(context,width,height);

            setMainView(inflater,"activity_review_for_exodus");

            card_background = (ImageView) getContentViewById("review_info_background");
            statusbar_background = (View) getContentViewById("review_statusbar_background");
            toolbar_layout = (RelativeLayout) getContentViewById("review_toolbar");
            transaction_layout = (RelativeLayout) getContentViewById("review_info_transaction_content_layout");
            message_layout = (RelativeLayout) getContentViewById("review_info_message_content_layout");
            sendButton = (Button) getContentViewById("review_send_button");
            cancelButton = (Button) getContentViewById("review_cancel_button");
            actionbar_title = (TextView) getContentViewById("review_title");
            editButton = (Button) getContentViewById("review_info_edit_button");
            simple_title = (TextView) getContentViewById("review_info_coin_title_simple_text");
            detail_title = (TextView) getContentViewById("review_info_coin_title_detail_text");
            addr_title = (TextView) getContentViewById("review_info_addr_title_text");
        }

        @Override
        ViewGroup getView(HW_SECURITY_UI hw_security_ui)
        {
            Drawable review_background = null;
            Drawable review_button = getDrawable("review_button_background");
            int review_color_code;
            switch (hw_security_ui){
                case SIGN_MSG_ETH_BG:
                    review_background = getDrawable("wallet_card_eth");
                    review_color_code = getColorCode("colorReviewETH");
                    break;
                case SIGN_MSG_BTC_BG:
                    review_background = getDrawable("wallet_card_btc");
                    review_color_code = getColorCode("colorReviewBTC");
                    break;
                case SIGN_MSG_LTC_BG:
                    review_background = getDrawable("wallet_card_ltc");
                    review_color_code = getColorCode("colorReviewLTC");
                    break;
                case SIGN_MSG_BAT_BG:
                    review_background = getDrawable("wallet_card_bat");
                    review_color_code = getColorCode("colorReviewBAT");
                    break;
                default:
                    return null;
            }

            card_background.setImageDrawable(review_background);
            if(review_button!=null){
                review_button.setColorFilter(review_color_code, PorterDuff.Mode.MULTIPLY);
            }
            sendButton.setBackground(review_button);
            cancelButton.setBackground(review_button);
            statusbar_background.setBackgroundColor(review_color_code);
            toolbar_layout.setBackgroundColor(review_color_code);
            transaction_layout.setVisibility(View.GONE);
            message_layout.setVisibility(View.VISIBLE);
            actionbar_title.setText(getResourceId("text_signmsg_actionbar_title","string"));
            editButton.setVisibility(View.INVISIBLE);
            simple_title.setVisibility(View.GONE);
            detail_title.setVisibility(View.GONE);
            addr_title.setVisibility(View.VISIBLE);
            cancelButton.setText(getResourceId("cancel","string"));
            sendButton.setText(getResourceId("text_signmsg_sign_button","string"));

            return returnMainViewBySize(width,height) ;
        }

        Drawable getDrawable(String drawableId){
            Drawable ret = null;
            if (Build.VERSION.SDK_INT>=21) {
                ret = mContext.getDrawable(
                        getResourceId(drawableId,"drawable"));
            } else {
                ret = mContext.getResources().getDrawable(
                        getResourceId(drawableId,"drawable"));
            }
            return ret;
        }

        int getColorCode(String colorCodeId){
            int ret = 0;
            if (Build.VERSION.SDK_INT>=23) {
                ret = mContext.getColor(
                        getResourceId(colorCodeId,"color"));
            } else {
                ret = mContext.getResources().getColor(
                        getResourceId(colorCodeId,"color"));
            }
            return ret;
        }
    }

    private class VerifySocialPageHolder extends BasePageHolder{
        private TextView titleView;
        private LinearLayout codeLayout;
        private TextView nameView;
        private TextView informationView;
        private TextView rejectView;
        private String name;

        VerifySocialPageHolder(LayoutInflater inflater, Context context, int width, int height, String name){
            super(context,width,height);

            this.name = name;
            setMainView(inflater,"activity_verification_code");

            titleView = (TextView) getContentViewById("verification_code_title");
            codeLayout = (LinearLayout) getContentViewById("code_linearlayout");
            nameView = (TextView) getContentViewById("verification_code_name");
            informationView = (TextView) getContentViewById("verification_code_information");
            rejectView = (TextView) getContentViewById("verification_code_reject");
            getContentViewById("secure_layout").setVisibility(View.VISIBLE);
        }

        @Override
        ViewGroup getView(HW_SECURITY_UI hw_security_ui)
        {
            if(name==null){
                return null;
            }

            String str_title = String.format(context.getString(getResourceId("verification_share_code_title","string")),name);

            titleView.setText(str_title);
            codeLayout.setVisibility(View.INVISIBLE);
            nameView.setText(name);
            informationView.setVisibility(View.INVISIBLE);

            SpannableString content = new SpannableString(context.getString(getResourceId("verification_share_code_reject","string")));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            rejectView.setText(content);

            return returnMainViewBySize(width,height) ;
        }


    }

    private class SocialRestorePageHolder extends BasePageHolder{

        SocialRestorePageHolder(LayoutInflater inflater, Context context, int width, int height){
            super(context,width,height);

            setMainView(inflater,"specific_social_restore_activity_for_exodus");
        }

        @Override
        ViewGroup getView(HW_SECURITY_UI hw_security_ui)
        {
            return returnMainViewBySize(width,height) ;
        }

    }

    private void setTransactionBtnText(String btnText) {
        if (mTransactionButton != null) {
            mTransactionButton.setText(
                    getResourceId(btnText, "string"));
        }
    }

    private void setTransactionBtnBackground(HW_SECURITY_UI mHwSecurityUi) {
        mTransactionBtnBackground = mContext.getResources().getDrawable(getResourceId("review_button_background","drawable"));
        int review_color_code = 0;
        switch (mHwSecurityUi){
            case BTC_CANCEL_BUTTON:
            case BTC_SEND_BUTTON:
                if (Build.VERSION.SDK_INT>=23) {
                    review_color_code = mContext.getColor(
                            getResourceId("colorReviewBTC","color"));
                } else {
                    review_color_code = mContext.getResources().getColor(
                            getResourceId("colorReviewBTC","color"));
                }
                break;
            case ETH_CANCEL_BUTTON:
            case ETH_SEND_BUTTON:
                if (Build.VERSION.SDK_INT>=23) {
                    review_color_code = mContext.getColor(
                            getResourceId("colorReviewETH","color"));
                } else {
                    review_color_code = mContext.getResources().getColor(
                            getResourceId("colorReviewETH","color"));
                }
                break;
            case LTC_CANCEL_BUTTON:
            case LTC_SEND_BUTTON:
                if (Build.VERSION.SDK_INT>=23) {
                    review_color_code = mContext.getColor(
                            getResourceId("colorReviewLTC","color"));
                } else {
                    review_color_code = mContext.getResources().getColor(
                            getResourceId("colorReviewLTC","color"));
                }
                break;
            case BAT_CANCEL_BUTTON:
            case BAT_SEND_BUTTON:
                if (Build.VERSION.SDK_INT>=23) {
                    review_color_code = mContext.getColor(
                            getResourceId("colorReviewBAT","color"));
                } else {
                    review_color_code = mContext.getResources().getColor(
                            getResourceId("colorReviewBAT","color"));
                }
                break;
            default:
                if (Build.VERSION.SDK_INT>=23) {
                    review_color_code = mContext.getColor(
                            getResourceId("colorReviewDefault","color"));
                } else {
                    review_color_code = mContext.getResources().getColor(
                            getResourceId("colorReviewDefault","color"));
                }
                break;
        }
        mTransactionBtnBackground.setColorFilter(review_color_code,PorterDuff.Mode.MULTIPLY);

        if (mTransactionBtnBackground != null) {
            mTransactionButton.setBackground(mTransactionBtnBackground);
        }
    }

    private void createLinearLayout(String name) {
        LinearLayout layout = mView.findViewById(
                getResourceId(name,"id"));
        mView.measure(View.MeasureSpec.makeMeasureSpec(mWidth, EXACTLY), View.MeasureSpec.makeMeasureSpec(mHeight, EXACTLY));
        mView.layout(0, 0, layout.getMeasuredWidth(), layout.getMeasuredHeight());
    }

    private void createRelativeLayout(String name) {
        RelativeLayout layout = mView.findViewById(
                getResourceId(name,"id"));
        mView.measure(View.MeasureSpec.makeMeasureSpec(mWidth, EXACTLY), View.MeasureSpec.makeMeasureSpec(mHeight, EXACTLY));
        mView.layout(0, 0, layout.getMeasuredWidth(), layout.getMeasuredHeight());
    }

    private void createTextViewLayout(String name) {
        TextView textView = mView.findViewById(
                getResourceId(name,"id"));
        mView.measure(View.MeasureSpec.makeMeasureSpec(mWidth, EXACTLY), View.MeasureSpec.makeMeasureSpec(mHeight, EXACTLY));
        mView.layout(0, 0, mView.getMeasuredWidth(), textView.getMeasuredHeight());
    }

}
