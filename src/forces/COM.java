package forces;

import java.util.List;

import masses.Mass;

import org.jbox2d.common.Vec2;


public class COM extends AbstractForce {
    protected double xdir;
    protected double ydir;
    private static double COM_DIRECTION = 90;
    private static double COM_EXPONENT = 0.0;
    private List<Mass> mList;
    
    /**
     * This is a gravitational force which applies a resistive force on masses proportional to their
     * velocity.In the data file, this force is indicated by the keyword viscosity followed by a
     * scale value
     * 
     * @param magnitude
     */
    public COM (double magnitude, List<Mass> massList) {
        mMagnitude = magnitude;
        mList = massList;
    }

    /**
     * Calculations of center of mass forces takes the x and y positions as input and returns a Vec2
     * vector which can be used to calculate their effects of moving objects. 
     */
    @Override
    public Vec2 calculateForce (double x, double y) {
    	double x_total = 0.;
    	double y_total = 0.;
		
		for (Mass m : mList) {
			x_total += m.x;
			y_total += m.y;
		}
		double x_com = x_total / mList.size();
		double y_com = y_total / mList.size();
		
		Vec2 com = new Vec2();
        float x_f = (float) (x_com - x);
        float y_f = (float) (y_com - x);
        com.set(x_f, y_f);
		com.normalize();
		com = com.mul((float) (mMagnitude / Math.pow(
				Math.sqrt(Math.pow(x_com - x, 2)
						+ Math.pow(y_com - y, 2)), mExponent)));
        return com;
    }

    @Override
    public void setMagnitude (double magnitude) {
        this.mMagnitude = magnitude;
    }

    @Override
    public void setDirection (double direction) {
        this.mDirection = COM_DIRECTION;
    }

    @Override
    public void setExponent (double exponent) {
        this.mExponent = COM_EXPONENT;
    }

}
