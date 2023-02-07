package cs3500.hw5.shape;


import cs3500.hw5.Posn;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Represents a Shape. Shapes contain number of sides, integer values for location, and angle of
 * direction. INVARIANTS: The animation speed is non-negative, the startTick is non-negative, the
 * lastTick is non-negative, an AbstractMove inside of moves contains a Shape inside of shapes, and
 * the size of shapes must be greater than zero if the size of moves is greater than zero.
 */
public abstract class AbstractShape extends JPanel implements IShape {

  //represents the number of sides for this shape, cannot be changed once instantiated
  protected String name;

  //the width of the shape
  protected int width;

  //the height of the shape
  protected int height;

  //represents the current position of this shape
  //(located at average of width and height for this shape)
  protected Posn currentPosition;

  //represents the redness of the shape
  protected int r;

  //represents the greenness of the shape
  protected int g;

  //represents the blueness of the shape
  protected int b;

  //represents the time at which the Shape appears
  protected int appearanceTime;

  //throws an exception if any of the given parameters are not valid
  protected void checkConstructorExceptions(int width, int height, int r, int g, int b,
      int appearance, Posn currentPosition) throws IllegalArgumentException {

    //throw exception if given colors are not valid
    areColorsValid(r, g, b);

    //throw exception if given width/height are not valid
    areDimensionsValid(width, height);

    //throw exception if either of the given positions have fields that are negative
    arePosnsValid(currentPosition);

    //throw exception if the given start time is non-positive
    isTimeValid(appearance);
  }

  //throw IAE if given appearance times is non-positive
  private void isTimeValid(int appearance) {
    if (appearance < 0) {
      throw new IllegalArgumentException("The given appearance times for this shape cannot be" +
          "non-positive!");
    }
  }

  //throw exception if either of the given positions have fields that are negative
  protected void arePosnsValid(Posn currentPosition) {
    if (currentPosition.getX() < 0 || currentPosition.getY() < 0) {
      throw new IllegalArgumentException("At least one of the given Posns have a " +
          "negative field value!");
    }
  }

  //throws an exception if the given colors are invalid
  protected void areColorsValid(int r, int g, int b) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("The int values for color must be " +
          "between 0 and 255 inclusive!");
    }
  }

  //a method that throws an exception if either the given width is wider than the screen
  //or the given height is taller than the screen
  protected void areDimensionsValid(int w, int h) {
    if (w < 0 || h < 0) {
      throw new IllegalArgumentException("The height and/or width of this Shape must be positive!");
    }
  }

  @Override
  public void draw(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    g2.setPaint(new Color(this.r, this.g, this.b));
  }

  @Override
  public int getR() {
    return r;
  }

  @Override
  public int getG() {
    return g;
  }

  @Override
  public int getB() {
    return b;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  public Posn getCurrentPosition() {
    return currentPosition;
  }

  public int getAppearanceTime() {
    return appearanceTime;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setCurrentPosition(Posn currentPosition) {
    this.currentPosition = currentPosition;
  }

  public void setR(int r) {
    this.r = r;
  }

  public void setG(int g) {
    this.g = g;
  }

  public void setB(int b) {
    this.b = b;
  }

  public void setAppearanceTime(int appearanceTime) {
    this.appearanceTime = appearanceTime;
  }

  public abstract AbstractShape shapeFactory(String name, String type);
}
