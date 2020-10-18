package application;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;

public class Sprite {
	protected String dir = "src/asset/image/sprite";
	protected String[][] sequenceArray;
	protected double[][] effectArray;
	protected String[][] bgSequenceArray;
	protected double[][] bgEffectArray;
	protected int frameCount = 60;
	protected int sequenceCount = 27;
	protected int bgCount = 3;
	protected int width;
	protected int height;
	protected static double vSpeed = 1;
	protected static double hSpeed = 0;

	// Initialize variables
	public Sprite(int w, int h) {
		sequenceArray = new String[sequenceCount][];
		effectArray = new double[sequenceCount][];
		bgSequenceArray = new String[bgCount][];
		bgEffectArray = new double[bgCount][];
		width = w;
		height = h;
	}

	// Add sprites to the spriteArray and sequenceArray
	// Sprite array is an array of images of one sprite
	// Sequence array is an array of image sequences of different sprites
	public void loadSprites() {
		for (int i = 0; i < sequenceCount; i++) {
			String[] spriteArray = new String[frameCount];
			for (int j = 0; j < frameCount; j++) {
				String filePostfix = Integer.toString(j);
				if (j < 10) {
					filePostfix = "0" + String.valueOf(j);
				}
				String fileName = dir + Integer.toString(i + 1) + "/sprite" + Integer.toString(i + 1) + "_"
						+ filePostfix + ".png";
				System.out.println(fileName);
				spriteArray[j] = fileName;
			}
			sequenceArray[i] = spriteArray;
			double[] defaultEffect = { 0, 0, 0, 0, 0 };
			effectArray[i] = defaultEffect;
		}
	}

	public void loadBG() {
		int bg1FrameCount = 151;
		int bg2FrameCount = 46;
		String[] bgArray0 = new String[1];
		String[] bgArray1 = new String[bg1FrameCount];
		String[] bgArray2 = new String[bg2FrameCount];

		// Load bg0 (still frame)
		bgArray0[0] = "src/asset/image/bg0/bg0_00.jpg";
		
		// Load bg1
		for (int j = 0; j < bg1FrameCount; j++) {
			String filePostfix = String.valueOf(j);
			if (j < 10) {
				filePostfix = "00" + String.valueOf(j);
			} else if (j < 100) {
				filePostfix = "0" + String.valueOf(j);
			}
			String fileName = "src/asset/image/bg1" + "/bg1_" + filePostfix + ".jpg";
			System.out.println(fileName);
			bgArray1[j] = fileName;
		}
		// Load bg2
		for (int j = 0; j < bg2FrameCount; j++) {
			String filePostfix = String.valueOf(j);
			if (j < 10) {
				filePostfix = "00" + String.valueOf(j);
			} else if (j < 100) {
				filePostfix = "0" + String.valueOf(j);
			}
			String fileName = "src/asset/image/bg2" + "/bg2_" + filePostfix + ".jpg";
			System.out.println(fileName);
			bgArray2[j] = fileName;
		}
		
		bgSequenceArray[0] = bgArray0;
		bgSequenceArray[1] = bgArray1;
		bgSequenceArray[2] = bgArray2;
		double[] defaultEffect = { 0, 0, 0, 0, 0 };
		bgEffectArray[0] = defaultEffect;
		bgEffectArray[1] = defaultEffect;
		bgEffectArray[2] = defaultEffect;
	}

	public int getSequenceCount() {
		return sequenceCount;
	}

	public String[][] getSequenceArray() {
		return sequenceArray;
	}

	// Add sprite to window
	public SpriteView addSprite(int sequenceNum, boolean random, boolean customization, boolean title) {
		File file = new File(sequenceArray[sequenceNum][0]);
		Image image = new Image(file.toURI().toString());
		SpriteView iv = new SpriteView(width, height, random, false, sequenceNum);
		
		iv.setImage(image);
		iv.SetHSBC(effectArray[sequenceNum][0], effectArray[sequenceNum][1], effectArray[sequenceNum][2],
				effectArray[sequenceNum][3]);
		iv.speed = effectArray[sequenceNum][4];

		if (customization == true) {
			iv.customization = true;
		}
		if (title == true) {
			iv.title = true;
		}

		Window.sequenceGroup.getChildren().add(iv);
		Window.ivList.add(iv);

		return iv;
	}

	public SpriteView addBG(int bgNum) {
		File file = new File(bgSequenceArray[bgNum][0]);
		Image image = new Image(file.toURI().toString());
		SpriteView iv = new SpriteView(width, height, false, true, bgNum);

		iv.setImage(image);
		iv.SetHSBC(bgEffectArray[bgNum][0], bgEffectArray[bgNum][1], bgEffectArray[bgNum][2], bgEffectArray[bgNum][3]);
		iv.speed = bgEffectArray[bgNum][4];
		iv.SetFrameCount(150);

		Window.backgroundGroup.getChildren().add(iv);
		return iv;
	}

	// Add all sprite to window (for debugging)
	public void addAllSprites() {
		for (int i = 0; i < sequenceCount; i++) {
			File file = new File(sequenceArray[i][0]);
			Image image = new Image(file.toURI().toString());
			SpriteView iv = new SpriteView(width, height, true, false, i);
			iv.setImage(image);
			iv.setFitHeight(70);
			iv.setFitWidth(50);
			iv.setX(ThreadLocalRandom.current().nextInt(1, width - 100));
			iv.setY(ThreadLocalRandom.current().nextInt(1, height - 50));
			Window.sequenceGroup.getChildren().add(iv);
		}
	}
}
