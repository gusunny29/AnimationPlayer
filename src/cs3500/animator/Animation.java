package cs3500.animator;


import java.util.TimerTask;


import cs3500.animator.controller.IController;


/**
 * Represents an actual animation containing the MVC objects.
 */
public class Animation extends TimerTask {

  protected IController controller;
  protected String[] args;

  /**
   * Constructs an animation. Takes in the controller, the speed of the animation and the main
   * arguments.
   *
   * @param controller Controller object used to access the model and view
   * @param args       main arguments
   */
  public Animation(IController controller, String[] args) {
    this.controller = controller;

    this.args = args;
  }

  @Override
  public void run() {

    int tick = controller.getCurrentTick();

    if (!controller.isGamePaused()) {
      controller.setCurrentTick(tick + controller.getAnimationSpeed());
    }

    controller.setViewShapes(controller.getShapesAt(tick));
    controller.refreshScreen();
    controller.playGame();

    if (tick >= controller.getLastTick()) {
      if (controller.getLooping()) {
        controller.setCurrentTick(0);
      }
    }
  }
}
