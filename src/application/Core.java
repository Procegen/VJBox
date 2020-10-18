package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import application.Keyboard;

public class Core extends Application {
	protected Group root; // all the stuff in the window attaches here
	protected static Window window;
	protected int width = 1280;
	protected int height = 720;
	public static UIcontrol ui;
	public static UIrecord uiRecord;
	public static UIhelp uiHelp;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}

	private void initUI(Stage stage) {
		root = new Group();
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("VJ Box");
		Keyboard.setKeyInput(scene);

		// Setup window
		window = new Window(width, height - 100);
		ui = new UIcontrol(width, height);
		uiRecord = new UIrecord(width, height);
		uiHelp = new UIhelp(width, height);
		root.getChildren().add(window.getGP());
		root.getChildren().add(ui.getUI());
		root.getChildren().add(uiRecord.getUI());
		root.getChildren().add(uiHelp.getUI());
	}
}
