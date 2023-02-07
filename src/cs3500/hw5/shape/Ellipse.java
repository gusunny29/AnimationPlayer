package cs3500.hw5.shape;



import cs3500.hw5.Posn;
import java.awt.Graphics;

/**
 * Represents a type of Shape. Is defined by its width and height. INVARIANTS: the values for the
 * position of the Ellipse will never be negative, the width and height will always be positive, the
 * RGB values added together will never be negative, any of the RGB values will never be greater
 * than 255, the sum of the RGB values equal 255, the appearance will never be non-positive.
 */
public class Ellipse extends AbstractShape {

  /**
   * Constructs a {@code Shape} object. All values are specified to be the same as the given ones.
   *
   * @param name            the name of this ellipse
   * @param width           The width of the ellipse
   * @param height          The height of the ellipse
   * @param currentPosition the current position of the ellipse
   * @param r               the redness of the ellipse
   * @param g               the greenness of the ellipse
   * @param b               the blueness of the ellipse
   * @param appearance      the time of appearance of the ellipse
   * @throws IllegalArgumentException if the given width or height is non-positive
   * @throws IllegalArgumentException if given r g b values are either greater than 255, less than
   *                                  0, or do not add up to 255
   * @throws IllegalArgumentException if the given currentPosition has fields that are negative
   * @throws IllegalArgumentException if given a non-positive time for appearance
   */

  public Ellipse(String name, Posn currentPosition, int width, int height, int r, int g, int b,
                 int appearance) throws IllegalArgumentException {
    //checks to make sure the inputs are valid
    checkConstructorExceptions(width, height, r, g, b, appearance, currentPosition);

    this.name = name;

    this.currentPosition = currentPosition;

    this.r = r;

    this.g = g;

    this.b = b;

    this.width = width;

    this.height = height;

    this.appearanceTime = appearance;
  }

  /**
   * Client calls this to create a Ellipse of the given name with default values.
   * @param name the name of the Ellipse object
   */
  public Ellipse(String name) {
    this.name = name;
    this.currentPosition = new Posn(0, 0);
    this.r = 255;
    this.g = 0;
    this.b = 0;
    this.width = 25;
    this.height = 25;
    this.appearanceTime = 1;
  }

  @Override
  public void draw(Graphics g) {

    //super.draw(g);

    g.fillOval(this.currentPosition.getX(), this.currentPosition.getY(), width, height);
  }

  @Override
  public AbstractShape shapeFactory(String name, String type) {
    return new Ellipse(name);
  }
}
