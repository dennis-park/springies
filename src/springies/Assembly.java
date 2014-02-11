package springies;

import java.util.ArrayList;
import masses.Mass;
import springs.Spring;


/**
 * This class defines a spring-mass assembly 
 * and provides methods for adding masses and springs.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 * 
 */
public class Assembly {
    private ArrayList<Mass> mMasses;
    private ArrayList<Spring> mSprings;

    /**
     * Structure contains list of masses and list of springs.
     * 
     */
    public Assembly () {
        mMasses = new ArrayList<Mass>();
        mSprings = new ArrayList<Spring>();
    }
    
    /**
     * Adds specified mass object to assembly's member mass list.
     * @param mass
     */
    public void add (Mass mass) {
        mMasses.add(mass);
    }

    /**
     * Adds specified spring object to assembly's member spring list.
     * 
     * @param spring
     */
    public void add (Spring spring) {
        mSprings.add(spring);
    }
    
    /**
     * Getter for this Assembly object's member mass list.
     * 
     * @return mMasses
     */
    public ArrayList<Mass> getMassList () {
        return this.mMasses;
    }

    /**
     * Getter for this Assembly object's member spring list.
     * 
     * @return mSprings
     */
    public ArrayList<Spring> getSpringList () {
        return this.mSprings;
    }

    /**
     * Getter for Assembly object created.
     * 
     * @return this
     */
    public Assembly getAssembly () {
        return this;
    }
}
