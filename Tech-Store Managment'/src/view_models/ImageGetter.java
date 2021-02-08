package view_models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageGetter {

	private ImageGetter() {}
	public static ImageView getImage(String path, double width, double height) {
		ImageView img = new ImageView(new Image(path));
		img.setFitWidth(width);
		img.setFitHeight(height);
		return img;
	}

}
