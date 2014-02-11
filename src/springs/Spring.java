package springs;

import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import springies.Constants;
import forces.Force;
import jboxGlue.PhysicalObject;
import jgame.JGObject;
import masses.Mass;

/**
 * This class defines a Spring and its relationship between 
 * its two mass nodes.  It is a subclass of PhysicalObject and 
 * it draws a line between the two masses. They form the chassis of 
 * an Assembly implying they exert a force on these masses so
 * Spring implements the Force interface.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 */
public class Spring extends PhysicalObject implements Force {
    private Mass mStart;
    private Mass mEnd;
    private double mRestLength;
    private double mKval;

    /**
     * Spring constructor that sets private member attributes
     * @param m1
     * @param m2
     * @param length
     * @param k
     */
    public Spring (Mass m1, Mass m2, double length, double k) {
        super("spring", 1, Constants.DEFAULT_COLOR);
        mStart = m1;
        mEnd = m2;
        mRestLength = length;
        mKval = k;
        m1.connectSpring(this);
        m2.connectSpring(this);
        PolygonDef shape = new PolygonDef();
        this.createBody(shape);
    }

    /**
     * Spring constructor that sets private member attributes
     * @param start
     * @param end
     * @param length
     * @return 
     */
    public Spring (Mass start, Mass end, double length) {
        this(start, end, length, Constants.DEFAULT_KVAL);
    }

    /**
     * Spring constructor that sets private member attributes
     * @param start
     * @param end
     * @return 
     */
    public Spring (Mass start, Mass end) {
        this(start, end, computeLength(start, end), Constants.DEFAULT_KVAL);
    }

    /**
     * Computes the length between the two mass nodes that the spring is to 
     * exert a force on.
     * @param m1
     * @param m2
     * @return 
     */
    public static double computeLength (Mass m1, Mass m2) {
        double m1_x = m1.getBody().getPosition().x;
        double m1_y = m1.getBody().getPosition().y;
        double m2_x = m2.getBody().getPosition().x;
        double m2_y = m2.getBody().getPosition().y;
        return Math.sqrt(Math.pow(m1_x - m2_x, 2)
                         + Math.pow(m1_y - m2_y, 2));
    }

    @Override
    /**
     * Computes Hooke's Law
     * 
     * @param x
     * @param y
     * @return spring force vector
     */
    public Vec2 calculateForce () {
        float x = mEnd.getBody().getPosition().x - mStart.getBody().getPosition().x;
        float y = mEnd.getBody().getPosition().y - mStart.getBody().getPosition().y;
        Vec2 force = new Vec2(x, y);
        force.normalize();
        return force;
    }

    @Override
    /**
     * Computes Hooke's Law
     * 
     * @param mass
     * @return spring force vector
     */
    public Vec2 calculateForce (Mass mass) {
        return Constants.ZERO_VECTOR;
    }

    /**
     * Applies Spring force on the two masses.
     *
     */
    public void doSpringForce () {
        float mag = (float) (mKval * (mRestLength - computeLength(mStart, mEnd)));
        Vec2 force = calculateForce();
        mStart.applyForceVector(force.mul(-1 * mag));
        mEnd.applyForceVector(force.mul(mag));
    }

    /**
     * Prints to console the spring force that is exerted
     * on the two mass nodes
     * 
     * @param force
     * @param mag
     * @return
     */
    public void testSpringForce (Vec2 force, float mag) {
        System.out.printf("Rest length = %.2f, Force length = %.2f, difference = %.2f\n",
                          mRestLength, force.mul(-1 * mag).length(),
                          mRestLength - force.mul(-1 * mag).length());
        System.out.printf("\tForce applied on mass (%s): <%.2f, %.2f>\n",
                          mStart.getName(), force.mul(-1 * mag).x, force.mul(-1 * mag).y);
        System.out.printf("\tForce applied on mass (%s): <%.2f, %.2f>\n",
                          mEnd.getName(), force.mul(mag).x, force.mul(mag).y);
    }

    protected double getLength () {
        return mRestLength;
    }

    protected double setLength (double length) {
        return mRestLength = length;
    }

    /**
     * Getter for starting mass node
     * @return mStart
     */
    public Mass getStart () {
        return mStart;
    }
    
    /**
     * Getter for ending mass node
     * @return mEnd
     */
    public Mass getEnd () {
        return mEnd;
    }

    /**
     * Getter for starting mass node's x position
     * @return mStart.x
     */
    public double getStartX () {
        return mStart.x;
    }

    /**
     * Getter for starting mass node's y position
     * @return mStart.y
     */
    public double getStartY () {
        return mStart.y;
    }

    /**
     * Getter for ending mass node's x position
     * @return mEnd.x
     */
    public double getEndX () {
        return mEnd.x;
    }

    /**
     * Getter for ending mass node's y position
     * @return mEnd.y
     */
    public double getEndY () {
        return mEnd.y;
    }
    
    @Override
    /**
     * Computes the spring force every time the spring moves
     * and creates a displacement (distance between two mass nodes),
     * due to the nature of Hooke's Law.
     */
    public void move () {
        doSpringForce();
        super.move();
    }

    @Override
    protected void paintShape () {
        myEngine.setColor(Constants.DEFAULT_COLOR);
        myEngine.drawLine(getStartX(), getStartY(), getEndX(), getEndY());
    }

    /**
     * No amplitude change should be recorded for regular Spring objects.
     * 
     * @param increase
     */
    public void changeAmplitude (boolean increase) {
    }
    
    @Override
    public void hit (JGObject other)
    {
        if (other.getName() == "wall") {
            // we hit something! bounce off it!
            Vec2 velocity = myBody.getLinearVelocity();
            // is it a tall wall?
            final double DAMPING_FACTOR = 1;
            boolean isSide = other.getBBox().height > other.getBBox().width;
            if (isSide) {
                velocity.x *= -DAMPING_FACTOR;
            }
            else {
                velocity.y *= -DAMPING_FACTOR;
            }
            // apply the change
            myBody.setLinearVelocity(velocity);
        }
    }

}
