package cs3500.hw5.shape;

import cs3500.hw5.Posn;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Represents a specific type of shape. All three sides are the same length and the equilateral
 * triangle is defined by its side length.
 * INVARIANTS: the values for the position of the EquilateralTriangle will never be negative,
 * the side length will always be positive, the RGB values added together will never be negative,
 * any of the RGB values will never be greater than 255, the sum of the RGB values equal 255,
 * the appearance will never be non-positive.
 */
public class EquilateralTriangle extends AbstractShape {

  /**
   * Constructs a {@code Shape} object. All values are specified to be the same as the given ones.
   *
   * @param name            the name of this triangle
   * @param sideLength      the length of one side of a triangle
   * @param currentPosition the current position of the triangle
   * @param r               the redness of the triangle
   * @param g               the greenness of the triangle
   * @param b               the blueness of the triangle
   * @param appearance      the time of appearance of the triangle
   * @throws IllegalArgumentException if the given width or height is non-positive
   * @throws IllegalArgumentException if given r g b values are either greater than 255, less than
   *                                  0, or do not add up to 255
   * @throws IllegalArgumentException if the given currentPosition has fields ```that are negative
   * @throws IllegalArgumentException if given a non-positive time for appearance
   */

  public EquilateralTriangle(String name, Posn currentPosition, int sideLength, int r, int g, int b,
                int appearance) throws IllegalArgumentException {
    //checks to make sure the inputs are valid
    checkConstructorExceptions(width, height, r, g, b, appearance, currentPosition);

    //throw exception if given side length is non-positive
    if (sideLength < 1) {
      throw new IllegalArgumentException("The given sideLength must be positive!");
    }

    this.name = name;

    this.currentPosition = currentPosition;

    this.width = sideLength;

    this.height = (int)(Math.sqrt(3) / 2) * sideLength;

    this.r = r;

    this.g = g;

    this.b = b;

    this.appearanceTime = appearance;
  }

  /**
   * Client calls this to create a EquilateralTriangle of the given name with default values.
   * @param name the name of the EquilateralTriangle object
   */
  public EquilateralTriangle(String name) {
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

    super.draw(g);

    Graphics2D g2 = (Graphics2D) g;

    //g2.draw;
  }

  @Override
  public AbstractShape shapeFactory(String name, String type) {
    return new EquilateralTriangle(name);
  }
}
