package cs3500.hw5.moves;

import cs3500.hw5.Posn;
import cs3500.hw5.shape.AbstractShape;

/**
 * Represents a KeyFrame containing a destination set of values and time.
 * A type of movement that is an alternate to actually moves themselves.
 */
public class KeyFrame {

  private AbstractShape toActUpon;
  private int tick;
  private int x;
  private int y;
  private int w;
  private int h;
  private int r;
  private int g;
  private int b;

  /**
   * Simplified constructor for the KeyFrame class. Default values are used.
   * @param s the shape to act upon with this keyFrame
   * @param tick the destination tick
   */
  public KeyFrame(AbstractShape s, int tick) {
    this.toActUpon = s;
    this.tick = tick;
    this.x = 0;
    this.y = 0;
    this.w = 25;
    this.h = 25;
    this.r = 255;
    this.g = 0;
    this.b = 0;
  }

  /**
   * Constructor for the KeyFrame class.
   * @param s the shape to act upon with this keyFrame
   * @param tick the destination tick
   * @param x the destination x value
   * @param y the destination y value
   * @param w the destination w value
   * @param h the destination h value
   * @param r the destination r value
   * @param g the destination g value
   * @param b the destination b value
   */
  public KeyFrame(AbstractShape s, int tick, int x, int y, int w, int h, int r, int g, int b) {
    this.toActUpon = s;
    this.tick = tick;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public AbstractShape getToActUpon() {
    return toActUpon;
  }

  public int getTick() {
    return tick;
  }

  public void setTick(int tick) {
    this.tick = tick;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getW() {
    return w;
  }

  public void setW(int w) {
    this.w = w;
  }

  public int getH() {
    return h;
  }

  public void setH(int h) {
    this.h = h;
  }

  public int getR() {
    return r;
  }

  public void setR(int r) {
    this.r = r;
  }

  public int getG() {
    return g;
  }

  public void setG(int g) {
    this.g = g;
  }

  public int getB() {
    return b;
  }

  public void setB(int b) {
    this.b = b;
  }

  /**
   * Updates the values for the shape.
   */
  public void updateValues() {
    toActUpon.setCurrentPosition(new Posn(x, y));
    toActUpon.setWidth(w);
    toActUpon.setHeight(h);
    toActUpon.setR(r);
    toActUpon.setG(g);
    toActUpon.setB(b);
  }
}
