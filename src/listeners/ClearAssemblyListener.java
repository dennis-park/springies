package listeners;

import springies.Springies;

public class ClearAssemblyListener implements Listener{

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

}
