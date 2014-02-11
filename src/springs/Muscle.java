package springs;

import springies.Constants;
import masses.Mass;


/**
 * The Muscle class defines a Spring with an amplitude and length 
 * that is affected by time and sinusoidal motion.  User input will change 
 * the amplitude if necessary.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 */

public class Muscle extends Spring {

    private double mAmplitude;
    private double mLength;
    private int mCount;

    /**
     * Muscle constructor with define private member variables.
     * 
     * @param m1
     * @param m2
     * @param amplitude
     * @return 
     */
    public Muscle (Mass m1, Mass m2, double amplitude) {
        super(m1, m2);
        mAmplitude = amplitude;
        mLength = super.getLength();
    }

    /**
     * Muscle constructor with define private member variables.
     * 
     * @param m1
     * @param m2
     * @param length
     * @param amplitude
     * @return 
     */
    public Muscle (Mass m1, Mass m2, double length, double amplitude) {
        super(m1, m2, length);
        mAmplitude = amplitude;
        mLength = length;
    }

    /**
     * Muscle constructor with define private member variables.
     * 
     * @param m1
     * @param m2
     * @param length
     * @param k
     * @param amplitude
     * @return 
     */
    public Muscle (Mass m1, Mass m2, double length, double k, double amplitude) {
        super(m1, m2, length, k);
        mAmplitude = amplitude;
        mLength = length;
    }

    @Override
    /**
     * Muscle will move according to physics defined below.
     * 
     */
    public void move () {
        setLength(mLength + mAmplitude
                  * Math.cos((mCount * Math.PI) / (Constants.DEFAULT_PERIOD / 2)));
        mCount++;
    }

    @Override
    /**
     * Muscle will change amplitude based on a specified increase.
     * 
     * @param increase
     */
    public void changeAmplitude (boolean increase) {
        if (increase) {
            mAmplitude += Constants.AMPLITUDE_INCREMENT;
        }
        else {
            mAmplitude -= Constants.AMPLITUDE_INCREMENT;
        }
    }

}
