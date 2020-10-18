package application;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class UIrecord {
	protected Group uiRecord;
	protected HBox recordPane;
	protected int width;
	protected int height;
	
	public UIrecord(int w, int h) {
		width = w;
		height = h;
		
		// Create components
		uiRecord = new Group();
		recordPane = new HBox();
		Button recordbtn = new Button("RECORD");
		Button playbtn = new Button("PLAY");
		Button customizebtn = new Button("CUSTOMIZATION");
		
		// Add components
		uiRecord.getChildren().add(recordPane);
		recordPane.getChildren().add(recordbtn);
		recordPane.getChildren().add(playbtn);
		recordPane.getChildren().add(customizebtn);
		
		// Style
		recordPane.setAlignment(Pos.CENTER);
		recordPane.setTranslateY(height*0.9);
		recordPane.setTranslateX(0);
		recordPane.setPrefHeight(height*0.1);
		recordPane.setPrefWidth(width);
		recordPane.setStyle("-fx-background-color: #363636; -fx-border-color: white");
		recordPane.setSpacing(20);
		customizebtn.setTranslateX(width/3);
		recordbtn.setPrefSize(100, 30);
		playbtn.setPrefSize(100, 30);
		customizebtn.setPrefHeight(30);
		
		// Event
		customizebtn.setOnAction(event -> {
			if (Core.ui.openUI == true) {
				Core.window.removeAllSprites();
				System.out.println("Close ui");
				Core.ui.uiSlide(false);
			}
			else if (Core.ui.openUI == false){
				System.out.println("Open ui");
				Core.ui.uiSlide(true);
			}
		});
		
		recordbtn.setOnAction(event -> {
			if (!Window.record) {
				// Reset data
				Window.recordTime = 0;
				Window.recordQueue.clear();
				
				Window.record = true;
				recordbtn.setText("Stop");
			}
			else {
				Window.record = false;
				recordbtn.setText("Record");
			}
		});
		
		playbtn.setOnAction(event -> {
			Window.recordTime = 0.0f;
			Window.play = true;
		});
	}

	public Group getUI() {
		return uiRecord;
	}
}
