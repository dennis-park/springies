package forces;

import java.util.List;
import masses.Mass;
import org.jbox2d.common.Vec2;


public class COM extends Force {
    private static final double DEFAULT_MAGNITUDE = 50;
    private static final double DEFAULT_EXPONENT = 0.0;
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

    public COM (double magnitude, List<Mass> mass_list) {
        this(magnitude, DEFAULT_EXPONENT, mass_list);
    }

    public COM (List<Mass> mass_list) {
        this(DEFAULT_MAGNITUDE, DEFAULT_EXPONENT, mass_list);
    }

    public void setMassList(List<Mass> mass_list) {
        if (mass_list != null && mass_list.size() > 0) {
            this.mList = mass_list;
        }
    }
    /**
     * Calculations of the center of gravity point based on the list of all masses 
     * R = (1/M) sum (m*r). 
     */
    public Vec2 calculateCOMPoint () {
        if (mList == null || mList.size() == 0) { 
            throw new RuntimeException("call addMassList(List<Mass>) with a MassList of size > 0!"); 
        }

        double sum_mass = 0.0;
        double sum_mr_x = 0.0;
        double sum_mr_y = 0.0;
        
        for (Mass m : mList) {
            Vec2 mass_position = m.getBody().getPosition();
            float mass = m.getBody().m_mass;
            sum_mr_x += mass * mass_position.x;
            sum_mr_y += mass * mass_position.y;
            sum_mass += mass;
            // System.out.printf("\tx = %.2f, y = %.2f, mass = %.2f\n", mass_position.x, mass_position.y, mass);
        }

        float x_com_pt = (float) ((1 / sum_mass) * sum_mr_x);
        float y_com_pt = (float) ((1 / sum_mass) * sum_mr_y);
        
        // System.out.printf("\tCalculating COM point: x=%.2f, y=%.2f\n", x_com_pt, y_com_pt);
        return new Vec2(x_com_pt, y_com_pt); 
    }

    /**
     * Calculations of center of mass forces takes the x and y positions as input and returns a Vec2
     * object representing the center of mass force acting on the mass at that position.
     */
    public Vec2 calculateForce (Mass mass) {
        if (mList == null) { throw new RuntimeException(
                                                        "call addMassList(List<Mass>) before calculateForce()!"); }

        Vec2 com_point = calculateCOMPoint();
        Vec2 mass_position = mass.getBody().getPosition();
        double x_pos = mass_position.x;
        double y_pos = mass_position.y;
        float x_f = (float) (com_point.x - x_pos);
        float y_f = (float) (com_point.y - y_pos);

        Vec2 force = new Vec2();
        force.set(x_f, y_f);

        float distance = force.length();
        float total_magnitude = (float) (mMagnitude / Math.pow(distance, mExponent));

        force.normalize();
        
        // testCOMCalculations(mass, force, total_magnitude);
        return force.mul(total_magnitude);
    }
    
    private void testCOMCalculations(Mass mass, Vec2 force, float total_magnitude) {
        Vec2 v = force.mul(total_magnitude);
        float x_f = v.x;
        float y_f = v.y;
        System.out.printf("\tX = %.2f, Y = %.2f\n", 
                          mass.getBody().getPosition().x, mass.getBody().getPosition().y);
        System.out.printf("\tCenter of Mass vector: mag = %.2f, exponent = %.2f, x = %.2f, y = %.2f\n", 
                          this.mMagnitude, this.mExponent, x_f, y_f);
    }
}
