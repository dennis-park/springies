package masses;

import org.jbox2d.common.Vec2;
import springies.Constants;

/**
 * This class defines a Mass object that is fixed in position and does not 
 * move according to the force exerted by its connected springs.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 */
public class FixedMass extends Mass {
    private double mStartX;
    private double mStartY;

    /**
     * Constructor for FixedMass with defining private member variables
     * @param id
     * @param x
     * @param y
     * @param mass
     */
    public FixedMass (String id, double x, double y, double mass) {
        super(id, x, y, mass);
        mStartX = x;
        mStartY = y;
        this.myBody.setLinearVelocity(Constants.ZERO_VECTOR);
    }

    /**
     * Constructor for FixedMass with defining private member variables
     * @param id
     * @param x
     * @param y
     */
    public FixedMass (String id, double x, double y) {
        this(id, x, y, Constants.DEFAULT_MASS);
    }

    @Override
    /**
     * Applies a zero vector force.
     * @param force
     */
    public void applyForceVector (Vec2 force) {
        myBody.applyForce(Constants.ZERO_VECTOR, myBody.m_xf.position);
    }

    @Override
    /**
     * Body has no velocity, as is the nature of a Fixed Mass node.
     */
    public void move () {
        myBody.setLinearVelocity(Constants.ZERO_VECTOR);
        setPos(mStartX, mStartY);
        super.move();
    }

}
