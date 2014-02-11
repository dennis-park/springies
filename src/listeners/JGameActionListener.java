package listeners;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import masses.Mass;
import springies.EnvironmentManager;
import springies.Springies;
import springs.Spring;


/**
 * This class implements a Key Listener in order to toggle walls in (towards center)
 * and out (away from center) in Springies. 
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 * 
 */
public class JGameActionListener {
    public static final double DEFAULT_MOUSE_MASS_XPOSITION = -1;
    public static final double DEFAULT_MOUSE_MASS_YPOSITION = -1;
    
    private Springies mSpringies;
    private EnvironmentManager mEnvironmentManager;
    private boolean leftMouseHeld = false;
    private Mass mMouseMass = new Mass("mouse_mass", DEFAULT_MOUSE_MASS_XPOSITION, DEFAULT_MOUSE_MASS_YPOSITION);
    private Spring mMouseSpring;
    
    public JGameActionListener(Springies s, EnvironmentManager e_manager) {
    	mSpringies = s;
        mEnvironmentManager = e_manager;
    }
 
    public void doKeyEvent (int key) {
        boolean move_walls_out = true;
        
        switch (key) {
        case KeyEvent.VK_UP:
        	System.out.printf("Up Key pressed%d\n", key);
            mEnvironmentManager.moveWalls(move_walls_out);
            break;
        case KeyEvent.VK_DOWN:
        	System.out.printf("Down Key pressed %d\n", key);
            mEnvironmentManager.moveWalls(!move_walls_out);
            break;
        case KeyEvent.VK_N:
        	System.out.printf("N key pressed %d\n", key);
        	mSpringies.makeAssembly();
        	break;
        case KeyEvent.VK_C:
        	System.out.printf("C key pressed %d\n", key);
        	mSpringies.clearLoadedAssemblies();
        	break;
        case KeyEvent.VK_G:
        	System.out.printf("G key pressed %d\n", key);
            mEnvironmentManager.toggleForces(EnvironmentManager.GRAV_ID);
            break;
        case KeyEvent.VK_V:
        	System.out.printf("V key pressed %d\n", key);
            mEnvironmentManager.toggleForces(EnvironmentManager.VISC_ID);
            break;
        case KeyEvent.VK_M:
        	System.out.printf("M key pressed %d\n", key);
            mEnvironmentManager.toggleForces(EnvironmentManager.COM_ID);
            break;
        case KeyEvent.VK_1:
        	System.out.printf("1 key pressed %d\n", key);
        	mEnvironmentManager.toggleWallForces(EnvironmentManager.TOP_ID);
        	break;
        case KeyEvent.VK_2:
        	System.out.printf("2 key pressed %d\n", key);
        	mEnvironmentManager.toggleWallForces(EnvironmentManager.RIGHT_ID);
        	break;
        case KeyEvent.VK_3:
        	System.out.printf("3 key pressed %d\n", key);
        	mEnvironmentManager.toggleWallForces(EnvironmentManager.BOTTOM_ID);
        	break;
        case KeyEvent.VK_4:
        	System.out.printf("4 key pressed %d\n", key);
        	mEnvironmentManager.toggleWallForces(EnvironmentManager.LEFT_ID);
        	break;
        case KeyEvent.VK_EQUALS:
        	System.out.printf("+ key pressed %d\n", key);
        	mEnvironmentManager.changeMuscleAmplitude(true);
        	break;
        case KeyEvent.VK_MINUS:
        	System.out.printf("- key pressed %d\n", key);
        	mEnvironmentManager.changeMuscleAmplitude(false);
        	break;
        }
    }

    public void doMouseEvent (boolean left_pressed, boolean right_pressed, int mouse_x, int mouse_y) {
        boolean left_mouse_released = !left_pressed && leftMouseHeld;
        boolean left_mouse_just_pressed = left_pressed && !leftMouseHeld;
        boolean left_mouse_held = left_pressed && leftMouseHeld;
        
        double mouse_x_double = (double) mouse_x;
        double mouse_y_double = (double) mouse_y;
        
        if (left_mouse_just_pressed) {
            System.out.printf("Left mouse just pressed\n");
            leftMouseHeld = true;
            mMouseMass.setPos(mouse_x_double, mouse_y_double);
            Mass nearest_mass = findNearestMass(mMouseMass);
            mMouseSpring = new Spring(mMouseMass, nearest_mass);
        }
        else if (left_mouse_released) {
            System.out.printf("Left mouse released\n");
            leftMouseHeld = false;
            mMouseMass.setPos(DEFAULT_MOUSE_MASS_XPOSITION, DEFAULT_MOUSE_MASS_YPOSITION);
            mMouseSpring.remove();
        }
        else if (left_mouse_held) {
            System.out.printf("Left mouse held and moved to (%.2f, %.2f)\n", mouse_x_double, mouse_y_double);
            mMouseMass.setPos(mouse_x_double, mouse_y_double);
            mMouseSpring.doSpringForce();
        }
    }

    private Mass findNearestMass (Mass mouse_mass) {
        ArrayList<Mass> all_masses = mEnvironmentManager.getMassList();
        if (all_masses.size() == 0) {
            // THROW ERROR HERE
        }
        double shortest_distance = Double.POSITIVE_INFINITY;
        Mass nearest_mass = all_masses.get(0);
        for (Mass mass: all_masses) {
            double current_distance = Spring.computeLength(mouse_mass, mass);
            if (current_distance < shortest_distance) {
                nearest_mass = mass;
                shortest_distance = current_distance;
            }
        }
        return nearest_mass;
    }
    
    
}