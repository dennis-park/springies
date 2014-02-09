package listeners;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Parsers.ModelParser;
import springies.Springies;


public class LoadNewAssemblyListener implements Listener{

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

}
