package ProjectThree;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class EncancedComboBox extends ComboBox<String> {
    private ComboBox<String> comboBox = new ComboBox<>();

    public EncancedComboBox(String prompetText) {
        comboBox.setPromptText(prompetText);
        comboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> p) {
                return new ListCell<String>() {
                    private final HBox hBox;

                    {
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        hBox = new HBox();
                        hBox.setPrefWidth(207);
                        hBox.setPrefHeight(35);
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(0, 0, 0, 8));
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            // Check if the image file exists, if not, use "unknown.png"
                            String imagePath = "flags/" + item.toLowerCase() + ".png";
                            ImageView image = null;
                            try {
                                image = new ImageView(new Image(imagePath));
							} catch (Exception e) {
                                image = new ImageView(new Image("flags/unknown.png"));

							}
                            image.setFitHeight(33);
                            image.setFitWidth(33);
                            Label st = new Label(item);
                            st.setPadding(new Insets(5, 0, 0, 0));
                            hBox.getChildren().clear();
                            hBox.getChildren().addAll(image, st);
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });
    }

    public void addCountry(String country) {
        comboBox.getItems().add(country);
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }
}
