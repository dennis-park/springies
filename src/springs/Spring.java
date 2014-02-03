package springs;

import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;
import masses.Mass;

public class Spring extends PhysicalObject{
	private Mass mStart;
	private Mass mEnd;
	private double mLength;
	private double mKval;
	private int mStartX;
	private int mStartY;
	private int mEndX;
	private int mEndY;
	private double mWidth;
	private double mHeight;

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
		
	}
	private void init(double width, double height, double mass) {
		mWidth = width;
		mHeight = height;
		// make it a rect
		PolygonDef shape = new PolygonDef();
		shape.density = (float) mass;
		shape.setAsBox((float) width, (float) height);
		createBody(shape);
		setBBox(-(int) width / 2, -(int) height / 2, (int) width, (int) height);
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
		// either the myFirst or mySecond coordinates should be equivalent and
		// so have no effect
		Vec2 force = new Vec2(
				(float)((x-mStart.x)+(x-mEnd.x)),
				(float)((y-mStart.y)+(y-mEnd.y))
				);
		force.normalize();
		force = force.mul(mag);
		return force;
	}
	public void updateString() {
		//TODO
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
	@Override
	protected void paintShape() {
		// TODO Auto-generated method stub
		
	}
}
