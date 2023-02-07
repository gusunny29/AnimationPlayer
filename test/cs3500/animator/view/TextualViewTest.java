package cs3500.animator.view;

import static org.junit.Assert.assertEquals;

import cs3500.hw5.Posn;
import cs3500.hw5.animation.AnimationModelImpl;
import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.moves.ColorMoves;
import cs3500.hw5.moves.PositionMoves;
import cs3500.hw5.moves.SizeMoves;
import cs3500.hw5.moves.TimeMoves;
import cs3500.hw5.shape.AbstractShape;
import cs3500.hw5.shape.Circle;
import cs3500.hw5.shape.Ellipse;
import cs3500.hw5.shape.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 * Tests for the Textual View Class. Confirms the functionality of the textual view with the given
 * animation.
 */
public class TextualViewTest {

  AbstractShape redRectangle = new Rectangle("R", new Posn(200, 200), 50,
      100, 255, 0, 0, 1);

  AbstractShape blueEllipse = new Ellipse("C", new Posn(440, 70), 120, 60,
      0, 0, 255, 6);

  AbstractShape circle = new Circle("CI", new Posn(0, 0), 50, 255,
      0, 0, 1);

  ArrayList<AbstractShape> shapes = new ArrayList<AbstractShape>(
      Arrays.asList(redRectangle, blueEllipse, circle));

  ArrayList<AbstractMove> moves = new ArrayList<AbstractMove>(
      Arrays.asList(new PositionMoves(redRectangle,
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
          new TimeMoves(blueEllipse, 80, 100)));


  @Test
  public void emptyTextView() {
    assertEquals("Canvas 3 4 1 2\n", new TextualView(new ArrayList<Integer>(Arrays.asList(
        1, 2, 3, 4)), new ArrayList<AbstractShape>(),
        new ArrayList<AbstractMove>()).textBuilder(new AnimationModelImpl()));
  }

  @Test
  public void playTest1() {
    ITextualView onlyShapes = new TextualView(new ArrayList<Integer>(Arrays.asList(
        1, 2, 3, 4)), shapes, moves);

    assertEquals("Canvas 3 4 1 2\n"
            + "shape R Rectangle\n"
            + "motion R 1 200 200 50 100 255 0 0     10 200 200 50 100 255 0 0\n"
            + "motion R 10 200 200 50 100 255 0 0     50 300 300 50 100 255 0 0\n"
            + "motion R 50 300 300 50 100 255 0 0     51 300 300 50 100 255 0 0\n"
            + "motion R 51 300 300 50 100 255 0 0     70 300 300 25 100 255 0 0\n"
            + "motion R 70 300 300 25 100 255 0 0     100 200 200 25 100 255 0 0\n"
            + "\n"
            + "shape C Ellipse\n"
            + "motion C 6 440 70 120 60 0 0 255     20 440 70 120 60 0 0 255\n"
            + "motion C 20 440 70 120 60 0 0 255     50 440 250 120 60 0 0 255\n"
            + "motion C 50 440 250 120 60 0 0 255     70 440 370 120 60 0 170 85\n"
            + "motion C 70 440 370 120 60 0 170 85     80 440 370 120 60 0 255 0\n"
            + "motion C 80 440 370 120 60 0 255 0     100 440 370 120 60 0 255 0\n"
            + "\n"
            + "shape CI Circle\n"
            + "motion CI 1 0 0 100 100 255 0 0     100 0 0 100 100 255 0 0",
        onlyShapes.textBuilder(new AnimationModelImpl(shapes, moves)));
  }

  @Test
  public void redRectangleTest() {
    ITextualView redRec = new TextualView(new ArrayList<Integer>(Arrays.asList(
        1, 2, 3, 4)), new ArrayList<AbstractShape>(Arrays.asList(redRectangle)),
        new ArrayList<AbstractMove>(Arrays.asList(new SizeMoves(redRectangle,
                50, 100, 25, 100,
                51, 70),
            new PositionMoves(redRectangle,
                new Posn(300, 300), new Posn(200, 200), 70, 100))));

    assertEquals("Canvas 3 4 1 2\n"
            + "shape R Rectangle\n"
            + "motion R 1 200 200 50 100 255 0 0     51 200 200 50 100 255 0 0\n"
            + "motion R 51 200 200 50 100 255 0 0     70 200 200 25 100 255 0 0\n"
            + "motion R 70 200 200 25 100 255 0 0     100 200 200 25 100 255 0 0",
        redRec.textBuilder(new AnimationModelImpl(
            new ArrayList<AbstractShape>(Arrays.asList(redRectangle)),
            new ArrayList<AbstractMove>(Arrays.asList(new SizeMoves(redRectangle,
                    50, 100, 25, 100,
                    51, 70),
                new PositionMoves(redRectangle,
                    new Posn(300, 300), new Posn(200, 200), 70, 100))))));
  }


}