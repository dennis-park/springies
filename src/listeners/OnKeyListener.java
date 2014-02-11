package listeners;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import jboxGlue.WorldManager;
import springies.Assembly;
import springies.AssemblyFactory;
import springies.Constants;
import springies.EnvironmentManager;
import springies.Springies;


/**
 * This class implements a on key listener in order to create a spring and drag the assembly
 * around in Springies. Must be initialized in initGame() and called during doFrame() to work.
 * 
 * <p>
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
public class OnKeyListener {
    private Springies mSpringies;
    private EnvironmentManager mEnvironmentManager;

    /**
     * OnKeyListener needs to have knowledge of the EnvironmentManager to call appropriate
     * toggle methods
     * 
     * @param s
     * @param e_manager
     */
    public OnKeyListener (Springies s, EnvironmentManager e_manager) {
        mSpringies = s;
        mEnvironmentManager = e_manager;
    }

    /**
     * <p>
     * Given a key event, called during Springies.doFrame(), perform the appropriate action.
     * </p>
     * <p>
     * Known dependency: clearLastKey() MUST be called after doKeyEvent(key) is called, or else
     * the key state is "stuck"
     * </p>
     * <p>
     * By pressing 'n', allow users to load a new assembly into the simulation. It should be
     * possible to animate multiple assemblies during the same simulation. Each assembly should have
     * its own center of mass. Using a JFileChooser here would be great rather than choosing one
     * randomly or just having multiple copies of the same models.
     * </p>
     * <p>
     * By pressing 'c', allow users to clear all loaded assemblies.
     * </p>
     * <p>
     * By pressing 'g', 'v', 'm', allow users to toggle (turn on if it is off and off if it is on)
     * the application of gravity, viscosity, and center of mass forces, respectively.
     * </p>
     * <p>
     * By pressing '1', '2', '3', '4', allow users to toggle (turn on if it is off and off if it is
     * on) the application of the appropriate wall repulsion forces.
     * </p>
     * <p>
     * By pressing up or down arrow, allow user to increase or decrease the size of the walled area
     * in which the assemblies animate by a fixed amount of pixels on all sides. It is okay to make
     * the size larger than the window's size, that just means an assembly (i.e., a pendulum) would
     * not bounce off the sides of the window. Change muscle's amplitude
     * </p>
     * <p>
     * By pressing '+' or '-', allow users to increase or decrease the amplitude of all muscles in
     * all assemblies by a fixed amount.
     * </p>
     * 
     * @param key
     */
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
                mEnvironmentManager.toggleForces(Constants.GRAV_ID);
                break;
            case KeyEvent.VK_V:
                mEnvironmentManager.toggleForces(Constants.VISC_ID);
                break;
            case KeyEvent.VK_M:
                mEnvironmentManager.toggleForces(Constants.COM_ID);
                break;
            case KeyEvent.VK_1:
                mEnvironmentManager.toggleWallForces(Constants.TOP_ID);
                break;
            case KeyEvent.VK_2:
                mEnvironmentManager.toggleWallForces(Constants.RIGHT_ID);
                break;
            case KeyEvent.VK_3:
                mEnvironmentManager.toggleWallForces(Constants.BOTTOM_ID);
                break;
            case KeyEvent.VK_4:
                mEnvironmentManager.toggleWallForces(Constants.LEFT_ID);
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
