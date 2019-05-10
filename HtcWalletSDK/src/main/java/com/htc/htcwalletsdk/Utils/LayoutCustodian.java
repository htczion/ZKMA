package com.htc.htcwalletsdk.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.htc.htcwalletsdk.CONSTANT;

import java.io.File;
import java.io.FileOutputStream;

import static android.view.View.MeasureSpec.EXACTLY;

/**
 * Created by shihshi-mac on 2018/10/2.
 */
public class LayoutCustodian
{
    static final String TAG = "LayoutCustodian";

    public static boolean saveLayout(Context context, int layoutRes, String filename)
    {
        boolean bRet = false;
        try {
            Point size = new Point();
            ((Activity) context).getWindowManager().getDefaultDisplay().getRealSize(size);
            int width = size.x;
            int height = size.y;

//           final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//            int width = metrics.widthPixels;
///           int height = metrics.heightPixels;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup viewGroup = (ViewGroup)inflater.inflate(layoutRes, null);

            viewGroup.measure(View.MeasureSpec.makeMeasureSpec(width, EXACTLY), View.MeasureSpec.makeMeasureSpec(height, EXACTLY));
            viewGroup.layout(0, 0, viewGroup.getMeasuredWidth(), viewGroup.getMeasuredHeight());

            Bitmap bitmap = LayoutCustodian.viewToBitmap(viewGroup);

            try {

                File file = new File(context.getFilesDir(),"tmp");
                if(!file.exists()){
                    file.mkdir();
                }

                FileOutputStream output = new FileOutputStream(file.getPath() + File.separator + filename);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.close();
                bRet = true;
            } catch (Exception e) {
                e.printStackTrace();
                bRet = false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bRet = false;
        }
        return bRet;
    }


    public static boolean saveLayout(Context context, CONSTANT.HW_SECURITY_UI hwSecurityUi, String filename, int color_table_index)
    {
        boolean bRet = false;
        try {
            Point size = new Point();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            display.getRealSize(size);
            int width = size.x;
            int height = size.y;
            ZKMALog.d(TAG,String.valueOf(width)+","+String.valueOf(height));

            Bitmap bitmap = LayoutCustodian.viewToBitmap(new HWLayoutHelper(context, hwSecurityUi, width, height, color_table_index).getView());

            try {

                File file = new File(context.getFilesDir(),"tmp");
                if(!file.exists()){
                    file.mkdir();
                }

                FileOutputStream output = new FileOutputStream(file.getPath() + File.separator + filename);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.close();
                ZKMALog.d(TAG,"Create image:"+file.getPath() + File.separator + filename);
                bRet = true;
            } catch (Exception e) {
                e.printStackTrace();
                bRet = false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bRet = false;
        }
        return bRet;
    }

    public static boolean saveLayout(Context context, CONSTANT.HW_SECURITY_UI hwSecurityUi, String filename, String name)
    {
        boolean bRet = false;
        try {
            Point size = new Point();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            display.getRealSize(size);
            int width = size.x;
            int height = size.y;
            ZKMALog.d(TAG,String.valueOf(width)+","+String.valueOf(height));

            Bitmap bitmap = LayoutCustodian.viewToBitmap(new HWLayoutHelper(context, hwSecurityUi, width, height, name).getView());

            try {

                File file = new File(context.getFilesDir(),"tmp");
                if(!file.exists()){
                    file.mkdir();
                }

                FileOutputStream output = new FileOutputStream(file.getPath() + File.separator + filename);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.close();
                ZKMALog.d(TAG,"Create image:"+file.getPath() + File.separator + filename);
                bRet = true;
            } catch (Exception e) {
                e.printStackTrace();
                bRet = false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bRet = false;
        }
        return bRet;
    }

    public static boolean saveLayout(Context context, CONSTANT.HW_SECURITY_UI hwSecurityUi, String filename)
    {
        boolean bRet = false;
        try {
            Point size = new Point();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            display.getRealSize(size);
            int width = size.x;
            int height = size.y;
            ZKMALog.d(TAG,String.valueOf(width)+","+String.valueOf(height));

            Bitmap bitmap = LayoutCustodian.viewToBitmap(new HWLayoutHelper(context, hwSecurityUi, width, height).getView());

            try {

                File file = new File(context.getFilesDir(),"tmp");
                if(!file.exists()){
                    file.mkdir();
                }

                FileOutputStream output = new FileOutputStream(file.getPath() + File.separator + filename);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.close();
                ZKMALog.d(TAG,"Create image:"+file.getPath() + File.separator + filename);
                bRet = true;
            } catch (Exception e) {
                e.printStackTrace();
                bRet = false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bRet = false;
        }
        return bRet;
    }

    public static boolean removeLayout(Context context, CONSTANT.HW_SECURITY_UI hwSecurityUi, String filename)
    {
        boolean bRet = false;
        try {
            if(hwSecurityUi != CONSTANT.HW_SECURITY_UI.VERIFY_SOCIAL_CODE){
                return bRet;
            }

            try {
                File file = new File(context.getFilesDir(),"tmp");
                ZKMALog.d(TAG,"file:"+file.getPath());
                if(!file.exists()){
                    return bRet;
                }

                File png_file = new File(file.getPath() + File.separator + filename);
                ZKMALog.d(TAG,"png_file:"+png_file.getPath());

                if(png_file.delete()){
                    ZKMALog.d(TAG,png_file.getName() + " is deleted!");
                    bRet = true;
                }else{
                    ZKMALog.d(TAG,"Delete operation is failed.");
                    bRet = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                bRet = false;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bRet = false;
        }
        return bRet;
    }

    private static Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
