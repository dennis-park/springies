package springies;

import java.util.ArrayList;
import masses.Mass;
import springs.Spring;


public class Assembly {
    private ArrayList<Mass> mMasses;
    private ArrayList<Spring> mSprings;

    public Assembly () {
        mMasses = new ArrayList<Mass>();
        mSprings = new ArrayList<Spring>();
    }

    public void add (Mass mass) {
        mMasses.add(mass);
    }

    /*
     * public void add(FixedMass fixedMass) {
     * mMasses.add(fixedMass);
     * }
     */
    public void add (Spring spring) {
        mSprings.add(spring);
    }

    /*
     * public void add(Muscle muscle) {
     * mSprings.add(muscle);
     * }
     */

    public ArrayList<Mass> getMassList () {
        return this.mMasses;
    }

    public ArrayList<Spring> getSpringList () {
        return this.mSprings;
    }


    public Assembly getAssembly () {
        return this;
    }
}
