import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ShapeScene extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	Shape3D selected;
	Map<Shape3D, Boolean> shapes = new HashMap<Shape3D, Boolean>();
	ArrayList<Shape3D> listOfShapes = new ArrayList<Shape3D>();
	ArrayList<Rotate> rotates = new ArrayList<Rotate>();
	ArrayList<Scale> scales = new ArrayList<Scale>();
	ArrayList<Translate> translates = new ArrayList<Translate>();
	Slider rotateX = new Slider(0, 360, 0);
	Slider rotateY = new Slider(0, 360, 0);
	TextField scaleField = new TextField();
	ObservableList<Colors> colors = FXCollections.observableArrayList(
			new Colors("Gray", Color.GRAY),
			new Colors("Black", Color.BLACK),
			new Colors("Wheat", Color.WHEAT),
			new Colors("Red", Color.RED),
			new Colors("Blue", Color.BLUE),
			new Colors("White", Color.WHITE),
			new Colors("Aqua", Color.AQUA),
			new Colors("Deep Pink", Color.DEEPPINK),
			new Colors("Light Blue", Color.LIGHTBLUE),
			new Colors("Peru", Color.PERU)
			);
	ComboBox<Colors> shapeColorDrop = new ComboBox<Colors>(colors);
	
	public void start(Stage primaryStage) {
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");
		menuBar.getMenus().addAll(file);
		MenuItem save = new MenuItem("Save");
		MenuItem open = new MenuItem("Open");
		file.getItems().addAll(save, open);
		
		Translate zTranslate = new Translate(0, 0, -100);
		
//		Box box = new Box(10, 20, 10);
//		box.getTransforms().addAll(new Translate(-20, 0, 0));
//		Cylinder cylinder = new Cylinder(10, 20);
//		cylinder.getTransforms().addAll(new Translate(20, 0, 0));
//		Sphere sphere = new Sphere(10);
		
		PerspectiveCamera pCamera = new PerspectiveCamera(true);
		pCamera.setFieldOfView(45);
		pCamera.setFarClip(120);
		pCamera.getTransforms().addAll(zTranslate);
		
		
		Group shapesGroup = new Group();
		shapesGroup.getChildren().addAll();
		
		SubScene shapesSub = new SubScene(shapesGroup, 500, 500, true, SceneAntialiasing.DISABLED);
		shapesSub.setFill(Color.GRAY);
		shapesSub.setCamera(pCamera);
		
		//formScene
		Label label = new Label("Add A Shape");
		Button submit = new Button("Submit");
		Button back = new Button("Back");
		ComboBox<String> shapeDrop = new ComboBox<String>();
		shapeDrop.getItems().addAll("Sphere","Box","Cylinder");
		shapeDrop.getSelectionModel().selectFirst();
		
		Label lxs = new Label("X Position: ");
		Label lys = new Label("Y Position: ");
		Label lxb = new Label("X Position: ");
		Label lyb = new Label("Y Position: ");
		Label lxc = new Label("X Position: ");
		Label lyc = new Label("Y Position: ");
		Label lsradius = new Label("Radius: ");
		Label lcradius = new Label("Radius: ");
		Label lbwidth = new Label("Width: ");
		Label lbheight = new Label("Height: ");
		Label lblength = new Label("Length: ");
		Label lclength = new Label("Length: ");
		TextField xs = new TextField();
		TextField ys = new TextField();
		TextField xb = new TextField();
		TextField yb = new TextField();
		TextField sradius = new TextField();
		TextField bwidth = new TextField();
		TextField bheight = new TextField();
		TextField blength = new TextField();
		TextField clength = new TextField();
		TextField xc = new TextField();
		TextField yc = new TextField();
		TextField cradius = new TextField();
		ArrayList<TextField> atf = new ArrayList<TextField>();
		atf.add(xs);
		atf.add(ys);
		atf.add(xb);
		atf.add(yb);
		atf.add(xc);
		atf.add(yc);
		atf.add(xs);
		atf.add(sradius);
		atf.add(bwidth);
		atf.add(bheight);
		atf.add(blength);
		atf.add(clength);
		atf.add(cradius);
		
		//sphere grid pane
		GridPane gpSphere = new GridPane();
		gpSphere.add(lxs, 0, 0);
		gpSphere.add(xs, 1, 0);
		gpSphere.add(lys, 0, 1);
		gpSphere.add(ys, 1, 1);
		gpSphere.add(lsradius, 0, 2);
		gpSphere.add(sradius, 1, 2);
		gpSphere.setVgap(10);
		gpSphere.setHgap(10);
		gpSphere.setAlignment(Pos.CENTER);
		
		//box grid pane
		GridPane gpBox = new GridPane();
		gpBox.add(lxb, 0, 0);
		gpBox.add(xb, 1, 0);
		gpBox.add(lyb, 0, 1);
		gpBox.add(yb, 1, 1);
		gpBox.add(lbwidth, 0, 2);
		gpBox.add(bwidth, 1, 2);
		gpBox.add(lbheight, 0, 3);
		gpBox.add(bheight, 1, 3);
		gpBox.add(lblength, 0, 4);
		gpBox.add(blength, 1, 4);
		gpBox.setHgap(10);
		gpBox.setVgap(10);
		gpBox.setAlignment(Pos.CENTER);
		
		//cylinder grid pane
		GridPane gpCylinder = new GridPane();
		gpCylinder.add(lxc, 0, 0);
		gpCylinder.add(xc, 1, 0);
		gpCylinder.add(lyc, 0, 1);
		gpCylinder.add(yc, 1, 1);
		gpCylinder.add(lcradius, 0, 2);
		gpCylinder.add(cradius, 1, 2);
		gpCylinder.add(lclength, 0, 3);
		gpCylinder.add(clength, 1, 3);
		gpCylinder.setVgap(10);
		gpCylinder.setHgap(10);
		gpCylinder.setAlignment(Pos.CENTER);
		
		HBox hButton = new HBox(10, back, submit);
		hButton.setAlignment(Pos.CENTER);
		
		VBox form = new VBox(10, label, shapeDrop, gpSphere, hButton);
		form.setAlignment(Pos.CENTER);
		form.setPadding(new Insets(20));
		Scene formScene = new Scene(form, 600, 600);
		
		shapeDrop.getSelectionModel().selectedItemProperty().addListener((obs, o, n) ->{
			if (shapeDrop.getSelectionModel().getSelectedItem().equals("Sphere")) {
				form.getChildren().remove(2);
				form.getChildren().add(2, gpSphere);
			} else if (shapeDrop.getSelectionModel().getSelectedItem().equals("Box")) {
				form.getChildren().remove(2);
				form.getChildren().add(2, gpBox);
			} else if (shapeDrop.getSelectionModel().getSelectedItem().equals("Cylinder")) {
				form.getChildren().remove(2);
				form.getChildren().add(2, gpCylinder);
			}
		});
		//end formScene
		
		//Normal Scene
		Button addShape = new Button("Add Shape");
		addShape.setOnAction(event ->{
			primaryStage.setScene(formScene);
		});
		
		//dropdown of colors
		ComboBox<Colors> colorDrop = new ComboBox<Colors>(colors);
		colorDrop.getSelectionModel().selectFirst();
		colorDrop.getSelectionModel().selectedItemProperty().addListener((obs, o, n) ->{
			shapesSub.setFill(colorDrop.getSelectionModel().getSelectedItem().c);
		});
		
		//vSide Elements
		Label rotateLabel = new Label("Rotations");
		rotateLabel.setUnderline(true);
		Label rotateXLabel = new Label("X: ");
		Label rotateYLabel = new Label("Y: ");
		HBox rotateXbox = new HBox(rotateXLabel, rotateX);
		HBox rotateYbox = new HBox(rotateYLabel, rotateY);
		Label scaleLabel = new Label("Scale: ");
		scaleField.setPromptText("Ex. 1.5");
		HBox scaleBox = new HBox(scaleLabel, scaleField);
		scaleBox.setAlignment(Pos.CENTER);
		Label colorLabel = new Label("Color: ");
		shapeColorDrop.getSelectionModel().selectedItemProperty().addListener((obs, o, n) ->{
			ArrayList<Shape3D> shapeList = getArray(shapes);
			for(int i = 0; i < shapeList.size(); i++) {
				PhongMaterial material = new PhongMaterial();
				material.setDiffuseColor(shapeColorDrop.getSelectionModel().getSelectedItem().c);
				if (shapes.get(shapeList.get(i))) {
					shapeList.get(i).setMaterial(material);
				}
			}
		});
		
		rotateX.valueProperty().addListener((obs, o, n) -> {
			int index = listOfShapes.indexOf(selected);
			rotates.get(index*2).setAngle(rotateX.getValue());;
		});
		
		rotateY.valueProperty().addListener((obs, o, n) -> {
			int index = listOfShapes.indexOf(selected);
			rotates.get(index*2+1).setAngle(rotateY.getValue());;
		});
		
		scaleField.textProperty().addListener((obs, o, n) ->{
			try {
				double s = Double.parseDouble(scaleField.getText());
				int index = listOfShapes.indexOf(selected);
				scales.get(index).setX(s);
				scales.get(index).setY(s);
				scales.get(index).setZ(s);
			} catch (Exception e) {
				
			}
		});
		
		HBox colorBox = new HBox(colorLabel, shapeColorDrop);
		colorBox.setAlignment(Pos.CENTER_LEFT);
		
		//bottom hbox with dropdown and buttons
		HBox hBottom = new HBox(10, addShape, colorDrop);
		
		//vbox on the right
		VBox vSide = new VBox(10, rotateLabel, rotateXbox, rotateYbox, scaleBox, colorBox);
		
		//hbox with subscene and right toolbar
		HBox hbox = new HBox(10, shapesSub, vSide);
		
		//rootNode
		VBox vRoot = new VBox(10, hbox, hBottom);
		BorderPane bpRoot = new BorderPane();
		bpRoot.setTop(menuBar);
		bpRoot.setCenter(vRoot);
		vRoot.setAlignment(Pos.CENTER);
		vRoot.setPadding(new Insets(20));

		Scene myScene = new Scene(bpRoot);
		primaryStage.setTitle("Shape Scene Creator");
		primaryStage.setScene(myScene);
		primaryStage.show();
		
		save.setOnAction(event ->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Text File", "*.txt"));
			File savedFilePath = fileChooser.showSaveDialog(primaryStage);
			try {
				PrintWriter printWriter = new PrintWriter(savedFilePath);
				for (int i = 0; i < listOfShapes.size(); i++) {
					PhongMaterial pm = (PhongMaterial)listOfShapes.get(i).getMaterial();
					Color c = pm.getDiffuseColor();
					int indexOfColor = 0;
					for (int j = 0; j < colors.size(); j++) {
						if (colors.get(j).c.equals(c))
							indexOfColor = j;
					}
					String sphere = "";
					String box = "";
					String cylinder = "";
					if (listOfShapes.get(i).getClass().getSimpleName().equals("Sphere")) {
						Sphere s = (Sphere)listOfShapes.get(i);
						sphere = "," + s.getRadius();
						System.out.println("Sphere");
					} else if (listOfShapes.get(i).getClass().getSimpleName().equals("Box")) {
						Box b = (Box)listOfShapes.get(i);
						box = "," + b.getWidth() + "," + b.getHeight() + "," + b.getDepth();
						System.out.println("Box");
					} else if (listOfShapes.get(i).getClass().getSimpleName().equals("Cylinder")) {
						Cylinder cyl = (Cylinder)listOfShapes.get(i);
						cylinder = "," + cyl.getRadius() + "," + cyl.getHeight();
						System.out.println("Cylinder");
					}
					
					printWriter.println(
							listOfShapes.get(i).getClass().getSimpleName() + "," +
							translates.get(i).getX()  + "," +
							translates.get(i).getY()  + "," +
							rotates.get((i*2)).getAngle()  + "," +
							rotates.get((i*2)+1).getAngle()  + "," +
							scales.get(i).getX()  + "," +
							indexOfColor +
							sphere +
							box +
							cylinder
					);
				}
				printWriter.close();
			}
			catch (Exception e) {
				System.out.println("Error creating File.");
			}
		});
		
		open.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
			File openedFilePath = fileChooser.showOpenDialog(primaryStage);
			try {
				clearAll();
				Scanner sc = new Scanner(openedFilePath);
				shapesGroup.getChildren().clear();
				while (sc.hasNextLine()) {
					System.out.println("Once");
					String line = sc.nextLine();
					String[] lineArray = line.split(",");
						if (lineArray[0].equals("Sphere")) {
							Sphere s = new Sphere(Double.parseDouble(lineArray[7]));
							Translate t = new Translate(Double.parseDouble(lineArray[1]), Double.parseDouble(lineArray[2]), 0);
							translates.add(t);
							Rotate horizontalRotate = new Rotate(Double.parseDouble(lineArray[4]), Rotate.Y_AXIS);
							Rotate verticalRotate = new Rotate(Double.parseDouble(lineArray[3]), Rotate.X_AXIS);
							rotates.add(verticalRotate);
							rotates.add(horizontalRotate);
							Scale scale = new Scale(Double.parseDouble(lineArray[5]),Double.parseDouble(lineArray[5]),Double.parseDouble(lineArray[5]));
							scales.add(scale);
							s.getTransforms().addAll(t, horizontalRotate, verticalRotate, scale);
							PhongMaterial pm = new PhongMaterial();
							pm.setDiffuseColor(colors.get(Integer.parseInt(lineArray[6])).c);
							s.setMaterial(pm);
							clearShapes(shapes);
							shapes.put(s, true);
							shapesGroup.getChildren().addAll(s);
							listOfShapes.add(s);
							s.setOnMouseClicked(new Clicked(s));
							selected = s;
							rotateX.setValue(Double.parseDouble(lineArray[3]));
							rotateY.setValue(Double.parseDouble(lineArray[4]));
							scaleField.setText(lineArray[5]);
							Color c = colors.get(Integer.parseInt(lineArray[6])).c;
							int indexOfColor = 0;
							for (int j = 0; j < colors.size(); j++) {
								if (colors.get(j).c.equals(c))
									indexOfColor = j;
							}
							shapeColorDrop.getSelectionModel().select(indexOfColor);
						} else if (lineArray[0].equals("Box")) {
							Box b = new Box(Double.parseDouble(lineArray[7]),Double.parseDouble(lineArray[8]),Double.parseDouble(lineArray[9]));
							Translate t = new Translate(Double.parseDouble(lineArray[1]), Double.parseDouble(lineArray[2]), 0);
							translates.add(t);
							Rotate horizontalRotate = new Rotate(Double.parseDouble(lineArray[4]), Rotate.Y_AXIS);
							Rotate verticalRotate = new Rotate(Double.parseDouble(lineArray[3]), Rotate.X_AXIS);
							rotates.add(verticalRotate);
							rotates.add(horizontalRotate);
							Scale scale = new Scale(Double.parseDouble(lineArray[5]),Double.parseDouble(lineArray[5]),Double.parseDouble(lineArray[5]));
							scales.add(scale);
							b.getTransforms().addAll(t, horizontalRotate, verticalRotate, scale);
							PhongMaterial pm = new PhongMaterial();
							pm.setDiffuseColor(colors.get(Integer.parseInt(lineArray[6])).c);
							b.setMaterial(pm);
							clearShapes(shapes);
							shapes.put(b, true);
							shapesGroup.getChildren().addAll(b);
							listOfShapes.add(b);
							b.setOnMouseClicked(new Clicked(b));
							selected = b;
							rotateX.setValue(Double.parseDouble(lineArray[3]));
							rotateY.setValue(Double.parseDouble(lineArray[4]));
							scaleField.setText(lineArray[5]);
							Color c = colors.get(Integer.parseInt(lineArray[6])).c;
							int indexOfColor = 0;
							for (int j = 0; j < colors.size(); j++) {
								if (colors.get(j).c.equals(c))
									indexOfColor = j;
							}
							shapeColorDrop.getSelectionModel().select(indexOfColor);
						} else if (lineArray[0].equals("Cylinder")) {
							Cylinder cyl = new Cylinder();
							cyl.setRadius(Double.parseDouble(lineArray[7]));
							cyl.setHeight(Double.parseDouble(lineArray[8]));
							Translate t = new Translate(Double.parseDouble(lineArray[1]), Double.parseDouble(lineArray[2]), 0);
							translates.add(t);
							Rotate horizontalRotate = new Rotate(Double.parseDouble(lineArray[4]), Rotate.Y_AXIS);
							Rotate verticalRotate = new Rotate(Double.parseDouble(lineArray[3]), Rotate.X_AXIS);
							rotates.add(verticalRotate);
							rotates.add(horizontalRotate);
							Scale scale = new Scale(Double.parseDouble(lineArray[5]),Double.parseDouble(lineArray[5]),Double.parseDouble(lineArray[5]));
							scales.add(scale);
							cyl.getTransforms().addAll(t, horizontalRotate, verticalRotate, scale);
							PhongMaterial pm = new PhongMaterial();
							pm.setDiffuseColor(colors.get(Integer.parseInt(lineArray[6])).c);
							cyl.setMaterial(pm);
							clearShapes(shapes);
							shapes.put(cyl, true);
							shapesGroup.getChildren().addAll(cyl);
							listOfShapes.add(cyl);
							cyl.setOnMouseClicked(new Clicked(cyl));
							selected = cyl;
							rotateX.setValue(Double.parseDouble(lineArray[3]));
							rotateY.setValue(Double.parseDouble(lineArray[4]));
							scaleField.setText(lineArray[5]);
							Color c = colors.get(Integer.parseInt(lineArray[6])).c;
							int indexOfColor = 0;
							for (int j = 0; j < colors.size(); j++) {
								if (colors.get(j).c.equals(c))
									indexOfColor = j;
							}
							shapeColorDrop.getSelectionModel().select(indexOfColor);
						}
					}
				sc.close();
			} catch (Exception e){
				System.out.println("Error reading file");
			}
		});
		
		submit.setOnAction(event ->{
			if (shapeDrop.getSelectionModel().getSelectedItem().equals("Sphere")) {
				//cylinder.getTransforms().addAll(new Translate(20, 0, 0));
				Sphere sphere = new Sphere(Double.parseDouble(sradius.getText()));
				Translate trans = new Translate(Double.parseDouble(xs.getText()), Double.parseDouble(ys.getText()), 0);
				sphere.getTransforms().addAll(trans);
				translates.add(trans);
				
				PhongMaterial pm = new PhongMaterial();
				pm.setDiffuseColor(Color.WHITE);
				sphere.setMaterial(pm);
				
				shapesGroup.getChildren().addAll(sphere);
				
				Rotate horizontalRotate = new Rotate(0, Rotate.Y_AXIS);
				Rotate verticalRotate = new Rotate(0, Rotate.X_AXIS);
				Scale scaleTransform = new Scale(1,1,1);
				sphere.getTransforms().addAll(verticalRotate, horizontalRotate, scaleTransform);
				rotates.add(verticalRotate);
				rotates.add(horizontalRotate);
				scales.add(scaleTransform);

				selected = sphere;
				clearShapes(shapes);
				shapes.put(sphere, true);
				listOfShapes.add(sphere);
				
				sphere.setOnMouseClicked(new Clicked(sphere));
				
			} else if (shapeDrop.getSelectionModel().getSelectedItem().equals("Box")) {
				Box box = new Box();
				box.setHeight(Double.parseDouble(bheight.getText()));
				box.setDepth(Double.parseDouble(blength.getText()));
				box.setWidth(Double.parseDouble(bwidth.getText()));
				Translate trans = new Translate(Double.parseDouble(xb.getText()), Double.parseDouble(yb.getText()), 0);
				box.getTransforms().addAll(trans);
				translates.add(trans);
				
				PhongMaterial pm = new PhongMaterial();
				pm.setDiffuseColor(Color.WHITE);
				box.setMaterial(pm);
				
				shapesGroup.getChildren().addAll(box);
				
				Rotate horizontalRotate = new Rotate(0, Rotate.Y_AXIS);
				Rotate verticalRotate = new Rotate(0, Rotate.X_AXIS);
				Scale scaleTransform = new Scale(1,1,1);
				box.getTransforms().addAll(verticalRotate, horizontalRotate, scaleTransform);
				rotates.add(verticalRotate);
				rotates.add(horizontalRotate);
				scales.add(scaleTransform);
				
				selected = box;
				clearShapes(shapes);
				shapes.put(box, true);
				listOfShapes.add(box);
				
				box.setOnMouseClicked(new Clicked(box));
				
			} else if (shapeDrop.getSelectionModel().getSelectedItem().equals("Cylinder")) {
				Cylinder cylinder = new Cylinder();
				cylinder.setRadius(Double.parseDouble(cradius.getText()));
				cylinder.setHeight(Double.parseDouble(clength.getText()));
				Translate trans = new Translate(Double.parseDouble(xc.getText()), Double.parseDouble(yc.getText()), 0);
				cylinder.getTransforms().addAll(trans);
				translates.add(trans);
				shapesGroup.getChildren().addAll(cylinder);
				
				PhongMaterial pm = new PhongMaterial();
				pm.setDiffuseColor(Color.WHITE);
				cylinder.setMaterial(pm);
				
				Rotate horizontalRotate = new Rotate(0, Rotate.Y_AXIS);
				Rotate verticalRotate = new Rotate(0, Rotate.X_AXIS);
				Scale scaleTransform = new Scale(1,1,1);
				cylinder.getTransforms().addAll(verticalRotate, horizontalRotate, scaleTransform);
				rotates.add(verticalRotate);
				rotates.add(horizontalRotate);
				scales.add(scaleTransform);
				
				selected = cylinder;
				clearShapes(shapes);
				shapes.put(cylinder, true);
				listOfShapes.add(cylinder);
				
				cylinder.setOnMouseClicked(new Clicked(cylinder));
			}
			
			for (int i = 0; i < atf.size(); i++){
				atf.get(i).setText("");
			}
			shapeColorDrop.getSelectionModel().select(5);
			rotateX.setValue(0);
			rotateY.setValue(0);
			scaleField.setText("");
			primaryStage.setScene(myScene);
		});
		
		back.setOnAction(event ->{
			primaryStage.setScene(myScene);
		});
	}
	
	public class Clicked implements EventHandler<MouseEvent>{
		
		Shape3D shape;
		
		public Clicked(Shape3D shape) {
			this.shape = shape;
		}
		
		@Override
		public void handle(MouseEvent event) {
			clearShapes(shapes);
			shapes.replace(shape, true);
			selected = shape;
			System.out.println("Selected Shape " + shape.getClass().getSimpleName());
			//ArrayList<Shape3D> shapeList = getArray(shapes);
			int index = listOfShapes.indexOf(shape);
			int indexR = index*2;
			Material m = selected.getMaterial();
			PhongMaterial pm = (PhongMaterial)m;
			Color c = pm.getDiffuseColor();
			int indexColorDrop = 0;
			for (int i = 0; i < colors.size(); i++) {
				if (colors.get(i).c.equals(c))
					indexColorDrop = i;
			}
//			System.out.println("Rotation Array");
//			for(int i = 0; i < rotates.size(); i++) {
//				System.out.println("I: " + i + " Angle: " + rotates.get(i).getAngle() + " IndexR: " + indexR);
//			}
			//System.out.println("X: " + rotates.get(0).getAngle() + "  \nY: " + rotates.get(1).getAngle());
			//System.out.println("Index: " + indexR);
			//System.out.println("Index + 1: " + (index + 1));
			rotateX.setValue(rotates.get(indexR).getAngle());
			rotateY.setValue(rotates.get(indexR+1).getAngle());
			scaleField.setText(Double.toString(scales.get(index).getX()));
			shapeColorDrop.getSelectionModel().select(indexColorDrop);
//			for(int i = 0; i < rotates.size(); i++) {
//				System.out.println(rotates.get(i).getAngle());
//			}
			//System.out.println("X" + rotateX.getValue() + "Y" + rotateY.getValue());
		}
		
	}
	
	public void clearShapes(Map<Shape3D, Boolean> shapes) {
		Set<Shape3D> keySet = shapes.keySet();
		Object[] keyList = keySet.toArray();
		for (int i = 0; i < shapes.size(); i++) {
			shapes.replace((Shape3D)keyList[i], false);
		}
	}
	
	public ArrayList<Shape3D> getArray(Map<Shape3D, Boolean> shapes) {
		Set<Shape3D> keySet = shapes.keySet();
		Object[] keyList = keySet.toArray();
		ArrayList<Shape3D> shape3d = new ArrayList<Shape3D>();
		for (int i = 0; i < keyList.length; i++) {
			shape3d.add((Shape3D)keyList[i]);
		}
		return shape3d;
	}
	
	public void clearAll() {
		selected = null;
		shapes = new HashMap<Shape3D, Boolean>();
		listOfShapes = new ArrayList<Shape3D>();
		rotates = new ArrayList<Rotate>();
		scales = new ArrayList<Scale>();
		translates = new ArrayList<Translate>();
		rotateX.setValue(0);
		rotateY.setValue(0);
		scaleField.setText("");
		scaleField.setPromptText("Ex. 1.5");
	}
	
	class Colors{
		public String name;
		public Color c;
		
		public Colors(String name, Color color) {
			this.name = name;
			this.c = color;
		}
		
		public String toString() {
			return name;
		}
	}
}
