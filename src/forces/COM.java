package forces;

import java.util.List;
import masses.Mass;
import org.jbox2d.common.Vec2;


public class COM extends Force {
    private static final double DEFAULT_MAGNITUDE = 50;
    private static final double DEFAULT_EXPONENT = 0.0;
    private List<Mass> mList;
    private double x_com_pt;
    private double y_com_pt;

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
        calculateCOMPoint();
    }

    public COM (double magnitude, List<Mass> mass_list) {
        this(magnitude, DEFAULT_EXPONENT, mass_list);
    }

    public COM (List<Mass> mass_list) {
        this(DEFAULT_MAGNITUDE, DEFAULT_EXPONENT, mass_list);
    }

    public void setMassList(List<Mass> mass_list) {
        if (mass_list != null && mass_list.size() > 0) {
            this.mList = mass_list;
            calculateCOMPoint();
        }
    }
    /**
     * Calculations of the center of gravity point based on the list of all masses 
     * R = (1/M) sum (m*r). 
     */
    public void calculateCOMPoint () {
        if (mList == null || mList.size() == 0) { 
            throw new RuntimeException("call addMassList(List<Mass>) with a MassList of size > 0!"); 
        }

        double sum_mass = 0.0;
        double sum_mr_x = 0.0;
        double sum_mr_y = 0.0;

        for (Mass m : mList) {
            double mass = m.getMass();
            sum_mr_x += mass * m.x;
            sum_mr_y += mass * m.y;
            sum_mass += mass;
        }

        x_com_pt = (1 / sum_mass) * sum_mr_x;
        y_com_pt = (1 / sum_mass) * sum_mr_y;
    }

    /**
     * Calculations of center of mass forces takes the x and y positions as input and returns a Vec2
     * object representing the center of mass force acting on the mass at that position.
     */
    public Vec2 calculateForce (Mass mass) {
        if (mList == null) { throw new RuntimeException(
                                                        "call addMassList(List<Mass>) before calculateForce()!"); }

        double x_pos = mass.x;
        double y_pos = mass.y;
        float x_f = (float) (x_com_pt - x_pos);
        float y_f = (float) (y_com_pt - y_pos);

        Vec2 force = new Vec2();
        force.set(x_f, y_f);

        float distance = force.length();
        float total_magnitude = (float) (mMagnitude / Math.pow(distance, mExponent));

        force.normalize();
        return force.mul(total_magnitude);
    }
}
