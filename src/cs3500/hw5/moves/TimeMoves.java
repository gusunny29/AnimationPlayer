package cs3500.hw5.moves;

import cs3500.hw5.shape.AbstractShape;

/**
 * Represents the class for keeping the fields of a shape constant while time continues to change.
 * INVARIANTS: the startTick is less than the endTick, both startTick and endTick are positive
 */
public class TimeMoves extends AbstractMove {

  /**
   * Class that will keep the fields constant over time.
   *
   * @param s         Shape that is at hand.
   * @param startTick starting tick value.
   * @param endTick   ending tick value.
   * @throws IllegalArgumentException The ending tick value cannot be less than the start value and
   *                                  the startTick cannot be non-positive.
   */
  public TimeMoves(AbstractShape s, int startTick, int endTick) throws IllegalArgumentException {
    /*
    if (endTick <= startTick || startTick < 1) {
      throw new IllegalArgumentException("The given tickEnd value must be larger than " +
              "the given tickStart value!");
    }

     */

    toActUpon = s;
    this.startTick = startTick;
    this.currentPosition = toActUpon.getCurrentPosition();
    this.startW = toActUpon.getWidth();
    this.startH = toActUpon.getHeight();
    this.startR = toActUpon.getR();
    this.startG = toActUpon.getG();
    this.startB = toActUpon.getB();

    this.endTick = endTick;
    this.endPosition = toActUpon.getCurrentPosition();
    this.endW = toActUpon.getWidth();
    this.endH = toActUpon.getHeight();
    this.endR = toActUpon.getR();
    this.endG = toActUpon.getG();
    this.endB = toActUpon.getB();
  }


  @Override
  public void updateValues() {
    //do nothing here because there are no values to update regarding the shape because
    //time is not associated with shape
  }
}
