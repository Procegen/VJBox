package application;

import javafx.scene.Group;

public class UIhelp {
	protected Group uiGroup;
	protected int width;
	protected int height;
	public UIhelp(int w, int h) {
		width = w;
		height = h;
		
		// Create components
		uiGroup = new Group();
		uiGroup.prefWidth(w * 0.9);
		uiGroup.prefHeight(h * 0.9);
		uiGroup.setStyle("-fx-background-color: White");
	}
	public Group getUI() {
		return uiGroup;
	}
}
