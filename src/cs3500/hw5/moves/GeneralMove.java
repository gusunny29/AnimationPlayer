package cs3500.hw5.moves;

import cs3500.hw5.Posn;
import cs3500.hw5.shape.AbstractShape;

/**
 * Represents a general move class. Takes every start and end value for input as part of the
 * constructor.
 */
public class GeneralMove extends AbstractMove {

  private String shapeName;

  /**
   * Adds a movement to the animation.
   *
   * @param s  The name of the shape (added with
   * {@link cs3500.animator.util.AnimationBuilder#declareShape})
   * @param t1 The start time of this transformation
   * @param x1 The initial x-position of the shape
   * @param y1 The initial y-position of the shape
   * @param w1 The initial width of the shape
   * @param h1 The initial height of the shape
   * @param r1 The initial red color-value of the shape
   * @param g1 The initial green color-value of the shape
   * @param b1 The initial blue color-value of the shape
   * @param t2 The end time of this transformation
   * @param x2 The final x-position of the shape
   * @param y2 The final y-position of the shape
   * @param w2 The final width of the shape
   * @param h2 The final height of the shape
   * @param r2 The final red color-value of the shape
   * @param g2 The final green color-value of the shape
   * @param b2 The final blue color-value of the shape
   */
  public GeneralMove(AbstractShape s, int t1, int x1, int y1, int w1, int h1, int r1, int g1,
      int b1,
      int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {

    toActUpon = s;

    this.startTick = t1;
    this.currentPosition = new Posn(x1, y1);
    startW = w1;
    startH = h1;
    startR = r1;
    startG = g1;
    startB = b1;
    endTick = t2;
    endPosition = new Posn(x2, y2);
    endW = w2;
    endH = h2;
    endR = r2;
    endG = g2;
    endB = b2;

    toActUpon.setCurrentPosition(new Posn(x2, y2));
    toActUpon.setWidth(w2);
    toActUpon.setHeight(h2);
    toActUpon.setR(r2);
    toActUpon.setG(g2);
    toActUpon.setB(b2);


  }

  @Override
  public void updateValues() {
    toActUpon.setCurrentPosition(endPosition);
    toActUpon.setWidth(endW);
    toActUpon.setHeight(endH);
    toActUpon.setR(endR);
    toActUpon.setG(endG);
    toActUpon.setB(endB);
  }
}
