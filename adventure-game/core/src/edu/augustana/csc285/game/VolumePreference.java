package edu.augustana.csc285.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class VolumePreference {
	
	private static final String MUSIC_VOLUME = "volume";
	private static final String MUSIC_ENABLED = "music enabled";
	private static final String PREF_NAME = "gameVolumeControl";
	
	protected Preferences getPrefs(){
		return Gdx.app.getPreferences(PREF_NAME);
	}
	
	public boolean isMusicEnabled(){
		return getPrefs().getBoolean(MUSIC_ENABLED, true);
	}
	
	public void setMusicEnabled(boolean musicEnabled){
		getPrefs().putBoolean(MUSIC_ENABLED, musicEnabled);
		getPrefs().flush();
	}
	
	public float getMusicVolume(){
		return getPrefs().getFloat(MUSIC_VOLUME, 0.5f);
	}
	
	public void setMusicVolume(float volume){
		getPrefs().putFloat(MUSIC_VOLUME, volume);
		getPrefs().flush();
	}

}
