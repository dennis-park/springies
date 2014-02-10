package listeners;

import java.awt.event.KeyEvent;
import springies.Springies;


public class ClearAssemblyListener implements SpringiesListener{

	private Springies mSpringies;

	public ClearAssemblyListener(Springies s) {
		mSpringies = s;
	}

	@Override
	public void doAction() {
		mSpringies.clearLoadedAssemblies();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int key = e.getKeyChar();
		if (key == 'c') {
			doAction();
		}
		mSpringies.clearKey(key);
	}

}
