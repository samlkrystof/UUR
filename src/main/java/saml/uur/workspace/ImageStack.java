package saml.uur.workspace;

import javafx.scene.image.Image;

/******************************************************************************
 * Instances of class saml.uur.workspace.ImageStack are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class ImageStack {

    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    private Link first;
    private Link last;
    private Link current;

    private int total = 0;

    //== ACCESS METHODS OF INSTANCES ===========================================
    public Image getImage() {
        return current.image;
    }

    //== PUBLIC METHODS OF INSTANCES ===========================================
    public void addImageToStack(Image image) {
        Link newLink = new Link(image);
        if (first == null) {
            first = newLink;
            last = newLink;
        } else {
            newLink.next = first;
            first.previous = newLink;
            first = newLink;
        }
        current = newLink;
        total++;
        if (total > 20) {
            total--;
            last = last.previous;
            last.next = null;
        }
    }

    public void increment() {
        if (current.next != null) {
            current = current.next;
        }
    }

    public void decrement() {
        if (current.previous != null) {
            current = current.previous;
        }
    }

}

/**
 * representation of one piese of linked list
 */
class Link {
    public Link previous;
    public Link next;
    public Image image;

    public Link(Image image) {
        this.image = image;
    }
}
