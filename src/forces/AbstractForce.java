package forces;

import org.jbox2d.common.Vec2;

public class AbstractForce {
    protected double mMagnitude;
    protected double mDirection;
    protected double mExponent;
    protected boolean forceOn;
    
    /**
     * This is a general abstract class for global environmental 
     * forces. Forces in the world have a magnitude, direction, and exponent
     * (0.0 for a force independent of location). calculateForce will 
     * take x and y values as input (position or velocity) and return a 
     * resulting Vec2 force vector to apply to an object. 
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
