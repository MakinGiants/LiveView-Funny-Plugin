package com.makingiants.liveview.funny.model.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.makingiants.liveview.funny.model.sounds.Sound;
import com.makingiants.liveview.funny.model.sounds.Category;

import android.content.Context;
import android.util.Log;

public class CategoryDAO {
	
	/**
	 * This file contain the categories and sounds for each one 
	 */
	private final static String SOUNDS_FILE = "sounds.xml";
	
	/**
	 * Get the List of categories 
	 * 
	 * @param context actual context of the app
	 * @return
	 */
	public static ArrayList<Category> getCategories(Context context) {
		
		ArrayList<Category> categories = new ArrayList<Category>();
		Category categoryTemp = null;
		
		try {
			
			final boolean done = false;
			
			final XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			final XmlPullParser parser = factory.newPullParser();
			
			final InputSource source = new InputSource(context.getAssets().open(SOUNDS_FILE));
			parser.setInput(source.getByteStream(), source.getEncoding());
			
			int eventType = parser.getEventType();
			String name = "";
			
			while (eventType != XmlPullParser.END_DOCUMENT && !done) {
				
				switch (eventType) {
				
					case XmlPullParser.START_TAG:
						
						name = parser.getName();
						if (name.equals("category")) {
							categoryTemp = new Category(parser.getAttributeValue(0));
						} else if (name.equals("sound")) {
							
							categoryTemp.addSound(new Sound(parser.getAttributeValue(0), parser
							        .getAttributeValue(1)));
						}
						
						break;
					case XmlPullParser.END_TAG:
						name = parser.getName();
						if (name.equals("category")) {
							categories.add(categoryTemp);
						}
				}
				eventType = parser.next();
			}
		} catch (final FileNotFoundException e) {
			Log.e("FileNotFoundException", e.getMessage());
		} catch (final IOException e) {
			Log.e("IOException en Favorito", e.getMessage());
		} catch (final Exception e) {
			Log.e("Exception en Favorito", e.getMessage());
		}
		
		return categories;
	}
	
}
