package model;

/**
 * @author Ethan McCue
 *
 * Interface for objects that can be moved in the main event loop
 */
public interface IMoveable {
  /**
   * Moves the object, called every tick
   */
  void move();
}
