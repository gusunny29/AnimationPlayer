package cs3500.hw5.animation;


import java.util.List;

import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.moves.KeyFrame;
import cs3500.hw5.shape.AbstractShape;

/**
 * Represents a single animation. Allows for mutating the speed, width/height, shapes present in the
 * game, outputting Animation as a String, etc. INVARIANTS: the speed of the animation is always
 * positive, the x and y coordinates of the Posn of any Shape are non-negative.
 */
public interface AnimationModel {


  /**
   * adds the given shape to the animationModel.
   *
   * @param s the shape to be added to the current animation model.
   */
  void addShape(AbstractShape s);

  /**
   * adds the given move to the animationModel.
   *
   * @param m the move to be added to the current animation model.
   */
  void addMove(AbstractMove m);


  /**
   * User can call when it wants to see the Shapes that are in this animation.
   *
   * @return a copy of the shapes that are in this animation
   */
  List<AbstractShape> getShapes();

  /**
   * User can call when it wants to see the moves that are in this animation.
   *
   * @return a copy of the moves that are in this animation
   */
  List<AbstractMove> getMoves();

  /**
   * Client calls this when they want to obtain the current tick of this animation model.
   *
   * @return the current tick of this animation model
   */
  int getTick();


  /**
   * returns a String representing the state of every Shape in the animation at the given tick
   * time.
   *
   * @param tick the time at which we want the value of every Shape in the animation
   * @return the list of AbstractShapes at the given tick
   * @throws IllegalArgumentException if the given tick is less than startTick or greater than
   *                                  endTick
   */
  List<AbstractShape> getAnimationStateAt(int tick) throws IllegalArgumentException;


  /**
   * Represents the animation as text instead of visual for the visually impaired.
   *
   * @return the String representation of the animation. Includes all intermediate moves as well.
   */
  String getTextAnimation();

  /**
   * Client calls this when they wish to obtain the final tick of the animation.
   *
   * @return the final tick of the animation
   */
  int getFinalTick();


  /**
   * Client calls this when they wish to obtain the values for the canvas of the view.
   *
   * @return an ArrayList of Integers representing the canvas
   */
  List<Integer> getBounds();


  /**
   * Client calls this when they wish to remove the shape with the given name from the model.
   *
   * @param name represents the name of the shape to be removed
   */
  void removeShape(String name);

  /**
   * Client calls this when they wish to add a keyFrame to the model.
   *
   * @param toAdd represents the KeyFrame to be added
   */
  void addKeyFrame(KeyFrame toAdd);

  /**
   * Client calls this when they wish to remove a keyFrame to the model.
   *
   * @param toRemove represents the KeyFrame to be added
   */
  void removeKeyFrame(KeyFrame toRemove);

  /**
   * Client calls this when they wish to obtain the list of KeyFrames this model contains.
   *
   * @return the list of KeyFrames this model contains
   */
  List<KeyFrame> getKeyFrames();

  /**
   * Client calls this when they wish to edit an already existing keyFrame.
   *
   * @param newerVersion The new Keyframe to redefine the existing keyframe
   */
  void editExistingKeyFrame(KeyFrame newerVersion) throws IllegalArgumentException;

  /**
   * Client calls this when they wish to update the current shapes in this model.
   *
   * @param modelShapesFromView the current shapes in the model
   */
  void setShapes(List<AbstractShape> modelShapesFromView);

  /**
   * Represents the keyframe as a string.
   *
   * @return the keyframe
   */
  String keyFrameAsText();

}
