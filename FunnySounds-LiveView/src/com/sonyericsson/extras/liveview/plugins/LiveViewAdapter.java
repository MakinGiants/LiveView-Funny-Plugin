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

package com.sonyericsson.extras.liveview.plugins;

import android.graphics.Bitmap;
import android.os.RemoteException;
import android.util.Log;

import com.sonyericsson.extras.liveview.IPluginServiceCallbackV1;
import com.sonyericsson.extras.liveview.IPluginServiceV1;

public class LiveViewAdapter {
	
	// Reference to LiveView application stub
	private IPluginServiceV1 mLiveView = null;
	
	public LiveViewAdapter(final IPluginServiceV1 liveView) {
		this.mLiveView = liveView;
	}
	
	public int installPlugin(final IPluginServiceCallbackV1 callback,
			final String menuIcon, final String pluginName,
			final boolean sandbox, final String packageName,
			final String launchIntent) throws RemoteException {
		int pluginId = 0;
		if (mLiveView != null) {
			// Register
			pluginId = mLiveView.register(callback, menuIcon, pluginName,
					sandbox, packageName);
			Log.d(PluginConstants.LOG_TAG, "Plugin registered. mPluginId: "
					+ pluginId + " isSandbox? " + sandbox);
			
			// Notify installation
			final int installedOk = mLiveView.notifyInstalled(launchIntent,
					pluginName);
			Log.d(PluginConstants.LOG_TAG, "Plugin installation notified.");
			
			if (installedOk >= 0) {
				Log.d(PluginConstants.LOG_TAG, "Registry success!");
			}
			else if (installedOk == -1) {
				Log.d(PluginConstants.LOG_TAG, "Already registered!");
			}
		}
		
		return pluginId;
	}
	
	public int register(final IPluginServiceCallbackV1 cb,
			final String imageMenu, final String pluginName,
			final boolean selectableMenu, final String packageName) {
		int result = 0;
		try {
			result = mLiveView.register(cb, imageMenu, pluginName,
					selectableMenu, packageName);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
		
		return result;
	}
	
	public void unregister(final int id, final IPluginServiceCallbackV1 cb) {
		try {
			mLiveView.unregister(id, cb);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public void sendAnnounce(final int id, final String imageAnnounce,
			final String header, final String body, final long timestamp,
			final String openInPhoneAction) {
		try {
			mLiveView.sendAnnounce(id, imageAnnounce, header, body, timestamp,
					openInPhoneAction);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public void sendImage(final int id, final int x, final int y,
			final String image) {
		try {
			mLiveView.sendImage(id, x, y, image);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public void sendImageAsBitmap(final int id, final int x, final int y,
			final Bitmap bitmapData) {
		try {
			mLiveView.sendImageAsBitmap(id, x, y, bitmapData);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public void clearDisplay(final int id) {
		try {
			mLiveView.clearDisplay(id);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public int notifyInstalled(final String launcherIntent,
			final String pluginName) {
		int result = 0;
		try {
			result = mLiveView.notifyInstalled(launcherIntent, pluginName);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
		
		return result;
	}
	
	public void ledControl(final int id, final int rgb565, final int delayTime,
			final int onTime) {
		try {
			mLiveView.ledControl(id, rgb565, delayTime, onTime);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public void vibrateControl(final int id, final int delayTime,
			final int onTime) {
		try {
			mLiveView.vibrateControl(id, delayTime, onTime);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public void sendImageAsBitmapByteArray(final int id, final int x,
			final int y, final byte[] bitmapByteArray) {
		try {
			mLiveView.sendImageAsBitmapByteArray(id, x, y, bitmapByteArray);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public void screenOff(final int id) {
		try {
			mLiveView.screenOff(id);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public void screenDim(final int id) {
		try {
			mLiveView.screenDim(id);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public void screenOn(final int id) {
		try {
			mLiveView.screenOn(id);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
	public void screenOnAuto(final int id) {
		try {
			mLiveView.screenOnAuto(id);
		} catch (final RemoteException re) {
			Log.e(PluginConstants.LOG_TAG, "Unexpected remote exception.");
		}
	}
	
}
