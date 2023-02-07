package cs3500.hw5.moves;

import cs3500.hw5.Posn;
import cs3500.hw5.shape.AbstractShape;

/**
 * Represents the different possible moves for a Shape. Can either change color, size or position.
 * INVARIANTS: startTick is non-negative, currentPosition has non-negative fields, startW is
 * non-negative, startH is non-negative, startR is non-negative, startG is non-negative, startB is
 * non-negative, endTick is non-negative, endPosition has non-negative fields, endW is non-negative,
 * endH is negative is non-negative, endR is non-negative, endG is non-negative, endB is
 * non-negative
 */
public abstract class AbstractMove implements IMove {

  //The shape that is acted on
  protected AbstractShape toActUpon;
  //Starting tick
  protected int startTick;
  //The Current Position represented by x,y
  protected Posn currentPosition;
  //Starting width
  protected int startW;
  //Staring Height
  protected int startH;
  //Starting Red value
  protected int startR;
  //Staring Green Value
  protected int startG;
  //Starting Blue value
  protected int startB;

  //End Tick
  protected int endTick;
  //Position wanted to be ended at
  protected Posn endPosition;
  //Desired Width to be changed to
  protected int endW;
  //height to be changed to
  protected int endH;
  //Color change for red
  protected int endR;
  //Color change for green
  protected int endG;
  //Color change for blue
  protected int endB;


  @Override
  public String getMoveSummary() {
    String firstHalf = "motion " + toActUpon.getName() + " " + startTick + " "
        + toActUpon.getCurrentPosition().getX() + " " + toActUpon.getCurrentPosition().getY() + " "
        + toActUpon.getWidth() + " " + toActUpon.getHeight() + " " + toActUpon.getR() + " "
        + toActUpon.getG() + " " + toActUpon.getB();

    //call mutate fields method
    updateValues();

    String secondHalf = "     " + endTick + " " + toActUpon.getCurrentPosition().getX() + " " +
        toActUpon.getCurrentPosition().getY() + " " + toActUpon.getWidth() + " " + toActUpon
        .getHeight() + " "
        + toActUpon.getR() + " " + toActUpon.getG() + " " + toActUpon.getB();

    return firstHalf + secondHalf;
  }

  public AbstractShape getToActUpon() {
    return toActUpon;
  }

  public int getStartTick() {
    return startTick;
  }

  public Posn getCurrentPosition() {
    return currentPosition;
  }

  public int getStartW() {
    return startW;
  }

  public int getStartH() {
    return startH;
  }

  public int getStartR() {
    return startR;
  }

  public int getStartG() {
    return startG;
  }

  public int getStartB() {
    return startB;
  }

  public int getEndTick() {
    return endTick;
  }

  public Posn getEndPosition() {
    return endPosition;
  }

  public int getEndW() {
    return endW;
  }

  public int getEndH() {
    return endH;
  }

  public int getEndR() {
    return endR;
  }

  public int getEndG() {
    return endG;
  }

  public int getEndB() {
    return endB;
  }

  public void setPosition(Posn posn) {
    currentPosition = posn;
  }
}
