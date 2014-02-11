package listeners;

import java.util.ArrayList;
import masses.Mass;
import masses.MouseMass;
import springies.EnvironmentManager;
import springs.Spring;

/**
 * This class implements a on click listener in order to 
 * create a spring and drag the assembly around in Springies.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 * 
 */
public class OnClickListener{
    public static final double DEFAULT_MOUSE_MASS_XPOSITION = -1;
    public static final double DEFAULT_MOUSE_MASS_YPOSITION = -1;
    
    private boolean leftMouseHeld = false;
    private Mass mMouseMass = new MouseMass("mouse_mass", DEFAULT_MOUSE_MASS_XPOSITION,
                                            DEFAULT_MOUSE_MASS_YPOSITION);
    private Spring mMouseSpring;
    private EnvironmentManager mEnvironmentManager;

    public OnClickListener (EnvironmentManager e_manager) {
        mEnvironmentManager = e_manager;
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
            System.out.printf("Left mouse held and moved to (%.2f, %.2f)\n", mouse_x_double,
                              mouse_y_double);
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
        /*
         * Temporary index = 0
         */
        Mass nearest_mass = all_masses.get(0);
        for (Mass mass : all_masses) {
            double current_distance = Spring.computeLength(mouse_mass, mass);
            if (current_distance < shortest_distance) {
                nearest_mass = mass;
                shortest_distance = current_distance;
            }
        }
        return nearest_mass;
    }
}
