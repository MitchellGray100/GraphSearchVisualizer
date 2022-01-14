package application;

import java.util.ArrayList;

import controller.ControllerImpl;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class Main extends Application {
	private ControllerImpl controller = new ControllerImpl();
	private HBox buttons = new HBox();
	private Cursor tempCursor;
	private Button[] buttonArray = new Button[8];
	private Scene scene;
	private int speed = 0;
	private State state = State.CLEARGRID;
	private GridPane largeGrid = new GridPane();
	private ArrayList<model.Node> list = new ArrayList<model.Node>();
	private AnimationTimer animationTimer;
	private double time = 0;
	private boolean stop = false;

	private enum State {
		CLEARGRID, PLACESTARTSQUARE, PLACEENDINGSQUARE, PLACEWALL, REMOVEWALL, BFS, DFS, ASTAR;
	}

	@Override
	public void start(Stage primaryStage) {
		// constants
		final int width = 1920;
		final int height = 1080;
		// nodes to put into root
		for (int r = 0; r < controller.getRowSize(); r++) {
			for (int c = 0; c < controller.getColumnSize(); c++) {
				largeGrid.add(new Tile(Color.WHITE, r, c), r, c);
			}
		}
		largeGrid.setAlignment(Pos.CENTER);
//		HBox buttons = new HBox();
		buttons.setSpacing(10);

		// CSS and Buttons
		ToolButton clearGrid = new ToolButton("Clear Grid");
		clearGrid.setId("clearGrid");
		ToolButton placeStartingNode = new ToolButton("Place Start Square");
		placeStartingNode.setId("placeStarting");
		ToolButton placeEndingNode = new ToolButton("Place Ending Square");
		placeEndingNode.setId("placeEnding");
		ToolButton placeWall = new ToolButton("Place Wall");
		placeWall.setId("placeWall");
		ToolButton removeWall = new ToolButton("Remove Wall");
		removeWall.setId("removeWall");
		ToolButton bfs = new RunnableToolButton("BFS");
		bfs.setId("bfs");
		ToolButton dfs = new RunnableToolButton("DFS");
		dfs.setId("dfs");
		ToolButton aStar = new RunnableToolButton("A*");
		aStar.setId("aStar");
		buttonArray[0] = clearGrid;
		buttonArray[1] = placeStartingNode;
		buttonArray[2] = placeEndingNode;
		buttonArray[3] = placeWall;
		buttonArray[4] = removeWall;
		buttonArray[5] = bfs;
		buttonArray[6] = dfs;
		buttonArray[7] = aStar;
		buttons.getChildren().addAll(clearGrid, placeStartingNode, placeEndingNode, placeWall, removeWall, bfs, dfs,
				aStar);
		Text text = new Text();
//		text.setText("test");
		VBox bottom = new VBox();

		Slider slide = new Slider();
		slide.setOnMouseEntered(event -> {
			tempCursor = scene.getCursor();
			scene.setCursor(Cursor.HAND);
		});
		slide.setOnMouseExited(event -> {
			scene.setCursor(tempCursor);
		});
		slide.setShowTickLabels(true);
		slide.setShowTickMarks(true);
		slide.setBlockIncrement(5);
		Text bottomText = new Text();
		bottomText.setText("Speed: " + speed);
		bottomText.setFont(new Font(32));
//		bottomText.setTextAlignment(TextAlignment.CENTER);
		bottom.getChildren().addAll(bottomText, slide);
		slide.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				speed = new_val.intValue();
				bottomText.setText("Speed: " + speed + "%");
			}
		});

		// root structure
		BorderPane root = new BorderPane();
		root.getStyleClass().add("root");
		root.setTop(buttons);
		root.setCenter(largeGrid);
		root.setLeft(text);
		root.setBottom(bottom);

		// Scaling
		Scale scale = new Scale(1, 1, 0, 0);
		scale.xProperty().bind(root.widthProperty().divide(width));
		scale.yProperty().bind(root.heightProperty().divide(height));
		root.getTransforms().add(scale);

		// display
		scene = new Scene(root, 1920, 1080);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setResizable(true);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private EventHandler<? super MouseEvent> setButtonStates(Button button) {
		switch (button.getText()) {
		case "Clear Grid":
			state = State.CLEARGRID;
			break;
		case "Place Start Square":
			state = State.PLACESTARTSQUARE;
			break;
		case "Place Ending Square":
			state = State.PLACEENDINGSQUARE;
			break;
		case "Place Wall":
			state = State.PLACEWALL;
			break;
		case "Remove Wall":
			state = State.REMOVEWALL;
			break;
		case "BFS":
			state = State.BFS;
			break;
		case "DFS":
			state = State.DFS;
			break;
		case "A*":
			state = State.ASTAR;
			break;
		}
//		System.out.println(button.getText());
//		System.out.println(state);
		return null;
	}

	private EventHandler<? super MouseEvent> clearGrid(GridPane grid) {
		for (int r = 0; r < grid.getRowCount(); r++) {
			for (int c = 0; c < grid.getColumnCount(); c++) {
				((Tile) (grid.getChildren().get(r * 20 + c))).border.setFill(Color.WHITE);
				((Tile) (grid.getChildren().get(r * 20 + c))).color = Color.WHITE;
			}
		}
		return null;
	}

	public class ToolButton extends Button {
		public ToolButton(String text) {
			this.getStyleClass().add("toolButton");
			this.setText(text);

			this.setOnMouseEntered(event -> {
				tempCursor = scene.getCursor();
				scene.setCursor(Cursor.HAND);
			});
			this.setOnMouseExited(event -> {
				scene.setCursor(tempCursor);
			});

			this.setOnMouseClicked(event -> {
				for (int i = 0; i < buttonArray.length; i++) {
					buttonArray[i].getStyleClass().remove("selectedButton");
				}
				this.getStyleClass().add("selectedButton");
			});

			this.setOnMouseReleased(event -> {
				setButtonStates(this);
				switch (state) {
				case ASTAR:
					if (controller.isReadyToSearch()) {
						controller.generateEdges();
						list = controller.aStar();
						animate(State.ASTAR);
					}
					break;
				case BFS:
					if (controller.isReadyToSearch()) {
						controller.generateEdges();
						list = controller.bfs();
						animate(State.BFS);
					}
					break;
				case CLEARGRID:
					controller.clear();
					clearGrid(largeGrid);
					break;
				case DFS:
					if (controller.isReadyToSearch()) {
						controller.generateEdges();
						list = controller.dfs();
						animate(State.DFS);
					}
					break;
				case PLACEENDINGSQUARE:
					break;
				case PLACESTARTSQUARE:
					break;
				case PLACEWALL:
					break;
				case REMOVEWALL:
					break;
				default:
					break;

				}
			});

		}
	}

	public class RunnableToolButton extends ToolButton {
		public RunnableToolButton(String text) {
			super(text);

			this.getStyleClass().add("runnableToolButton");
		}
	}

	public class Tile extends StackPane {
		Rectangle border = new Rectangle(45, 45);
		Color color;
		int r;
		int c;

		public Tile(Color color, int r, int c) {
			this.r = r;
			this.c = c;
			this.color = color;
			border.setFill(color);
			border.setStroke(Color.BLACK);
			this.getChildren().add(border);

			this.setOnMouseEntered(event -> {
				tempCursor = scene.getCursor();
				scene.setCursor(Cursor.CROSSHAIR);
				if (event.isShiftDown()) {
					System.out.println("Hello");
					drawTile();
				}

			});

			this.setOnMouseExited(event -> {
				scene.setCursor(tempCursor);
			});

			setOnMouseReleased(event -> {
				drawTile();
			});
		}

		private void drawTile() {
			switch (state) {
			case ASTAR:
				break;
			case BFS:
				break;
			case CLEARGRID:
				break;
			case DFS:
				break;
			case PLACEENDINGSQUARE:
				for (Node tile : largeGrid.getChildren()) {
					if (((Tile) tile).color == Color.RED) {
						((Tile) tile).color = Color.WHITE;
						((Tile) tile).border.setFill(Color.WHITE);
					}
				}

				if (c == controller.getSourceXCordinate() && r == controller.getSourceYCordinate()) {
					controller.setSourceCordinates(-1, -1);
				}

				this.color = Color.RED;
				border.setFill(this.color);
				controller.setSinkCordinates(c, r);
				break;

			case PLACESTARTSQUARE:
				for (Node tile : largeGrid.getChildren()) {

					if (((Tile) tile).color.equals(Color.LIME)) {
						((Tile) tile).color = Color.WHITE;
						((Tile) tile).border.setFill(Color.WHITE);
					}
				}

				if (c == controller.getSinkXCordinate() && r == controller.getSinkYCordinate()) {
					controller.setSinkCordinates(-1, -1);
				}

				this.color = Color.LIME;
				border.setFill(this.color);
				controller.setSourceCordinates(c, r);

				break;

			case PLACEWALL:
				if (c == controller.getSinkXCordinate() && r == controller.getSinkYCordinate()) {
					controller.setSinkCordinates(-1, -1);
				} else if (c == controller.getSourceXCordinate() && r == controller.getSourceYCordinate()) {
					controller.setSourceCordinates(-1, -1);
				}
				this.color = Color.GRAY;
				border.setFill(this.color);
				controller.addWall(c, r);

				break;

			case REMOVEWALL:
				if (color == Color.GRAY) {
					this.color = Color.WHITE;
					border.setFill(this.color);
					controller.removeWall(c, r);
				}
				break;
			default:
				break;

			}

		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void animate(State state) {
		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {

				update(state);
				if (stop || list.size() == 0) {
					stop = false;
					stop();
				}

			}
		};
		animationTimer.start();

	}

	protected void update(State state) {
		if (state != this.state) {
			removeColors();
			stop = true;
		}

		time += 0.01 * speed;

		System.out.println(time);
		if (time >= 1) {
			time = 0;
			if (list.size() != 0)
				colorOneSquare();
		}
	}

	private void colorOneSquare() {

		System.out.println(3);
		for (Node tile : largeGrid.getChildren()) {
			if (((Tile) tile).r == list.get(0).getColumn() && ((Tile) tile).c == list.get(0).getRow()) {
				((Tile) tile).color = Color.PURPLE;
				((Tile) tile).border.setFill(Color.PURPLE);
				list.remove(0);
				changeColors();
				break;
			}
		}

	}

	private void changeColors() {

		ObservableList<Node> temp = largeGrid.getChildren();
		for (Node tile : largeGrid.getChildren()) {
			if (list.size() == 0) {
				break;
			}

			if (((Tile) tile).color == Color.PURPLE) {
				if (isBlack(temp, ((Tile) tile).r, ((Tile) tile).c)) {
					((Tile) tile).color = Color.PINK;
					((Tile) tile).border.setFill(Color.PINK);
				}
			}

		}

	}

	private boolean isBlackHelper(ObservableList<Node> temp, int r, int c) {
		if (r < 0 || r >= controller.getRowSize() || c < 0 || c >= controller.getColumnSize()) {
			return true;
		}

		Tile tile = ((Tile) (temp.get(r * 20 + c)));

		if (tile.color == Color.GRAY || tile.color == Color.LIME || tile.color == Color.RED
				|| tile.color == Color.PURPLE || tile.color == Color.PINK) {
			return true;
		}
		return false;
	}

	private boolean isBlack(ObservableList<Node> temp, int r, int c) {
		boolean top = isBlackHelper(temp, r - 1, c);
		boolean bottom = isBlackHelper(temp, r + 1, c);
		boolean left = isBlackHelper(temp, r, c - 1);
		boolean right = isBlackHelper(temp, r, c + 1);
//		+ bottom + left + right);
		if (top && bottom && left && right) {
			return true;
		}
		return false;
	}

	private void removeColors() {
		for (Node tile : largeGrid.getChildren()) {
			if (((Tile) tile).color != Color.LIME && ((Tile) tile).color != Color.RED
					&& ((Tile) tile).color != Color.GRAY) {
				((Tile) tile).color = Color.WHITE;
				((Tile) tile).border.setFill(Color.WHITE);
			}
		}

	}
}
