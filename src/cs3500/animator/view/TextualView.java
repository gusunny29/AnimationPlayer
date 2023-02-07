package cs3500.animator.view;

import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.shape.AbstractShape;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.List;

import cs3500.hw5.animation.AnimationModel;

import javax.swing.JFrame;


/**
 * Represents a textual view of the animation. Outputs the summary of the animation as text.
 */
public class TextualView extends JFrame implements ITextualView {

  private final List<Integer> canvasDimensions;

  /**
   * TextualView Constructor that will be used to display the shapes and motions of an Animation.
   *
   * @param canvasDimensions dimensions that should be used for the canvas
   * @param shapes           Shapes list from the animation
   */
  public TextualView(List<Integer> canvasDimensions, List<AbstractShape> shapes,
                     List<AbstractMove> moves) {

    //call the super constructor first
    super();

    setPreferredSize(new Dimension(canvasDimensions.get(2), canvasDimensions.get(3)));
    setLocation(canvasDimensions.get(0), canvasDimensions.get(1));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //CLOSE THE GAME WHEN THE USER CLOSES THE FRAME
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //set the layout to be border style
    this.setLayout(new BorderLayout());

    this.canvasDimensions = canvasDimensions;

    this.pack();

  }

  @Override
  public String textBuilder(AnimationModel model) {
    String result = "";

    //add the canvas size and offset to result
    //WHAT TO DO HERE
    result += "Canvas " + canvasDimensions.get(2) + " " + canvasDimensions.get(3) + " " +
        canvasDimensions.get(0) + " " + canvasDimensions.get(1) + "\n";

    //add the getTextAnimation result from the model to result
    result += model.getTextAnimation();

    return result;
  }

  @Override
  public Integer getTick() {
    throw new UnsupportedOperationException("Not allowed in textual view ");
  }
}

