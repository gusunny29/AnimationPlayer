package cs3500.hw5;

import org.junit.Test;

import cs3500.hw5.Posn;
import cs3500.hw5.moves.PositionMoves;
import cs3500.hw5.moves.SizeMoves;
import cs3500.hw5.moves.TimeMoves;
import cs3500.hw5.shape.AbstractShape;
import cs3500.hw5.shape.Circle;

/**
 * Represents a set of tests for PositionMoves. Specifically tests its constructor.
 */
public class MovesTests {

  AbstractShape example = new Circle("EXAMPLE", new Posn(100, 100),
          12, 80, 80, 95, 1);


  //test IAE occurs when given an invalid position
  @Test(expected = IllegalArgumentException.class)
  public void givenNegPosition() {
    new PositionMoves(example, new Posn(200, 400), new Posn(-100, 140), 1, 10);
  }

  //test IAE occurs when given an invalid position
  @Test(expected = IllegalArgumentException.class)
  public void givenNegPositionY() {
    new PositionMoves(example, new Posn(200, 400), new Posn(100, -1), 1, 10);
  }

  //test IAE occurs when given an invalid start tick
  @Test(expected = IllegalArgumentException.class)
  public void nonPositiveStartTick() {
    new PositionMoves(example, new Posn(200, 400), new Posn(100, 40), 0, 10);
  }

  //test IAE occurs when given an invalid start tick
  @Test(expected = IllegalArgumentException.class)
  public void negativeStartTick() {
    new PositionMoves(example, new Posn(200, 400), new Posn(100, 40), -10, 10);
  }

  //test IAE occurs when given an invalid start tick
  @Test(expected = IllegalArgumentException.class)
  public void overlappingStartEndTicks() {
    new PositionMoves(example, new Posn(200, 400), new Posn(100, 40), 12, 11);
  }

  /**
   * Represents a set of tests for PositionMoves. Specifically tests its constructor.
   */
  public class SizeMovesTest {

    AbstractShape example = new Circle("EXAMPLE", new Posn(100, 100),
            12, 80, 80, 95, 1);


    //test IAE occurs when given an invalid width
    @Test(expected = IllegalArgumentException.class)
    public void givenNegWidth() {
      new SizeMoves(example, 25, 100, -10, 100, 1, 10);
    }

    //test IAE occurs when given an invalid height
    @Test(expected = IllegalArgumentException.class)
    public void givenNegHeight() {
      new SizeMoves(example, 25, 100,100, -1, 1, 10);
    }

    //test IAE occurs when given an invalid width
    @Test(expected = IllegalArgumentException.class)
    public void givenZeroWidth() {
      new SizeMoves(example, 25, 100,0, 100, 1, 10);
    }

    //test IAE occurs when given an invalid height
    @Test(expected = IllegalArgumentException.class)
    public void givenZeroHeight() {
      new SizeMoves(example, 25, 100,100, 0, 1, 10);
    }

    //test IAE occurs when given an invalid startTick
    @Test(expected = IllegalArgumentException.class)
    public void givenZeroStart() {
      new SizeMoves(example, 25, 100,100, 0, 0, 10);
    }

    //test IAE occurs when given an invalid startTick
    @Test(expected = IllegalArgumentException.class)
    public void givenNegStart() {
      new SizeMoves(example, 25, 100,100, 0, -1, 10);
    }

    //test IAE occurs when given ticks that overlap
    @Test(expected = IllegalArgumentException.class)
    public void givenOverlappingStartEnd() {
      new SizeMoves(example, 25, 100,100, 0, 5, 4);
    }

  }

  /**
   * Represents a set of tests for PositionMoves. Specifically tests its constructor.
   */
  public class TimeMovesTest {

    AbstractShape example = new Circle("EXAMPLE", new Posn(100, 100),
            12, 80, 80, 95, 1);


    //test IAE occurs when given an invalid width
    @Test(expected = IllegalArgumentException.class)
    public void givenNegStart() {
      new TimeMoves(example, -10, 100);
    }

    //test IAE occurs when given an invalid height
    @Test(expected = IllegalArgumentException.class)
    public void givenZeroStart() {
      new TimeMoves(example, 0, 100);
    }

    //test IAE occurs when given ticks that overlap
    @Test(expected = IllegalArgumentException.class)
    public void givenOverlappingStartEnd() {
      new TimeMoves(example, 5, 5);
    }


  }

}