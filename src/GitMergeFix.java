import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

/**
 * GitMergeFix.java - Remove the merge tags from a file.
 * @author Ben
 * @version 09-FEB-2016
 */
public class GitMergeFix implements MergeViewListener {

	private MergeView view;
	private GitFile gitFile;

	public GitMergeFix() {
		view = new MergeView(this);
	}

	public void checkForConflicts() {
		if (gitFile.hasConflicts()) {
			GitConflict gc = gitFile.popConflict();
			view.displayConflict(gc);
			
		} else {
			JFileChooser chooser = new JFileChooser();
			chooser.showSaveDialog(view.frame);
			try(FileWriter fw = new FileWriter(chooser.getSelectedFile())) {
				fw.write(gitFile.getResolvedContent());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void keepHead(GitConflict gc) {
		gc.setSolution(gc.getHead());
		gitFile.resolveConflict(gc);
		checkForConflicts();
	}

	@Override
	public void keepTail(GitConflict gc) {
		gc.setSolution(gc.getTail());
		gitFile.resolveConflict(gc);
		checkForConflicts();
	}
	
	@Override
	public void loadFile(String filename) {
		gitFile = new GitFile(filename);
		checkForConflicts();
	}

}
