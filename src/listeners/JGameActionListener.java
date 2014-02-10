package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import springies.EnvironmentManager;
import springies.Springies;


/**
 * This class implements a Key Listener in order to toggle walls in (towards center)
 * and out (away from center) in Springies. 
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 * 
 */
public class JGameActionListener {
    private EnvironmentManager mEnvironmentManager;
    private boolean leftMouseHeld = false;
    
    public JGameActionListener(EnvironmentManager e_manager) {
        mEnvironmentManager = e_manager;
    }
    
    public JGameActionListener() {
        
    }

    public void doKeyEvent (int key) {
        boolean move_walls_out = true;
        
        if (key == Springies.KeyUp) {
            System.out.printf("Up Key pressed\n");
            mEnvironmentManager.moveWalls(move_walls_out);
        }
        if (key == Springies.KeyDown) {
            System.out.printf("Down Key pressed\n");
            mEnvironmentManager.moveWalls(!move_walls_out);
        }
    }

    public void doMouseEvent (boolean left_pressed, boolean right_pressed, int mouse_x, int mouse_y) {
        boolean left_mouse_released = !left_pressed && leftMouseHeld;
        boolean left_mouse_just_pressed = left_pressed && !leftMouseHeld;
        boolean left_mouse_held = left_pressed && leftMouseHeld;
        
        if (left_mouse_just_pressed) {
            leftMouseHeld = true;
            // Make new mass (new_mass) at position: mouse_x and mouse_y
            // Make new spring connected to new_mass and nearest mass object
        }
        else if (left_mouse_released) {
            leftMouseHeld = false;
            // remove new_mass
            // remove spring
        }
        else if (left_mouse_held) {
            // drag new_mass with mouse_x and mouse_y
        }
        
    }
}