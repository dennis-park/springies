package forces;

import masses.Mass;
import org.jbox2d.common.Vec2;

/**
 * This is a general  class for global environmental 
 * forces. Forces in the world have a magnitude, direction, and exponent
 * (0.0 for a force independent of location). calculateForce will 
 * take x and y values as input (position or velocity) and return a 
 * resulting Vec2 force vector to apply to an object. 
 * 
 * @param x
 * @param y
 * @return
 */
public interface Force {
	double mMagnitude = 0.0;
	double mExponent = 0.0;
    
    /**
     * calculateForce will take as input an x and y value that can represent
     * either the position of the object the force is acting on or the velocity
     * (depends on force). It will then output a Vec2 that can be applied to 
     * a Body object.  
     * 
     * @param x
     * @param y
     * @return
     */
    public Vec2 calculateForce(double x, double y);
    
    /**
     * If calculateForce is not given an argument, it will return a zero vector
     * representing a force that does not exist. 
     * 
     * @param x
     * @param y
     * @return
     */
    public Vec2 calculateForce();
    
    public Vec2 calculateForce (Mass mass);
}
