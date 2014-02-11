package listeners;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import masses.Mass;
import masses.MouseMass;
import springies.EnvironmentManager;
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
    
    private EnvironmentManager mEnvironmentManager;
    private boolean leftMouseHeld = false;
    private Mass mMouseMass = new MouseMass("mouse_mass", DEFAULT_MOUSE_MASS_XPOSITION, DEFAULT_MOUSE_MASS_YPOSITION);
    private Spring mMouseSpring;
    
    public JGameActionListener(EnvironmentManager e_manager) {
        mEnvironmentManager = e_manager;
    }
 
    public void doKeyEvent (int key) {
        boolean move_walls_out = true;
        
        if (key == KeyEvent.VK_UP) {
            System.out.printf("Up Key pressed%d\n", key);
            mEnvironmentManager.moveWalls(move_walls_out);
        }
        if (key == KeyEvent.VK_DOWN) {
            System.out.printf("Down Key pressed %d\n", key);
            mEnvironmentManager.moveWalls(!move_walls_out);
        }
        if (key == KeyEvent.VK_N) {
            System.out.printf("N key pressed %d\n", key);
        }
        if (key == KeyEvent.VK_C) {
            System.out.printf("C key pressed %d\n", key);
        }
        if (key == KeyEvent.VK_G) {
            System.out.printf("G key pressed %d\n", key);
            mEnvironmentManager.toggleForces(EnvironmentManager.GRAV);
        }
        if (key == KeyEvent.VK_V) {
            System.out.printf("V key pressed %d\n", key);
            mEnvironmentManager.toggleForces(EnvironmentManager.VISC);
        }
        if (key == KeyEvent.VK_M) {
            System.out.printf("M key pressed %d\n", key);
            mEnvironmentManager.toggleForces(EnvironmentManager.COM);
        }
        if (key == KeyEvent.VK_1) {
            System.out.printf("1 key pressed %d\n", key);
            mEnvironmentManager.toggleWallForces(EnvironmentManager.TOP_ID);
        }
        if (key == KeyEvent.VK_2) {
            System.out.printf("2 key pressed %d\n", key);
            mEnvironmentManager.toggleWallForces(EnvironmentManager.RIGHT_ID);
        }
        if (key == KeyEvent.VK_3) {
            System.out.printf("3 key pressed %d\n", key);
            mEnvironmentManager.toggleWallForces(EnvironmentManager.BOTTOM_ID);
        }
        if (key == KeyEvent.VK_4) {
            System.out.printf("4 key pressed %d\n", key);
            mEnvironmentManager.toggleWallForces(EnvironmentManager.LEFT_ID);
        }
        if (key == KeyEvent.VK_EQUALS) {
            System.out.printf("+ key pressed %d\n", key);
            mEnvironmentManager.changeMuscleAmplitude(true);
        }
        if (key == KeyEvent.VK_MINUS) {
            System.out.printf("- key pressed %d\n", key);
            mEnvironmentManager.changeMuscleAmplitude(false);
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
            System.out.println("New Spring created");
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
        System.out.println("Finding nearest mass");
        ArrayList<Mass> all_masses = mEnvironmentManager.getMassList();
        if (all_masses.size() == 0) {
            // THROW ERROR HERE
            System.out.printf("Mass list is empty");
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