/*
 * Copyright (c) 2010 Sony Ericsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.sonyericsson.extras.liveview.plugins.sandbox;

import java.io.InputStream;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import com.makingiants.liveview.funny.model.SoundManager;
import com.sonyericsson.extras.liveview.plugins.AbstractPluginService;
import com.sonyericsson.extras.liveview.plugins.PluginConstants;
import com.sonyericsson.extras.liveview.plugins.PluginUtils;
import com.sonyericsson.extras.liveview.plugins.R;

public class SoundPluginService extends AbstractPluginService {
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	// Used to update the LiveView screen
	private Handler handler;
	
	// Workers
	private SoundManager soundManager = null;
	
	// Paint used in canvas to create bitmap for texts
	private Paint categoryPaint;
	private Paint soundPaint;
	
	// ****************************************************************
	// Service Overrides
	// ****************************************************************
	
	public void onStart(final Intent intent, final int startId) {
		super.onStart(intent, startId);
		
		if (soundManager == null) {
			soundManager = new SoundManager(this);
		}
		
		if (handler == null) {
			handler = new Handler();
		}
		
		if (categoryPaint == null) {
			categoryPaint = new Paint();
			categoryPaint.setColor(Color.WHITE);
			categoryPaint.setTextSize(20); // Text Size
			categoryPaint.setTypeface(Typeface.SANS_SERIF);
			categoryPaint.setShadowLayer(5.0f, 1.0f, 1.0f, Color.rgb(255, 230, 175));
			categoryPaint.setAntiAlias(true);
			categoryPaint.setTextAlign(Align.CENTER);
			
		}
		if (soundPaint == null) {
			soundPaint = new Paint();
			soundPaint.setColor(Color.WHITE);
			soundPaint.setTextSize(12); // Text Size
			soundPaint.setTypeface(Typeface.SANS_SERIF);
			soundPaint.setAntiAlias(true);
			soundPaint.setShadowLayer(1.0f, 1.0f, 1.0f, Color.rgb(255, 230, 175));
			soundPaint.setTextAlign(Align.CENTER);
		}
	}
	
	public void onCreate() {
		super.onCreate();
		
	}
	
	public void onDestroy() {
		super.onDestroy();
		
		stopWork();
	}
	
	/**
	 * Plugin is sandbox.
	 */
	protected boolean isSandboxPlugin() {
		return true;
	}
	
	/**
	 * Must be implemented. Starts plugin work, if any.
	 */
	protected void startWork() {
		
		showTextDelayed(soundManager.getActualCategory(), soundManager.getActualSound());
	}
	
	/**
	 * Must be implemented. Stops plugin work, if any.
	 */
	protected void stopWork() {
	}
	
	/**
	 * Must be implemented.
	 * 
	 * PluginService has done connection and registering to the LiveView
	 * Service.
	 * 
	 * If needed, do additional actions here, e.g. starting any worker that is
	 * needed.
	 */
	protected void onServiceConnectedExtended(final ComponentName className, final IBinder service) {
		
	}
	
	/**
	 * Must be implemented.
	 * 
	 * PluginService has done disconnection from LiveView and service has been
	 * stopped.
	 * 
	 * Do any additional actions here.
	 */
	protected void onServiceDisconnectedExtended(final ComponentName className) {
		
	}
	
	/**
	 * Must be implemented.
	 * 
	 * PluginService has checked if plugin has been enabled/disabled.
	 * 
	 * The shared preferences has been changed. Take actions needed.
	 */
	protected void onSharedPreferenceChangedExtended(final SharedPreferences prefs, final String key) {
		
	}
	
	protected void startPlugin() {
		// Log.d(PluginConstants.LOG_TAG, "startPlugin");
		startWork();
	}
	
	protected void stopPlugin() {
		// Log.d(PluginConstants.LOG_TAG, "stopPlugin");
		stopWork();
	}
	
	// ****************************************************************
	// GUI Changes
	// ****************************************************************
	
	private void showTextDelayed(final String category, final String sound) {
		handler.postDelayed(new Runnable() {
			
			public void run() {
				
				//PluginUtils.sendTextBitmap(mLiveViewAdapter, mPluginId, text, bitmapSizeX, fontSize);
				PluginUtils.sendScaledImage(mLiveViewAdapter, mPluginId,
				        getBackgroundBitmapWithText(category, sound));
			}
		}, 1000);
		
	}
	
	private void showText(final String category, final String sound) {
		
		handler.post(new Runnable() {
			
			public void run() {
				
				//PluginUtils.sendTextBitmap(mLiveViewAdapter, mPluginId, text, bitmapSizeX, fontSize);
				PluginUtils.sendScaledImage(mLiveViewAdapter, mPluginId,
				        getBackgroundBitmapWithText(category, sound));
			}
		});
		
	}
	
	/**
	 * Create a bitmap with background image and strings drawed on in
	 * 
	 * @param category
	 * @param sound
	 * @return
	 */
	private Bitmap getBackgroundBitmapWithText(String category, String sound) {
		InputStream is = this.getResources().openRawResource(R.drawable.background);
		Bitmap background = BitmapFactory.decodeStream(is).copy(Bitmap.Config.RGB_565, true);
		
		Canvas canvas = new Canvas(background);
		
		canvas.drawText(category, (PluginConstants.LIVEVIEW_SCREEN_X - category.length()) / 2, 40,
		        categoryPaint);
		canvas.drawText(sound, (PluginConstants.LIVEVIEW_SCREEN_X - sound.length()) / 2, 100,
		        soundPaint);
		
		return background;
	}
	
	// ****************************************************************
	// Events
	// ****************************************************************
	
	protected void button(final String buttonType, final boolean doublepress, final boolean longpress) {
		//Log.d(PluginConstants.LOG_TAG, "button - type " + buttonType + ", doublepress " + doublepress
		//        + ", longpress " + longpress);
		
		if (buttonType.equalsIgnoreCase(PluginConstants.BUTTON_UP)) {
			//showText(soundManager.jumpPastCategory(), 220, 65);
			showText(soundManager.jumpPastCategory(), soundManager.getActualSound());
		} else if (buttonType.equalsIgnoreCase(PluginConstants.BUTTON_DOWN)) {
			showText(soundManager.jumpNextCategory(), soundManager.getActualSound());
		} else if (buttonType.equalsIgnoreCase(PluginConstants.BUTTON_LEFT)) {
			showText(soundManager.getActualCategory(), soundManager.jumpPastSound());
		} else if (buttonType.equalsIgnoreCase(PluginConstants.BUTTON_RIGHT)) {
			showText(soundManager.getActualCategory(), soundManager.jumpNextSound());
		} else if (buttonType.equalsIgnoreCase(PluginConstants.BUTTON_SELECT)) {
			
			// Play the sound
			soundManager.playSound();
		}
		
	}
	
	protected void displayCaps(final int displayWidthPx, final int displayHeigthPx) {
		// Log.d(PluginConstants.LOG_TAG, "displayCaps - width " +
		// displayWidthPx+ ", height " + displayHeigthPx);
	}
	
	protected void onUnregistered() throws RemoteException {
		// Log.d(PluginConstants.LOG_TAG, "onUnregistered");
		stopWork();
	}
	
	protected void openInPhone(final String openInPhoneAction) {
		// Log.d(PluginConstants.LOG_TAG, "openInPhone: " + openInPhoneAction);
	}
	
	protected void screenMode(final int mode) {
		
		if (mode != PluginConstants.LIVE_SCREEN_MODE_ON) {
			startWork();
		}
	}
	
}