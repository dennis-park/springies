package forces;

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
public class Force {
    protected double mMagnitude;
    protected double mDirection;
    protected double mExponent;
    protected boolean forceOn;
    
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
    public Vec2 calculateForce(double x, double y) {
        return new Vec2(0.0f, 0.0f);
    }
    
    /**
     * If calculateForce is not given an argument, it will return a zero vector
     * representing a force that does not exist. 
     * 
     * @param x
     * @param y
     * @return
     */
    public Vec2 calculateForce() {
        return new Vec2(0.0f, 0.0f);
    }
   
    public void turnOn () {
        forceOn = true;
    }
    
    public void turnOff () {
        forceOn = false;
    }
    
    public void setMagnitude (double magnitude) {
        this.mMagnitude = magnitude;
    }
    
    public void setDirection (double direction) {
        this.mDirection = direction;
    }

    public void setExponent (double exponent) {
        this.mExponent = exponent;
    }
}
