package listeners;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Parsers.ModelParser;
import springies.Springies;


public class LoadNewAssemblyListener implements SpringiesListener{

	private Springies mSpringies;

	public LoadNewAssemblyListener(Springies s) {
		mSpringies = s;
	}

	@Override
	public void doAction() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"XML documents", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showDialog(null, "new Assembly file");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			new ModelParser(mSpringies).loadFile(file);
		}
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
