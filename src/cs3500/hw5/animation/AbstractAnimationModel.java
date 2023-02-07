package cs3500.hw5.animation;

import cs3500.hw5.Posn;
import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.moves.ColorMoves;
import cs3500.hw5.moves.KeyFrame;
import cs3500.hw5.moves.PositionMoves;
import cs3500.hw5.moves.SizeMoves;
import cs3500.hw5.shape.AbstractShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract class for the model interface. Includes fields regarding the shapes for
 * the animation, the speed of the animation, and the currentTick the game is on. INVARIANTS: if the
 * shapes field is empty then the moves field is empty, animationSpeed is positive, currentTick is
 * positive and less than or equal to endTick.
 */

public abstract class AbstractAnimationModel implements AnimationModel {

  //the shapes presently included in this animation
  protected List<AbstractShape> shapes;

  //represents all the different moves associated with this animation
  protected List<AbstractMove> moves;

  //represents all the keyFrames relevant to this animation
  protected List<KeyFrame> keyFrames;

  //the tick speed of this program
  protected int animationSpeed;

  //the current tick this program is on
  protected Integer startTick;

  //represents the last tick of the animation
  protected int lastTick;

  //Represents if the game is paused
  protected boolean isGamePaused;

  //represents the x location of the canvas on the screen
  protected int xLocation;

  //represents the y location of the canvas on the screen
  protected int yLocation;

  //represents the canvas width
  protected int canvasWidth;

  //represents the canvas height
  protected int canvasHeight;


  @Override
  public void addShape(AbstractShape s) {

    boolean matchingShape = false;
    for (AbstractShape sh : shapes) {
      if (sh.getName().equals(s.getName())) {
        matchingShape = true;
      }
    }

    if (!matchingShape) {
      shapes.add(s);
    }
  }

  @Override
  public List<AbstractShape> getAnimationStateAt(int tick) throws IllegalArgumentException {

    List<AbstractShape> shapesOnScreen = new ArrayList<AbstractShape>();

    for (AbstractShape s : shapes) {
      //all the key frames for this given shape
      List<KeyFrame> keyFramesForThisShape = getKeyFramesForThisShape(s);

      if ((keyFramesForThisShape.size() == 0 && tick >= s.getAppearanceTime())
          || tick >= firstKeyFrame(s) && tick <= lastKeyFrame(s)) {
        shapesOnScreen.add(s);
      }
    }

    //loop through the shapes on the screen
    for (int i = 0; i < shapesOnScreen.size(); i++) {

      AbstractShape currentShape = shapesOnScreen.get(i);

      //all the key frames for this given shape
      List<KeyFrame> keyFramesForThisShape = getKeyFramesForThisShape(currentShape);

      for (int j = 0; j < keyFramesForThisShape.size(); j++) {

        KeyFrame current = keyFramesForThisShape.get(j);

        if (current.getTick() <= tick) {
          current.updateValues();
        } else if (current.getTick() > tick) {
          //check the previous keyFrame
          if (j > 0) {
            //get the previous keyFrame
            KeyFrame previous = keyFramesForThisShape.get(j - 1);
            //if the previous has already occurred, linear interpolate
            if (previous.getTick() <= tick) {
              updateShapeKeyFrame(currentShape, previous, current, tick);
            }
          }
        }
      }
    }

    return shapesOnScreen;
  }

  //returns the integer value representing the last KeyFrame of s
  private int lastKeyFrame(AbstractShape s) {
    int tick = getFinalTick();
    for (KeyFrame k : keyFrames) {
      if (k.getToActUpon().getName().equals(s.getName())) {
        if (tick < k.getTick()) {
          tick = k.getTick();
        }
      }
    }

    return tick;
  }

  //returns the integer value representing the first keyFrame of s
  private int firstKeyFrame(AbstractShape s) {
    int tick = s.getAppearanceTime();
    for (KeyFrame k : keyFrames) {
      if (k.getToActUpon().getName().equals(s.getName())) {
        if (k.getTick() < tick) {
          tick = k.getTick();
        }
      }
    }
    return tick;
  }

  //updates the shape, given a key frame
  private void updateShapeKeyFrame(AbstractShape currentShape, KeyFrame previous, KeyFrame current,
      int tick) {
    int r = calcValue(previous.getR(), current.getR(), previous.getTick(),
        current.getTick(), tick);
    int g = calcValue(previous.getG(), current.getG(), previous.getTick(),
        current.getTick(), tick);
    int b = calcValue(previous.getB(), current.getB(), previous.getTick(),
        current.getTick(), tick);

    currentShape.setR(r);
    currentShape.setG(g);
    currentShape.setB(b);

    int x = calcValue(previous.getX(), current.getX(), previous.getTick(),
        current.getTick(), tick);
    int y = calcValue(previous.getY(), current.getY(), previous.getTick(),
        current.getTick(), tick);

    currentShape.setCurrentPosition(new Posn(x, y));

    int width = calcValue(previous.getW(), current.getW(), previous.getTick(),
        current.getTick(), tick);
    int height = calcValue(previous.getH(), current.getH(), previous.getTick(),
        current.getTick(), tick);

    currentShape.setWidth(width);
    currentShape.setHeight(height);
  }

  //returns the getAnimationStateAt version using moves
  protected List<AbstractShape> getAnimationStateAtMoves(int tick) throws
      IllegalArgumentException {

    List<AbstractShape> shapesOnScreen = new ArrayList<AbstractShape>();
    for (AbstractShape s : shapes) {
      if (tick >= s.getAppearanceTime()) {
        shapesOnScreen.add(s);
      }
    }

    for (int i = 0; i < shapes.size(); i++) {
      AbstractShape currentShape = shapes.get(i);

      if (tick < currentShape.getAppearanceTime()) {
        break;
      }

      List<AbstractMove> movesForShape = getMovesForThisShape(currentShape);

      for (int j = 0; j < movesForShape.size(); j++) {

        AbstractMove currentMove = movesForShape.get(j);

        //check if the move was entirely completed before this tick
        if (currentMove.getEndTick() <= tick) {
          //update the values if move has already concluded
          currentMove.updateValues();
        } else if (currentMove.getStartTick() == tick) {
          break;
        } else if (currentMove.getStartTick() > tick) {
          break;
        } else if (currentMove.getStartTick() <= tick && currentMove.getEndTick() >= tick) {

          updateShape(currentMove, currentShape, tick);
        }
      }
    }

    return shapesOnScreen;
  }


  //converts the given moves to keyFrames
  protected List<KeyFrame> convertMovesToKeyFrames(List<AbstractMove> moves) {

    List<KeyFrame> keyFrames = new ArrayList<KeyFrame>();

    for (int j = 0; j < shapes.size(); j++) {

      AbstractShape s = shapes.get(j);

      List<AbstractMove> movesForCurrentShape = getMovesForThisShape(s);

      movesForCurrentShape = sortMovesByTime(movesForCurrentShape);

      List<AbstractShape> shapesAtMoment = getAnimationStateAtMoves(s.getAppearanceTime());

      //add the start state of the shape
      keyFrames.add(new KeyFrame(s, s.getAppearanceTime(), s.getCurrentPosition().getX(),
          s.getCurrentPosition().getY(), s.getWidth(), s.getHeight(), s.getR(),
          s.getG(), s.getB()));

      if (movesForCurrentShape.size() > 0) {
        //the first move for this shape
        AbstractMove firstMove = movesForCurrentShape.get(0);

        //add the beginning state of firstMove to keyframes
        keyFrames
            .add(new KeyFrame(s, firstMove.getStartTick(), firstMove.getCurrentPosition().getX(),
                firstMove.getCurrentPosition().getY(), firstMove.getStartW(), firstMove.getStartH(),
                firstMove.getStartR(), firstMove.getStartG(), firstMove.getStartB()));

      }

      //add the end state for each move for this shape to keyframes
      for (int i = 0; i < movesForCurrentShape.size(); i++) {

        AbstractMove m = movesForCurrentShape.get(i);

        //handleOverlappingMoves(m, s, movesForCurrentShape, i);
        if (i < movesForCurrentShape.size() - 1) {

          AbstractMove next = movesForCurrentShape.get(i + 1);
          //if the moves occur over the same interval

          boolean sameInterval = m.getStartTick() == next.getStartTick() &&
              m.getEndTick() == next.getEndTick();

          boolean gapAfterThisMove = m.getEndTick() < next.getStartTick();

          if (sameInterval) {
            //if this move is color
            if (m instanceof ColorMoves) {
              keyFrames.add(new KeyFrame(s, m.getEndTick(), next.getEndPosition().getX(),
                  next.getEndPosition().getY(), next.getEndW(), next.getEndH(),
                  m.getEndR(), m.getEndG(), m.getEndB()));
            }

            //if this move is size
            else if (m instanceof SizeMoves) {
              keyFrames.add(new KeyFrame(s, m.getEndTick(), next.getEndPosition().getX(),
                  next.getEndPosition().getY(), m.getEndW(), m.getEndH(),
                  next.getEndR(), next.getEndG(), next.getEndB()));
            }

            //if this move is Position
            else if (m instanceof PositionMoves) {
              keyFrames.add(new KeyFrame(s, m.getEndTick(), m.getEndPosition().getX(),
                  m.getEndPosition().getY(), next.getEndW(), next.getEndH(),
                  next.getEndR(), next.getEndG(), next.getEndB()));
            }
            i = i + 1;
          } else if (!gapAfterThisMove) {
            List<AbstractShape> currentShapes = getAnimationStateAtMoves(m.getEndTick());

            for (AbstractShape shape : currentShapes) {
              if (shape.getName().equals(m.getToActUpon().getName())) {
                keyFrames.add(new KeyFrame(s, m.getEndTick(), shape.getCurrentPosition().getX(),
                    shape.getCurrentPosition().getY(), shape.getWidth(), shape.getHeight(),
                    shape.getR(), shape.getG(), shape.getB()));
              }
            }
          } else {
            //add the move itself
            keyFrames.add(new KeyFrame(s, m.getEndTick(), m.getEndPosition().getX(),
                m.getEndPosition().getX(), m.getEndW(), m.getEndH(),
                m.getStartR(), m.getStartG(), m.getStartB()));

            //add the time move after to close the gap
            keyFrames.add(new KeyFrame(s, next.getStartTick(), m.getEndPosition().getX(),
                m.getEndPosition().getX(), m.getEndW(), m.getEndH(),
                m.getStartR(), m.getStartG(), m.getStartB()));
          }
        } else {
          List<AbstractShape> currentShapes = getAnimationStateAtMoves(m.getEndTick());
          for (AbstractShape shape : currentShapes) {
            if (shape.getName().equals(m.getToActUpon().getName())) {
              keyFrames.add(new KeyFrame(s, m.getEndTick(), shape.getCurrentPosition().getX(),
                  shape.getCurrentPosition().getY(), shape.getWidth(), shape.getHeight(),
                  shape.getR(), shape.getG(), shape.getB()));
            }
          }
        }
      }
    }

    return keyFrames;
  }


  //sorts the moves by the time intervals in which they occur
  protected List<AbstractMove> sortMovesByTime(List<AbstractMove> movesForThisShape) {
    //the sorted list of all the moves for this shape by the time they occur
    List<AbstractMove> sorted = new ArrayList<AbstractMove>();

    //a copy of the list of moves for this shape
    List<AbstractMove> copy = new ArrayList<AbstractMove>();

    copy.addAll(movesForThisShape);

    //while there is still some moves left in copy
    while (copy.size() > 0) {

      AbstractMove firstMove = null;

      for (AbstractMove m : copy) {
        if (firstMove == null || m.getStartTick() < firstMove.getStartTick()) {
          firstMove = m;
        }
      }

      //add the firstMove to the back of sorted
      sorted.add(firstMove);

      //remove the firstMove from copy
      copy.remove(firstMove);
    }

    //return the sorted list of moves by their startTick
    return sorted;
  }


  //updates the values of the current shape based off the given move
  private void updateShape(AbstractMove currentMove, AbstractShape currentShape, int tick) {

    int r = calcValue(currentShape.getR(), currentMove.getEndR(), currentMove.getStartTick(),
        currentMove.getEndTick(), tick);
    int g = calcValue(currentShape.getG(), currentMove.getEndG(), currentMove.getStartTick(),
        currentMove.getEndTick(), tick);
    int b = calcValue(currentShape.getB(), currentMove.getEndB(), currentMove.getStartTick(),
        currentMove.getEndTick(), tick);

    currentShape.setR(r);
    currentShape.setG(g);
    currentShape.setB(b);

    int x = calcValue(currentMove.getCurrentPosition().getX(), currentMove.getEndPosition().getX(),
        currentMove.getStartTick(), currentMove.getEndTick(), tick);
    int y = calcValue(currentMove.getCurrentPosition().getY(), currentMove.getEndPosition().getY(),
        currentMove.getStartTick(), currentMove.getEndTick(), tick);

    currentShape.setCurrentPosition(new Posn(x, y));

    int width = calcValue(currentShape.getWidth(), currentMove.getEndW(),
        currentMove.getStartTick(), currentMove.getEndTick(), tick);
    int height = calcValue(currentShape.getHeight(), currentMove.getEndH(),
        currentMove.getStartTick(), currentMove.getEndTick(), tick);

    currentShape.setWidth(width);
    currentShape.setHeight(height);

  }

  //returns the list of AbstractMove for the given Shape
  protected List<AbstractMove> getMovesForThisShape(AbstractShape s) {
    List<AbstractMove> movesForGivenShape = new ArrayList<AbstractMove>();
    //for the given shape, cycle through the moves and return the
    // list of all that apply to this move
    for (AbstractMove move : moves) {
      if (move.getToActUpon().equals(s)) {
        movesForGivenShape.add(move);
      }
    }
    return movesForGivenShape;
  }

  protected int calcValue(int startVal, int endVal, int startTick, int endTick, int tick) {
    double dStartVal = (double) startVal;
    double dStartTick = (double) startTick;
    double dEndVal = (double) endVal;
    double dEndTick = (double) endTick;
    double dTick = (double) tick;

    int finalVal = (int) ((dStartVal * ((dEndTick - dTick) / (dEndTick
        - dStartTick))) + dEndVal * ((dTick - dStartTick) /
        (dEndTick - dStartTick)));
    return finalVal;
  }

  @Override
  public String getTextAnimation() {
    return null;
  }

  @Override
  public int getFinalTick() {
    int last = 0;

    for (KeyFrame k : keyFrames) {
      if (k.getTick() > last) {
        last = k.getTick();
      }
    }
    return last;
  }

  @Override
  public void addMove(AbstractMove m) {
    moves.add(m);
  }

  @Override
  public List<AbstractShape> getShapes() {
    List<AbstractShape> copy = new ArrayList<AbstractShape>();
    copy.addAll(shapes);
    return copy;
  }

  @Override
  public List<AbstractMove> getMoves() {
    List<AbstractMove> copy = new ArrayList<AbstractMove>();
    copy.addAll(moves);
    return copy;
  }

  @Override
  public int getTick() {
    return this.startTick;
  }



  //returns the keyFrames that act on the given shape
  private List<KeyFrame> getKeyFramesForThisShape(AbstractShape currentShape) {
    //ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();
    List<KeyFrame> keyFramesLocal = new ArrayList<KeyFrame>();

    for (KeyFrame k : keyFrames) {
      if (k.getToActUpon().getName().equals(currentShape.getName())) {
        keyFramesLocal.add(k);
      }
    }

    if (keyFramesLocal.size() == 0) {
      List<KeyFrame> staysInPlace = new ArrayList<KeyFrame>();
      staysInPlace
          .add(new KeyFrame(currentShape, getFinalTick(), currentShape.getCurrentPosition().getX(),
              currentShape.getCurrentPosition().getY(), currentShape.getWidth(),
              currentShape.getHeight(), currentShape.getR(),
              currentShape.getG(), currentShape.getB()));
    }
    return keyFramesLocal;
  }

  /*
  @Override
  public void sortKeyFrames() {
    //sorts the moves by the time intervals in which they occur
      //the sorted list of all the moves for this shape by the time they occur
      ArrayList<KeyFrame> sorted = new ArrayList<KeyFrame>();

      //a copy of the list of moves for this shape
      ArrayList<KeyFrame> copy = new ArrayList<KeyFrame>();

      copy.addAll(keyFrames);

      //while there is still some moves left in copy
      while (copy.size() > 0) {

        KeyFrame firstMove = null;

        for (KeyFrame m : copy) {
          if (firstMove == null || m.getTick() < firstMove.getTick()) {
            firstMove = m;
          }
        }

        //add the firstMove to the back of sorted
        sorted.add(firstMove);

        //remove the firstMove from copy
        copy.remove(firstMove);
      }


  }

   */
}