package cs3500.animator.view;

import cs3500.hw5.shape.Circle;
import cs3500.hw5.shape.Ellipse;
import cs3500.hw5.shape.Rectangle;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.shape.AbstractShape;
import javax.swing.JFrame;


/**
 * Represents the SVG view format for an animation. Can be used to display the animation visually as
 * well.
 */
public class SVGView extends JFrame implements ISVGView {

  private final Appendable svg;
  private final List<Integer> canvasDimensions;
  protected List<AbstractShape> shapes;
  protected List<AbstractMove> moves;


  /**
   * Constructor to initialize an SVG view of the animation.
   *
   * @param canvasDimensions Dimensions that the canvas of the animation would have
   * @param shapes           shapes list from animation
   */
  public SVGView(List<Integer> canvasDimensions,
                 List<AbstractShape> shapes,
                 List<AbstractMove> moves) {
    super();

    this.canvasDimensions = canvasDimensions;

    setPreferredSize(new Dimension(canvasDimensions.get(2), canvasDimensions.get(3)));
    setLocation(canvasDimensions.get(0), canvasDimensions.get(1));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.svg = new StringBuilder();
    this.shapes = shapes;
    this.moves = moves;
  }


  @Override
  public String textAnimationGame() {
    String appendShape = "";
    tryCatch(String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\" xmlns=\""
            + "http://www.w3.org/2000/svg\">\n", this.canvasDimensions.get(2),
        this.canvasDimensions.get(3)));

    for (AbstractShape currentShape : this.shapes) {
      if (currentShape instanceof Rectangle) {
        appendShape = "rect";
      } else if (currentShape instanceof Circle) {
        appendShape = "circle";
      } else if (currentShape instanceof Ellipse) {
        appendShape = "ellipse";
      } else {
        throw new IllegalArgumentException("Invalid shape");
      }
      tryCatch(onlyShape(appendShape, currentShape));

      tryCatch(svgMovesOutput(currentShape, appendShape));

    }
    tryCatch("</svg>");

    return svg.toString();
  }


  private void tryCatch(String attemptToAppend) {
    try {
      svg.append(attemptToAppend);
    } catch (IOException e) {
      throw new IllegalStateException("Bad Appendable");
    }
  }

  private String svgMovesOutput(AbstractShape currentShape, String appendShape) {
    String svg = "";
    for (AbstractMove currentMove : this.getterMoves(currentShape)) {
      int durationTime = currentMove.getEndTick() - currentMove.getStartTick();

      //Checking if The position changed
      if (currentShape.getCurrentPosition().getX() != currentMove.getEndPosition().getX()) {
        String toBeAnimated = "";
        if (appendShape.equals("rect")) {
          toBeAnimated = "x";
        } else {
          toBeAnimated = "cx";
        }
        svg += moveAnimate(currentMove.getStartTick(), durationTime,
            currentShape.getCurrentPosition().getX(),
            currentMove.getEndPosition().getX(), toBeAnimated);
      }
      if (currentShape.getCurrentPosition().getY() != currentMove.getEndPosition().getY()) {
        String toBeAnimated = "";
        if (appendShape.equals("rect")) {
          toBeAnimated = "y";
        } else {
          toBeAnimated = "cy";
        }
        svg += moveAnimate(currentMove.getStartTick(), durationTime,
            currentShape.getCurrentPosition().getY(),
            currentMove.getEndPosition().getY(), toBeAnimated);
      }

      if (currentShape.getWidth() != currentMove.getEndW()) {
        String toBeAnimated = "";
        if (appendShape.equals("rect")) {
          toBeAnimated = "width";
        } else {
          toBeAnimated = "rx";
        }
        svg += moveAnimate(currentMove.getStartTick(), durationTime, currentShape.getWidth(),
            currentMove.getEndW(), "width");
      }
      if (currentShape.getHeight() != currentMove.getEndH()) {
        String toBeAnimated = "";
        if (appendShape.equals("rect")) {
          toBeAnimated = "height";
        } else {
          toBeAnimated = "ry";
        }
        svg += moveAnimate(currentMove.getStartTick(), durationTime, currentShape.getHeight(),
            currentMove.getEndH(), "height");
      }

      //Checking color Moves
      if (currentShape.getR() != currentMove.getEndR()
          || currentShape.getG() != currentMove.getEndG()
          || currentShape.getB() != currentMove.getEndG()) {
        svg += "    <animate attributeType=\"xml\" begin=\"" + currentMove.getStartTick() + "s\" "
            + "dur=\"" + durationTime + "s\" attributeName=\"fill\" from=\"rgb(" +
            currentShape.getR() + ", " + currentShape.getG() + ", " + currentShape.getB()
            + ") \" to=\"" +
            "(" + currentMove.getEndR() + ", " + currentMove.getEndG() + ", " + currentMove
            .getEndB() + ")" +
            "\" fill=\"freeze\"/>\n";
      }

      currentMove.updateValues();
    }

    svg += "</" + appendShape + ">\n\n";

    return svg;

  }

  private String moveAnimate(int startTick, int duration,
      int startMove, int endMove, String toBeAnimated) {

    return "    <animate attributeType=\"xml\" begin=\"" + startTick + "s\" "
        + "dur=\"" + duration + "s\" attributeName=\"" + toBeAnimated + "\" from=\"" + startMove +
        "\" to=\"" + endMove + "\" fill=\"freeze\"/>\n";
  }

  private String onlyShape(String toBeAppended, AbstractShape currentShape) {
    String text = "";
    switch (toBeAppended) {
      case "rect":
        text = String.format("<" + toBeAppended + " id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" "
                + "height=\"%d\" fill=\"rgb(%d, %d, %d)\" visibility= \"visible\" > \n",
            currentShape.getName(),
            currentShape.getCurrentPosition().getX(),
            currentShape.getCurrentPosition().getY(),
            currentShape.getWidth(),
            currentShape.getHeight(),
            currentShape.getR(),
            currentShape.getG(),
            currentShape.getB());
        break;

      case "circle":
        text = String
            .format("<circle id=\"%s\" cx=\"%d\" cy=\"%d\" r=\"%d\" fill=\"rgb(%d, %d, %d)\" "
                    + "visibility = \"visible\" > \n",
                currentShape.getName(),
                currentShape.getCurrentPosition().getX(),
                currentShape.getCurrentPosition().getY(),
                (currentShape.getWidth() / 2),
                currentShape.getR(),
                currentShape.getG(),
                currentShape.getB());
        break;

      case "ellipse":
        text = String.format("<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" "
                + "fill=\"rgb(%d, %d, %d)\" "
                + "visibility = \"visible\" > \n",
            currentShape.getName(),
            currentShape.getCurrentPosition().getX(),
            currentShape.getCurrentPosition().getY(),
            (currentShape.getWidth() / 2),
            (currentShape.getHeight() / 2),
            currentShape.getR(),
            currentShape.getG(),
            currentShape.getB());
        break;

      default:
        throw new IllegalArgumentException("Invalid Shape");
    }

    return text;


  }


  private ArrayList<AbstractMove> getterMoves(AbstractShape s) {
    ArrayList<AbstractMove> movesForGivenShape = new ArrayList<AbstractMove>();
    //for the given shape, cycle through the moves and return the
    // list of all that apply to this move
    for (AbstractMove move : this.moves) {
      if (move.getToActUpon().equals(s)) {
        movesForGivenShape.add(move);
      }
    }
    return movesForGivenShape;
  }

  @Override
  public Integer getTick() {
    throw new UnsupportedOperationException("Not Supported");
  }
}