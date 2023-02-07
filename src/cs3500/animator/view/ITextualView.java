package cs3500.animator.view;

import cs3500.hw5.animation.AnimationModel;

/**
 * Represents an interface for the Textual View class. Client can call operations relevant
 * to the textual view here.
 */
public interface ITextualView extends IView {

  /**
   * Returns the textual view of the animation in string format.
   * @param model object of the model used to pass information about the animation to the view
   * @return String representing the textual view
   */
  String textBuilder(AnimationModel model);
}
