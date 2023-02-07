package cs3500.hw5.shape;



import cs3500.hw5.Posn;
import java.awt.Graphics;

/**
 * Represents a type of Shape. Is defined by its radius.
 * INVARIANTS: the values for the position of the Circle will never be negative, the radius will
 * always be positive, the RGB values added together will never be negative, any of the RGB
 * values will never be greater than 255, the sum of the RGB values equal 255, the appearance
 * will never be non-positive.
 */
public class Circle extends AbstractShape {

  /**
   * Constructs a {@code Shape} object. All values are specified to be the same as the given ones.
   *
   * @param name            the name of this Circle
   * @param radius          the radius of the Circle
   * @param currentPosition the current position of the Circle
   * @param r               the redness of the Circle
   * @param g               the greenness of the Circle
   * @param b               the blueness of the Circle
   * @param appearance      the time of appearance of the Circle
   * @throws IllegalArgumentException if the given width or height is non-positive
   * @throws IllegalArgumentException if given r g b values are either greater than 255, less than
   *                                  0, or do not add up to 255
   * @throws IllegalArgumentException if the given currentPosition or
   *                                  currentDestination have fields that are negative
   * @throws IllegalArgumentException if given a non-positive time for appearance
   */

  public Circle(String name, Posn currentPosition, int radius, int r, int g, int b,
                int appearance) throws IllegalArgumentException {
    //checks to make sure the inputs are valid
    checkConstructorExceptions(width, height, r, g, b, appearance, currentPosition);

    if (radius < 1) {
      throw new IllegalArgumentException("The given radius must be positive!");
    }

    this.name = name;

    this.currentPosition = currentPosition;

    this.r = r;

    this.g = g;

    this.b = b;

    this.width = 2 * radius;

    this.height = 2 * radius;

    this.appearanceTime = appearance;
  }

  /**
   * Client calls this to create a circle of the given name with default values.
   * @param name the name of the circle object
   */
  public Circle(String name) {
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
    return new Circle(name);
  }
}
