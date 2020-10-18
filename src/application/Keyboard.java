package application;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;

public class Keyboard {
	protected static String audio = "src/asset/audio/audio_";
	protected static String[] audioTypeArray = new String[]{"piano_","bigAndDumb_","ethereal_"};
	protected static int currentAudioType = 0;
	public static void setKeyInput(Scene scene) {
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			for (KeyCode k : KeyCode.values()) {
				if (k==key.getCode() && key.getCode().isLetterKey()) {
					Window.recordKey(k);
					playSequence(k);
					playSound(k);
				}
			} 
		});
	}
	
	public static void playSequence(KeyCode k) {
		char index = k.getChar().charAt(0);		
		for (char c = 'A'; c <= 'Z'; c++) {
			if (c == index) {
				System.out.println(c);
				Window.sprite.addSprite(c - 'A', true, false, false);
			}
		}
	}
	
	public static void playSound(KeyCode k) {
		selectSound(k);
		File audioFile = new File(audio);
		String audioFileString = audioFile.toURI().toString();
		AudioClip audioClip = new AudioClip(audioFileString);
		
		audioClip.play();
		
	}
	
	public static void switchSound(Integer audioNum) {
		currentAudioType = audioNum;
	}
	
	public static void selectSound(KeyCode k) {
		audio = "src/asset/audio/audio_" + audioTypeArray[currentAudioType];
		switch (k.getChar().charAt(0)) {
			case 'Q':
				audio += "26.wav";
				break;
			case 'W':
				audio += "25.wav";
				break;
			case 'E':
				audio += "24.wav";
				break;
			case 'R':
				audio += "23.wav";
				break;
			case 'T':
				audio += "22.wav";
				break;
			case 'A':
				audio += "21.wav";
				break;
			case 'S':
				audio += "20.wav";
				break;
			case 'D':
				audio += "19.wav";
				break;
			case 'F':
				audio += "18.wav";
				break;
			case 'G':
				audio += "17.wav";
				break;
			case 'Z':
				audio += "16.wav";
				break;
			case 'X':
				audio += "15.wav";
				break;
			case 'C':
				audio += "14.wav";
				break;
			case 'V':
				audio += "13.wav";
				break;
			case 'Y':
				audio += "12.wav";
				break;
			case 'U':
				audio += "11.wav";
				break;
			case 'I':
				audio += "10.wav";
				break;
			case 'O':
				audio += "9.wav";
				break;
			case 'P':
				audio += "8.wav";
				break;
			case 'H':
				audio += "7.wav";
				break;
			case 'J':
				audio += "6.wav";
				break;
			case 'K':
				audio += "5.wav";
				break;
			case 'L':
				audio += "4.wav";
				break;
			case 'B':
				audio += "3.wav";
				break;
			case 'N':
				audio += "2.wav";
				break;
			case 'M':
				audio += "1.wav";
				break;
			default:
				audio += "13.wav";
		}
	}

}
