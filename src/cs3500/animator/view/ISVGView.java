package cs3500.animator.view;

/**
 * Represents an interface for the SVG View class. Client can call operations relevant
 * to the SVG view here.
 */
public interface ISVGView extends IView {

  /**
   * User can call this to obtain a textual representation of the game.
   * @return this AnimationModel game as a String
   */
  String textAnimationGame();

}
