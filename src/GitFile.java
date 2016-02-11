
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GitFile {
	
	private Queue<GitConflict> conflicts;
	private ArrayList<String> content;
	
	public GitFile(String filename) {
		content = new ArrayList<String>();
		conflicts = new LinkedList<GitConflict>();
		loadFromFile(filename);
	}
	
	private void loadFromFile(String filename) {
		int currentLineNum = 0;
		String groupSoFar = "";
		String currentHeader = "";
		int headerIndex = -1;
		String commitID = "";
		try {
			Scanner scan = new Scanner(new File(filename));
			// Get one line at a time
			while (scan.hasNext()) {
				String line = scan.nextLine() + "\n";
				// Add the line to the copy of the entire content of the file.
				content.add(line);
				
				if (line.contains(GitConflict.HEADER)) {
					// Keep track of the heads index and reset the group of text so far
					headerIndex = currentLineNum;
					groupSoFar = "";
				} else if (line.contains(GitConflict.MIDDLE)) {
					// Keep track of everything that was in the header and reset groupSoFar to keep track of the tail.
					currentHeader = groupSoFar;
					groupSoFar = "";
				} else if (line.contains(GitConflict.TAIL)) {
					// Add the conflict
					try {
						Scanner sc = new Scanner(line);
						sc.next();
						commitID = sc.next();
						sc.close(); 
					} catch (Exception e) {
						
					}
					conflicts.add(new GitConflict(headerIndex, currentHeader, groupSoFar, commitID, currentLineNum));
				} else {
					// update group so far
					groupSoFar += line;
				}
				currentLineNum++;
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String getResolvedContent() {
		String result = "";
		for (String s : content) {
			result += s;
		}
		return result;
	}
	
	
	public void resolveConflict(GitConflict gc) {
		for (int i = gc.getStartIndex(); i <= gc.getEndIndex(); i++) {
			content.set(i, "");
		}
		content.set(gc.getStartIndex(), gc.getSolution());
	}
	
	public boolean hasConflicts() {
		return !conflicts.isEmpty();
	}
	
	public GitConflict popConflict() {
		return conflicts.remove();
	}
}
