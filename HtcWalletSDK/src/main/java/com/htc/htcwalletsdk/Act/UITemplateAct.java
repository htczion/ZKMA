package com.htc.htcwalletsdk.Act;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.R;
import com.htc.htcwalletsdk.Utils.ZKMALog;


public class UITemplateAct extends BaseResultCallbackAct {
    private static final String TAG = "WalletSecure_UITemplateAct";

    private RelativeLayout mRelativeLayout;
    private LinearLayout mLinearLayout;
    private ScrollView mScrollView;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mSubtitle;
    private ProgressBar mProgBar1;
    private ProgressBar mProgBar2;
    private ProgressBar mProgBar3;
    private FrameLayout mDynamicContent;
    private FrameLayout mKeyboardView;
    private ImageView mImageView;
    private Button nextBtn;
    public ImageButton mBackButton;
    //private TextView mHint_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_template);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // For not opaque(transparent) color.
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );

        mRelativeLayout = (RelativeLayout) findViewById(R.id.main_layout);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view);
        mTitle = (TextView) findViewById(R.id.title);
        mDescription = (TextView) findViewById(R.id.description);
        mSubtitle = (TextView) findViewById(R.id.subtitle);
        mProgBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        mProgBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        mProgBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        mBackButton = (ImageButton) findViewById(R.id.backButton);
        mDynamicContent = (FrameLayout) findViewById(R.id.dynamic_content);
        mKeyboardView = (FrameLayout) findViewById(R.id.keyboard);

        unlimitTextline();

        // button: the view you want to enlarge hit area
//        final View parent = (View) mBackButton.getParent();
//        parent.post( new Runnable() {
//            public void run() {
//                final Rect rect = new Rect();
//                mBackButton.getHitRect(rect);
//                rect.top -= convertDpToPixel(12,UITemplateAct.this);    // increase top hit area
//                rect.left -= convertDpToPixel(12,UITemplateAct.this);   // increase left hit area
//                rect.bottom += convertDpToPixel(12,UITemplateAct.this); // increase bottom hit area
//                rect.right += convertDpToPixel(12,UITemplateAct.this);  // increase right hit area
//                parent.setTouchDelegate( new TouchDelegate( rect , mBackButton));
//            }
//        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                ZKMALog.d(TAG,"onClick++");
                onBackPressed();
                ZKMALog.d(TAG,"onClick--");
            }
        });
    }

    //finish activity and return result to WalletSecureUI
    int activityResultValue = RESULT.E_TEEKM_FAILURE;
    @SuppressLint("LongLogTag")
    public void finish(int result_id) {
        ZKMALog.d(TAG,"finish()");
        activityResultValue = result_id;
        finish();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onStop() {
        ZKMALog.d(TAG,"onStop()");
        super.onStop();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onDestroy() {
        ZKMALog.d(TAG,"onDestroy()");
        if(null!= sResultCallback){
            if (activityResultValue == RESULT.SUCCESS) {
                sResultCallback.makeSuccess();
                sResultCallback = null;
            }
            else {
                sResultCallback.makeFailure (activityResultValue);
                sResultCallback = null;
            }
        }
        super.onDestroy();
    }

    private void unlimitTextline() {
        //for SW only
        mTitle.setMaxLines(Integer.MAX_VALUE);
        mTitle.setMinLines(0);
        mDescription.setMaxLines(Integer.MAX_VALUE);
        mDescription.setMinLines(0);
    }

    public void setViewTitle(String title) {
        mTitle.setText(title);
    }

    public void setViewTitle(int resId) {
        mTitle.setText(resId);
    }

    public void setViewDescription (int resId) {
        mDescription.setText(resId);
    }

    public void setViewDescriptionVisible () {
        mDescription.setVisibility(View.VISIBLE);
    }

    public void setViewDescriptionInvisible () {
        mDescription.setVisibility(View.INVISIBLE);
    }

    public void setViewDescriptionCenter() {
        mDescription.setGravity(Gravity.CENTER);
    }

    public void setViewDescriptionStart() {
        mDescription.setGravity(Gravity.START);
    }

    public void setViewSubtitleVisible () {
        mSubtitle.setVisibility(View.VISIBLE);
    }

    public void setViewSubtitle(int resId) {
        mSubtitle.setText(resId);
    }

    public void setFrameLayout (View view) {
        mDynamicContent.removeAllViews();
        mDynamicContent.addView(view);
    }

    public void setKeyboardView (View view) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mScrollView.getLayoutParams();
        params.addRule(RelativeLayout.ABOVE, R.id.keyboard);
        mKeyboardView.removeAllViews();
        mKeyboardView.addView(view);
    }

    public void removeKeyboardView () {
        mKeyboardView.removeAllViews();
    }

    public boolean checkKeyboardView () {
        return (mKeyboardView.getChildCount()==1);
    }

    public void setProgressvalue (int progressvalue1, int progressvalue2, int progressvalue3) {
        mProgBar1.setProgress(progressvalue1);
        mProgBar2.setProgress(progressvalue2);
        mProgBar3.setProgress(progressvalue3);
    }

    public void setProgressVisible () {
        mProgBar1.setVisibility(View.VISIBLE);
        mProgBar2.setVisibility(View.VISIBLE);
        mProgBar3.setVisibility(View.VISIBLE);
    }

    public void setProgressInvisible () {
        mProgBar1.setVisibility(View.INVISIBLE);
        mProgBar2.setVisibility(View.INVISIBLE);
        mProgBar3.setVisibility(View.INVISIBLE);
    }

    public void setBackButtonInvisible () {
        mBackButton.setVisibility(View.INVISIBLE);
    }

    private float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public void setNavigationBarInvisible () {
        Window window = this.getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    public void setFrameLayoutBelowSubtitle () {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mDynamicContent.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, R.id.subtitle);
    }

    public void fullScrollBottom() {
        mScrollView.post(new Runnable() {
            public void run() {
                mScrollView.scrollTo(0, mScrollView.getBottom());
            }
        });
    }

    public interface Callback{
        void onBackButtonPressed();
    }
    UITemplateAct.Callback mCallback;

    public void setBackButtonOnClickListener(Callback callback) {
        mCallback = callback;
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZKMALog.d(TAG,"onClick++");
                mCallback.onBackButtonPressed();
                ZKMALog.d(TAG,"onClick--");
            }
        });
    }

    public void avoidTruncatedRKI() {
        mRelativeLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mImageView = findViewById(R.id.recoveryKey_image);
                nextBtn = findViewById(R.id.next_button);
                try {
                    int minimumMargin = UITemplateAct.this.getResources().getDimensionPixelOffset(R.dimen.common_margin_m1);
                    if ((nextBtn.getTop() - mImageView.getBottom()) < minimumMargin) {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) nextBtn.getLayoutParams();
                        params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        params.addRule(RelativeLayout.BELOW, R.id.recoveryKey_image);
                        int l = params.leftMargin;
                        int r = params.rightMargin;
                        int b = params.bottomMargin;
                        params.setMargins(l, minimumMargin, r, b);
                        nextBtn.setLayoutParams(params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void avoidTruncatedWRK() {
        mRelativeLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mLinearLayout = (LinearLayout) findViewById(R.id.LinearLayoutRoot);
                nextBtn = findViewById(R.id.next_button);
                try {
                    int minimumMargin = UITemplateAct.this.getResources().getDimensionPixelOffset(R.dimen.common_margin_m1);
                    if ((nextBtn.getTop() - mLinearLayout.getBottom()) < minimumMargin) {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) nextBtn.getLayoutParams();
                        params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        params.addRule(RelativeLayout.BELOW, R.id.LinearLayoutRoot);
                        int l = params.leftMargin;
                        int r = params.rightMargin;
                        int b = params.bottomMargin;
                        params.setMargins(l, minimumMargin, r, b);
                        nextBtn.setLayoutParams(params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
