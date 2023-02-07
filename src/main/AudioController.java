//Use to control the game audio. Including main menu music, background music and the sound when you complete the game.
package main;

import javafx.scene.media.AudioClip;

public class AudioController {
	private AudioClip backGroundMusic;
	private AudioClip inGameSound;
	private AudioClip levelComplete;

	private Thread audioThread;
	private Thread gameThread;
	private Thread completeThread;

	public AudioController() {
		// Load the audio path
		String audioPath = ClassLoader.getSystemResource("menuLoop.wav").toString();
		String inGamePath = ClassLoader.getSystemResource("inGameSound.wav").toString();
		String completePath = ClassLoader.getSystemResource("levelComplete.mp3").toString();

		// Setup the audio player
		setBackGroundMusic(new AudioClip(audioPath));
		setInGameSound(new AudioClip(inGamePath));
		setLevelComplete(new AudioClip(completePath));
	}

	public void playBackGroundMusic() {
		audioThread = new Thread(() -> {
			getBackGroundMusic().setCycleCount(100);
			getBackGroundMusic().setVolume(0.7);
			getBackGroundMusic().play();
		});
		audioThread.setDaemon(true);
		audioThread.start();
	}

	public void muteBackGroundMusic() {
		getBackGroundMusic().stop();
	}

	public AudioClip getBackGroundMusic() {
		return backGroundMusic;
	}

	public void setBackGroundMusic(AudioClip backGroundMusic) {
		this.backGroundMusic = backGroundMusic;
	}

	public void playInGameMusic() {
		gameThread = new Thread(() -> {
			getInGameSound().setCycleCount(100);
			getInGameSound().setVolume(0.5);
			getInGameSound().play();
		});
		gameThread.setDaemon(true);
		gameThread.start();
	}

	public void muteInGameMusic() {
		getInGameSound().stop();
	}

	public AudioClip getInGameSound() {
		return inGameSound;
	}

	public void setInGameSound(AudioClip inGameSound) {
		this.inGameSound = inGameSound;
	}

	public void playLevelComplete() {
		completeThread = new Thread(() -> {
			getLevelComplete().setVolume(1);
			getLevelComplete().play();
		});
		completeThread.setDaemon(true);
		completeThread.start();
	}

	public AudioClip getLevelComplete() {
		return levelComplete;
	}

	public void setLevelComplete(AudioClip levelComplete) {
		this.levelComplete = levelComplete;
	}
}