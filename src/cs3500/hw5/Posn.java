package cs3500.hw5;

/**
 * Represents a 2D location with an x coordinate and a y coordinate. Both x and y coordinate
 * given cannot be negative. INVARIANT: both x and y are not negative.
 */
public class Posn {

  //represents the coordinates of the given Posn
  private final int x;
  private final int y;

  /**
   * Constructs a {@code Posn} with default coordinates of 0, 0.
   */
  public Posn() {
    this(0, 0);
  }

  /**
   * Constructs a {@code Posn} with coordinates at the given values.
   */
  public Posn(int x, int y) {
    /*
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Neither of the given x and y coordinates can" +
              " be negative!");
    }

     */

    this.x = x;
    this.y = y;
  }

  /**
   * Returns the value of the x field of this Posn. Need it in order to be able to determine
   * if the given Posn has an x value off the screen.
   * @return the x coordinate of this Posn
   */
  public int getX() {
    return x;
  }

  /**
   * Returns the value of the y field of this Posn. Need it in order to be able to determine
   * if the given Posn has a y value off the screen.
   * @return the y coordinate of this Posn
   */
  public int getY() {
    return y;
  }
}
