package com.goody.base.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BaseUtils {
    private final static String TAG = "Utils---";

    public static void startActivity(Context context, Class<?> clz) {
        context.startActivity(new Intent(context, clz));
    }

    public static void startActivity(Context context, Class<?> clz,long time) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                context.startActivity(new Intent(context, clz));
            }
        },time);
    }

    public static void startActivity(Context context, Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, clz);
        if (bundle != null) intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /*???????????????Fragment*/
    public static FragmentTransaction switchFragment(AppCompatActivity activity, int containerViewId, Fragment targetFragment) {
        FragmentManager fm = activity.getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        FragmentTransaction transaction = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (!targetFragment.equals(fragment) && !fragment.isHidden())
                transaction.hide(fragment);
        }
        //??????????????????????????????
        if (targetFragment.isAdded()) {
            //?????????????????? ???????????????????????????
            transaction.show(targetFragment);
        } else {
            //??????????????????
            transaction.add(containerViewId, targetFragment, targetFragment.getClass().getName());
        }
        return transaction;
    }

    /*???????????????Fragment*/
    public static FragmentTransaction switchFragment(FragmentActivity activity, int containerViewId, Fragment targetFragment) {
        FragmentManager fm = activity.getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        FragmentTransaction transaction = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (!targetFragment.equals(fragment) && !fragment.isHidden())
                transaction.hide(fragment);
        }
        //??????????????????????????????
        if (targetFragment.isAdded()) {
            //?????????????????? ???????????????????????????
            transaction.show(targetFragment);
        } else {
            //??????????????????
            transaction.add(containerViewId, targetFragment, targetFragment.getClass().getName());
        }
        return transaction;
    }

    public static AlertDialog.Builder getNormalDialog(Context context, String title, String message, String pButName, DialogInterface.OnClickListener pOnClickListener, String nButName, DialogInterface.OnClickListener nOnClickListener) {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setTitle(title);
        normalDialog.setMessage(message);
        normalDialog.setPositiveButton(pButName, pOnClickListener);
        normalDialog.setNegativeButton(nButName, nOnClickListener);
        return normalDialog;
    }

    public static void showNormalDialog(Context context, String title, String message, String pButName, DialogInterface.OnClickListener pOnClickListener, String nButName, DialogInterface.OnClickListener nOnClickListener) {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setTitle(title);
        normalDialog.setMessage(message);
        normalDialog.setPositiveButton(pButName, pOnClickListener);
        normalDialog.setNegativeButton(nButName, nOnClickListener);
        normalDialog.show();
    }

    /*???????????????*/
    public static void winTextColorWhite(Context context, String bgColor) {
        ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        ((Activity) context).getWindow().setStatusBarColor(Color.parseColor(bgColor));
    }

    /*???????????????*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void winTextColorBlack(Context context, String bgColor) {
        ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ((Activity) context).getWindow().setStatusBarColor(Color.parseColor(bgColor));
    }

    /*??????*/
    public static void winXY(Context context) {
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        ((Activity) context).getWindow().setAttributes(params);
        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /*??????????????????*/
    public static int[] getScreenXY(Context context) {
        int[] xY = new int[2];
        xY[0] = context.getResources().getDisplayMetrics().widthPixels;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        xY[1] = displayMetrics.heightPixels;
        return xY;
    }

    /*float??????????????????*/
    public static String floatFormat(float value, int size) {
        BigDecimal text = new BigDecimal(value);
        return text.setScale(size, RoundingMode.HALF_DOWN).toString();
    }

    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    public static float sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }

    private static ProgressDialog progressDialog;

    /**
     * ???????????????????????????????????????????????????
     */
    public synchronized static void showProgressDialog(Context context, String message) {
        if (progressDialog != null) closeProgressDialog();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    /**
     * ???????????????????????????????????????????????????
     */
    public synchronized static void showProgressHorizontal(Context context, String message) {
        if (progressDialog != null) closeProgressDialog();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
    }

    public static void setProgressProgress(int progress) {
        if (progressDialog != null) {
            progressDialog.setProgress(progress);
        }
    }

    /**
     * ?????????????????????
     */
    public static void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /*??????????????????*/
    private static long time = 0;

    public static boolean isOnClick(int ms) {
        boolean is = System.currentTimeMillis() - time > ms;
        if (is) {
            time = System.currentTimeMillis();
            Log.i(TAG, "???????????? ms:" + ms);
        } else {
            Log.i(TAG, "????????????");
        }
        return is;
    }

    public static String getFileSuffix(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot);
            }
        }
        return filename;
    }

    /**
     * ??????????????????
     */
    public static String formetFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (size == 0) return "0B";
        if (size < 1024) {
            return df.format((double) size) + "B";
        } else if (size < 1048576) {
            return df.format((double) size / 1024) + "KB";
        } else if (size < 1073741824) {
            return df.format((double) size / 1048576) + "MB";
        } else {
            return df.format((double) size / 1073741824) + "GB";
        }
    }

    //?????? LinearLayoutManager
    public static RecyclerView.LayoutManager getLinearLayoutManager(Context context, int orientation) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(orientation);
        return linearLayoutManager;
    }

    //?????? StaggeredGridLayoutManager
    public static RecyclerView.LayoutManager getStaggeredGridLayoutManager(int spanCount,int orientation) {
        return new StaggeredGridLayoutManager(spanCount, orientation);
    }

    //?????? GridLayoutManager
    public static RecyclerView.LayoutManager getGridLayoutManager(Context context,int spanCount) {
        return new GridLayoutManager(context, spanCount);
    }

    public static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }
}
