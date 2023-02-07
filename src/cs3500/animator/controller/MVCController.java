package cs3500.animator.controller;


import cs3500.hw5.Posn;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.view.IView;
import cs3500.animator.view.IVisualView;
import cs3500.hw5.animation.AnimationModel;
import cs3500.hw5.moves.KeyFrame;
import cs3500.hw5.shape.AbstractShape;


/**
 * Controller implementation of the IController interface. Utilizes the model-view-controller
 * design. to promote loose coupling.
 */
public class MVCController implements IController {

  private final AnimationModel model;
  private final IVisualView view;
  private int count;

  /**
   * Client calls this when they wish to construct an object of the controller.
   *
   * @param model represents the model of the animation
   * @param view  represents the view of the animation
   */
  public MVCController(AnimationModel model, IVisualView view) {
    this.model = model;
    this.view = view;
    this.count = 0;
  }


  @Override
  public void playGame() {

    //Sets the shapes and keyframes for the view
    view.setModelShapes(model.getShapes());
    view.setModelKeyFrames(model.getKeyFrames());

    //check if the user clicked a shape
    Posn originalClick = determinedIfClicked(count);
    if (originalClick.getX() != 0 && originalClick.getY() != 0) {
      view.setClickedPosition(originalClick);
    }

    //check for the user removingShapes
    if (view.getWasRemoveClicked()) {

      AbstractShape clicked = view.getShapeClicked(model.getShapes(), view.getterPosition());

      if (clicked != null) {

        model.removeShape(clicked.getName());
        view.setWasRemoveClicked(false);
      }

    }

    //get the newly addedShapes
    List<AbstractShape> addedShapes = view.getShapes();
    List<KeyFrame> removedKeyFrames = view.getRemovedKeyFrames();
    List<KeyFrame> addedKeyFrames = view.getAddKeyFrames();
    List<KeyFrame> editKeyFrames = view.getEditKeyFrames();

    //add every given shape to the model's list of shapes
    for (AbstractShape s : addedShapes) {
      model.addShape(s);
    }

    for (KeyFrame k : removedKeyFrames) {
      model.removeKeyFrame(k);
    }

    for (KeyFrame k : addedKeyFrames) {
      model.addKeyFrame(k);
    }

    for (KeyFrame k : editKeyFrames) {
      for (int i = 0; i < model.getKeyFrames().size(); i++) {
        if (model.getKeyFrames().get(i).getTick() == k.getTick()
            && model.getKeyFrames().get(i).getToActUpon().getName()
            .equals(k.getToActUpon().getName())) {
          model.getKeyFrames().get(i).setX(k.getX());
          model.getKeyFrames().get(i).setY(k.getY());
          model.getKeyFrames().get(i).setW(k.getW());
          model.getKeyFrames().get(i).setH(k.getH());
          model.getKeyFrames().get(i).setR(k.getR());
          model.getKeyFrames().get(i).setG(k.getG());
          model.getKeyFrames().get(i).setB(k.getB());
          break;
        }
      }
    }

    view.setEditKeyFrames(new ArrayList<KeyFrame>());
    view.setRemovedKeyFrames(new ArrayList<KeyFrame>());
    //set the shapes in the view back to be empty
    view.setShapesInView(new ArrayList<AbstractShape>());
    view.setAddKeyFrames(new ArrayList<KeyFrame>());

    view.makeVisible();
  }

  @Override
  public void setViewShapes(List<AbstractShape> shapes) {
    view.setShapes(shapes);
  }

  @Override
  public Integer getCurrentTick() {
    return view.getTick();
  }

  @Override
  public void setCurrentTick(Integer tick) {
    view.setCurrentTick(tick);
  }

  @Override
  public boolean getLooping() {
    return view.getLooping();
  }

  @Override
  public int getAnimationSpeed() {
    return view.getAnimationSpeed();
  }

  @Override
  public IView getView() {
    return view;
  }

  @Override
  public List<AbstractShape> getShapesAt(int tick) {
    return model.getAnimationStateAt(tick);
  }

  @Override
  public void refreshScreen() {
    view.refresh();
  }

  @Override
  public int getLastTick() {
    return model.getFinalTick();
  }

  @Override
  public boolean isGamePaused() {
    return view.getIsGamePaused();
  }

  private Posn determinedIfClicked(int count) {
    Posn originalClick = new Posn(0, 0);

    for (int i = 0; i < model.getShapes().size(); i++) {
      AbstractShape currentShape = model.getShapes().get(i);
      if (shapesContainsPosn(currentShape, view.getClickedPosition())) {
        if (lastShapeContainsPosn(view.getClickedPosition(),
            model.getShapes().subList(i + 1, model.getShapes().size()))) {

          //How do we determine if the shape is on top
          if (this.count > 0) {
            view.eraseRadio();
            view.eraseName();
          }
          view.displayName(currentShape);
          view.setIsGamePaused(true);
          view.displayPrompt();
          this.count++;

          originalClick = new Posn(view.getClickedPosition().getX(),
              view.getClickedPosition().getY());
          view.setNegPosition();
        }
      }
    }

    return originalClick;
  }

  //returns true if the given shape contains the posn
  private boolean shapesContainsPosn(AbstractShape s, Posn currentPosition) {
    //x,y of a shape is the top left of the shape
    if (currentPosition == null) {
      return false;
    } else {
      for (int i = 0; i < model.getShapes().size(); i++) {
        if (s.getCurrentPosition().getX() <= currentPosition.getX()
            && currentPosition.getX() <= s.getCurrentPosition().getX() + s.getWidth()
            && s.getCurrentPosition().getY() <= currentPosition.getY()
            && currentPosition.getY() <= s.getCurrentPosition().getY() + s.getHeight()) {
          return true;
        }
      }
    }

    return false;
  }


  //return true if none of the shapes in the given list contain the given position
  private boolean lastShapeContainsPosn(Posn position, List<AbstractShape> subList) {

    for (int i = subList.size() - 1; i >= 0; i--) {

      if (subList.get(i).getCurrentPosition().getX() <= position.getX()
          && position.getX() <= subList.get(i).getCurrentPosition().getX() + subList.get(i)
          .getWidth()
          && subList.get(i).getCurrentPosition().getY() <= position.getY()
          && position.getY() <= subList.get(i).getCurrentPosition().getY() + subList.get(i)
          .getHeight()) {
        return false;
      }
    }

    return true;
  }
}
