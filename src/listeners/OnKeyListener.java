package listeners;

import java.awt.event.KeyEvent;
import springies.EnvironmentManager;
import springies.Springies;

/**
 * This class implements a Key Listener in order to toggle walls in (towards center)
 * and out (away from center) in Springies.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 * 
 */
public class OnKeyListener{
    private Springies mSpringies;
    private EnvironmentManager mEnvironmentManager;

    public OnKeyListener (Springies s, EnvironmentManager e_manager) {
        mSpringies = s;
        mEnvironmentManager = e_manager;
    }

    public void doKeyEvent (int key) {
        boolean move_walls_out = true;

        switch (key) {
            case KeyEvent.VK_UP:
                mEnvironmentManager.moveWalls(move_walls_out);
                break;
            case KeyEvent.VK_DOWN:
                mEnvironmentManager.moveWalls(!move_walls_out);
                break;
            case KeyEvent.VK_N:
                mSpringies.makeAssembly();
                break;
            case KeyEvent.VK_C:
                mSpringies.clearLoadedAssemblies();
                break;
            case KeyEvent.VK_G:
                mEnvironmentManager.toggleForces(EnvironmentManager.GRAV_ID);
                break;
            case KeyEvent.VK_V:
                mEnvironmentManager.toggleForces(EnvironmentManager.VISC_ID);
                break;
            case KeyEvent.VK_M:
                mEnvironmentManager.toggleForces(EnvironmentManager.COM_ID);
                break;
            case KeyEvent.VK_1:
                mEnvironmentManager.toggleForces(EnvironmentManager.TOP_ID);
                break;
            case KeyEvent.VK_2:
                mEnvironmentManager.toggleForces(EnvironmentManager.RIGHT_ID);
                break;
            case KeyEvent.VK_3:
                mEnvironmentManager.toggleForces(EnvironmentManager.BOTTOM_ID);
                break;
            case KeyEvent.VK_4:
                mEnvironmentManager.toggleForces(EnvironmentManager.LEFT_ID);
                break;
            case KeyEvent.VK_EQUALS:
                mEnvironmentManager.changeMuscleAmplitude(true);
                break;
            case KeyEvent.VK_MINUS:
                mEnvironmentManager.changeMuscleAmplitude(false);
                break;
        }
    }
}
