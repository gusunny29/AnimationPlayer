package cs3500.animator.view;

import cs3500.hw5.Posn;
import cs3500.hw5.moves.AbstractMove;
import cs3500.hw5.moves.KeyFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import cs3500.hw5.shape.AbstractShape;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 * Represents a visual animation view. Draws and plays the animation inside a window, similar to the
 * summary given in HW5.
 */
public class VAView extends JFrame implements IVisualView {

  protected JButton quitButton;
  protected JButton commandButton;
  protected JPanel buttonPanel;
  protected ShapePanel shapePanel;
  protected List<AbstractShape> shapes;
  protected int currentTick;
  protected boolean isLooping;
  protected boolean isPaused;
  protected int animationSpeed;


  /**
   * Constructs an {@code VAView} object. Sets default values.
   */
  public VAView(List<Integer> canvasDimensions, List<AbstractShape> shapes,
                List<AbstractMove> moves) {
    super();
    setTitle("Animation! View mode only.");
    setPreferredSize(new Dimension(canvasDimensions.get(2), canvasDimensions.get(3)));
    setLocation(canvasDimensions.get(0), canvasDimensions.get(1));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setLayout(new BorderLayout());
    shapePanel = new ShapePanel();
    shapePanel.setPreferredSize(new Dimension(800, 800));
    add(shapePanel, BorderLayout.CENTER);

    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    add(buttonPanel, BorderLayout.SOUTH);

    commandButton = new JButton("Execute");
    buttonPanel.add(commandButton);

    //ADD THE QUIT BUTTON
    quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    buttonPanel.add(quitButton);

    //add scrollbars
    JScrollBar horizontal = new JScrollBar(JScrollBar.HORIZONTAL);
    JScrollBar vertical = new JScrollBar(JScrollBar.VERTICAL);

    getContentPane().add(horizontal, BorderLayout.SOUTH);
    getContentPane().add(vertical, BorderLayout.EAST);

    this.shapes = shapes;
    currentTick = 0;
    isLooping = false;
    isPaused = false;
    animationSpeed = 1;

    this.pack();

  }

  /**
   * Default constructor for the VAView with no canvas dimensions specified.
   */
  public VAView() {
    super();
    setTitle("Animation! View mode only.");
    setPreferredSize(new Dimension(800, 800));
    setLocation(250, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setLayout(new BorderLayout());
    shapePanel = new ShapePanel();
    shapePanel.setPreferredSize(new Dimension(600, 600));
    add(shapePanel, BorderLayout.CENTER);

    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    add(buttonPanel, BorderLayout.SOUTH);

    commandButton = new JButton("Execute");
    buttonPanel.add(commandButton);

    //ADD THE QUIT BUTTON
    quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    buttonPanel.add(quitButton);

    //add scrollbars
    JScrollBar horizontal = new JScrollBar(JScrollBar.HORIZONTAL);
    JScrollBar vertical = new JScrollBar(JScrollBar.VERTICAL);

    getContentPane().add(horizontal, BorderLayout.SOUTH);
    getContentPane().add(vertical, BorderLayout.EAST);

    currentTick = 0;
    isLooping = true;
    isPaused = true;
    animationSpeed = 1;

    this.pack();

  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    commandButton.addActionListener(actionEvent);
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
  public void setCurrentTick(Integer tick) {
    this.currentTick = tick;
  }

  @Override
  public boolean getIsGamePaused() {
    return isPaused;
  }

  @Override
  public boolean getLooping() {
    return isLooping;
  }

  @Override
  public int getAnimationSpeed() {
    return animationSpeed;
  }

  @Override
  public Integer getTick() {
    return currentTick;
  }

  @Override
  public Posn getClickedPosition() {
    return new Posn(0,0);
  }

  @Override
  public void displayName(AbstractShape s) {
    //This will never be called
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void setIsGamePaused(boolean pause) {
    //This will never be called
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void setNegPosition() {
    //This will never be called
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void displayPrompt() {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void eraseRadio() {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void eraseName() {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public List<AbstractShape> getShapes() {
    return this.shapes;
  }

  @Override
  public void setShapesInView(List<AbstractShape> shapes) {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }


  @Override
  public AbstractShape getShapeClicked(List<AbstractShape> shapes, Posn clickedPosition) {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void setModelShapes(List<AbstractShape> shapes) {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }


  @Override
  public boolean getWasRemoveClicked() {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void setWasRemoveClicked(boolean b) {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void setClickedPosition(Posn p) {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");

  }

  @Override
  public Posn getterPosition() {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void setModelKeyFrames(List<KeyFrame> keyFrames) {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void setRemovedKeyFrames(List<KeyFrame> keyFrames) {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public List<KeyFrame> getRemovedKeyFrames() {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }

  @Override
  public void setAddKeyFrames(List<KeyFrame> keyFrames) {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");

  }

  @Override
  public List<KeyFrame> getAddKeyFrames() {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");

  }

  @Override
  public List<KeyFrame> getEditKeyFrames() {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");

  }

  @Override
  public void setEditKeyFrames(List<KeyFrame> keyFrames) {
    throw new UnsupportedOperationException("Cannot do this in the visual view!");
  }
}