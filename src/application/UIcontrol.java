package application;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UIcontrol {
	protected Group ui;
	protected VBox btnPane;
	protected FlowPane keyPane;
	protected FlowPane bgPane;
	protected FlowPane audioPane;
	protected Slider hueSlider;
	protected Slider satSlider;
	protected Slider brightSlider;
	protected Slider contrastSlider;
	protected Slider speedSlider;
	
	long lasttime;
	boolean firsttime = true;
	boolean openUI = true;
	protected int width;
	protected int height;
	public static Driver d;
	
	public UIcontrol(int w, int h) {
		width = w;
		height = h;
		
		// Create components
		ui = new Group();
		btnPane = new VBox();
		d = new Driver();
		
		// Add components
		ui.getChildren().add(btnPane);
		
		// Style
		btnPane.setAlignment(Pos.TOP_CENTER);
		btnPane.setTranslateX(width-width*0.2);
		btnPane.setPrefWidth(width*0.2);
		btnPane.setMinHeight(height);
		btnPane.setStyle("-fx-background-color: #303030; -fx-border-color: white");

		// Methods
		addKeys();
		addAudioButtons();
		addBGButtons();
		addHSBSlider();
		addAnimSlider();
	}

	public Group getUI() {
		return ui;
	}
	
	public void uiSlide(boolean open) {
		openUI = open;
		d.start();
	}
	
	public void addKeys() {
		// Create accordion
		TitledPane keyTitlePane = new TitledPane();
		keyPane = new FlowPane();   
		keyPane.setStyle("-fx-background-color: #303030");
		keyTitlePane.setText("KEYBOARD");
		keyTitlePane.setContent(keyPane);
		keyTitlePane.setAlignment(Pos.CENTER);
		keyPane.setAlignment(Pos.CENTER);
		
		for (char c = 'A'; c <= 'Z'; c++) {
			Button btn = new Button(String.valueOf(c));
			btn.setPrefSize(50, 50);
			keyPane.getChildren().add(btn);
			final int sequenceNum = c - 'A';
			btn.setOnAction(event -> {
				Core.window.removeAllSprites();
				Window.sprite.addSprite(sequenceNum, false, true, false);
				setSliderValues();
			});
		}
		
		btnPane.getChildren().add(keyTitlePane);
	}
	
	public void addAudioButtons() {
		// Create accordion
		TitledPane audioTitlePane = new TitledPane();
		audioPane = new FlowPane();    
		audioPane.setStyle("-fx-background-color: #303030");
		audioTitlePane.setText("AUDIO");
		audioTitlePane.setContent(audioPane);
		audioTitlePane.setAlignment(Pos.CENTER);
		audioTitlePane.setExpanded(false);
		audioPane.setAlignment(Pos.CENTER);
		
		for (int i = 1; i <= 3; i++) {
			Button btn = new Button(String.valueOf(i));
			btn.setPrefSize(50, 50);
			audioPane.getChildren().add(btn);
			btn.setOnAction(event -> {			
				Keyboard.switchSound(Integer.parseInt(btn.getText())-1);
			});
		}
		
		btnPane.getChildren().add(audioTitlePane);
	}
	
	public void addBGButtons() {
		// Create accordion
		TitledPane bgTitlePane = new TitledPane();
		bgPane = new FlowPane();    
		bgPane.setStyle("-fx-background-color: #303030");
		bgTitlePane.setText("BACKGROUND");
		bgTitlePane.setContent(bgPane);
		bgTitlePane.setAlignment(Pos.CENTER);
		bgTitlePane.setExpanded(false);
		bgPane.setAlignment(Pos.CENTER);
		
		// Default bg button
		Button btn0 = new Button("0");
		btn0.setPrefSize(50, 50);
		bgPane.getChildren().add(btn0);
		btn0.setOnAction(event -> {
			Window.bg = Window.sprite.addBG(0);
		});
		
		for (int i = 1; i <= 2; i++) {
			Button btn = new Button(String.valueOf(i));
			btn.setPrefSize(50, 50);
			bgPane.getChildren().add(btn);
			btn.setOnAction(event -> {			
				Window.bg = Window.sprite.addBG(Integer.parseInt(btn.getText()));
				
			});
		}
		
		btnPane.getChildren().add(bgTitlePane);
	}
	
	public void addHSBSlider() {
		// Create accordion
		TitledPane hsbTitlePane = new TitledPane();
		VBox HSBPane = new VBox();    
		HSBPane.setStyle("-fx-background-color: #303030");
		hsbTitlePane.setText("COLOR ADJUSTMENT");
		hsbTitlePane.setContent(HSBPane);
		hsbTitlePane.setAlignment(Pos.CENTER);
		hsbTitlePane.setExpanded(false);
		HSBPane.setAlignment(Pos.CENTER);
		
		// Create sliders
        hueSlider = new Slider(-1, 1, 0); 
        hueSlider.setShowTickMarks(true); 
        hueSlider.setMajorTickUnit(0.25f); 
        hueSlider.setBlockIncrement(0.1f); 
        hueSlider.setValue(Window.ivList.get(0).GetHue());
        satSlider = new Slider(-1, 1, 0); 
        satSlider.setShowTickMarks(true); 
        satSlider.setMajorTickUnit(0.25f); 
        satSlider.setBlockIncrement(0.1f); 
        brightSlider = new Slider(-1, 1, 0); 
        brightSlider.setShowTickMarks(true); 
        brightSlider.setMajorTickUnit(0.25f); 
        brightSlider.setBlockIncrement(0.1f); 
        contrastSlider = new Slider(-1, 1, 0); 
        contrastSlider.setShowTickMarks(true); 
        contrastSlider.setMajorTickUnit(0.25f); 
        contrastSlider.setBlockIncrement(0.1f); 
        
        // Create slider labels
        Label hueLabel = new Label("Hue");
        Label satLabel = new Label("Saturation");
        Label brightLabel = new Label("Brightness");
        Label contrastLabel = new Label("Contrast");
        
        // Style
        hueLabel.setTextFill(Color.web("#ffffff"));
        satLabel.setTextFill(Color.web("#ffffff"));
        brightLabel.setTextFill(Color.web("#ffffff"));
        contrastLabel.setTextFill(Color.web("#ffffff"));
        
		hueSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (!Window.ivList.isEmpty()) {
					Window.ivList.get(0).SetHue(newValue.doubleValue());
					// Save the value to the effectArray
					Window.sprite.effectArray[Window.ivList.get(0).sequenceNum][0] = newValue.doubleValue();
				}
			}
		});
		satSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (!Window.ivList.isEmpty()) {
					Window.ivList.get(0).SetSaturation(newValue.doubleValue());
					// Save the value to the effectArray
					Window.sprite.effectArray[Window.ivList.get(0).sequenceNum][1] = newValue.doubleValue();
				}
			}
		});
		brightSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (!Window.ivList.isEmpty()) {
					Window.ivList.get(0).SetBrightness(newValue.doubleValue());
					// Save the value to the effectArray
					Window.sprite.effectArray[Window.ivList.get(0).sequenceNum][2] = newValue.doubleValue();
				}
			}
		});
		contrastSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (!Window.ivList.isEmpty()) {
					Window.ivList.get(0).SetContrast(newValue.doubleValue());
					// Save the value to the effectArray
					Window.sprite.effectArray[Window.ivList.get(0).sequenceNum][3] = newValue.doubleValue();
				}
			}
		});
		
		HSBPane.getChildren().addAll(hueLabel, hueSlider, satLabel, satSlider, brightLabel, brightSlider, contrastLabel, contrastSlider);
		btnPane.getChildren().add(hsbTitlePane);
	}
	
	public void addAnimSlider() {
		// Create accordion
		TitledPane animTitlePane = new TitledPane();
		VBox animPane = new VBox();     
		animPane.setStyle("-fx-background-color: #303030");
		animTitlePane.setText("ANIMATION");
		animTitlePane.setContent(animPane);
		animTitlePane.setAlignment(Pos.CENTER);
		animTitlePane.setExpanded(false);
		animPane.setAlignment(Pos.CENTER);
		
		// Create sliders
        speedSlider = new Slider(-500, 500, 0); 
        speedSlider.setShowTickMarks(true); 
        speedSlider.setMajorTickUnit(100); 
        speedSlider.setBlockIncrement(20); 
        
        // Create slider labels
        Label speedLabel = new Label("Speed");
        
        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Window.ivList.get(0).speed = newValue.doubleValue();
				// Save the value to the effectArray
				Window.sprite.effectArray[Window.ivList.get(0).sequenceNum][4] = newValue.doubleValue();
			}
		});
		
		animPane.getChildren().addAll(speedLabel, speedSlider);
		btnPane.getChildren().add(animTitlePane);
	}
	
	public void setSliderValues() {
		hueSlider.setValue(Window.ivList.get(0).hue);
		satSlider.setValue(Window.ivList.get(0).sat);
		brightSlider.setValue(Window.ivList.get(0).bright);
		contrastSlider.setValue(Window.ivList.get(0).contrast);
		speedSlider.setValue(Window.ivList.get(0).speed);
	}
		
    public class Driver extends AnimationTimer
    {
    	@Override
    	public void handle( long now )
    	{
    		// close UI
    		if (!openUI) {
        		if ( firsttime ) { lasttime = now; firsttime = false; }
        		else
        		{
        			if (ui.getTranslateX() < width * 0.25) {
            			double speed = 600;
            		    double deltat = (now-lasttime ) * 1.0e-9;
            			double xPos = ui.getTranslateX() + speed * deltat;
            			
            			ui.setTranslateX(xPos);
            			
            			lasttime = now;
        			}
        			else {
        				ui.setTranslateX(width * 0.25);
        				stop();
        				firsttime = true;
        			}
        	    }
    		}
    		
    		// open UI
    		else {
        		if ( firsttime ) { lasttime = now; firsttime = false; }
        		else
        		{
        			if (ui.getTranslateX() > 0) {
            			double speed = -600;
            		    double deltat = (now-lasttime ) * 1.0e-9;
            			double xPos = ui.getTranslateX() + speed * deltat;
            			
            			ui.setTranslateX(xPos);
            			
            			lasttime = now;
        			}
        			else {
        				ui.setTranslateX(0);
        				stop();
        				firsttime = true;
        			}
        	    }
    		}
    	}
    }
}
