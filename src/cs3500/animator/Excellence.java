package cs3500.animator;


import cs3500.animator.view.EditView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import java.io.PrintStream;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cs3500.animator.controller.IController;
import cs3500.animator.controller.MVCController;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.ISVGView;
import cs3500.animator.view.ITextualView;
import cs3500.animator.view.IView;
import cs3500.animator.view.IVisualView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.VAView;

import cs3500.hw5.animation.AnimationModel;
import cs3500.hw5.animation.AnimationModelImpl;
import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.shape.AbstractShape;



/**
 * Represents the entry-point of the program. The main method is defined here to give away control
 * to the MVC design.
 */
public final class Excellence {

  //returns the last occurrence of the String matching the given keyPhrase
  //arguments
  private static String lastInput(String[] args, String keyPhrase) {
    String result = "";

    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equals(keyPhrase)) {
        result = args[i + 1];
      }
    }

    return result;
  }

  //returns true if the given view is valid
  private static boolean isAValidView(String view) {
    return view.equals("text") || view.equals("visual") || view.equals("svg") || view
        .equals("edit");
  }


  /**
   * Represents the main method. Runs the actual program.
   *
   * @param args the command arguments given to the program.
   */
  public static void main(String[] args) throws IOException {

    handleRestOfMain(args);


  }

  private static void handleRestOfMain(String[] args) throws FileNotFoundException {

    AnimationModel model;
    IView view;
    //get the given view type as a String
    String givenView = lastInput(args, "-view");

    //SPEED OF ANIMATION
    int speedOfAnimation = 1;

    //get the given ticks per second as a String
    String ticksPerSec = lastInput(args, "-speed");

    if (!ticksPerSec.equals("")) {
      speedOfAnimation = Integer.valueOf(ticksPerSec);
    }

    //OUTPUT FILE
    PrintStream out = System.out;

    //get the given output file as a String
    String givenOutputFile = lastInput(args, "-out");

    //if there is an output stream given, write to that file
    if (!givenOutputFile.equals("")) {
      out = new PrintStream(givenOutputFile);
    }

    //get the given input file as a String
    String givenInputFile = lastInput(args, "-in");

    //throw exception if no input file is given
    if (givenInputFile.equals("")) {
      throw new IllegalArgumentException("Must be given a valid input file!");
    }

    //the file to read from
    File source = new File(givenInputFile);

    FileReader fileReader = new FileReader(source);

    AnimationReader entireReader = new AnimationReader();

    //instantiate the builder object using the outer class
    AnimationModelImpl.Builder builder = new AnimationModelImpl.Builder();

    //how do we actually use the animationReader to read the file?
    model = entireReader.parseFile(fileReader, builder);

    view = getViewType(givenView, model.getBounds(), model.getShapes(), model.getMoves(),
        model.getTick());

    if (view instanceof IVisualView) {
      IVisualView convertedView = (IVisualView) view;

      IController controller = new MVCController(model, convertedView);

      Timer t = new Timer();

      TimerTask task = new Animation(controller, args);

      t.schedule(task, 2000, 100);
    } else if (view instanceof ITextualView) {
      out.append(((TextualView) view).textBuilder(model));
    } else if (view instanceof ISVGView) {
      SVGView viewConverted = (SVGView) view;
      out.append(viewConverted.textAnimationGame());
    } else {
      EditView viewConverted = (EditView) view;
      viewConverted.setCurrentTick(model.getTick());
      IController controller = new MVCController(model, viewConverted);
      Timer t = new Timer();
      TimerTask task = new Animation(controller, args);
    }
  }


  //returns the view representing the given String
  private static IView getViewType(String givenView, List<Integer> bounds,
                                   List<AbstractShape> shapes, List<AbstractMove> moves,
                                   Integer tick) {

    if (!isAValidView(givenView)) {
      throw new IllegalArgumentException(
          "The given view must be either text, visual, svg, or edit!");
    }

    if (givenView.equals("text")) {
      return new TextualView(bounds, shapes, moves);
    } else if (givenView.equals("visual")) {
      return new VAView(bounds, shapes, moves);
    } else if (givenView.equals("svg")) {
      return new SVGView(bounds, shapes, moves);
    } else {
      return new EditView(new VAView(bounds, shapes, moves));
    }
  }
}