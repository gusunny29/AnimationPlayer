package cs3500.animator.view;


import static org.junit.Assert.assertEquals;


import cs3500.hw5.Posn;

import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.moves.ColorMoves;
import cs3500.hw5.moves.PositionMoves;
import cs3500.hw5.moves.SizeMoves;
import cs3500.hw5.moves.TimeMoves;
import cs3500.hw5.shape.AbstractShape;
import cs3500.hw5.shape.Circle;
import cs3500.hw5.shape.Ellipse;
import cs3500.hw5.shape.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 * Represents the SVG View Testing for the animation. Confirms the functionality of the SVG View
 * format.
 */
public class SVGViewTest {

  AbstractShape redRectangle = new Rectangle("R", new Posn(200, 200), 50,
      100, 255, 0, 0, 1);

  AbstractShape blueEllipse = new Ellipse("C", new Posn(440, 70), 120, 60,
      0, 0, 255, 6);

  AbstractShape circle = new Circle("CI", new Posn(0, 0), 50, 255,
      0, 0, 1);


  ArrayList<AbstractShape> shapes = new ArrayList<AbstractShape>(
      Arrays.asList(redRectangle, blueEllipse));

  ArrayList<AbstractMove> moves =
      new ArrayList<AbstractMove>(Arrays.asList(
          new PositionMoves(redRectangle, new Posn(200, 200),
              new Posn(300, 300), 10, 50),
          new SizeMoves(redRectangle, 50, 100, 25, 100,
              51, 70),
          new PositionMoves(redRectangle, new Posn(300, 300),
              new Posn(200, 200), 70, 100),
          new PositionMoves(blueEllipse, new Posn(440, 70),
              new Posn(440, 250), 20, 50),
          new PositionMoves(blueEllipse, new Posn(440, 250), new Posn(440, 370),
              50, 70),
          new ColorMoves(blueEllipse, 0, 0, 255, 0, 170, 85,
              50, 70),
          new ColorMoves(blueEllipse, 0, 170, 85, 0, 255, 0,
              70, 80),
          new TimeMoves(blueEllipse, 80, 100)));


  @Test
  public void svgTest() {
    ISVGView svg = new SVGView(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)), shapes,
        moves);
    assertEquals(
        "<svg width=\"3\" height=\"4\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/"
            + "svg\">\n"
            + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255, 0, 0)"
            + "\" visibility= \"visible\" > \n"
            + "    <animate attributeType=\"xml\" begin=\"10s\" dur=\"40s\" attributeName=\"x\" "
            + "from=\"200\" to=\"300\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"10s\" dur=\"40s\" attributeName=\"y\" "
            + "from=\"200\" to=\"300\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"51s\" dur=\"19s\" attributeName=\"x\" "
            + "from=\"300\" to=\"200\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"51s\" dur=\"19s\" attributeName=\"y\" "
            + "from=\"300\" to=\"200\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"51s\" dur=\"19s\" attributeName=\"width\""
            + " from=\"50\" to=\"25\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"70s\" dur=\"30s\" attributeName=\"x\" "
            + "from=\"300\" to=\"200\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"70s\" dur=\"30s\" attributeName=\"y\" "
            + "from=\"300\" to=\"200\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"70s\" dur=\"30s\" attributeName=\"width\""
            + " from=\"25\" to=\"50\" fill=\"freeze\"/>\n"
            + "</rect>\n"
            + "\n"
            + "<ellipse id=\"C\" cx=\"440\" cy=\"70\" rx=\"60\" ry=\"30\" fill=\"rgb(0, 0, 255)\" "
            + "visibility = \"visible\" > \n"
            + "    <animate attributeType=\"xml\" begin=\"20s\" dur=\"30s\" attributeName=\"cy\" "
            + "from=\"70\" to=\"250\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"20s\" dur=\"30s\" attributeName=\"fill\" "
            + "from=\"rgb(0, 0, 255) \" to=\"(0, 0, 255)\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"50s\" dur=\"20s\" attributeName=\"cy\" "
            + "from=\"250\" to=\"370\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"50s\" dur=\"20s\" attributeName=\"fill\" "
            + "from=\"rgb(0, 0, 255) \" to=\"(0, 0, 255)\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"50s\" dur=\"20s\" attributeName=\"cy\" "
            + "from=\"370\" to=\"70\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"50s\" dur=\"20s\" attributeName=\"fill\" "
            + "from=\"rgb(0, 0, 255) \" to=\"(0, 170, 85)\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"70s\" dur=\"10s\" attributeName=\"cy\" "
            + "from=\"370\" to=\"70\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"70s\" dur=\"10s\" attributeName=\"fill\" "
            + "from=\"rgb(0, 170, 85) \" to=\"(0, 255, 0)\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"80s\" dur=\"20s\" attributeName=\"cy\" "
            + "from=\"370\" to=\"70\" fill=\"freeze\"/>\n"
            + "    <animate attributeType=\"xml\" begin=\"80s\" dur=\"20s\" attributeName=\"fill\" "
            + "from=\"rgb(0, 255, 0) \" to=\"(0, 0, 255)\" fill=\"freeze\"/>\n"
            + "</ellipse>\n"
            + "\n"
            + "</svg>", svg.textAnimationGame());


  }

  @Test
  public void onlyShapes() {
    ISVGView svg = new SVGView(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)),
        new ArrayList<AbstractShape>(Arrays.asList(redRectangle, blueEllipse)),
        new ArrayList<AbstractMove>());

    assertEquals(
        "<svg width=\"3\" height=\"4\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg"
            + "\">\n"
            + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255, 0, 0)"
            + "\" visibility= \"visible\" > \n"
            + "</rect>\n"
            + "\n"
            + "<ellipse id=\"C\" cx=\"440\" cy=\"70\" rx=\"60\" ry=\"30\" fill=\"rgb(0, 0, 255)\" "
            + "visibility = \"visible\" > \n"
            + "</ellipse>\n"
            + "\n"
            + "</svg>", svg.textAnimationGame());
  }

  @Test
  public void redRectangleTest() {
    ArrayList<AbstractShape> shapes = new ArrayList<AbstractShape>(
        Arrays.asList(redRectangle));
    ArrayList<AbstractMove> moves = new ArrayList<AbstractMove>(Arrays.asList(
        new PositionMoves(redRectangle, new Posn(200, 200),
            new Posn(300, 300), 10, 50),
        new SizeMoves(redRectangle, 50, 100, 25, 100,
            51, 70)));

    assertEquals("<svg width=\"3\" height=\"4\" version=\"1.1\" xmlns="
        + "\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill="
        + "\"rgb(255, 0, 0)\" visibility= \"visible\" > \n"
        + "    <animate attributeType=\"xml\" begin=\"10s\" dur=\"40s\" attributeName="
        + "\"x\" from=\"200\" to=\"300\" fill=\"freeze\"/>\n"
        + "    <animate attributeType=\"xml\" begin=\"10s\" dur=\"40s\" attributeName="
        + "\"y\" from=\"200\" to=\"300\" fill=\"freeze\"/>\n"
        + "    <animate attributeType=\"xml\" begin=\"51s\" dur=\"19s\" attributeName="
        + "\"x\" from=\"300\" to=\"200\" fill=\"freeze\"/>\n"
        + "    <animate attributeType=\"xml\" begin=\"51s\" dur=\"19s\" attributeName="
        + "\"y\" from=\"300\" to=\"200\" fill=\"freeze\"/>\n"
        + "    <animate attributeType=\"xml\" begin=\"51s\" dur=\"19s\" attributeName="
        + "\"width\" from=\"50\" to=\"25\" fill=\"freeze\"/>\n"
        + "</rect>\n"
        + "\n"
        + "</svg>", new SVGView(new ArrayList<Integer>(Arrays.asList(1,2,3,4)),
        shapes, moves).textAnimationGame());
  }
}

