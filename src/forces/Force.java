package forces;

import masses.Mass;
import org.jbox2d.common.Vec2;


/**
 * This is a general class for global environmental forces. Forces in the world have a magnitude,
 * calculateForce will take a Mass object as input (position or velocity) and return a resulting
 * Vec2 force vector to apply to an object.
 * 
 * We assumed that only Mass elements will affect the calculation of these forces (i.e., all other
 * elements are mass-less). These forces should be set in an optional second data file, called
 * environment.xml in the assets folder, that describes the environmental conditions.
 * 
 */
public interface Force {
    double mMagnitude = 0.0;

    /**
     * If calculateForce is not given an argument, it should a zero vector representing a force that
     * does not exist.
     * 
     * @return
     */
    public Vec2 calculateForce ();

    /**
     * calculateForce will take as input an x and y value that can represent
     * either the position of the object the force is acting on or the velocity
     * (depends on force). It will then output a Vec2 that can be applied to
     * a Body object.
     * 
     * @param Mass mass
     * @return Vec2 force
     */
    public Vec2 calculateForce (Mass mass);
}
