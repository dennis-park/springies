package springies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import springs.Muscle;
import springs.Spring;
import masses.FixedMass;
import masses.Mass;
import factory.Factory;
import factory.ModelFactory;

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
	/*public void add(FixedMass fixedMass) {
		mMasses.add(fixedMass);
	}*/
	public void add(Spring spring) {
		mSprings.add(spring);
	}
	/*public void add(Muscle muscle) {
		mSprings.add(muscle);
	}*/
	
	public ArrayList<Mass> getMassList() {
		return this.mMasses;
	}
	public ArrayList<Spring> getSpringList() {
		return this.mSprings;
	}
	
	public void updateAssembly(Springies s) {
		ModelFactory factory = new ModelFactory(s);
	}
	
	public Assembly getAssembly() {
		return this;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
