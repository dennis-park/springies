package forces;

import java.util.List;
import masses.Mass;
import org.jbox2d.common.Vec2;

public class COM extends AbstractForce {
    private static double DEFAULT_EXPONENT = 0.0;
    private List<Mass> mList;

    /**
     * This is an imaginary force which attracts all masses toward their calculated center of mass.
     * In the data file, this force is indicated by the keyword centermass followed by a magnitude
     * and an exponent value. An exponent value of 2.0 means inverse-square force (the force is
     * inversely proportional to the distance squared). A value of 0.0 is a constant force
     * independent of position. If the magnitude of this force is negative, it becomes a repulsion
     * force.
     * 
     * @param magnitude
     */
    public COM (double magnitude, double exponent, List<Mass> mass_list) {
        mMagnitude = magnitude;
        mExponent = exponent;
        mList = mass_list;
    }

    public COM (double magnitude, double exponent) {
        this(magnitude, exponent, null);
    }

    public COM (double magnitude) {
        this(magnitude, DEFAULT_EXPONENT, null);
    }

    public void addMassList (List<Mass> mass_list) {
        mList = mass_list;
    }

    /**
     * Calculations of center of mass forces takes the x and y positions as input and returns a Vec2
     * vector which can be used to calculate their effects of moving objects.
     */
    public Vec2 calculateForce (double x, double y) {
        if (mList == null) { 
            throw new RuntimeException("call addMassList(List<Mass>) before calculateForce()!"); 
        }
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
        com =
                com.mul((float) (mMagnitude / Math.pow(
                                                       Math.sqrt(Math.pow(x_com - x, 2)
                                                                 + Math.pow(y_com - y, 2)),
                                                       mExponent)));
        return com;
    }
}
