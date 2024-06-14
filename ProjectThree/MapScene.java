package ProjectThree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MapScene extends Scene {

	static int numberOfVertices;
	static int numberOfEdges;

	Graph graph;

	File file; // File object for the file to be compressed

	BorderPane bp = new BorderPane(); // Main layout pane for the scene

	double maxWidth = 900;
	double maxHeight = 517.60;
	Stage stage; // Stage on which the scene is set
	Scene scene; // Previous scene to return to
	EncancedComboBox sourceBoxCustom = new EncancedComboBox("Enter Source");
	EncancedComboBox desBoxCustom = new EncancedComboBox("Enter Destination");

	public MapScene(Stage stage, Scene scene, File file) {
		super(new BorderPane(), 1200, 650);
		this.stage = stage;
		this.scene = scene;

		this.bp = ((BorderPane) this.getRoot());

		this.file = file;

		readFile();
		addFX();
	}

	public void readFile() {
		try (FileInputStream fileInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

			String line;
			String[] firstLine = bufferedReader.readLine().split(",");

			numberOfVertices = Integer.parseInt(firstLine[0].trim());
			numberOfEdges = Integer.parseInt(firstLine[1].trim());

			graph = new Graph(numberOfVertices);

			for (int i = 0; i < numberOfVertices; i++) {
				line = bufferedReader.readLine();

				String[] tkz = line.split(",");
				String countryName = tkz[0].trim();
				double latitude = Double.parseDouble(tkz[1].trim());
				double longitude = Double.parseDouble(tkz[2].trim());

				Country country = new Country(countryName, longitude, latitude);

				country.setX((((longitude + 180.0) / 360.0) * maxWidth));
				country.setY((((90.0 - latitude) / 180.0) * maxHeight));

				Vertix vertix = new Vertix(country);
				graph.addVertix(vertix);
			}

			while ((line = bufferedReader.readLine()) != null) {
				String[] tkz = line.split(",");
				Vertix source = graph.getVertix(tkz[0].trim());
				Vertix destination = graph.getVertix(tkz[1].trim());

				Edge edge = new Edge(source, destination);
				source.getVertices().addLast(edge);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ScrollPane getMap() {
		Image mapImage = new Image("Images/World-Physical-Map-Simplified.jpg");
		ImageView map = new ImageView(mapImage);

		map.setFitWidth(maxWidth); // Adjust the size as needed
		map.setFitHeight(maxHeight);

		map.getStyleClass().add("map-image");

		Pane pane = new Pane(map);

		Vertix[] nodes = graph.getHashTable().getTable();

		for (int i = 0; i < nodes.length; i++) {
			Vertix vertix = nodes[i];

			String countryName = vertix.getCountry().getCountryName();
			ImageView flagLocationImage = getIcon(countryName);

			sourceBoxCustom.addCountry(countryName);
			desBoxCustom.addCountry(countryName);

			flagLocationImage.setLayoutX(vertix.getCountry().getX() - 512 / 2 / 20);
			flagLocationImage.setLayoutY(vertix.getCountry().getY() - 512 / 20);
			pane.getChildren().add(flagLocationImage);
		}

		ScrollPane scrollPane = new ScrollPane(pane);
		scrollPane.setPannable(true); // Allow panning
		scrollPane.setPrefViewportWidth(maxWidth - 15);
		scrollPane.setPrefViewportHeight(maxHeight - 9);

		return scrollPane;
	}

	private void addFX() {
		VBox tableBox = new VBox(10, getMap());
		tableBox.setAlignment(Pos.CENTER);
		bp.setPadding(new Insets(15));

		BorderPane.setMargin(tableBox, new Insets(0, 0, 0, 20));

		bp.setLeft(tableBox);

		Label sourceLabel = new Label("Source: ");
		sourceLabel.setStyle("-fx-text-fill: #141E46; " + "-fx-background-color: white; " + "-fx-padding: 3; "
				+ "-fx-border-color: #41B06E; " + "-fx-border-radius: 5; " + "-fx-background-radius: 5; "
				+ "-fx-font-size: 16px;");

		sourceLabel.setPrefWidth(150);

		Label targetLabel = new Label("Target: ");
		targetLabel.setStyle("-fx-text-fill: #141E46; " + "-fx-background-color: white; " + "-fx-padding: 3; "
				+ "-fx-border-color: #41B06E; " + "-fx-border-radius: 5; " + "-fx-background-radius: 5; "
				+ "-fx-font-size: 16px;");

		targetLabel.setPrefWidth(150);

		sourceLabel.setAlignment(Pos.CENTER);
		targetLabel.setAlignment(Pos.CENTER);

		Button goButton = new Button("GO");
		goButton.setMaxWidth(Double.MAX_VALUE);

		Label pathLabel = new Label("Path: ");
		pathLabel.setStyle("-fx-text-fill: #141E46; " + "-fx-background-color: white; " + "-fx-padding: 3; "
				+ "-fx-border-color: #41B06E; " + "-fx-border-radius: 5; " + "-fx-background-radius: 5; "
				+ "-fx-font-size: 16px;");

		TextArea pathArea = new TextArea();

		Label disLabel = new Label("Distance: ");
		disLabel.setStyle("-fx-text-fill: #141E46; " + "-fx-background-color: white; " + "-fx-padding: 3; "
				+ "-fx-border-color: #41B06E; " + "-fx-border-radius: 5; " + "-fx-background-radius: 5; "
				+ "-fx-font-size: 16px;");

		TextField disField = new TextField();

		HBox disBox = new HBox(10, disLabel, disField);
		disBox.setAlignment(Pos.CENTER);

		Button back = new Button("Back");
		back.setOnAction(e -> {
			stage.setScene(scene);
		});
		back.setMaxWidth(Double.MAX_VALUE);

		GridPane gp = new GridPane();

		HBox sourceBox = new HBox(5, sourceLabel, sourceBoxCustom.getComboBox());
		sourceBox.setAlignment(Pos.CENTER);
		gp.add(sourceBox, 0, 0);
		GridPane.setColumnSpan(sourceBox, 2);

		HBox desBox = new HBox(5, targetLabel, desBoxCustom.getComboBox());
		desBox.setAlignment(Pos.CENTER);
		gp.add(desBox, 0, 1);
		GridPane.setColumnSpan(desBox, 2);

		gp.add(goButton, 0, 2);
		GridPane.setColumnSpan(goButton, 2);

		GridPane.setMargin(goButton, new Insets(0, 0, 25, 0));

		gp.add(pathLabel, 0, 3);
		gp.add(pathArea, 0, 4);
		GridPane.setColumnSpan(pathArea, 2);

		gp.add(disLabel, 0, 5);
		gp.add(disField, 1, 5);

		gp.add(back, 0, 6);
		GridPane.setColumnSpan(back, 2);

		gp.setVgap(10);
		BorderPane.setMargin(gp, new Insets(0, 25, 0, 20));

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPrefWidth(100);

		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPrefWidth(150);

		gp.getColumnConstraints().addAll(column1, column2);
		gp.setAlignment(Pos.CENTER);

		bp.setRight(gp);

		goButton.setOnAction(e -> {
			pathArea.clear();
			disField.clear();

			String source = sourceBoxCustom.getComboBox().getSelectionModel().getSelectedItem();
			String destination = desBoxCustom.getComboBox().getSelectionModel().getSelectedItem();

	        if (source == null || destination == null) {
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Warning");
	            alert.setHeaderText("Source or Destination Not Selected");
	            alert.setContentText("Please select both source and destination before proceeding.");
	            alert.showAndWait();
	            return;
	        }

	        if (source.equals(destination)) {
	            pathArea.setText("Source and destination are the same.");
	            disField.setText("0.0");
	            return;
	        }

			HeapNode result = graph.getResult(numberOfEdges, source, destination);

			Pane mapPane = (Pane) ((ScrollPane) tableBox.getChildren().get(0)).getContent();
			clearMapElements(mapPane); // Clear existing lines and plane

			if (result != null) {
				// Display path and distance
				LinkedList path = result.getPath();
				StringBuilder pathString = new StringBuilder();
				double totalDistance = result.getCost();

				if (!path.isEmpty()) {
					Edge firstEdge = path.getFirst();

					for (int i = 1; i < path.getSize(); i++) {
						Edge nextEdge = path.getNodeByIndex(i).getEdge();
						if (i == 1) {
							pathString.append("From ").append(firstEdge.getSource().getCountry().getCountryName())
							.append(" to ").append(nextEdge.getDestination().getCountry().getCountryName())
							.append(" with Distance ").append(String.format("%.2f", nextEdge.getCost()))
							.append("\n");
						}else {
							pathString.append("From ").append(nextEdge.getSource().getCountry().getCountryName())
							.append(" to ").append(nextEdge.getDestination().getCountry().getCountryName())
							.append(" with Distance ").append(String.format("%.2f", nextEdge.getCost()))
							.append("\n");

						}
						
						firstEdge = nextEdge;
					}
				}

				pathArea.setText(pathString.toString());
				disField.setText(String.format("%.2f", totalDistance));

				// Draw lines on the map
				drawPath(mapPane, path);
			} else {
				// No path found
				pathArea.setText("There is no path between " + source + " and " + destination);
				disField.setText("N/A");
			}
		});
	}

	private void drawPath(Pane mapPane, LinkedList path) {
		Path combinedPath = new Path();
		Edge previousEdge = null;
		LinkedListNode currentNode = path.getFirstNode();

		while (currentNode != null) {
			Edge edge = currentNode.getEdge();
			Country sourceCountry = edge.getSource().getCountry();
			Country destinationCountry = edge.getDestination().getCountry();

			if (previousEdge != null) {
				combinedPath.getElements().add(new LineTo(destinationCountry.getX(), destinationCountry.getY()));
			} else {
				combinedPath.getElements().add(new MoveTo(sourceCountry.getX(), sourceCountry.getY()));
				combinedPath.getElements().add(new LineTo(destinationCountry.getX(), destinationCountry.getY()));
			}
			previousEdge = edge;
			currentNode = currentNode.getNext();
		}

		previousEdge = null;
		currentNode = path.getFirstNode();
		while (currentNode != null) {
			Edge edge = currentNode.getEdge();
			Country sourceCountry = edge.getSource().getCountry();
			Country destinationCountry = edge.getDestination().getCountry();

			if (previousEdge != null) {
				Line line = new Line(sourceCountry.getX(), sourceCountry.getY(), destinationCountry.getX(),
						destinationCountry.getY());
				line.setStroke(Color.RED);
				line.setStrokeWidth(2);
				line.getStrokeDashArray().addAll(10d, 10d);
				line.setStrokeLineCap(StrokeLineCap.ROUND);
				mapPane.getChildren().add(line);
			}
			previousEdge = edge;
			currentNode = currentNode.getNext();
		}

		Image planeImage = new Image("Images/plane.png");
		ImageView planeImageView = new ImageView(planeImage);

		planeImageView.setFitWidth(30);
		planeImageView.setFitHeight(30);

		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.seconds(5));
		pathTransition.setPath(combinedPath);
		pathTransition.setNode(planeImageView);
		pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount(PathTransition.INDEFINITE);
		pathTransition.setAutoReverse(false);

		pathTransition.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
			double angle = calculateAngle(combinedPath,
					pathTransition.getCurrentTime().toMillis() / pathTransition.getCycleDuration().toMillis());
			planeImageView.setRotate(angle);
		});

		pathTransition.setOnFinished(event -> {
			pathTransition.jumpTo(Duration.ZERO);
			pathTransition.play();
		});

		pathTransition.play();

		mapPane.getChildren().add(planeImageView);
	}

	private double calculateAngle(Path path, double t) {
		t = Math.max(0, Math.min(1, t));

		ObservableList<PathElement> elements = path.getElements();
		int segmentIndex = (int) (t * (elements.size() - 1));

		if (segmentIndex < 0 || segmentIndex >= elements.size() - 1) {
			return 0; 
		}

		PathElement startElement = elements.get(segmentIndex);
		PathElement endElement = elements.get(segmentIndex + 1);

		double startX, startY, endX, endY;

		if (startElement instanceof MoveTo) {
			MoveTo moveTo = (MoveTo) startElement;
			startX = moveTo.getX();
			startY = moveTo.getY();
		} else if (startElement instanceof LineTo) {
			LineTo lineTo = (LineTo) startElement;
			startX = lineTo.getX();
			startY = lineTo.getY();
		} else {
			return 0;
		}

		if (endElement instanceof LineTo) {
			LineTo lineTo = (LineTo) endElement;
			endX = lineTo.getX();
			endY = lineTo.getY();
		} else {
			return 0;
		}

		double angle = Math.toDegrees(Math.atan2(endY - startY, endX - startX));
		return angle;
	}

	private ImageView firstSelectedFlag = null;
	private ImageView secondSelectedFlag = null;
	private String firstCountry = null;
	private String secondCountry = null;

	private ImageView getIcon(String image) {
		Glow glow = new Glow();
		glow.setLevel(0.5);

		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(0.2);
		colorAdjust.setContrast(0.0);
		colorAdjust.setSaturation(-0.1);
		colorAdjust.setHue(0.166);

		glow.setInput(colorAdjust);

		String imagePath = "locations/" + image.toLowerCase() + ".png";

		Image flagImage = null;

		try {
			flagImage = new Image(imagePath);
		} catch (Exception e) {
			flagImage = new Image("locations/unknown.png");

		}

		ImageView flagImageView = new ImageView(flagImage);

		final boolean[] isGreen = { false };

		flagImageView.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			if (!isGreen[0])
				flagImageView.setEffect(glow);
		});
		flagImageView.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if (!isGreen[0])
				flagImageView.setEffect(null);
		});

		flagImageView.setFitWidth(flagImage.getWidth() / 20);
		flagImageView.setFitHeight(flagImage.getHeight() / 20);

		ColorAdjust greenAdjust = new ColorAdjust();
		greenAdjust.setHue(0.5); // Adjust the hue to make the image green

		flagImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if (isGreen[0]) {
				flagImageView.setEffect(null); // Set back to normal

				// Remove from source or destination if already set
				if (sourceBoxCustom.getComboBox().getSelectionModel().getSelectedItem() != null
						&& sourceBoxCustom.getComboBox().getSelectionModel().getSelectedItem().equals(image)) {
					sourceBoxCustom.getComboBox().getSelectionModel().clearSelection();
					sourceBoxCustom.getComboBox().setPromptText("Enter Source");
					firstSelectedFlag = null;
					firstCountry = null;
				} else if (desBoxCustom.getComboBox().getSelectionModel().getSelectedItem() != null
						&& desBoxCustom.getComboBox().getSelectionModel().getSelectedItem().equals(image)) {
					desBoxCustom.getComboBox().getSelectionModel().clearSelection();
					desBoxCustom.getComboBox().setPromptText("Enter Destination");
					secondSelectedFlag = null;
					secondCountry = null;
				}

			} else {
				if (firstSelectedFlag == null) {
					flagImageView.setEffect(greenAdjust); // Set to green
					firstSelectedFlag = flagImageView;
					firstCountry = image;
					sourceBoxCustom.getComboBox().setValue(image);
				} else if (secondSelectedFlag == null) {
					flagImageView.setEffect(greenAdjust); // Set to green
					secondSelectedFlag = flagImageView;
					secondCountry = image;
					desBoxCustom.getComboBox().setValue(image);
				} else {
					// Replace the first selected flag
					firstSelectedFlag.setEffect(null); // Set back to normal
					sourceBoxCustom.getComboBox().getSelectionModel().clearSelection();
					sourceBoxCustom.getComboBox().setPromptText("Enter Source");

					firstSelectedFlag = secondSelectedFlag;
					firstCountry = secondCountry;
					sourceBoxCustom.getComboBox().setValue(firstCountry);

					flagImageView.setEffect(greenAdjust); // Set new one to green
					secondSelectedFlag = flagImageView;
					secondCountry = image;
					desBoxCustom.getComboBox().setValue(image);
				}
			}
			isGreen[0] = !isGreen[0]; // Toggle the state
		});

		return flagImageView;
	}
	
	private void clearMapElements(Pane mapPane) {
	    mapPane.getChildren().removeIf(node -> (node instanceof Line || node instanceof Path)
	            && !(node instanceof ImageView && ((ImageView) node).getImage().getUrl().contains("locations")));

	    mapPane.getChildren().removeIf(
	            node -> node instanceof ImageView && ((ImageView) node).getImage().getUrl().contains("plane"));
	}

}
