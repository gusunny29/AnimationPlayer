package cs3500.animator.view;

import cs3500.animator.mouse.MouseKeys;
import cs3500.hw5.Posn;
import cs3500.hw5.moves.KeyFrame;
import cs3500.hw5.shape.Circle;
import cs3500.hw5.shape.Ellipse;
import cs3500.hw5.shape.Rectangle;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;


import cs3500.hw5.shape.AbstractShape;

/**
 * Represents a class where the view is displayed visually but can also be edited to pause, start,
 * resume and restart this animation.
 */
public class EditView extends JFrame implements IVisualView {

  private final IVisualView visualView;
  private JPanel inputPanel;
  private JPanel radioPanel;
  private JPanel removePanel;
  private JPanel editPanel;
  private JLabel name;
  private ShapePanel shapePanel;
  private Integer currentTick;
  private boolean isGamePaused;
  private boolean isLooping;
  private int animationSpeed;
  private final MouseKeys mouseEvents;
  private List<AbstractShape> shapes;
  private List<AbstractShape> modelShapes;
  private List<KeyFrame> modelKeyFrames;
  private List<KeyFrame> removeKeyFrames;
  private List<KeyFrame> addKeyFrames;
  private List<KeyFrame> editKeyFrames;
  private boolean wasRemoveShapeClicked;

  /**
   * Constructor for the EditView class. Takes in an IVisualView object to delegate to.
   *
   * @param visualView the visual view class object to be mutated
   */
  public EditView(IVisualView visualView) {
    super();
    setTitle("Animation! Edit mode :)");
    setPreferredSize(new Dimension(1000, 800));
    setLocation(250, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setLayout(new BorderLayout());
    shapePanel = new ShapePanel();
    shapePanel.setLayout(new BoxLayout(shapePanel, BoxLayout.PAGE_AXIS));

    shapePanel.setPreferredSize(new Dimension(600, 600));

    JScrollBar horizontal = new JScrollBar(JScrollBar.HORIZONTAL);
    JScrollBar vertical = new JScrollBar(JScrollBar.VERTICAL);

    getContentPane().add(horizontal, BorderLayout.SOUTH);
    getContentPane().add(vertical, BorderLayout.EAST);

    add(shapePanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    add(buttonPanel, BorderLayout.NORTH);

    JButton startButton = new JButton("Start");

    startButton.addActionListener((ActionEvent e) -> {
      if (this.currentTick < 2) {
        this.currentTick = 0;
        this.isGamePaused = false;
      }
    });
    buttonPanel.add(startButton);

    JButton pauseResumeButton = new JButton("Pause/Resume");
    pauseResumeButton.addActionListener((ActionEvent e) -> {
      this.isGamePaused = !isGamePaused;
    });
    buttonPanel.add(pauseResumeButton);

    JButton enableLooping = new JButton("Enable/Disable Looping");
    enableLooping.addActionListener((ActionEvent e) -> {
      this.isLooping = !isLooping;
    });
    buttonPanel.add(enableLooping);

    JButton increaseSpeedButton = new JButton("> Speed");
    increaseSpeedButton.addActionListener((ActionEvent e) -> {
      this.animationSpeed = animationSpeed * 2;
    });
    buttonPanel.add(increaseSpeedButton);

    JButton decreaseSpeedButton = new JButton("< Speed");
    decreaseSpeedButton.addActionListener((ActionEvent e) -> {
      if (animationSpeed != 1) {
        this.animationSpeed = animationSpeed / 2;
      }
    });
    buttonPanel.add(decreaseSpeedButton);

    //ADD THE QUIT BUTTON
    JButton restartButton = new JButton("Restart");
    restartButton.addActionListener((ActionEvent e) -> {
      this.currentTick = 0;
      isGamePaused = false;
    });

    buttonPanel.add(restartButton);

    JButton addShape = new JButton("Add Shape");
    addShape.addActionListener((ActionEvent e) -> {

      this.isGamePaused = true;

      shapePanel.add(shapeOptions());

    });

    buttonPanel.add(addShape);

    buttonPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    mouseEvents = new MouseKeys();

    shapePanel.addMouseListener(mouseEvents);

    inputPanel = new JPanel();
    inputPanel.setPreferredSize(new Dimension(100, 10));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Click on any shape to edit Animation, " +
            "Click Start to start the animation."));
    inputPanel.setLayout(new FlowLayout());
    JRadioButton acknowledge = new JRadioButton("OK");
    acknowledge.addActionListener((ActionEvent e) -> {
      shapePanel.remove(inputPanel);
    });
    inputPanel.add(acknowledge);
    shapePanel.add(inputPanel);

    this.currentTick = 0;
    this.animationSpeed = 1;
    this.isGamePaused = true;
    this.isLooping = true;
    shapes = new ArrayList<AbstractShape>();
    wasRemoveShapeClicked = false;

    modelShapes = new ArrayList<AbstractShape>();
    removeKeyFrames = new ArrayList<KeyFrame>();
    modelKeyFrames = new ArrayList<KeyFrame>();
    addKeyFrames = new ArrayList<KeyFrame>();
    editKeyFrames = new ArrayList<KeyFrame>();

    shapePanel.setVisible(true);

    this.pack();
    this.visualView = visualView;
  }


  private Component shapeOptions() {
    JPanel shapeRadioPanel = new JPanel();
    shapeRadioPanel.setBorder(BorderFactory.createTitledBorder("Shapes: "));

    shapeRadioPanel.setLayout(new BoxLayout(shapeRadioPanel, BoxLayout.LINE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[4];

    //buttons groups are used to combine radio buttons. Only one radio
    // button in each group can be selected.
    ButtonGroup rGroup1 = new ButtonGroup();
    radioButtons[0] = new JRadioButton("Rectangle");
    radioButtons[0].addActionListener((ActionEvent e) -> {

      String name = JOptionPane.showInputDialog(
          null, "Name of Shape: ");
      int width = Integer.parseInt(JOptionPane.showInputDialog(
          null, "Width of Shape: "));
      int height = Integer.parseInt(JOptionPane.showInputDialog(
          null, "Height of Shape: "));
      List<Integer> prompts = restOfPrompt();

      AbstractShape newShape = new Rectangle(name,
          new Posn(prompts.get(0), prompts.get(1)),
          width, height, prompts.get(2),
          prompts.get(3), prompts.get(4), this.currentTick);

      shapes.add(newShape);

      rGroup1.clearSelection();
    });

    radioButtons[1] = new JRadioButton("Circle");
    radioButtons[1].addActionListener((ActionEvent e) -> {
      shapePanel.remove(shapeRadioPanel);
      String name = JOptionPane.showInputDialog(
          null, "Name of Shape: ");
      int radius = Integer.parseInt(JOptionPane.showInputDialog(
          null, "Radius of Shape: "));

      List<Integer> prompts = restOfPrompt();

      AbstractShape newShape = new Circle(name,
          new Posn(prompts.get(0), prompts.get(1)),
          radius,
          prompts.get(2),
          prompts.get(3), prompts.get(4), this.currentTick);

      shapes.add(newShape);

      rGroup1.clearSelection();

    });

    radioButtons[2] = new JRadioButton("Ellipse");
    radioButtons[2].addActionListener((ActionEvent e) -> {

      String name = JOptionPane.showInputDialog(
          null, "Name of Shape: ");
      int width = Integer.parseInt(JOptionPane.showInputDialog(
          null, "Width of Shape: "));
      int height = Integer.parseInt(JOptionPane.showInputDialog(
          null, "Height of Shape: "));

      List<Integer> prompts = restOfPrompt();

      AbstractShape newShape = new Ellipse(name,
          new Posn(prompts.get(0), prompts.get(1)),
          width,
          height,
          prompts.get(2),
          prompts.get(3), prompts.get(4), this.currentTick);

      shapes.add(newShape);

      rGroup1.clearSelection();

    });

    radioButtons[3] = new JRadioButton("Continue");
    radioButtons[3].addActionListener((ActionEvent e) -> {
      isGamePaused = false;

      shapePanel.remove(shapeRadioPanel);
    });

    for (int i = 0; i < radioButtons.length; i++) {
      rGroup1.add(radioButtons[i]);
      shapeRadioPanel.add(radioButtons[i]);
    }

    return shapeRadioPanel;
  }


  //returns the rest of the prompt as a list of integers
  private List<Integer> restOfPrompt() {

    int x = Integer.parseInt(JOptionPane.showInputDialog(
        null, "X Coordinate of Shape: "));
    int y = Integer.parseInt(JOptionPane.showInputDialog(
        null, "Y Coordinate of Shape: "));
    int rVal = Integer.parseInt(JOptionPane.showInputDialog(
        null, "Red Value of Shape: "));
    int bVal = Integer.parseInt(JOptionPane.showInputDialog(
        null, "Blue Value of Shape: "));
    int gVal = Integer.parseInt(JOptionPane.showInputDialog(
        null, "Green Value of Shape: "));

    return new ArrayList<Integer>(Arrays.asList(x, y, rVal, gVal, bVal));
  }


  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void setCurrentTick(Integer tick) {
    this.currentTick = tick;
  }

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    visualView.setCommandButtonListener(actionEvent);
  }

  @Override
  public void setShapes(List<AbstractShape> shapes) {
    shapePanel.setShapes(shapes);
  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public boolean getLooping() {
    return this.isLooping;
  }

  @Override
  public int getAnimationSpeed() {
    return this.animationSpeed;
  }

  @Override
  public Integer getTick() {
    return this.currentTick;
  }

  @Override
  public boolean getIsGamePaused() {
    return isGamePaused;
  }

  @Override
  public List<AbstractShape> getShapes() {
    return this.shapes;
  }

  @Override
  public void setIsGamePaused(boolean pause) {
    this.isGamePaused = pause;
  }

  @Override
  public void setNegPosition() {
    this.mouseEvents.setNegPosition();

  }

  @Override
  public Posn getClickedPosition() {
    return this.mouseEvents.getClickedPosition();
  }

  public void setClickedPosition(Posn p) {
    this.mouseEvents.setClickedPosition(p);
  }

  @Override
  public Posn getterPosition() {
    return this.mouseEvents.getterPosition();
  }

  @Override
  public void setModelKeyFrames(List<KeyFrame> keyFrames) {
    this.modelKeyFrames = keyFrames;
  }

  @Override
  public void setRemovedKeyFrames(List<KeyFrame> keyFrames) {
    this.removeKeyFrames = keyFrames;
  }

  @Override
  public List<KeyFrame> getRemovedKeyFrames() {
    return this.removeKeyFrames;
  }

  @Override
  public void setAddKeyFrames(List<KeyFrame> keyFrames) {
    this.addKeyFrames = keyFrames;
  }

  @Override
  public List<KeyFrame> getAddKeyFrames() {
    return this.addKeyFrames;
  }

  @Override
  public List<KeyFrame> getEditKeyFrames() {
    return this.editKeyFrames;
  }

  @Override
  public void setEditKeyFrames(List<KeyFrame> keyFrames) {
    this.editKeyFrames = keyFrames;
  }

  @Override
  public void displayName(AbstractShape s) {
    name = new JLabel(s.getName() + "("
        + s.getCurrentPosition().getX() + ", "
        + s.getCurrentPosition().getY() + ")");

    shapePanel.add(name);

  }

  @Override
  public void displayPrompt() {
    //radio buttons
    radioPanel = new JPanel();
    radioPanel.setBorder(BorderFactory.createTitledBorder("Possible Commands:"));

    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[5];

    //buttons groups are used to combine radio buttons. Only one radio
    // button in each group can be selected.
    ButtonGroup rGroup1 = new ButtonGroup();
    radioButtons[0] = new JRadioButton("Add KeyFrame ");
    radioButtons[0].addActionListener((ActionEvent e) -> {
      try {
        Integer tick = Integer.parseInt(JOptionPane.showInputDialog(
            null, "Tick of the KeyFrame you want to add:"));
        outputAddingKeyFrameOptions((int) tick);
      } catch (NumberFormatException o) {
        shapePanel.remove(radioPanel);
      }
      eraseRadio();
      eraseName();

    });
    radioButtons[1] = new JRadioButton("Edit KeyFrame ");
    radioButtons[1].addActionListener((ActionEvent e) -> {
      outputEditKeyFrameOptions();
      eraseRadio();
      eraseName();
    });
    radioButtons[2] = new JRadioButton("Remove KeyFrame");
    radioButtons[2].addActionListener((ActionEvent e) -> {
      outputRemoveKeyFrameOptions();
      eraseRadio();
      eraseName();
    });
    radioButtons[3] = new JRadioButton("Remove Shape ");
    radioButtons[3].addActionListener((ActionEvent e) -> {
      wasRemoveShapeClicked = true;
      eraseRadio();
      eraseName();
    });

    //CONTINUE GAME
    radioButtons[4] = new JRadioButton("Continue Game ");
    radioButtons[4].addActionListener((ActionEvent e) -> {
      eraseRadio();
      eraseName();
      this.isGamePaused = false;
    });

    for (int i = 0; i < radioButtons.length; i++) {
      rGroup1.add(radioButtons[i]);
      radioPanel.add(radioButtons[i]);
    }

    shapePanel.add(radioPanel);
  }

  private void outputModificationBoard(KeyFrame key) {
    JPanel choicePanel = new JPanel();
    choicePanel.setBorder(BorderFactory.createTitledBorder("Shapes: "));

    choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.LINE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[3];

    //buttons groups are used to combine radio buttons. Only one radio
    // button in each group can be selected.
    ButtonGroup rGroup1 = new ButtonGroup();

    radioButtons[0] = new JRadioButton("Position");

    radioButtons[0].addActionListener((ActionEvent e) -> {
      shapePanel.remove(choicePanel);
      try {
        Integer x = Integer.parseInt(JOptionPane.showInputDialog(
            null, "X coordinate you want to edit:"));
        Integer y = Integer.parseInt(JOptionPane.showInputDialog(
            null, "Y coordinate you want to edit:"));
        editKeyFrames.add(new KeyFrame(key.getToActUpon(),
            key.getTick(),
            x,
            y,
            key.getW(),
            key.getH(),
            key.getR(),
            key.getB(),
            key.getG()));
      } catch (NumberFormatException o) {
        shapePanel.remove(choicePanel);
      }
    });

    radioButtons[1] = new JRadioButton("Size");
    radioButtons[1].addActionListener((ActionEvent e) -> {
      shapePanel.remove(choicePanel);
      try {
        Integer width = Integer.parseInt(JOptionPane.showInputDialog(
            null, "Width you want to edit:"));
        Integer height = Integer.parseInt(JOptionPane.showInputDialog(
            null, "Height you want to edit:"));
        editKeyFrames.add(new KeyFrame(key.getToActUpon(),
            key.getTick(),
            key.getX(),
            key.getY(),
            width,
            height,
            key.getR(),
            key.getB(),
            key.getG()));
      } catch (NumberFormatException o) {
        shapePanel.remove(choicePanel);
      }

    });
    radioButtons[2] = new JRadioButton("Color");
    radioButtons[2].addActionListener((ActionEvent e) -> {
      shapePanel.remove(choicePanel);
      try {
        Integer r = Integer.parseInt(JOptionPane.showInputDialog(
            null, "Red you want to edit:"));
        Integer g = Integer.parseInt(JOptionPane.showInputDialog(
            null, "Blue you want to edit:"));
        Integer b = Integer.parseInt(JOptionPane.showInputDialog(
            null, "Green you want to edit:"));

        editKeyFrames.add(new KeyFrame(key.getToActUpon(),
            key.getTick(),
            key.getX(),
            key.getY(),
            key.getW(),
            key.getH(),
            r,
            b,
            g));
      } catch (NumberFormatException o) {
        shapePanel.remove(choicePanel);
      }
    });

    for (int i = 0; i < radioButtons.length; i++) {
      rGroup1.add(radioButtons[i]);
      choicePanel.add(radioButtons[i]);
    }
    shapePanel.add(choicePanel);

  }


  private void outputEditKeyFrameOptions() {
    //Determines the shape at hand
    AbstractShape shape = whichShape();
    //Determines the list of KeyFrames that needs to be affected
    List<KeyFrame> listOfKF = keyFramesForShape(shape);
    //Displays the list of KeyFrames to the user
    displayEditKeyFrames(listOfKF);

  }

  private void displayEditKeyFrames(List<KeyFrame> listOfKF) {
    //radio buttons
    editPanel = new JPanel();
    editPanel.setBorder(BorderFactory.createTitledBorder("Ticks:"));

    editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.PAGE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[listOfKF.size() + 1];
    ButtonGroup rGroup1 = new ButtonGroup();

    for (int i = 0; i < listOfKF.size(); i++) {
      radioButtons[i] = new JRadioButton("" + listOfKF.get(i).getTick());
      int finalI = i;
      radioButtons[i].addActionListener((ActionEvent e) -> {
        shapePanel.remove(editPanel);
        outputModificationBoard(listOfKF.get(finalI));
      });
    }

    radioButtons[radioButtons.length - 1] = new JRadioButton("Continue");
    radioButtons[radioButtons.length - 1].addActionListener((ActionEvent e) -> {
      isGamePaused = false;

      shapePanel.remove(editPanel);
    });

    for (int i = 0; i < radioButtons.length; i++) {
      rGroup1.add(radioButtons[i]);
      editPanel.add(radioButtons[i]);
    }

    shapePanel.add(editPanel);

  }


  private void outputAddingKeyFrameOptions(int tick) {
    //Determines the shape at hand
    AbstractShape shape = whichShape();
    //Determines the list of KeyFrames that needs to be affected
    List<KeyFrame> listOfKF = keyFramesForShape(shape);
    if (listOfKF.get(0).getTick() > tick) {
      addKeyFrames.add(addKF(shape, tick, listOfKF, 0));
    } else if (listOfKF.get(listOfKF.size() - 1).getTick() < tick) {
      addKeyFrames.add(addKF(shape, tick, listOfKF, listOfKF.size() - 1));
    } else if (!keyFramesMatch(listOfKF, tick)) {

      KeyFrame beforeKey = findBefore(tick, listOfKF);
      KeyFrame afterKey = findAfter(tick, listOfKF);
      addKeyFrames.add(calcExistingFrame(beforeKey, afterKey, tick));

    }

  }

  private boolean keyFramesMatch(List<KeyFrame> listOfKF, int tick) {
    for (KeyFrame k : listOfKF) {
      if (k.getTick() == tick) {
        return true;
      }
    }
    return false;

  }

  private KeyFrame calcExistingFrame(KeyFrame beforeKey, KeyFrame afterKey, int tick) {
    int x = calcValue(beforeKey.getX(), afterKey.getX(), beforeKey.getTick(), afterKey.getTick(),
        tick);
    int y = calcValue(beforeKey.getY(), afterKey.getY(), beforeKey.getTick(), afterKey.getTick(),
        tick);
    int width = calcValue(beforeKey.getW(), afterKey.getW(), beforeKey.getTick(),
        afterKey.getTick(), tick);
    int height = calcValue(beforeKey.getH(), afterKey.getH(), beforeKey.getTick(),
        afterKey.getTick(), tick);
    int r = calcValue(beforeKey.getR(), afterKey.getR(), beforeKey.getTick(), afterKey.getTick(),
        tick);
    int g = calcValue(beforeKey.getG(), afterKey.getG(), beforeKey.getTick(), afterKey.getTick(),
        tick);
    int b = calcValue(beforeKey.getB(), afterKey.getB(), beforeKey.getTick(), afterKey.getTick(),
        tick);

    return new KeyFrame(beforeKey.getToActUpon(), tick, x, y, width, height, r, g, b);


  }

  private int calcValue(int startVal, int endVal, int startTick, int endTick, int tick) {
    double dStartTick = (double) startTick;
    double dEndVal = (double) endVal;
    double dEndTick = (double) endTick;
    double dTick = (double) tick;

    int finalVal = (int) (((double) startVal * ((dEndTick - dTick) / (dEndTick
        - dStartTick))) + dEndVal * ((dTick - dStartTick) /
        (dEndTick - dStartTick)));
    return finalVal;
  }

  private KeyFrame addKF(AbstractShape shape, int tick, List<KeyFrame> listOfKF, int size) {
    return new KeyFrame(shape, tick,
        listOfKF.get(size).getX(),
        listOfKF.get(size).getY(),
        listOfKF.get(size).getW(),
        listOfKF.get(size).getH(),
        listOfKF.get(size).getR(),
        listOfKF.get(size).getG(),
        listOfKF.get(size).getB());
  }

  private KeyFrame findAfter(int tick, List<KeyFrame> listOfKF) {
    for (int i = 0; i < listOfKF.size(); i++) {
      if (listOfKF.get(i).getTick() > tick) {
        return listOfKF.get(i);
      }
    }
    return null;
  }

  private KeyFrame findBefore(int tick, List<KeyFrame> listOfKF) {
    for (int i = 0; i < listOfKF.size(); i++) {
      if (listOfKF.get(i).getTick() > tick) {
        return listOfKF.get(i - 1);
      }
    }
    return null;

  }


  private void outputRemoveKeyFrameOptions() {
    //Determines the shape at hand
    AbstractShape shape = whichShape();
    //Determines the list of KeyFrames that needs to be affected
    List<KeyFrame> listOfKF = keyFramesForShape(shape);
    //Outputs the list of keyframes for user input
    displayKeyFrameOptions(listOfKF);

  }

  private void displayKeyFrameOptions(List<KeyFrame> listOfKF) {
    //radio buttons
    removePanel = new JPanel();
    removePanel.setBorder(BorderFactory.createTitledBorder("Ticks:"));

    removePanel.setLayout(new BoxLayout(removePanel, BoxLayout.PAGE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[listOfKF.size() + 1];
    ButtonGroup rGroup1 = new ButtonGroup();

    for (int i = 0; i < listOfKF.size(); i++) {
      radioButtons[i] = new JRadioButton("" + listOfKF.get(i).getTick());
      int finalI = i;
      radioButtons[i].addActionListener((ActionEvent e) -> {
        removeKeyFrames.add(listOfKF.get(finalI));
        shapePanel.remove(removePanel);
      });
    }

    radioButtons[radioButtons.length - 1] = new JRadioButton("Continue");
    radioButtons[radioButtons.length - 1].addActionListener((ActionEvent e) -> {
      isGamePaused = false;

      shapePanel.remove(removePanel);
    });

    for (int i = 0; i < radioButtons.length; i++) {
      rGroup1.add(radioButtons[i]);
      removePanel.add(radioButtons[i]);
    }

    shapePanel.add(removePanel);

  }


  @Override
  public void eraseRadio() {
    shapePanel.remove(radioPanel);
  }

  @Override
  public void eraseName() {
    shapePanel.remove(name);
  }

  @Override
  public void setShapesInView(List<AbstractShape> shapes) {
    this.shapes = shapes;
  }

  public void setModelShapes(List<AbstractShape> shapes) {
    modelShapes = shapes;
  }

  @Override
  public AbstractShape getShapeClicked(List<AbstractShape> shapes, Posn clickPosition) {

    for (int i = modelShapes.size() - 1; i >= 0; i--) {
      if (shapesContainsPosn(modelShapes.get(i), clickPosition)) {
        return modelShapes.get(i);
      }
    }

    return null;
  }

  private AbstractShape whichShape() {
    for (AbstractShape s : modelShapes) {
      if (shapesContainsPosn(s, getterPosition())) {
        return s;
      }
    }
    //this is handled by the method that calls it to ensure no NullPointerException
    return null;
  }

  private List<KeyFrame> keyFramesForShape(AbstractShape s) {
    List<KeyFrame> kfForShape = new ArrayList<KeyFrame>();
    if (s == null) {
      return kfForShape;
    }
    for (KeyFrame key : modelKeyFrames) {
      if (key.getToActUpon().getName().equals(s.getName())) {
        kfForShape.add(key);
      }
    }

    kfForShape.remove(0);

    return kfForShape;
  }

  @Override
  public boolean getWasRemoveClicked() {
    return wasRemoveShapeClicked;
  }

  @Override
  public void setWasRemoveClicked(boolean b) {
    this.wasRemoveShapeClicked = b;
  }

  private boolean shapesContainsPosn(AbstractShape s, Posn currentPosition) {
    //x,y of a shape is the top left of the shape
    if (currentPosition == null) {
      return false;
    } else {
      return s.getCurrentPosition().getX() <= currentPosition.getX()
          && currentPosition.getX() <= s.getCurrentPosition().getX() + s.getWidth()
          && s.getCurrentPosition().getY() <= currentPosition.getY()
          && currentPosition.getY() <= s.getCurrentPosition().getY() + s.getHeight();
    }
  }
}
