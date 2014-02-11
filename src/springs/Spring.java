package springs;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;

import forces.Force;
import jboxGlue.PhysicalObject;
import jgame.JGColor;
import jgame.JGObject;
import masses.Mass;

public class Spring extends PhysicalObject implements Force{
    private Mass mStart;
    private Mass mEnd;
    private double mRestLength;
    private double mKval;

    public static final double DEFAULT_KVAL = 1;
    public static JGColor DEFAULT_COLOR = JGColor.blue;

    /**
     * Spring constructor that sets private member attributes
     */
    public Spring(Mass m1, Mass m2, double length, double k) {
        super("spring", 1, DEFAULT_COLOR);
        mStart = m1;
        mEnd = m2;
        mRestLength = length;
        mKval = k;
        m1.connectSpring(this);
        m2.connectSpring(this);
        PolygonDef shape = new PolygonDef();
        this.createBody(shape);
    }
    public Spring(Mass start, Mass end, double length){
        this(start, end, length, DEFAULT_KVAL);
    }
    public Spring(Mass start, Mass end) {
        this(start, end, computeLength(start,end), DEFAULT_KVAL);
    }
    public static double computeLength(Mass m1, Mass m2) {
//        System.out.printf("Mass 1 (%.2f, %.2f), Mass 2 (%.2f, %.2f)\n", 
//                          m1.getBody().getPosition().x, m1.getBody().getPosition().y, m2.getBody().getPosition().x, m2.getBody().getPosition().y);
        double m1_x = m1.getBody().getPosition().x;
        double m1_y = m1.getBody().getPosition().y;
        double m2_x = m2.getBody().getPosition().x;
        double m2_y = m2.getBody().getPosition().y;
        return Math.sqrt(Math.pow(m1_x - m2_x, 2)
                         + Math.pow(m1_y - m2_y, 2));
    }
    /** (M1)-------(M2)
     *  <p>
     *  for the force on (M1): 
     *     -kx<x2-x1, y2-y1>     
     * @return
     */
    /**
     * Computes Hooke's Law
     * @param x
     * @param y
     * @return spring force vector
     */
    private Vec2 computeNormalizedForce() {
    	float x = mEnd.getBody().getPosition().x - mStart.getBody().getPosition().x;
        float y = mEnd.getBody().getPosition().y - mStart.getBody().getPosition().y;
        Vec2 force = new Vec2(x, y);
        force.normalize();
        return force;
    }
    @Override
	public Vec2 calculateForce(double x, double y) {
		return new Vec2(0.0f, 0.0f);
	}
	@Override
	public Vec2 calculateForce() {
		float x = mEnd.getBody().getPosition().x - mStart.getBody().getPosition().x;
        float y = mEnd.getBody().getPosition().y - mStart.getBody().getPosition().y;
        Vec2 force = new Vec2(x, y);
        force.normalize();
        return force;
	}
	@Override
	public Vec2 calculateForce(Mass mass) {
		return new Vec2(0.0f, 0.0f);
	}
    public void doSpringForce() {
        float mag = (float) (mKval*(mRestLength-computeLength(mStart,mEnd)));
        Vec2 force = calculateForce();
        // Vec2 force = computeNormalizedForce();
        //testSpringForce(force, mag);
        mStart.applyForceVector(force.mul(-1*mag));
        mEnd.applyForceVector(force.mul(mag));
    }
    public void testSpringForce(Vec2 force, float mag) {
    	System.out.printf("Rest length = %.2f, Force length = %.2f, difference = %.2f\n", 
    			mRestLength, force.mul(-1*mag).length(), mRestLength - force.mul(-1*mag).length());
    	System.out.printf("\tForce applied on mass (%s): <%.2f, %.2f>\n", 
    			mStart.getName(), force.mul(-1*mag).x, force.mul(-1*mag).y); 
    	System.out.printf("\tForce applied on mass (%s): <%.2f, %.2f>\n", 
    			mEnd.getName(), force.mul(mag).x, force.mul(mag).y);
    }

    protected double getLength() {
        return mRestLength;
    }
    protected double setLength(double length) {
        return mRestLength = length;
    }
    public Mass getStart() {
        return mStart;
    }
    public Mass getEnd() {
        return mEnd;
    }
    public double getStartX() {
        return mStart.x;
    }
    public double getStartY() {
        return mStart.y;
    }
    public double getEndX() {
        return mEnd.x;
    }
    public double getEndY() {
        return mEnd.y;
    }

    @Override 
    public void hit(JGObject other) {}
    
    @Override
    public void move() {
        doSpringForce();
        // computeNormalizedForce();
        super.move();
    }

    @Override
    protected void paintShape() {
        myEngine.setColor(DEFAULT_COLOR);
        myEngine.drawLine(getStartX(), getStartY(), getEndX(), getEndY());
    }
}
