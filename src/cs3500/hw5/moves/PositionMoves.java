package cs3500.hw5.moves;

import cs3500.hw5.Posn;
import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.shape.AbstractShape;

/**
 * Represents the position move possibilities for shapes. INVARIANTS: the startTick is less than the
 * endTick, both startTick and endTick are positive, the x and y coordinates of the position are
 * non-negative.
 */
public class PositionMoves extends AbstractMove {

  /**
   * The function that moves positions for a shape.
   *
   * @param s         the shape that is needed to be moved.
   * @param position  position to be moved to.
   * @param startTick Starting tick value.
   * @param endTick   Ending tick value.
   */
  public PositionMoves(AbstractShape s, Posn startPosition, Posn position, int startTick,
      int endTick) {

    if (endTick <= startTick || startTick < 1) {
      throw new IllegalArgumentException("The given tickEnd value must be larger than " +
          "the given tickStart value!");
    }

    if (position.getX() < 0 || position.getY() < 0) {
      throw new IllegalArgumentException("The given position cannot be negative!");
    }

    toActUpon = s;
    this.startTick = startTick;
    this.currentPosition = startPosition;
    this.startW = toActUpon.getWidth();
    this.startH = toActUpon.getHeight();
    this.startR = toActUpon.getR();
    this.startG = toActUpon.getG();
    this.startB = toActUpon.getB();

    this.endTick = endTick;
    this.endPosition = position;
    this.endW = toActUpon.getWidth();
    this.endH = toActUpon.getHeight();
    this.endR = toActUpon.getR();
    this.endG = toActUpon.getG();
    this.endB = toActUpon.getB();

  }


  @Override
  public void updateValues() {
    toActUpon.setCurrentPosition(endPosition);
    int x = endPosition.getX();
    int y = endPosition.getY();

  }
}
