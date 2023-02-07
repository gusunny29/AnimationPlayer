package cs3500.hw5.shape;


import cs3500.hw5.Posn;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Represents a specific type of shape. Is defined by its height and width. INVARIANTS: the values
 * for the position of the Rectange will never be negative, height and width will always be
 * positive, the RGB values added together will never be negative, any of the RGB values will never
 * be greater than 255, the sum of the RGB values equal 255, the appearance will never be
 * non-positive.
 */
public class Rectangle extends AbstractShape {

  /**
   * Constructs a {@code Shape} object. All values are specified to be the same as the given ones.
   *
   * @param name            the name of this rectangle
   * @param width           Width of the rectangle
   * @param height          Height of the rectangle
   * @param currentPosition the current position of the rectangle
   * @param r               the redness of the rectangle
   * @param g               the greenness of the rectangle
   * @param b               the blueness of the rectangle
   * @param appearance      the time of appearance of the rectangle
   * @throws IllegalArgumentException if the given width or height is non-positive
   * @throws IllegalArgumentException if given r g b values are either greater than 255, less than
   *                                  0, or do not add up to 255
   * @throws IllegalArgumentException if the given currentPosition has fields that are negative
   * @throws IllegalArgumentException if given a non-positive time for appearance
   */

  public Rectangle(String name, Posn currentPosition, int width, int height, int r, int g, int b,
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
   * Client calls this to create a Rectangle of the given name with default values.
   *
   * @param name the name of the Rectangle object
   */
  public Rectangle(String name) {
    this.name = name;
    this.currentPosition = new Posn(100, 100);
    this.r = 0;
    this.g = 255;
    this.b = 0;
    this.width = 25;
    this.height = 25;
    this.appearanceTime = 32;
  }

  /**
   * Client calls this to create a Rectangle of the given name with default values.
   * Takes in the name and the appearnce time.
   *
   * @param name the name of the Rectangle object
   * @param appearance the appearance time of the shape
   */
  public Rectangle(String name, int appearance) {
    this.name = name;
    this.currentPosition = new Posn(100, 100);
    this.r = 0;
    this.g = 255;
    this.b = 0;
    this.width = 25;
    this.height = 25;
    this.appearanceTime = appearance;
  }

  @Override
  public void draw(Graphics g) {
    //super.draw(g);

    Graphics2D g2 = (Graphics2D) g;

    g2.fillRect(currentPosition.getX(), currentPosition.getY(), width, height);
  }

  @Override
  public AbstractShape shapeFactory(String name, String type) {
    return new Rectangle(name);
  }
}
