package springs;

import org.jbox2d.common.Vec2;
import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;
import masses.Mass;

public class Spring extends PhysicalObjectRect{
	private Mass mStart, mEnd;
	private double mLength, mKval;
	private int mStartX, mStartY, mEndX, mEndY;

	private static final int DEFAULT_KVAL = 1;
	private static JGColor DEFAULT_COLOR = JGColor.blue;

	/**
	 * Spring constructor that sets private member attributes
	 */
	public Spring(Mass m1, Mass m2, double length, double k) {
		super("spring", 1, DEFAULT_COLOR, pfwidth, pfheight);
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
		return Math.sqrt(Math.pow(m1.getX() - m2.getX(), 2)
				+ Math.pow(m1.getY() - m2.getY(), 2));
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
		// either the myFirst or mySecond coordinates should be equivalent and
		// so have no effect
		Vec2 force = new Vec2(
				(float)((x-mStart.getX())+(x-mEnd.getX())),
				(float)((y-mStart.getY())+(y-mEnd.getY()))
				);
		force.normalize();
		force = force.mul(mag);
		return force;
	}

	@Override
	protected void paintShape() {
		myEngine.setColor(myColor);
		myEngine.drawLine(mStartX, mEndY, mStartX, mEndY);
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
