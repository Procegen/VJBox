package application;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

import java.util.*;

public class Window {
	protected Group gp;
	public static Group sequenceGroup;
	protected Button uiBtn;
	public static Sprite sprite;
	public static Group backgroundGroup;
	public static double recordTime;
	public static double playTime;
	public static boolean record;
	public static boolean play;
	public static Queue<RecordData> recordQueue;
	
	public Group getGP() {
		return gp;
	}
	
	public Group getSequenceGroup() {
		return sequenceGroup;
	}
	
	public Button getUIBtn() {
		return uiBtn;
	}
	
	protected int width;
	protected int height;
	public static ArrayList<SpriteView> ivList;
	public static SpriteView bg;

	public Window(int w, int h) {
		play = false;
		record = false;
		recordTime = 0.0f;
		playTime = 0.0f;
		recordQueue = new LinkedList<>();
		
		width = w;
		height = h;
		gp = new Group();
		sequenceGroup = new Group();
		backgroundGroup = new Group();
		ivList = new ArrayList<SpriteView>();
		sprite = new Sprite(w,h);
		sprite.loadSprites();
		sprite.loadBG();
		
		gp.getChildren().add(backgroundGroup);
		gp.getChildren().add(sequenceGroup);
		bg = sprite.addBG(0);
		bg.frameCount = 45;
		sprite.addSprite(26, false, false, true);	
		
		// Animation
		Driver d = new Driver();
		d.start();
	}
	
	public void removeSprite(int sequenceNum) {
		SpriteView iv = null;
		for (SpriteView i : ivList) {
			if (i.sequenceNum == sequenceNum) {
				iv = i;
			}
		}
		if (iv != null) {
			sequenceGroup.getChildren().remove(iv);
			ivList.remove(iv);
		}
	}
	public void removeAllSprites() {
		ivList.clear();
		sequenceGroup.getChildren().clear();
	}
	
	public static void recordKey(KeyCode k) {
		if (record) {
			RecordData r = new RecordData();
			r.time = recordTime;
			r.key = k;
			recordQueue.add(r);
			System.out.println("Recorded: " + k.getChar() + "| Time: " + recordTime);
		}
	}

	static class RecordData {
		public double time;
		public KeyCode key;
	}
	
    public class Driver extends AnimationTimer
    {
    	boolean firsttime = true;
    	double lasttime;
    	int frame = 0;
    	double deltatSum = 0;
    	
    	@Override
    	public void handle( long now )
    	{
			if (firsttime) { lasttime = now; firsttime = false; } 
			else {
				double deltat = (now - lasttime) * 1.0e-9;
				deltatSum += deltat;
				
				// Increment frame number (30fps)
				if (deltatSum >= 0.03) {
					frame++;
					deltatSum = 0;
				}
				
				if (record) {
					recordTime += deltat;
				}
				
				// Loop through sprites to apply changes 
				for (SpriteView i : ivList) {
					// Change frame
					i.changeSpriteImage(frame);
					i.lifeTime += deltat;
					
					// Rotate
					if (i.animNum == 1) {
						double rot = i.getRotate() + i.speed * deltat;
						i.setRotate(rot);
					}
					
					// Translate
					else if (i.animNum == 2) {
						double posX = i.getTranslateX() + i.speed * deltat;
						double posY = i.getTranslateY() + i.speed * deltat;
	
						i.setTranslateX(posX);
						i.setTranslateY(posY);
					}
				}
				
				// Remove sprites that have lived more than 2 seconds
				for (SpriteView i : new ArrayList<>(ivList)) {
					if (i.lifeTime > 2.0f && !i.customization && !i.title) {
						ivList.remove(i);
						sequenceGroup.getChildren().remove(i);
					}
					else if (i.lifeTime > 3.0f && !i.customization && i.title) {
						ivList.remove(i);
						sequenceGroup.getChildren().remove(i);
					}
				}
				
				// Playback
				if (play && !record) {
					playTime += deltat;
					
					// Find current key
					if (recordQueue.size() > 0) {
						double peekTime = recordQueue.peek().time;
						if ((Math.abs(playTime - peekTime) < 0.1)) {
							System.out.println("found record");
							char index = recordQueue.peek().key.getChar().charAt(0);
							for (char c = 'A'; c <= 'Z'; c++) {
								if (c == index) {
									sprite.addSprite(c - 'A', true, false, false);
									Keyboard.playSound(recordQueue.peek().key);
									recordQueue.remove();
								}
							}
						}
					}
					else {
						play = false;
						playTime = 0.0f;
					}
				}
				lasttime = now;
			}
    	}
    }
}