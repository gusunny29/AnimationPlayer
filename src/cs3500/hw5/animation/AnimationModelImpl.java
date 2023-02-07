package cs3500.hw5.animation;


import cs3500.animator.util.AnimationBuilder;

import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.moves.ColorMoves;
import cs3500.hw5.moves.GeneralMove;
import cs3500.hw5.moves.KeyFrame;
import cs3500.hw5.moves.PositionMoves;
import cs3500.hw5.moves.SizeMoves;
import cs3500.hw5.moves.TimeMoves;
import cs3500.hw5.shape.AbstractShape;
import cs3500.hw5.shape.Circle;
import cs3500.hw5.shape.Ellipse;
import cs3500.hw5.shape.EquilateralTriangle;
import cs3500.hw5.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the implementation of the {@code AnimationModel} interface. Has a field representing
 * all the Shapes currently inside the animation. INVARIANTS: The animation speed is non-negative,
 * the startTick is non-negative, the lastTick is non-negative, an AbstractMove inside of moves
 * contains a Shape inside of shapes, and the size of shapes must be greater than zero if the size
 * of moves is greater than zero.
 */
public class AnimationModelImpl extends AbstractAnimationModel {

  /**
   * Default constructor for AnimationModelImpl. Creates an animation with no shapes. Speed is set
   * to 10 by default. Current tick is zero.
   */
  public AnimationModelImpl() {
    this(new ArrayList<>(), new ArrayList<AbstractMove>(),
        1, 1, 100,
        new ArrayList<Integer>(Arrays.asList(250, 200, 800, 800)));
  }

  /**
   * Constructs an AnimationModelImpl. Given shapes with default values of 500 for both width and
   * height. Speed is set to 10 by default. Current tick is zero.
   *
   * @param shapes the Shapes to be animated
   */
  public AnimationModelImpl(List<AbstractShape> shapes, List<AbstractMove> moves) {
    this(shapes, moves, 1, 1, 100,
        new ArrayList<Integer>(Arrays.asList(250, 200, 800, 800)));
  }

  /**
   * Constructs an AnimationModelImpl. Given shapes with default values of 500 for both width and
   * height. Speed is set to 10 by default. Current tick is zero.
   *
   * @param shapes the Shapes to be animated
   */
  public AnimationModelImpl(List<AbstractShape> shapes) {
    this(shapes, new ArrayList<AbstractMove>(), 1, 1, 100,
        new ArrayList<Integer>(Arrays.asList(250, 200, 800, 800)));
  }

  /**
   * Constructor for AnimationModelImpl. Calls the last constructor.
   *
   * @param shapes         represents the shapes in the animation
   * @param moves          represents the moves for the animation
   * @param animationSpeed represents the speed of the animation
   * @param bounds         represents the canvas bounds of the animation
   */
  public AnimationModelImpl(List<AbstractShape> shapes, List<AbstractMove> moves,
      int animationSpeed, List<Integer> bounds) {
    this(shapes, moves, animationSpeed, 1, 100, bounds);
    lastTick = getFinalTick();
  }

  /**
   * Constructs an AnimationModelImpl. The shapes are set to the given shapes as well as the given
   * height and width. Current tick is zero.
   *
   * @param shapes the Shapes to be animated
   * @throws IllegalArgumentException if given a non-positive width
   * @throws IllegalArgumentException if given a non-positive height
   * @throws IllegalArgumentException if given a non-empty list of moves and an empty list of
   *                                  shapes
   * @throws IllegalArgumentException if given a startTick or endTick less than zero
   * @throws IllegalArgumentException if given a startTick that is greater than the given endTick
   */
  public AnimationModelImpl(List<AbstractShape> shapes, List<AbstractMove> moves,
      int animationSpeed,
      int startTick, int lastTick, List<Integer> canvas) throws
      IllegalArgumentException {

    if (animationSpeed < 0) {
      throw new IllegalArgumentException("Cannot be given a negative animation speed!");
    }

    if (startTick < 0) {
      throw new IllegalArgumentException("Cannot be given a negative start tick!");
    }

    if (lastTick < 0) {
      throw new IllegalArgumentException("Cannot be given a negative end tick!");
    }

    if (startTick > lastTick) {
      throw new IllegalArgumentException("The given start tick cannot be greater than the " +
          "given endTick!");
    }

    //throw IAE if any of the given moves acts on a Shape that is not in shapes
    for (AbstractMove currentMove : moves) {
      boolean thisMoveHasValidShape = false;
      for (AbstractShape currentShape : shapes) {
        if (currentMove.getToActUpon().equals(currentShape)) {
          thisMoveHasValidShape = true;
        }
      }
      if (!thisMoveHasValidShape) {
        throw new IllegalArgumentException("The given move does not act "
            + "on a shape in this animation!");
      }
    }

    //check if any of the names occur twice in the ArrayList
    for (int i = 0; i < shapes.size(); i++) {
      int count = 0;
      String current = shapes.get(i).getName();

      for (int j = 0; j < shapes.size(); j++) {
        if (shapes.get(j).getName().equals(current)) {
          count++;
        }
      }

      if (count > 1) {
        throw new IllegalArgumentException("Cannot be given the same name for any two shapes!");
      }
    }

    this.lastTick = lastTick;

    List<AbstractMove> movesSorted = new ArrayList<AbstractMove>();

    movesSorted = sortMovesByTime(moves);

    //throw IAE if any moves of the same type have conflicting time intervals
    boolean sameTypeMoveOverlap = doSameTypeMovesOverlap(movesSorted);

    if (sameTypeMoveOverlap) {
      throw new IllegalArgumentException("More than one of the same type of move during the " +
          "same time interval is not allowed!");
    }

    //throw IAE if the size of Shapes does not match
    this.shapes = shapes;
    this.moves = moves;
    this.keyFrames = convertMovesToKeyFrames(moves);
    this.animationSpeed = animationSpeed;
    this.startTick = startTick;
    this.isGamePaused = true;
    xLocation = canvas.get(0);
    yLocation = canvas.get(1);
    canvasWidth = canvas.get(2);
    canvasHeight = canvas.get(3);
  }


  /**
   * Constructs an AnimationModelImpl class. Is given shapes, frames and a canvas.
   *
   * @param shapes Shapes to be in the animation
   * @param frames keyframes to be in the animation
   * @param canvas the canvas that represents the animation
   */
  public AnimationModelImpl(List<AbstractShape> shapes, List<KeyFrame> frames,
      List<AbstractMove> moves,
      List<Integer> canvas) {

    //check if any of the names occur twice in the ArrayList
    for (int i = 0; i < shapes.size(); i++) {
      int count = 0;
      String current = shapes.get(i).getName();

      for (int j = 0; j < shapes.size(); j++) {
        if (shapes.get(j).getName().equals(current)) {
          count++;
        }
      }

      if (count > 1) {
        throw new IllegalArgumentException("Cannot be given the same name for any two shapes!");
      }
    }

    //throw IAE if the size of Shapes does not match
    this.shapes = shapes;
    this.moves = moves;
    this.keyFrames = frames;
    this.keyFrames.addAll(convertMovesToKeyFrames(moves));
    this.animationSpeed = 1;
    this.startTick = 1;
    this.isGamePaused = true;
    xLocation = canvas.get(0);
    yLocation = canvas.get(1);
    canvasWidth = canvas.get(2);
    canvasHeight = canvas.get(3);
    this.lastTick = getFinalTick();
  }

  @Override
  public String keyFrameAsText() {
    String result = "";
    for (KeyFrame k : keyFrames) {

      result += k.getToActUpon().getName() + " " + k.getTick() + " "
          + k.getX() + " " + k.getY() + " " + k.getW() + " " + k.getH() + " " + k.getR()
          + " " + k.getG() + " " + k.getB() + "\n";
    }
    return result;
  }


  @Override
  public String getTextAnimation() {
    //for each shape in Shapes, print their shape log

    String result = "";

    for (int i = 0; i < shapes.size(); i++) {

      AbstractShape s = shapes.get(i);

      //sort the moves by the time they occur
      List<AbstractMove> movesSorted = getMovesForThisShape(s);

      //returns the sorted List of moves for this shape by the time they occur
      movesSorted = sortMovesByTime(movesSorted);

      String intermediate = printHeaderFirstLine("", s, movesSorted);

      result += intermediate;

      if (movesSorted.size() > 0) {
        result += "\n";
      }

      result += printRestOfLines(movesSorted, i);
    }

    return result;
  }

  @Override
  public List<Integer> getBounds() {
    return new ArrayList<Integer>(Arrays.asList(xLocation, yLocation, canvasWidth, canvasHeight));
  }

  @Override
  public void removeShape(String name) {
    for (AbstractShape s : shapes) {
      if (s.getName().equals(name)) {
        shapes.remove(s);
        break;
      }
    }
  }

  @Override
  public void addKeyFrame(KeyFrame toAdd) {
    boolean foundKeyFrame = false;
    for (KeyFrame k : keyFrames) {
      if (k.getToActUpon().getName().equals(toAdd.getToActUpon().getName())
          && k.getTick() == toAdd.getTick()) {
        //editExistingKeyFrame(toAdd);
        foundKeyFrame = true;
        break;
      }
    }
    if (!foundKeyFrame) {
      System.out.println("Have entered not found condition");
      //List<KeyFrame> forThisShape = keyFramesForShape(toAdd.getToActUpon());
      for (int i = 0; i < keyFrames.size(); i++) {
        if (i == keyFrames.size() - 1) {
          System.out.println("Have entered add at back not found condition");
          keyFrames.add(toAdd);
          break;
        }

        //very first keyFrame
        else if (i == 0) {
          KeyFrame first = keyFrames.get(i);

          if (toAdd.getToActUpon().getName().equals(first.getToActUpon().getName())
              && first.getTick() >= toAdd.getTick()) {
            keyFrames.add(i, toAdd);
          }
        }

        //if this shape does not match the next shape
        else if (!keyFrames.get(i).getToActUpon().getName().equals(keyFrames.get(
            i + 1).getToActUpon().getName())) {

          KeyFrame next = keyFrames.get(i + 1);
          KeyFrame current = keyFrames.get(i);

          System.out.println("Have entered different shapes condition");

          if (toAdd.getToActUpon().getName().equals(next.getToActUpon().getName())
              && toAdd.getTick() <= next.getTick()) {
            keyFrames.add(i + 1, toAdd);
            break;
          }

          if (toAdd.getToActUpon().getName().equals(current.getToActUpon().getName())
              && toAdd.getTick() >= current.getTick()) {
            keyFrames.add(i + 1, toAdd);
            break;
          }
        } else {
          KeyFrame next = keyFrames.get(i + 1);
          KeyFrame current = keyFrames.get(i);

          //keyFrame in the middle of two other keyFrames
          if (current.getTick() <= toAdd.getTick() && toAdd.getTick() <= next.getTick()
              && current.getToActUpon().getName().equals(next.getToActUpon().getName())
              && toAdd.getToActUpon().getName().equals(next.getToActUpon().getName())
              && current.getToActUpon().getName().equals(toAdd.getToActUpon().getName())) {
            keyFrames.add(i + 1, toAdd);
            break;
          }
        }
      }
    }
  }

  private List<KeyFrame> keyFramesForShape(AbstractShape s) {
    List<KeyFrame> kfForShape = new ArrayList<KeyFrame>();
    if (s == null) {
      return kfForShape;
    }
    for (KeyFrame key : keyFrames) {
      if (key.getToActUpon().getName().equals(s.getName())) {
        kfForShape.add(key);
      }
    }

    kfForShape.remove(0);

    return kfForShape;
  }

  @Override
  public void removeKeyFrame(KeyFrame toRemove) {
    for (KeyFrame k : keyFrames) {
      if (k.getToActUpon().getName().equals(toRemove.getToActUpon().getName())
          && k.getTick() == toRemove.getTick()) {
        keyFrames.remove(k);
        break;
      }
    }
  }

  public List<KeyFrame> getKeyFrames() {
    return keyFrames;
  }

  @Override
  public void editExistingKeyFrame(KeyFrame newerVersion) throws IllegalArgumentException {
    boolean wasAKeyFrameChanged = false;
    for (int i = 0; i < keyFrames.size(); i++) {
      KeyFrame k = keyFrames.get(i);

      if (k.getToActUpon().getName().equals(newerVersion.getToActUpon().getName())
          && k.getTick() == newerVersion.getTick()) {
        //remove the original keyFrame
        keyFrames.remove(k);
        keyFrames.add(i, newerVersion);
        wasAKeyFrameChanged = true;
        break;
      }
    }

    if (!wasAKeyFrameChanged) {
      throw new IllegalArgumentException("There was no keyFrame already existing that matched the "
          + "given key frame!");
    }
  }

  @Override
  public void setShapes(List<AbstractShape> modelShapesFromView) {
    this.shapes = modelShapesFromView;
  }


  //returns true if any of the same moves overlap
  private boolean doSameTypeMovesOverlap(List<AbstractMove> movesSorted) {

    //size - 1 because if we reach the last one and still haven't returned true, then it will
    //be true no matter what
    for (int i = 0; i < movesSorted.size() - 1; i++) {

      AbstractMove current = movesSorted.get(i);
      AbstractMove next = movesSorted.get(i + 1);

      boolean sameTypeOfMove = current instanceof ColorMoves && next instanceof ColorMoves
          || current instanceof PositionMoves && next instanceof PositionMoves
          || current instanceof SizeMoves && next instanceof SizeMoves
          || current instanceof TimeMoves && next instanceof TimeMoves;

      if (current.getEndTick() > next.getStartTick() && sameTypeOfMove
          && current.getToActUpon() == (next.getToActUpon())) {
        return true;
      }
    }

    return false;
  }


  //returns the String representing the rest of the lines for the shape
  private String printRestOfLines(List<AbstractMove> movesSorted, int i) {

    String result = "";

    if (movesSorted.size() == 1) {
      AbstractMove givenMove = movesSorted.get(i);
      result += givenMove.getMoveSummary();
    }

    //for each move that occurs on this specific shape, add the line to the shapeLog
    for (int j = 0; j < movesSorted.size(); j++) {

      if (j != 0) {
        result += "\n";
      }

      AbstractMove givenMove = movesSorted.get(j);
      AbstractShape shape = givenMove.getToActUpon();

      if (j < movesSorted.size() - 1) {

        AbstractMove next = movesSorted.get(j + 1);

        boolean sameTimeIntervalMoves = givenMove.getStartTick() == next.getStartTick()
            && givenMove.getEndTick() == next.getEndTick();

        if (sameTimeIntervalMoves) {

          String firstHalf = "motion " + shape.getName() + " " + givenMove.getStartTick() + " "
              + shape.getCurrentPosition().getX() + " " + shape.getCurrentPosition().getY()
              + " " + shape.getWidth() + " " + shape.getHeight() + " " + shape.getR() + " "
              + shape.getG() + " " + shape.getB();

          result += firstHalf;
          givenMove.updateValues();
          next.updateValues();

          result += "     " + next.getEndTick() + " " + shape.getCurrentPosition().getX() + " " +
              shape.getCurrentPosition().getY() + " " + shape.getWidth() + " " +
              shape.getHeight() + " " + shape.getR() + " " + shape.getG() + " "
              + shape.getB();
          //increment j to avoid adding repeat moves
          j = j + 1;
        } else {
          result += givenMove.getMoveSummary();
        }

        //check if the startTick for the nextMove is greater than the endTick for this move
        boolean gapInMoves = next.getStartTick() > givenMove.getEndTick();

        AbstractShape current = givenMove.getToActUpon();

        if (gapInMoves) {
          result += "\nmotion " + current.getName() + " " + givenMove.getEndTick() + " " +
              current.getCurrentPosition().getX() + " " + current.getCurrentPosition().getY()
              + " "
              +
              +current.getWidth() + " " + current.getHeight() + " " + current.getR() + " "
              + current
              .getG() + " " +
              current.getB() + "     " + next.getStartTick() + " " +
              current.getCurrentPosition().getX() + " " + current.getCurrentPosition().getY()
              + " "
              +
              +current.getWidth() + " " + current.getHeight() + " " + current.getR() + " "
              + current
              .getG() + " " +
              current.getB();
        }
      }

      //add the last move in the entire sequence
      if (j == movesSorted.size() - 1 && movesSorted.size() > 1) {
        AbstractMove previous = movesSorted.get(j - 1);
        boolean previousTimeMatchesThisTime = givenMove.getStartTick() == previous.getStartTick()
            && givenMove.getEndTick() == previous.getEndTick();
        if (!previousTimeMatchesThisTime) {
          result += givenMove.getMoveSummary();
        }
      }
    }

    if (i != shapes.size() - 1) {
      result += "\n\n";
    }

    return result;
  }

  //prints the header and the first line for the given shape
  private String printHeaderFirstLine(String result, AbstractShape s,
      List<AbstractMove> movesForThisShape) {
    //add the header
    result += "shape " + s.getName() + " " + s.getClass().getSimpleName() + "\n";

    //find the time at when the first move occurs
    int firstMoveTick;

    if (movesForThisShape.size() == 0) {
      firstMoveTick = this.lastTick;
    } else {
      firstMoveTick = firstMove(movesForThisShape);
    }

    //print out the first line of the shape summary
    AbstractMove firstTimeMove = new TimeMoves(s, s.getAppearanceTime(), firstMoveTick);
    result += firstTimeMove.getMoveSummary();

    return result;
  }


  //returns the integer value for the earliest tick move from the given list of moves
  //return -1 if there are no moves in the given list
  protected int firstMove(List<AbstractMove> givenMoves) {
    int firstMoveTime = -1;
    for (AbstractMove m : givenMoves) {
      if (m.getStartTick() < firstMoveTime || firstMoveTime == -1) {
        firstMoveTime = m.getStartTick();
      }
    }
    return firstMoveTime;
  }


  /**
   * A class that helps to connect the given parsing code with our model. Describes constructing any
   * animation, shape-by-shape and motion-by-motion. Adapts from the model interface that the
   * AnimationReader expects to the one our model actually has.
   */
  public static final class Builder implements AnimationBuilder<AnimationModel> {

    //the shapes presently included in this animation
    private List<AbstractShape> shapes;

    //represents all the different moves associated with this animation
    private List<AbstractMove> moves;

    //represents the integers for the canvas and bounds size and location
    private List<Integer> bounds;

    private List<KeyFrame> keyFrames;


    /**
     * Constructor for the Builder class. Builds the object to bridge between our implementation and
     * a common class.
     */
    public Builder() {
      shapes = new ArrayList<AbstractShape>();
      moves = new ArrayList<AbstractMove>();
      keyFrames = new ArrayList<KeyFrame>();
      bounds = new ArrayList<Integer>(Arrays.asList(250, 200, 800, 800));
    }


    //returns the integer value for the earliest tick move from the given list of moves
    //return -1 if there are no moves in the given list
    protected int firstMove(List<AbstractMove> givenMoves) {
      int firstMoveTime = -1;
      for (AbstractMove m : givenMoves) {
        if (m.getStartTick() < firstMoveTime || firstMoveTime == -1) {
          firstMoveTime = m.getStartTick();
        }
      }
      return firstMoveTime;
    }

    @Override
    public AnimationModel build() {
      AbstractAnimationModel current = new AnimationModelImpl(shapes, moves,
          1, bounds);
      for (AbstractShape s : shapes) {
        List<AbstractMove> movesForShape = current.getMovesForThisShape(s);
        int firstTick = firstMove(movesForShape);
        s.setAppearanceTime(firstTick);
      }
      AnimationModel result = new AnimationModelImpl(shapes, keyFrames, moves, bounds);
      return result;
    }


    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      bounds = new ArrayList<Integer>(Arrays.asList(x, y, width, height));

      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type) {

      AbstractShape s;

      if (type.equals("rectangle")) {
        s = new Rectangle(name);
      } else if (type.equals("ellipse")) {
        s = new Ellipse(name);
      } else if (type.equals("triangle")) {
        s = new EquilateralTriangle(name);
      } else {
        s = new Circle(name);
      }

      //add the shape
      shapes.add(s);

      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2,
        int x2, int y2, int w2, int h2, int r2,
        int g2,
        int b2) {
      //make a general constructor for a move with all values (start and end)
      AbstractShape current = null;
      for (AbstractShape s : shapes) {
        if (s.getName().equals(name)) {
          current = s;
        }
      }
      AbstractMove move = new GeneralMove(current, t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2,
          w2, h2, r2, g2, b2);

      //add the move
      moves.add(move);

      //return this object
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addKeyframe(String name, int t, int x, int y, int w,
        int h, int r, int g, int b) {
      AbstractShape s = new Rectangle("Default");

      for (AbstractShape sh : shapes) {
        if (sh.getName().equals(name)) {
          s = sh;
        }
      }

      keyFrames.add(new KeyFrame(s, t, x, y, w, h, r, g, b));

      return this;
    }
  }

}