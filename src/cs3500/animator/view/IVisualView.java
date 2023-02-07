package cs3500.animator.view;

import cs3500.hw5.Posn;

import cs3500.hw5.moves.KeyFrame;
import java.awt.event.ActionListener;
import java.util.List;

import cs3500.hw5.shape.AbstractShape;

/**
 * Represents the Visual View interface. Includes all functionalities the user might need to display
 * the actual view.
 */
public interface IVisualView extends IView {

  /**
   * Makes the view visible, is usually called after the view is constructed.
   */
  void makeVisible();


  /**
   * Provides the view with an action listener for the ubtton that should cause the program to
   * process a command. Control goes to action listener when button is pressed.
   *
   * @param actionEvent the event that occurs
   */
  void setCommandButtonListener(ActionListener actionEvent);

  /**
   * Provides the view with the shapes to be drawn.
   *
   * @param shapes the shapes to be drawn
   */
  void setShapes(List<AbstractShape> shapes);

  /**
   * Signal the view to draw itself.
   */
  void refresh();

  /**
   * Sets the Current tick to the value of the passed in tick.
   *
   * @param tick the desired tick value to be set to
   */
  void setCurrentTick(Integer tick);

  /**
   * Integer representing the animation speed.
   *
   * @return the animation speed from the view
   */
  int getAnimationSpeed();

  /**
   * Gets the value for if the game is paused.
   *
   * @return Whether or not the game is paused
   */
  boolean getIsGamePaused();


  /**
   * Gets whether the animation should be repeating or not.
   *
   * @return looping boolean value for setting the current isLooping field
   */
  boolean getLooping();

  /**
   * Gets the Posn of the mouse click.
   *
   * @return the x,y value of the Mouse Clicked Position
   */
  Posn getClickedPosition();

  /**
   * Displays the name of the shape.
   */
  void displayName(AbstractShape s);

  /**
   * Sets the isGamePaused variable to the passed in pause value.
   */
  void setIsGamePaused(boolean pause);

  /**
   * Sets the Clicked Position to a non on screen value.
   */
  void setNegPosition();

  /**
   * Displays the prompt for keyframes.
   */
  void displayPrompt();

  /**
   * Erases the button panel from the shapePanel.
   */
  void eraseRadio();

  /**
   * Erases the name from the shapePanel.
   */
  void eraseName();

  /**
   * Returns the list of shapes that the view created.
   *
   * @return the arraylist of shapes that the view has created
   */
  List<AbstractShape> getShapes();

  /**
   * Client calls this when they wish to set the list of shapes in the view.
   *
   * @param shapes the new list of shapes in the view
   */
  void setShapesInView(List<AbstractShape> shapes);


  /**
   * Client calls this when they wish to obtain the shape that was clicked.
   *
   * @param shapes          the shapes currently in the model
   * @param clickedPosition the position of the click
   * @return the shape that was clicked
   */
  AbstractShape getShapeClicked(List<AbstractShape> shapes, Posn clickedPosition);

  /**
   * Client calls this when they wish to update the shapes in the view to be the model shapes.
   *
   * @param shapes the shapes currently in the model
   */
  void setModelShapes(List<AbstractShape> shapes);


  /**
   * Client calls this when they wish to obtain whether or not the removeShape button was clicked.
   *
   * @return true if the remove shape button was clicked
   */
  boolean getWasRemoveClicked();

  /**
   * Sets the Remove clicked to the passed in value.
   *
   * @param b boolean that remove clicked should be set to
   */
  void setWasRemoveClicked(boolean b);

  /**
   * Sets the position of the click passed in.
   */
  void setClickedPosition(Posn p);

  /**
   * Gets the position to be used.
   *
   * @return position
   */
  Posn getterPosition();

  /**
   * gets the Keyframes from the model.
   *
   * @param keyFrames the keyframes
   */
  void setModelKeyFrames(List<KeyFrame> keyFrames);


  /**
   * Sets the removed key frames from the edit view to the passed in variable.
   *
   * @param keyFrames arraylist of keyframes to set removedKeyFrames
   */
  void setRemovedKeyFrames(List<KeyFrame> keyFrames);

  /**
   * Gets the removed Key Frames from the view class to be accessed by other classes.
   *
   * @return the arraylist representing the key frames to be removed
   */
  List<KeyFrame> getRemovedKeyFrames();


  /**
   * Sets the added key frames from the edit view to the passed in variable.
   *
   * @param keyFrames arraylist of keyframes to set addKeyFrames
   */
  void setAddKeyFrames(List<KeyFrame> keyFrames);


  /**
   * Gets the added Key Frames from the view class to be accessed by other classes.
   *
   * @return the arraylist representing the key frames to be added
   */
  List<KeyFrame> getAddKeyFrames();

  /**
   * Gets the edited Key Frames from the view class to be accessed by other classes.
   *
   * @return the arraylist representing the key frames to be edited
   */
  List<KeyFrame> getEditKeyFrames();

  /**
   * Sets the edit key frames from the edit view to the passed in variable.
   *
   * @param keyFrames arraylist of keyframes to set editKeyFrames
   */
  void setEditKeyFrames(List<KeyFrame> keyFrames);
}

