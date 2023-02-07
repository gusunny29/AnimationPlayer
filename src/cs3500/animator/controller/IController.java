package cs3500.animator.controller;

import java.util.List;

import cs3500.animator.view.IView;
import cs3500.hw5.shape.AbstractShape;

/**
 * Controller interface for the animation program.
 * The functions here play the animation but don't take any user input.
 */
public interface IController {


  /**
   * Start the program. Play the animation.
   */
  void playGame();

  /**
   * Mutates the view's list of shapes.
   * @param shapes the view's list of shapes
   */
  void setViewShapes(List<AbstractShape> shapes);



  /**
   * Client calls this when they wish to obtain the list of Shapes at a given tick .
   * @param tick the time for the states of the shapes
   * @return a list of shapes with their current fields updated for the given tick
   */
  List<AbstractShape> getShapesAt(int tick);

  /**
   * Refreshes the screen for the user.
   */
  void refreshScreen();

  /**
   * Client calls this when they wish to obtain the last tick value of the entire animation.
   * @return the last tick value of the entire animation
   */
  int getLastTick();

  /**
   * Represents whether or not the game is paused.
   * @return Boolean representing if the game is paused.
   */
  boolean isGamePaused();

  /**
   * Represents the current tick of the animation.
   * @return the Integer value of the current tick
   */
  Integer getCurrentTick();

  /**
   * Sets the value of the current tick to be the passed in tick.
   * @param tick the new current tick
   */
  void setCurrentTick(Integer tick);


  /**
   * Gets the field for whether the animation should loop from the view.
   * @return the boolean value for whether the animation is looping
   */
  boolean getLooping();

  /**
   * Gets the speed of the animation.
   * @return speed of the animation speed
   */
  int getAnimationSpeed();

  /**
   * Client calls this when they wish to obtain the View from the controller.
   * @return the view
   */
  IView getView();
}
