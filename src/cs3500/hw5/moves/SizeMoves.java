package cs3500.hw5.moves;

import cs3500.hw5.shape.AbstractShape;

/**
 * Represents the class for changing the size of a shape. INVARIANTS: the startTick is less than the
 * endTick, both startTick and endTick are positive, the width and height are positive.
 */
public class SizeMoves extends AbstractMove {

  /**
   * Function that changes the size of a shape.
   *
   * @param s         shape that is meant to be changed
   * @param width     width that should be changed to
   * @param height    height that should be changed to
   * @param startTick Starting tick value
   * @param endTick   ending tick value
   */
  public SizeMoves(AbstractShape s, int startWidth, int startHeight, int width, int height,
      int startTick, int endTick) {

    if (endTick <= startTick || startTick < 1) {
      throw new IllegalArgumentException("The given tickEnd value must be larger than " +
          "the given tickStart value!");
    }

    if (width < 1 || height < 1) {
      throw new IllegalArgumentException("The given width and height cannot be negative!");
    }

    toActUpon = s;
    this.startTick = startTick;
    this.currentPosition = toActUpon.getCurrentPosition();
    this.startW = startWidth;
    this.startH = startHeight;
    this.startR = toActUpon.getR();
    this.startG = toActUpon.getG();
    this.startB = toActUpon.getB();

    this.endTick = endTick;
    this.endPosition = toActUpon.getCurrentPosition();
    this.endW = width;
    this.endH = height;
    this.endR = toActUpon.getR();
    this.endG = toActUpon.getG();
    this.endB = toActUpon.getB();


  }

  @Override
  public void updateValues() {
    toActUpon.setWidth(endW);
    toActUpon.setHeight(endH);
  }
}
