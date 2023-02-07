package cs3500.hw5.moves;

import cs3500.hw5.Posn;
import cs3500.hw5.shape.AbstractShape;

/**
 * An interface for the methods associated with a move. Includes getters for the fields of the
 * move.
 */
public interface IMove {

  /**
   * Client calls this when they want a summary of the before and after states of the acted upon
   * shape for this move.
   *
   * @return a String summary of the before and after states of the acted upon shape for this move
   */
  String getMoveSummary();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the Shape
   */
  AbstractShape getToActUpon();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the startTick
   */
  int getStartTick();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the currentPosition
   */
  Posn getCurrentPosition();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the starting width
   */
  int getStartW();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the starting height
   */
  int getStartH();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the starting redness
   */
  int getStartR();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the starting greenness
   */
  int getStartG();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the starting blueness
   */
  int getStartB();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the end tick
   */
  int getEndTick();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the end position
   */
  Posn getEndPosition();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the end width
   */
  int getEndW();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the end height
   */
  int getEndH();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the end redness
   */
  int getEndR();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the end greenness
   */
  int getEndG();

  /**
   * Client calls this to obtain the field from the move.
   *
   * @return the end blueness
   */
  int getEndB();

  /**
   * Client calls this when they wish to update the current values for a given shape.
   */
  void updateValues();

}
