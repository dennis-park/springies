package listeners;

import java.awt.event.KeyEvent;

import springies.Springies;
import forces.ForceManager;

public class ToggleForceListener implements SpringiesListener{

	private Springies mSpringies;
	
	public ToggleForceListener(Springies s) {
		mSpringies = s;
	}
	
	@Override
	public void doAction() {
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
		switch (key) {
		case 'g':
			break;
		case 'v':
			break;
		case 'm':
			break;
		}
		mSpringies.clearKey(key);
	}
}
