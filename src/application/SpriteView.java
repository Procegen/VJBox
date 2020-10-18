package application;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpriteView extends ImageView{
	public double hue = 0;
	public double sat = 0;
	public double bright = 0;
	public double contrast = 0;
	public int frame = 0;
	protected int frameCount = 60;
	protected int frameCounter = 0;
	public int animNum;
	public int sequenceNum;
	public double speed;
	protected int width;
	protected int height;
	protected ColorAdjust effect;
	public double lifeTime;
	public boolean customization;
	public boolean title;
	private boolean animStarted;
	protected int frameOffset;
	
	public SpriteView(int w, int h, boolean rand, boolean center, int seqNum) {
		super();
		animStarted = false;
		title = false;
		customization = false;
		lifeTime = 0;
		speed = 300;
		width = w;
		height = h;
		animNum = ThreadLocalRandom.current().nextInt(0, 3);
		sequenceNum = seqNum;
		
		// Color adjustment
		effect = new ColorAdjust();
		setEffect(effect);
		
		if (rand) {
			setX(ThreadLocalRandom.current().nextInt(-100, width - 200));
			setY(ThreadLocalRandom.current().nextInt(-100, height - 300));
		}
		else if (!center) {
			setX(width/2-200-width*0.1);
			setY(height/2-200-height*0.1);
		}
		else {
			setX(0);
			setY(0);
		}
	}
	
	public void SetHSBC(double h, double s, double b, double c) {
		hue = h;
		sat = s;
		bright = b;
		contrast = c;
		effect.setHue(hue);
		effect.setSaturation(sat);
		effect.setBrightness(bright);
		effect.setContrast(contrast);
	}
	
	public void SetHue(double h) {
		hue = h;
		effect.setHue(hue);
	}
	
	public void SetBrightness(double b) {
		bright = b;
		effect.setBrightness(bright);
	}
	
	public void SetSaturation(double s) {
		sat = s;
		effect.setSaturation(sat);
	}
	
	public void SetContrast(double c) {
		contrast = c;
		effect.setContrast(contrast);
	}
	
	public void changeSpriteImage(int frame) {
		if (frame >= frameCount) {
			frame = frame % frameCount;
		}
		if (!animStarted) {
			frameOffset = frame;
			animStarted = true;
		}
		frame -= frameOffset;
		if (frame < 0) {
			frame += 60;
		}
		
		File file = new File(Window.sprite.sequenceArray[sequenceNum][frame]);
		Image image = new Image(file.toURI().toString());
		setImage(image);
		frameCounter++;
	}
	
	public void changeBGImage(int frame) {
		if (frame >= frameCount) {
			frame = frame % frameCount;
		}
		
		File file = new File(Window.sprite.bgSequenceArray[sequenceNum][frame]);
		Image image = new Image(file.toURI().toString());
		setImage(image);
	}
	
	public double GetHue() { return hue; }
	public double GetSaturation() { return sat; }
	public double GetBrightness() { return bright; }
	public double GetContrast() { return contrast; }
	public double GetSpeed() { return speed; }
	public void SetFrameCount(int fc) { frameCount = fc; }
}
