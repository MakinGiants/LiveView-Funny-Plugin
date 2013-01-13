package com.makingiants.liveview.funny.model.sounds;

import java.util.ArrayList;

/**
 * Sounds are organized by categories.
 * Each category have a list of sounds. 
 *
 */
public class Category {
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	// Sounds
	private ArrayList<Sound> sounds;
	private int actualSound;
	
	// Category
	private String name;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public Category(String name) {
		this.sounds = new ArrayList<Sound>();
		this.actualSound = 0;
		this.name = name;
	}
	
	// ****************************************************************
	// Accessor Methods
	// ****************************************************************
	
	public void addSound(Sound sound) {
		sounds.add(sound);
	}
	
	public Sound getActualSound() {
		return sounds.get(actualSound);
	}
	
	/**
	 * Move to next sound and return the new actual sound
	 * @return
	 */
	public Sound moveToNextSound() {
		actualSound++;
		if (actualSound >= sounds.size()) {
			actualSound = 0;
		}
		
		return sounds.get(actualSound);
	}
	
	public Sound moveToPreviousSound() {
		actualSound--;
		if (actualSound < 0) {
			actualSound = sounds.size() - 1;
		}
		
		return sounds.get(actualSound);
	}
	
	public String getName() {
		return name;
	}
	
	public int getActualSoundNumber() {
		return actualSound + 1;// To avoid 0 value
	}
	
	public int getSoundsLength() {
		return sounds.size();
	}
}
