package cs3500.hw5;

import cs3500.animator.util.AnimationReader;
import cs3500.hw5.animation.AnimationModel;
import cs3500.hw5.animation.AnimationModelImpl;
import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.moves.ColorMoves;
import cs3500.hw5.moves.KeyFrame;
import cs3500.hw5.moves.PositionMoves;
import cs3500.hw5.moves.SizeMoves;
import cs3500.hw5.moves.TimeMoves;
import cs3500.hw5.shape.AbstractShape;
import cs3500.hw5.shape.Circle;
import cs3500.hw5.shape.Ellipse;
import cs3500.hw5.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;


/**
 * Tests the AnimationModelImpl class. Tests methods as well as constructors of the class.
 */
public class AnimationModelImplTest {

  AbstractShape redRectangle = new Rectangle("R", new Posn(200, 200), 50,
      100, 255, 0, 0, 1);

  AbstractShape blueEllipse = new Ellipse("C", new Posn(440, 70), 120, 60,
      0, 0, 255, 6);

  AbstractShape circle = new Circle("CI", new Posn(0, 0), 50, 255,
      0, 0, 1);


  //EXAMPLES OF ANIMATION MODELS
  AnimationModel rectangleFromHWNoMoves = new AnimationModelImpl(new ArrayList<AbstractShape>(
      Arrays.asList(new Rectangle("R", new Posn(200, 200), 50,
          100, 255, 0, 0, 1))),
      new ArrayList<AbstractMove>());

  AnimationModel ellipseFromHWNoMoves = new AnimationModelImpl(new ArrayList<AbstractShape>(
      Arrays.asList(blueEllipse)),
      new ArrayList<AbstractMove>());


  AnimationModel empty = new AnimationModelImpl(
      new ArrayList<AbstractShape>(), new ArrayList<AbstractMove>());


  //test that an IAE is thrown if given a move that acts on a Shape not in this AnimationModel
  @Test(expected = IllegalArgumentException.class)
  public void testMoveWithNoShapeInAnimation() {
    new AnimationModelImpl(new ArrayList<AbstractShape>(),
        new ArrayList<AbstractMove>(Arrays.asList(new ColorMoves(circle, 0, 0,
            255, 0, 0, 255,
            1, 50))));
  }

  //test that an IAE is thrown if given a move that acts on a Shape not in this AnimationModel
  @Test(expected = IllegalArgumentException.class)
  public void testMoveWithNoShapeInAnimationMatchingMove() {
    new AnimationModelImpl(new ArrayList<AbstractShape>(Arrays.asList()),
        new ArrayList<AbstractMove>(Arrays.asList(new ColorMoves(circle, 0, 0,
            255, 0, 0, 255,
            1, 50))));
  }

  //test that an IAE is thrown when given two moves of the sameType that are overlapping
  @Test(expected = IllegalArgumentException.class)
  public void testOverlappingTimeIntervals() {
    new AnimationModelImpl(new ArrayList<AbstractShape>(Arrays.asList(circle)),
        new ArrayList<AbstractMove>(Arrays.asList(
            new ColorMoves(circle, 0, 0, 255, 0, 255, 0,
                1, 50),
            new ColorMoves(circle, 0, 0, 255, 0, 0, 255,
                25, 75))));
  }

  //test that an IAE is thrown when given two moves of the sameType that are overlapping
  @Test(expected = IllegalArgumentException.class)
  public void testOverlappingTimeIntervalsNotSorted() {
    new AnimationModelImpl(new ArrayList<AbstractShape>(Arrays.asList(circle)),
        new ArrayList<AbstractMove>(Arrays.asList(
            new ColorMoves(circle, 0, 0, 255, 0, 255,
                0, 1, 50),
            new ColorMoves(circle, 0, 0, 255, 0, 0,
                255, 25, 75))));
  }

  //test that the empty AnimationModel produces the emptyString for the
  @Test
  public void testEmptyAnimationModel() {
    assertEquals("", empty.getTextAnimation());
  }

  //test the rectangle first lines from the HW
  @Test
  public void TestRectangleFirstLine() {
    assertEquals("shape R Rectangle\n" +
            "motion R 1 200 200 50 100 255 0 0     100 200 200 50 100 255 0 0",
        rectangleFromHWNoMoves.getTextAnimation());
  }

  //test the rectangle first two lines from the HW
  @Test
  public void TestRectangleFirstTwoLines() {
    assertEquals("shape R Rectangle\n" +
            "motion R 1 200 200 50 100 255 0 0     10 200 200 50 100 255 0 0\n" +
            "motion R 10 200 200 50 100 255 0 0     50 300 300 50 100 255 0 0",
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle)),
            new ArrayList<AbstractMove>(Arrays.asList(
                new PositionMoves(redRectangle, new Posn(200, 200), new Posn(300, 300),
                    10, 50)))).getTextAnimation());
  }


  //test the rectangle first two moves of the HW text animation
  @Test
  public void TestRectangleFirstTwoMovesTextSummary() {
    assertEquals("shape R Rectangle\n" +
            "motion R 1 200 200 50 100 255 0 0     10 200 200 50 100 255 0 0\n" +
            "motion R 10 200 200 50 100 255 0 0     50 300 300 50 100 255 0 0\n" +
            "motion R 50 300 300 50 100 255 0 0     51 300 300 50 100 255 0 0\n" +
            "motion R 51 300 300 50 100 255 0 0     70 300 300 25 100 255 0 0",
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle)),
            new ArrayList<AbstractMove>(Arrays.asList(new PositionMoves(redRectangle,
                    new Posn(200, 200), new Posn(300, 300), 10, 50),
                new SizeMoves(redRectangle, 50, 100, 25, 100,
                    51, 70)))).getTextAnimation());
  }


  //test the first ellipse line from the HW
  @Test
  public void testFirstEllipseLineHW() {
    assertEquals("shape C Ellipse\n" +
            "motion C 6 440 70 120 60 0 0 255     100 440 70 120 60 0 0 255",
        ellipseFromHWNoMoves.getTextAnimation());
  }

  //tests the occurances after the first move
  @Test
  public void testSecondEllipseLineHW() {
    assertEquals("shape C Ellipse\n"
            + "motion C 6 440 70 120 60 0 0 255     20 440 70 120 60 0 0 255\n"
            + "motion C 20 440 70 120 60 0 0 255     50 440 250 120 60 0 0 255",
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(blueEllipse)),
            new ArrayList<AbstractMove>(Arrays.asList(new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20,
                50)))).getTextAnimation());
  }

  //tests the occurrences after the second move
  @Test
  public void testThirdEllipseLineHW() {
    assertEquals("shape C Ellipse\n"
            + "motion C 6 440 70 120 60 0 0 255     20 440 70 120 60 0 0 255\n"
            + "motion C 20 440 70 120 60 0 0 255     50 440 250 120 60 0 0 255\n"
            + "motion C 50 440 250 120 60 0 0 255     70 440 370 120 60 0 0 255",
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(blueEllipse)),
            new ArrayList<AbstractMove>(Arrays.asList(new PositionMoves(blueEllipse,
                    new Posn(440, 70), new Posn(440, 250), 20, 50),
                new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                    50, 70)))).getTextAnimation());
  }

  //tests the occurrences after the second move
  @Test
  public void testOverlappingEllipseLineHW() {
    assertEquals("shape C Ellipse\n" +
            "motion C 6 440 70 120 60 0 0 255     20 440 70 120 60 0 0 255\n" +
            "motion C 20 440 70 120 60 0 0 255     50 440 250 120 60 0 0 255\n" +
            "motion C 50 440 250 120 60 0 0 255     70 440 370 120 60 0 170 85",
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(blueEllipse)),
            new ArrayList<AbstractMove>(Arrays.asList(new PositionMoves(blueEllipse,
                    new Posn(440, 70), new Posn(440, 250), 20, 50),
                new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                    50, 70),
                new ColorMoves(blueEllipse, 0, 0, 255, 0,
                    170, 85, 50, 70)))).getTextAnimation());
  }


  //test the entire AnimationModel from the HW
  @Test
  public void testEntireAnimation() {
    assertEquals("shape R Rectangle\n" +
            "motion R 1 200 200 50 100 255 0 0     10 200 200 50 100 255 0 0\n" +
            "motion R 10 200 200 50 100 255 0 0     50 300 300 50 100 255 0 0\n" +
            "motion R 50 300 300 50 100 255 0 0     51 300 300 50 100 255 0 0\n" +
            "motion R 51 300 300 50 100 255 0 0     70 300 300 25 100 255 0 0\n" +
            "motion R 70 300 300 25 100 255 0 0     100 200 200 25 100 255 0 0\n" +
            "\n" +
            "shape C Ellipse\n" +
            "motion C 6 440 70 120 60 0 0 255     20 440 70 120 60 0 0 255\n" +
            "motion C 20 440 70 120 60 0 0 255     50 440 250 120 60 0 0 255\n" +
            "motion C 50 440 250 120 60 0 0 255     70 440 370 120 60 0 170 85\n" +
            "motion C 70 440 370 120 60 0 170 85     80 440 370 120 60 0 255 0\n" +
            "motion C 80 440 370 120 60 0 255 0     100 440 370 120 60 0 255 0",
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getTextAnimation());
  }


  @Test
  public void testRectanglePositionAt69() {
    assertEquals(300,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            69).get(0).getCurrentPosition().getX());
  }

  @Test
  public void testRectanglePositionAt70() {
    assertEquals(300,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            70).get(0).getCurrentPosition().getX());
  }

  @Test
  public void testRectanglePositionAt71() {
    assertEquals(296,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            71).get(0).getCurrentPosition().getX());
  }

  @Test
  public void testEllipsePosnAt6() {
    assertEquals(70,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            6).get(1).getCurrentPosition().getY());
  }

  @Test
  public void testRectangleWidth71() {
    assertEquals(25,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            71).get(0).getWidth());
  }

  @Test
  public void testRectangleWidth75() {
    assertEquals(25,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            75).get(0).getWidth());
  }

  @Test
  public void testRectanglePositionTime20() {
    assertEquals(225,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            20).get(0).getCurrentPosition().getX());
  }

  @Test
  public void testRectangleWidth92() {
    assertEquals(25,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            92).get(0).getWidth());
  }

  @Test
  public void testEllipseY6() {
    assertEquals(70,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            6).get(1).getCurrentPosition().getY());
  }

  @Test
  public void testEllipseY20() {
    assertEquals(70,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            20).get(1).getCurrentPosition().getY());
  }

  @Test
  public void testEllipseY35() {
    assertEquals(160,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            35).get(1).getCurrentPosition().getY());
  }

  @Test
  public void testEllipseY40() {
    assertEquals(190,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            40).get(1).getCurrentPosition().getY());
  }

  @Test
  public void testEllipseY50() {
    assertEquals(250,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            50).get(1).getCurrentPosition().getY());
  }

  @Test
  public void testEllipseY60() {
    assertEquals(310,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            60).get(1).getCurrentPosition().getY());
  }

  @Test
  public void testEllipseY70() {
    assertEquals(370,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            70).get(1).getCurrentPosition().getY());
  }

  @Test
  public void testEllipseY92() {
    assertEquals(370,
        new AnimationModelImpl(new ArrayList<AbstractShape>(
            Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
            new PositionMoves(redRectangle,
                new Posn(200, 200), new Posn(300, 300), 10, 50),
            new SizeMoves(redRectangle, 50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100),
            new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70),
            new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                50, 70),
            new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                70, 80),
            new TimeMoves(blueEllipse, 80, 100)))).getAnimationStateAt(
            92).get(1).getCurrentPosition().getY());
  }


  @Test
  public void testUpdateShapes() {
    assertEquals(70, blueEllipse.getCurrentPosition().getY());
    //mutate
    new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));
    assertEquals(76, blueEllipse.getCurrentPosition().getY());
  }


  @Test
  public void testUpdateShapesRectangle() {
    assertEquals(200, redRectangle.getCurrentPosition().getY());
    //mutate
    new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));
    assertEquals(205, redRectangle.getCurrentPosition().getY());
  }

  @Test
  public void testExcellence() throws FileNotFoundException {
    //the file to read from
    File source = new File("src/" + "resources/smalldemo.txt");

    FileReader fileReader = new FileReader(source);

    AnimationReader entireReader = new AnimationReader();

    //instantiate the builder object using the outer class
    AnimationModelImpl.Builder builder = new AnimationModelImpl.Builder();

    //how do we actually use the animationReader to read the file?
    AnimationModel model = entireReader.parseFile(fileReader, builder);

    assertEquals(200, model.getAnimationStateAt(15).
        get(0).getCurrentPosition().getX());
  }


  @Test
  public void blueEllipseTest() {
    assertEquals(70,
        new AnimationModelImpl(new ArrayList<AbstractShape>(Arrays.asList(blueEllipse)),
            new ArrayList<AbstractMove>(Arrays.asList(new PositionMoves(blueEllipse,
                    new Posn(440, 70), new Posn(440, 250), 20, 50),
                new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                    50, 70),
                new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
                    50, 70),
                new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
                    70, 80)))).getAnimationStateAt(6).get(0).getCurrentPosition().getY());

  }


  @Test
  public void testKeyFramesAsTextEmpty() {
    assertEquals("", new AnimationModelImpl().keyFrameAsText());
  }

  @Test
  public void testKeyFramesAsTextEasy() {
    assertEquals("R 1 200 200 50 100 255 0 0", new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(new Rectangle("R", new Posn(200, 200), 50,
            100, 255, 0, 0, 1))),
        new ArrayList<AbstractMove>()).keyFrameAsText());
  }

  @Test
  public void testKeyFramesAsTextOneMove() {
    assertEquals("C 6 440 70 120 60 0 0 255\n" +
        "C 20 440 70 120 60 0 0 255\n" +
        "C 50 440 250 120 60 0 0 255\n", new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(blueEllipse)),
        new ArrayList<AbstractMove>(Arrays.asList(new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20,
            50)))).keyFrameAsText());
  }


  @Test
  public void testKeyFramesAsTextTwoMoves() {
    assertEquals("C 6 440 70 120 60 0 0 255\n" +
        "C 20 440 70 120 60 0 0 255\n" +
        "C 50 440 250 120 60 0 0 255\n" +
        "C 70 440 370 120 60 0 0 255\n", new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(blueEllipse)),
        new ArrayList<AbstractMove>(Arrays.asList(new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70)))).keyFrameAsText());
  }

  @Test
  public void testKeyFramesAsTextTwoShapes() {
    assertEquals("R 1 200 200 50 100 255 0 0\n" +
        "R 10 200 200 50 100 255 0 0\n" +
        "R 50 300 300 50 100 255 0 0\n" +
        "R 51 300 300 50 100 255 0 0\n" +
        "R 70 300 300 25 100 255 0 0\n" +
        "R 100 200 200 25 100 255 0 0\n" +
        "C 6 440 370 120 60 0 255 0\n" +
        "C 20 440 70 120 60 0 0 255\n" +
        "C 50 440 250 120 60 0 255 0\n" +
        "C 70 440 370 120 60 0 170 85\n" +
        "C 80 440 370 120 60 0 255 0\n" +
        "C 100 440 370 120 60 0 255 0\n", new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100)))).keyFrameAsText());
  }

  @Test
  public void testKeyFramesAsTextTwoShapesSecond() {
    assertEquals("C 6 440 70 120 60 0 0 255\n" +
        "C 20 440 70 120 60 0 0 255\n" +
        "C 50 440 250 120 60 0 0 255\n" +
        "C 70 440 370 120 60 0 0 255\n", new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(blueEllipse)),
        new ArrayList<AbstractMove>(Arrays.asList(new PositionMoves(blueEllipse,
                new Posn(440, 70), new Posn(440, 250), 20, 50),
            new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
                50, 70)))).keyFrameAsText());
  }

  @Test
  public void testRemovingKeyFrames() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.removeKeyFrame(
        new KeyFrame(redRectangle, 1));

    assertEquals(11, example.getKeyFrames().size());

    for (KeyFrame k : example.getKeyFrames()) {
      System.out.println(
          k.getToActUpon().getName() + " " + k.getTick() + " " + k.getX() + " " + k.getY() + " " +
              k.getW() + " " + k.getH() + " " + k.getR() + " " + k.getG() + " " + k.getB());
    }
  }


  @Test
  public void testAddingKeyFrames() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.addKeyFrame(new KeyFrame(new Rectangle("R"), 150, 300, 300, 25,
        100, 255, 0, 0));

    assertEquals(250,
        example.getAnimationStateAt(125).get(0).getCurrentPosition().getX());

    for (KeyFrame k : example.getKeyFrames()) {
      System.out.println(
          k.getToActUpon().getName() + " " + k.getTick() + " " + k.getX() + " " + k.getY() + " " +
              k.getW() + " " + k.getH() + " " + k.getR() + " " + k.getG() + " " + k.getB());
    }
  }

  @Test
  public void testEditingKeyFramesAgain() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.editExistingKeyFrame(new KeyFrame(new Rectangle("R"), 70, 300, 300,
        50, 100, 255, 0, 0));

    assertEquals(50, example.getAnimationStateAt(50).get(0).getWidth());

    assertEquals(50, example.getAnimationStateAt(55).get(0).getWidth());

    assertEquals(50, example.getAnimationStateAt(70).get(0).getWidth());

    for (KeyFrame k : example.getKeyFrames()) {
      System.out.println(
          k.getToActUpon().getName() + " " + k.getTick() + " " + k.getX() + " " + k.getY() + " " +
              k.getW() + " " + k.getH() + " " + k.getR() + " " + k.getG() + " " + k.getB());
    }
  }

  @Test
  public void testAddingKeyFramesInOrder() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.addKeyFrame(new KeyFrame(new Rectangle("R"), 70, 300, 300,
        25, 100, 255, 0, 0));

    example.addKeyFrame(new KeyFrame(new Rectangle("R"), 5, 200, 200,
        50, 100, 255, 0, 0));

    example.addKeyFrame(new KeyFrame(new Rectangle("C"), 10, 440, 70,
        120, 60, 0, 0, 255));

    assertEquals(14, example.getKeyFrames().size());

    for (KeyFrame k : example.getKeyFrames()) {
      System.out.println(
          k.getToActUpon().getName() + " " + k.getTick() + " " + k.getX() + " " + k.getY() + " " +
              k.getW() + " " + k.getH() + " " + k.getR() + " " + k.getG() + " " + k.getB());
    }
  }


  @Test
  public void testAddingKeyFramesAfterTheLastFrameMiddleShape() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.addKeyFrame(new KeyFrame(redRectangle, 150, 200, 200,
        25, 100, 255, 0, 0));

    assertEquals(13, example.getKeyFrames().size());

    for (KeyFrame k : example.getKeyFrames()) {
      System.out.println(
          k.getToActUpon().getName() + " " + k.getTick() + " " + k.getX() + " " + k.getY() + " " +
              k.getW() + " " + k.getH() + " " + k.getR() + " " + k.getG() + " " + k.getB());
    }
  }


  @Test
  public void testAddingKeyFramesAfterTheLastFrame() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.addKeyFrame(new KeyFrame(blueEllipse, 150, 440, 370,
        120, 60, 0, 255, 0));

    assertEquals(13, example.getKeyFrames().size());

    for (KeyFrame k : example.getKeyFrames()) {
      System.out.println(
          k.getToActUpon().getName() + " " + k.getTick() + " " + k.getX() + " " + k.getY() + " " +
              k.getW() + " " + k.getH() + " " + k.getR() + " " + k.getG() + " " + k.getB());
    }
  }

  @Test
  public void testAddingKeyFramesBeforeTheFirstFrame() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.addKeyFrame(new KeyFrame(blueEllipse, 1, 440, 70,
        120, 60, 0, 0, 255));

    assertEquals(13, example.getKeyFrames().size());

    for (KeyFrame k : example.getKeyFrames()) {
      System.out.println(
          k.getToActUpon().getName() + " " + k.getTick() + " " + k.getX() + " " + k.getY() + " " +
              k.getW() + " " + k.getH() + " " + k.getR() + " " + k.getG() + " " + k.getB());
    }
  }


  @Test
  public void testEditingKeyFrames() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.editExistingKeyFrame(new KeyFrame(new Rectangle("R"), 100, 300, 300,
        75, 100, 255, 0, 0));

    assertEquals(50, example.getAnimationStateAt(85).get(0).getWidth());

    for (KeyFrame k : example.getKeyFrames()) {
      System.out.println(
          k.getToActUpon().getName() + " " + k.getTick() + " " + k.getX() + " " + k.getY() + " " +
              k.getW() + " " + k.getH() + " " + k.getR() + " " + k.getG() + " " + k.getB());
    }
  }

  @Test
  public void testAddingAShape() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.addShape(new Rectangle("Rectanlsakjdf"));

    assertEquals(3, example.getAnimationStateAt(85).size());

    for (AbstractShape s : example.getShapes()) {
      System.out.println("The shapes in the animation currently are: " + s.getName());
    }
  }

  @Test
  public void testAddingAShapeThatAlreadyExists() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.addShape(new Rectangle("R"));

    assertEquals(2, example.getAnimationStateAt(85).size());

    for (AbstractShape s : example.getShapes()) {
      System.out.println("The shapes in the animation currently are: " + s.getName());
    }
  }

  @Test
  public void testRemovingAShape() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.removeShape("R");

    assertEquals(1, example.getAnimationStateAt(85).size());

    for (AbstractShape s : example.getShapes()) {
      System.out.println("The shapes in the animation currently are: " + s.getName());
    }
  }

  @Test
  public void testRemovingAShapeThatDoesNotExist() {
    AnimationModel example = new AnimationModelImpl(new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle, blueEllipse)), new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle,
            new Posn(200, 200), new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70),
        new PositionMoves(redRectangle,
            new Posn(300, 300), new Posn(200, 200), 70, 100),
        new PositionMoves(blueEllipse,
            new Posn(440, 70), new Posn(440, 250), 20, 50),
        new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
            50, 70),
        new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
            50, 70),
        new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
            70, 80),
        new TimeMoves(blueEllipse, 80, 100))));

    example.removeShape("asdkjb;hasghlf");

    assertEquals(2, example.getAnimationStateAt(85).size());

    for (AbstractShape s : example.getShapes()) {
      System.out.println("The shapes in the animation currently are: " + s.getName());
    }
  }
}