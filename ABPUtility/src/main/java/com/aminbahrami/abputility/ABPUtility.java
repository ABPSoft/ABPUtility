package com.aminbahrami.abputility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;

public class ABPUtility
{
	public static int darkenColor(int color,float factor)
	{
		int a=Color.alpha(color);
		int r=Math.round(Color.red(color)*factor);
		int g=Math.round(Color.green(color)*factor);
		int b=Math.round(Color.blue(color)*factor);
		return Color.argb(a,
				Math.min(r,255),
				Math.min(g,255),
				Math.min(b,255));
	}
	
	public static String htmlToText(String input)
	{
		Spanned text=null;
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		{
			text=Html.fromHtml(input,Html.FROM_HTML_MODE_COMPACT);
		}
		else
		{
			text=Html.fromHtml(input);
		}
		
		return text.toString();
	}
	
	public static void changeStatusBarBackground(Activity activity,int color)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			Window window=activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(color);
		}
	}
	
	public static void hideStatusBar(Activity activity)
	{
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	public static void hideNavigationBar(Activity activity)
	{
		View decorView=activity.getWindow().getDecorView();
		int uiOptions=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN;
		
		decorView.setSystemUiVisibility(uiOptions);
	}
	
	public static Bitmap radiusImage(Bitmap bitmap,int radius)
	{
		Bitmap imageRounded=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
		Canvas canvas=new Canvas(imageRounded);
		Paint mPaint=new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setShader(new BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP));
		canvas.drawRoundRect((new RectF(0,0,bitmap.getWidth(),bitmap.getHeight())),radius,radius,mPaint);
		
		return imageRounded;
	}
	
	public static boolean saveBitmap(Bitmap bitmap,File file)
	{
		try
		{
			FileOutputStream fOut=new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG,85,fOut);
			fOut.flush();
			fOut.close();
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	public static Bitmap screenshot(View view)
	{
		View screenView=view.getRootView();
		screenView.setDrawingCacheEnabled(true);
		Bitmap bitmap=Bitmap.createBitmap(screenView.getDrawingCache());
		screenView.setDrawingCacheEnabled(false);
		return bitmap;
	}
	
	public static int getAppVersionCode(Context context,String packageName)
	{
		try
		{
			PackageInfo packageInfo=context.getPackageManager().getPackageInfo(packageName,0);
			
			return packageInfo.versionCode;
		}
		catch(PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
			
			return 0;
		}
	}
	
	public static String getAppVersionName(Context context,String packageName)
	{
		try
		{
			PackageInfo packageInfo=context.getPackageManager().getPackageInfo(packageName,0);
			
			return packageInfo.versionName;
		}
		catch(PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
			
			return "0";
		}
	}
	
	public static String numberFormat(int number)
	{
		NumberFormat numberFormat=NumberFormat.getNumberInstance();
		
		return numberFormat.format(number);
	}
	
	public static String numberFormat(long number)
	{
		NumberFormat numberFormat=NumberFormat.getNumberInstance();
		
		return numberFormat.format(number);
	}
	
	public static String numberFormat(double number)
	{
		NumberFormat numberFormat=NumberFormat.getNumberInstance();
		
		return numberFormat.format(number);
	}
	
	public static String numberFormat(float number)
	{
		NumberFormat numberFormat=NumberFormat.getNumberInstance();
		
		return numberFormat.format(number);
	}
	
	public static int dpToPx(Context context,int dp)
	{
		DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
		return Math.round(dp*(displayMetrics.xdpi/DisplayMetrics.DENSITY_DEFAULT));
	}
	
	public static int pxToDp(Context context,int px)
	{
		DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
		return Math.round(px/(displayMetrics.xdpi/DisplayMetrics.DENSITY_DEFAULT));
	}
}
