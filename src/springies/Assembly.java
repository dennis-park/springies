package springies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import springs.Spring;
import masses.FixedMass;
import masses.Mass;
import factory.Factory;

public class Assembly implements Iterable{
	private ArrayList<Mass> mMasses;
	private ArrayList<Spring> mSprings;
	
	private Factory mFactory;
	
	public Assembly() {
		mMasses = new ArrayList<Mass>();
		mSprings = new ArrayList<Spring>();
	}
	
	public void add(Mass mass) {
		mMasses.add(mass);
	}
	public void add(FixedMass fixedMass) {
		mMasses.add(fixedMass);
	}
	public void add(Spring spring) {
		mSprings.add(spring);
	}
	
	public ArrayList<Mass> getMassList() {
		return this.mMasses;
	}
	public ArrayList<Spring> getSpringList() {
		return this.mSprings;
	}
	
	public Assembly makeAssembly() {
		Assembly assembly = null;
		Factory factory = new Factory();
		factory.makeSprings();
		factory.makeMuscles();
		assembly = new Assembly();
		return assembly;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
