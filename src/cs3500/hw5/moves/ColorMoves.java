package cs3500.hw5.moves;


import cs3500.hw5.shape.AbstractShape;

/**
 * The move which represents changing colors within a shape.
 * INVARIANTS: the startTick is less than the endTick, both startTick and endTick are positive, the
 * r value is between 0 and 255 inclusive, the g value is between 0 and 255 inclusive, the
 * b value is between 0 and 255 inclusive, and the sum of the RGB values is 255
 */
public class ColorMoves extends AbstractMove {

  /**
   * Function to change the color of a shape.
   * @param s Shape at hand
   * @param r Red value
   * @param g Green value
   * @param b Blue value
   * @param startTick Desired Starting tick
   * @param endTick Desired end tick
   */
  public ColorMoves(AbstractShape s, int startR, int startG, int startB, int r, int g, int b,
                    int startTick, int endTick) {

    if (endTick <= startTick || startTick < 1) {
      throw new IllegalArgumentException("The given tickEnd value must be larger than " +
              "the given tickStart value!");
    }

    if (r + g + b != 255 || r < 0 || g < 0 || b < 0 || r > 255 || g > 255 || b > 255) {
      throw new IllegalArgumentException("The given rgb values are invalid!");
    }

    toActUpon = s;
    this.startTick = startTick;
    this.currentPosition = toActUpon.getCurrentPosition();
    this.startW = toActUpon.getWidth();
    this.startH = toActUpon.getHeight();
    this.startR = startR;
    this.startG = startG;
    this.startB = startB;

    this.endTick = endTick;
    this.endPosition = toActUpon.getCurrentPosition();
    this.endW = toActUpon.getWidth();
    this.endH = toActUpon.getHeight();
    this.endR = r;
    this.endG = g;
    this.endB = b;
  }

  @Override
  public void updateValues() {
    toActUpon.setR(this.endR);
    toActUpon.setG(this.endG);
    toActUpon.setB(this.endB);
  }
}
