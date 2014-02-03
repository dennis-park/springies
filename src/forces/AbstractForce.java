package forces;

import org.jbox2d.common.Vec2;

public abstract class AbstractForce {
    protected double mMagnitude;
    protected double mDirection;
    protected double xdir;
    protected double ydir;
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
    public abstract void toggleForce();
    
    public abstract Vec2 calculateForce(double x, double y);
    
    public abstract void setMagnitude(double magnitude);
    
    public abstract void setDirection(double direction);
    
    public abstract void setExponent(double exponent);
}
