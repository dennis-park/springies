package walls;

import java.util.HashMap;
import masses.Mass;
import org.jbox2d.common.Vec2;
import springies.Springies;
import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;

public class Wall extends PhysicalObjectRect{
	public double mWidth;
	public double mHeight;
	public static double DEFAULT_MASS = 0.0;
	public static int wallid;
	private Springies mSpringies;
	
	public Wall(Springies springies,
				double width,
				double height) {
		super("wall", 2, JGColor.green, width, height, DEFAULT_MASS);
		mSpringies = springies;
		mWidth = width;
		mHeight = height;
	}
	public Wall(double width,
				double height) {
		super("wall", 2, JGColor.green, width, height);
	}
	@Override
	public void move() {
		
	}
	/*public void applyForce() {
		for (Mass m : mSpringies.getAssemblyMap().values) {
			
		}
	}
	
*/
	
	/*
    wall.setPos(displayWidth() / 2, WALL_MARGIN);
    wall = new PhysicalObjectRect("wall", 2, JGColor.green,
                                  WALL_WIDTH, WALL_THICKNESS);
    wall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
    wall = new PhysicalObjectRect("wall", 2, JGColor.green,
                                  WALL_THICKNESS, WALL_HEIGHT);
    wall.setPos(WALL_MARGIN, displayHeight() / 2);
    wall = new PhysicalObjectRect("wall", 2, JGColor.green,
                                  WALL_THICKNESS, WALL_HEIGHT);
    wall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);*/
}