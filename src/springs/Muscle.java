package springs;

import springies.Constants;
import masses.Mass;


public class Muscle extends Spring {

    private double mAmplitude;
    private double mLength;
    private int mCount;

    public Muscle (Mass m1, Mass m2, double amplitude) {
        super(m1, m2);
        mAmplitude = amplitude;
        mLength = super.getLength();
    }

    public Muscle (Mass m1, Mass m2, double length, double amplitude) {
        super(m1, m2, length);
        mAmplitude = amplitude;
        mLength = length;
    }

    public Muscle (Mass m1, Mass m2, double length, double k, double amplitude) {
        super(m1, m2, length, k);
        mAmplitude = amplitude;
        mLength = length;
    }

    @Override
    public void move () {
        setLength(mLength + mAmplitude
                  * Math.cos((mCount * Math.PI) / (Constants.DEFAULT_PERIOD / 2)));
        mCount++;
    }

    @Override
    public void changeAmplitude (boolean increase) {
        //System.out.printf("Changing amplitude. Increase = %b\n", increase);
        if (increase) {
            mAmplitude += Constants.AMPLITUDE_INCREMENT;
        }
        else {
            mAmplitude -= Constants.AMPLITUDE_INCREMENT;
        }
    }

}
