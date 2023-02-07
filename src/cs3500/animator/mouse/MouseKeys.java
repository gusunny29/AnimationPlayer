package cs3500.animator.mouse;


import cs3500.hw5.Posn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents the events that are recorded through mouse use. This allows the position of the mouse
 * to be recorded.
 */
public class MouseKeys extends MouseAdapter {

  private Posn currentPosition;
  private Posn position;

  /**
   * Allows the position of the mouse press to be recorded.
   *
   * @param e the mouse event of press
   */
  @Override
  public void mousePressed(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();

    currentPosition = new Posn(x, y);
    position = new Posn(0, 0);

  }

  /**
   * Allows other classes to access the pressed position.
   *
   * @return Posn of the pressed position
   */
  public Posn getClickedPosition() {
    return this.currentPosition;
  }

  /**
   * Sets the Position to be negative so that the next position won't be mistaken as the previous
   * position.
   */
  public void setNegPosition() {
    this.currentPosition = new Posn(-1, -1);
  }

  /**
   * Sets the position variable to the passed in position.
   *
   * @param p position to be redefined
   */
  public void setClickedPosition(Posn p) {
    this.position = p;
  }

  /**
   * Allows other classes to access this position.
   *
   * @return this position in the class
   */
  public Posn getterPosition() {
    return this.position;
  }


}
