package cs3500.hw5.shape;



import cs3500.hw5.Posn;
import java.awt.Graphics;

/**
 * Represents all the methods associated with a Shape. Draws the shape.
 */
public interface IShape {

  /**
   * Client calls this when they wish to draw the given shape.
   *
   * @param g the Graphics object to draw on
   */
  void draw(Graphics g);

  /**
   * Client calls this when they want to obtain the value for r.
   *
   * @return the redness of the shape
   */
  int getR();

  /**
   * Client calls this when they want to obtain the value for g.
   *
   * @return the greenness of the shape
   */
  int getG();

  /**
   * Client calls this when they want to obtain the value for b.
   *
   * @return the blueness of the shape
   */
  int getB();

  /**
   * Client calls this to get the name of the Shape.
   *
   * @return a String representing the name of this shape
   */
  String getName();

  /**
   * Client calls this to get the width of the Shape.
   *
   * @return an int representing the width of this shape
   */
  int getWidth();

  /**
   * Client calls this to get the height of the Shape.
   *
   * @return an int representing the height of this shape
   */
  int getHeight();

  /**
   * Client calls this to get the current position of this shape.
   *
   * @return a Posn representing the current position of this shape
   */
  Posn getCurrentPosition();

  /**
   * Client calls this to get the appearance time of the Shape.
   *
   * @return an int representing the appearance time of this shape
   */
  int getAppearanceTime();


  /**
   * Client calls this when they want to set the value for r.
   */
  void setR(int r) throws IllegalArgumentException;

  /**
   * Client calls this when they want to set the value for g.
   */
  void setG(int g) throws IllegalArgumentException;

  /**
   * Client calls this when they want to set the value for b.
   */
  void setB(int b) throws IllegalArgumentException;

  /**
   * Client calls this to set the name of the Shape.
   */
  void setName(String name);

  /**
   * Client calls this to set the width of the Shape.
   */
  void setWidth(int width);

  /**
   * Client calls this to set the width of the Shape.
   */
  void setHeight(int height);

  /**
   * Client calls this to set the current position of this shape.
   */
  void setCurrentPosition(Posn position);

  /**
   * Client calls this to set the appearance time of the Shape.
   */
  void setAppearanceTime(int time) throws IllegalArgumentException;

  /**
   * Client calls this when they want to add a new Shape to the list of current Shapes
   * by giving only a name and a String representing the type of shape.
   * @param name the name of the shape
   * @param type the type of the shape
   * @return the created Shape
   */
  AbstractShape shapeFactory(String name, String type);

}
