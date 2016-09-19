package entities;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Javier Zazueta
 * Created by javier on 9/15/2016.
 */
public class Clone extends Creature
{
  protected ArrayList<Point2D.Double> path;

  public Clone(ArrayList<Point2D.Double> path)
  {
    this.path = path;
  }

  public void tick()
  {

  }

  public void stepSound()
  {

  }

  public double calculateDistance()
  {
    return 0;
  }
}
