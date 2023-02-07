package cs3500.animator.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import cs3500.hw5.shape.AbstractShape;
import javax.swing.JPanel;


/**
 * This panel represents the area where the Shapes themselves must be drawn. Represents the custom
 * drawing of Shapes.
 */
public class ShapePanel extends JPanel {

  protected List<AbstractShape> shapes;

  /**
   * Default constructor for {@code ShapePanel}. Creates a ShapePanel with default values.
   */
  public ShapePanel() {
    super();
    shapes = new ArrayList<AbstractShape>();
    setBackground(Color.WHITE);
    for (int i = 0; i < shapes.size(); i++) {
      AbstractShape current = shapes.get(i);
      add(current);
      //set the order of preference by the order in which they appear
      setComponentZOrder(current, i);

    }
  }

  /**
   * Client calls this when they wish to set the shapes in this shapePanel.
   *
   * @param shapes the updated shapes in this shape panel
   */
  public void setShapes(List<AbstractShape> shapes) {
    this.shapes = shapes;
  }


  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    for (AbstractShape s : shapes) {

      g2.setPaint(new Color(s.getR(), s.getG(), s.getB()));

      s.draw(g);
    }
  }

}
