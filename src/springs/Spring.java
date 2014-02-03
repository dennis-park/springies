package springs;

import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;
import masses.Mass;

public class Spring extends PhysicalObjectRect{
	private Mass mStart;
	private Mass mEnd;
	private double mLength;
	private double mKval;
	private int mStartX;
	private int mStartY;
	private int mEndX;
	private int mEndY;
	private static double mWidth = 2.0;
	private static double mHeight = 1.0;
	private String springID = "spring";

	private static final int DEFAULT_KVAL = 1;
	private static JGColor DEFAULT_COLOR = JGColor.blue;

	/**
	 * Spring constructor that sets private member attributes
	 */
	public Spring(Mass m1, Mass m2, double length, double k) {
		super("spring", 1, DEFAULT_COLOR, mWidth, mHeight);
		mStart = m1;
		mEnd = m2;
		mLength = length;
		mKval = k;
		
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

	protected double getLength() {
		return mLength;
	}
	protected double setLength(double length) {
		return mLength = length;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
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
	public void updateString(Mass mass_start, Mass mass_end) {
		
	}
	
	public Mass getStart() {
		return mStart;
	}
	public Mass getEnd() {
		return mEnd;
	}
	public int getStartX() {
		return mStartX;
	}
	public int getStartY() {
		return mStartY;
	}
	public int getEndX() {
		return mEndX;
	}
	public int getEndY() {
		return mEndY;
	}
}
