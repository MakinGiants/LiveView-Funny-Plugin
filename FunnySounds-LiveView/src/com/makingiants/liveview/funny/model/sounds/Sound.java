package com.makingiants.liveview.funny.model.sounds;

/**
 * Represent a file sound that can be played
 */
public class Sound {
	
	// ****************************************************************
	// Constants
	// ****************************************************************
	
	/**
	 * Folder in assets where the sounds are
	 */
	private static final String FOLDER = "sounds/";
	
	// ****************************************************************
	// Attributes
	// ****************************************************************
	
	private String file;
	private String name;
	
	// ****************************************************************
	// Constructor
	// ****************************************************************
	
	public Sound(final String name, final String file) {
		this.name = name;
		this.file = FOLDER + file;
	}
	
	// ****************************************************************
	// Accessor Methods
	// ****************************************************************
	
	public String getFile() {
		return file;
	}
	
	public void setFile(final String file) {
		this.file = FOLDER + file;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
}
