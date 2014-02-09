package listeners;

import java.awt.event.KeyEvent;

import springies.Springies;

public class ClearAssemblyListener implements SpringiesListener{

	private Springies mSpringies;
	//private EnvironmentManager mEnv;
	
	public ClearAssemblyListener(Springies s) {
		mSpringies = s;
		//mEnv = manager;
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
		// TODO Auto-generated method stub
		
	}

}
