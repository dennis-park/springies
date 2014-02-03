package springs;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jgame.JGColor;
import masses.Mass;

public class Spring extends PhysicalObject{
	private Mass mStart;
	private Mass mEnd;
	private double mLength;
	private double mKval;
	
	private static final int DEFAULT_KVAL = 1;
	private static JGColor DEFAULT_COLOR = JGColor.blue;

	/**
	 * Spring constructor that sets private member attributes
	 */
	public Spring(Mass m1, Mass m2, double length, double k) {
		super("spring", 1, DEFAULT_COLOR);
		mStart = m1;
		mEnd = m2;
		mLength = length;
		mKval = k;
		m1.connectSpring(this);
		m2.connectSpring(this);
		PolygonDef shape = new PolygonDef();
		this.createBody(shape);
	}
	public Spring(Mass start, Mass end) {
		this(start, end, computeLength(start,end), DEFAULT_KVAL);
	}
	public Spring(Mass start, Mass end, double length){
		this(start, end, length, DEFAULT_KVAL);
	}
	private static double computeLength(Mass m1, Mass m2) {
		return Math.sqrt(Math.pow(m1.x - m2.x, 2)
				+ Math.pow(m1.y - m2.y, 2));
	}
	/**
	 * Computes Hooke's Law
	 * @param x
	 * @param y
	 * @return spring force vector
	 */
	public Vec2 getForce(double x, double y) {
		float mag =	(float) (mKval*(mLength-computeLength(mStart,mEnd)));
		Vec2 force = new Vec2(
				(float)((x-mStart.x)+(x-mEnd.x)),
				(float)((y-mStart.y)+(y-mEnd.y))
				);
		force.normalize();
		force = force.mul(mag);
		return force;
	}
	protected double getLength() {
		return mLength;
	}
	protected double setLength(double length) {
		return mLength = length;
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
	protected void paintShape() {
		myEngine.setColor(DEFAULT_COLOR);
		myEngine.drawLine(getStartX(), getStartY(), getEndX(), getEndY());
	}
}
