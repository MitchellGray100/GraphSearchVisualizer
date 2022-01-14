package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		final int width = 1920;
		final int height = 1080;
		GridPane largeGrid = new GridPane();
		HBox buttons = new HBox();
		Text text = new Text();
		text.setText("test");
		BorderPane root = new BorderPane();
		Scale scale = new Scale(1, 1, 0, 0);
		scale.xProperty().bind(root.widthProperty().divide(width));
		scale.yProperty().bind(root.heightProperty().divide(height));
		root.getTransforms().add(scale);
		root.setTop(buttons);
		root.setCenter(largeGrid);
		root.setLeft(text);
//		root.scaleXProperty().bind(primaryStage.widthProperty());
//		root.scaleYProperty().bind(primaryStage.heightProperty().divide(1080));
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setResizable(true);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
