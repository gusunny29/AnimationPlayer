package cs3500.animator.controller;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.animator.view.EditView;
import cs3500.animator.view.VAView;
import cs3500.hw5.Posn;
import cs3500.hw5.animation.AnimationModel;
import cs3500.hw5.animation.AnimationModelImpl;
import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.moves.ColorMoves;
import cs3500.hw5.moves.PositionMoves;
import cs3500.hw5.moves.SizeMoves;
import cs3500.hw5.moves.TimeMoves;
import cs3500.hw5.shape.AbstractShape;
import cs3500.hw5.shape.Ellipse;
import cs3500.hw5.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the implmentation of IController. Tests the methods of the MVCController class.
 */
public class MVCControllerTest {

  AbstractShape redRectangle = new Rectangle("R", new Posn(200, 200), 50,
          100, 255, 0, 0, 1);

  AbstractShape blueEllipse = new Ellipse("C", new Posn(440, 70), 120, 60,
          0, 0, 255, 6);

  AnimationModel exampleModel = new AnimationModelImpl(new ArrayList<AbstractShape>(
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

  IController exampleController = new MVCController(exampleModel, new EditView(new VAView()));


  @Test
  public void testGetCurrentTick() {
    assertEquals(0, (int)exampleController.getCurrentTick());
    exampleController.setCurrentTick(75);
    assertEquals(75, (int)exampleController.getCurrentTick());
  }

  @Test
  public void defaultSettingForLooping() {
    assertTrue(exampleController.getLooping());
  }

  @Test
  public void testAnimationSpeed() {
    assertEquals(1, exampleController.getAnimationSpeed());
  }

  @Test
  public void testGetAnimationStateAt() {
    assertEquals(new ArrayList<AbstractShape>(Arrays.asList(new Rectangle("R",
            new Posn(300, 300), 50, 100, 255, 0, 0, 1),
            new Ellipse("C", new Posn(440, 250), 120, 60,
                    0, 0, 255, 6))),
            exampleController.getShapesAt(50));
  }

  @Test
  public void testGetFinalTick() {
    assertEquals(100, exampleController.getLastTick());
  }

  @Test
  public void testGetIsGamePaused() {
    assertTrue(exampleController.isGamePaused());
  }
}