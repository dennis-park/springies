package listeners;

import java.util.List;
import masses.Mass;
import masses.MouseMass;
import springies.EnvironmentManager;
import springs.Spring;


/**
 * This class implements a on click listener in order to create a spring and drag the assembly
 * around in Springies. Must be initialized in init game and called during doFrame() to work.
 * 
 *  * <p>
 * public void initGame () { </br> ... </br> mKeyListener = new OnKeyListener(this,
 * mEnvironmentManager); </br> mMouseListener = new OnClickListener(mEnvironmentManager); </br>
 * 
 * }
 * </p>
 * <p>
 * public void doFrame () { </br> doListenerEvents(); </br> //toggleView(); </br> if
 * (!mAssemblyList.isEmpty()) { </br> mEnvironmentManager.doForces(); </br>
 * WorldManager.getWorld().step(1f, 1); </br> moveObjects(); </br> checkCollision(1 + 2, 1); </br> }
 * </br> } </br>
 * </p>
 * <p>
 * private void doListenerEvents () { </br> int last_key = getLastKey(); </br> clearLastKey(); //
 * last key has to be cleared every time </br> mKeyListener.doKeyEvent(last_key); </br>
 * mMouseListener </br> .doMouseEvent(getMouseButton(1), getMouseButton(3), getMouseX(),
 * getMouseY()); </br> }
 * </p>
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 * 
 */
public class OnClickListener {
    public static final double DEFAULT_MOUSE_MASS_XPOSITION = -1;
    public static final double DEFAULT_MOUSE_MASS_YPOSITION = -1;

    private boolean leftMouseHeld = false;
    private Mass mMouseMass = new MouseMass("mouse_mass", DEFAULT_MOUSE_MASS_XPOSITION,
                                            DEFAULT_MOUSE_MASS_YPOSITION);
    private Spring mMouseSpring;
    private EnvironmentManager mEnvironmentManager;

    /**
     * OnClickListener needs to have knowledge of the EnvironmentManager to call appropriate
     * toggle methods
     * 
     * @param e_manager
     */
    public OnClickListener (EnvironmentManager e_manager) {
        mEnvironmentManager = e_manager;
    }

    /**
     * This doMouseEvent method needs to be called by Springies.java every frame to send the current
     * state of the mouse from the built-in JEngine listener to our "listener" which will call the
     * appropriate methods on the simulation objects.
     * 
     * @param left_pressed
     * @param right_pressed
     * @param mouse_x
     * @param mouse_y
     */
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
            leftMouseHeld = false;
            mMouseMass.setPos(DEFAULT_MOUSE_MASS_XPOSITION, DEFAULT_MOUSE_MASS_YPOSITION);
            mMouseSpring.remove();
        }
        else if (left_mouse_held) {
            mMouseMass.setPos(mouse_x_double, mouse_y_double);
            mMouseSpring.doSpringForce();
        }
    }

    /**
     * Helper method to find the nearest mass. Iterates through all assemblies and all
     * masses to find closest one.
     * 
     * @param mouse_mass
     * @return
     */
    private Mass findNearestMass (Mass mouse_mass) {
        List<Mass> all_masses = mEnvironmentManager.getMassList();
        if (all_masses.size() == 0) {
            System.out.printf("Mass list is empty");
            System.exit(0);
        }
        double shortest_distance = Double.POSITIVE_INFINITY;
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
