package entities;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Ben Matthews
 *         Class that contains fields and methods
 *         that are common between all creatures
 *         (player + monsters)
 */
public abstract class Creature extends Entity
{
  public double speed;
  public double angle;
  double stepDistance;

  double lastX;
  double lastZ;
  private double distanceTraveled;

  ArrayList<CreaturePathInfo> pathTaken;
  //boolean engaged = false;
  int health;

  /**
   * Get distance that the zombie has traveled and
   * play a sound effect if the zombie is close enough
   * to the player.
   */
  void updateDistance()
  {
    distanceTraveled += calculateDistance();
    if (distanceTraveled > stepDistance)
    {
      distanceTraveled = 0;
      stepSound();
    }
  }


  /**
   * For zombie and player, calculates distance between the last
   * and current locations.
   *
   * @return The square root of the sum of the squares of deltaX and
   * deltaZ.
   */
  public abstract double calculateDistance();

  /**
   * Plays sound effects for player and zombies.
   */
  public abstract void stepSound();

}
