package cs3500.animator.view;

/**
 * An interface that encompasses all the different types of Views.
 */
public interface IView {

  /**
   * allows classes to get the value of the tick.
   * @return the integer value of the current tick
   */
  Integer getTick();

}
