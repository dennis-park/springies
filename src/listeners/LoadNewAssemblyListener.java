package listeners;

import java.awt.event.KeyEvent;
import springies.Springies;


public class LoadNewAssemblyListener implements SpringiesListener{

	private Springies mSpringies;

	public LoadNewAssemblyListener(Springies s) {
		mSpringies = s;
	}

	@Override
	public void doAction() {
		mSpringies.makeAssembly();
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
		if (key == 'n') {
			doAction();
		}
		mSpringies.clearKey(key);
	}

}
