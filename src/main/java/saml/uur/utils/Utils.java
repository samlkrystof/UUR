package saml.uur.utils;

import javafx.beans.property.IntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.converter.NumberStringConverter;

/******************************************************************************
 * Instances of class saml.uur.utils.Utils are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class Utils {

    //== CONSTANT CLASS ATTRIBUTES =============================================
    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 150;

    //== VARIABLE CLASS ATTRIBUTES =============================================

    public static Images images = new Images();


    //== PUBLIC CLASS METHODS ==================================================

    public static void bindControl(IntegerProperty property, TextField textField, Slider slider) {
        slider.valueProperty().bindBidirectional(property);
        TextFormatter<Number> formatter = new TextFormatter<>(new NumberStringConverter(),1);
        textField.setTextFormatter(formatter);
        formatter.valueProperty().bindBidirectional(property);
    }

    public static ImageView getImageView(Image image) {
        ImageView result = new ImageView(image);
        result.setPreserveRatio(true);
        result.setFitHeight(15);
        return result;
    }

    /**
     *
     * @param first first point
     * @param second second point
     * @return double array with 4 elements, first is x coordinate of start, second is y
     * coordinate of the start, third is width, fourth is height
     */
    public static double[] calculateDimensions(Point2D first, Point2D second) {
        double[] result = new double[4];
        if (first.getX() < second.getX() && first.getY() < second.getY()) {
            result[0] = first.getX();
            result[1] = first.getY();
            result[2] = second.getX() - first.getX();
            result[3] = second.getY() - first.getY();
        } else if (first.getX() < second.getX() && first.getY() > second.getY()) {
            result[0] = first.getX();
            result[1] = second.getY();
            result[2] = second.getX() - first.getX();
            result[3] = first.getY() - second.getY();
        } else if (first.getX() > second.getX() && first.getY() < second.getY()) {
            result[0] = second.getX();
            result[1] = first.getY();
            result[2] = first.getX() - second.getX();
            result[3] = second.getY() - first.getY();
        } else {
            result[0] = second.getX();
            result[1] = second.getY();
            result[2] = first.getX() - second.getX();
            result[3] = first.getY() - second.getY();
        }
        return result;
    }

    /** check if this point is in the image */
    public static boolean checkBorders(int x, int y, WritableImage image) {
        return x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight();
    }

    public static double computeLengthOfLine(final Point2D first, final Point2D second) {

        final double xDifference = first.getX() - second.getX();
        final double yDifference = first.getY() - second.getY();

        return Math.sqrt(xDifference * xDifference + yDifference * yDifference);
    }

}
