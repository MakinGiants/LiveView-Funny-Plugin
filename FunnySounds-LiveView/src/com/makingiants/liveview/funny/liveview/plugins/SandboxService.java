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

package com.makingiants.liveview.funny.liveview.plugins;

import java.io.IOException;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.makingiants.liveview.funny.R;
import com.makingiants.liveview.funny.model.SoundPlayer;
import com.makingiants.liveview.funny.model.sounds.CategoryManager;
import com.sonyericsson.extras.liveview.plugins.AbstractPluginService;
import com.sonyericsson.extras.liveview.plugins.PluginConstants;
import com.sonyericsson.extras.liveview.plugins.PluginUtils;

public class SandboxService extends AbstractPluginService {
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	// Used to update the LiveView screen
	private Handler handler;
	
	// Workers
	private CategoryManager soundManager;
	private SoundPlayer player;
	
	// Paint used in canvas to create bitmap for texts
	private Paint categoryPaint, soundPaint, numbersPaint;
	
	// Streams for background image in LiveView
	private Bitmap bitmapBackground;
	
	// Ads attribute
	// private Airpush airpush;
	
	// ****************************************************************
	// Service Overrides
	// ****************************************************************
	
	@Override
	public void onStart(final Intent intent, final int startId) {
		super.onStart(intent, startId);
		
		if (handler == null) {
			
			handler = new Handler();
			
			// Init paints
			categoryPaint = new Paint();
			categoryPaint.setColor(Color.WHITE);
			categoryPaint.setTextSize(20); // Text Size
			categoryPaint.setTypeface(Typeface.SANS_SERIF);
			categoryPaint.setShadowLayer(5.0f, 1.0f, 1.0f,
					Color.rgb(255, 230, 175));
			categoryPaint.setAntiAlias(true);
			categoryPaint.setTextAlign(Paint.Align.CENTER);
			
			soundPaint = new Paint();
			soundPaint.setColor(Color.WHITE);
			soundPaint.setTextSize(12); // Text Size
			soundPaint.setTypeface(Typeface.SANS_SERIF);
			soundPaint.setAntiAlias(true);
			soundPaint.setShadowLayer(1.0f, 1.0f, 1.0f,
					Color.rgb(255, 230, 175));
			soundPaint.setTextAlign(Paint.Align.CENTER);
			
			numbersPaint = new Paint();
			numbersPaint.setColor(Color.WHITE);
			numbersPaint.setTextSize(11); // Text Size
			numbersPaint.setTypeface(Typeface.SANS_SERIF);
			numbersPaint.setAntiAlias(true);
			numbersPaint.setShadowLayer(1.0f, 1.0f, 1.0f,
					Color.rgb(255, 230, 175));
			numbersPaint.setTextAlign(Paint.Align.CENTER);
			
			// Init bitmaps
			bitmapBackground = BitmapFactory.decodeStream(this.getResources()
					.openRawResource(R.drawable.background));
			
			// Init airpush ads
			// airpush = new Airpush(getApplicationContext());
			// airpush.startSmartWallAd(); //launch smart wall on App start
			// airpush.startPushNotification(false);
			// Airpush.enableSDK(getApplicationContext(), true);
			
			// Init other attributes
			player = new SoundPlayer(this);
			soundManager = new CategoryManager(this);
			
		}
		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		stopWork();
	}
	
	/**
	 * Plugin is sandbox.
	 */
	@Override
	protected boolean isSandboxPlugin() {
		return true;
	}
	
	/**
	 * Must be implemented. Starts plugin work, if any.
	 */
	@Override
	protected void startWork() {
		
		// Check if plugin is enabled.
		if (mSharedPreferences.getBoolean(
				PluginConstants.PREFERENCES_PLUGIN_ENABLED, false)) {
			showTextDelayed(soundManager.getActualCategoryNumber(),
					soundManager.getActualCategory(),
					soundManager.getActualSoundNumber(), soundManager
							.getActualSound().getName());
		}
	}
	
	/**
	 * Must be implemented. Stops plugin work, if any.
	 */
	@Override
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
	@Override
	protected void onServiceConnectedExtended(final ComponentName className,
			final IBinder service) {
		
	}
	
	/**
	 * Must be implemented.
	 * 
	 * PluginService has done disconnection from LiveView and service has been
	 * stopped.
	 * 
	 * Do any additional actions here.
	 */
	@Override
	protected void onServiceDisconnectedExtended(final ComponentName className) {
		
	}
	
	/**
	 * Must be implemented.
	 * 
	 * PluginService has checked if plugin has been enabled/disabled.
	 * 
	 * The shared preferences has been changed. Take actions needed.
	 */
	@Override
	protected void onSharedPreferenceChangedExtended(
			final SharedPreferences prefs, final String key) {
		
	}
	
	@Override
	protected void startPlugin() {
		// Log.d(PluginConstants.LOG_TAG, "startPlugin");
		startWork();
	}
	
	@Override
	protected void stopPlugin() {
		// Log.d(PluginConstants.LOG_TAG, "stopPlugin");
		stopWork();
	}
	
	// ****************************************************************
	// Events
	// ****************************************************************
	
	@Override
	protected void button(final String buttonType, final boolean doublepress,
			final boolean longpress) {
		// Log.d(PluginConstants.LOG_TAG, "button - type " + buttonType +
		// ", doublepress " + doublepress
		// + ", longpress " + longpress);
		
		if (mSharedPreferences.getBoolean(
				PluginConstants.PREFERENCES_PLUGIN_ENABLED, false)) {
			
			if (buttonType.equalsIgnoreCase(PluginConstants.BUTTON_UP)) {
				
				// Order of this calls are importand, first move and then check
				// the number for the new
				// sound or categorys
				String category = soundManager.movePreviousCategory();
				String sound = soundManager.getActualSound().getName();
				int actualCategory = soundManager.getActualCategoryNumber();
				int actualSound = soundManager.getActualSoundNumber();
				
				showText(actualCategory, category, actualSound, sound);
				
			}
			else if (buttonType.equalsIgnoreCase(PluginConstants.BUTTON_DOWN)) {
				
				// Order of this calls are importand, first move and then check
				// the number for the new
				// sound or categorys
				String category = soundManager.moveNextCategory();
				String sound = soundManager.getActualSound().getName();
				int actualCategory = soundManager.getActualCategoryNumber();
				int actualSound = soundManager.getActualSoundNumber();
				
				showText(actualCategory, category, actualSound, sound);
				
			}
			else if (buttonType.equalsIgnoreCase(PluginConstants.BUTTON_LEFT)) {
				
				// Order of this calls are importand, first move and then check
				// the number for the new
				// sound or categorys
				String category = soundManager.getActualCategory();
				String sound = soundManager.movePreviousSound();
				int actualCategory = soundManager.getActualCategoryNumber();
				int actualSound = soundManager.getActualSoundNumber();
				
				showText(actualCategory, category, actualSound, sound);
				
			}
			else if (buttonType.equalsIgnoreCase(PluginConstants.BUTTON_RIGHT)) {
				
				// Order of this calls are importand, first move and then check
				// the number for the new
				// sound or categorys
				String category = soundManager.getActualCategory();
				String sound = soundManager.moveNextSound();
				int actualCategory = soundManager.getActualCategoryNumber();
				int actualSound = soundManager.getActualSoundNumber();
				
				showText(actualCategory, category, actualSound, sound);
				
			}
			else if (buttonType.equalsIgnoreCase(PluginConstants.BUTTON_SELECT)) {
				try {
					player.play(soundManager.getActualSound().getFile());
				} catch (IOException e) {
					Log.e("SoundPluginService", "IOException 1", e);
				} catch (InterruptedException e) {
					Log.e("SoundPluginService", "InterruptedException 2", e);
				}
			}
		}
	}
	
	@Override
	protected void displayCaps(final int displayWidthPx,
			final int displayHeigthPx) {
		// Log.d(PluginConstants.LOG_TAG, "displayCaps - width " +
		// displayWidthPx+ ", height " + displayHeigthPx);
	}
	
	@Override
	protected void onUnregistered() throws RemoteException {
		// Log.d(PluginConstants.LOG_TAG, "onUnregistered");
		stopWork();
	}
	
	@Override
	protected void openInPhone(final String openInPhoneAction) {
		// Log.d(PluginConstants.LOG_TAG, "openInPhone: " + openInPhoneAction);
	}
	
	@Override
	protected void screenMode(final int mode) {
		
	}
	
	// ****************************************************************
	// GUI Changes
	// ****************************************************************
	
	private void showTextDelayed(final int categoryNumber,
			final String category, final int soundNumber, final String sound) {
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				// PluginUtils.sendTextBitmap(mLiveViewAdapter, mPluginId, text,
				// bitmapSizeX, fontSize);
				PluginUtils.sendScaledImage(
						mLiveViewAdapter,
						mPluginId,
						getBackgroundBitmapWithText(categoryNumber, category,
								soundNumber, sound));
			}
		}, 1000);
		
	}
	
	private void showText(final int categoryNumber, final String category,
			final int soundNumber, final String sound) {
		
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				
				// PluginUtils.sendTextBitmap(mLiveViewAdapter, mPluginId, text,
				// bitmapSizeX, fontSize);
				PluginUtils.sendScaledImage(
						mLiveViewAdapter,
						mPluginId,
						getBackgroundBitmapWithText(categoryNumber, category,
								soundNumber, sound));
				
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
	private Bitmap getBackgroundBitmapWithText(int categoryIndex,
			final String category, int soundIndex, final String sound) {
		
		Bitmap background = bitmapBackground.copy(Bitmap.Config.RGB_565, true);
		
		final Canvas canvas = new Canvas(background);
		
		canvas.drawText(
				String.format("%d/%d", categoryIndex,
						soundManager.getCategoriesLength()),
				PluginConstants.LIVEVIEW_SCREEN_X / 2, 30, numbersPaint);
		canvas.drawText(category, PluginConstants.LIVEVIEW_SCREEN_X / 2, 50,
				categoryPaint);
		canvas.drawText(
				String.format("%d/%d", soundIndex,
						soundManager.getSoundsLength()),
				PluginConstants.LIVEVIEW_SCREEN_X / 2, 90, numbersPaint);
		canvas.drawText(sound, PluginConstants.LIVEVIEW_SCREEN_X / 2, 105,
				soundPaint);
		
		return background;
	}
}